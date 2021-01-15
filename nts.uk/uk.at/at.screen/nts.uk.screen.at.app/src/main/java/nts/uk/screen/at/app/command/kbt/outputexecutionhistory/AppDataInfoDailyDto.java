package nts.uk.screen.at.app.command.kbt.outputexecutionhistory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppDataInfoDailyDto {
    /**社員ID*/
    private String employeeId;

    /**実行ID*/
    private String	executionId;

    /**エラーメッセージ*/
    private String errorMessage;
}
