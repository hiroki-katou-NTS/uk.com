package nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth;

import java.util.List;

import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.paydayprocessing.PaydayProcessing;


public interface PaydayProcessingRepository {

	PaydayProcessing select1(String companyCode, int processingNo);
	
	List<PaydayProcessing> select1b(String companyCode, int bonusAtr);
	
	List<PaydayProcessing> select3(String companyCode);
	
	void insert1(PaydayProcessing domain);

	void update1(PaydayProcessing domain);
	
	void update2(PaydayProcessing domain);
}
