package com.jonathan;

import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class BufferQueue {
    UserList[] users;
    LocalTime startHour;
    int capacityPerTurn, intervalLength;

    public BufferQueue(ServiceMetaData metaData) {
        capacityPerTurn = metaData.getMaxSize();
        startHour = metaData.getStartHour();
        intervalLength = metaData.getIntervalLength();
        int numIntervals = (int) MINUTES.between(metaData.getStartHour(), metaData.getEndHour())
                                                                / metaData.getIntervalLength();
        users = new UserList[numIntervals];
    }

    public boolean insertUser(User user) {
        if (user.getAttentionHour().compareTo(startHour) < 0) {
            return false;
        }

        int INTERVAL_POS = (int) MINUTES.between(startHour, user.getAttentionHour()) / intervalLength;

        if (INTERVAL_POS > users.length) {
            return false;
        }

        if (users[INTERVAL_POS] == null) {
            users[INTERVAL_POS] = new UserList(capacityPerTurn);
        } else if (!users[INTERVAL_POS].isActive) {
            return false;
        }

        users[INTERVAL_POS].pushBack(user);

        return true;
    }
    
    public boolean deleteUser(String id, LocalTime hour) {
        int INTERVAL_POS = (int) MINUTES.between(startHour, hour) / intervalLength;
        
        return users[INTERVAL_POS].delete(id);
    }
}

class UserList {
    public static class SinglyNode {
        public User user;
        public SinglyNode next;

        public SinglyNode(User user) {
            this.user = user;
            this.next = null;
        }
    }

    public SinglyNode head;
    public SinglyNode tail;
    private int size, maxSize;
    public boolean isActive = true;

    public UserList(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int getSize() {
        return size;
    }

    public void pushBack(User user) {
        SinglyNode current = new SinglyNode(user);
        if (isEmpty()) {
            head = tail = current;
            size++;
        } else if (size < maxSize) {
            tail.next = current;
            tail = current;
            size++;
        }
    }

    public User popFront() throws Exception {
        SinglyNode userToTake = null;

        if (isEmpty()) {
            throw new Exception();
        } else {
            userToTake = head;
            head = head.next;
            size--;
            if (isEmpty())
                tail = null;
        }

        return userToTake.user;
    }

    public boolean delete(String uid) {
        SinglyNode elementToDelete = null, previous = null;

        if (isEmpty())
            return false;

        elementToDelete = head;

        if (elementToDelete.user.getUid().equals(uid) && elementToDelete == tail) {
            head = tail = null;
            return true;
        }

        while (!elementToDelete.user.getUid().equals(uid)) {
            if (elementToDelete.next == null) {
                return false;
            } else {
                previous = elementToDelete;
                elementToDelete = elementToDelete.next;
            }
        }

        if (elementToDelete == head) {
            head = head.next;
        } else if (elementToDelete == tail) {
            previous.next = null;
            tail = previous;
        }

        elementToDelete.next = null;
        return true;
    }
}
