package nts.uk.ctx.sys.gateway.dom.accessrestrictions;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * @author thanhpv 
 * @name: 許可IPアドレス
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.GateWay.アクセス制限.許可IPアドレス
 */
@Getter
public class AllowedIPAddress extends DomainObject{

	/** 開始アドレス */
	private IPAddressSetting startAddress;
	
	/** IPアドレスの登録形式 */
	private IPAddressRegistrationFormat ipInputType;

	/** 終了アドレス */
	private Optional<IPAddressSetting> endAddress; 
	
	/** 備考 */
	private Optional<IPAddressComment> comment;

	public AllowedIPAddress(IPAddressSetting startAddress, int ipInputType,
			Optional<IPAddressSetting> endAddress, String comment) {
		super();
		this.startAddress = startAddress;
		this.ipInputType = IPAddressRegistrationFormat.valueOf(ipInputType);
		this.endAddress = endAddress;
		this.comment = comment == null ? Optional.empty(): Optional.of(new IPAddressComment(comment));
	}
	
} 