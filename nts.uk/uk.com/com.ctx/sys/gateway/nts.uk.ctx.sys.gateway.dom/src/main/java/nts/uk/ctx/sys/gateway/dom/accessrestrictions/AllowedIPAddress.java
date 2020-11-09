package nts.uk.ctx.sys.gateway.dom.accessrestrictions;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.net.Ipv4Address;

/**
 * @author thanhpv 
 * @name: 許可IPアドレス
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.GateWay.アクセス制限.許可IPアドレス
 */
@Getter
public class AllowedIPAddress extends DomainObject{
	
	/** IPアドレスの登録形式 */
	private IPAddressRegistrationFormat ipInputType;

	/** 開始アドレス */
	private Ipv4Address startAddress;

	/** 終了アドレス */
	private Optional<Ipv4Address> endAddress; 
	
	/** 備考 */
	private Optional<IPAddressComment> comment;

	public AllowedIPAddress(IPAddressRegistrationFormat ipInputType, Ipv4Address startAddress, Optional<Ipv4Address> endAddress, String comment) {
		super();
		this.ipInputType = ipInputType;
		this.startAddress = startAddress;
		this.endAddress = endAddress;
		this.comment = comment == null ? Optional.empty(): Optional.of(new IPAddressComment(comment));
	}
	
	/**
	 * アクセスできるか
	 * @param ipAddress
	 * @return boolean アクセス可否
	 */
	public boolean isAccessable(Ipv4Address ipAddress) {
		switch(this.ipInputType){
		case IP_ADDRESS_RANGE:
			return ipAddress.compareRangeTo(this.startAddress, this.endAddress.get());
		case SPECIFIC_IP_ADDRESS:
			return ipAddress.compareTo(this.startAddress);
		default:
			throw new IllegalArgumentException("case文を追加してください");
		}
	}
} 