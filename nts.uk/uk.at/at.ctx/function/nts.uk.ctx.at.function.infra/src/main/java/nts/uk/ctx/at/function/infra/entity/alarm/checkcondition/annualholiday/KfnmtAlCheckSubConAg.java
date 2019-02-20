package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.annualholiday;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.AlarmCheckSubConAgr;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_AL_CHECK_SUB_CON_AG")
public class KfnmtAlCheckSubConAg extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtAlCheckSubConAgPK pk;

	@Basic
	@Column(name = "NARROW_UNTIL_NEXT")
	public int narrowUntilNext;

	@Basic
	@Column(name = "NARROW_LAST_DAY")
	public int narrowLastDay;

	@Basic
	@Column(name = "NUMBER_DAY_AWARD")
	public int numberDayAward;

	@Basic
	@Column(name = "PERIOD_UNTIL_NEXT")
	public int periodUntilNext;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false),
			@JoinColumn(name = "CD", referencedColumnName = "CD", insertable = false, updatable = false) })
	public KfnmtAlarmCheckConditionCategory condition;

	public KfnmtAlCheckSubConAg(KfnmtAlCheckSubConAgPK pk, int narrowUntilNext, int narrowLastDay, int numberDayAward,
			int periodUntilNext) {
		super();
		this.pk = pk;
		this.narrowUntilNext = narrowUntilNext;
		this.narrowLastDay = narrowLastDay;
		this.numberDayAward = numberDayAward;
		this.periodUntilNext = periodUntilNext;
	}

	public static KfnmtAlCheckSubConAg toEntity(String companyId, String code, int category,
			AlarmCheckSubConAgr domain) {
		return new KfnmtAlCheckSubConAg(new KfnmtAlCheckSubConAgPK(companyId, category, code),
				domain.isNarrowUntilNext() ? 1 : 0, domain.isNarrowLastDay() ? 1 : 0, domain.getNumberDayAward().v(),
				domain.getPeriodUntilNext().v());
	}

	public AlarmCheckSubConAgr toDomain() {
		return new AlarmCheckSubConAgr(narrowUntilNext == 1, narrowLastDay == 1, numberDayAward, periodUntilNext);
	}
}
