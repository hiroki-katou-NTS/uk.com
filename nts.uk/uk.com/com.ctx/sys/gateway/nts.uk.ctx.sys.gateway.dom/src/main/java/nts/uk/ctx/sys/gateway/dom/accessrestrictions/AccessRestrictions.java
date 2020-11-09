package nts.uk.ctx.sys.gateway.dom.accessrestrictions;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.net.Ipv4Address;

/**
 * @author thanhpv
 * @name アクセス制限
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.GateWay.アクセス制限.アクセス制限
 */
@Getter
@AllArgsConstructor
public class AccessRestrictions extends AggregateRoot{
	
	/** 契約コード  */
	private ContractCode tenantCode;

	/** アクセス制限機能管理区分  */
	private NotUseAtr accessLimitUseAtr = NotUseAtr.NOT_USE;
	
	/** 許可IPアドレス  */
	private List<AllowedIPAddress> whiteList;
	

	public AccessRestrictions(String tenantCode, NotUseAtr accessLimitUseAtr, List<AllowedIPAddress> whiteList) {
		super();
		this.tenantCode = new ContractCode(tenantCode);
		this.accessLimitUseAtr = accessLimitUseAtr;
		this.whiteList = whiteList;
	}
	
	/** [1] 許可IPアドレスを追加する */
	public void addIPAddress(AllowedIPAddress e) {
		for (AllowedIPAddress ip : this.whiteList) {
			if(ip.getStartAddress().compareTo(e.getStartAddress())) {
				throw new BusinessException("Msg_1835");
			}
		}
		this.whiteList.add(e);
	}
	
	/** [2] 許可IPアドレスを更新する */
	public void updateIPAddress(AllowedIPAddress oldIp, AllowedIPAddress newIp) {
		this.whiteList.removeIf(c-> (c.getStartAddress().compareTo(oldIp.getStartAddress())));
		this.addIPAddress(newIp);
		this.whiteList.sort((AllowedIPAddress x, AllowedIPAddress y) -> x.getStartAddress().toString().compareTo(y.getStartAddress().toString()));
	}
	
	/** [3] 許可IPアドレスを削除する */
	public void deleteIPAddress(Ipv4Address e) {
		this.whiteList.removeIf(c->c.getStartAddress().compareTo(e));
		if(this.whiteList.isEmpty()) {
			this.accessLimitUseAtr = NotUseAtr.NOT_USE;
		}
	}
	
	/**
	 * アクセスできるか
	 * @param ipAddress
	 * @return boolean アクセス可否
	 */
	public boolean isAccessable(Ipv4Address ipAddress) {
		if (accessLimitUseAtr == NotUseAtr.NOT_USE) {
			return true;
		}
		return this.whiteList.stream()
				.map(a -> a.isAccessable(ipAddress))
				.anyMatch(accessable -> accessable == true);
	}	
	
	/** [4] アクセス制限を追加する */
	public void createAccessRestrictions() {
		this.accessLimitUseAtr = NotUseAtr.NOT_USE;
		this.whiteList = new ArrayList<>();
	}
	
	
}
