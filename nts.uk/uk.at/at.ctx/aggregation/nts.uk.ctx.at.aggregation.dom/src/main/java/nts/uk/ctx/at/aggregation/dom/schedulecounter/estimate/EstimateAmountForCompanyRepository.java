package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

import java.util.Optional;

public interface EstimateAmountForCompanyRepository {
	
	/**
	 * 
	 * @param cid 会社ID
	 * @param estimateAmountForCompany 会社の目安金額
	 */
	void insert(String cid, EstimateAmountForCompany estimateAmountForCompany);
	
	/**
	 * 
	 * @param cid 会社ID
	 * @param estimateAmountForCompany 会社の目安金額
	 */
	void update(String cid, EstimateAmountForCompany estimateAmountForCompany);
	
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
	Optional<EstimateAmountForCompany> getCompanyEstimateAmount(String cid);

}
