package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.shr.com.net.Ipv4Address;
@RunWith(JMockit.class)
public class NarrowDownIPAddressDSTest {

	@Injectable
	private NarrowDownIPAddressDS.Require require;
	@Test
	public void testNarrowDownIPAddressDS() {
		List<Ipv4Address> listIpv4Address = new ArrayList<>();
		listIpv4Address.add(Ipv4Address.parse("192.168.100.1"));
		listIpv4Address.add(Ipv4Address.parse("192.168.100.2"));
		listIpv4Address.add(Ipv4Address.parse("192.168.100.4"));
		listIpv4Address.add(Ipv4Address.parse("192.168.100.5"));
		listIpv4Address.add(Ipv4Address.parse("192.168.100.7"));
		listIpv4Address.add(Ipv4Address.parse("192.168.100.8"));
		listIpv4Address.add(Ipv4Address.parse("192.168.100.10"));
		new Expectations() {
			{
				require.getIPAddressByStartEndIP(192,168, 100, 1, 10);
				result = listIpv4Address;
			}
		};
		Map<Ipv4Address , Boolean> result = NarrowDownIPAddressDS.narrowDownIPAddressDS(192,168, 100, 1, 10, require);
		List<Ipv4Address> listAdd = new ArrayList<>();
		List<Ipv4Address> listError = new ArrayList<>();
		result.forEach((k,v) -> {
			if(v) {
				listAdd.add(k);
			}else {
				listError.add(k);
			}
			
		});
		listAdd.sort(Comparator.comparing(Ipv4Address::getHost2));
		listError.sort(Comparator.comparing(Ipv4Address::getHost2));
		assertThat(listAdd).extracting(d -> d.toString())
		.containsExactly("192.168.100.3", "192.168.100.6","192.168.100.9");
		assertThat(listError).extracting(d -> d.toString())
		.containsExactly("192.168.100.1", "192.168.100.2","192.168.100.4","192.168.100.5", "192.168.100.7","192.168.100.8","192.168.100.10");
		
	}

}
