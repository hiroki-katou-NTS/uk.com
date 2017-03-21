package nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.payday.Payday;

public interface PaydayRepository {

	List<Payday> find1_3(String companyCode, int processingNo, int payBonusAtr, int processingYm, int sparePayAtr);

	List<Payday> find2(String companyCode, int payBonusAtr, int processingYm, int sparePayAtr);

	List<Payday> find4(String companyCode, int processingNo);

	List<Payday> find5(String companyCode, int processingNo, int payBonusAtr);

	List<Payday> find6(String companyCode, int processingNo, int processingYm);

	List<Payday> find7(String companyCode, int processingNo, int payBonusAtr, GeneralDate strYmd, GeneralDate endYmd);

	List<Payday> find11(String companyCode, int processingNo, int payBonusAtr, GeneralDate strYmd, GeneralDate endYmd);

	void insert1(Payday domain);
}
