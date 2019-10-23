package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;

/**
 * データ復旧の結果ログ
 */
@Getter
public class DataRecoveryLog extends DomainObject {

	/**
	 * データ復旧処理ID
	 */
	private String recoveryProcessId;

	/**
	 * 対象者
	 */
	private String target;

	/**
	 * エラー内容
	 */
	private RecoveryErrDescript errorContent;

	/**
	 * 対象日
	 */
	private GeneralDate targetDate;

	public DataRecoveryLog(String recoveryProcessId, String target, String errorContent, GeneralDate targetDate) {
		this.recoveryProcessId = recoveryProcessId;
		this.target            = target;
		this.errorContent      = new RecoveryErrDescript(errorContent);
		this.targetDate        = targetDate;
	}
}
