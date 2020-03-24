package nts.uk.ctx.at.function.app.find.statement.outputitemsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class OutputScreenListOfStampFinder {

	@Inject
	private CompanyAdapter company;
	
	@Inject
	private ClosureService closureService;

	// 起動する(khởi động)
	public OutputScreenListOfStampDto initScreen() {
			String employeeID = AppContexts.user().employeeId();
			GeneralDate ymd = GeneralDate.today();
			OutputScreenListOfStampDto result = new OutputScreenListOfStampDto();
		// [RQ622]会社IDから会社情報を取得する
		CompanyInfor companyInfo = company.getCurrentCompany().orElseGet(() -> {
			throw new RuntimeException("System Error: Company Info");
		});
		// 社員に対応する締め期間を取得する(Lấy closurePeriod ứng với employee)
		DatePeriod period = closureService.findClosurePeriod(employeeID, ymd);
		result.setCompanyCode(companyInfo.getCompanyCode());
		result.setCompanyName(companyInfo.getCompanyName());
		result.setStartDate(period.start());
		result.setEndDate(period.end());
		return result;

	}
}
