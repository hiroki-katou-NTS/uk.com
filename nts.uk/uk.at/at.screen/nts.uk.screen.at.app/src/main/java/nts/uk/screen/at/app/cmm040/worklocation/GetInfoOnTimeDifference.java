package nts.uk.screen.at.app.cmm040.worklocation;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement.RegionalTimeDifference;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement.RegionalTimeDifferenceRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * SQ: 地域別時差管理の情報を取得する
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM040_勤務場所の登録.A：勤務場所の登録.メニュー別OCD.勤務場所を取得する
 * @author chungnt
 *
 */

@Stateless
public class GetInfoOnTimeDifference {
	
	@Inject
	private RegionalTimeDifferenceRepository regionalTimeDifferenceRepo;
	
	public List<GetInfoOnTimeDifferenceDto> getInfoOnTimeDifference() {
		
		List<RegionalTimeDifference> regionalTimeDifferences = regionalTimeDifferenceRepo.getAll(AppContexts.user().contractCode());
		
		return regionalTimeDifferences.stream().map(m -> {
			return new GetInfoOnTimeDifferenceDto(m.getCode().v(), m.getName().v(), m.getRegionalTimeDifference().v());
		}).collect(Collectors.toList());
	}	
}
