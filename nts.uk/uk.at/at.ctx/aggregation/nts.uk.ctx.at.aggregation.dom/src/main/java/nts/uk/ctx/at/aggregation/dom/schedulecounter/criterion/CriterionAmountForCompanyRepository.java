package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

import java.util.Optional;

public interface CriterionAmountForCompanyRepository {

	/**
	 *
	 * @param cid 会社ID
	 * @param criterion 会社の目安金額
	 */
	void insert(String cid, CriterionAmountForCompany criterion);

	/**
	 *
	 * @param cid 会社ID
	 * @param criterion 会社の目安金額
	 */
	void update(String cid, CriterionAmountForCompany criterion);


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
	Optional<CriterionAmountForCompany> get(String cid);

}
