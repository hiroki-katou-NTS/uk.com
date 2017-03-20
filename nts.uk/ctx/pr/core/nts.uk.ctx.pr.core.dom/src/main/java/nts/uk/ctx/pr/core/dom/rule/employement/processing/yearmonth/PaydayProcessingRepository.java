package nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth;

import java.util.List;

import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.paydayprocessing.PaydayProcessing;


public interface PaydayProcessingRepository {

	List<PaydayProcessing> select3(String companyCode);
	
	void insert1(PaydayProcessing domain);
}
