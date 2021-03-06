package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
import nts.uk.ctx.at.function.dom.processexecution.ReflectionApprovalResult;
import nts.uk.ctx.at.function.dom.processexecution.SaveData;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceCreation;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.CreationPeriod;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreation;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreationPeriod;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KfnmtProcessExecutionSetting.
 */
@Data
@Entity
@Table(name = "KFNMT_AUTOEXEC_SETTEING")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class KfnmtProcessExecutionSetting extends ContractUkJpaEntity implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	public static final long serialVersionUID = 1L;

//	/**
//	 * Column ?????????????????????
//	 */
//	@Version
//	@Column(name = "EXCLUS_VER")
//	public long version;

	/**
	 * The primary key
	 */
	@EmbeddedId
	public KfnmtProcessExecutionSettingPK kfnmtProcExecSetPK;

	/**
	 * The personal schedule creation classification.<br>
	 * ????????????????????????????????????
	 */
	@Column(name = "PER_SCHEDULE_CLS")
	public int perScheduleCls;

	/**
	 * The target month.<br>
	 * ?????????
	 */
	@Column(name = "TARGET_MONTH")
	public int targetMonth;

	/**
	 * The target date.<br>
	 * ?????????
	 */
	@Column(name = "TARGET_DATE")
	public Integer targetDate;

	/**
	 * The creation period.<br>
	 * ????????????
	 */
	@Column(name = "CREATION_PERIOD")
	public Integer creationPeriod;

	/**
	 * The designated year.<br>
	 * ?????????
	 */
	@Column(name = "DESIGNATED_YEAR")
	public Integer designatedYear;

	/**
	 * The start month day.<br>
	 * ??????????????????
	 */
	@Column(name = "START_MONTHDAY")
	public Integer startMonthDay;

	/**
	 * The end month day.<br>
	 * ??????????????????
	 */
	@Column(name = "END_MONTHDAY")
	public Integer endMonthDay;

	/**
	 * The create new employee schedule.<br>
	 * ???????????????????????????
	 */
	@Column(name = "CRE_NEW_SYA_SCHED")
	public int createNewEmpSched;

	/**
	 * The daily perf classification.<br>
	 * ????????????????????????????????????
	 */
	@Column(name = "DAILY_PERF_CLS")
	public int dailyPerfCls;

	/**
	 * The daily perf item.<br>
	 * ?????????????????????
	 */
	@Column(name = "DAILY_PERF_ITEM")
	public int dailyPerfItem;

	/**
	 * The create new employee daily perf.<br>
	 * ????????????????????????????????????
	 */
	@Column(name = "CRE_NEW_SYA_DAI")
	public int createNewEmpDailyPerf;

	/**
	 * The reflect result classification.<br>
	 * ??????????????????
	 */
	@Column(name = "REFLECT_RS_CLS")
	public int reflectResultCls;

	/**
	 * The monthly agg classification.<br>
	 * ????????????
	 */
	@Column(name = "MONTHLY_AGG_CLS")
	public int monthlyAggCls;

	/**
	 * The app route update attribute.<br>
	 * ???????????????????????????
	 */
	@Column(name = "APP_ROUTE_UPDATE_ATR_DAI")
	public int appRouteUpdateAtr;

	/**
	 * The create new emp app.<br>
	 * ???????????????????????????
	 */
	@Column(name = "CRE_NEW_SYA_APP")
	public Integer createNewEmpApp;

	/**
	 * The app route update attribute monthly.<br>
	 * ?????????????????????????????????
	 */
	@Column(name = "APP_ROUTE_UPDATE_ATR_MON")
	public int appRouteUpdateAtrMon;

	/**
	 * The alarm attribute.<br>
	 * ????????????????????????
	 */
	@Column(name = "ALARM_ATR")
	public int alarmAtr;

	/**
	 * The alarm code.<br>
	 * ?????????
	 */
	@Column(name = "ALARM_CODE")
	public String alarmCode;

	/**
	 * The mail principal.<br>
	 * ????????????????????????(??????)
	 */
	@Column(name = "MAIL_PRINCIPAL")
	public Integer mailPrincipal;

	/**
	 * The mail administrator.<br>
	 * ????????????????????????(?????????)
	 */
	@Column(name = "MAIL_ADMINISTRATOR")
	public Integer mailAdministrator;

	/**
	 * The display tp principal.<br>
	 * ???????????????????????????(??????)
	 */
	@Column(name = "DISPLAY_TP_PRINCIPAL")
	public Integer displayTpPrincipal;

	/**
	 * The display tp admin.<br>
	 * ????????????????????????(?????????)
	 */
	@Column(name = "DISPLAY_TP_ADMIN")
	public Integer displayTpAdmin;

	/**
	 * The ext output attribute.<br>
	 * ??????????????????
	 **/
	@Column(name = "EXT_OUTPUT_ART")
	public int extOutputArt;

	/**
	 * The ext acceptance attribute.<br>
	 * ??????????????????
	 **/
	@Column(name = "EXT_ACCEPTANCE_ART")
	public int extAcceptanceArt;

	/**
	 * The data storage attribute.<br>
	 * ????????????????????????
	 **/
	@Column(name = "DATA_STORAGE_ART")
	public int dataStorageArt;

	/**
	 * The data storage code.<br>
	 * ?????????????????????
	 **/
	@Column(name = "DATA_STORAGE_CODE")
	public String dataStorageCode;

	/**
	 * The data deletion attribute.<br>
	 * ????????????????????????
	 **/
	@Column(name = "DATA_DELETION_ART")
	public int dataDeletionArt;

	/**
	 * The data deletion code.<br>
	 * ?????????????????????
	 **/
	@Column(name = "DATA_DELETION_CODE")
	public String dataDeletionCode;

	/**
	 * The agg any period attribute.<br>
	 * ????????????????????????????????????
	 **/
	@Column(name = "AGG_ANY_PERIOD_ART")
	public int aggAnyPeriodArt;

	/**
	 * The agg any period code.<br>
	 * ????????????????????????
	 **/
	@Column(name = "AGG_ANY_PERIOD_CODE")
	public String aggAnyPeriodCode;

	/**
	 * The recreate change bus.<br>
	 * ?????????????????????????????????
	 */
	@Column(name = "RECRE_CHANGE_BUS")
	public int recreateChangeBus;

	/**
	 * The recreate transfer.<br>
	 * ???????????????????????????
	 */
	@Column(name = "RECRE_CHANGE_WKP")
	public int recreateTransfer;

	/**
	 * The recreate leave sya.<br>
	 * ?????????????????????????????????
	 **/
	@Column(name = "RECRE_LEAVE_SYA")
	public int recreateLeaveSya;

	/**
	 * The index reorg attribute.<br>
	 * ??????????????????????????????????????????
	 **/
	@Column(name = "INDEX_REORG_ART")
	public int indexReorgArt;

	/**
	 * The upd statistics attribute.<br>
	 * ???????????????????????????
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
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "execSetting", orphanRemoval = true)
	public List<KfnmtExecutionExternalAccept> externalAcceptList;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "execSetting", orphanRemoval = true)
	public List<KfnmtExecutionExternalOutput> externalOutputList;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "execSetting", orphanRemoval = true)
	public List<KfnmtExecutionIndexCategory> indexCategoryList;

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
		entity.alarmAtr = domain.getAlarmExtraction().getAlarmExtractionCls().value;
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
		entity.indexReorgArt = domain.getIndexReconstruction().getIndexReorgAttr().value;
		entity.updStatisticsArt = domain.getIndexReconstruction().getUpdateStatistics().value;
				
		entity.externalAcceptList = domain.getExternalAcceptance().getExtAcceptCondCodeList().stream()
				.map(data -> KfnmtExecutionExternalAccept.builder()
						.contractCode(contractCode)
						.pk(new KfnmtExecutionExternalAcceptPk(entity.kfnmtProcExecSetPK.companyId, execItemCode, data.v()))
						.execSetting(entity)
						.build())
				.collect(Collectors.toList());
		entity.externalOutputList = domain.getExternalOutput().getExtOutCondCodeList().stream()
				.map(data -> KfnmtExecutionExternalOutput.builder()
						.contractCode(contractCode)
						.pk(new KfnmtExecutionExternalOutputPk(entity.kfnmtProcExecSetPK.companyId, execItemCode, data.v()))
						.execSetting(entity)
						.build())
				.collect(Collectors.toList());
		entity.indexCategoryList = domain.getIndexReconstruction().getCategoryList().stream()
				.map(data -> KfnmtExecutionIndexCategory.builder()
						.contractCode(contractCode)
						.pk(new KfnmtExecutionIndexCategoryPk(entity.kfnmtProcExecSetPK.companyId, execItemCode, data.v()))
						.execSetting(entity)
						.build())
				.collect(Collectors.toList());
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
													  this.displayTpPrincipal));
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
		domain.setExternalAcceptance(new ExternalAcceptance(
				this.extAcceptanceArt, 
				this.externalAcceptList.stream().map(data -> data.pk.extAcceptCd).collect(Collectors.toList())));
		// Sets external output
		domain.setExternalOutput(new ExternalOutput(
				this.extOutputArt, 
				this.externalOutputList.stream().map(data -> data.pk.extOutputCd).collect(Collectors.toList())));
		// Sets aggregation any period
		domain.setAggrAnyPeriod(new AggregationAnyPeriod(this.aggAnyPeriodArt, this.aggAnyPeriodCode));
		// Sets index reconstruction
		domain.setIndexReconstruction(new IndexReconstruction(
				this.updStatisticsArt, 
				this.indexReorgArt, 
				this.indexCategoryList.stream().map(data -> data.pk.categoryNo).collect(Collectors.toList())));
		return domain;
	}

}
