package nts.uk.ctx.exio.dom.exo.execlog;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exo.category.CategoryCd;
import nts.uk.ctx.exio.dom.exo.condset.ExternalOutputConditionCode;
import nts.uk.ctx.exio.dom.exo.condset.ExternalOutputConditionName;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 外部出力実行結果ログ
 */
@Getter
@Setter
@NoArgsConstructor
public class ExterOutExecLog extends AggregateRoot {

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
	private Optional<String> userId;

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
	private Optional<String> fileId;

	/**
	 * ファイルサイズ
	 */
	private Optional<Long> fileSize;

	/**
	 * ファイル削除
	 */
	private NotUseAtr deleteFile;

	/**
	 * ファイル名
	 */
	private Optional<UploadFileName> fileName;

	/**
	 * ロール種類
	 */
	private Optional<CategoryCd> categoryID;

	/**
	 * 処理単位
	 */
	private Optional<String> processUnit;

	/**
	 * 処理終了日時
	 */
	private Optional<GeneralDateTime> processEndDateTime;

	/**
	 * 処理開始日時
	 */
	private GeneralDateTime processStartDateTime;

	/**
	 * 定型区分
	 */
	private StandardClassification standardClass;

	/**
	 * 実行形態
	 */
	private ExecutionForm executeForm;

	/**
	 * 実行者ID
	 */
	private String executeId;

	/**
	 * 指定基準日
	 */
	private GeneralDate designatedReferenceDate;

	/**
	 * 指定終了日
	 */
	private GeneralDate specifiedEndDate;

	/**
	 * 指定開始日付
	 */
	private GeneralDate specifiedStartDate;

	/**
	 * 条件設定コード
	 */
	private ExternalOutputConditionCode codeSettingCondition;

	/**
	 * 結果状態
	 */
	private Optional<ResultStatus> resultStatus;

	/**
	 * 設定名称
	 */
	private ExternalOutputConditionName nameSetting;

	public ExterOutExecLog(String companyId, String outputProcessId, String userId, int totalErrorCount, int totalCount,
			String fileId, Long fileSize, int deleteFile, String fileName, Integer categoryID, String processUnit,
			GeneralDateTime processEndDateTime, GeneralDateTime processStartDateTime, int standardClass,
			int executeForm, String executeId, GeneralDate designatedReferenceDate, GeneralDate specifiedEndDate,
			GeneralDate specifiedStartDate, String codeSettingCondition, Integer resultStatus, String nameSetting) {
		super();
		this.companyId = companyId;
		this.outputProcessId = outputProcessId;
		this.userId = Optional.ofNullable(userId);
		this.totalErrorCount = totalErrorCount;
		this.totalCount = totalCount;
		this.fileId = Optional.ofNullable(fileId);
		this.fileSize = Optional.ofNullable(fileSize);
		this.deleteFile = EnumAdaptor.valueOf(deleteFile, NotUseAtr.class);
		this.fileName = fileName == null ? Optional.empty() : Optional.ofNullable(new UploadFileName(fileName));
		this.categoryID = categoryID == null ? Optional.empty()
				: Optional.of(new CategoryCd(categoryID));
		this.processUnit = Optional.ofNullable(processUnit);
		this.processEndDateTime = Optional.ofNullable(processEndDateTime);
		this.processStartDateTime = processStartDateTime;
		this.standardClass = EnumAdaptor.valueOf(standardClass, StandardClassification.class);
		this.executeForm = EnumAdaptor.valueOf(executeForm, ExecutionForm.class);
		this.executeId = executeId;
		this.designatedReferenceDate = designatedReferenceDate;
		this.specifiedEndDate = specifiedEndDate;
		this.specifiedStartDate = specifiedStartDate;
		this.codeSettingCondition = new ExternalOutputConditionCode(codeSettingCondition);
		this.resultStatus = resultStatus == null ? Optional.empty()
				: Optional.of(EnumAdaptor.valueOf(resultStatus, ResultStatus.class));
		this.nameSetting = new ExternalOutputConditionName(nameSetting);
	}

}
