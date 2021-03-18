package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;

/**
 * 
 * @author sonnlb 子の看護介護の勤務種類か判断する
 */
public interface CheckChildCareService {
	public boolean checkChildCare(WorkTypeSet wkSet, String cId);
}
