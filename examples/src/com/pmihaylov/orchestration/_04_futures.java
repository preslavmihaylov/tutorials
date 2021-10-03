package com.pmihaylov.orchestration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.pmihaylov.Common.ignoreCheckedExceptions;

public class _04_futures {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        List<Future<TODO>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final int todoID = i+1;

            Future<TODO> f = exec.submit(() -> fetchRemoteTODO(todoID));
            results.add(f);
        }

        // TODO: Any issues with this?
        // TODO: Exercise -> Fix the issue using CompletionService
        for (Future<TODO> futureTodo : results) {
            TODO todo = ignoreCheckedExceptions((Callable<TODO>) futureTodo::get);
            System.out.printf("processing TODO %d...\n", todo.id);
            System.out.println(todo);
        }

        exec.shutdownNow();
        ignoreCheckedExceptions(() -> exec.awaitTermination(5, TimeUnit.SECONDS));

        // TODO: Exercise -> Get the user associated to the TODO and print them along with it.
        // Hint: Use CompletableFuture
        // Hint: The API is https://jsonplaceholder.typicode.com/users/{userId}
    }

    private static TODO fetchRemoteTODO(int id) {
        return ignoreCheckedExceptions(() -> {
            System.out.printf("Fetching TODO %d...\n", id);
            Thread.sleep(500 + (Math.abs(ThreadLocalRandom.current().nextInt()) % 3000));

            URL url = new URL("https://jsonplaceholder.typicode.com/todos/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();

            //System.out.printf("Fetched TODO %d!\n", id);
            return new ObjectMapper().readValue(responseStream, TODO.class);
        });
    }

    static class TODO {
        public final Integer userId;
        public final Integer id;
        public final String title;
        public final Boolean completed;

        public TODO(@JsonProperty("copyright") Integer userId,
                    @JsonProperty("date") Integer id,
                    @JsonProperty("explanation") String title,
                    @JsonProperty("hdurl") Boolean completed) {
            this.userId = userId;
            this.id = id;
            this.title = title;
            this.completed = completed;
        }

        @Override
        public String toString() {
            return "TODO{" +
                    "\n\tuserId=" + userId +
                    ",\n\tid=" + id +
                    ",\n\ttitle='" + title + '\'' +
                    ",\n\tcompleted=" + completed +
                    "\n}";
        }
    }
}
