package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * KFNMT_RPT_WK_YEAR_CALC id
 * 
 * @author LienPTK
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KfnmtRptWkYearCalcPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 項目設定ID */
	@Basic(optional = false)
	@Column(name = "LAYOUT_ID")
	public String layoutId;

	/** コード(非表示) */
	@Basic(optional = false)
	@Column(name = "ITEM_OUT_CD")
	public String itemOutCd;
	
	/** 日次の勤怠項目．勤怠項目ID */
	@Column(name = "ATTENDANCE_ITEM_ID")
	public int attandanceItemId;
}
