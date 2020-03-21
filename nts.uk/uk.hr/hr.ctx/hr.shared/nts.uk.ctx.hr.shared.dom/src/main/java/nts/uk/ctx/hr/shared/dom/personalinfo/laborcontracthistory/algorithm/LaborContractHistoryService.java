/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.algorithm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.WageTypeDto;

/**
 * @author laitv
 * 
 *
 */

@Stateless
public class LaborContractHistoryService {
	
	@Inject
	private LaborContractHistoryRepository laborContractHisRepo;
	
	
	// 基準日、社員IDリストより、給与区分を取得する
	// path :UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.労働契約履歴.アルゴリズム.基準日、社員IDリストより、給与区分を取得する
	public List<WageTypeDto> getWageTypeInfo(List<String> sids, GeneralDate baseDate){
		
		 List<WageTypeDto> result = laborContractHisRepo.getListWageType(sids, baseDate);
		 return result;
	}
	
		
}
