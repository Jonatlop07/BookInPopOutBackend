package com.jonathan;

import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

public class ColaPrioridad {
    public static ServicesPriorityQueue cola;
    public static BufferQueue bufferQueue;
    private static Thread timeThread;

    public static void setUpPriorityQueue(ServiceMetaData newMetaData) {
        cola = new ServicesPriorityQueue(newMetaData);
        bufferQueue = new BufferQueue(newMetaData);
        startThread(newMetaData);
    }
    
    public static void startThread(ServiceMetaData newMetaData) {
        timeThread = new Thread() {
            public void run() {
                while (LocalTime.now().compareTo(newMetaData.getEndHour()) <= 0
                        && bufferQueue != null & cola != null) {
                    if (LocalTime.now().compareTo(newMetaData.getStartHour()) >= 0) {
                        int bufferPos = (int) MINUTES.between(newMetaData.getStartHour(), LocalTime.now())
                                                                / newMetaData.getIntervalLength();
                        UserList currentQueue= null;
                        
                        if (bufferPos < bufferQueue.users.length){
                            currentQueue = bufferQueue.users[bufferPos];
                        }

                        while (currentQueue != null && !currentQueue.isEmpty()) {
                            try {
                                    cola.insert(currentQueue.popFront());
                                    if (currentQueue.isActive) {
                                        currentQueue.isActive = false;
                                    }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };

        timeThread.start();
    }

    public static void deletePriorityQueue() {
        cola = null;
        bufferQueue = null;
    }
}
