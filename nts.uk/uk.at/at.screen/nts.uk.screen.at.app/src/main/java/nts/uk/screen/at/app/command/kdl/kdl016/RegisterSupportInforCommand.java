package nts.uk.screen.at.app.command.kdl.kdl016;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;

import java.util.List;

@Data
@AllArgsConstructor
public class RegisterSupportInforCommand {
    /** 社員リスト */
    private List<String> employeeIds;
    /** 応援先ドロップダウンリスト */
    private String supportDestinationId;

    private int orgUnit;

    /** 応援種類 */
    private int supportType;
    /** 応援時間帯 */
    private TimeSpanForCalcDto supportTimeSpan;
    /** 応援期間 */
    private String supportPeriodStart;
    private String supportPeriodEnd;
}
