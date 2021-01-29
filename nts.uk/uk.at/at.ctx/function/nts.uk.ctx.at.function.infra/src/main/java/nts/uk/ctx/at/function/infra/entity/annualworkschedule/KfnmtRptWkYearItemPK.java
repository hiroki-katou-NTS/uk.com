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
 * 
 * @author LienPTK
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KfnmtRptWkYearItemPK implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 項目設定ID */
	@Basic(optional = false)
	@Column(name = "LAYOUT_ID")
	public String layoutId;

	/** コード(非表示) */
	@Basic(optional = false)
	@Column(name = "ITEM_OUT_CD")
	public String itemOutCd;

}