package nts.uk.ctx.at.function.infra.entity.alarmworkplace.condition;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmpatternworkplace.KfnmtALstWkpPtn;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.monthdayperiod.KfnmtAssignDatelineEndPk;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.singlemonth.KfnmtAssignNumofMon;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * entity : チェック条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_PTN_MAP_CAT")
public class KfnmtPtnMapCat extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtPtnMapCatPk pk;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="ALARM_PATTERN_CD", referencedColumnName="ALARM_PATTERN_CD", insertable = false, updatable = false),
		@JoinColumn(name="CATEGORY", referencedColumnName="CATEGORY", insertable = false, updatable = false) })
	public KfnmtWkpCheckCondition checkCondition;



}
