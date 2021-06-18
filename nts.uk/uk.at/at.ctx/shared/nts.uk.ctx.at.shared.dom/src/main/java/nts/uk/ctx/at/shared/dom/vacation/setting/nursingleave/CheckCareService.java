package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.CheckCareResult;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;

/**
 * 
 * @author sonnlb 子の看護介護の勤務種類か判断する
 */
public interface CheckCareService {
	public CheckCareResult checkCare(WorkTypeSet wkSet, String cId);
}
