package nts.uk.ctx.sys.assist.app.find.resultofrestoration;



import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryLog;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResult;
import nts.uk.ctx.sys.assist.dom.storage.LoginInfo;


/**
 * Dto データ復旧の結果
 */
@Data
@AllArgsConstructor
public class ResultOfRestorationDto {
	/**
	 * データ復旧処理ID
	 */
	private String dataRecoveryProcessId;
	
	/**
	 * データ保存処理ID
	 */
	private String dataStorageProcessId;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * パターンコード
	 */
	private String patternCode;

	/**
	 * 実行者
	 */
	private String practitioner;

	/**
	 * 実行結果
	 */
	private String executionResult;

	//field 実行結果
	private List<DataRecoveryLog> listDataRecoveryLogs;
		
	/**
	 * 開始日時
	 */
	private GeneralDateTime startDateTime;

	/**
	 * 終了日時
	 */
	private GeneralDateTime endDateTime;

	/**
	 * 保存形態
	 */
	private int saveForm;

	/**
	 * 保存名称
	 */
	private String saveName;

	/**
	 * ログイン情報
	 */
    private LoginInfo loginInfo;
    
	public static ResultOfRestorationDto fromDomain(DataRecoveryResult domain) {
		return new ResultOfRestorationDto(	
			domain.getDataRecoveryProcessId(),
			domain.getDataStorageProcessId(),
			domain.getCid(),
			domain.getPatternCode(),
			domain.getPractitioner(),
			domain.getExecutionResult(),
			domain.getListDataRecoveryLogs(),
			domain.getStartDateTime(),
			domain.getEndDateTime().orElse(null),
			domain.getSaveForm().value,
			domain.getSaveName().v(),
			domain.getLoginInfo()
			);
	}
}
