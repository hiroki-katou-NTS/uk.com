package nts.uk.ctx.at.record.dom.workrecord.actualsituation;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.company.AffComHistItemImport;
import nts.uk.ctx.at.record.dom.adapter.company.AffCompanyHistImport;
import nts.uk.ctx.at.record.dom.adapter.company.SyCompanyRecordAdapter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * @author thanhnx
 * フレックス不足の相殺が実施できるかチェックする
 */
@Stateless
public class CheckShortageFlex {
//	 @Inject
//	 private ClosureEmploymentService closureEmploymentService;
	
	@Inject
	private SyCompanyRecordAdapter syCompanyRecordAdapter;
	
	public boolean checkShortageFlex(String employeeId, GeneralDate date){
		
		// mock ClosureEmploymentService
		DatePeriod datePeriod = new DatePeriod(GeneralDate.today(), GeneralDate.max());
		// パラメータ「基準日」がパラメータ「当月の期間」に含まれているかチェックする
		if (date.before(datePeriod.start()) || date.after(datePeriod.end()))
			return false;
		List<AffCompanyHistImport> affCompanyHis = syCompanyRecordAdapter.getAffCompanyHistByEmployee(Arrays.asList(employeeId), datePeriod);
		if (!affCompanyHis.isEmpty()) {
			for (AffComHistItemImport his : affCompanyHis.get(0).getLstAffComHistItem()) {
				DatePeriod datePeriodHis = his.getDatePeriod();
				GeneralDate endDate = datePeriodHis.end();
				if (endDate != null
						&& (endDate.afterOrEquals(datePeriod.start()) && endDate.beforeOrEquals(datePeriod.end()))) {
					datePeriod = new DatePeriod(datePeriod.start(), endDate);
				}
			}
		}
		//TODO 対象期間の日の承認が済んでいるかチェックする
		
		//TODO 対象月の承認が済んでいるかチェックする
		return true;
	}
}
