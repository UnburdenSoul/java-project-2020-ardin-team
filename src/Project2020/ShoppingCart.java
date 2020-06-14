package Project2020;
import java.util.ArrayList;
import java.util.Scanner;


public class ShoppingCart {
	ArrayList <ItemOrdered> orderList = new ArrayList <ItemOrdered>();
	
	
// ��� ������� �� � ��������� �������� ��� item�rdered ����� ������� �� ����� �� �� stock ��� ������������ 
	
	// �������������� � DEFAULT CONSTRUCTOR
	
	void addItemOrdered(ItemOrdered ordered) throws NotEnoughStockException,NegativeOrderException  
{
		boolean flag=false;
		int i=0;

		for ( i=0; i<orderList.size(); i++ )  // ������ �� ������� ��� ���������� ����� ������������
				{
				if(orderList.get(i).getItem().isthesame(ordered.getItem()))
				{
				flag=true;
				break;
				}
			}
		if(ordered.getquantity()<0)
		{
			throw new NegativeOrderException();
		}
		else if (flag)
		{
			// ��� ������ �� ����������� ��� ���������� ������� ��� �� ���� ����������
			// �������� ��� �������� ��� ����������� ���� � ������� �� ���� ��� ���� �����������
			// ����.
			orderList.get(i).setquantity(orderList.get(i).getquantity()+ordered.getquantity());
		}
		else if ((ordered.getquantity())> ((ordered.getItem()).getstock()))
		{
			throw new NotEnoughStockException(); 
		}
		else
		{ 
			ordered.getItem().setstock((ordered.getItem()).getstock()-ordered.getquantity());
			orderList.add(ordered);
		}
		
	
		
		
}
	
void removeItemOrdered(int i)
//������� ���� ����� ��� �������� �� �����
//�� i ����� ������� ��� ��� ���� ���� ����������
//��� �� ARRAY LIST �� ���������!!!!!!!!!!!!!!!!!
	{
		// ��� ��������� ��� ���� ����������� � ������� ���� ��� stock ��� ������������ ��� ��������
		// ��� ���������� ��� �� shopping cart
	orderList.get(i).getItem().setstock(orderList.get(i).getItem().getstock()+orderList.get(i).getquantity());
	orderList.remove(i);
	}

void changeItemOrderedQuantity(int i, int newquantity) throws NotEnoughStockException
// ���� � ������� � ���� ���� �����������
	{
	 try
	 {
		 //������ ��������� ��� �������� ��� ������� ����������� ����� ��� Stock
		 // ��� ���� ��������� �� ���� ��� ���� ��� ������ ������� ������ Stock
		 // ��� �� ��� ����������, �� ������� ���� stock=stock-newquantity
		 // �� ��� ���� throw notenoughstockexception
		 orderList.get(i).getItem().setstock(orderList.get(i).getItem().getstock()+orderList.get(i).getquantity());
		 if (newquantity<orderList.get(i).getItem().getstock())
		  {
			 throw new NotEnoughStockException();
		  }
		 
		 else
		 {
			 orderList.get(i).getItem().setstock(orderList.get(i).getItem().getstock()-newquantity);
			 orderList.get(i).setquantity(newquantity);
		 }
	 }
	 catch(NotEnoughStockException e){}
			//���� �� �� ������� ��� ���� ����.
	
	}

void clearCart()
	{
	int i= orderList.size();
	while(!orderList.isEmpty()) 
	{
		
		
		removeItemOrdered(i-1);
		i--;
	}
	 }
	

double calculateNet()
	{
	 double result=0;
	 for (int i=0; i<orderList.size(); i++ )
	 {		// ��������� �� ���� ����������������� ��� ���� ���������� ��� ����� ���� �� ���� �����������
		 result=result+orderList.get(i).getquantity()*orderList.get(i).getItem().getprice();
	 }
	 return result;
	}
	
double caluclateCourierCost(Buyer buyer) // ������� ��� ������ ��� BUYER TOY SHOPPINGCART
 { 
	
	double courierCost=((calculateNet()*2)/100);
	if (courierCost<3) courierCost=3;
	
	if(buyer.getBuyerCategory()=="SILVER") return(courierCost/2);
	else if(buyer.getBuyerCategory()=="GOLD") return 0;
	else return (courierCost);
 }
	
public void showCart(Buyer buyer) // ������� ��� ������ ��� BUYER TOY SHOPPINGCART
	{
	double finalCost =0;
		for (int i=0; i<this.orderList.size(); i++ )
		{
			System.out.println("Item order number: "+ i);
			System.out.println('\n');
			System.out.println(orderList.get(i).getItem().toString());
			System.out.println('\n'+'\n');
		}
		
		System.out.println("Your Net value is: "+ calculateNet());
		System.out.println("Your courier cost is: "+ caluclateCourierCost(buyer));
		finalCost=calculateNet()+caluclateCourierCost(buyer);
		System.out.println("Your Final cost is: "+ finalCost);
	}
	
 public void checkout(Buyer buyer0)
 
  {
	double fullcost=0;
	showCart(buyer0);
	int flag = Str.inputInt('\n'+"Press 1 to continue on with the transaction or 2 to", 1, 2); 
    
    if (flag==1)
    {
    	int i=orderList.size();
    	
   	 while(!orderList.isEmpty()) 
    	{
    		fullcost=fullcost+orderList.get(i-1).getItem().getprice()*orderList.get(i-1).getquantity();
    		
    		orderList.remove(i-1);
    		i--;
    	}
   	
   	 
   	 buyer0.awardBonus(fullcost);
    }
    else 
    {																//������� !!!!! ����� ������ ��� �����
    	System.out.println("You did not complete the transaction");
      
    }
   
  }

}
