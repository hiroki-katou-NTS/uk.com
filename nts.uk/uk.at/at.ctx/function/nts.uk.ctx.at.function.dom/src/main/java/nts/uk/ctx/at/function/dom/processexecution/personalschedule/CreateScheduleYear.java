package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ENUM : 更新処理スケジュール作成指定年
 * @author tutk
 *
 */

@Getter
@NoArgsConstructor
public enum CreateScheduleYear {
	
	/* 本年 */
	THIS_YEAR(0,"Enum_CreateScheduleYear_THIS_YEAR"),
	
	/* 翌年 */
	FOLLOWING_YEAR(1,"Enum_CreateScheduleYear_FOLLOWING_YEAR");
	
	/** The value. */
	public  int value;
	
	public  String nameId;
	
	private CreateScheduleYear(int type,String nameId) {
		this.value = type;
		this.nameId = nameId;
	}
	

}
