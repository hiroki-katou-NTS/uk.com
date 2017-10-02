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
@Table(name = "KSCST_FORMULA_TIME_UNIT")
public class KscstFormulaTimeUnit extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscstFormulaTimeUnitPK kscstFormulaTimeUnitPK;

	/*丸め区分 */
	@Column(name = "ROUNDING_TIME")
	public int roundingTime;
	
	/* 丸め区分 */
	@Column(name = "ROUNDING_ATR")
	public int roundingAtr;
	
	/* 単価 */
	@Column(name = "UNIT_PRICE")
	public int unitPrice;
	
	/* 実績表示区分 */
	@Column(name = "ACTUAL_DISPLAY_ATR")
	public int actualDisplayAtr;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstFormulaTimeUnitPK;
	}
}
