package nts.uk.ctx.exio.dom.exo.exechist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exo.adapter.bs.employee.PersonInfoImport;
import nts.uk.ctx.exio.dom.exo.condset.ExternalOutputConditionName;
import nts.uk.ctx.exio.dom.exo.execlog.ExecutionForm;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLog;
import nts.uk.ctx.exio.dom.exo.execlog.ResultStatus;
import nts.uk.ctx.exio.dom.exo.execlog.StandardClassification;
import nts.uk.ctx.exio.dom.exo.execlog.UploadFileName;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 実行履歴一覧
 */
@Value
public class ExecHist {
	/**
	 * 外部出力処理ID
	 */
	private String outputProcessId;

	/**
	 * ファイル削除
	 */
	private NotUseAtr deleteFile;

	/**
	 * ファイルID
	 */
	private Optional<String> fileId;

	/**
	 * 処理開始日時
	 */
	private GeneralDateTime processStartDateTime;

	/**
	 * 社員名
	 */
	private String sName;

	/**
	 * 設定名称
	 */
	private ExternalOutputConditionName nameSetting;

	/**
	 * 定型区分
	 */
	private StandardClassification standardClass;

	/**
	 * 実行形態
	 */
	private ExecutionForm executeForm;

	/**
	 * トータルカウント
	 */
	private int totalCount;

	/**
	 * 処理単位
	 */
	private Optional<String> processUnit;

	/**
	 * 結果状態
	 */
	private Optional<ResultStatus> resultStatus;

	/**
	 * トータルエラーカウント
	 */
	private int totalErrorCount;

	/**
	 * ファイル名
	 */
	private Optional<UploadFileName> fileName;

	/**
	 * ファイルサイズ
	 */
	private Optional<Long> fileSize;

	public ExecHist(String outputProcessId, NotUseAtr deleteFile, Optional<String> fileId,
			GeneralDateTime processStartDateTime, String sName, ExternalOutputConditionName nameSetting,
			StandardClassification standardClass, ExecutionForm executeForm, int totalCount,
			Optional<String> processUnit, Optional<ResultStatus> resultStatus, int totalErrorCount,
			Optional<UploadFileName> fileName, Optional<Long> fileSize) {
		super();
		this.outputProcessId = outputProcessId;
		this.deleteFile = deleteFile;
		this.fileId = fileId;
		this.processStartDateTime = processStartDateTime;
		this.sName = sName;
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

	public static List<ExecHist> fromExterOutExecLogAndPersonInfo(List<ExterOutExecLog> exterOutExecLogList,
			List<PersonInfoImport> personInfoList) {
		List<ExecHist> histList = new ArrayList<>();
		for (ExterOutExecLog log : exterOutExecLogList) {
			String sName = "";
			Optional<PersonInfoImport> personOtp = personInfoList.stream()
					.filter(x -> x.getEmployeeId().equals(log.getExecuteId())).findFirst();
			if (personOtp.isPresent()) {
				sName = personOtp.get().getBusinessName();
			}
			ExecHist hist = new ExecHist(log.getOutputProcessId(), log.getDeleteFile(), log.getFileId(),
					log.getProcessStartDateTime(), sName, log.getNameSetting(), log.getStandardClass(),
					log.getExecuteForm(), log.getTotalCount(), log.getProcessUnit(), log.getResultStatus(),
					log.getTotalErrorCount(), log.getFileName(), log.getFileSize());
			histList.add(hist);
		}
		return histList;
	}
}
