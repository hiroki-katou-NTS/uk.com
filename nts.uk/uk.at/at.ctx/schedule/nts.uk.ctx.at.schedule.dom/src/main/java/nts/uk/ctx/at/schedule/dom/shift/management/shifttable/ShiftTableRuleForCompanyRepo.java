package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.Optional;

public interface ShiftTableRuleForCompanyRepo {
	
	public void insert(ShiftTableRuleForCompany domain);
	
	public void update(ShiftTableRuleForCompany domain);
	
	public void delete(String companyId);
	
	public Optional<ShiftTableRuleForCompany> getShiftTableRuleForCompany(String companyId);

}
