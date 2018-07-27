package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;


public interface ControlOfAttendanceItemsRepository {

	Optional<ControlOfAttendanceItems> getControlOfAttendanceItem(String companyID,int itemDailyID);
	
	void updateControlOfAttendanceItem(ControlOfAttendanceItems	controlOfAttendanceItems);	
	
	void insertControlOfAttendanceItem(ControlOfAttendanceItems	controlOfAttendanceItems);
}
