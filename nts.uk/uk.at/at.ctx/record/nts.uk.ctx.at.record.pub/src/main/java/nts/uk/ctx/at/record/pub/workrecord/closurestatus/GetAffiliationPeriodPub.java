package nts.uk.ctx.at.record.pub.workrecord.closurestatus;

import java.time.Period;
import java.util.List;

import nts.arc.time.GeneralDate;

public interface GetAffiliationPeriodPub {

	/**
	 * RequestList588
	 * Alg: 社員の指定期間中の所属期間を取得する（年月）
	 * @param listSid
	 * @param period
	 * @param baseDate
	 */
	EmpAffInfoExport getAffiliationPeriod(List<String> listSid , Period period , GeneralDate baseDate);
	
}
