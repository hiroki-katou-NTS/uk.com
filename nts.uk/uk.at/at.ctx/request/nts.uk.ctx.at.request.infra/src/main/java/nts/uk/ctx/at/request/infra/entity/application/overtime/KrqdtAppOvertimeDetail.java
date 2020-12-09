package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.NumberOfMonth;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36Agree;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeAnnual;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeMonth;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimit;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimitAverage;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimitMonth;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimitPerMonth;
import nts.uk.ctx.at.request.infra.entity.application.holidaywork.KrqdtAppHdWork;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 時間外時間の詳細
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQDT_APP_OVERTIME_DETAIL")
public class KrqdtAppOvertimeDetail extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public KrqdtAppOvertimeDetailPk appOvertimeDetailPk;

	/**
	 * 年月
	 */
	@Column(name = "YEAR_MONTH")
	public Integer yearMonth;

	/*
	 * 申請時間
	 */
	@Column(name = "APPLICATION_TIME")
	public Integer appTimeAgree;

	/*
	 * 実績時間
	 */
	@Column(name = "ACTUAL_TIME")
	public Integer actualTimeMonth;

	/*
	 * 限度アラーム時間
	 */
	@Column(name = "LIMIT_ALARM_TIME")
	public Integer limitAlarmTime;

	/*
	 * 限度エラー時間
	 */
	@Column(name = "LIMIT_ERROR_TIME")
	public Integer limitErrorTime;

	/*
	 * 36年間超過回数
	 */
	@Column(name = "NUM_OF_YEAR36_OVER")
	public Integer numOfYear36Over;

	/*
	 * 36年間超過月
	 */
	@OneToMany(targetEntity = KrqdtYear36OverMonth.class, mappedBy = "appOvertimeDetail", cascade = CascadeType.ALL)
	@JoinTable(name = "KRQDT_YEAR36_OVER_MONTH")
	public List<KrqdtYear36OverMonth> year36OverMonth;

	/*
	 * 特例限度アラーム時間
	 */
	@Column(name = "EXCEPTION_LIMIT_ALARM_TIME")
	public Integer exceptionLimitAlarmTime;

	/*
	 * 特例限度エラー時間
	 */
	@Column(name = "EXCEPTION_LIMIT_ERROR_TIME")
	public Integer exceptionLimitErrorTime;

	/*
	 * 実績時間
	 */
	@Column(name = "ACTUAL_TIME_YEAR")
	public Integer actualTimeAnnual;

	/*
	 * 限度時間
	 */
	@Column(name = "LIMIT_TIME_YEAR")
	public Integer limitTime;

	/*
	 * 申請時間
	 */
	@Column(name = "REG_APPLICATION_TIME")
	public Integer appTimeAgreeUpperLimit;

	/*
	 * 時間外時間
	 */
	@Column(name = "REG_ACTUAL_TIME")
	public Integer overTime;

	/*
	 * 上限時間
	 */
	@Column(name = "REG_LIMIT_TIME")
	public Integer upperLimitTimeMonth;

	/*
	 * 平均時間
	 */
	@OneToMany(targetEntity = KrqdtTime36UpLimitPerMonth.class, mappedBy = "appOvertimeDetail", cascade = CascadeType.ALL)
	@JoinTable(name = "KRQDT_APP_OVERTIME_DET_M")
	public List<KrqdtTime36UpLimitPerMonth> averageTimeLst;

	/*
	 * 上限時間
	 */
	@Column(name = "REG_LIMIT_TIME_MULTI")
	public Integer upperLimitTimeAverage;

	@OneToOne
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "APP_ID", referencedColumnName = "APP_ID") })
	public KrqdtAppOvertime appOvertime;

	@OneToOne
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "APP_ID", referencedColumnName = "APP_ID") })
	public KrqdtAppHdWork appHolidayWork;

	@Override
	protected Object getKey() {
		return appOvertimeDetailPk;
	}

	public AppOvertimeDetail toDomain() {
		return new AppOvertimeDetail(appOvertimeDetailPk.cid, appOvertimeDetailPk.appId, new YearMonth(yearMonth),
				new Time36Agree(new AttendanceTimeMonth(appTimeAgree),
						new Time36AgreeMonth(new AttendanceTimeMonth(actualTimeMonth),
								new AgreementOneMonthTime(limitAlarmTime), new AgreementOneMonthTime(limitErrorTime),
								new NumberOfMonth(numOfYear36Over),
								year36OverMonth.stream().map(x -> new YearMonth(x.year36OverMonthPk.overMonth))
										.collect(Collectors.toList()),
								exceptionLimitAlarmTime == null ? Optional.empty()
										: Optional.of(new AgreementOneMonthTime(exceptionLimitAlarmTime)),
								exceptionLimitErrorTime == null ? Optional.empty()
										: Optional.of(new AgreementOneMonthTime(exceptionLimitErrorTime))),
						new Time36AgreeAnnual(new AttendanceTimeYear(actualTimeAnnual),
								new AgreementOneYearTime(limitTime))),
				new Time36AgreeUpperLimit(
						new AttendanceTimeMonth(
								appTimeAgreeUpperLimit),
						new Time36AgreeUpperLimitMonth(new AttendanceTimeMonth(overTime),
								new AgreementOneMonthTime(upperLimitTimeMonth)),
						new Time36AgreeUpperLimitAverage(averageTimeLst.stream()
								.map(x -> new Time36AgreeUpperLimitPerMonth(
										new YearMonthPeriod(new YearMonth(x.pk.periodYearStart),
												new YearMonth(x.pk.periodYearEnd)),
										new AttendanceTimeMonth(x.averageTime), new AttendanceTimeYear(x.totalTime)))
								.collect(Collectors.toList()), new AgreementOneMonthTime(upperLimitTimeAverage))));
	}

	public static KrqdtAppOvertimeDetail toEntity(Optional<AppOvertimeDetail> domainOtp) {
		if (!domainOtp.isPresent()) {
			return null;
		}
		AppOvertimeDetail domain = domainOtp.get();
		return new KrqdtAppOvertimeDetail(new KrqdtAppOvertimeDetailPk(domain.getCid(), domain.getAppId()),
				domain.getYearMonth().v(), domain.getTime36Agree().getApplicationTime().v(),
				domain.getTime36Agree().getAgreeMonth().getActualTime().v(),
				domain.getTime36Agree().getAgreeMonth().getLimitAlarmTime().v(),
				domain.getTime36Agree().getAgreeMonth().getLimitErrorTime().v(),
				domain.getTime36Agree().getAgreeMonth().getNumOfYear36Over().v(),
				domain.getTime36Agree().getAgreeMonth().getYear36OverMonth().stream()
						.map(x -> new KrqdtYear36OverMonth(
								new KrqdtYear36OverMonthPk(domain.getCid(), domain.getAppId(), x.v())))
						.collect(Collectors.toList()),
				domain.getTime36Agree().getAgreeMonth().getExceptionLimitAlarmTime().map(x -> x.v()).orElse(null),
				domain.getTime36Agree().getAgreeMonth().getExceptionLimitErrorTime().map(x -> x.v()).orElse(null),
				domain.getTime36Agree().getAgreeAnnual().getActualTime().v(),
				domain.getTime36Agree().getAgreeAnnual().getLimitTime().v(),
				domain.getTime36AgreeUpperLimit().getApplicationTime().v(),
				domain.getTime36AgreeUpperLimit().getAgreeUpperLimitMonth().getOverTime().v(),
				domain.getTime36AgreeUpperLimit().getAgreeUpperLimitMonth().getUpperLimitTime().v(),
				domain.getTime36AgreeUpperLimit().getAgreeUpperLimitAverage().getAverageTimeLst().stream()
						.map(x -> new KrqdtTime36UpLimitPerMonth(
								new KrqdpTime36UpLimitPerMonthPK(domain.getCid(), domain.getAppId(),
										x.getPeriod().start().v(), x.getPeriod().end().v()),
								x.getAverageTime().v(), x.getTotalTime().v(), null))
						.collect(Collectors.toList()),
				domain.getTime36AgreeUpperLimit().getAgreeUpperLimitAverage().getUpperLimitTime().v(), null, null);
	}

	public KrqdtAppOvertimeDetail(KrqdtAppOvertimeDetailPk appOvertimeDetailPk, Integer yearMonth, Integer appTimeAgree,
			Integer actualTimeMonth, Integer limitAlarmTime, Integer limitErrorTime, Integer numOfYear36Over,
			Integer exceptionLimitAlarmTime, Integer exceptionLimitErrorTime, Integer actualTimeAnnual,
			Integer limitTime, Integer appTimeAgreeUpperLimit, Integer overTime, Integer upperLimitTimeMonth,
			Integer upperLimitTimeAverage) {
		super();
		this.appOvertimeDetailPk = appOvertimeDetailPk;
		this.yearMonth = yearMonth;
		this.appTimeAgree = appTimeAgree;
		this.actualTimeMonth = actualTimeMonth;
		this.limitAlarmTime = limitAlarmTime;
		this.limitErrorTime = limitErrorTime;
		this.numOfYear36Over = numOfYear36Over;
		this.exceptionLimitAlarmTime = exceptionLimitAlarmTime;
		this.exceptionLimitErrorTime = exceptionLimitErrorTime;
		this.actualTimeAnnual = actualTimeAnnual;
		this.limitTime = limitTime;
		this.appTimeAgreeUpperLimit = appTimeAgreeUpperLimit;
		this.overTime = overTime;
		this.upperLimitTimeMonth = upperLimitTimeMonth;
		this.upperLimitTimeAverage = upperLimitTimeAverage;
	}

}
