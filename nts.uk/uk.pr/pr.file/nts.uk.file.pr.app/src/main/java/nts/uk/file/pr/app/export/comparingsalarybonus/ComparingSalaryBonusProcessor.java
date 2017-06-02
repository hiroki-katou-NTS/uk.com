package nts.uk.file.pr.app.export.comparingsalarybonus;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.file.pr.app.export.comparingsalarybonus.data.SalaryBonusDetail;
import nts.uk.file.pr.app.export.comparingsalarybonus.query.ComparingSalaryBonusQuery;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ComparingSalaryBonusProcessor {
	@Inject
	private ComparingSalaryBonusQueryRepository compareSalaryBonusQueryRepo;

	public void caculate(ComparingSalaryBonusQuery comparingQuery) {
		String  companyCode;
		companyCode= AppContexts.user().companyCode();
		List<SalaryBonusDetail> lstEarlyMonth = this.compareSalaryBonusQueryRepo.getContentReportEarlyMonth(companyCode,
				comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth1(), comparingQuery.getFormCode());
		List<SalaryBonusDetail> lstLaterMonth = this.compareSalaryBonusQueryRepo.getContentReportEarlyMonth(companyCode,
				comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth2(), comparingQuery.getFormCode());
       
	}
}
