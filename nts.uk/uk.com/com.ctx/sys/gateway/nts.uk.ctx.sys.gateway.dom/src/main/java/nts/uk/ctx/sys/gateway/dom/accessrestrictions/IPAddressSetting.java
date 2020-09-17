package nts.uk.ctx.sys.gateway.dom.accessrestrictions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.security.audittrail.basic.IPAddress;

/**
 * @author thanhpv
 * @name IPアドレス設定
 */
@Getter
@AllArgsConstructor
public class IPAddressSetting extends DomainObject {

	/** IPアドレス1 */
	private IPAddress ip1;

	/** IPアドレス2 */
	private IPAddress ip2;

	/** IPアドレス3 */
	private IPAddress ip3;

	/** IPアドレス4 */
	private IPAddress ip4;
	
	public boolean equals(IPAddressSetting ipAddressSetting) {
		if (this.ip1.v() == ipAddressSetting.ip1.v()
				&& this.ip2.v() == ipAddressSetting.ip2.v()
				&& this.ip3.v() == ipAddressSetting.ip3.v()
				&& this.ip4.v() == ipAddressSetting.ip4.v()) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		return this.ip1.toString() + this.ip2.toString() + this.ip3.toString() + this.ip4.toString(); 
	}

	public IPAddressSetting(int ip1, int ip2, int ip3, int ip4) {
		super();
		this.ip1 = new IPAddress(ip1);
		this.ip2 = new IPAddress(ip2);
		this.ip3 = new IPAddress(ip3);
		this.ip4 = new IPAddress(ip4);
	}
	
}
