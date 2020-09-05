package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InformationItemDto {
	// 勤怠項目ID
	private int attendanceItemId;
	// 名称
	private String name;
	// 勤怠項目属性
	private int attendanceItemAtt;
	// 勤怠項目に関連するマスタの種類
	private int masterType;
	// 画面上の属性
	private int attOnScreen;
	// 並び順(表示番号)
	private int orderNo;
}
