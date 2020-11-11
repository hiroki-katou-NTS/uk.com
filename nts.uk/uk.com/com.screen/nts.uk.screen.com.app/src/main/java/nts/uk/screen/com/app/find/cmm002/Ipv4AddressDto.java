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
	private Integer ip1;

	/** IPアドレス2 */
	private Integer ip2;

	/** IPアドレス3 */
	private Integer ip3;

	/** IPアドレス4 */
	private Integer ip4;

	public Ipv4AddressDto(Ipv4Address domain) {
		super();
		this.ip1 = domain.getNet1().v();
		this.ip2 = domain.getNet2().v();
		this.ip3 = domain.getHost1().v();
		this.ip4 = domain.getHost2().v();
	}
	
}
