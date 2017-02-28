package nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth;

import java.util.List;

import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.paydayprocessing.PaydayProcessing;


public interface PaydayProcessingRepository {

	List<PaydayProcessing> findAll3(String companyCode);
}
