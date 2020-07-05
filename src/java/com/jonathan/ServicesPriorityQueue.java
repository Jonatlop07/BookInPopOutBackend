package com.jonathan;

import java.time.LocalTime;

public class ServicesPriorityQueue {
    int size = 0, maxSize;
    LocalTime startHour, endHour;
    User[] heap;

    public ServicesPriorityQueue(ServiceMetaData metaData) {
        maxSize = metaData.getMaxSize();
        heap = new User[maxSize];
        startHour = metaData.getStartHour();
        endHour = metaData.getEndHour();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(User user){
        if (size < maxSize) {
            heap[size] = user;
            sinkUp(size);
            size++;
        }
    }

    public User extractMax() {
        User result = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        
        if (size - 1 > 1) {
            sinkDown(size);
        }
        
        size--;
        
        return result;
    }

    public User remove(String uid) {
        int position = find(uid);

        heap[position].setAttentionHour(LocalTime.MIN);
        sinkUp(position);
        User userToRemove = extractMax();

        return userToRemove;
    }

    private int find(String uid) {
        for (int i = 0; i < size; i++) {
            if (heap[i].getUid().equals(uid)) {
                return i;
            }
        }

        return -1;
    }

    private void sinkUp(int position) {
        int parent = (position - 1) / 2;
        int current = position;

        LocalTime parentAttentionHour = heap[parent].getAttentionHour();
        LocalTime currentAttentionHour = heap[current].getAttentionHour();

        while (current > 0 && parentAttentionHour.compareTo(currentAttentionHour) >= 0) {
            if (parentAttentionHour.compareTo(currentAttentionHour) == 0
                    && heap[parent].getPriority() >= heap[current].getPriority()) {
                break;
            }

            swap(current, parent);
            current = parent;
            currentAttentionHour = heap[current].getAttentionHour();

            parent = (parent - 1) / 2;
            parentAttentionHour = heap[parent].getAttentionHour();
        }
    }

    private void sinkDown(int position) {
        int maxIndex = swapIndexIfLesser(position);

        if (position != maxIndex) {
            swap(position, maxIndex);
            sinkDown(position);
        }
    }

    private int swapIndexIfLesser(int position) {
        if (heap[position] == null) {
            return position;
        } else {
            int leftChild = 2 * position + 1;
            int rightChild = 2 * position + 2;

            LocalTime maxIndexAttentionHour = heap[position].getAttentionHour();
            LocalTime leftChildAttentionHour = LocalTime.MAX;
            LocalTime rightChildAttentionHour = LocalTime.MAX;

            if (leftChild < size) {
                leftChildAttentionHour = heap[leftChild].getAttentionHour();
            }
            if (rightChild < size) {
                rightChildAttentionHour = heap[rightChild].getAttentionHour();
            }

            boolean swapWithLeftChild = leftChildAttentionHour.compareTo(maxIndexAttentionHour) < 0
                    || (leftChildAttentionHour.compareTo(maxIndexAttentionHour) == 0
                            && heap[leftChild].getPriority() > heap[position].getPriority());

            boolean swapWithRightChild = rightChildAttentionHour.compareTo(maxIndexAttentionHour) < 0
                    || (rightChildAttentionHour.compareTo(maxIndexAttentionHour) == 0
                            && heap[rightChild].getPriority() > heap[position].getPriority());

            if (swapWithLeftChild) {
                position = leftChild;
            } else if (swapWithRightChild) {
                position = rightChild;
            }

            return position;
        }
    }

    private void swap(int a, int b) {
        User temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }
}
