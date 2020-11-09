package nts.uk.ctx.sys.gateway.dom.accessrestrictions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import nts.uk.shr.com.net.Ipv4Address;

public class AllowedIPAddressTest {
	
	static class Dummy{
		private static Optional<Ipv4Address> optIpv4Address = Optional.empty();
		private static String comment = "";
	}


	@Test
	public void testIsAccessable_SPECIFIC_OK() {
		Ipv4Address targetIpv4Address = Ipv4Address.toAddress("192.192.192.192");
		AllowedIPAddress allowedIPAddress = new AllowedIPAddress(
				IPAddressRegistrationFormat.SPECIFIC_IP_ADDRESS, 
				Ipv4Address.toAddress("192.192.192.192"), 
				Dummy.optIpv4Address, 
				Dummy.comment);
		
		val result = allowedIPAddress.isAccessable(targetIpv4Address);
		assertThat(result).isEqualTo(true);
	}
	
	@Test
	public void testIsAccessable_SPECIFIC_NG() {
		Ipv4Address targetIpv4Address = Ipv4Address.toAddress("255.255.255.255");
		AllowedIPAddress allowedIPAddress = new AllowedIPAddress(
				IPAddressRegistrationFormat.SPECIFIC_IP_ADDRESS, 
				Ipv4Address.toAddress("192.192.192.192"), 
				Dummy.optIpv4Address, 
				Dummy.comment);
		
		val result = allowedIPAddress.isAccessable(targetIpv4Address);
		assertThat(result).isEqualTo(false);
	}
	
	@Test
	public void testIsAccessable_RANGE_OK() {
		Ipv4Address targetIpv4Address = Ipv4Address.toAddress("192.192.192.192");
		AllowedIPAddress allowedIPAddress = new AllowedIPAddress(
				IPAddressRegistrationFormat.IP_ADDRESS_RANGE, 
				Ipv4Address.toAddress("192.192.192.100"), 
				Optional.of(Ipv4Address.toAddress("192.192.192.200")), 
				Dummy.comment);
		
		val result = allowedIPAddress.isAccessable(targetIpv4Address);
		assertThat(result).isEqualTo(true);
	}
	
	@Test
	public void testIsAccessable_RANGE_NG() {
		Ipv4Address targetIpv4Address = Ipv4Address.toAddress("192.192.192.92");
		AllowedIPAddress allowedIPAddress = new AllowedIPAddress(
				IPAddressRegistrationFormat.IP_ADDRESS_RANGE, 
				Ipv4Address.toAddress("192.192.192.100"), 
				Optional.of(Ipv4Address.toAddress("192.192.192.200")), 
				Dummy.comment);
		
		val result = allowedIPAddress.isAccessable(targetIpv4Address);
		assertThat(result).isEqualTo(false);
	}
}
