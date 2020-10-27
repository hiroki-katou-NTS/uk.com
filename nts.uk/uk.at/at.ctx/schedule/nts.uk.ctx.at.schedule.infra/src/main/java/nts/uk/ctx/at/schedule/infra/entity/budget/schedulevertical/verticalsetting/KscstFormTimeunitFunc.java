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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_FORM_TIMEUNIT_FUNC")
public class KscstFormTimeunitFunc extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KscstFormTimeunitFuncPK kscstTimeUnitFuncPK;

	/* 勤怠項目ID */
	@Column(name = "ATTENDANCE_ITEM_ID")
	public String attendanceItemId;

	/* 予定項目ID */
	@Column(name = "PRESET_ITEM_ID")
	public String presetItemId;

	/* 演算子区分 */
	@Column(name = "OPERATOR_ATR")
	public int operatorAtr;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "VERTICAL_CAL_CD", referencedColumnName = "VERTICAL_CAL_CD", insertable = false, updatable = false),
			@JoinColumn(name = "VERTICAL_CAL_ITEM_ID", referencedColumnName = "VERTICAL_CAL_ITEM_ID", insertable = false, updatable = false) })
	public KscstFormTimeUnit kscstFormulaTimeUnit;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstTimeUnitFuncPK;
	}
}
