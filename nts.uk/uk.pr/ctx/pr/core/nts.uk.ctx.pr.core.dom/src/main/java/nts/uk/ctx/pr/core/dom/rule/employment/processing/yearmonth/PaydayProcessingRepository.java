package nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.paydayprocessing.PaydayProcessing;

public interface PaydayProcessingRepository {

	PaydayProcessing selectOne(String companyCode, int processingNo);

	List<PaydayProcessing> select1(String companyCode);

	/**
	 * Added by sonnh1
	 * 
	 * @param companyCode
	 * @param processingNo
	 * @return
	 */
	Optional<PaydayProcessing> select2(String companyCode, int processingNo);

	List<PaydayProcessing> select3(String companyCode);

	List<PaydayProcessing> select4(String companyCode);

	void insert1(PaydayProcessing domain);

	void update1(PaydayProcessing domain);

	void update2(PaydayProcessing domain);
}
