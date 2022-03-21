package nts.uk.screen.at.app.kha;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupportOperationSettingDto {
    /** 利用するか **/
    private boolean isUsed;

    /** 応援先が応援者を指定できるか **/
    private boolean supportDestinationCanSpecifySupporter;

    /** 一日の最大応援回数 **/
    private Integer maxNumberOfSupportOfDay;
}
