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
        double deltaX = (rightX - leftX) / (pointsCount - 1);
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

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException{
        if (leftX >= rightX && values.length < 2) {
            throw new IllegalArgumentException();
        }
        double deltaX = (rightX - leftX) / (values.length - 1);

        curId = 0;
        for ( ; actualLength < values.length; ++curId) {
            FunctionPoint p = new FunctionPoint(curId*deltaX + leftX, values[curId]);
            addBack(p);
        }
    }

    public Node getNodeByIndex(int index) {
        if (actualLength - index > index ) { //searching in the first half
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

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException{
        indexNLengthCheck(index);
        return getNodeByIndex(index).point;
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

    public void addBack(FunctionPoint point) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException{
        if (actualLength==0){
            head = new Node();
            head.point = point;

            head.next = tail;
            head.prev = tail;
            tail = head;
        }
        else{
            Node newNode = new Node();
            newNode.point = point;

            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            tail.next = head;
        }
        ++actualLength;
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


    public String toString(){
        String res = "{ ";
        Node tmp = head;
        do {
            res+= tmp.point.toString() + ", ";
            tmp = tmp.next;

        }while (tmp!=head);
        res+="}";
        return res;
    }

    public boolean equals(Object o) {
        boolean res = false;
        if (o instanceof LinkedListTabulatedFunction){
            LinkedListTabulatedFunction l = (LinkedListTabulatedFunction) o;
            if (this.actualLength == l.getActualLength()) {
                Node first1 = this.head;
                Node first2 = l.head;
                res = first1.point.equals(first2.point);

                Node tmp1 = first1.next;
                Node tmp2 = first2.next;

                while (tmp1 == tmp2 && tmp1 != first1 && res)
                {
                    res = tmp1.point.equals(tmp2.point);
                    tmp1 = tmp1.next;
                    tmp2 = tmp2.next;
                }
            }
        }
        else if (o instanceof TabulatedFunction){
            TabulatedFunction f = (TabulatedFunction) o;
            if (this.actualLength == f.getActualLength()){
                res = true;
                Node first = head;
                try {
                    for (int i = 0; (i < actualLength) && res; ++i){
                        res = first.point.equals(f.getPoint(i));
                        first = first.next;
                    }
                }
                catch (Exception ex){
                    res = false;
                }
            }
        }
        return res;
    }

    public int hashCode() {
        int prime = 17;
        int res = 1;
        Node tmp = head;
        for (int i = 0; i < actualLength; ++i){
            res = (int) (res*prime + tmp.point.hashCode());
            tmp = tmp.next;
        }
        return res + actualLength;
    }

    public Object clone(){
        LinkedListTabulatedFunction l = new LinkedListTabulatedFunction();
        try {
            Node tmp = head;
            do{
                l.addPoint(tmp.point);
                tmp = tmp.next;
            }while (tmp!=head);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return  l;
    }








}