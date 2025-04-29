package cn.har01d;

import java.util.concurrent.*;
import java.util.*;
import java.util.stream.*;

public class OptimizedThreadTimeout {

    public List<Message> executeTasks(ExecutorService executor, int taskCount, long timeoutMillis) {
        try {
            // Submit tasks and collect Futures
            List<Future<List<Message>>> futures = IntStream.range(0, taskCount)
                    .mapToObj(i -> executor.submit(createTask(i)))
                    .toList();

            return getResult(futures, timeoutMillis);
        } finally {
            executor.shutdownNow();
        }
    }

    private static List<Message> getResult(List<Future<List<Message>>> futures, long timeoutMillis) {
        long startTime = System.currentTimeMillis();
        List<Message> results = new ArrayList<>();
        List<Future<List<Message>>> incompleteFutures = new ArrayList<>();

        // Process all futures with intelligent timeout handling
        for (Future<List<Message>> future : futures) {
            long remaining = Math.max(1, timeoutMillis - (System.currentTimeMillis() - startTime));

            try {
                List<Message> result = future.get(remaining, TimeUnit.MILLISECONDS);
                results.addAll(result);
            } catch (TimeoutException e) {
                incompleteFutures.add(future);
            } catch (ExecutionException e) {
                System.out.println("Task failed: " + e.getCause());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Execution interrupted", e);
            }
        }

        // Second pass for any futures that might have completed during processing
        Iterator<Future<List<Message>>> iterator = incompleteFutures.iterator();
        while (iterator.hasNext()) {
            Future<List<Message>> future = iterator.next();
            if (future.isDone()) {
                try {
                    results.addAll(future.get());
                    iterator.remove();
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Error getting late-completed task: " + e.getCause());
                }
            }
        }

        // Cancel any truly incomplete tasks
        incompleteFutures.forEach(f -> f.cancel(true));

        return results;
    }

    // Extracted for testability
    protected Callable<List<Message>> createTask(int taskId) {
        return () -> {
            System.out.println("Task " + taskId + " started");
            int workTime = ThreadLocalRandom.current().nextInt(100, 1500);
            Thread.sleep(workTime);
            return List.of(new Message("Task " + taskId + " completed in " + workTime + "ms"));
        };
    }

    public static class Message {
        private final String message;

        public Message(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }

    // Original main method now uses the testable method
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        OptimizedThreadTimeout optimizer = new OptimizedThreadTimeout();

        List<Message> results = optimizer.executeTasks(executor, 10, 1000);

        System.out.println("\nSuccessfully completed tasks (" + results.size() + "):");
        results.forEach(System.out::println);
    }
}
