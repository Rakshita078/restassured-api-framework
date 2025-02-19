package concepts;

public class MethodChaining {

    public static void main(String[] args){
//        MethodChaining methodChaining = new MethodChaining();
//        methodChaining.a1();
//        methodChaining.a2();
//        methodChaining.a3();
//        methodChaining.a1().a2().a3();
        a1().a2().a3();
    }

    public static MethodChaining a1(){
        System.out.println("this is a1 method");
        return new MethodChaining();
    }
    public MethodChaining a2(){
        System.out.println("this is a2 method");
        return this;
    }
    public MethodChaining a3(){
        System.out.println("this is a3 method");
        return this;
    }
}
