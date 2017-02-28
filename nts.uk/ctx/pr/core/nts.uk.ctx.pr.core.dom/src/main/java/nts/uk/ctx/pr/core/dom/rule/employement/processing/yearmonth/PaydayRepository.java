package nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.payday.Payday;

public interface PaydayRepository {

	List<Payday> findAll1_3(String companyCode, String processingNo, int payBonusAtr, int processingYm,
			int sparePayAtr);

	List<Payday> findAll2(String companyCode, int payBonusAtr, int processingYm, int sparePayAtr);

	List<Payday> findAll4(String companyCode, String processingNo);

	List<Payday> findAll5(String companyCode, String processingNo, int payBonusAtr);

	List<Payday> findAll6(String companyCode, String processingNo, int processingYm);

	List<Payday> findAll7(String companyCode, String processingNo, int payBonusAtr, GeneralDate strYmd,
			GeneralDate endYmd);

	List<Payday> findAll11(String companyCode, String processingNo, int payBonusAtr, GeneralDate strYmd,
			GeneralDate endYmd);
}
