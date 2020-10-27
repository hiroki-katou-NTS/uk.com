package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
* 項目の算出式
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNRT_CALC_FORMULA_ITEM")
public class KfnrtCalcFormulaItem extends ContractUkJpaEntity implements Serializable
{
	private static final long serialVersionUID = 1L;
	/**
	* ID
	*/
	@EmbeddedId
	public KfnrtCalcFormulaItemPk calcFormulaItemPk;
	
	/**
	* オペレーション
	*/
	@Basic(optional = false)
	@Column(name = "OPERATION")
	public int operation;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "SET_OUT_CD", referencedColumnName = "SET_OUT_CD", insertable = false, updatable = false),
			@JoinColumn(name = "ITEM_OUT_CD", referencedColumnName = "CD", insertable = false, updatable = false) })
	public KfnrtItemOutTblBook itemOutTblBook;

	@Override
	protected Object getKey()
	{
		return calcFormulaItemPk;
	}

	public CalcFormulaItem toDomain() {
		return CalcFormulaItem.createFromJavaType(this.calcFormulaItemPk.cid, this.calcFormulaItemPk.setOutCd, this.calcFormulaItemPk.itemOutCd, this.calcFormulaItemPk.attendanceItemId, this.operation);
	}

	public static KfnrtCalcFormulaItem toEntity(CalcFormulaItem domain) {
		return new KfnrtCalcFormulaItem(new KfnrtCalcFormulaItemPk(domain.getCid(), domain.getSetOutCd(), domain.getItemOutCd(),
										domain.getAttendanceItemId()), domain.getOperation());
	}

	public KfnrtCalcFormulaItem(KfnrtCalcFormulaItemPk calcFormulaItemPk, int operation) {
		super();
		this.calcFormulaItemPk = calcFormulaItemPk;
		this.operation = operation;
	}
}
