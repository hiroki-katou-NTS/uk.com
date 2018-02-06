package nts.uk.ctx.at.shared.dom.worktype.algorithm;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetCheck;
import nts.uk.shr.com.context.AppContexts;

/**
 * 公休系の勤務種類を取得する
 * @author tanlv
 *
 */
public class PublicHolidaysWorkType {
	@Inject
	public WorkTypeRepository workTypeRepository;
	
	/**
	 * 休日系の勤務種類を取得する
	 * @return
	 */
	public List<WorkType> obtainPublicHolidaysWorkType() {
		// ドメインモデル「勤務種類」を取得する
		// ドメインモデル「勤務種類の並び順」を取得する
		// 取得したドメインモデル「勤務種類」を返す
		String companyId = AppContexts.user().companyId();
		List<WorkType> workType = workTypeRepository.getAcquiredHolidayWorkTypes(companyId);
		
		List<WorkType> workTypeNew = new ArrayList<WorkType>();
		
		workType.forEach((item -> {
			WorkTypeSet workTypeSet = item.getWorkTypeSetList().get(0);
			if (workTypeSet.getDigestPublicHd() == WorkTypeSetCheck.CHECK) {
				workTypeNew.add(item);
			}
		}));
		
		return workType;
	}
}
