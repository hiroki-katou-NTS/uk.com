package nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth;

import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.systemday.SystemDay;


public interface SystemDayRepository {

	SystemDay select1(String companyCode, int processingNo);

	void insert(SystemDay domain);
	
	void update1(SystemDay domain);
	
	void delete(SystemDay domain);
}
