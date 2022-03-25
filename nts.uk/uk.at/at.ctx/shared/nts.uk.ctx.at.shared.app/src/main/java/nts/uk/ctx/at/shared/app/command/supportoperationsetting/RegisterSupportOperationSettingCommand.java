package nts.uk.ctx.at.shared.app.command.supportoperationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterSupportOperationSettingCommand {
    /** 利用するか **/
    private int isUsed;

    /** 応援先が応援者を指定できるか **/
//    private boolean supportDestinationCanSpecifySupporter;

    /** 一日の最大応援回数 **/
    private Integer maxNumberOfSupportOfDay;
}
