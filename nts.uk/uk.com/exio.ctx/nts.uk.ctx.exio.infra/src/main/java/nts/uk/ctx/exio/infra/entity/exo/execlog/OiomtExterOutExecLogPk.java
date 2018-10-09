package nts.uk.ctx.exio.infra.entity.exo.execlog;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 外部出力実行結果ログ: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OiomtExterOutExecLogPk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 外部出力処理ID
	 */
	@Basic(optional = false)
	@Column(name = "OUT_PROCESS_ID")
	public String outProcessId;

}
