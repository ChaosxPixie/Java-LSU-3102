/**
 * A program to profile the Eratosthene's Sieve prime generator
 * and compare the exact value of the prime-counting function and
 * the n/ln(n) approximation.
 * @author Duncan, Malana Fuentes
 * @see EratosthenesUtil
 * <pre>
 * Date:9/1/2022
 * Course: csc 3102
 * Project # 0
 * Instructor: Dr. Duncan
 * </pre>
 */

import java.util.ArrayList;
import java.util.Scanner;

public class EratosthenesProfiler {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a Number: ");
        long n = scan.nextLong();
//        for(int i = 0; i < primes.size()-1; i++){   (I used this to test the prime numbers)
//          System.out.println(primes.get(i));}
        EratosthenesUtil eUtil = new EratosthenesUtil();
        long start = System.nanoTime();
        ArrayList<Long> piVal = EratosthenesUtil.sieve(n);
        long end = System.nanoTime();
        long microseconds = (end - start) / 1000; //total time in microsec.
        System.out.println("Array of all primes in the range 2 to n is: ");
        System.out.println(piVal); //printing primes
        System.out.println("Time to generate Primes: " + microseconds + " microseconds"); //printing time
        System.out.println("pi(" + n + ") = " + piVal.size()+"\n");

        double[] time = new double[15]; // time
        long[] piVals = new long[15];
        double[] approx = new double[15]; //n/ln(n)
        double[] error = new double[15]; //% error

        int j = 0;
        for(long i = 10000; i <= 150000; i+=10000) {
            start = System.nanoTime(); //START TIME
            ArrayList<Long> plist = eUtil.sieve(i); //CALCULATING SIEVE
            end = System.nanoTime();//END TIME
            microseconds = (end - start) / 1000; //Total time in microseconds
            time[j] = microseconds;
            piVals[j] = plist.size();
            approx[j] = i/Math.log(i);
            error[j] = ((approx[j] - piVals[j])/piVals[j]) * 100;
            j++;
        }
        j = 0;
        System.out.println("  N" + "\t\t Time"+ "\tpi(n)"+ "\t\t n/ln(n)" + "\t\t % Error");
        for(int i = 10000; i <= 150000; i+=10000) {
            System.out.println(i + "\t" + time[j] + "\t"+ piVals[j] +" \t " + approx[j] +  "\t" + error[j]);
            j++;
        }

    }
}
    

