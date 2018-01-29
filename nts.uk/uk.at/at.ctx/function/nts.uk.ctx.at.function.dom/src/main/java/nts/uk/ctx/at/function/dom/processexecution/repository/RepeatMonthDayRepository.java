package nts.uk.ctx.at.function.dom.processexecution.repository;

import java.util.List;

import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthDaysSelect;

public interface RepeatMonthDayRepository {
	// insert
	public void insert(String companyId, String execItemCd, List<RepeatMonthDaysSelect> days);
	
	// remove
	public void removeAllByCidAndExecCd(String companyId, String execItemCd);
}
