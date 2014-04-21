/*
*	Assumption: The programming language is not Java. Java does not support unsigned.
*
*/



unsigned long factorial (unsigned long n) 
{ 
	return (n == 0) ? 1 : n * factorial(n - 1); 
} 

/*
* To find nCk, the below method is ineffecient. There are a lot of calls to the factorial() function which will utilize a lot of stack. 
*The long avoids overflow but there are unnecessary multiplications which can be avoided
* For eg. 52C47 = 52C5 = (52*51*50*49*48)/fact(5). This is just one call to fact() and k changes from 47 to 5. Using the function below, it will be calculated as fact(52)/(fact(5)*fact(47) 
*which makes 3 calls to fact() function and has less multiplication overhead. Thus, we need to keep the value of k as small as possible. 
*This can be achieved by checking if k > (n-k). If yes, k = n- k. 
*
*/
unsigned long combinatorial(unsigned long n, unsigned long k) 
{ 
	return factorial(n) / ( factorial(k) * factorial(n-k) ); 
}

/*
* I would try to make the function something like below
*/
    
static unsigned long  betterCombinatorial(unsigned long n, unsigned long k)
{

        long originalNumber = n;
        //If k is larger than n-k
		k = k >(n-k) ? n-k:k;


        long numerator =1;

		//Calculate numerator part.
        for(int  i = 0; i < k; i++){

            numerator*= originalNumber--;

        }

		return numerator/factorial(k);
}

