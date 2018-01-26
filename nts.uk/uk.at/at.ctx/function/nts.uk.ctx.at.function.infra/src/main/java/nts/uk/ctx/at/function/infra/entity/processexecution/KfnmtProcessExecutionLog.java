package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;
import java.util.List;
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
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EachProcessPeriod;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.OverallErrorDetail;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Entity
@Table(name="KFNMT_PROC_EXEC_LOG")
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtProcessExecutionLog extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
    public KfnmtProcessExecutionLogPK kfnmtProcExecLogPK;
	
	/* 現在の実行状態 */
	@Column(name = "CURRENT_STATUS")
	public Integer currentStatus;
	
	/* 全体の終了状態 */
	@Column(name = "OVERALL_STATUS")
	public Integer overallStatus;
	
	/* 全体のエラー詳細 */
	@Column(name = "ERROR_DETAIL")
	public Integer errorDetail;
	
	/* 前回実行日時 */
	@Column(name = "LAST_EXEC_DATETIME")
	public GeneralDateTime lastExecDateTime;
	
	/* スケジュール作成の期間 */
	@Column(name = "SCH_CREATE_START")
	public GeneralDate schCreateStart;
	
	/* スケジュール作成の期間 */
	@Column(name = "SCH_CREATE_END")
	public GeneralDate schCreateEnd;
	
	/* 日別作成の期間 */
	@Column(name = "DAILY_CREATE_START")
	public GeneralDate dailyCreateStart;
	
	/* 日別作成の期間 */
	@Column(name = "DAILY_CREATE_END")
	public GeneralDate dailyCreateEnd;
	
	/* 日別計算の期間 */
	@Column(name = "DAILY_CALC_START")
	public GeneralDate dailyCalcStart;
	
	/* 日別計算の期間 */
	@Column(name = "DAILY_CALC_END")
	public GeneralDate dailyCalcEnd;
	
	/* 前回実行日時（即時実行含めない） */
	@Column(name = "LAST_EXEC_DATETIME_EX")
	public GeneralDateTime prevExecDateTimeEx;
	
	@OneToMany(mappedBy = "procExecLogItem", cascade = CascadeType.ALL)
    @JoinTable(name = "KFNMT_EXEC_TASK_LOG")
    private List<KfnmtExecutionTaskLog> taskLogList;
	
	@Override
	protected Object getKey() {
		return this.kfnmtProcExecLogPK;
	}
	
	public ProcessExecutionLog toDomain() {
		List<ExecutionTaskLog> taskLogList =
				this.taskLogList.stream().map(x -> x.toDomain()).collect(Collectors.toList());
		return new ProcessExecutionLog(new ExecutionCode(this.kfnmtProcExecLogPK.execItemCd),
				this.kfnmtProcExecLogPK.companyId,
				this.errorDetail == null ? null : EnumAdaptor.valueOf(this.errorDetail, OverallErrorDetail.class),
				EnumAdaptor.valueOf(this.overallStatus, EndStatus.class),
				this.lastExecDateTime,
				new EachProcessPeriod(new DatePeriod(this.schCreateStart, this.schCreateEnd),
						new DatePeriod(this.dailyCreateStart, this.dailyCreateEnd),
						new DatePeriod(this.dailyCalcStart, this.dailyCalcEnd)),
				taskLogList,
				this.kfnmtProcExecLogPK.execId,
				EnumAdaptor.valueOf(this.currentStatus, CurrentExecutionStatus.class),
				this.prevExecDateTimeEx);
	}
	
	public static KfnmtProcessExecutionLog toEntity(ProcessExecutionLog domain) {
		List<KfnmtExecutionTaskLog> taskLogList =
				domain.getTaskLogList()
					.stream()
						.map(x -> KfnmtExecutionTaskLog.toEntity(domain.getCompanyId(),
																 domain.getExecItemCd().v(),
																 domain.getExecId(),
																 x))
								.collect(Collectors.toList());
		return new KfnmtProcessExecutionLog(
				new KfnmtProcessExecutionLogPK(domain.getCompanyId(), domain.getExecItemCd().v(), domain.getExecId()),
				domain.getCurrentStatus() == null ? null : domain.getCurrentStatus().value,
				domain.getOverallStatus() == null ? null : domain.getOverallStatus().value,
				domain.getOverallError() == null ? null : domain.getOverallError().value,
				domain.getLastExecDateTime(),
				domain.getEachProcPeriod() == null ? null : domain.getEachProcPeriod().getScheduleCreationPeriod().start(),
				domain.getEachProcPeriod() == null ? null : domain.getEachProcPeriod().getScheduleCreationPeriod().end(),
				domain.getEachProcPeriod() == null ? null : domain.getEachProcPeriod().getDailyCreationPeriod().start(),
				domain.getEachProcPeriod() == null ? null : domain.getEachProcPeriod().getDailyCreationPeriod().end(),
				domain.getEachProcPeriod() == null ? null : domain.getEachProcPeriod().getDailyCalcPeriod().start(),
				domain.getEachProcPeriod() == null ? null : domain.getEachProcPeriod().getDailyCalcPeriod().end(),
				domain.getLastExecDateTimeEx(),
				taskLogList);
	}

	public KfnmtProcessExecutionLog(Integer currentStatus, Integer overallStatus, Integer errorDetail,
			GeneralDateTime lastExecDateTime, GeneralDate schCreateStart, GeneralDate schCreateEnd,
			GeneralDate dailyCreateStart, GeneralDate dailyCreateEnd, GeneralDate dailyCalcStart,
			GeneralDate dailyCalcEnd, GeneralDateTime prevExecDateTimeEx, List<KfnmtExecutionTaskLog> taskLogList) {
		super();
		this.currentStatus = currentStatus;
		this.overallStatus = overallStatus;
		this.errorDetail = errorDetail;
		this.lastExecDateTime = lastExecDateTime;
		this.schCreateStart = schCreateStart;
		this.schCreateEnd = schCreateEnd;
		this.dailyCreateStart = dailyCreateStart;
		this.dailyCreateEnd = dailyCreateEnd;
		this.dailyCalcStart = dailyCalcStart;
		this.dailyCalcEnd = dailyCalcEnd;
		this.prevExecDateTimeEx = prevExecDateTimeEx;
		this.taskLogList = taskLogList;
	}
}
