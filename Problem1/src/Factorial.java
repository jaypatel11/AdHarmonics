/*
*	Assumption: The programming language is not Java. Java does not support unsigned
*
*/

unsigned long factorial (unsigned long n) 
{ 
return (n == 0) ? 1 : n * factorial(n - 1); 
} 

unsigned long combinatorial(unsigned long n, unsigned long k) 
{ 
return factorial(n) / ( factorial(k) * factorial(n-k) ); 
} 

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


}
