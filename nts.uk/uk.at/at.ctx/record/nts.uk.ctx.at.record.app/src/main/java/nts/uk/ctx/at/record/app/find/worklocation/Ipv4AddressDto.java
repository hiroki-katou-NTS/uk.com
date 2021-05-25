package nts.uk.ctx.at.record.app.find.worklocation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.net.Ipv4Address;
/**
 * 
 * @author tutk
 *
 */
@Getter
@AllArgsConstructor
public class Ipv4AddressDto {

	/** net1 */
	private int net1;

	/** net2 */
	private int net2;

	/** host1 */
	private int host1;

	/** host2 */
	private int host2;

	public Ipv4AddressDto(Ipv4Address domain) {
		super();
		this.net1 = domain.getNet1();
		this.net2 = domain.getNet2();
		this.host1 = domain.getHost1();
		this.host2 = domain.getHost2();
	}
	
	public  Ipv4Address toDomain() {
		return Ipv4Address.parse(this.net1 + "." + this.net2 + "." + this.host1 + "." + this.host2);
	}
	
}
