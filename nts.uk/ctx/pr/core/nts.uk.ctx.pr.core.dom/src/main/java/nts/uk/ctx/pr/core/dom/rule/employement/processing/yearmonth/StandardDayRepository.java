package nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth;

import java.util.List;

import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.standardday.StandardDay;

public interface StandardDayRepository {

	List<StandardDay> select1(String companyCode, int processingNo);
	
	void insert1(StandardDay domain);
	
	void update1(StandardDay domain);
}
