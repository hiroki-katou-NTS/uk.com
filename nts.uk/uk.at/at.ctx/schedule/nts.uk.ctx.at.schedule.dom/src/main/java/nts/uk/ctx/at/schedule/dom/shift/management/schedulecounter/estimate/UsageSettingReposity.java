package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import java.util.Optional;

public interface UsageSettingReposity {

	/**
	 * 
	 * @param cid 会社ID
	 * @param usageSetting 目安利用区分
	 */
	void insert(String cid, UsageSetting usageSetting);
	
	/**
	 * 
	 * @param cid 会社ID
	 * @param usageSetting 目安利用区分
	 */
	void update(String cid, UsageSetting usageSetting);
	
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
	Optional<UsageSetting> get(String cid);
	
	
}
