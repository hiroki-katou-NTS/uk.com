package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;

/**
 * 月次集計エラー情報
 * @author shuichu_ishida
 */
@Getter
@Setter
@AllArgsConstructor
public class MonthlyAggregationErrorInfo {

	/** リソースID */
	private String resourceId;
	/** エラーメッセージ */
	private ErrMessageContent message;
}
