package nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodinfor.ErrorMess;
/**
 * 任意期間集計エラーメッセージ情報
 * @author phongtq
 *
 */
@Getter
public class AggrPeriodInfor extends AggregateRoot{

	/** 社員ID*/
	private String memberId;
	
	/** 任意期間集計実行ログID*/
	private String periodArrgLogId;
	
	/** リソースID*/
	private String resourceId;
	
	/** 処理日*/
	private GeneralDate processDay;
	
	/** エラーメッセージ*/
	private ErrorMess errorMess;

	public AggrPeriodInfor(String memberId, String periodArrgLogId, String resourceId, GeneralDate processDay,
			ErrorMess errorMess) {
		super();
		this.memberId = memberId;
		this.periodArrgLogId = periodArrgLogId;
		this.resourceId = resourceId;
		this.processDay = processDay;
		this.errorMess = errorMess;
	}
	public static AggrPeriodInfor createFromJavaType(String memberId, String periodArrgLogId, String resourceId, GeneralDate processDay,
			String errorMess){
		return new AggrPeriodInfor(memberId, periodArrgLogId, resourceId, processDay, new ErrorMess(errorMess));
	}
	
	
}
