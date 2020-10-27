package nts.uk.ctx.exio.infra.entity.exi.opmanage;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 外部受入動作管理: 主キー情報
 */
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
public class OiottExAcOpMngPk implements Serializable {
	private static final long serialVersionUID = 1L;

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
	@Column(name = "PROCESS_ID")
	public String processId;

}
