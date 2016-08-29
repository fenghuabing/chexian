import java.util.concurrent.Executors;

import com.picc.chexian.core.enums.FactType;


public class Test {

	public static void main(String[] args) {
		FactType [] a = FactType.values();
		System.out.println(a[0].getKey()+"-"+a[0].name());
		Executors.newFixedThreadPool(3);
		Executors.newCachedThreadPool();
	}

}
