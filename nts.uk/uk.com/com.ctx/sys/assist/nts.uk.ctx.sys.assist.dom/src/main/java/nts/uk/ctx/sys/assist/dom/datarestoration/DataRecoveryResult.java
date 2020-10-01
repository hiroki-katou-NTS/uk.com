package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.storage.LoginInfo;
//import nts.uk.ctx.sys.assist.dom.storage.PatternCode;
import nts.uk.ctx.sys.assist.dom.storage.SaveName;
import nts.uk.ctx.sys.assist.dom.storage.StorageForm;

/**
 * データ復旧の結果
 */
@Getter
public class DataRecoveryResult extends AggregateRoot {

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

	/**
	 * 実行結果
	 */
	private List<DataRecoveryLog> listDataRecoveryLogs;
	
	/**
	 * 開始日時
	 */
	private GeneralDateTime startDateTime;

	/**
	 * 終了日時
	 */
	private Optional<GeneralDateTime> endDateTime;

	/**
	 * 保存形態
	 */
	private StorageForm saveForm;

	/**
	 * 保存名称
	 */
	private SaveName saveName;

	/**
	 * ログイン情報
	 */
    private LoginInfo loginInfo;	

    
	public DataRecoveryResult(
			String dataRecoveryProcessId, 
			String cid, 
			String patternCode,
			String practitioner, 
			String executionResult,
			List<DataRecoveryLog> listDataRecoveryLogs,
			GeneralDateTime startDateTime, 
			GeneralDateTime endDateTime, 
			Integer saveForm, 
			String saveName, 
			String ipAddress, 
			String pcName, 
			String account) {
		this.dataRecoveryProcessId = dataRecoveryProcessId;
		this.cid                   = cid;
		this.patternCode           = patternCode;
		this.practitioner          = practitioner;
		this.executionResult       = executionResult;
		this.listDataRecoveryLogs  = listDataRecoveryLogs;
		this.startDateTime         = startDateTime;
		this.endDateTime           = Optional.ofNullable(endDateTime);
		this.saveForm              = EnumAdaptor.valueOf(saveForm, StorageForm.class);
		this.saveName              = new SaveName(saveName);
		this.loginInfo			   = new LoginInfo(ipAddress,pcName,account);
	}
}