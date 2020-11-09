package nts.uk.shr.com.net;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lombok.val;

public class Ipv4AddressTest {
	
	static class Dummy{
		private static String address = "192.192.192.192";
		private static Ipv4Address ipv4Address 		= Ipv4Address.toAddress("192.192.192.192");
	}

	@Test
	public void testToString() {
		val result = Dummy.ipv4Address.toString();
		assertThat(result).isEqualTo(Dummy.address);
	}
	
	@Test
	public void testCompareTo_OK() {
		Ipv4Address ipv4Address 	= Ipv4Address.toAddress("192.192.192.192");
		Ipv4Address okAddress 		= Ipv4Address.toAddress("192.192.192.192");
		val result = ipv4Address.compareTo(okAddress);
		assertThat(result).isEqualTo(true);
	}

	@Test
	public void testCompareTo_NG() {
		Ipv4Address ipv4Address 	= Ipv4Address.toAddress("192.192.192.192");
		Ipv4Address ngAddress 		= Ipv4Address.toAddress("123.123.123.123");
		val result = ipv4Address.compareTo(ngAddress);
		assertThat(result).isEqualTo(false);
	}
	
	@Test
	public void testCompareRangeTo_OK() {
		Ipv4Address ipv4Address 	= Ipv4Address.toAddress("192.192.192.192");
		Ipv4Address okStartAddress	= Ipv4Address.toAddress("192.192.192.191");
		Ipv4Address okEndAddress 	= Ipv4Address.toAddress("192.192.192.193");
		val result = ipv4Address.compareRangeTo(okStartAddress, okEndAddress);
		assertThat(result).isEqualTo(true);
	}
	
	@Test
	public void testCompareRangeTo_NG() {
		Ipv4Address ipv4Address 	= Ipv4Address.toAddress("192.192.192.192");
		Ipv4Address ngStartAddress 	= Ipv4Address.toAddress("192.192.192.100");
		Ipv4Address ngEndAddress 	= Ipv4Address.toAddress("192.192.192.150");
		val result = ipv4Address.compareRangeTo(ngStartAddress, ngEndAddress);
		assertThat(result).isEqualTo(false);
	}



}
