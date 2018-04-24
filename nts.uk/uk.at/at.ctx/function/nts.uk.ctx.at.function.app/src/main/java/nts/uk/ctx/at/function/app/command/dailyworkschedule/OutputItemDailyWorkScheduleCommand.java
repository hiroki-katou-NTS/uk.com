/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.command.dailyworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.dailyworkschedule.NameWorkTypeOrHourZone;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;

/**
 * The Class OutputItemDailyWorkScheduleCommand.
 */
@Setter
@NoArgsConstructor
public class OutputItemDailyWorkScheduleCommand implements OutputItemDailyWorkScheduleGetMemento{

	/** The item code. */
	private int itemCode;
	
	/** The item name. */
	private String itemName;
	
	/** The lst displayed attendance. */
	private List<TimeItemTobeDisplayCommand> lstDisplayedAttendance;
	
	/** The lst remark content. */
	private List<PrintRemarksContentCommand> lstRemarkContent;
	
	/** The zone name. */
	private int zoneName;
	
	// This variable used to know is new mode when save.
	@Getter
	private boolean isNewMode;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getItemCode()
	 */
	@Override
	public OutputItemSettingCode getItemCode() {
		return new OutputItemSettingCode(this.itemCode);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getItemName()
	 */
	@Override
	public OutputItemSettingName getItemName() {
		return new OutputItemSettingName(this.itemName);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getLstDisplayedAttendance()
	 */
	@Override
	public List<AttendanceItemsDisplay> getLstDisplayedAttendance() {
		return lstDisplayedAttendance.stream()
										.map(command -> {
											AttendanceItemsDisplay domainObject = new AttendanceItemsDisplay(command.getSortBy(), 
																										command.getItemToDisplay());
											return domainObject;
										}).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getLstRemarkContent()
	 */
	@Override
	public List<PrintRemarksContent> getLstRemarkContent() {
		return lstRemarkContent.stream()
									.map(command -> {
										PrintRemarksContent domainObject = new PrintRemarksContent(command.getUsedClassification(), 
																									command.getPrintitem());
										return domainObject;
									}).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getZoneName()
	 */
	@Override
	public NameWorkTypeOrHourZone getZoneName() {
		return NameWorkTypeOrHourZone.valueOf(this.zoneName);
	}

	@Override
	public String getCompanyID() {
		// TODO Auto-generated method stub
		return null;
	}
}
