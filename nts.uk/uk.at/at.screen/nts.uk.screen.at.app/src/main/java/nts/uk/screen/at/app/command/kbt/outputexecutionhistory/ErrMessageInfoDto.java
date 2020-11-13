package nts.uk.screen.at.app.command.kbt.outputexecutionhistory;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@Builder
public class ErrMessageInfoDto {

    /**
     * 実行社員ID
     */
    private String employeeID;

    /**
     * 就業計算と集計実行ログID
     */
    private String empCalAndSumExecLogID;
    /**
     * リソースID
     */
    private String resourceID;

    /**
     * 実行内容
     */
    private Integer executionContent;

    /**
     * 処理日
     */
    private GeneralDate disposalDay;

    /**
     * エラーメッセージ
     */
    private String messageError;
}
