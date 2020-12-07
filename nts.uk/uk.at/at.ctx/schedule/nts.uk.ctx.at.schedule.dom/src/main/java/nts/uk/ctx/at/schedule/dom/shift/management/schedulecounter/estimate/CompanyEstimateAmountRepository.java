package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import java.util.Optional;

public interface CompanyEstimateAmountRepository {
	
	/**
	 * 
	 * @param cid 会社ID
	 * @param companyEstimateAmount 会社の目安金額
	 */
	void insert(String cid, CompanyEstimateAmount companyEstimateAmount);
	
	/**
	 * 
	 * @param cid 会社ID
	 * @param companyEstimateAmount 会社の目安金額
	 */
	void update(String cid, CompanyEstimateAmount companyEstimateAmount);
	
	/**
	 * 
	 * @param cid 会社ID
	 * @return
	 */
	boolean exist(String cid);
	
	/**
	 * 
	 * @param cid 会社ID
	 * @return
	 */
	Optional<CompanyEstimateAmount> getCompanyEstimateAmount(String cid);

}
