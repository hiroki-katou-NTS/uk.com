package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KfnmtProcessExecutionSetting.
 */
@Entity
@Table(name = "KFNMT_PROC_EXEC_SETTING")
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtProcessExecutionSetting extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kfnmt proc exec set PK. */
	/* 主キー */
	@EmbeddedId
	public KfnmtProcessExecutionSettingPK kfnmtProcExecSetPK;

	/** The exclus ver. */
	@Version
	@Column(name = "EXCLUS_VER")
	private Long exclusVer;

	/** The Contract Code. */
	@Column(name = "CONTRACT_CD")
	public String contractCode;

	/**
	 * The per schedule cls. 個人スケジュール作成
	 */
	@Column(name = "PER_SCHEDULE_CLS")
	public int perScheduleCls;

	/**
	 * The target month. 対象月
	 */
	@Column(name = "TARGET_MONTH")
	public int targetMonth;

	/**
	 * The target date. 対象日
	 */
	@Column(name = "TARGET_DATE")
	public Integer targetDate;

	/**
	 * The creation period. 作成期間
	 */
	@Column(name = "CREATION_PERIOD")
	public Integer creationPeriod;

	/**
	 * The designated year. 指定年
	 */
	@Column(name = "DESIGNATED_YEAR")
	public Integer designatedYear;

	/**
	 * The start month day. 指定開始月日
	 */
	@Column(name = "START_MONTHDAY")
	public Integer startMonthDay;

	/**
	 * The end month day. 指定終了月日
	 */
	@Column(name = "END_MONTHDAY")
	public Integer endMonthDay;

	/**
	 * The create employee. 新入社員を作成する
	 */
	@Column(name = "CRE_NEW_SYA_SCHED")
	public int createEmployee;

	/**
	 * The daily perf cls. 日別実績の作成・計算
	 */
	@Column(name = "DAILY_PERF_CLS")
	public int dailyPerfCls;

	/**
	 * The daily perf item. 作成・計算項目
	 */
	@Column(name = "DAILY_PERF_ITEM")
	public int dailyPerfItem;

	/**
	 * The mid join employee. 途中入社は入社日からにする _> 新入社員を再作成する
	 */
	@Column(name = "CRE_NEW_SYA_DAI")
	public int midJoinEmployee;

	/**
	 * The reflect result cls. 承認結果反映
	 */
	@Column(name = "REFLECT_RS_CLS")
	public int reflectResultCls;

	/**
	 * The monthly agg cls. 月別集計
	 */
	@Column(name = "MONTHLY_AGG_CLS")
	public int monthlyAggCls;

	/**
	 * The app route update atr. 承認ルート更新（日次）.承認ルート更新区分
	 */
	@Column(name = "APP_ROUTE_UPDATE_ATR_DAI")
	public int appRouteUpdateAtr;

	/**
	 * The create new emp. 承認ルート更新（日次）.新入社員を作成する
	 */
	@Column(name = "CRE_NEW_SYA_APP")
	public Integer createNewEmp;

	/**
	 * The app route update atr mon. 承認ルート更新（月次）
	 */
	@Column(name = "APP_ROUTE_UPDATE_ATR_MON")
	public int appRouteUpdateAtrMon;

	/**
	 * The alarm atr. 承認ルート更新（月次）
	 */
	@Column(name = "ALARM_ATR")
	public int alarmAtr;

	/**
	 * The alarm code. 承認ルート更新（月次）
	 */
	@Column(name = "ALARM_CODE")
	public String alarmCode;

	/**
	 * The mail principal. メールを送信する(本人)
	 */
	@Column(name = "MAIL_PRINCIPAL")
	public Integer mailPrincipal;

	/**
	 * The mail administrator. メールを送信する(管理者)
	 */
	@Column(name = "MAIL_ADMINISTRATOR")
	public Integer mailAdministrator;

	/** The display tp principal. */
	@Column(name = "DISPLAY_TP_PRINCIPAL")
	public Integer displayTpPrincipal;

	/** The display tp admin. */
	@Column(name = "DISPLAY_TP_ADMIN")
	public Integer displayTpAdmin;

	/** 
	 * The ext output art. 
	 * 外部出力区分
	 **/
	@Column(name = "EXT_OUTPUT_ART")
	public int extOutputArt;

	/** 
	 * The ext acceptance art. 
	 * 外部受入区分
	 **/
	@Column(name = "EXT_ACCEPTANCE_ART")
	public int extAcceptanceArt;

	/** 
	 * The data storage art. 
	 * データの保存区分 
	 **/
	@Column(name = "DATA_STORAGE_ART")
	public int dataStorageArt;

	/** 
	 * The data storage code. 
	 * パターンコード
	 **/
	@Column(name = "DATA_STORAGE_CODE")
	public String dataStorageCode;

	/** 
	 * The data deletion art. 
	 * データの削除
	 **/
	@Column(name = "DATA_DELETION_ART")
	public int dataDeletionArt;

	/** 
	 * The data deletion code. 
	 * パターンコード
	 **/
	@Column(name = "DATA_DELETION_CODE")
	public String dataDeletionCode;

	/** 
	 * The agg any period art. 
	 * 使用区分
	 **/
	@Column(name = "AGG_ANY_PERIOD_ART")
	public int aggAnyPeriodArt;

	/** 
	 * The agg any period code. 
	 **/
	@Column(name = "AGG_ANY_PERIOD_CODE")
	public String aggAnyPeriodCode;

	/**
	 * The recre change bus. 勤務種別変更者を再作成
	 */
	@Column(name = "RECRE_CHANGE_BUS")
	public int recreateWorkType;

	/**
	 * The recreate transfer. 異動者を再作成する
	 */
	@Column(name = "RECRE_CHANGE_WKP")
	public int recreateTransfer;

	/** The recre leave sya. 
	 * 休職者・休業者を再作成				
	 **/
	@Column(name = "RECRE_LEAVE_SYA")
	public int recreLeaveSya;

	/** 
	 * The index reorg art. 
	 * 使用区分
	 **/
	@Column(name = "INDEX_REORG_ART")
	public int indexReorgArt;

	/** 
	 * The upd statistics art. 
	 * 統計情報を更新する
	 **/
	@Column(name = "UPD_STATISTICS_ART")
	public int updStatisticsArt;

	/** The cloud cre flag. */
	@Column(name = "CLOUD_CRE_FLAG")
	public int cloudCreFlag;

	/** The proc exec. */
	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "EXEC_ITEM_CD", referencedColumnName = "EXEC_ITEM_CD", insertable = false, updatable = false) })
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

	public KfnmtProcessExecutionSetting(KfnmtProcessExecutionSettingPK kfnmtProcExecSetPK, Long exclusVer,
			String contractCode, int perScheduleCls, int targetMonth, Integer targetDate, Integer creationPeriod,
			Integer designatedYear, Integer startMonthDay, Integer endMonthDay, int createEmployee, int dailyPerfCls,
			int dailyPerfItem, int midJoinEmployee, int reflectResultCls, int monthlyAggCls, int appRouteUpdateAtr,
			Integer createNewEmp, int appRouteUpdateAtrMon, int alarmAtr, String alarmCode, Integer mailPrincipal,
			Integer mailAdministrator, Integer displayTpPrincipal, Integer displayTpAdmin, int extOutputArt,
			int extAcceptanceArt, int dataStorageArt, String dataStorageCode, int dataDeletionArt,
			String dataDeletionCode, int aggAnyPeriodArt, String aggAnyPeriodCode, int recreateWorkType,
			int recreateTransfer, int recreLeaveSya, int indexReorgArt, int updStatisticsArt, int cloudCreFlag) {
		super();
		this.kfnmtProcExecSetPK = kfnmtProcExecSetPK;
		this.exclusVer = exclusVer;
		this.contractCode = contractCode;
		this.perScheduleCls = perScheduleCls;
		this.targetMonth = targetMonth;
		this.targetDate = targetDate;
		this.creationPeriod = creationPeriod;
		this.designatedYear = designatedYear;
		this.startMonthDay = startMonthDay;
		this.endMonthDay = endMonthDay;
		this.createEmployee = createEmployee;
		this.dailyPerfCls = dailyPerfCls;
		this.dailyPerfItem = dailyPerfItem;
		this.midJoinEmployee = midJoinEmployee;
		this.reflectResultCls = reflectResultCls;
		this.monthlyAggCls = monthlyAggCls;
		this.appRouteUpdateAtr = appRouteUpdateAtr;
		this.createNewEmp = createNewEmp;
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
		this.recreateWorkType = recreateWorkType;
		this.recreateTransfer = recreateTransfer;
		this.recreLeaveSya = recreLeaveSya;
		this.indexReorgArt = indexReorgArt;
		this.updStatisticsArt = updStatisticsArt;
		this.cloudCreFlag = cloudCreFlag;
	}
	

}
