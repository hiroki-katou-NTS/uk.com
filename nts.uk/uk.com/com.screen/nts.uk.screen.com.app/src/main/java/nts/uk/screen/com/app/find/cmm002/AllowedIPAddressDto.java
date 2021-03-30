package nts.uk.screen.com.app.find.cmm002;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.sys.gateway.dom.accessrestrictions.AllowedIPAddress;

/**
 * @author thanhpv 
 * @name: 許可IPアドレス
 */
@Getter
@AllArgsConstructor
public class AllowedIPAddressDto{
	
	/** IPアドレスの登録形式 */
	private Integer ipInputType;

	/** 開始アドレス */
	private IPAddressSettingDto startAddress;

	/** 終了アドレス */
	private IPAddressSettingDto endAddress; 
	
	/** 備考 */
	private String comment;

	public AllowedIPAddressDto(AllowedIPAddress domain) {
		super();
		this.ipInputType = domain.getIpInputType().value;
		this.startAddress = new IPAddressSettingDto(domain.getStartAddress());
		this.endAddress = domain.getEndAddress().isPresent()? new IPAddressSettingDto(domain.getEndAddress().get()):null;
		this.comment = domain.getComment().isPresent()?domain.getComment().get().v():null;
	}
}
