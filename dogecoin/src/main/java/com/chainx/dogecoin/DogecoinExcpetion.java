package com.chainx.dogecoin;

public class DogecoinExcpetion extends Exception {

    public DogecoinExcpetion(String errJFun, String errCFun, String error) {
        System.out.println("Dogecoin error function in Android: " + errJFun);
        System.out.println("Dogecoin error function in DLL: " + errCFun);
        System.out.println("Dogecoin dll error: " + error);
    }
}
