package nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem;

import java.util.Optional;


public interface ControlOfMonthlyItemsRepository {

	Optional<ControlOfMonthlyItems> getControlOfMonthlyItem(String companyID,int itemMonthlyID);
	
	void updateControlOfMonthlyItem(ControlOfMonthlyItems controlOfMonthlyItems);
	
	void addControlOfMonthlyItem(ControlOfMonthlyItems controlOfMonthlyItems);
}
