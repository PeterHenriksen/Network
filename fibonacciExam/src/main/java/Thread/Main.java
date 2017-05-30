/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thread;

import java.lang.reflect.Array;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter Henriksen
 */
public class Main extends Thread {

    static ExecutorService pool = Executors.newCachedThreadPool();
 

    public static void main(String[] args) {
        BlockingQueue S2 = new ArrayBlockingQueue(11);
        BlockingQueue S1 = new ArrayBlockingQueue(11);
        S1.add(4);
        S1.add(5);
        S1.add(8);
        S1.add(12);
        S1.add(21);
        S1.add(22);
        S1.add(34);
        S1.add(35);
        S1.add(36);
        S1.add(37);
        S1.add(42);
        createThread(10,S1, S2);
        consumerThread(S2);
    }

    public static void createThread(int threads, BlockingQueue S1, BlockingQueue S2) {
        for (int i = 0; i < threads; i++) {
            pool.execute(() -> {
                int number = 0;
                while (!S1.isEmpty()) {
                    number = (int) S1.poll();
                    System.out.println("Producer got " + number);
                    S2.add(fib(number));
                }
            });
        }
    }

    public static void consumerThread(BlockingQueue S2) {
        pool.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            int temp = 0;
            int total = 0;
            while (!S2.isEmpty()) {
                temp = (int) S2.poll();
                System.out.println("consumer got " + temp);
                total += temp;
            }
            System.out.println("total value " + total);
        });
    }

    private static int fib(int n) {
        if ((n == 0) || (n == 1)) {
            return n;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }
}
