package nts.uk.ctx.sys.shared.dom.toppagealarm;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author yennth
 *
 */
@Getter
public class TopPageAlarmDetail {
	/** 会社ID */
	private String companyId;
	/** 実行ログID */
	private String executionLogId;
	/** 管理社員ID */
	private String managerId;
	/** 実行完了日時 */
	private GeneralDateTime finishDateTime;
	/** 連番 */
	private SerialNo serialNo;
	/** エラーメッセージ */
	private ErrorMessage errorMessage ;
	/** 対象社員ID */
	private String targerEmployee ;
	public TopPageAlarmDetail(String companyId, String executionLogId, String managerId, GeneralDateTime finishDateTime,
			SerialNo serialNo, ErrorMessage errorMessage, String targerEmployee) {
		super();
		this.companyId = companyId;
		this.executionLogId = executionLogId;
		this.managerId = managerId;
		this.finishDateTime = finishDateTime;
		this.serialNo = serialNo;
		this.errorMessage = errorMessage;
		this.targerEmployee = targerEmployee;
	}
	
	public static TopPageAlarmDetail createFromJavaType(String companyId, String executionLogId, String managerId, GeneralDateTime finishDateTime,
			int serialNo, String errorMessage, String targerEmployee){
		return new TopPageAlarmDetail(companyId, executionLogId, managerId, finishDateTime, new SerialNo(serialNo), new ErrorMessage(errorMessage), targerEmployee);
	}
}
