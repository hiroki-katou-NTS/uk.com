package nts.uk.ctx.at.function.ac.widgetKtg;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.OptionalWidgetAdapter;
import nts.uk.ctx.at.request.pub.application.recognition.HolidayInstructPub;
import nts.uk.ctx.at.request.pub.application.recognition.OverTimeInstructPub;
@Stateless
public class OptionalWidgetImplementFinder implements OptionalWidgetAdapter {

	@Inject
	OverTimeInstructPub overTimeInstructPub;
	
	@Inject 
	HolidayInstructPub holidayInstructPub;
	
	
	@Override
	public int getNumberOT(String employeeId, GeneralDate startDate, GeneralDate endDate) {
		return overTimeInstructPub.acquireOverTimeWorkInstruction(employeeId, startDate, endDate).size();
	}

	@Override
	public int getNumberBreakIndication(String employeeId, GeneralDate startDate, GeneralDate endDate) {
		return holidayInstructPub.acquireBreakIndication(employeeId, startDate, endDate).size();
	}



}
