package nts.uk.screen.at.app.command.kbt.outputexecutionhistory;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@Builder
public class AggrPeriodInforDto {
    /** 社員ID*/
    private String memberId;

    /** 任意期間集計実行ログID*/
    private String periodArrgLogId;

    /** リソースID*/
    private String resourceId;

    /** 処理日*/
    private GeneralDate processDay;

    /** エラーメッセージ*/
    private String errorMess;
}
