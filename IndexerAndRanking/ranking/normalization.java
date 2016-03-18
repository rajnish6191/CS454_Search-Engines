package ranking;

import java.math.BigDecimal;
import java.math.MathContext;


public class normalization {

    
    
    public static void main(String args[])
    {
  
        
        
        
        
double d1 = 132.5d;
double d2 = 767.1d;
MathContext mc = new MathContext(4);
Decimal bd1 = new Decimal(0.2d);
Decimal bd2 = new Decimal(0.7d);

    
BigDecimal result = bd1.multiply(bd2,mc);


        System.out.print("value== "+result.doubleValue());
        
    }
	public  double  normalization(double value) {
		double normalizing=0;
		if(value>1 )
		{   
			normalizing= (1-0)/((1-0)*(value-1)+1);
		}  
		return normalizing; 
	}
}