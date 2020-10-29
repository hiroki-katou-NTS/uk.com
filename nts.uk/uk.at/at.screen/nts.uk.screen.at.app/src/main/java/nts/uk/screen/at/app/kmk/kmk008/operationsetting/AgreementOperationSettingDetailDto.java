package nts.uk.screen.at.app.kmk.kmk008.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementOperationSettingDetailDto {

    /** ３６協定起算月 **/
    private int startingMonth;

    /** 締め日 **/
    private int closureDate;

    /** 締め日 **/
    private boolean isLastDay;

    /** 特別条項申請を使用する **/
    private boolean specialConditionApplicationUse;

    /** 年間の特別条項申請を使用する **/
    private boolean yearSpecicalConditionApplicationUse;

}
