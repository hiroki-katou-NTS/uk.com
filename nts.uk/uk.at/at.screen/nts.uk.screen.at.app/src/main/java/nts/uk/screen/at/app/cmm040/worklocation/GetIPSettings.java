package nts.uk.screen.at.app.cmm040.worklocation;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.worklocation.Ipv4AddressDto;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.net.Ipv4Address;

/**
 * IP設定を取得する
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM040_勤務場所の登録.B：IPアドレス設定.メニュー別OCD.IP設定を取得する
 * @author tutk
 *
 */
@Stateless
public class GetIPSettings {

	@Inject
	private WorkLocationRepository repo;
	
	public List<Ipv4AddressDto> getIP4Address(String workLocationCode) {
		String contractCode = AppContexts.user().contractCode();
		List<Ipv4Address> data = repo.getIPAddressSettings(contractCode, workLocationCode);
		return data.stream().map(c->new Ipv4AddressDto(c)).collect(Collectors.toList());
	}
}
