package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaTimeUnit;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_FORMULA_ITEM")
public class KscstTimeUnitFunc extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscstTimeUnitFuncPK kscstTimeUnitFuncPK;
	
	/* 勤怠項目ID */
	@Column(name = "ATTENDANCE_ITEM_ID")
	public String attendanceItemId;
	
	/* 予定項目ID */
	@Column(name = "PRESET_ITEM_ID")
	public String presetItemId;
	
	/* 演算子区分 */
	@Column(name = "OPERATOR_ATR")
	public int operatorAtr;
	
	/* 順番 */
	@Column(name = "DISPORDER")
	public int dispOrder;
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "KSCST_FORMULA_TIME_UNIT.CID", insertable = false, updatable = false),
		@JoinColumn(name = "VERTICAL_CAL_CD", referencedColumnName = "KSCST_FORMULA_TIME_UNIT.VERTICAL_CAL_CD", insertable = false, updatable = false),
		@JoinColumn(name = "ITEM_ID", referencedColumnName = "KSCST_FORMULA_TIME_UNIT.ITEM_ID", insertable = false, updatable = false)
	})
	public KscstFormulaTimeUnit kscstFormulaTimeUnit;
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstTimeUnitFuncPK;
	}
}
