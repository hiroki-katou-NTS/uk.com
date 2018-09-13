package nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 明細書印字年月設定: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtSpecPrintYmSetPk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 処理区分NO
	 */
	@Basic(optional = false)
	@Column(name = "PROCESS_CATE_NO")
	public int processCateNo;

	/**
	 * 処理年月
	 */
	@Basic(optional = false)
	@Column(name = "PROCESS_DATE")
	public int processDate;

}
