package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.OneDayRepeatInterval;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.RepeatDetailSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.TaskEndDate;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.TaskEndTime;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.DailyDaySetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatDetailSettingDaily;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatDetailSettingMonthly;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatDetailSettingWeekly;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthDaysSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatWeekDaysSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.WeeklyWeekSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndDateClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndTimeClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.OneDayRepeatClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.RepeatContentItem;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.EndTime;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.OneDayRepeatIntervalDetail;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.StartTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Entity
@Table(name="KFNMT_EXEC_TASK_SETTING")
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtExecutionTaskSetting extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
    public KfnmtExecutionTaskSettingPK kfnmtExecTaskSettingPK;
	
	/* 開始日 */
	@Column(name = "START_DATE")
	public GeneralDate startDate;
	
	/* 開始時刻 */
	@Column(name = "START_TIME")
	public Integer startTime;
	
	/* 実行タスク終了時刻設定 */
	@Column(name = "END_TIME_CLS")
	public Integer endTimeCls;
	
	/* 終了時刻 */
	@Column(name = "END_TIME")
	public Integer endTime;
	
	/* 実行タスク終了時刻設定 */
	@Column(name = "ONE_DAY_REP_CLS")
	public Integer oneDayRepCls;
	
	/* 繰り返し間隔 */
	@Column(name = "ONE_DAY_REP_INR")
	public Integer oneDayRepInterval;
	
	/* 繰り返しする */
	@Column(name = "REP_CLS")
	public Integer repeatCls;
	
	/* 繰り返し内容 */
	@Column(name = "REP_CONTENT")
	public Integer repeatContent;
	
	/* 実行タスク終了日区分 */
	@Column(name = "END_DATE_CLS")
	public Integer endDateCls;
	
	/* 終了日日付指定 */
	@Column(name = "END_DATE")
	public GeneralDate endDate;
	
	/* 更新処理有効設定 */
	@Column(name = "ENABLED_SETTING")
	public Integer enabledSetting;
	
	/* 次回実行日時 */
	@Column(name = "NEXT_EXEC_DATE_TIME")
	public GeneralDateTime nextExecDateTime;
	
	
	/* 日 */
	@Column(name = "MONDAY")
	public Integer monday;
	
	/* 月 */
	@Column(name = "TUESDAY")
	public Integer tuesday;
	
	/* 火 */
	@Column(name = "WEDNESDAY")
	public Integer wednesday;
	
	/* 水 */
	@Column(name = "THURSDAY")
	public Integer thursday;
	
	/* 木 */
	@Column(name = "FRIDAY")
	public Integer friday;
	
	/* 金 */
	@Column(name = "SATURDAY")
	public Integer saturday;
	
	/* 土 */
	@Column(name = "SUNDAY")
	public Integer sunday;
	
	/* 1月 */
	@Column(name = "REP_INR_JAN")
	public Integer january;
	
	/* 2月 */
	@Column(name = "REP_INR_FEB")
	public Integer february;
	
	/* 3月 */
	@Column(name = "REP_INR_MAR")
	public Integer march;
	
	/* 4月 */
	@Column(name = "REP_INR_APR")
	public Integer april;
	
	/* 5月 */
	@Column(name = "REP_INR_MAY")
	public Integer may;
	
	/* 6月 */
	@Column(name = "REP_INR_JUN")
	public Integer june;
	
	/* 7月 */
	@Column(name = "REP_INR_JUL")
	public Integer july;
	
	/* 8月 */
	@Column(name = "REP_INR_AUG")
	public Integer august;
	
	/* 9月 */
	@Column(name = "REP_INR_SEP")
	public Integer september;
	
	/* 10月 */
	@Column(name = "REP_INR_OCT")
	public Integer october;
	
	/* 11月 */
	@Column(name = "REP_INR_NOV")
	public Integer november;
	
	/* 12月 */
	@Column(name = "REP_INR_DEC")
	public Integer december;
	
	/* スケジュールID */
	@Column(name = "SCHEDULE_ID")
	public String scheduleId;
	/* 終了処理スケジュールID*/
	@Column(name = "END_SCHEDULE_ID")
	public String endScheduleId;
	
	@OneToMany(mappedBy="execTaskSetting", cascade = CascadeType.ALL)
	@JoinTable(name = "KFNMT_REP_MONTH_DATE")
	public List<KfnmtRepeatMonthDay> repeatMonthDateList;
	
	@Override
	protected Object getKey() {
		return this.kfnmtExecTaskSettingPK;
	}
	
	/**
	 * Convert entity to domain
	 * @return WorkplaceManager object
	 */
	public ExecutionTaskSetting toDomain() {
		// 終了時刻 
		TaskEndTime endTime = new TaskEndTime(
				EnumAdaptor.valueOf(this.endTimeCls, EndTimeClassification.class),
				this.endTime == null ? null : new EndTime(this.endTime));
		
		// 繰り返し間隔
		OneDayRepeatInterval oneDayRepInr = new OneDayRepeatInterval(this.oneDayRepInterval == null ? null : EnumAdaptor.valueOf(this.oneDayRepInterval,OneDayRepeatIntervalDetail.class), EnumAdaptor.valueOf(this.oneDayRepCls, OneDayRepeatClassification.class));
				
		// 終了日日付指定
		TaskEndDate endDate = new TaskEndDate(
				EnumAdaptor.valueOf(this.endDateCls, EndDateClassification.class),
				this.endDate);
		
		
		// 繰り返し詳細設定(毎週)
		RepeatDetailSettingWeekly weekly =
				new RepeatDetailSettingWeekly(
						new RepeatWeekDaysSelect(this.monday == 1 ? true : false,
												this.tuesday == 1 ? true : false,
												this.wednesday == 1 ? true : false,
												this.thursday == 1 ? true : false,
												this.friday == 1 ? true : false,
												this.saturday == 1 ? true : false,
												this.sunday == 1 ? true : false));
		
		// 繰り返し詳細設定(毎月)
		List<RepeatMonthDaysSelect> days =
				this.repeatMonthDateList
						.stream()
							.map(x -> EnumAdaptor.valueOf(x.kfnmtRepMonthDayPK.monthDay, RepeatMonthDaysSelect.class))
								.collect(Collectors.toList());
		RepeatMonthSelect months =
				new RepeatMonthSelect(this.january == 1 ? true : false,
									this.february == 1 ? true : false,
									this.march == 1 ? true : false,
									this.april == 1 ? true : false,
									this.may == 1 ? true : false,
									this.june == 1 ? true : false,
									this.july == 1 ? true : false,
									this.august == 1 ? true : false,
									this.september == 1 ? true : false,
									this.october == 1 ? true : false,
									this.november == 1 ? true : false,
									this.december == 1 ? true : false);
		RepeatDetailSettingMonthly monthly = new RepeatDetailSettingMonthly(days, months);
		
		// 繰り返し詳細設定
		RepeatDetailSetting detailSetting = new RepeatDetailSetting(weekly, monthly);
		
		return new ExecutionTaskSetting(oneDayRepInr,
										new ExecutionCode(this.kfnmtExecTaskSettingPK.execItemCd),
										this.kfnmtExecTaskSettingPK.companyId,
										this.enabledSetting == 1 ? true : false,
										this.nextExecDateTime,
										endDate,
										endTime,
										this.repeatCls == 1 ? true : false,
										EnumAdaptor.valueOf(this.repeatContent, RepeatContentItem.class),
										detailSetting,
										startDate,
										new StartTime(this.startTime),this.scheduleId,this.endScheduleId);
	}
	
	/**
	 * Convert domain to entity
	 * @param domain ExecutionTaskSetting object
	 * @return KfnmtExecutionTaskSetting object
	 */
	public static KfnmtExecutionTaskSetting toEntity(ExecutionTaskSetting domain) {
		return new KfnmtExecutionTaskSetting(
							new KfnmtExecutionTaskSettingPK(domain.getCompanyId(), domain.getExecItemCd().v()),
							domain.getStartDate(),
							domain.getStartTime().v(),
							domain.getEndTime() == null ? 0 : domain.getEndTime().getEndTimeCls().value,
							domain.getEndTime().getEndTime() == null ? null : domain.getEndTime().getEndTime().v(),
							domain.getOneDayRepInr() == null ? 0 : domain.getOneDayRepInr().getOneDayRepCls().value,
							(domain.getOneDayRepInr().getDetail() == null || !domain.getOneDayRepInr().getDetail().isPresent()) ? null : domain.getOneDayRepInr().getDetail().get().value,
							domain.isRepeat() ? 1 : 0,
							domain.getContent() == null ? 0 : domain.getContent().value,
							domain.getEndDate() == null ? 0 : domain.getEndDate().getEndDateCls().value,
							domain.getEndDate() == null ? null : domain.getEndDate().getEndDate(),
							domain.isEnabledSetting() ? 1 : 0,
							(domain.getNextExecDateTime() == null || !domain.getNextExecDateTime().isPresent())  ? null : domain.getNextExecDateTime().get(),
							domain.getDetailSetting().getWeekly().getWeekdaySetting().isMonday() ? 1 : 0,
							domain.getDetailSetting().getWeekly().getWeekdaySetting().isTuesday() ? 1 : 0,
							domain.getDetailSetting().getWeekly().getWeekdaySetting().isWednesday() ? 1 : 0,
							domain.getDetailSetting().getWeekly().getWeekdaySetting().isThursday() ? 1 : 0,
							domain.getDetailSetting().getWeekly().getWeekdaySetting().isFriday() ? 1 : 0,
							domain.getDetailSetting().getWeekly().getWeekdaySetting().isSaturday() ? 1 : 0,
							domain.getDetailSetting().getWeekly().getWeekdaySetting().isSunday() ? 1 : 0,
							domain.getDetailSetting().getMonthly().getMonth().isJanuary() ? 1 : 0,
							domain.getDetailSetting().getMonthly().getMonth().isFebruary() ? 1 : 0,
							domain.getDetailSetting().getMonthly().getMonth().isMarch() ? 1 : 0,
							domain.getDetailSetting().getMonthly().getMonth().isApril() ? 1 : 0,
							domain.getDetailSetting().getMonthly().getMonth().isMay() ? 1 : 0,
							domain.getDetailSetting().getMonthly().getMonth().isJune() ? 1 : 0,
							domain.getDetailSetting().getMonthly().getMonth().isJuly() ? 1 : 0,
							domain.getDetailSetting().getMonthly().getMonth().isAugust() ? 1 : 0,
							domain.getDetailSetting().getMonthly().getMonth().isSeptember() ? 1 : 0,
							domain.getDetailSetting().getMonthly().getMonth().isOctober() ? 1 : 0,
							domain.getDetailSetting().getMonthly().getMonth().isNovember() ? 1 : 0,
							domain.getDetailSetting().getMonthly().getMonth().isDecember() ? 1 : 0,
							domain.getScheduleId(), (domain.getEndScheduleId()!=null && domain.getEndScheduleId().isPresent())?domain.getEndScheduleId().get():null,		
							new ArrayList<>());
	}
}
