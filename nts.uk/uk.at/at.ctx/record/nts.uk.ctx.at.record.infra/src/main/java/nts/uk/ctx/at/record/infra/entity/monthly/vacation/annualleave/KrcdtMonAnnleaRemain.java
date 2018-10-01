package nts.uk.ctx.at.record.infra.entity.monthly.vacation.annualleave;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveAttdRateDays;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveGrant;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveMaxRemainingTime;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveRemainingDetail;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUndigestedNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUsedDays;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUsedNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AttendanceRate;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnLeaRemainingNum;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnLeaUsedNum;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnualLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.RealAnnualLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.TimeAnnualLeaveUsedTime;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.UndigestedAnnualLeaveDays;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.UndigestedTimeAnnualLeaveTime;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;
import nts.uk.ctx.at.shared.dom.common.days.YearlyDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：年休月別残数データ
 * 
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KRCDT_MON_ANNLEA_REMAIN")
@NoArgsConstructor
public class KrcdtMonAnnleaRemain extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAnnleaRemainPK PK;

	/** 締め処理状態 */
	@Column(name = "CLOSURE_STATUS")
	public int closureStatus;

	/** 開始年月日 */
	@Column(name = "START_DATE")
	public GeneralDate startDate;
	/** 終了年月日 */
	@Column(name = "END_DATE")
	public GeneralDate endDate;

	/** 年休使用日数 */
	@Column(name = "USED_DAYS")
	public double usedDays;
	/** 年休使用日数付与前 */
	@Column(name = "USED_DAYS_BEFORE")
	public double usedDaysBefore;
	/** 年休使用日数付与後 */
	@Column(name = "USED_DAYS_AFTER")
	public Double usedDaysAfter;

	/** 使用時間 */
	@Column(name = "USED_MINUTES")
	public Integer usedMinutes;
	/** 使用時間付与前 */
	@Column(name = "USED_MINUTES_BEFORE")
	public Integer usedMinutesBefore;
	/** 使用時間付与後 */
	@Column(name = "USED_MINUTES_AFTER")
	public Integer usedMinutesAfter;
	/** 使用回数 */
	@Column(name = "USED_TIMES")
	public Integer usedTimes;

	/** 実年休使用日数 */
	@Column(name = "FACT_USED_DAYS")
	public double factUsedDays;
	/** 実年休使用日数付与前 */
	@Column(name = "FACT_USED_DAYS_BEFORE")
	public double factUsedDaysBefore;
	/** 実年休使用日数付与後 */
	@Column(name = "FACT_USED_DAYS_AFTER")
	public Double factUsedDaysAfter;

	/** 実使用時間 */
	@Column(name = "FACT_USED_MINUTES")
	public Integer factUsedMinutes;
	/** 実使用時間付与前 */
	@Column(name = "FACT_USED_MINUTES_BEFORE")
	public Integer factUsedMinutesBefore;
	/** 実使用時間付与後 */
	@Column(name = "FACT_USED_MINUTES_AFTER")
	public Integer factUsedMinutesAfter;
	/** 実使用回数 */
	@Column(name = "FACT_USED_TIMES")
	public Integer factUsedTimes;

	/** 合計残日数 */
	@Column(name = "REMAINING_DAYS")
	public double remainingDays;
	/** 合計残時間 */
	@Column(name = "REMAINING_MINUTES")
	public Integer remainingMinutes;
	/** 実合計残日数 */
	@Column(name = "FACT_REMAINING_DAYS")
	public double factRemainingDays;
	/** 実合計残時間 */
	@Column(name = "FACT_REMAINING_MINUTES")
	public Integer factRemainingMinutes;

	/** 合計残日数付与前 */
	@Column(name = "REMAINING_DAYS_BEFORE")
	public double remainingDaysBefore;
	/** 合計残時間付与前 */
	@Column(name = "REMAINING_MINUTES_BEFORE")
	public Integer remainingMinutesBefore;
	/** 実合計残日数付与前 */
	@Column(name = "FACT_REMAINING_DAYS_BEFORE")
	public double factRemainingDaysBefore;
	/** 実合計残時間付与前 */
	@Column(name = "FACT_REMAINING_MINUTES_BEFORE")
	public Integer factRemainingMinutesBefore;

	/** 合計残日数付与後 */
	@Column(name = "REMAINING_DAYS_AFTER")
	public Double remainingDaysAfter;
	/** 合計残時間付与後 */
	@Column(name = "REMAINING_MINUTES_AFTER")
	public Integer remainingMinutesAfter;
	/** 実合計残日数付与後 */
	@Column(name = "FACT_REMAINING_DAYS_AFTER")
	public Double factRemainingDaysAfter;
	/** 実合計残時間付与後 */
	@Column(name = "FACT_REMAINING_MINUTES_AFTER")
	public Integer factRemainingMinutesAfter;

	/** 未消化日数 */
	@Column(name = "UNUSED_DAYS")
	public double unusedDays;
	/** 未消化時間 */
	@Column(name = "UNUSED_MINUTES")
	public Integer unusedMinutes;

	/** 所定日数 */
	@Column(name = "PREDETERMINED_DAYS")
	public int predeterminedDays;
	/** 労働日数 */
	@Column(name = "LABOR_DAYS")
	public int laborDays;
	/** 控除日数 */
	@Column(name = "DEDUCTION_DAYS")
	public int deductionDays;

	/** 半日年休使用回数 */
	@Column(name = "HALF_USED_TIMES")
	public Integer halfUsedTimes;
	/** 半日年休使用回数付与前 */
	@Column(name = "HALF_USED_TIMES_BEFORE")
	public Integer halfUsedTimesBefore;
	/** 半日年休使用回数付与後 */
	@Column(name = "HALF_USED_TIMES_AFTER")
	public Integer halfUsedTimesAfter;
	/** 半日年休残回数 */
	@Column(name = "HALF_REMAINING_TIMES")
	public Integer halfRemainingTimes;
	/** 半日年休残回数付与前 */
	@Column(name = "HALF_REMAINING_TIMES_BEFORE")
	public Integer halfRemainingTimesBefore;
	/** 半日年休残回数付与後 */
	@Column(name = "HALF_REMAINING_TIMES_AFTER")
	public Integer halfRemainingTimesAfter;

	/** 実半日年休使用回数 */
	@Column(name = "FACT_HALF_USED_TIMES")
	public Integer factHalfUsedTimes;
	/** 実半日年休使用回数付与前 */
	@Column(name = "FACT_HALF_USED_TIMES_BEFORE")
	public Integer factHalfUsedTimesBefore;
	/** 実半日年休使用回数付与後 */
	@Column(name = "FACT_HALF_USED_TIMES_AFTER")
	public Integer factHalfUsedTimesAfter;
	/** 実半日年休残回数 */
	@Column(name = "FACT_HALF_REMAINING_TIMES")
	public Integer factHalfRemainingTimes;
	/** 実半日年休残回数付与前 */
	@Column(name = "FACT_HALF_REMAINING_TIMES_BE")
	public Integer factHalfRemainingTimesBefore;
	/** 実半日年休残回数付与後 */
	@Column(name = "FACT_HALF_REMAINING_TIMES_AF")
	public Integer factHalfRemainingTimesAfter;

	/** 時間年休上限残時間 */
	@Column(name = "TIME_REMAINING_MINUTES")
	public Integer timeRemainingMinutes;
	/** 時間年休上限残時間付与前 */
	@Column(name = "TIME_REMAINING_MINUTES_BEFORE")
	public Integer timeRemainingMinutesBefore;
	/** 時間年休上限残時間付与後 */
	@Column(name = "TIME_REMAINING_MINUTES_AFTER")
	public Integer timeRemainingMinutesAfter;

	/** 実時間年休上限残時間 */
	@Column(name = "FACT_TIME_REMAINING_MINUTES")
	public Integer factTimeRemainingMinutes;
	/** 実時間年休上限残時間付与前 */
	@Column(name = "FACT_TIME_REMAINING_MINUTES_BE")
	public Integer factTimeRemainingMinutesBefore;
	/** 実時間年休上限残時間付与後 */
	@Column(name = "FACT_TIME_REMAINING_MINUTES_AF")
	public Integer factTimeRemainingMinutesAfter;

	/** 付与区分 */
	@Column(name = "GRANT_ATR")
	public int grantAtr;
	/** 付与日数 */
	@Column(name = "GRANT_DAYS")
	public Double grantDays;
	/** 付与所定日数 */
	@Column(name = "GRANT_PREDETERMINED_DAYS")
	public Integer grantPredeterminedDays;
	/** 付与労働日数 */
	@Column(name = "GRANT_LABOR_DAYS")
	public Integer grantLaborDays;
	/** 付与控除日数 */
	@Column(name = "GRANT_DEDUCTION_DAYS")
	public Integer grantDeductionDays;
	/** 控除日数付与前 */
	@Column(name = "DEDUCTION_DAYS_BEFORE")
	public Integer deductionDaysBefore;
	/** 控除日数付与後 */
	@Column(name = "DEDUCTION_DAYS_AFTER")
	public Integer deductionDaysAfter;
	/** 出勤率 */
	@Column(name = "ATTENDANCE_RATE")
	public Double attendanceRate;

	/** 年休月別残数明細 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "krcdtMonAnnleaRemain", orphanRemoval = true)
	public List<KrcdtMonAnnleaDetail> krcdtMonAnnleaDetails;

	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}

	/**
	 * ドメインに変換
	 * 
	 * @return 年休月別残数データ
	 */
	public AnnLeaRemNumEachMonth toDomain() {

		// 年休月別残数明細を分類する
		List<AnnualLeaveRemainingDetail> normalDetail = new ArrayList<>();
		List<AnnualLeaveRemainingDetail> normalDetailBefore = new ArrayList<>();
		List<AnnualLeaveRemainingDetail> normalDetailAfter = new ArrayList<>();
		List<AnnualLeaveRemainingDetail> realDetail = new ArrayList<>();
		List<AnnualLeaveRemainingDetail> realDetailBefore = new ArrayList<>();
		List<AnnualLeaveRemainingDetail> realDetailAfter = new ArrayList<>();
		if (this.krcdtMonAnnleaDetails != null) {
			this.krcdtMonAnnleaDetails.sort((a, b) -> a.PK.grantDate.compareTo(b.PK.grantDate));
			for (val detail : this.krcdtMonAnnleaDetails) {
				switch (detail.PK.remainingType) {
				case 0: // 残数
					normalDetail.add(detail.toDomainForNormal());
					realDetail.add(detail.toDomainForReal());
					break;
				case 1: // 付与前残数
					normalDetailBefore.add(detail.toDomainForNormal());
					realDetailBefore.add(detail.toDomainForReal());
					break;
				case 2: // 付与後残数
					normalDetailAfter.add(detail.toDomainForNormal());
					realDetailAfter.add(detail.toDomainForReal());
					break;
				}
			}
		}

		// 年休：使用時間
		UsedMinutes valUsedMinutesAfter = null;
		if (this.usedMinutesAfter != null) {
			valUsedMinutesAfter = new UsedMinutes(this.usedMinutesAfter);
		}
		TimeAnnualLeaveUsedTime valUsedTime = null;
		if (this.usedTimes != null && this.usedMinutes != null && this.usedMinutesBefore != null) {
			valUsedTime = TimeAnnualLeaveUsedTime.of(new UsedTimes(this.usedTimes), new UsedMinutes(this.usedMinutes),
					new UsedMinutes(this.usedMinutesBefore), Optional.ofNullable(valUsedMinutesAfter));
		}

		// 年休：残数付与後
		AnnualLeaveRemainingNumber valRemainAfter = null;
		if (this.remainingDaysAfter != null) {
			RemainingMinutes valRemainMinutesAfter = null;
			if (this.remainingMinutesAfter != null) {
				valRemainMinutesAfter = new RemainingMinutes(this.remainingMinutesAfter);
			}
			valRemainAfter = AnnualLeaveRemainingNumber.of(new AnnualLeaveRemainingDayNumber(this.remainingDaysAfter),
					Optional.ofNullable(valRemainMinutesAfter), normalDetailAfter);
		}

		// 年休
		AnnualLeaveUsedDayNumber valUsedDaysAfter = null;
		if (this.usedDaysAfter != null) {
			valUsedDaysAfter = new AnnualLeaveUsedDayNumber(this.usedDaysAfter);
		}
		RemainingMinutes valRemainMinutes = null;
		if (this.remainingMinutes != null) {
			valRemainMinutes = new RemainingMinutes(this.remainingMinutes);
		}
		RemainingMinutes valRemainMinutesBefore = null;
		if (this.remainingMinutesBefore != null) {
			valRemainMinutesBefore = new RemainingMinutes(this.remainingMinutesBefore);
		}
		UndigestedTimeAnnualLeaveTime valUnusedMinutes = null;
		if (this.unusedMinutes != null) {
			valUnusedMinutes = UndigestedTimeAnnualLeaveTime.of(new UsedMinutes(this.unusedMinutes));
		}
		AnnualLeave annualLeave = AnnualLeave.of(
				AnnualLeaveUsedNumber.of(AnnualLeaveUsedDays.of(new AnnualLeaveUsedDayNumber(this.usedDays),
						new AnnualLeaveUsedDayNumber(this.usedDaysBefore), Optional.ofNullable(valUsedDaysAfter)),
						Optional.ofNullable(valUsedTime)),
				AnnualLeaveRemainingNumber.of(new AnnualLeaveRemainingDayNumber(this.remainingDays),
						Optional.ofNullable(valRemainMinutes), normalDetail),
				AnnualLeaveRemainingNumber.of(new AnnualLeaveRemainingDayNumber(this.remainingDaysBefore),
						Optional.ofNullable(valRemainMinutesBefore), normalDetailBefore),
				Optional.ofNullable(valRemainAfter),
				AnnualLeaveUndigestedNumber.of(
						UndigestedAnnualLeaveDays.of(new AnnualLeaveUsedDayNumber(this.unusedDays)),
						Optional.ofNullable(valUnusedMinutes)));

		// 実年休：使用時間
		UsedMinutes valFactUsedMinutesAfter = null;
		if (this.factUsedMinutesAfter != null) {
			valFactUsedMinutesAfter = new UsedMinutes(this.factUsedMinutesAfter);
		}
		TimeAnnualLeaveUsedTime valFactUsedTime = null;
		if (this.factUsedTimes != null && this.factUsedMinutes != null && this.factUsedMinutesBefore != null) {
			valFactUsedTime = TimeAnnualLeaveUsedTime.of(new UsedTimes(this.factUsedTimes),
					new UsedMinutes(this.factUsedMinutes), new UsedMinutes(this.factUsedMinutesBefore),
					Optional.ofNullable(valFactUsedMinutesAfter));
		}

		// 実年休：残数付与後
		AnnualLeaveRemainingNumber valFactRemainAfter = null;
		if (this.factRemainingDaysAfter != null) {
			RemainingMinutes valFactRemainMinutesAfter = null;
			if (this.factRemainingMinutesAfter != null) {
				valFactRemainMinutesAfter = new RemainingMinutes(this.factRemainingMinutesAfter);
			}
			valFactRemainAfter = AnnualLeaveRemainingNumber.of(
					new AnnualLeaveRemainingDayNumber(this.factRemainingDaysAfter),
					Optional.ofNullable(valFactRemainMinutesAfter), realDetailAfter);
		}

		// 実年休
		AnnualLeaveUsedDayNumber valFactUsedDaysAfter = null;
		if (this.factUsedDaysAfter != null) {
			valFactUsedDaysAfter = new AnnualLeaveUsedDayNumber(this.factUsedDaysAfter);
		}
		RemainingMinutes valFactRemainMinutes = null;
		if (this.factRemainingMinutes != null) {
			valFactRemainMinutes = new RemainingMinutes(this.factRemainingMinutes);
		}
		RemainingMinutes valFactRemainMinutesBefore = null;
		if (this.factRemainingMinutesBefore != null) {
			valFactRemainMinutesBefore = new RemainingMinutes(this.factRemainingMinutesBefore);
		}
		RealAnnualLeave realAnnualLeave = RealAnnualLeave.of(
				AnnualLeaveUsedNumber.of(AnnualLeaveUsedDays.of(new AnnualLeaveUsedDayNumber(this.factUsedDays),
						new AnnualLeaveUsedDayNumber(this.factUsedDaysBefore),
						Optional.ofNullable(valFactUsedDaysAfter)), Optional.ofNullable(valFactUsedTime)),
				AnnualLeaveRemainingNumber.of(new AnnualLeaveRemainingDayNumber(this.factRemainingDays),
						Optional.ofNullable(valFactRemainMinutes), realDetail),
				AnnualLeaveRemainingNumber.of(new AnnualLeaveRemainingDayNumber(this.factRemainingDaysBefore),
						Optional.ofNullable(valFactRemainMinutesBefore), realDetailBefore),
				Optional.ofNullable(valFactRemainAfter));

		// 半日年休
		HalfDayAnnualLeave halfDayAnnualLeave = null;
		if (this.halfRemainingTimes != null && this.halfRemainingTimesBefore != null && this.halfUsedTimes != null
				&& this.halfUsedTimesBefore != null) {
			RemainingTimes valHalfRemainTimesAfter = null;
			if (this.halfRemainingTimesAfter != null) {
				valHalfRemainTimesAfter = new RemainingTimes(this.halfRemainingTimesAfter);
			}
			UsedTimes valHalfUsedTimesAfter = null;
			if (this.halfUsedTimesAfter != null) {
				valHalfUsedTimesAfter = new UsedTimes(this.halfUsedTimesAfter);
			}
			halfDayAnnualLeave = HalfDayAnnualLeave.of(
					HalfDayAnnLeaRemainingNum.of(new RemainingTimes(this.halfRemainingTimes),
							new RemainingTimes(this.halfRemainingTimesBefore),
							Optional.ofNullable(valHalfRemainTimesAfter)),
					HalfDayAnnLeaUsedNum.of(new UsedTimes(this.halfUsedTimes), new UsedTimes(this.halfUsedTimesBefore),
							Optional.ofNullable(valHalfUsedTimesAfter)));
		}

		// 実半日年休
		HalfDayAnnualLeave realHalfDayAnnualLeave = null;
		if (this.factHalfRemainingTimes != null && this.factHalfRemainingTimesBefore != null
				&& this.factHalfUsedTimes != null && this.factHalfUsedTimesBefore != null) {
			RemainingTimes valFactHalfRemainTimesAfter = null;
			if (this.factHalfRemainingTimesAfter != null) {
				valFactHalfRemainTimesAfter = new RemainingTimes(this.factHalfRemainingTimesAfter);
			}
			UsedTimes valFactHalfUsedTimesAfter = null;
			if (this.factHalfUsedTimesAfter != null) {
				valFactHalfUsedTimesAfter = new UsedTimes(this.factHalfUsedTimesAfter);
			}
			realHalfDayAnnualLeave = HalfDayAnnualLeave.of(
					HalfDayAnnLeaRemainingNum.of(new RemainingTimes(this.factHalfRemainingTimes),
							new RemainingTimes(this.factHalfRemainingTimesBefore),
							Optional.ofNullable(valFactHalfRemainTimesAfter)),
					HalfDayAnnLeaUsedNum.of(new UsedTimes(this.factHalfUsedTimes),
							new UsedTimes(this.factHalfUsedTimesBefore),
							Optional.ofNullable(valFactHalfUsedTimesAfter)));
		}

		// 年休付与情報
		AnnualLeaveGrant annualLeaveGrant = null;
		if (this.grantDays != null && this.grantLaborDays != null && this.grantPredeterminedDays != null
				&& this.grantDeductionDays != null && this.deductionDaysBefore != null
				&& this.deductionDaysAfter != null && this.attendanceRate != null) {
			annualLeaveGrant = AnnualLeaveGrant.of(new AnnualLeaveGrantDayNumber(this.grantDays),
					new YearlyDays((double) this.grantLaborDays), new YearlyDays((double) this.grantPredeterminedDays),
					new YearlyDays((double) this.grantDeductionDays),
					new MonthlyDays((double) this.deductionDaysBefore),
					new MonthlyDays((double) this.deductionDaysAfter), new AttendanceRate(this.attendanceRate));
		}

		// 上限残時間
		AnnualLeaveMaxRemainingTime maxRemainingTime = null;
		if (this.timeRemainingMinutes != null && this.timeRemainingMinutesBefore != null) {
			RemainingMinutes valTimeRemainMinutesAfter = null;
			if (this.timeRemainingMinutesAfter != null) {
				valTimeRemainMinutesAfter = new RemainingMinutes(this.timeRemainingMinutesAfter);
			}
			maxRemainingTime = AnnualLeaveMaxRemainingTime.of(new RemainingMinutes(this.timeRemainingMinutes),
					new RemainingMinutes(this.timeRemainingMinutesBefore),
					Optional.ofNullable(valTimeRemainMinutesAfter));
		}

		// 実上限残時間
		AnnualLeaveMaxRemainingTime realMaxRemainingTime = null;
		if (this.factTimeRemainingMinutes != null && this.factTimeRemainingMinutesBefore != null) {
			RemainingMinutes valFactTimeRemainMinutesAfter = null;
			if (this.factTimeRemainingMinutesAfter != null) {
				valFactTimeRemainMinutesAfter = new RemainingMinutes(this.factTimeRemainingMinutesAfter);
			}
			realMaxRemainingTime = AnnualLeaveMaxRemainingTime.of(new RemainingMinutes(this.factTimeRemainingMinutes),
					new RemainingMinutes(this.factTimeRemainingMinutesBefore),
					Optional.ofNullable(valFactTimeRemainMinutesAfter));
		}

		return AnnLeaRemNumEachMonth.of(this.PK.employeeId, new YearMonth(this.PK.yearMonth),
				EnumAdaptor.valueOf(this.PK.closureId, ClosureId.class),
				new ClosureDate(this.PK.closureDay, (this.PK.isLastDay != 0)),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class), annualLeave, realAnnualLeave,
				Optional.ofNullable(halfDayAnnualLeave), Optional.ofNullable(realHalfDayAnnualLeave),
				Optional.ofNullable(annualLeaveGrant), Optional.ofNullable(maxRemainingTime),
				Optional.ofNullable(realMaxRemainingTime),
				AnnualLeaveAttdRateDays.of(new MonthlyDays((double) this.laborDays),
						new MonthlyDays((double) this.predeterminedDays), new MonthlyDays((double) this.deductionDays)),
				(this.grantAtr != 0));
	}

	/**
	 * ドメインから変換 （for Insert）
	 * 
	 * @param domain
	 *            年休月別残数データ
	 */
	public void fromDomainForPersist(AnnLeaRemNumEachMonth domain) {

		this.PK = new KrcdtMonAnnleaRemainPK(domain.getEmployeeId(), domain.getYearMonth().v(),
				domain.getClosureId().value, domain.getClosureDate().getClosureDay().v(),
				(domain.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		this.fromDomainForUpdate(domain);
	}

	/**
	 * ドメインから変換 (for Update)
	 * 
	 * @param domain
	 *            年休月別残数データ
	 */
	public void fromDomainForUpdate(AnnLeaRemNumEachMonth domain) {

		// Optional列の初期化
		this.usedDaysAfter = null;
		this.usedMinutes = null;
		this.usedMinutesBefore = null;
		this.usedMinutesAfter = null;
		this.usedTimes = null;
		this.factUsedDaysAfter = null;
		this.factUsedMinutes = null;
		this.factUsedMinutesBefore = null;
		this.factUsedMinutesAfter = null;
		this.factUsedTimes = null;
		this.remainingMinutes = null;
		this.factRemainingMinutes = null;
		this.remainingMinutesBefore = null;
		this.factRemainingMinutesBefore = null;
		this.remainingDaysAfter = null;
		this.remainingMinutesAfter = null;
		this.factRemainingDaysAfter = null;
		this.factRemainingMinutesAfter = null;
		this.unusedMinutes = null;
		this.halfUsedTimes = null;
		this.halfUsedTimesBefore = null;
		this.halfUsedTimesAfter = null;
		this.halfRemainingTimes = null;
		this.halfRemainingTimesBefore = null;
		this.halfRemainingTimesAfter = null;
		this.factHalfUsedTimes = null;
		this.factHalfUsedTimesBefore = null;
		this.factHalfUsedTimesAfter = null;
		this.factHalfRemainingTimes = null;
		this.factHalfRemainingTimesBefore = null;
		this.factHalfRemainingTimesAfter = null;
		this.timeRemainingMinutes = null;
		this.timeRemainingMinutesBefore = null;
		this.timeRemainingMinutesAfter = null;
		this.factTimeRemainingMinutes = null;
		this.factTimeRemainingMinutesBefore = null;
		this.factTimeRemainingMinutesAfter = null;
		this.grantDays = null;
		this.grantPredeterminedDays = null;
		this.grantLaborDays = null;
		this.grantDeductionDays = null;
		this.deductionDaysBefore = null;
		this.deductionDaysAfter = null;
		this.attendanceRate = null;

		val normal = domain.getAnnualLeave();
		val normalUsed = normal.getUsedNumber();
		val real = domain.getRealAnnualLeave();
		val realUsed = real.getUsedNumber();

		this.closureStatus = domain.getClosureStatus().value;
		this.startDate = domain.getClosurePeriod().start();
		this.endDate = domain.getClosurePeriod().end();

		// 年休：使用数
		this.usedDays = normalUsed.getUsedDays().getUsedDays().v();
		this.usedDaysBefore = normalUsed.getUsedDays().getUsedDaysBeforeGrant().v();
		if (normalUsed.getUsedDays().getUsedDaysAfterGrant().isPresent()) {
			this.usedDaysAfter = normalUsed.getUsedDays().getUsedDaysAfterGrant().get().v();
		}
		if (normalUsed.getUsedTime().isPresent()) {
			val normalUsedTime = normalUsed.getUsedTime().get();
			this.usedMinutes = normalUsedTime.getUsedTime().v();
			this.usedMinutesBefore = normalUsedTime.getUsedTimeBeforeGrant().v();
			if (normalUsedTime.getUsedTimeAfterGrant().isPresent()) {
				this.usedMinutesAfter = normalUsedTime.getUsedTimeAfterGrant().get().v();
			}
			this.usedTimes = normalUsedTime.getUsedTimes().v();
		}

		// 実年休：使用数
		this.factUsedDays = realUsed.getUsedDays().getUsedDays().v();
		this.factUsedDaysBefore = realUsed.getUsedDays().getUsedDaysBeforeGrant().v();
		if (realUsed.getUsedDays().getUsedDaysAfterGrant().isPresent()) {
			this.factUsedDaysAfter = realUsed.getUsedDays().getUsedDaysAfterGrant().get().v();
		}
		if (realUsed.getUsedTime().isPresent()) {
			val realUsedTime = realUsed.getUsedTime().get();
			this.factUsedMinutes = realUsedTime.getUsedTime().v();
			this.factUsedMinutesBefore = realUsedTime.getUsedTimeBeforeGrant().v();
			if (realUsedTime.getUsedTimeAfterGrant().isPresent()) {
				this.factUsedMinutesAfter = realUsedTime.getUsedTimeAfterGrant().get().v();
			}
			this.factUsedTimes = realUsedTime.getUsedTimes().v();
		}

		// 年休：残数
		this.remainingDays = normal.getRemainingNumber().getTotalRemainingDays().v();
		if (normal.getRemainingNumber().getTotalRemainingTime().isPresent()) {
			this.remainingMinutes = normal.getRemainingNumber().getTotalRemainingTime().get().v();
		}
		this.remainingDaysBefore = normal.getRemainingNumberBeforeGrant().getTotalRemainingDays().v();
		if (normal.getRemainingNumberBeforeGrant().getTotalRemainingTime().isPresent()) {
			this.remainingMinutesBefore = normal.getRemainingNumberBeforeGrant().getTotalRemainingTime().get().v();
		}
		if (normal.getRemainingNumberAfterGrant().isPresent()) {
			val normalRemainAfter = normal.getRemainingNumberAfterGrant().get();
			this.remainingDaysAfter = normalRemainAfter.getTotalRemainingDays().v();
			if (normalRemainAfter.getTotalRemainingTime().isPresent()) {
				this.remainingMinutesAfter = normalRemainAfter.getTotalRemainingTime().get().v();
			}
		}

		// 実年休：残数
		this.factRemainingDays = real.getRemainingNumber().getTotalRemainingDays().v();
		if (real.getRemainingNumber().getTotalRemainingTime().isPresent()) {
			this.factRemainingMinutes = real.getRemainingNumber().getTotalRemainingTime().get().v();
		}
		this.factRemainingDaysBefore = real.getRemainingNumberBeforeGrant().getTotalRemainingDays().v();
		if (real.getRemainingNumberBeforeGrant().getTotalRemainingTime().isPresent()) {
			this.factRemainingMinutesBefore = real.getRemainingNumberBeforeGrant().getTotalRemainingTime().get().v();
		}
		if (real.getRemainingNumberAfterGrant().isPresent()) {
			val realRemainAfter = real.getRemainingNumberAfterGrant().get();
			this.factRemainingDaysAfter = realRemainAfter.getTotalRemainingDays().v();
			if (realRemainAfter.getTotalRemainingTime().isPresent()) {
				this.factRemainingMinutesAfter = realRemainAfter.getTotalRemainingTime().get().v();
			}
		}

		// 年休：未消化数
		val normalUndigest = normal.getUndigestedNumber();
		this.unusedDays = normalUndigest.getUndigestedDays().getUndigestedDays().v();
		if (normalUndigest.getUndigestedTime().isPresent()) {
			this.unusedMinutes = normalUndigest.getUndigestedTime().get().getUndigestedTime().v();
		}

		// 出勤率日数
		val attendanceRateDays = domain.getAttendanceRateDays();
		this.predeterminedDays = attendanceRateDays.getPrescribedDays().v().intValue();
		this.laborDays = attendanceRateDays.getWorkingDays().v().intValue();
		this.deductionDays = attendanceRateDays.getDeductedDays().v().intValue();

		// 半日年休
		if (domain.getHalfDayAnnualLeave().isPresent()) {
			val normalHalf = domain.getHalfDayAnnualLeave().get();
			this.halfUsedTimes = normalHalf.getUsedNum().getTimes().v();
			this.halfUsedTimesBefore = normalHalf.getUsedNum().getTimesBeforeGrant().v();
			if (normalHalf.getUsedNum().getTimesAfterGrant().isPresent()) {
				this.halfUsedTimesAfter = normalHalf.getUsedNum().getTimesAfterGrant().get().v();
			}
			this.halfRemainingTimes = normalHalf.getRemainingNum().getTimes().v();
			this.halfRemainingTimesBefore = normalHalf.getRemainingNum().getTimesBeforeGrant().v();
			if (normalHalf.getRemainingNum().getTimesAfterGrant().isPresent()) {
				this.halfRemainingTimesAfter = normalHalf.getRemainingNum().getTimesAfterGrant().get().v();
			}
		}

		// 実半日年休
		if (domain.getRealHalfDayAnnualLeave().isPresent()) {
			val realHalf = domain.getRealHalfDayAnnualLeave().get();
			this.factHalfUsedTimes = realHalf.getUsedNum().getTimes().v();
			this.factHalfUsedTimesBefore = realHalf.getUsedNum().getTimesBeforeGrant().v();
			if (realHalf.getUsedNum().getTimesAfterGrant().isPresent()) {
				this.factHalfUsedTimesAfter = realHalf.getUsedNum().getTimesAfterGrant().get().v();
			}
			this.factHalfRemainingTimes = realHalf.getRemainingNum().getTimes().v();
			this.factHalfRemainingTimesBefore = realHalf.getRemainingNum().getTimesBeforeGrant().v();
			if (realHalf.getRemainingNum().getTimesAfterGrant().isPresent()) {
				this.factHalfRemainingTimesAfter = realHalf.getRemainingNum().getTimesAfterGrant().get().v();
			}
		}

		// 上限残時間
		if (domain.getMaxRemainingTime().isPresent()) {
			val normalMax = domain.getMaxRemainingTime().get();
			this.timeRemainingMinutes = normalMax.getTime().v();
			this.timeRemainingMinutesBefore = normalMax.getTimeBeforeGrant().v();
			if (normalMax.getTimeAfterGrant().isPresent()) {
				this.timeRemainingMinutesAfter = normalMax.getTimeAfterGrant().get().v();
			}
		}

		// 実上限残時間
		if (domain.getRealMaxRemainingTime().isPresent()) {
			val realMax = domain.getRealMaxRemainingTime().get();
			this.factTimeRemainingMinutes = realMax.getTime().v();
			this.factTimeRemainingMinutesBefore = realMax.getTimeBeforeGrant().v();
			if (realMax.getTimeAfterGrant().isPresent()) {
				this.factTimeRemainingMinutesAfter = realMax.getTimeAfterGrant().get().v();
			}
		}

		// 付与区分
		this.grantAtr = (domain.isGrantAtr() ? 1 : 0);

		// 年休付与情報
		if (domain.getAnnualLeaveGrant().isPresent()) {
			val grantInfo = domain.getAnnualLeaveGrant().get();
			this.grantDays = grantInfo.getGrantDays().v();
			this.grantPredeterminedDays = grantInfo.getGrantPrescribedDays().v().intValue();
			this.grantLaborDays = grantInfo.getGrantWorkingDays().v().intValue();
			this.grantDeductionDays = grantInfo.getGrantDeductedDays().v().intValue();
			this.deductionDaysBefore = grantInfo.getDeductedDaysBeforeGrant().v().intValue();
			this.deductionDaysAfter = grantInfo.getDeductedDaysAfterGrant().v().intValue();
			this.attendanceRate = grantInfo.getAttendanceRate().v().doubleValue();
		}

		// 年休月別残数明細：残数
		List<GeneralDate> normalGrantDateList = new ArrayList<>();
		Map<GeneralDate, AnnualLeaveRemainingDetail> normalRemain = new HashMap<>();
		Map<GeneralDate, AnnualLeaveRemainingDetail> normalRealRemain = new HashMap<>();
		for (val detail : normal.getRemainingNumber().getDetails()) {
			val grantDate = detail.getGrantDate();
			if (!normalGrantDateList.contains(grantDate))
				normalGrantDateList.add(grantDate);
			normalRemain.putIfAbsent(grantDate, detail);
		}
		for (val detail : real.getRemainingNumber().getDetails()) {
			val grantDate = detail.getGrantDate();
			if (!normalGrantDateList.contains(grantDate))
				normalGrantDateList.add(grantDate);
			normalRealRemain.putIfAbsent(grantDate, detail);
		}
		normalGrantDateList.removeIf(c -> {
			return (!normalRemain.containsKey(c) || !normalRealRemain.containsKey(c));
		});

		// 年休月別残数明細：残数付与前
		List<GeneralDate> beforeGrantDateList = new ArrayList<>();
		Map<GeneralDate, AnnualLeaveRemainingDetail> beforeRemain = new HashMap<>();
		Map<GeneralDate, AnnualLeaveRemainingDetail> beforeRealRemain = new HashMap<>();
		for (val detail : normal.getRemainingNumberBeforeGrant().getDetails()) {
			val grantDate = detail.getGrantDate();
			if (!beforeGrantDateList.contains(grantDate))
				beforeGrantDateList.add(grantDate);
			beforeRemain.putIfAbsent(grantDate, detail);
		}
		for (val detail : real.getRemainingNumberBeforeGrant().getDetails()) {
			val grantDate = detail.getGrantDate();
			if (!beforeGrantDateList.contains(grantDate))
				beforeGrantDateList.add(grantDate);
			beforeRealRemain.putIfAbsent(grantDate, detail);
		}
		beforeGrantDateList.removeIf(c -> {
			return (!beforeRemain.containsKey(c) || !beforeRealRemain.containsKey(c));
		});

		// 年休月別残数明細：残数付与後
		List<GeneralDate> afterGrantDateList = new ArrayList<>();
		Map<GeneralDate, AnnualLeaveRemainingDetail> afterRemain = new HashMap<>();
		Map<GeneralDate, AnnualLeaveRemainingDetail> afterRealRemain = new HashMap<>();
		if (normal.getRemainingNumberAfterGrant().isPresent() && real.getRemainingNumberAfterGrant().isPresent()) {
			for (val detail : normal.getRemainingNumberAfterGrant().get().getDetails()) {
				val grantDate = detail.getGrantDate();
				if (!afterGrantDateList.contains(grantDate))
					afterGrantDateList.add(grantDate);
				afterRemain.putIfAbsent(grantDate, detail);
			}
			for (val detail : real.getRemainingNumberAfterGrant().get().getDetails()) {
				val grantDate = detail.getGrantDate();
				if (!afterGrantDateList.contains(grantDate))
					afterGrantDateList.add(grantDate);
				afterRealRemain.putIfAbsent(grantDate, detail);
			}
			afterGrantDateList.removeIf(c -> {
				return (!afterRemain.containsKey(c) || !afterRealRemain.containsKey(c));
			});
		}

		// 年休月別残数明細 更新
		if (this.krcdtMonAnnleaDetails == null)
			this.krcdtMonAnnleaDetails = new ArrayList<>();
		val itrDetails = this.krcdtMonAnnleaDetails.listIterator();
		while (itrDetails.hasNext()) {
			val detail = itrDetails.next();
			switch (detail.PK.remainingType) {
			case 0:
				if (!normalGrantDateList.contains(detail.PK.grantDate))
					itrDetails.remove();
				break;
			case 1:
				if (!beforeGrantDateList.contains(detail.PK.grantDate))
					itrDetails.remove();
				break;
			case 2:
				if (!afterGrantDateList.contains(detail.PK.grantDate))
					itrDetails.remove();
				break;
			default:
				itrDetails.remove();
				break;
			}
		}
		for (val grantDate : normalGrantDateList) {
			KrcdtMonAnnleaDetail detail = new KrcdtMonAnnleaDetail();
			val detailOpt = this.krcdtMonAnnleaDetails.stream().filter(c -> {
				return c.PK.remainingType == 0 && c.PK.grantDate.equals(grantDate);
			}).findFirst();
			if (detailOpt.isPresent()) {
				detailOpt.get().fromDomainForUpdate(normalRemain.get(grantDate), normalRealRemain.get(grantDate));
			} else {
				detail.fromDomainForPersist(domain, 0, normalRemain.get(grantDate), normalRealRemain.get(grantDate));
				this.krcdtMonAnnleaDetails.add(detail);
			}
		}
		for (val grantDate : beforeGrantDateList) {
			KrcdtMonAnnleaDetail detail = new KrcdtMonAnnleaDetail();
			val detailOpt = this.krcdtMonAnnleaDetails.stream().filter(c -> {
				return c.PK.remainingType == 1 && c.PK.grantDate.equals(grantDate);
			}).findFirst();
			if (detailOpt.isPresent()) {
				detailOpt.get().fromDomainForUpdate(beforeRemain.get(grantDate), beforeRealRemain.get(grantDate));
			} else {
				detail.fromDomainForPersist(domain, 1, beforeRemain.get(grantDate), beforeRealRemain.get(grantDate));
				this.krcdtMonAnnleaDetails.add(detail);
			}
		}
		for (val grantDate : afterGrantDateList) {
			KrcdtMonAnnleaDetail detail = new KrcdtMonAnnleaDetail();
			val detailOpt = this.krcdtMonAnnleaDetails.stream().filter(c -> {
				return c.PK.remainingType == 2 && c.PK.grantDate.equals(grantDate);
			}).findFirst();
			if (detailOpt.isPresent()) {
				detailOpt.get().fromDomainForUpdate(afterRemain.get(grantDate), afterRealRemain.get(grantDate));
			} else {
				detail.fromDomainForPersist(domain, 2, afterRemain.get(grantDate), afterRealRemain.get(grantDate));
				this.krcdtMonAnnleaDetails.add(detail);
			}
		}
	}
}
