package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

import java.util.Optional;

public interface HandlingOfCriterionAmountRepository {
	/**
	 *
	 * @param cid 会社ID
	 * @param handling 目安金額の扱い
	 */
	void insert(String cid, HandlingOfCriterionAmount handling);

	/**
	 *
	 * @param cid 会社ID
	 * @param handling 目安金額の扱い
	 */
	void update(String cid, HandlingOfCriterionAmount handling);

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
	Optional<HandlingOfCriterionAmount> get(String cid);
}
