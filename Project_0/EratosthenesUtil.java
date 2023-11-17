/**
 * Provides an implementation for the Eratosthene's sieve generator
 * @author Duncan, YOUR NAME
 * <pre>
 * Date: 9/1/2022
 * Course: csc 3102
 * Project # 0
 * Instructor: Dr. Duncan
 * </pre>
 */

import java.util.ArrayList;

public class EratosthenesUtil
{
    /**
     * Generates a sequence of all primes less than the specified parameter
     * @param n the upperbound on the prime sequence
     * @return an array list containing all primes in [2,n] or an empty array
     * list if n < 2.
     */
    public static ArrayList<Long> sieve(long n)
    {
        ArrayList<Long> primes = new ArrayList<>();
        if (n < 2) primes.clear();
        else
        {   boolean isPrime[] = new boolean[(int)n + 1];
            for(int i = 0; i <= n; i++)
                isPrime[i] = true;

            for(int i = 2; i*i <= n; i++)
            {
                if (isPrime[i] == true); // if isPrime = true
                {
                    for (int j = (i * i); j < n + 1; j = j + i) // checks for multiples of i
                    {
                        isPrime[j] = false;   //isPrime = false
                    }
                }
            }
            for (long i = 2; i < n + 1; i++) {
                if (isPrime[(int) i] == true)
                    primes.add(i);
            }
        }
        return primes;
    }

    /**
     * Gives a string representation of the specified array list of longs
     * enclosed in a pair of curly brackets and each adjacent pair of primes
     * separated by a comma followed by a space.
     *
     * @return a string representation of an array list of longs
     */
    public static String toString(ArrayList<Long> v)
    {
        String answer = "{";
            for (int i = 0; i < v.size() - 1; i++)
                answer = answer + v.get(i) +", ";
                answer = answer + v.get(v.size()-1) + "}";
        return answer;
    }
}