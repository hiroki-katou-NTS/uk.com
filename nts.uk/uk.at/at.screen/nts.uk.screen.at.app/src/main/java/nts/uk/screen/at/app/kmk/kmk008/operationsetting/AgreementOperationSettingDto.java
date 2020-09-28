package nts.uk.screen.at.app.kmk.kmk008.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.standardtime.enums.StartingMonthType;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementOperationSettingDto {

    /** ３６協定起算月 **/
    private StartingMonthType startingMonth;

    /** 締め日 **/
    private ClosureDate closureDate;

    /** 特別条項申請を使用する **/
    private boolean specicalConditionApplicationUse;

    /** 年間の特別条項申請を使用する **/
    private boolean yearSpecicalConditionApplicationUse;

    public static AgreementOperationSettingDto setData(Optional<AgreementOperationSetting> data){

        return data.map(setting -> new AgreementOperationSettingDto(
                setting.getStartingMonth(),
                setting.getClosureDate(),
                setting.isSpecicalConditionApplicationUse(),
                setting.isYearSpecicalConditionApplicationUse()
        )).orElseGet(AgreementOperationSettingDto::new);
    }
}
