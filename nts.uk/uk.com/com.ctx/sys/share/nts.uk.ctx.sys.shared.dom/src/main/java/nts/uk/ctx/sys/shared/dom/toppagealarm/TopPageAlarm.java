package nts.uk.ctx.sys.shared.dom.toppagealarm;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.shared.dom.toppagealarmset.AlarmCategory;
/**
 * トップページアラーム
 * @author yennth
 *
 */
@Getter
public class TopPageAlarm {
	/** 会社ID */
	private String companyId;
	/** 実行ログID */
	private String executionLogId;
	/** 管理社員ID */
	private String managerId;
	/** 実行完了日時 */
	private GeneralDateTime finishDateTime;
	/** 実行内容 */
	private AlarmCategory executionContent;
	/** エラーの有無 */
	private ExistenceError existenceError;
	/** 了解フラグ */
	private RogerFlag rogerFlag;
	
	public TopPageAlarm(String companyId, String executionLogId, String managerId, GeneralDateTime finishDateTime,
			AlarmCategory executionContent, ExistenceError existenceError, RogerFlag rogerFlag) {
		super();
		this.companyId = companyId;
		this.executionLogId = executionLogId;
		this.managerId = managerId;
		this.finishDateTime = finishDateTime;
		this.executionContent = executionContent;
		this.existenceError = existenceError;
		this.rogerFlag = rogerFlag;
	}
	
	public static TopPageAlarm createFromJavaType(String companyId, String executionLogId, String managerId, GeneralDateTime finishDateTime,
			int executionContent, int existenceError, int rogerFlag){
		return new TopPageAlarm(companyId, executionLogId, managerId, finishDateTime,
									EnumAdaptor.valueOf(executionContent, AlarmCategory.class), 
									EnumAdaptor.valueOf(existenceError, ExistenceError.class),
									EnumAdaptor.valueOf(rogerFlag, RogerFlag.class));
	}
}
