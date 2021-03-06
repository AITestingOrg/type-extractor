package org.aist.aide.formexpert.domain.models.pipe;

import java.util.Queue;

import org.aist.aide.formexpert.domain.models.pipe.filters.Filter;

public class Pipe<T> {
    private Queue<Filter<T>> queue;

    public Pipe(Queue<Filter<T>> queue) {
        this.queue = queue;
    }

    public T exec(T obj) throws Exception {
        while (!queue.isEmpty()) {
            var filter = queue.poll();
            var result = filter.run(obj);
            if (result == null) {
                throw new Exception();
            }
            obj = result;
        }
        return obj;
    }
}
