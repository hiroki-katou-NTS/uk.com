package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodinfor.ErrorMess;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PeriodInforDto {
	/** 社員ID*/
	private String memberId;
	
	/** 任意期間集計実行ログID*/
	private String periodArrgLogId;
	
	/** リソースID*/
	private String resourceId;
	
	/** 処理日*/
	private GeneralDate processDay;
	
	/** エラーメッセージ*/
	private String errorMess;
}
