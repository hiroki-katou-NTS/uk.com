package nts.uk.ctx.at.record.dom.divergence.time.message;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;

/**
 * The Interface WorkTypeDivergenceTimeErrorAlarmMessageRepository.
 */
public interface WorkTypeDivergenceTimeErrorAlarmMessageRepository {

	/**
	 * Gets the by divergence time no.
	 *
	 * @param divergenceTimeNo the divergence time no
	 * @param cId the c id
	 * @param workTypeCode the work type code
	 * @return the by divergence time no
	 */
	public Optional<WorkTypeDivergenceTimeErrorAlarmMessage> getByDivergenceTimeNo(Integer divergenceTimeNo, CompanyId cId,
			BusinessTypeCode workTypeCode);

	/**
	 * Gets the by divergence time no list.
	 *
	 * @param divergenceTimeNo the divergence time no
	 * @param cId the c id
	 * @param workTypeCode the work type code
	 * @return the by divergence time no list
	 */
	public List<WorkTypeDivergenceTimeErrorAlarmMessage> getByDivergenceTimeNoList (List<Integer> divergenceTimeNo, CompanyId cId,
			BusinessTypeCode workTypeCode);

	/**
	 * Adds the.
	 *
	 * @param message
	 *            the message
	 */
	public void add(WorkTypeDivergenceTimeErrorAlarmMessage message);

	/**
	 * Update.
	 *
	 * @param message
	 *            the message
	 */
	public void update(WorkTypeDivergenceTimeErrorAlarmMessage message);
}
