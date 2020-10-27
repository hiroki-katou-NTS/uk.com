package nts.uk.ctx.sys.assist.infra.entity.datarestoration;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 対象者: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SspdtRecoverTargetPk implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * データ復旧処理ID
	 */
	@Basic(optional = false)
	@Column(name = "DATA_RECOVERY_PROCESS_ID")
	public String dataRecoveryProcessId;

	/**
	 * 社員ID
	 */
	@Basic(optional = false)
	@Column(name = "SID")
	public String sid;
}
