package nts.uk.ctx.exio.infra.entity.exo.execlog;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLog;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 外部出力実行結果ログ
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIODT_EX_OUT_EXEC_LOG")
public class OiomtExterOutExecLog extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtExterOutExecLogPk exterOutExecLogPk;
	
	/**
	 * 処理開始日時
	 */
	@Basic(optional = false)
	@Column(name = "PRO_START_DATETIME")
	public GeneralDateTime processStartDatetime;

	/**
	 * 処理終了日時
	 */
	@Basic(optional = true)
	@Column(name = "PRO_END_DATETIME")
	public GeneralDateTime processEndDatetime;

	/**
	 * ユーザID
	 */
	@Basic(optional = true)
	@Column(name = "UID")
	public String uid;

	/**
	 * ロール種類
	 */
	@Basic(optional = true)
	@Column(name = "CTG_ID")
	public Integer categoryId;

	/**
	 * 実行者ID
	 */
	@Basic(optional = true)
	@Column(name = "EXEC_ID")
	public String execId;

	/**
	 * 処理単位
	 */
	@Basic(optional = true)
	@Column(name = "PRO_UNIT")
	public String processUnit;

	/**
	 * 定型区分
	 */
	@Basic(optional = false)
	@Column(name = "STD_ATR")
	public int stdClass;

	/**
	 * 条件設定コード
	 */
	@Basic(optional = false)
	@Column(name = "CND_SET_CD")
	public String codeSetCond;

	/**
	 * 設定名称
	 */
	@Basic(optional = false)
	@Column(name = "NAME_SET")
	public String nameSetting;

	/**
	 * 実行形態
	 */
	@Basic(optional = false)
	@Column(name = "EXEC_FORM")
	public int execForm;

	/**
	 * トータルカウント
	 */
	@Basic(optional = false)
	@Column(name = "TOTAL_CNT")
	public int totalCount;

	/**
	 * 結果状態
	 */
	@Basic(optional = true)
	@Column(name = "RESULT_STATUS")
	public Integer resultStatus;
	
	/**
	 * トータルエラーカウント
	 */
	@Basic(optional = false)
	@Column(name = "TOTAL_ERR_CNT")
	public int totalErrCount;

	/**
	 * 指定開始日付
	 */
	@Basic(optional = false)
	@Column(name = "SPECIFIED_START_DATE")
	public GeneralDate specifiedStartDate;

	/**
	 * 指定終了日
	 */
	@Basic(optional = false)
	@Column(name = "SPECIFIED_END_DATE")
	public GeneralDate specifiedEndDate;

	/**
	 * 指定基準日
	 */
	@Basic(optional = false)
	@Column(name = "DESIGNATED_REFER_DATE")
	public GeneralDate designatedReferDate;
	
	/**
	 * ファイルID
	 */
	@Basic(optional = true)
	@Column(name = "FILE_ID")
	public String fileId;

	/**
	 * ファイル名
	 */
	@Basic(optional = true)
	@Column(name = "FILE_NAME")
	public String fileName;

	/**
	 * ファイルサイズ
	 */
	@Basic(optional = true)
	@Column(name = "FILE_SIZE")
	public Long fileSize;

	/**
	 * ファイル削除
	 */
	@Basic(optional = false)
	@Column(name = "DEL_FILE")
	public int delFile;

	@Override
	protected Object getKey() {
		return exterOutExecLogPk;
	}

	public ExterOutExecLog toDomain() {
		return new ExterOutExecLog(this.exterOutExecLogPk.cid, this.exterOutExecLogPk.outProcessId, this.uid,
				this.totalErrCount, this.totalCount, this.fileId, this.fileSize, this.delFile, this.fileName,
				this.categoryId, this.processUnit, this.processEndDatetime, this.processStartDatetime, this.stdClass,
				this.execForm, this.execId, this.designatedReferDate, this.specifiedEndDate, this.specifiedStartDate,
				this.codeSetCond, this.resultStatus, this.nameSetting);
	}

	public static OiomtExterOutExecLog toEntity(ExterOutExecLog domain) {
		return new OiomtExterOutExecLog(new OiomtExterOutExecLogPk(domain.getCompanyId(), domain.getOutputProcessId()),
				domain.getProcessStartDateTime(), 
				domain.getProcessEndDateTime().orElse(null),
				domain.getUserId().orElse(null), 
				domain.getCategoryID().isPresent() ? domain.getCategoryID().get().v() : null,
				domain.getExecuteId(), 
				domain.getProcessUnit().orElse(null), 
				domain.getStandardClass().value, 
				domain.getCodeSettingCondition().v(),
				domain.getNameSetting().v(),
				domain.getExecuteForm().value,
				domain.getTotalCount(),
				domain.getResultStatus().isPresent() ? domain.getResultStatus().get().value : null,
				domain.getTotalErrorCount(), 
				domain.getSpecifiedStartDate(),
				domain.getSpecifiedEndDate(), 
				domain.getDesignatedReferenceDate(),
				domain.getFileId().orElse(null), 
				domain.getFileName().isPresent() ? domain.getFileName().get().v() : null,
				domain.getFileSize().orElse(null), 
				domain.getDeleteFile().value);
	}

}
