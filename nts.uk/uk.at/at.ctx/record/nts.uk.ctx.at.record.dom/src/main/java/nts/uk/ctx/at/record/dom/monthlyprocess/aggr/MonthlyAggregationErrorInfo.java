package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;

/**
 * 月次集計エラー情報
 * @author shuichi_ishida
 */
@Getter
@Setter
@AllArgsConstructor
public class MonthlyAggregationErrorInfo implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** リソースID */
	private String resourceId;
	/** エラーメッセージ */
	private ErrMessageContent message;
}
