package nts.uk.file.pr.app.export.comparingsalarybonus;

import java.util.List;
/**
 * The Interface ComparingSalaryBonusQueryRepository.
 */
public interface ComparingSalaryBonusQueryRepository {
	//EA1
	public List<?> getAllPayment(String companyCode, String personId, int paymentBonus, String yearMonth1, String yearMonth2);
	
	//EA2
	public List<?> getPaymentDetail(String companyCode, String personId, int paymentBonus, String yearMonth1, String yearMonth2);
	
	//EA3
	public List<?> getPayComDetail(String companyCode, String formCode);
	public List<?> findAll();
}