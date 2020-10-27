package nts.uk.ctx.exio.infra.entity.exi.execlog;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 外部受入実行結果ログ: 主キー情報
 */
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
public class OiodtExAcExecLogPk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 条件設定コード
	 */
	@Basic(optional = false)
	@Column(name = "CONDITION_SET_CD")
	public String conditionSetCd;

	/**
	 * 外部受入処理ID
	 */
	@Basic(optional = false)
	@Column(name = "EXTERNAL_PROCESS_ID")
	public String externalProcessId;

}
