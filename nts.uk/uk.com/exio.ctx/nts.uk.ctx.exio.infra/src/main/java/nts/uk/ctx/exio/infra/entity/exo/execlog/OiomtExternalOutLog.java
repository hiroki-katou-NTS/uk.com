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
import nts.uk.ctx.exio.dom.exo.execlog.ExternalOutLog;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 外部出力結果ログ
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EXTERNAL_OUT_LOG")
public class OiomtExternalOutLog extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtExternalOutLogPk externalOutLogPk;

	/**
	 * エラー内容
	 */
	@Basic(optional = true)
	@Column(name = "ERR_CONTENT")
	public String errContent;

	/**
	 * エラー対象値
	 */
	@Basic(optional = true)
	@Column(name = "ERR_TARGET_VAL")
	public String errTargetVal;

	/**
	 * エラー日付
	 */
	@Basic(optional = true)
	@Column(name = "ERR_DATE")
	public GeneralDate errDate;

	/**
	 * エラー社員
	 */
	@Basic(optional = true)
	@Column(name = "ERR_EMP")
	public String errEmp;

	/**
	 * エラー項目
	 */
	@Basic(optional = true)
	@Column(name = "ERR_ITEM")
	public String errItem;

	/**
	 * ログ登録日時
	 */
	@Basic(optional = false)
	@Column(name = "LOG_REGISTER_DATE")
	public GeneralDateTime logRegisterDate;

	/**
	 * 処理カウント
	 */
	@Basic(optional = false)
	@Column(name = "PROCESS_COUNT")
	public int processCount;

	/**
	 * 処理内容
	 */
	@Basic(optional = false)
	@Column(name = "PROCESS_CONTENT")
	public int processContent;

	@Override
	protected Object getKey() {
		return externalOutLogPk;
	}

	public ExternalOutLog toDomain() {
		return new ExternalOutLog(this.externalOutLogPk.cid, this.externalOutLogPk.outProcessId, this.errContent,
				this.errTargetVal, this.errDate, this.errEmp, this.errItem, this.logRegisterDate, this.externalOutLogPk.logSequenceNum,
				this.processCount, this.processContent);
	}

	public static OiomtExternalOutLog toEntity(ExternalOutLog domain) {
		return new OiomtExternalOutLog(new OiomtExternalOutLogPk(domain.getCompanyId(), domain.getOutputProcessId(), domain.getLogSequenceNumber()),
				domain.getErrorContent().orElse(null), domain.getErrorTargetValue().orElse(null),
				domain.getErrorDate().orElse(null), domain.getErrorEmployee().orElse(null),
				domain.getErrorItem().orElse(null), domain.getLogRegisterDateTime(), domain.getProcessCount(), domain.getProcessContent().value);
	}

}