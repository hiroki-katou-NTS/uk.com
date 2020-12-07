package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import java.util.Optional;

public interface HandingOfEstimateAmountRepository {
	/**
	 * 
	 * @param cid 会社ID
	 * @param heAmount 目安金額の扱い
	 */
	void insert(String cid, HandingOfEstimateAmount heAmount);

	/**
	 * 
	 * @param cid 会社ID
	 * @param heAmount 目安金額の扱い
	 */
	void update(String cid, HandingOfEstimateAmount heAmount);
	
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
	Optional<HandingOfEstimateAmount> get(String cid);
}
