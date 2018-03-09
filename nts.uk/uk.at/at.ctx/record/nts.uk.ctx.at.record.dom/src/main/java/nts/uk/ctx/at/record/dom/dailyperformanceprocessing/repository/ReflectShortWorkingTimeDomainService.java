package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import nts.arc.time.GeneralDate;

//短時間勤務時間帯を反映する
public interface ReflectShortWorkingTimeDomainService {
	public void reflect(String companyId,GeneralDate date, String employeeId);
}
