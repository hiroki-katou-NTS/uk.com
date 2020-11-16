package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.processexecution.AggrFrameCode;
import nts.uk.ctx.at.function.dom.processexecution.AggregationAnyPeriod;
import nts.uk.ctx.at.function.dom.processexecution.AlarmExtraction;
import nts.uk.ctx.at.function.dom.processexecution.AppRouteUpdateDaily;
import nts.uk.ctx.at.function.dom.processexecution.AppRouteUpdateMonthly;
import nts.uk.ctx.at.function.dom.processexecution.AuxiliaryPatternCode;
import nts.uk.ctx.at.function.dom.processexecution.DeleteData;
import nts.uk.ctx.at.function.dom.processexecution.ExternalAcceptance;
import nts.uk.ctx.at.function.dom.processexecution.ExternalOutput;
import nts.uk.ctx.at.function.dom.processexecution.IndexReconstruction;
import nts.uk.ctx.at.function.dom.processexecution.MonthlyAggregate;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionSetting;
import nts.uk.ctx.at.function.dom.processexecution.ReExecutionCondition;
import nts.uk.ctx.at.function.dom.processexecution.ReflectionApprovalResult;
import nts.uk.ctx.at.function.dom.processexecution.SaveData;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceCreation;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.CreationPeriod;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreation;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreationPeriod;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KfnmtProcessExecutionSetting.
 */
@Data
@Entity
@Table(name = "KFNMT_PROC_EXEC_SETTING")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class KfnmtProcessExecutionSetting extends UkJpaEntity implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	public static final long serialVersionUID = 1L;

	/**
	 * Column 排他バージョン
	 */
	@Version
	@Column(name = "EXCLUS_VER")
	public long version;

	/**
	 * The primary key
	 */
	@EmbeddedId
	public KfnmtProcessExecutionSettingPK kfnmtProcExecSetPK;

	/**
	 * The contract code.
	 */
	@Column(name = "CONTRACT_CD")
	public String contractCode;

	/**
	 * The personal schedule creation classification.<br>
	 * 個人スケジュール作成区分
	 */
	@Column(name = "PER_SCHEDULE_CLS")
	public int perScheduleCls;

	/**
	 * The target month.<br>
	 * 対象月
	 */
	@Column(name = "TARGET_MONTH")
	public int targetMonth;

	/**
	 * The target date.<br>
	 * 対象日
	 */
	@Column(name = "TARGET_DATE")
	public Integer targetDate;

	/**
	 * The creation period.<br>
	 * 作成期間
	 */
	@Column(name = "CREATION_PERIOD")
	public Integer creationPeriod;

	/**
	 * The designated year.<br>
	 * 指定年
	 */
	@Column(name = "DESIGNATED_YEAR")
	public Integer designatedYear;

	/**
	 * The start month day.<br>
	 * 指定開始月日
	 */
	@Column(name = "START_MONTHDAY")
	public Integer startMonthDay;

	/**
	 * The end month day.<br>
	 * 指定終了月日
	 */
	@Column(name = "END_MONTHDAY")
	public Integer endMonthDay;

	/**
	 * The create new employee schedule.<br>
	 * 新入社員を作成する
	 */
	@Column(name = "CRE_NEW_SYA_SCHED")
	public int createNewEmpSched;

	/**
	 * The daily perf classification.<br>
	 * 日別実績の作成・計算区分
	 */
	@Column(name = "DAILY_PERF_CLS")
	public int dailyPerfCls;

	/**
	 * The daily perf item.<br>
	 * 作成・計算項目
	 */
	@Column(name = "DAILY_PERF_ITEM")
	public int dailyPerfItem;

	/**
	 * The create new employee daily perf.<br>
	 * 新入社員は入社日から作成
	 */
	@Column(name = "CRE_NEW_SYA_DAI")
	public int createNewEmpDailyPerf;

	/**
	 * The reflect result classification.<br>
	 * 承認結果反映
	 */
	@Column(name = "REFLECT_RS_CLS")
	public int reflectResultCls;

	/**
	 * The monthly agg classification.<br>
	 * 月別集計
	 */
	@Column(name = "MONTHLY_AGG_CLS")
	public int monthlyAggCls;

	/**
	 * The app route update attribute.<br>
	 * 承認ルート更新区分
	 */
	@Column(name = "APP_ROUTE_UPDATE_ATR_DAI")
	public int appRouteUpdateAtr;

	/**
	 * The create new emp app.<br>
	 * 新入社員を作成する
	 */
	@Column(name = "CRE_NEW_SYA_APP")
	public Integer createNewEmpApp;

	/**
	 * The app route update attribute monthly.<br>
	 * 承認ルート更新（月次）
	 */
	@Column(name = "APP_ROUTE_UPDATE_ATR_MON")
	public int appRouteUpdateAtrMon;

	/**
	 * The alarm attribute.<br>
	 * アラーム抽出区分
	 */
	@Column(name = "ALARM_ATR")
	public int alarmAtr;

	/**
	 * The alarm code.<br>
	 * コード
	 */
	@Column(name = "ALARM_CODE")
	public String alarmCode;

	/**
	 * The mail principal.<br>
	 * メールを送信する(本人)
	 */
	@Column(name = "MAIL_PRINCIPAL")
	public Integer mailPrincipal;

	/**
	 * The mail administrator.<br>
	 * メールを送信する(管理者)
	 */
	@Column(name = "MAIL_ADMINISTRATOR")
	public Integer mailAdministrator;

	/**
	 * The display tp principal.<br>
	 * トップページに表示(本人)
	 */
	@Column(name = "DISPLAY_TP_PRINCIPAL")
	public Integer displayTpPrincipal;

	/**
	 * The display tp admin.<br>
	 * メールを送信する(管理者)
	 */
	@Column(name = "DISPLAY_TP_ADMIN")
	public Integer displayTpAdmin;

	/**
	 * The ext output attribute.<br>
	 * 外部出力区分
	 **/
	@Column(name = "EXT_OUTPUT_ART")
	public int extOutputArt;

	/**
	 * The ext acceptance attribute.<br>
	 * 外部受入区分
	 **/
	@Column(name = "EXT_ACCEPTANCE_ART")
	public int extAcceptanceArt;

	/**
	 * The data storage attribute.<br>
	 * データの保存区分
	 **/
	@Column(name = "DATA_STORAGE_ART")
	public int dataStorageArt;

	/**
	 * The data storage code.<br>
	 * パターンコード
	 **/
	@Column(name = "DATA_STORAGE_CODE")
	public String dataStorageCode;

	/**
	 * The data deletion attribute.<br>
	 * データの削除区分
	 **/
	@Column(name = "DATA_DELETION_ART")
	public int dataDeletionArt;

	/**
	 * The data deletion code.<br>
	 * パターンコード
	 **/
	@Column(name = "DATA_DELETION_CODE")
	public String dataDeletionCode;

	/**
	 * The agg any period attribute.<br>
	 * 任意期間の集計の使用区分
	 **/
	@Column(name = "AGG_ANY_PERIOD_ART")
	public int aggAnyPeriodArt;

	/**
	 * The agg any period code.<br>
	 * 任意集計枠コード
	 **/
	@Column(name = "AGG_ANY_PERIOD_CODE")
	public String aggAnyPeriodCode;

	/**
	 * The recreate change bus.<br>
	 * 勤務種別変更者を再作成
	 */
	@Column(name = "RECRE_CHANGE_BUS")
	public int recreateChangeBus;

	/**
	 * The recreate transfer.<br>
	 * 異動者を再作成する
	 */
	@Column(name = "RECRE_CHANGE_WKP")
	public int recreateTransfer;

	/**
	 * The recreate leave sya.<br>
	 * 休職者・休業者を再作成
	 **/
	@Column(name = "RECRE_LEAVE_SYA")
	public int recreateLeaveSya;

	/**
	 * The index reorg attribute.<br>
	 * インデックス再構成の使用区分
	 **/
	@Column(name = "INDEX_REORG_ART")
	public int indexReorgArt;

	/**
	 * The upd statistics attribute.<br>
	 * 統計情報を更新する
	 **/
	@Column(name = "UPD_STATISTICS_ART")
	public int updStatisticsArt;

	/**
	 * The proc exec.
	 */
	@OneToOne
	@JoinColumns({@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
				  @JoinColumn(name = "EXEC_ITEM_CD", referencedColumnName = "EXEC_ITEM_CD", insertable = false, updatable = false)})
	public KfnmtProcessExecution procExec;

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	@Override
	protected Object getKey() {
		return this.kfnmtProcExecSetPK;
	}

	public KfnmtProcessExecutionSetting(KfnmtProcessExecutionSettingPK kfnmtProcExecSetPK,
	                                    String contractCode, int perScheduleCls, int targetMonth, Integer targetDate, Integer creationPeriod,
	                                    Integer designatedYear, Integer startMonthDay, Integer endMonthDay, int createNewEmpSched, int dailyPerfCls,
	                                    int dailyPerfItem, int createNewEmpDailyPerf, int reflectResultCls, int monthlyAggCls, int appRouteUpdateAtr,
	                                    Integer createNewEmpApp, int appRouteUpdateAtrMon, int alarmAtr, String alarmCode, Integer mailPrincipal,
	                                    Integer mailAdministrator, Integer displayTpPrincipal, Integer displayTpAdmin, int extOutputArt,
	                                    int extAcceptanceArt, int dataStorageArt, String dataStorageCode, int dataDeletionArt,
	                                    String dataDeletionCode, int aggAnyPeriodArt, String aggAnyPeriodCode, int recreateChangeBus,
	                                    int recreateTransfer, int recreateLeaveSya, int indexReorgArt, int updStatisticsArt) {
		super();
		this.kfnmtProcExecSetPK = kfnmtProcExecSetPK;
		this.contractCode = contractCode;
		this.perScheduleCls = perScheduleCls;
		this.targetMonth = targetMonth;
		this.targetDate = targetDate;
		this.creationPeriod = creationPeriod;
		this.designatedYear = designatedYear;
		this.startMonthDay = startMonthDay;
		this.endMonthDay = endMonthDay;
		this.createNewEmpSched = createNewEmpSched;
		this.dailyPerfCls = dailyPerfCls;
		this.dailyPerfItem = dailyPerfItem;
		this.createNewEmpDailyPerf = createNewEmpDailyPerf;
		this.reflectResultCls = reflectResultCls;
		this.monthlyAggCls = monthlyAggCls;
		this.appRouteUpdateAtr = appRouteUpdateAtr;
		this.createNewEmpApp = createNewEmpApp;
		this.appRouteUpdateAtrMon = appRouteUpdateAtrMon;
		this.alarmAtr = alarmAtr;
		this.alarmCode = alarmCode;
		this.mailPrincipal = mailPrincipal;
		this.mailAdministrator = mailAdministrator;
		this.displayTpPrincipal = displayTpPrincipal;
		this.displayTpAdmin = displayTpAdmin;
		this.extOutputArt = extOutputArt;
		this.extAcceptanceArt = extAcceptanceArt;
		this.dataStorageArt = dataStorageArt;
		this.dataStorageCode = dataStorageCode;
		this.dataDeletionArt = dataDeletionArt;
		this.dataDeletionCode = dataDeletionCode;
		this.aggAnyPeriodArt = aggAnyPeriodArt;
		this.aggAnyPeriodCode = aggAnyPeriodCode;
		this.recreateChangeBus = recreateChangeBus;
		this.recreateTransfer = recreateTransfer;
		this.recreateLeaveSya = recreateLeaveSya;
		this.indexReorgArt = indexReorgArt;
		this.updStatisticsArt = updStatisticsArt;
	}

	/**
	 * Creates from domain.
	 *
	 * @param companyId       the company id
	 * @param execItemCode    the exec item code
	 * @param contractCode    the contract code
	 * @param domain          the domain
	 * @return the entity Kfnmt process execution setting
	 */
	public static KfnmtProcessExecutionSetting createFromDomain(String companyId,
	                                                            String execItemCode,
	                                                            String contractCode,
	                                                            ProcessExecutionSetting domain) {
		if (domain == null) {
			return null;
		}
		KfnmtProcessExecutionSetting entity = new KfnmtProcessExecutionSetting();
		entity.kfnmtProcExecSetPK = new KfnmtProcessExecutionSettingPK(companyId, execItemCode);
		entity.contractCode = contractCode;
		entity.perScheduleCls = domain.getPerScheduleCreation().getPerScheduleCls().value;
		entity.targetMonth = domain.getPerScheduleCreation().getPerSchedulePeriod().getTargetMonth().value;
		entity.targetDate = domain.getPerScheduleCreation().getPerSchedulePeriod().getTargetDate()
																				  .map(TargetDate::v)
																				  .orElse(null);
		entity.creationPeriod = domain.getPerScheduleCreation().getPerSchedulePeriod().getCreationPeriod()
																					  .map(CreationPeriod::v)
																					  .orElse(null);
		entity.designatedYear = domain.getPerScheduleCreation().getPerSchedulePeriod().getDesignatedYear()
																					  .map(createScheduleYear -> createScheduleYear.value)
																					  .orElse(null);
		entity.startMonthDay = domain.getPerScheduleCreation().getPerSchedulePeriod().getStartMonthDayIntVal();
		entity.endMonthDay = domain.getPerScheduleCreation().getPerSchedulePeriod().getEndMonthDayIntVal();
		entity.createNewEmpSched = domain.getPerScheduleCreation().getCreateNewEmpSched().value;
		entity.dailyPerfCls = domain.getDailyPerf().getDailyPerfCls().value;
		entity.dailyPerfItem = domain.getDailyPerf().getDailyPerfItem().value;
		entity.createNewEmpDailyPerf = domain.getDailyPerf().getCreateNewEmpDailyPerf().value;
		entity.reflectResultCls = domain.getReflectAppResult().getReflectResultCls().value;
		entity.monthlyAggCls = domain.getMonthlyAggregate().getMonthlyAggCls().value;
		entity.appRouteUpdateAtr = domain.getAppRouteUpdateDaily().getAppRouteUpdateAtr().value;
		entity.createNewEmpApp = domain.getAppRouteUpdateDaily().getCreateNewEmpApp()
																.map(createNewEmpApp -> createNewEmpApp.value)
																.orElse(null);
		entity.appRouteUpdateAtrMon = domain.getAppRouteUpdateMonthly().getAppRouteUpdateAtr().value;
		entity.alarmAtr = domain.getAlarmExtraction().getAlarmAtr().value;
		entity.alarmCode = domain.getAlarmExtraction().getAlarmCode()
													  .map(AlarmPatternCode::v)
													  .orElse(null);
		entity.mailPrincipal = domain.getAlarmExtraction().getMailPrincipal()
														  .map(mailPrincipal -> mailPrincipal ? 1 : 0)
														  .orElse(null);
		entity.mailAdministrator = domain.getAlarmExtraction().getMailAdministrator()
															  .map(mailAdministrator -> mailAdministrator ? 1 : 0)
															  .orElse(null);
		entity.displayTpPrincipal = domain.getAlarmExtraction().getDisplayOnTopPagePrincipal()
															   .map(displayTpPrincipal -> displayTpPrincipal ? 1 : 0)
															   .orElse(null);
		entity.displayTpAdmin = domain.getAlarmExtraction().getDisplayOnTopPageAdministrator()
														   .map(displayTpAdmin -> displayTpAdmin ? 1 : 0)
														   .orElse(null);
		entity.extOutputArt = domain.getExternalOutput().getExtOutputCls().value;
		entity.extAcceptanceArt = domain.getExternalAcceptance().getExtAcceptCls().value;
		entity.dataStorageArt = domain.getSaveData().getSaveDataCls().value;
		entity.dataStorageCode = domain.getSaveData().getPatternCode()
													 .map(AuxiliaryPatternCode::v)
													 .orElse(null);
		entity.dataDeletionArt = domain.getDeleteData().getDataDelCls().value;
		entity.dataDeletionCode = domain.getDeleteData().getPatternCode()
														.map(AuxiliaryPatternCode::v)
														.orElse(null);
		entity.aggAnyPeriodArt = domain.getAggrAnyPeriod().getAggAnyPeriodAttr().value;
		entity.aggAnyPeriodCode = domain.getAggrAnyPeriod().getAggrFrameCode()
														   .map(AggrFrameCode::v)
														   .orElse(null);
		if (domain.getReExecCondition() != null) {
			entity.recreateChangeBus = domain.getReExecCondition().getRecreatePersonChangeWkt().value;
			entity.recreateTransfer = domain.getReExecCondition().getRecreateTransfer().value;
			entity.recreateLeaveSya = domain.getReExecCondition().getRecreateLeave().value;
		}
		entity.indexReorgArt = domain.getIndexReconstruction().getIndexReorgAttr().value;
		entity.updStatisticsArt = domain.getIndexReconstruction().getUpdateStatistics().value;
		return entity;
	}

	/**
	 * Converts entity to domain.
	 *
	 * @return the domain Process execution setting
	 */
	public ProcessExecutionSetting toDomain() {
		// Instantiates new domain
		ProcessExecutionSetting domain = new ProcessExecutionSetting();
		// Sets alarm extraction
		domain.setAlarmExtraction(new AlarmExtraction(this.alarmAtr,
													  this.alarmCode,
													  this.mailPrincipal,
													  this.mailAdministrator,
													  this.displayTpAdmin,
													  this.displayTpAdmin));
		// Sets personal schedule creation
		PersonalScheduleCreationPeriod perSchedulePeriod = new PersonalScheduleCreationPeriod(this.creationPeriod,
																							  this.targetDate,
																							  this.targetMonth,
																							  this.designatedYear,
																							  this.startMonthDay,
																							  this.endMonthDay);
		PersonalScheduleCreation perScheduleCreation = new PersonalScheduleCreation(perSchedulePeriod,
																					this.perScheduleCls,
																					this.createNewEmpSched);
		domain.setPerScheduleCreation(perScheduleCreation);
		// Sets daily performance creation
		domain.setDailyPerf(new DailyPerformanceCreation(this.dailyPerfItem, this.createNewEmpDailyPerf, this.dailyPerfCls));
		// Sets reflection approval result
		domain.setReflectAppResult(new ReflectionApprovalResult(this.reflectResultCls));
		// Sets monthly aggregate
		domain.setMonthlyAggregate(new MonthlyAggregate(this.monthlyAggCls));
		// Sets approval route update daily
		domain.setAppRouteUpdateDaily(new AppRouteUpdateDaily(this.appRouteUpdateAtr, this.createNewEmpApp));
		// Sets approval route update monthly
		domain.setAppRouteUpdateMonthly(new AppRouteUpdateMonthly(this.appRouteUpdateAtrMon));
		// Sets delete data
		domain.setDeleteData(new DeleteData(this.dataDeletionArt, this.dataDeletionCode));
		// Sets save data
		domain.setSaveData(new SaveData(this.dataStorageArt, this.dataStorageCode));
		// Sets external acceptance
		domain.setExternalAcceptance(new ExternalAcceptance(this.extAcceptanceArt, Collections.emptyList()));
		// Sets external output
		domain.setExternalOutput(new ExternalOutput(this.extOutputArt, Collections.emptyList()));
		// Sets aggregation any period
		domain.setAggrAnyPeriod(new AggregationAnyPeriod(this.aggAnyPeriodArt, this.aggAnyPeriodCode));
		// Sets index reconstruction
		domain.setIndexReconstruction(new IndexReconstruction(this.updStatisticsArt, this.indexReorgArt, Collections.emptyList()));
		// Sets re-execution condition
		domain.setReExecCondition(new ReExecutionCondition(this.recreateChangeBus, this.recreateTransfer, this.recreateLeaveSya));
		return domain;
	}

}
