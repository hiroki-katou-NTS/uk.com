package nts.uk.ctx.exio.app.find.exo.executionlog;

import lombok.Value;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLog;
@Value
public class ExterOutExecLogDto {
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 外部出力処理ID
	 */
	private String outputProcessId;

	/**
	 * ユーザID
	 */
	private String userId;

	/**
	 * トータルエラーカウント
	 */
	private int totalErrorCount;

	/**
	 * トータルカウント
	 */
	private int totalCount;

	/**
	 * ファイルID
	 */
	private String fileId;

	/**
	 * ファイルサイズ
	 */
	private Long fileSize;

	/**
	 * ファイル削除
	 */
	private int deleteFile;

	/**
	 * ファイル名
	 */
	private String fileName;

	/**
	 * ロール種類
	 */
	private Integer roleType;

	/**
	 * 処理単位
	 */
	private String processUnit;

	/**
	 * 処理終了日時
	 */
	private String processEndDateTime;

	/**
	 * 処理開始日時
	 */
	private String processStartDateTime;

	/**
	 * 定型区分
	 */
	private int standardClass;

	/**
	 * 実行形態
	 */
	private int executeForm;

	/**
	 * 実行者ID
	 */
	private String executeId;

	/**
	 * 指定基準日
	 */
	private String designatedReferenceDate;

	/**
	 * 指定終了日
	 */
	private String specifiedEndDate;

	/**
	 * 指定開始日付
	 */
	private String specifiedStartDate;

	/**
	 * 条件設定コード
	 */
	private String codeSettingCondition;

	/**
	 * 結果状態
	 */
	private Integer resultStatus;

	/**
	 * 設定名称
	 */
	private String nameSetting;

	public static ExterOutExecLogDto fromDomain(ExterOutExecLog domain) {
		return new ExterOutExecLogDto(domain.getCompanyId(), 
				domain.getOutputProcessId(), domain.getUserId().orElse(null), domain.getTotalErrorCount(),
				domain.getTotalCount(), domain.getFileId().orElse(null), domain.getFileSize().orElse(null),
				domain.getDeleteFile() != null ? domain.getDeleteFile().value : null,
				domain.getFileName().isPresent() ? domain.getFileName().get().v() : null,
				domain.getCategoryID().isPresent() ? domain.getCategoryID().get().v() : null,
				domain.getProcessUnit().orElse(null),
				domain.getProcessEndDateTime().isPresent() ? domain.getProcessEndDateTime().get().toString() : null,
				domain.getProcessStartDateTime() != null ? domain.getProcessStartDateTime().toString() : null,
				domain.getStandardClass() != null ? domain.getStandardClass().value : null,
				domain.getExecuteForm() != null ? domain.getExecuteForm().value : null,
				domain.getExecuteId() != null ? domain.getExecuteId().toString() : null,
				domain.getDesignatedReferenceDate() != null ? domain.getDesignatedReferenceDate().toString() : null,
				domain.getSpecifiedEndDate() != null ? domain.getSpecifiedEndDate().toString() : null,
				domain.getSpecifiedStartDate() != null ? domain.getSpecifiedStartDate().toString() : null,
				domain.getCodeSettingCondition() != null ? domain.getCodeSettingCondition().v() : null,
				domain.getResultStatus().isPresent() ? domain.getResultStatus().get().value : null,
				domain.getNameSetting() != null ? domain.getNameSetting().v() : null);
	}

}
