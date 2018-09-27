package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Entity
@Table(name="KFNMT_PROC_EXEC_SETTING")
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtProcessExecutionSetting extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
    public KfnmtProcessExecutionSettingPK kfnmtProcExecSetPK;
	
	/* 個人スケジュール作成 */
	@Column(name = "PER_SCHEDULE_CLS")
	public int perScheduleCls;
	
	/* 対象月 */
	@Column(name = "TARGET_MONTH")
	public int targetMonth;
	
	/* 対象日 */
	@Column(name = "TARGET_DATE")
	public Integer targetDate;
	
	/* 作成期間 */
	@Column(name = "CREATION_PERIOD")
	public Integer creationPeriod;
	
	/* 作成対象 */
	@Column(name = "CREATION_TARGET")
	public int creationTarget;
	
	/* 勤務種別変更者を再作成 */
	@Column(name = "RECREATE_WK_TYPE")
	public int recreateWorkType;
	
	/* 手修正を保護する */
	@Column(name = "MANUAL_CORRECTION")
	public int manualCorrection;
	
	/* 新入社員を作成する */
	@Column(name = "CREATE_EMPLOYEE")
	public int createEmployee;
	
	/* 異動者を再作成する */
	@Column(name = "RECREATE_TRANSFER")
	public int recreateTransfer;
	
	/* 日別実績の作成・計算 */
	@Column(name = "DAILY_PERF_CLS")
	public int dailyPerfCls;
	
	/* 作成・計算項目 */
	@Column(name = "DAILY_PERF_ITEM")
	public int dailyPerfItem;

	/* 途中入社は入社日からにする _> 新入社員を再作成する */
	@Column(name = "MID_JOIN_EMP")
	public int midJoinEmployee;
	
	/* 承認結果反映 */
	@Column(name = "REFLECT_RS_CLS")
	public int reflectResultCls;
	
	/* 月別集計 */
	@Column(name = "MONTHLY_AGG_CLS")
	public int monthlyAggCls;
	
	/* 更新処理の日別処理対象者区分.勤務種別変更者を再作成 */
	@Column(name = "RE_TYPE_CHANGE_PER")
	public int recreateTypeChangePerson;
	
	/* 更新処理の日別処理対象者区分.異動者を再作成する */
	@Column(name = "RE_TRANSFER")
	public int recreateTransfers;
	
	/* 承認ルート更新（日次）.承認ルート更新区分 */
	@Column(name = "APP_ROUTE_UPDATE_ATR_DAI")
	public int appRouteUpdateAtr;
	
	/* 承認ルート更新（日次）.新入社員を作成する*/
	@Column(name = "CREATE_NEW_EMP")
	public Integer createNewEmp;
	
	/* 承認ルート更新（月次） */
	@Column(name = "APP_ROUTE_UPDATE_ATR_MON")
	public int appRouteUpdateAtrMon;
	
	/* 承認ルート更新（月次） */
	@Column(name = "ALARM_ATR")
	public int alarmAtr;
	
	/* 承認ルート更新（月次） */
	@Column(name = "ALARM_CODE")
	public String alarmCode;
	
	/* メールを送信する(本人)*/
	@Column(name = "MAIL_PRINCIPAL")
	public Integer mailPrincipal;
	
	/* メールを送信する(管理者) */
	@Column(name = "MAIL_ADMINISTRATOR")
	public Integer mailAdministrator;
	
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="EXEC_ITEM_CD", referencedColumnName="EXEC_ITEM_CD", insertable = false, updatable = false)
	})
	public KfnmtProcessExecution procExec;
	
	@Override
	protected Object getKey() {
		return this.kfnmtProcExecSetPK;
	}

	public KfnmtProcessExecutionSetting(KfnmtProcessExecutionSettingPK kfnmtProcExecSetPK, int perScheduleCls,
			int targetMonth, Integer targetDate, Integer creationPeriod, int creationTarget, int recreateWorkType,
			int manualCorrection, int createEmployee, int recreateTransfer, int dailyPerfCls, int dailyPerfItem,
			int midJoinEmployee, int reflectResultCls, int monthlyAggCls, int recreateTypeChangePerson,
			int recreateTransfers, int appRouteUpdateAtr, Integer createNewEmp, int appRouteUpdateAtrMon, int alarmAtr,
			String alarmCode, Integer mailPrincipal, Integer mailAdministrator) {
		super();
		this.kfnmtProcExecSetPK = kfnmtProcExecSetPK;
		this.perScheduleCls = perScheduleCls;
		this.targetMonth = targetMonth;
		this.targetDate = targetDate;
		this.creationPeriod = creationPeriod;
		this.creationTarget = creationTarget;
		this.recreateWorkType = recreateWorkType;
		this.manualCorrection = manualCorrection;
		this.createEmployee = createEmployee;
		this.recreateTransfer = recreateTransfer;
		this.dailyPerfCls = dailyPerfCls;
		this.dailyPerfItem = dailyPerfItem;
		this.midJoinEmployee = midJoinEmployee;
		this.reflectResultCls = reflectResultCls;
		this.monthlyAggCls = monthlyAggCls;
		this.recreateTypeChangePerson = recreateTypeChangePerson;
		this.recreateTransfers = recreateTransfers;
		this.appRouteUpdateAtr = appRouteUpdateAtr;
		this.createNewEmp = createNewEmp;
		this.appRouteUpdateAtrMon = appRouteUpdateAtrMon;
		this.alarmAtr = alarmAtr;
		this.alarmCode = alarmCode;
		this.mailPrincipal = mailPrincipal;
		this.mailAdministrator = mailAdministrator;
		
	}

	

	
}
