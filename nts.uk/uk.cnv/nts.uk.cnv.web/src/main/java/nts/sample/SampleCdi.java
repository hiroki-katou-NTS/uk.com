package nts.sample;

import javax.ejb.Stateless;

@Stateless
public class SampleCdi {

	public int test(String a) {
		return a.length();
	}
}
