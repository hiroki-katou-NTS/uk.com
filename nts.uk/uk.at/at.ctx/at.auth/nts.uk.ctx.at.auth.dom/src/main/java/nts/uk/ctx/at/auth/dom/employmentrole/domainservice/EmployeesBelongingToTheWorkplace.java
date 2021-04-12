package nts.uk.ctx.at.auth.dom.employmentrole.domainservice;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AffWorkplaceHistoryItemImport;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AuthWorkPlaceAdapter;

/**
 * @author thanhpv
 * @name 	職場に所属する社員Publish	
 */
@Stateless
public class EmployeesBelongingToTheWorkplace {

	@Inject
	private AuthWorkPlaceAdapter authWorkPlaceAdapter;
	
	/**
	 * @param workPlaceIds 職場リスト List<職場ID>		
	 * @param baseDate 基準日	
	 * @return 	所属職場リスト	List＜所属職場履歴項目＞
	 */
	public List<AffWorkplaceHistoryItemImport> get(List<String> workPlaceIds, GeneralDate baseDate) {
		//	return 職場（List）と基準日から所属職場履歴項目を取得する(職場リスト,基準日)		
		return authWorkPlaceAdapter.getWorkHisItemfromWkpIdsAndBaseDate(workPlaceIds, baseDate);
	}
}
