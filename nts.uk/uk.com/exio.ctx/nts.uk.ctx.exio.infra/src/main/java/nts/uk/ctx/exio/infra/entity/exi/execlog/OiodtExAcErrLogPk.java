package nts.uk.ctx.exio.infra.entity.exi.execlog;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 外部受入エラーログ: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OiodtExAcErrLogPk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ログ連番
	 */
	@Basic(optional = false)
	@Column(name = "LOG_SEQ_NUMBER")
	public int logSeqNumber;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 外部受入処理ID
	 */
	@Basic(optional = false)
	@Column(name = "EXTERNAL_PROCESS_ID")
	public String externalProcessId;

}
