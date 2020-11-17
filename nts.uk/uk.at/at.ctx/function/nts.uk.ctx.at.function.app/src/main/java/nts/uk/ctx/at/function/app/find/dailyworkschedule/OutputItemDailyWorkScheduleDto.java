/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import java.util.List;

import lombok.Data;

/**
 * The Class OutputItemDailyWorkScheduleDto.
 * @author HoangDD
 */
@Data
public class OutputItemDailyWorkScheduleDto {
	
	/** The layout id. */
	// 出力レイアウトID
	private String layoutId;

	/** The item code. */
	// コード
	private String itemCode;
	
	/** The item name. */
	// 名称
	private String itemName;
	
	/** The lst displayed attendance. */
	// 可能な選択項目
	private List<TimeitemTobeDisplayDto> lstDisplayedAttendance;
	
	/** The lst remark content. */
	// 表示する勤怠項目
	private List<PrintRemarksContentDto> lstRemarkContent;
	
	/** The zone name. */
	// 勤務種類・就業時間帯の名称
	private int workTypeNameDisplay;
	
	/** The remark input no. */
	// 備考入力No
	private int remarkInputNo;
	
	/** The font size. */
	// 文字の大きさ
	private int fontSize;
}
