package nts.uk.screen.at.app.command.kbt.outputexecutionhistory;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;

@Data
@NoArgsConstructor
/** 出力するデータを取得する */
public class GetDataToOutputCommand {

    /** 開始日 */
    private GeneralDateTime startDate;

    /** 終了日 */
    private GeneralDateTime endDate;

    /** 社員名を出力するか */
    private Boolean isExportEmployeeName;
}
