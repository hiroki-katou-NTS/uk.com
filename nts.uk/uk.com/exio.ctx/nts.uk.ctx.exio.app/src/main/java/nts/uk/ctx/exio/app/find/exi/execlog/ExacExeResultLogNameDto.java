package nts.uk.ctx.exio.app.find.exi.execlog;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLog;
@AllArgsConstructor
@Value
public class ExacExeResultLogNameDto {

	/**
	 * 会社ID
	 */
	private String cid;
	
	private String companyCd;

	/**
	 * 条件設定コード
	 */
	private String conditionSetCd;
	
	/**
	 * 
	 */
	private String conditionSetName;

	/**
	 * 外部受入処理ID
	 */
	private String externalProcessId;

	/**
	 * 実行者ID
	 */
	private String executorId;

	/**
	 * ユーザID
	 */
	private String userId;
	
	/**
	 * ユーザ名
	 */
	private String userName;
	
	private String userCode;

	/**
	 * 処理開始日時
	 */
	private GeneralDateTime processStartDatetime;

	/**
	 * 定型区分
	 */
	private int standardAtr;

	/**
	 * 実行形態
	 */
	private int executeForm;

	/**
	 * 対象件数
	 */
	private int targetCount;

	/**
	 * エラー件数
	 */
	private int errorCount;

	/**
	 * ファイル名
	 */
	private String fileName;

	/**
	 * システム種類
	 */
	private int systemType;

	/**
	 * 結果状態
	 */
	private Integer resultStatus;

	/**
	 * 処理終了日時
	 */
	private GeneralDateTime processEndDatetime;

	/**
	 * 処理区分
	 */
	private int processAtr;
	
	public static ExacExeResultLogNameDto fromDomain(ExacExeResultLog domain, String conditionSetName, String userCd,
			String userName, String companyCd) {
		return new ExacExeResultLogNameDto(domain.getCid(), companyCd,
				domain.getConditionSetCd(),
				conditionSetName,
				domain.getExternalProcessId(),
				domain.getExecutorId(),
				domain.getUserId(),
				userName,
				userCd,
				domain.getProcessStartDatetime(),
				domain.getStandardAtr().value,
				domain.getExecuteForm().value,
				domain.getTargetCount(),
				domain.getErrorCount(),
				domain.getFileName(),
				domain.getSystemType().value,
				domain.getResultStatus().isPresent() ? domain.getResultStatus().get().value : 0,
				domain.getProcessEndDatetime().isPresent() ? domain.getProcessEndDatetime().get() : null,
				domain.getProcessAtr().value);
	}
}
