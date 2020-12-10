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
import nts.uk.ctx.at.function.dom.dailyworkschedule.FontSizeEnum;
import nts.uk.ctx.at.function.dom.dailyworkschedule.NameWorkTypeOrHourZone;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;

/**
 * The Class OutputItemDailyWorkScheduleCommand.
 * @author HoangDD
 */
@Setter
@NoArgsConstructor
public class OutputItemDailyWorkScheduleCommand implements OutputItemDailyWorkScheduleGetMemento {
	
	private String layoutId;

	/** The item code. */
	private String itemCode;
	
	/** The item name. */
	private String itemName;
	
	/** The lst displayed attendance. */
	private List<TimeItemTobeDisplayCommand> lstDisplayedAttendance;
	
	/** The lst remark content. */
	private List<PrintRemarksContentCommand> lstRemarkContent;
	
	/** The zone name. */
	private int workTypeNameDisplay;
	
	/** The remark input no. */
	private int remarkInputNo;

	// 文字の大きさ
	private int fontSize;
	
	// This variable used to know is new mode when save.
	@Getter
	private boolean newMode; 
	
	@Getter
	private int selectionType; 
	
	@Getter
	private String employeeId;
	

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
					return new AttendanceItemsDisplay(command.getSortBy(), command.getItemToDisplay());
				}).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getLstRemarkContent()
	 */
	@Override
	public List<PrintRemarksContent> getLstRemarkContent() {
		return lstRemarkContent.stream()
				.map(command -> {
					return new PrintRemarksContent(command.getUsedClassification(), command.getPrintItem());
				}).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getWorkTypeNameDisplay()
	 */
	@Override
	public NameWorkTypeOrHourZone getWorkTypeNameDisplay() {
		return NameWorkTypeOrHourZone.valueOf(this.workTypeNameDisplay);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getRemarkInputNo()
	 */
	@Override
	public RemarkInputContent getRemarkInputNo() {
		return RemarkInputContent.valueOf(this.remarkInputNo);
	}

	@Override
	public FontSizeEnum getFontSize() {
		return FontSizeEnum.valueOf(this.fontSize);
	}

	@Override
	public String getLayoutId() {
		return this.layoutId;
	}
}
