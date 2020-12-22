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
	private Integer ip1;

	/** IPアドレス2 */
	private Integer ip2;

	/** IPアドレス3 */
	private Integer ip3;

	/** IPアドレス4 */
	private Integer ip4;

	public IPAddressSettingDto(IPAddressSetting domain) {
		super();
		this.ip1 = domain.getIp1().v();
		this.ip2 = domain.getIp2().v();
		this.ip3 = domain.getIp3().v();
		this.ip4 = domain.getIp4().v();
	}
	
}
