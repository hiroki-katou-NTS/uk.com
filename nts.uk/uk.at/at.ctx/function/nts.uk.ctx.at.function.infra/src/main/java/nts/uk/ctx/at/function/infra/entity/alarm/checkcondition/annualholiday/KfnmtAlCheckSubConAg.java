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
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.AlarmCheckSubConAgr;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALST_CHK_HDPAID")
public class KfnmtAlCheckSubConAg extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtAlCheckSubConAgPK pk;

	/**
	 * 次回期間使用区分
	 */
	@Basic
	@Column(name = "NEXT_PERIOD_ATR")
	public int narrowUntilNext;
	
	/**
	 * 次回期間
	 */
	@Basic
	@Column(name = "NEXT_PERIOD")
	public Integer periodUntilNext;

	/**
	 * 前回年休付与日数使用区分
	 */
	@Basic
	@Column(name = "LASTTIME_DAY_ATR")
	public int narrowLastDay;

	/**
	 * 前回年休付与日数
	 */
	@Basic
	@Column(name = "LASTTIME_DAY")
	public Integer numberDayAward;


	@Override
	protected Object getKey() {
		return this.pk;
	}

	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false),
			@JoinColumn(name = "CD", referencedColumnName = "CD", insertable = false, updatable = false) })
	public KfnmtAlarmCheckConditionCategory condition;

	public KfnmtAlCheckSubConAg(KfnmtAlCheckSubConAgPK pk, int narrowUntilNext, int narrowLastDay, Integer numberDayAward,
			Integer periodUntilNext) {
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
				domain.isNarrowUntilNext() ? 1 : 0, domain.isNarrowLastDay() ? 1 : 0, domain.getNumberDayAward().isPresent() ? domain.getNumberDayAward().get().v() : null,
						domain.getPeriodUntilNext().isPresent() ? domain.getPeriodUntilNext().get().v() : null);
	}

	public AlarmCheckSubConAgr toDomain() {
		return new AlarmCheckSubConAgr(narrowUntilNext == 1, narrowLastDay == 1, numberDayAward, periodUntilNext);
	}
}
