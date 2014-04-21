
public class Factorial {


    private static long  factorial (long n)
    {
        return (n == 0) ? 1 : n * factorial(n - 1);
    }

    private static long  combinatorial(long n, long k)
    {

        long originalNumber = n;
        k = (n-k) < k ? n-k:k;


        long numerator =1;

        for(int  i = 0; i < k; i++){

            numerator*= originalNumber--;

        }

        return numerator/factorial(k);
    }


    public static void main(String args[]){

        System.out.println(combinatorial(53,47));

    }
}
