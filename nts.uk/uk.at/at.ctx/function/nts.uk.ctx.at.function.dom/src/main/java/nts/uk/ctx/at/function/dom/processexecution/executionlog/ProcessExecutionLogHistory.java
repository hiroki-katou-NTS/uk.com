package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;

import java.util.List;
import java.util.Optional;

/**
 * Domain UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.更新処理自動実行ログ.更新処理自動実行ログ履歴
 */
@Getter
public class ProcessExecutionLogHistory extends AggregateRoot {

    /* コード */
    private ExecutionCode execItemCd;

    /* 会社ID */
    private String companyId;

    /* 実行ID */
    private String execId;

    /* 全体のシステムエラー状態*/
    private Optional<Boolean> errorSystem;

    /* 全体の業務エラー状態*/
    private Optional<Boolean> errorBusiness;

    /* 全体の終了状態 */
    private Optional<EndStatus> overallStatus;

    /* 前回実行日時 */
    private Optional<GeneralDateTime> lastExecDateTime;

    /* 前回終了日時*/
    private Optional<GeneralDateTime> lastEndExecDateTime;

    /* 各処理の期間 */
    private Optional<EachProcessPeriod> eachProcPeriod;

    /* 各処理の終了状態 */
    private List<ExecutionTaskLog> taskLogList;

    /*強制終了の原因*/
    private Optional<OverallErrorDetail> overallError;

    private ProcessExecutionLogHistory() {
    }

    public static ProcessExecutionLogHistory createFromMemento(MementoGetter memento) {
        ProcessExecutionLogHistory domain = new ProcessExecutionLogHistory();
        domain.getMemento(memento);
        return domain;
    }

    public void getMemento(MementoGetter memento) {
        this.execItemCd = new ExecutionCode(memento.getExecItemCd());
        this.companyId = memento.getCompanyId();
        this.execId = memento.getExecId();
        this.errorSystem = Optional.ofNullable(memento.getErrorSystem());
        this.errorBusiness = Optional.ofNullable(memento.getErrorBusiness());
        this.overallStatus = Optional.ofNullable(memento.getOverallStatus())
        		.map(data -> EnumAdaptor.valueOf(data, EndStatus.class));
        this.lastExecDateTime = Optional.ofNullable(memento.getLastExecDateTime());
        this.lastEndExecDateTime = Optional.ofNullable(memento.getLastEndExecDateTime());
        this.eachProcPeriod = Optional.ofNullable(memento.getEachProcPeriod());
        this.taskLogList = memento.getTaskLogList();
        this.overallError = Optional.ofNullable(memento.getOverallError())
        		.map(data -> EnumAdaptor.valueOf(data, OverallErrorDetail.class));
    }

    public void setMemento(MementoSetter memento) {
        memento.setExecItemCd(this.execItemCd.v());
        memento.setCompanyId(this.companyId);
        memento.setExecId(this.execId);
        memento.setErrorSystem(this.errorSystem.orElse(null));
        memento.setErrorBusiness(this.errorBusiness.orElse(null));
        memento.setOverallStatus(this.overallStatus.map(data -> data.value).orElse(null));
        memento.setLastExecDateTime(this.lastExecDateTime.orElse(null));
        memento.setLastEndExecDateTime(this.lastEndExecDateTime.orElse(null));
        memento.setEachProcPeriod(this.eachProcPeriod.orElse(null));
        memento.setTaskLogList(this.taskLogList);
        memento.setOverallError(this.overallError.map(data -> data.value).orElse(null));
    }

    public static interface MementoSetter {
        public void setExecItemCd(String execItemCd);

        public void setCompanyId(String companyId);

        public void setExecId(String execId);

        public void setErrorSystem(Boolean errorSystem);

        public void setErrorBusiness(Boolean errorBusiness);

        public void setOverallStatus(Integer overallStatus);

        public void setLastExecDateTime(GeneralDateTime lastExecDateTime);

        public void setLastEndExecDateTime(GeneralDateTime lastEndExecDateTime);

        public void setEachProcPeriod(EachProcessPeriod eachProcPeriod);

        public void setTaskLogList(List<ExecutionTaskLog> taskLogList);

        public void setOverallError(Integer overallError);
    }

    public static interface MementoGetter {
        public String getExecItemCd();

        public String getCompanyId();

        public String getExecId();

        public Boolean getErrorSystem();

        public Boolean getErrorBusiness();

        public Integer getOverallStatus();

        public GeneralDateTime getLastExecDateTime();

        public GeneralDateTime getLastEndExecDateTime();

        public EachProcessPeriod getEachProcPeriod();

        public List<ExecutionTaskLog> getTaskLogList();

        public Integer getOverallError();
    }
}
