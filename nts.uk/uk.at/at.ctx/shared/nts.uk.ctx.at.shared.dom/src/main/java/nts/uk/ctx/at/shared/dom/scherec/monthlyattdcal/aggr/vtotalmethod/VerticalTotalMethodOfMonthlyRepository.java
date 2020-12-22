package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

import java.util.Optional;

/**
 * The Interface VerticalTotalMethodOfMonthlyRepository.
 *
 * @author HoangNDH
 */
public interface VerticalTotalMethodOfMonthlyRepository {
	
	/**
	 * Find by cid.
	 *
	 * @param companyId the company id
	 * @return the vertical total method of monthly
	 */
	public Optional<AggregateMethodOfMonthly> findByCid(String companyId);
	
	/**
	 * Insert.
	 *
	 * @param setting the setting
	 */
	public void insert(AggregateMethodOfMonthly setting);
	
	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	public void update(AggregateMethodOfMonthly setting);
}
