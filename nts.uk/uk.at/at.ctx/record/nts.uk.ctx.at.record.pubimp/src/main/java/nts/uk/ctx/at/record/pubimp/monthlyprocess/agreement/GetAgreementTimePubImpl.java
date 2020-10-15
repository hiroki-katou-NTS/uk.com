package nts.uk.ctx.at.record.pubimp.monthlyprocess.agreement;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreementTime;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetYearAndMultiMonthAgreementTime;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.pub.monthlyprocess.agreement.AgreementTimeExport;
import nts.uk.ctx.at.record.pub.monthlyprocess.agreement.GetAgreementTimePub;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ScheRecAtr;

/**
 * 実装：36協定時間の取得
 * @author shuichi_ishida
 */
@Stateless
public class GetAgreementTimePubImpl implements GetAgreementTimePub {

	@Inject 
	private RecordDomRequireService requireService;
	
	/** 36協定時間の取得 */
	@Override
	public AgreementTimeOfManagePeriod calcAgreementTime(String sid, YearMonth ym,
			List<IntegrationOfDaily> dailyRecord, GeneralDate baseDate, ScheRecAtr scheRecAtr) {
		val require = requireService.createRequire();
		
		return GetAgreementTime.get(require, sid, ym, dailyRecord, baseDate, scheRecAtr);
	}
	
	/** 36協定年間時間の取得 */
	@Override
	public Optional<AgreementTimeYear> getYear(String employeeId, YearMonthPeriod period, GeneralDate criteria, ScheRecAtr scheRecAtr) {
		val require = requireService.createRequire();
		
		return GetAgreementTime.getYear(require, employeeId, period, criteria, scheRecAtr);
	}
	
	/** 36協定上限複数月平均時間の取得 */
	@Override
	public Optional<AgreMaxAverageTimeMulti> getMaxAverageMulti(List<IntegrationOfDaily> dailyRecord, 
			String employeeId, YearMonth yearMonth, GeneralDate criteria, ScheRecAtr scheRecAtr) {
		val require = requireService.createRequire();
		
		return GetAgreementTime.getMaxAverageMulti(require, dailyRecord, employeeId, yearMonth, criteria, scheRecAtr);
	}
	
	/** 36協定上限複数月平均時間と年間時間の取得（日指定） */
	@Override
	public AgreementTimeExport getAverageAndYear(String companyId, String employeeId, YearMonth averageMonth,
			GeneralDate criteria, ScheRecAtr scheRecAtr) {
		val require = requireService.createRequire();
		
		val result = GetYearAndMultiMonthAgreementTime.getByYmAndDate(require, employeeId, criteria, averageMonth, scheRecAtr);
		
		return new AgreementTimeExport(result.getAgreementTimeYear().orElse(null), 
										result.getAgreMaxAverageTimeMulti().orElse(null));
	}
	
	/** 36協定上限複数月平均時間と年間時間の取得（年度指定） */
	@Override
	public AgreementTimeExport getAverageAndYear(String companyId, String employeeId, GeneralDate criteria,
			Year year, YearMonth averageMonth, ScheRecAtr scheRecAtr) {
		val require = requireService.createRequire();
		
		val result = GetYearAndMultiMonthAgreementTime.getByYmAndYear(require, employeeId, criteria, averageMonth, year, scheRecAtr);
		
		return new AgreementTimeExport(result.getAgreementTimeYear().orElse(null), 
				result.getAgreMaxAverageTimeMulti().orElse(null));
	}
}
