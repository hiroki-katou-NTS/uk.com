package nts.uk.file.pr.app.export.comparingsalarybonus;

import java.util.List;

import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ComparingFormDetail;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.PaymentDetail;
/**
 * The Interface ComparingSalaryBonusQueryRepository.
 */
public interface ComparingSalaryBonusQueryRepository {
	
	//join EA1 by month 1
	public List<DetailDifferential> getDetailDifferentialWithEarlyYM(String companyCode, List<String> personIDs, int yearMonth1, int categoryATR,
			int payBonusAttribute, String itemCode);
	
	//join EA1 by month 2
	public List<DetailDifferential> getDetailDifferentialWithLaterYM(String companyCode, List<String> personIDs, int yearMonth1, int categoryATR,
			int payBonusAttribute, String itemCode);
	
	//SEL_1 from QlsptPaycompFormDetail
	public List<ComparingFormDetail> getPayComDetailByFormCode(String companyCode, String formCode);
	
	
	//EA2 
	public List<PaymentDetail> getPaymentDetail(String companyCode, List<String> personIds, int payBonusAttr, int processingYm);
}