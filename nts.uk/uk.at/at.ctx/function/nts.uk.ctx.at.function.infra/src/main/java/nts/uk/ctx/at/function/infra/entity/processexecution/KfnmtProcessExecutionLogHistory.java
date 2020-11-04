package nts.uk.ctx.at.function.infra.entity.processexecution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EachProcessPeriod;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogHistory;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Entity UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.更新処理自動実行ログ.更新処理自動実行ログ履歴
 */
@Entity
@Data
@Table(name = "KFNMT_PROC_EXEC_LOG_HIST")
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtProcessExecutionLogHistory extends UkJpaEntity implements ProcessExecutionLogHistory.MementoGetter, ProcessExecutionLogHistory.MementoSetter, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The exclus ver.
     */
    @Version
    @Column(name = "EXCLUS_VER")
    private Long exclusVer;

    /**
     * The Contract Code.
     */
    @Column(name = "CONTRACT_CD")
    public String contractCode;

    /* 主キー */
    @EmbeddedId
    public KfnmtProcessExecutionLogHistoryPK kfnmtProcExecLogHstPK;

    /* 全体のシステムエラー状態*/
    @Column(name = "ERROR_SYSTEM")
    public Integer errorSystem;

    /* 全体の業務エラー状態*/
    @Column(name = "ERROR_BUSINESS")
    public Integer errorBusiness;

    /* 全体の終了状態 */
    @Column(name = "OVERALL_STATUS")
    public Integer overallStatus;

    /* 前回実行日時 */
    @Column(name = "LAST_EXEC_DATETIME")
    public GeneralDateTime lastExecDateTime;

    /* 前回終了日時*/
    @Column(name = "LAST_END_EXEC_DATETIME")
    public GeneralDateTime lastEndExecDateTime;

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

    /* 承認結果反映 */
    @Column(name = "RFL_APPR_START")
    public GeneralDate reflectApprovalResultStart;

    /* 承認結果反映 */
    @Column(name = "RFL_APPR_END")
    public GeneralDate reflectApprovalResultEnd;

    /*強制終了の原因*/
    @Column(name = "ERROR_DETAIL")
    public Integer overallError;

    /* 各処理の終了状態 */
    @OneToMany(mappedBy = "procExecLogHistItem", cascade = CascadeType.ALL)
    @JoinTable(name = "KFNMT_EXEC_TASK_LOG")
    public List<KfnmtExecutionTaskLog> taskLogList;

    @Override
    protected Object getKey() {
        if (this.kfnmtProcExecLogHstPK != null) {
            return this.kfnmtProcExecLogHstPK;
        }
        return null;
    }

    @Override
    public Boolean getErrorSystem() {
        return this.errorSystem == 1;
    }

    @Override
    public void setErrorSystem(Boolean errorSystem) {
        this.errorSystem = errorSystem ? 1 : 0;
    }

    @Override
    public Boolean getErrorBusiness() {
        return this.errorBusiness == 1;
    }

    @Override
    public void setErrorBusiness(Boolean errorBusiness) {
        this.errorBusiness = errorBusiness ? 1 : 0;
    }

    @Override
    public List<ExecutionTaskLog> getTaskLogList() {
        return this.taskLogList.stream()
                .map(KfnmtExecutionTaskLog::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void setTaskLogList(List<ExecutionTaskLog> taskLogList) {
        this.taskLogList = KfnmtExecutionTaskLog.toListEntity(
                this.kfnmtProcExecLogHstPK.companyId,
                this.kfnmtProcExecLogHstPK.execItemCd,
                this.kfnmtProcExecLogHstPK.execId,
                taskLogList
        );
    }

    @Override
    public String getExecItemCd() {
        return this.kfnmtProcExecLogHstPK.execItemCd;
    }

    @Override
    public void setExecItemCd(String execItemCd) {
        this.kfnmtProcExecLogHstPK.execItemCd = execItemCd;
    }

    @Override
    public String getCompanyId() {
        return this.kfnmtProcExecLogHstPK.companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.kfnmtProcExecLogHstPK.companyId = companyId;
    }

    @Override
    public String getExecId() {
        return this.kfnmtProcExecLogHstPK.execId;
    }

    @Override
    public void setExecId(String execId) {
        this.kfnmtProcExecLogHstPK.execId = execId;
    }

    @Override
    public EachProcessPeriod getEachProcPeriod() {
        return new EachProcessPeriod(
                new DatePeriod(this.schCreateStart, this.schCreateEnd),
                new DatePeriod(this.dailyCreateStart, this.dailyCreateEnd),
                new DatePeriod(this.dailyCalcStart, this.dailyCalcEnd),
                new DatePeriod(this.reflectApprovalResultStart, this.reflectApprovalResultEnd)
        );
    }

    @Override
    public void setEachProcPeriod(EachProcessPeriod eachProcPeriod) {
        Optional<DatePeriod> scheduleCreationPeriod = eachProcPeriod.getScheduleCreationPeriod();
        Optional<DatePeriod> dailyCreationPeriod = eachProcPeriod.getDailyCreationPeriod();
        Optional<DatePeriod> dailyCalcPeriod = eachProcPeriod.getDailyCalcPeriod();
        Optional<DatePeriod> reflectApprovalResult = eachProcPeriod.getReflectApprovalResult();

        this.schCreateStart = scheduleCreationPeriod.map(DatePeriod::start).orElse(null);
        this.schCreateEnd = scheduleCreationPeriod.map(DatePeriod::end).orElse(null);

        this.dailyCreateStart = dailyCreationPeriod.map(DatePeriod::start).orElse(null);
        this.dailyCreateEnd = dailyCreationPeriod.map(DatePeriod::end).orElse(null);

        this.dailyCalcStart = dailyCalcPeriod.map(DatePeriod::start).orElse(null);
        this.dailyCalcEnd = dailyCalcPeriod.map(DatePeriod::end).orElse(null);

        this.reflectApprovalResultStart = reflectApprovalResult.map(DatePeriod::start).orElse(null);
        this.reflectApprovalResultEnd = reflectApprovalResult.map(DatePeriod::end).orElse(null);
    }
}

