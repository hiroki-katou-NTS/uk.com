package nts.sample.cache;

import java.util.Optional;

public class SampleDomainService {

	public static int calculate(Require require, String code) {
		return 1;
	}
	
	public static interface Require {
		
		Optional<SampleDomain> getDomain(String code);
		
	}
}
