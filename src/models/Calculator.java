package models;

import java.io.Serializable;
import java.util.Scanner;


class Calculator implements Serializable {
    private double x,y,z;
    static Scanner scanner = new Scanner(System.in);
    private String _numX, _numY, _operation, _clientMessage;
    private int result = 0;

    Calculator() {
    }

    Calculator(int value) {
        result = value;
    }

    public Calculator(double _x,double _y,double _z){
        this.x = _x;
        this.y = _y;
        this.z = _z;
    }

    public Calculator(String numX, String numY, String operation, String message) {
        this._numX = numX;
        this._numY = numY;
        this._operation = operation;
        this._clientMessage = message;
    }

    //setters
    //getters
    public double getZ() {
        return z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String get_numX() {
        return _numX;
    }

    public String get_numY() {
        return _numY;
    }

    public String get_operation() {
        return _operation;
    }

    public String get_clientMessage() {
        return _clientMessage;
    }

    public String toString() {
        return "Calc {" + "X='" + x + '\'' + ", Y='" + y + '\'' + ", Z='" + z + '}';
    }
    public double runCalculateFunction(){
        double FUN1 = Math.log10(Math.pow(y,-(Math.sqrt(Math.abs(x)))));
        double FUN2 = (x-(y/2));
        double FUN3 = Math.pow(Math.sin(Math.atan(z)),2);
        double FUN4 = Math.pow(2.718d,x+y);
        double alpha = FUN1 * FUN2 + FUN3 + FUN4;
        return alpha;
    }

    public int getCalc(int num1, int num2, String operation) {
        int result = 0;
        char[] charArrayOperation = operation.toCharArray();
        switch (charArrayOperation[0]) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
            default:
                System.out.println("Операция не распознана. Повторите ввод.");
        }
        return result;
    }

    public int getInt() {

        System.out.println("Input number:");
        int num;
        if (scanner.hasNextInt()) {
            num = scanner.nextInt();
        } else {
            System.out.println("Error input number. Try again.");
            scanner.next();//рекурсия
            num = getInt();
        }
        return num;
    }

    public char getOperation() {
        System.out.println("Input operation:");
        char operation;
        if (scanner.hasNext()) {
            operation = scanner.next().charAt(0);
        } else {
            System.out.println("Error operation..");
            scanner.next();//рекурсия
            operation = getOperation();
        }
        return operation;
    }


    public int getResult() {
        return result;
    }
}
