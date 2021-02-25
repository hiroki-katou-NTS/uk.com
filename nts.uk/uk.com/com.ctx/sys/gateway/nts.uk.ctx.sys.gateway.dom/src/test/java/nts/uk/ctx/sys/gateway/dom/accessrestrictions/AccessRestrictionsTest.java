package nts.uk.ctx.sys.gateway.dom.accessrestrictions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class AccessRestrictionsTest {

	AllowedIPAddress ip1 = new AllowedIPAddress(new IPAddressSetting(1, 0, 0, 0), 0, Optional.empty(), null);
	AllowedIPAddress ip2 = new AllowedIPAddress(new IPAddressSetting(0, 1, 0, 0), 0, Optional.empty(), "test");
	AllowedIPAddress ip3 = new AllowedIPAddress(new IPAddressSetting(0, 0, 1, 0), 0, Optional.empty(), "test");
	AllowedIPAddress ip4 = new AllowedIPAddress(new IPAddressSetting(0, 0, 0, 1), 0, Optional.empty(), "test");
	AllowedIPAddress ip5 = new AllowedIPAddress(new IPAddressSetting(0, 0, 0, 0), 0, Optional.empty(), "test");
	@Test
	public void getter() {
		AccessRestrictions e = new AccessRestrictions(0, "000000000000", new ArrayList<AllowedIPAddress>());
		NtsAssert.invokeGetters(e);
	}
	@Test
	public void addIPAddress() {
		AccessRestrictions e = new AccessRestrictions(0, "000000000000", new ArrayList<AllowedIPAddress>());
		e.addIPAddress(ip1);
		e.addIPAddress(ip2);
		e.addIPAddress(ip3);
		e.addIPAddress(ip4);
		e.addIPAddress(ip5);
	}
	@Test
	public void addIPAddressEx1835() {
		AccessRestrictions e = new AccessRestrictions(1, "000000000000", new ArrayList<AllowedIPAddress>());
		e.addIPAddress(ip1);
		e.addIPAddress(ip5);
		NtsAssert.businessException("Msg_1835",
				() -> e.addIPAddress(ip5));
	}
	@Test
	public void updateIPAddress() {
		AccessRestrictions e = new AccessRestrictions(1, "000000000000", new ArrayList<AllowedIPAddress>());
		e.addIPAddress(ip1);
		e.addIPAddress(ip2);
		e.updateIPAddress(ip1,ip3);
	}
	@Test
	public void updateIPAddressEx1835() {
		AccessRestrictions e = new AccessRestrictions(1, "000000000000", new ArrayList<AllowedIPAddress>());
		e.addIPAddress(ip1);
		e.addIPAddress(ip2);
		NtsAssert.businessException("Msg_1835", 
				() -> e.updateIPAddress(ip2,ip1));
	}
	@Test
	public void delIPAddress() {
		AccessRestrictions e = new AccessRestrictions(1, "000000000000", new ArrayList<AllowedIPAddress>());
		e.addIPAddress(ip1);
		e.addIPAddress(ip2);
		e.deleteIPAddress(ip1.getStartAddress());
	}
	@Test
	public void delIPAddressNotUseAtr() {
		AccessRestrictions e = new AccessRestrictions(1, "000000000000", new ArrayList<AllowedIPAddress>());
		e.addIPAddress(ip1);
		e.deleteIPAddress(ip1.getStartAddress());
	}
	@Test
	public void AccessRestrictions() {
		AccessRestrictions e = new AccessRestrictions(1, "000000000000", new ArrayList<AllowedIPAddress>());
		e.createAccessRestrictions();
		assertThat(e.getAllowedIPaddress()).isEmpty();
		assertThat(e.getAccessLimitUseAtr().equals(NotUseAtr.NOT_USE)).isTrue();
	}
}
