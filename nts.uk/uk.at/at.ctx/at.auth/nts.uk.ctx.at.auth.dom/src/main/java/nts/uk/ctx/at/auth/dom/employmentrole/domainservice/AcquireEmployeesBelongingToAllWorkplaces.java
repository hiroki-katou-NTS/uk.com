package nts.uk.ctx.at.auth.dom.employmentrole.domainservice;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AffWorkplaceHistoryItemImport;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AuthWorkPlaceAdapter;
import nts.uk.ctx.at.auth.dom.adapter.workplace.WorkplaceInforImport2;

/**
 * @author thanhpv
 * @name 全ての職場の所属社員を取得するPublish
 */
@Stateless
public class AcquireEmployeesBelongingToAllWorkplaces {
	
	@Inject
	private AuthWorkPlaceAdapter authWorkPlaceAdapter;

	/**
	 * @param companyId 会社ID	会社ID
	 * @param baseDate 	基準日	年月日
	 * @return 	所属職場リスト	List＜所属職場履歴項目＞
	 */
	public List<AffWorkplaceHistoryItemImport> get(String companyId, GeneralDate baseDate) {
		//$職場情報一覧 = [No.559]運用している職場の情報をすべて取得する	
		List<WorkplaceInforImport2> list = authWorkPlaceAdapter.getAllActiveWorkplaceInfor(companyId, baseDate);
		//	$職場リスト = $職場情報一覧：map $.職場ID		
		List<String> workPlaceIds = list.stream().map(c->c.getWorkplaceId()).collect(Collectors.toList());
		//		return 職場（List）と基準日から所属職場履歴項目を取得する(職場リスト,基準日)		
		return authWorkPlaceAdapter.getWorkHisItemfromWkpIdsAndBaseDate(workPlaceIds, baseDate);
	}
}
