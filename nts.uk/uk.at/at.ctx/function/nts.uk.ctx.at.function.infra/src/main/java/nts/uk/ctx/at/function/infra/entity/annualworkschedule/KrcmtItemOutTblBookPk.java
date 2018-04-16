package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 帳表に出力する項目: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrcmtItemOutTblBookPk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	* 会社ID
	*/
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	* コード
	*/
	@Basic(optional = false)
	@Column(name = "CODE")
	public int code;

	/**
	* 並び順
	*/
	@Basic(optional = false)
	@Column(name = "SORT_BY")
	public int sortBy;
}
