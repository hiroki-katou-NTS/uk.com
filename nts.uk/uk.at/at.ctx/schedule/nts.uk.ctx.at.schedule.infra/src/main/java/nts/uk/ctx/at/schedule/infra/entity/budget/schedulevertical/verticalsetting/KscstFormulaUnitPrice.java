package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_FORMULA_UNITPRICE")
public class KscstFormulaUnitPrice extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主キー **/
	@EmbeddedId
	public KscstFormulaUnitPricePK kscstFormulaUnitPricePK;

	/**出勤区分**/
	@Column(name = "ATTENDANCE_ATR")
	public int attendanceAtr;
	
	/**単価**/
	@Column(name = "UNIT_PRICE")
	public int unitPrice;
	
	@Override
	protected Object getKey() {
		return kscstFormulaUnitPricePK;
	} 

}
