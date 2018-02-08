package nts.uk.ctx.at.shared.dom.worktype.algorithm;

import java.util.List;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 出勤系の勤務種類を取得する
 * @author tanlv
 *
 */
public class AttendanceWorkTypeService {
	@Inject
	public WorkTypeRepository workTypeRepository;
	
	/**
	 * 出勤系の勤務種類を取得する
	 * @return
	 */
	public List<WorkType> acquiredAttendanceWorkType() {
		// ドメインモデル「勤務種類」を取得する
		// ドメインモデル「勤務種類の並び順」を取得する
		// 取得したドメインモデル「勤務種類」を返す
		String companyId = AppContexts.user().companyId();
		List<WorkType> workType = workTypeRepository.getAcquiredAttendanceWorkTypes(companyId);
		
		return workType;
	}
}
