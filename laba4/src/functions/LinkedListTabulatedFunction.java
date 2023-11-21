package functions;

import java.io.Serializable;

public class LinkedListTabulatedFunction implements TabulatedFunction, Function, Serializable {
    private class Node{
        private FunctionPoint point;
        private Node prev, next;

        public Node(){
            point = new FunctionPoint();
            prev = next = null;
        }
    }

    private double leftDomainBorder, rightDomainBorder;
    private int actualLength, curId;

    private Node head, tail, cur;

    public LinkedListTabulatedFunction() {
        head = new Node();
        tail = new Node();
        cur = new Node();
    }


    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount){
        if (leftX >= rightX || pointsCount < 2) {
            throw new IllegalArgumentException();
        }

        actualLength = pointsCount;
        leftDomainBorder = leftX;
        rightDomainBorder = rightX;

        cur = head;

        cur.point = new FunctionPoint(leftX, 0);
        cur.next = new Node();
        cur.next.prev = cur;
        cur = cur.next;
        ++curId;
        double deltaX = (rightX - leftX) / (pointsCount );
        for (int i = 0; i < actualLength; i++) {
            cur.point = new FunctionPoint(leftX + i*deltaX, 0);
            cur.next = new Node();
            cur.next.prev = cur;
            cur = cur.next;
            curId++;
        }
        tail = cur.prev;
        tail.next = head;
        head.prev = tail;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values){
        if (leftX >= rightX && values.length < 2) {
            throw new IllegalArgumentException();
        }

        actualLength = values.length;
        leftDomainBorder = leftX;
        rightDomainBorder = rightX;

        head = new Node();
        cur = head;
        head.prev = null;

        cur.point = new FunctionPoint(leftX, values[0]);
        cur.next = new Node();
        cur.next.prev = cur;
        cur = cur.next;
        ++curId;

        double deltaX = (rightX - leftX) / (values.length);

        for (int i = 1; i < actualLength; ++i) {
            cur.point = new FunctionPoint(cur.prev.point.getX() + i*deltaX, values[i]);
            cur.next = new Node();
            cur.next.prev = cur;
            cur = cur.next;
            curId++;
        }
        tail = cur.prev;
        tail.next = head;
        head.prev = tail;
    }

    public Node getNodeByIndex(int index) {
        if (actualLength - index < index ) { //searching in the first half
            if (curId < index ){
                while (curId!=index){
                    cur = cur.next;
                    ++curId;
                }
            }
            else if (Math.abs(curId - index) < index){
                while (curId!=index){
                    cur = cur.prev;
                    --curId;
                }
            }
            else {
                curId = 0;
                cur = head;
                while (curId!=index){
                    ++curId;
                    cur=cur.next;
                }

            }
        }
        else {   // searching in the second half
            if (curId > index){
                while (curId!=index){
                    --curId;
                    cur = cur.prev;
                }

            }
            else if (Math.abs(curId-index) <= index){
                while (curId!=index){
                    ++curId;
                    cur = cur.next;
                }
            }
            else {
                while(curId!=index){
                    --curId;
                    cur = cur.prev;
                }
            }
        }
        return cur;
    }

    public double getPointY(int index) throws IllegalAccessError, FunctionPointIndexOutOfBoundsException{
        indexNLengthCheck(index);
        return getNodeByIndex(index).point.getY();
    }

    public void setPointY(int index, double y) throws IllegalAccessError, FunctionPointIndexOutOfBoundsException{
        indexNLengthCheck(index);
        getNodeByIndex(index).point.setY(y);
    }

    public double getLeftDomainBorder() {
        if (actualLength == 0) {
            throw new IllegalStateException();
        }
        return leftDomainBorder;
    }

    public double getRightDomainBorder() {
        if (actualLength == 0) {
            throw new IllegalStateException();
        }
        return rightDomainBorder;
    }

    public int getActualLength() {
        return actualLength;
    }

    boolean indexNLengthCheck(int index) throws IllegalStateException, FunctionPointIndexOutOfBoundsException{
        boolean correct = true;
        if (actualLength == 0) {
            correct = false;
            throw new IllegalStateException();
        }
        if (index < 0 || index >= actualLength) {
            correct = false;
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return correct;
    }

    boolean nodeXValidnessCheck(Node node, double x) throws InappropriateFunctionPointException{
        double left = Double.MIN_VALUE, right = Double.MAX_VALUE;

        if (node.prev != null) {
            left = node.prev.point.getX();
        }
        if (node.next != null) {
            right = node.next.point.getX();
        }

        if (left > x || right < x) {
            throw new InappropriateFunctionPointException();
        }
        return true;
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException {
        indexNLengthCheck(index);
        Node node = getNodeByIndex(index);
        nodeXValidnessCheck(node, point.getX());

        node.point = new FunctionPoint(point);
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException{
        indexNLengthCheck(index);
        return getNodeByIndex(index).point.getX();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException {
        indexNLengthCheck(index);
        Node node = getNodeByIndex(index);
        nodeXValidnessCheck(node, x);

        node.point.setX(x);
    }

    private void deleteNodeByIndex(int index) throws  FunctionPointIndexOutOfBoundsException { //удалить точку с указанным индексом
        indexNLengthCheck(index);

        getNodeByIndex(index);
        // currentInd=index;
        cur.prev.next = cur.next;
        cur.next.prev = cur.prev;
        cur = cur.prev;
        --curId;

        --actualLength;
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException {
        if (actualLength < 3) {
            throw new IllegalStateException();
        }
        if (index < 0 || index >= actualLength) {
            throw new FunctionPointIndexOutOfBoundsException();
        }

        deleteNodeByIndex(index);
    }

    public double getFunctionValue(double x) throws FunctionPointIndexOutOfBoundsException {
        if (actualLength < 2) {
            throw new IllegalStateException();
        }
        double value = Double.NaN;
        if ((leftDomainBorder > x) || (x > rightDomainBorder)) {
            throw new FunctionPointIndexOutOfBoundsException();
        }

        double y1 = head.point.getY(), y2 = head.next.point.getY();
        double x1 = head.point.getX(), x2 = head.next.point.getX();
        // (y-y1)/(y2-y1) = (x-x1)/(x2-x1)

        if ((x2 - x1) != 0 && (y2 - y1) != 0) {
            value = (y2 - y1) * (x - x1) / (x2 - x1) + y1;
        }
        return value;
    }


    Node addNodeToTail() { //добавить элемент в конец
        tail.next = new Node();
        tail.next.prev = tail;
        tail.next.next = head;
        tail = tail.next;
        ++actualLength;
        head.prev = tail;
        return tail;
    }

    Node addNodeByIndex(int index) throws FunctionPointIndexOutOfBoundsException {//добавить точку в указанное место
        if (index < 0 || index > actualLength) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (index == actualLength) {
            return addNodeToTail();
        }
        getNodeByIndex(index);
        Node node = new Node();
        node.next = cur;
        node.prev = cur.prev;
        cur.prev.next = node;
        cur.prev = node;

        cur = node;
        ++actualLength;

        return cur;

    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException {
        if (actualLength != 0 && (point.getX() < head.point.getX() || point.getX() > tail.point.getX() )) {
            throw new InappropriateFunctionPointException();
        }

        if (actualLength == 0) {
            head = new Node();
            head.point = new FunctionPoint(point);
            ++actualLength;
            tail = head;
            cur = head;
            curId = 0;
            return;
        }

        cur = head;
        curId = 0;
        while (cur.point.getX()< point.getX()) {
            cur = cur.next;
            ++curId;
        }

        if (cur.point.getX() == point.getX()) {
            throw new InappropriateFunctionPointException();
        }

        addNodeByIndex(curId).point = point;
    }








}