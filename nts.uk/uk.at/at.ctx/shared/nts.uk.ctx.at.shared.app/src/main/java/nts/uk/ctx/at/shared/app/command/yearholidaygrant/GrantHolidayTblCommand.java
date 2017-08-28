package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author TanLV
 *
 */
@Value
public class GrantHolidayTblCommand {
		
	/* 条件NO */
	private int conditionNo;
	
	/* 年休付与テーブル設定コード */
	private String yearHolidayCode;
	
	private List<GrantHolidayTbl> grantHolidayList; 
}
