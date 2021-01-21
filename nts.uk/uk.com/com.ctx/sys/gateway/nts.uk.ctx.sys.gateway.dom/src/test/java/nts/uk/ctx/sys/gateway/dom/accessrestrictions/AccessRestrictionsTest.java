package nts.uk.ctx.sys.gateway.dom.accessrestrictions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Mocked;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.net.Ipv4Address;

public class AccessRestrictionsTest {
	@Mocked AllowedIPAddress allowedIPAddress;
	
	static class Dummy{
		private static String tenantCode = "000000000000";
		private static IPAddressRegistrationFormat ipInputType = IPAddressRegistrationFormat.valueOf(0);
		private static AllowedIPAddress ip1 = new AllowedIPAddress(ipInputType, new IPAddressSetting(1, 0, 0, 0), Optional.empty(), null);
		private static AllowedIPAddress ip2 = new AllowedIPAddress(ipInputType, new IPAddressSetting(0, 1, 0, 0), Optional.empty(), null);
		private static AllowedIPAddress ip3 = new AllowedIPAddress(ipInputType, new IPAddressSetting(0, 0, 1, 0), Optional.empty(), null);
		private static AllowedIPAddress ip4 = new AllowedIPAddress(ipInputType, new IPAddressSetting(0, 0, 0, 1), Optional.empty(), null);
		private static AllowedIPAddress ip5 = new AllowedIPAddress(ipInputType, new IPAddressSetting(0, 0, 0, 0), Optional.empty(), null);
		private static Ipv4Address address = Ipv4Address.parse("255.255.255.255");
		private static AllowedIPAddress allowedAddress = new AllowedIPAddress(ipInputType, new IPAddressSetting(255, 255, 255, 255), Optional.empty(), null);
		private static List<AllowedIPAddress> whiteList = new ArrayList<AllowedIPAddress>();
		private static boolean result = true;
	}
	
	@Test
	public void getter() {
		AccessRestrictions e = new AccessRestrictions(Dummy.tenantCode, NotUseAtr.NOT_USE, new ArrayList<AllowedIPAddress>());
		NtsAssert.invokeGetters(e);
	}
	
	@Test
	public void addIPAddress() {
		AccessRestrictions e = new AccessRestrictions(Dummy.tenantCode, NotUseAtr.NOT_USE, new ArrayList<AllowedIPAddress>());
		e.addIPAddress(Dummy.ip1);
		e.addIPAddress(Dummy.ip2);
		e.addIPAddress(Dummy.ip3);
		e.addIPAddress(Dummy.ip4);
		e.addIPAddress(Dummy.ip5);
	}
	
	@Test
	public void addIPAddressEx1835() {
		AccessRestrictions e = new AccessRestrictions(Dummy.tenantCode, NotUseAtr.USE, new ArrayList<AllowedIPAddress>());
		e.addIPAddress(Dummy.ip1);
		e.addIPAddress(Dummy.ip5);
		NtsAssert.businessException("Msg_1835",
				() -> e.addIPAddress(Dummy.ip5));
	}
	
	@Test
	public void updateIPAddress() {
		AccessRestrictions e = new AccessRestrictions(Dummy.tenantCode, NotUseAtr.USE, new ArrayList<AllowedIPAddress>());
		e.addIPAddress(Dummy.ip1);
		e.addIPAddress(Dummy.ip2);
		e.updateIPAddress(Dummy.ip1,Dummy.ip3);
	}
	
	@Test
	public void updateIPAddressEx1835() {
		AccessRestrictions e = new AccessRestrictions(Dummy.tenantCode, NotUseAtr.USE, new ArrayList<AllowedIPAddress>());
		e.addIPAddress(Dummy.ip1);
		e.addIPAddress(Dummy.ip2);
		NtsAssert.businessException("Msg_1835", 
				() -> e.updateIPAddress(Dummy.ip2,Dummy.ip1));
	}
	
	@Test
	public void delIPAddress() {
		AccessRestrictions e = new AccessRestrictions(Dummy.tenantCode, NotUseAtr.USE, new ArrayList<AllowedIPAddress>());
		e.addIPAddress(Dummy.ip1);
		e.addIPAddress(Dummy.ip2);
		e.deleteIPAddress(Dummy.ip1.getStartAddress());
	}
	
	@Test
	public void delIPAddressNotUseAtr() {
		AccessRestrictions e = new AccessRestrictions(Dummy.tenantCode, NotUseAtr.USE, new ArrayList<AllowedIPAddress>());
		e.addIPAddress(Dummy.ip1);
		e.deleteIPAddress(Dummy.ip1.getStartAddress());
	}
	
	@Test
	public void AccessRestrictions() {
		AccessRestrictions e = new AccessRestrictions(Dummy.tenantCode, NotUseAtr.USE, new ArrayList<AllowedIPAddress>());
		e.createAccessRestrictions();
		assertThat(e.getWhiteList()).isEmpty();
		assertThat(e.getAccessLimitUseAtr().equals(NotUseAtr.NOT_USE)).isTrue();
	}
	
	@Test
	public void testIsAccessable_NotUse() {
		AccessRestrictions restrictions = new AccessRestrictions(
				Dummy.tenantCode, 
				NotUseAtr.NOT_USE, 
				Dummy.whiteList);
		
		val result = restrictions.isAccessable(Dummy.address);
		assertThat(result).isEqualTo(true);
	}
	
	@Test
	public void testIsAccessable_USE() {
		AccessRestrictions restrictions_OK = new AccessRestrictions(
				Dummy.tenantCode, 
				NotUseAtr.USE, 
				Dummy.whiteList);

		restrictions_OK.addIPAddress(Dummy.allowedAddress);
		
		new Expectations() {{
			allowedIPAddress.isAccessable(Dummy.address);
			result = Dummy.result;
		}};
		
		val result = restrictions_OK.isAccessable(Dummy.address);
		assertThat(result).isEqualTo(Dummy.result);
	}
	
}
