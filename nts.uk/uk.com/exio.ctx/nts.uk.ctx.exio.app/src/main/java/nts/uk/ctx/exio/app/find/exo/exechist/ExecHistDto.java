package nts.uk.ctx.exio.app.find.exo.exechist;

import lombok.Value;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exo.exechist.ExecHist;

@Value
public class ExecHistDto {
	/**
	 * 外部出力処理ID
	 */
	private String outputProcessId;

	/**
	 * ファイル削除
	 */
	private int deleteFile;

	/**
	 * ファイルID
	 */
	private String fileId;

	/**
	 * 処理開始日時
	 */
	private GeneralDateTime processStartDateTime;

	/**
	 * 社員名
	 */
	private String empName;

	/**
	 * 設定名称
	 */
	private String nameSetting;

	/**
	 * 定型区分
	 */
	private int standardClass;

	/**
	 * 実行形態
	 */
	private int executeForm;

	/**
	 * トータルカウント
	 */
	private int totalCount;

	/**
	 * 処理単位
	 */
	private String processUnit;

	/**
	 * 結果状態
	 */
	private Integer resultStatus;

	/**
	 * トータルエラーカウント
	 */
	private int totalErrorCount;

	/**
	 * ファイル名
	 */
	private String fileName;

	/**
	 * ファイルサイズ
	 */
	private Long fileSize;

	public static ExecHistDto fromDomain(ExecHist domain) {
		return new ExecHistDto(domain.getOutputProcessId(), domain.getDeleteFile().value,
				domain.getFileId().isPresent() ? domain.getFileId().get() : "", domain.getProcessStartDateTime(),
				domain.getSName(), domain.getNameSetting().v(), domain.getStandardClass().value,
				domain.getExecuteForm().value, domain.getTotalCount(),
				domain.getProcessUnit().isPresent() ? domain.getProcessUnit().get() : "",
				domain.getResultStatus().isPresent() ? domain.getResultStatus().get().value : null,
				domain.getTotalErrorCount(), domain.getFileName().isPresent() ? domain.getFileName().get().v() : "",
				domain.getFileSize().isPresent() ? domain.getFileSize().get() : null);
	}

	public ExecHistDto(String outputProcessId, int deleteFile, String fileId, GeneralDateTime processStartDateTime,
			String empName, String nameSetting, int standardClass, int executeForm, int totalCount, String processUnit,
			Integer resultStatus, int totalErrorCount, String fileName, Long fileSize) {
		super();
		this.outputProcessId = outputProcessId;
		this.deleteFile = deleteFile;
		this.fileId = fileId;
		this.processStartDateTime = processStartDateTime;
		this.empName = empName;
		this.nameSetting = nameSetting;
		this.standardClass = standardClass;
		this.executeForm = executeForm;
		this.totalCount = totalCount;
		this.processUnit = processUnit;
		this.resultStatus = resultStatus;
		this.totalErrorCount = totalErrorCount;
		this.fileName = fileName;
		this.fileSize = fileSize;
	}

}
