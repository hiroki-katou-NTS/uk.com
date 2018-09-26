package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author TanLV
 *
 */
@Value
public class GrantConditionCommand {
	/* 年休付与テーブル設定コード */
	private String yearHolidayCode;
	
	/* 条件NO */
	private int conditionNo;
	
	/* 条件値 */
	private Double conditionValue;
	
	/* 条件利用区分 */
	private int useConditionAtr;
	
	private List<GrantHolidayCommand> grantHolidayCommand;
}
