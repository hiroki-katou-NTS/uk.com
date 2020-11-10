package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting;

import java.util.Optional;

public interface TimesNumberCounterSelectionRepo {
	
	/**
	 * insert(回数集計選択)
	 * @param companyId
	 * @param domain
	 */
	public void insert(String companyId, TimesNumberCounterSelection domain);
	
	/**
	 * update(回数集計選択)
	 * @param companyId
	 * @param domain
	 */
	public void update(String companyId, TimesNumberCounterSelection domain);
	
	/**
	 * get
	 * @param companyId
	 * @param type 回数集計種類
	 * @return
	 */
	public Optional<TimesNumberCounterSelection> get(String companyId, TimesNumberCounterType type);
	
	/**
	 * exists
	 * @param companyId
	 * @return
	 */
	public boolean exists(String companyId, TimesNumberCounterType type);

}
