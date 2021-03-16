package nts.uk.screen.com.app.find.cmm002;

import lombok.Getter;
import nts.uk.shr.com.net.Ipv4Address;

/**
 * @author thanhpv
 * @name IPアドレス設定
 */
@Getter
public class Ipv4AddressDto {

	/** IPアドレス1 */
	private short ip1;

	/** IPアドレス2 */
	private short ip2;

	/** IPアドレス3 */
	private short ip3;

	/** IPアドレス4 */
	private short ip4;

	public Ipv4AddressDto(Ipv4Address domain) {
		super();
		this.ip1 = domain.getNet1();
		this.ip2 = domain.getNet2();
		this.ip3 = domain.getHost1();
		this.ip4 = domain.getHost2();
	}
	
}
