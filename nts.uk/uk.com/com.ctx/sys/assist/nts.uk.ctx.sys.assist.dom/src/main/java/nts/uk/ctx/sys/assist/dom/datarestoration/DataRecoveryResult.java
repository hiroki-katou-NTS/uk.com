package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;

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
	 * 会社ID
	 */
	private String cid;

	/**
	 * 保存セットコード
	 */
	private Optional<String> saveSetCode;

	/**
	 * 実行者
	 */
	private String practitioner;

	/**
	 * 実行結果
	 */
	private Optional<String> executionResult;

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
	private Integer saveForm;

	/**
	 * 保存名称
	 */
	private String saveName;

	public DataRecoveryResult(String dataRecoveryProcessId, String cid, String saveSetCode,
			String practitioner, String executionResult, GeneralDateTime startDateTime,
			GeneralDateTime endDateTime, Integer saveForm, String saveName) {
		this.dataRecoveryProcessId = dataRecoveryProcessId;
		this.cid                   = cid;
		this.saveSetCode           = Optional.ofNullable(saveSetCode);
		this.practitioner          = practitioner;
		this.executionResult       = Optional.ofNullable(executionResult);
		this.startDateTime         = startDateTime;
		this.endDateTime           = Optional.ofNullable(endDateTime);
		this.saveForm              = saveForm;
		this.saveName              = saveName;
	}
}
