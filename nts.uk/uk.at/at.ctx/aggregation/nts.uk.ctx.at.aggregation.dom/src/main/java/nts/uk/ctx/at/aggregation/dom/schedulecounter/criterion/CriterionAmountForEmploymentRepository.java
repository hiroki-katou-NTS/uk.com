package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

public interface EstimateAmountForEmploymentRepository {
	/**
	 * 
	 * @param cid 会社ID
	 * @param employEstimateAmount 雇用の目安金額
	 */
	void insert(String cid, EstimateAmountForEmployment employEstimateAmount);
	
	/**
	 * 
	 * @param cid 会社ID
	 * @param employEstimateAmount 雇用の目安金額
	 */
	void update(String cid, EstimateAmountForEmployment employEstimateAmount);
	
	/**
	 * 
	 * @param cid 会社ID
	 * @param employmentCd 雇用コード
	 * @return
	 */
	boolean exist(String cid, EmploymentCode employmentCd);
	
	/**
	 * 
	 * @param cid 会社ID
	 * @param employmentCd 雇用コード
	 * @return
	 */
	Optional<EstimateAmountForEmployment> getEmploymentEstimateAmount(String cid, EmploymentCode employmentCd);

	
	/**
	 * 
	 * @param cid 会社ID
	 * @return
	 */
	List<EstimateAmountForEmployment> getAllEmploymentEstimateAmount(String cid);
	
	/**
	 * 
	 * @param cid 会社ID
	 * @param employmentCd 雇用コード
	 */
	void delete(String cid, EmploymentCode employmentCd);
}
