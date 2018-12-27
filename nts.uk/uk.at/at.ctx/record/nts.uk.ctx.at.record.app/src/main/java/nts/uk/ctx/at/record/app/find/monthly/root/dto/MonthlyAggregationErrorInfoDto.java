package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;

/** 月次集計エラー情報 */
@Data
@NoArgsConstructor
@AllArgsConstructor 
public class MonthlyAggregationErrorInfoDto implements ItemConst {
	
	/** リソースID */
	private String resourceId;
	
	/** エラーメッセージ */
	private String message;
}
