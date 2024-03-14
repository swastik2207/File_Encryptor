import java.time.LocalDateTime;
import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Product {
    private String name;
    private double price;
    private int quantity;
    private List<Double> purchaseHistory;
  private int p_id;
    public Product(int id,String name, double price, int quantity) {
        this.name = name;
        this.p_id=id;
        this.price = price;
        this.quantity = quantity;
        this.purchaseHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId(){
                          return p_id;
                }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Double> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void addToPurchaseHistory(double totalPrice) {
        this.purchaseHistory.add(totalPrice);
    }
}

class Store {
    private List<Product> products;
    private Map<Product, List<Double>> purchaseHistory;

    public Store() {
        this.products = new ArrayList<>();
        this.purchaseHistory = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.add(product);
          try (FileWriter writer = new FileWriter("product_list.txt", true)) {
            writer.write("Product Id: "+ product.getId()+ "  Purchase History for " + product.getName() + " :  The price of the product" + product.getPrice() +" Total unit available "+product.getQuantity()+"\n"); }

        catch (IOException e) {
           e.printStackTrace();
        }
                                  }

    public void updateProduct(String name, double price, int quantity) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                product.setPrice(price);
                product.setQuantity(quantity);
                return;
            }
        }
        System.out.println("Product not found.");
    }

    public void displayProducts() {
        for (Product product : products) {
            if(product.getQuantity()!=0)System.out.println("Name: " + product.getName() + ", Price: $" + product.getPrice() + ", Quantity: " + product.getQuantity());
        }
    }

    public void purchaseProduct(String name, int quantity) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                if (product.getQuantity() >= quantity) {
                    double totalPrice = product.getPrice() * quantity;
                    product.setQuantity(product.getQuantity() - quantity);
                    product.addToPurchaseHistory(totalPrice);
                    purchaseHistory.put(product, product.getPurchaseHistory());
                    System.out.println("Purchase successful: " + quantity + " " + name + " for $" + totalPrice);
                    updatePurchaseHistoryFile(product,quantity);
                } else {
                    System.out.println("Not enough stock available for " + name);
                }
                return;
            }
        }
        System.out.println("Product not found.");
    }   private void updatePurchaseHistoryFile(Product product,int quantity) {
        try (FileWriter writer = new FileWriter("purchase_history.txt", true)) {
                    Date d1 = new Date();
            writer.write("Purchase History for " + product.getName() + ": " + quantity +" units purchased at "+d1+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     public void deleteProduct (String name){


                }


}

 class Main {
    public static void main(String[] args) {
        Store store = new Store();

            Scanner sc =new Scanner(System.in);
          boolean flag=true;
while(flag){
     System.out.println("1.Add a Product\n"+
                        "2.Purchase a Product\n"+
                        "3.Show the Inventory\n"+
                         "4.Update product");

      int input = sc.nextInt();
       if(input==1){


           System.out.println("Enter the ProductName\n");

           String p1=sc.nextLine();
           String productName = sc.nextLine();
                                          System.out.println("Enter the ProductPrice");
           double price = sc.nextDouble();

           System.out.println("Enter the ProductID\n");
           int id=sc.nextInt();
               System.out.println("\n");

           System.out.println("Enter the ProductQuantity");
           int quantity =sc.nextInt();

           store.addProduct(new Product(id,productName,price,quantity));


       // store.displayProducts();
}

   else if (input==2){


        System.out.println("Enter the ProductName\n");
       String n=sc.nextLine();
        String name = sc.nextLine();
        System.out.println("How many Units Needed \n");
        int units = sc.nextInt();
        store.purchaseProduct(name,units);


    }
   else if (input==3){
         store.displayProducts();

        }
   else if (input==4){


           System.out.println("Enter the ProductName\n");

           String p=sc.nextLine();
                     System.out.println("Enter the ProductName\n");

           
           String productName = sc.nextLine();
           System.out.println("Enter the ProductPrice");
           double price = sc.nextDouble();


           System.out.println("Enter the ProductQuantity");
           int quantity =sc.nextInt();

                store.updateProduct(productName,price,quantity);


                }

        }


}
}

