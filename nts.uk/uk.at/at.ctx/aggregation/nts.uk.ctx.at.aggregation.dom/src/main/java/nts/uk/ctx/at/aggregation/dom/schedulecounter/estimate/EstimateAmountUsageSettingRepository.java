package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

import java.util.Optional;

public interface EstimateAmountUsageSettingRepository {

	/**
	 * 
	 * @param cid 会社ID
	 * @param estimateAmountUsageSetting 目安利用区分
	 */
	void insert(String cid, EstimateAmountUsageSetting estimateAmountUsageSetting);
	
	/**
	 * 
	 * @param cid 会社ID
	 * @param estimateAmountUsageSetting 目安利用区分
	 */
	void update(String cid, EstimateAmountUsageSetting estimateAmountUsageSetting);
	
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
	Optional<EstimateAmountUsageSetting> get(String cid);
	
	
}
