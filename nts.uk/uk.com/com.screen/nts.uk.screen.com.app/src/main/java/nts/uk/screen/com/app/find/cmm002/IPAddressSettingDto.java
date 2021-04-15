package nts.uk.screen.com.app.find.cmm002;

import lombok.Getter;
import nts.uk.ctx.sys.gateway.dom.accessrestrictions.IPAddressSetting;

/**
 * @author thanhpv
 * @name IPアドレス設定
 */
@Getter
public class IPAddressSettingDto {

	/** IPアドレス1 */
	private Integer net1;

	/** IPアドレス2 */
	private Integer net2;

	/** IPアドレス3 */
	private Integer host1;

	/** IPアドレス4 */
	private Integer host2;

	public IPAddressSettingDto(IPAddressSetting domain) {
		super();
		this.net1 = domain.getIp1().v();
		this.net2 = domain.getIp2().v();
		this.host1 = domain.getIp3().v();
		this.host2 = domain.getIp4().v();
	}
	
}
