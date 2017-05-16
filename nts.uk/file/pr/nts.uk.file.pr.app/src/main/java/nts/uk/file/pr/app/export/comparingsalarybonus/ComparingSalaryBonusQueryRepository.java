package nts.uk.file.pr.app.export.comparingsalarybonus;

import java.util.List;

import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.PaycompConfirm;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.SalaryBonusDetail;
/**
 * The Interface ComparingSalaryBonusQueryRepository.
 */
public interface ComparingSalaryBonusQueryRepository {
	public List<SalaryBonusDetail> getContentReportEarlyMonth(String companyCode, List<String> PIDs, int yearMonth1, String formCode);
	public List<SalaryBonusDetail> getContentReportLaterMonth(String companyCode, List<String> PIDs, int yearMonth1, String formCode);
	public List<PaycompConfirm> getPayCompComfirm(String companyCode, List<String> personIDs, int processYMEarly,
			int processYMLater);
	public List<String> getDepartmentCodeList(String companyCode, List<String> PIDs, int yearMonth1, String formCode);
}