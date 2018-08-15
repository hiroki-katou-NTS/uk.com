package nts.uk.ctx.sys.assist.infra.entity.datarestoration;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryLog;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * データ復旧の結果ログ
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_DATA_RECOVER_LOG")
public class SspmtDataRecoverLog extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public SspmtDataRecoverLogPk dataRecoverLogPk;

	/**
	 * エラー内容
	 */
	@Basic(optional = false)
	@Column(name = "ERROR_CONTENT")
	public String errorContent;

	/**
	 * 対象日
	 */
	@Basic(optional = false)
	@Column(name = "TARGET_DATE")
	public GeneralDate targetDate;

	@Override
	protected Object getKey() {
		return dataRecoverLogPk;
	}

	public DataRecoveryLog toDomain() {
		return new DataRecoveryLog(this.dataRecoverLogPk.recoveryProcessId, this.dataRecoverLogPk.target, errorContent,
				this.targetDate);
	}

	public static SspmtDataRecoverLog toEntity(DataRecoveryLog domain) {
		return new SspmtDataRecoverLog(new SspmtDataRecoverLogPk(domain.getRecoveryProcessId(), domain.getTarget()),
				domain.getErrorContent().v(), domain.getTargetDate());
	}
}
