package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

public interface EmploymentEstimateAmountRepository {
	/**
	 * 
	 * @param cid 会社ID
	 * @param employEstimateAmount 雇用の目安金額
	 */
	void insert(String cid, EmploymentEstimateAmount employEstimateAmount);
	
	/**
	 * 
	 * @param cid 会社ID
	 * @param employEstimateAmount 雇用の目安金額
	 */
	void update(String cid, EmploymentEstimateAmount employEstimateAmount);
	
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
	Optional<EmploymentEstimateAmount> getEmploymentEstimateAmount(String cid, EmploymentCode employmentCd);

	
	/**
	 * 
	 * @param cid 会社ID
	 * @return
	 */
	List<EmploymentEstimateAmount> getAllEmploymentEstimateAmount(String cid);
	
	/**
	 * 
	 * @param cid 会社ID
	 * @param employmentCd 雇用コード
	 */
	void delete(String cid, EmploymentCode employmentCd);
}
