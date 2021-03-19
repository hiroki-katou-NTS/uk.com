package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

public interface CriterionAmountForEmploymentRepository {
	/**
	 *
	 * @param cid 会社ID
	 * @param criterion 雇用の目安金額
	 */
	void insert(String cid, CriterionAmountForEmployment criterion);

	/**
	 *
	 * @param cid 会社ID
	 * @param criterion 雇用の目安金額
	 */
	void update(String cid, CriterionAmountForEmployment criterion);

	/**
	 *
	 * @param cid 会社ID
	 * @param employmentCd 雇用コード
	 */
	void delete(String cid, EmploymentCode employmentCd);


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
	Optional<CriterionAmountForEmployment> get(String cid, EmploymentCode employmentCd);

	/**
	 *
	 * @param cid 会社ID
	 * @return
	 */
	List<CriterionAmountForEmployment> getAll(String cid);

}
