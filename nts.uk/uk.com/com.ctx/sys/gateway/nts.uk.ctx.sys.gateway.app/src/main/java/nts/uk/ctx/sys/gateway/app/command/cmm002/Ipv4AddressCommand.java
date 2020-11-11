package nts.uk.ctx.sys.gateway.app.command.cmm002;

import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.net.Ipv4Address;
import nts.uk.shr.com.net.Ipv4Part;

/**
 * @author thanhpv
 * @name IPアドレス設定
 */
@NoArgsConstructor
@Setter
public class Ipv4AddressCommand {

	/** ネットワーク部1 */
	private Ipv4Part net1;

	/** ネットワーク部2 */
	private Ipv4Part net2;

	/** ホスト部1 */
	private Ipv4Part host1;

	/** ホスト部2 */
	private Ipv4Part host2;

	public Ipv4Address toDomain() {
		return new Ipv4Address(this.net1, this.net2, this.host1, this.host2);
	}
	
}
