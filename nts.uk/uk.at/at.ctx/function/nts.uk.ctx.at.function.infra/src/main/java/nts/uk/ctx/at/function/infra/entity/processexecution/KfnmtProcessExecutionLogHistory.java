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
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EachProcessPeriod;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.OverallErrorDetail;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogHistory;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Entity
@Table(name="KFNMT_PROC_EXEC_LOG_HIST")
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtProcessExecutionLogHistory extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
    public KfnmtProcessExecutionLogHistoryPK kfnmtProcExecLogHstPK;
	
	/* 全体の終了状態 */
	@Column(name = "OVERALL_STATUS")
	public int overallStatus;
	
	/* 全体のエラー詳細 */
	@Column(name = "ERROR_DETAIL")
	public int errorDetail;
	
	/* 前回実行日時 */
	@Column(name = "LAST_EXEC_DATETIME")
	public GeneralDateTime prevExecDateTime;
	
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
	
	@OneToMany(mappedBy = "procExecLogHistItem", cascade = CascadeType.ALL)
    @JoinTable(name = "KFNMT_EXEC_TASK_LOG")
    private List<KfnmtExecutionTaskLog> taskLogList;
	
	@Override
	protected Object getKey() {
		return this.kfnmtProcExecLogHstPK;
	}
	
	public ProcessExecutionLogHistory toDomain() {
		List<ExecutionTaskLog> taskLogList =
				this.taskLogList.stream().map(x -> x.toDomain()).collect(Collectors.toList());
		return new ProcessExecutionLogHistory(new ExecutionCode(this.kfnmtProcExecLogHstPK.execItemCd),
				this.kfnmtProcExecLogHstPK.companyId,
				EnumAdaptor.valueOf(this.errorDetail, OverallErrorDetail.class),
				EnumAdaptor.valueOf(this.overallStatus, EndStatus.class),
				this.prevExecDateTime,
				new EachProcessPeriod(new DatePeriod(this.schCreateStart, this.schCreateEnd),
						new DatePeriod(this.dailyCreateStart, this.dailyCreateEnd),
						new DatePeriod(this.dailyCalcStart, this.dailyCalcEnd)),
				taskLogList,
				this.kfnmtProcExecLogHstPK.execId);
	}
	
	public static KfnmtProcessExecutionLogHistory toEntity(ProcessExecutionLogHistory domain) {
		return new KfnmtProcessExecutionLogHistory(
				new KfnmtProcessExecutionLogHistoryPK(domain.getCompanyId(), domain.getExecItemCd().v(), domain.getExecId()),
				domain.getOverallStatus() == null ? null : domain.getOverallStatus().value,
				domain.getOverallError() == null ? null : domain.getOverallError().value,
				domain.getLastExecDateTime(),
				domain.getEachProcPeriod() == null ? null : domain.getEachProcPeriod().getScheduleCreationPeriod().start(),
				domain.getEachProcPeriod() == null ? null : domain.getEachProcPeriod().getScheduleCreationPeriod().end(),
				domain.getEachProcPeriod() == null ? null : domain.getEachProcPeriod().getDailyCreationPeriod().start(),
				domain.getEachProcPeriod() == null ? null : domain.getEachProcPeriod().getDailyCreationPeriod().end(),
				domain.getEachProcPeriod() == null ? null : domain.getEachProcPeriod().getDailyCalcPeriod().start(),
				domain.getEachProcPeriod() == null ? null : domain.getEachProcPeriod().getDailyCalcPeriod().end(),
				null);
	}
}
