package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 項目の算出式: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KfnrtCalcFormulaItemPk implements Serializable
{
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
	@Column(name = "SET_OUT_CD")
	public String setOutCd;
	
	/**
	* コード
	*/
	@Basic(optional = false)
	@Column(name = "ITEM_OUT_CD")
	public String itemOutCd;
	
	/**
	* 勤怠項目ID
	*/
	@Basic(optional = false)
	@Column(name = "ATTENDANCE_ITEM_ID")
	public int attendanceItemId;
	
}
