package nts.uk.ctx.at.function.app.find.alarm;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionName;

@Data
@AllArgsConstructor
public class AlarmCheckConditonCodeDto {
	// カテゴリ
	private int category;

	// コード
	private String checkConditonCode;

	// 名称
	private String checkConditionName;
	
	// アラームリストの結果が閲覧できるロール一覧
	private List<String> listRoleId = new ArrayList<>();
}
