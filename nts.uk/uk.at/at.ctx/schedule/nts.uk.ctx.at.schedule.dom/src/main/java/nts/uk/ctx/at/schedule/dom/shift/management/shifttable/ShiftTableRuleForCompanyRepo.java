package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.Optional;

public interface ShiftTableRuleForCompanyRepo {
	
	/**
	 * insert
	 * @param domain　会社のシフト表のルール
	 */
	public void insert(String companyId, ShiftTableRuleForCompany domain);
	
	/**
	 * update
	 * @param domain　会社のシフト表のルール
	 */
	public void update(String companyId, ShiftTableRuleForCompany domain);
	
	/**
	 * delete
	 * @param companyId
	 */
	public void delete(String companyId);
	
	/**
	 * 
	 * @param companyId
	 * @return　会社のシフト表のルール
	 */
	public Optional<ShiftTableRuleForCompany> get(String companyId);

}
