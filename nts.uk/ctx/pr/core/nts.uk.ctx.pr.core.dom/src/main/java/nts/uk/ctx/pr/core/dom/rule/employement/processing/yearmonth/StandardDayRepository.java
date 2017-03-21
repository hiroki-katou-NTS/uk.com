package nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth;

import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.standardday.StandardDay;

public interface StandardDayRepository {
	
	StandardDay select1(String companyCode, int processingNo);
	
	void insert1(StandardDay domain);
	
	void update1(StandardDay domain);
}
