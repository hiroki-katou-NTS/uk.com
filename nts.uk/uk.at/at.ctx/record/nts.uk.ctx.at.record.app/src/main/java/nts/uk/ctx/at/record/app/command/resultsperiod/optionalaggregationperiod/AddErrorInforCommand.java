package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;

@AllArgsConstructor
@Data
public class AddErrorInforCommand {
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
	
	public AggrPeriodInfor toDomain(String memberId, String periodArrgLogId) {
		return AggrPeriodInfor.createFromJavaType(memberId, periodArrgLogId, this.resourceId, this.processDay,
				this.errorMess);

	}
}
