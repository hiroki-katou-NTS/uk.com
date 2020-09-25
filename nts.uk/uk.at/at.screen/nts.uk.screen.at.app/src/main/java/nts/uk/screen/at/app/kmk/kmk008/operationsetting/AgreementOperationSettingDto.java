package nts.uk.screen.at.app.kmk.kmk008.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.*;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementOperationSettingDto {

    /** ３６協定起算月 **/
    private StartingMonthType startingMonth;

    /** ３６協定超過上限回数 **/
    private TimeOverLimitType numberTimesOverLimitType;

    /** ３６協定締め日 **/
    private ClosingDateType closingDateType;

    /** ３６協定締め日区分 **/
    private ClosingDateAtr closingDateAtr;

    /** ３６協定対象設定 - 年間勤務表 **/
    private TargetSettingAtr yearlyWorkTableAtr;

    /** ３６協定対象設定 - アラームリスト **/
    private TargetSettingAtr alarmListAtr;

    public static AgreementOperationSettingDto setData(Optional<AgreementOperationSetting> data){

        return data.map(agreementOperationSetting -> new AgreementOperationSettingDto(
                agreementOperationSetting.getStartingMonth(),
                agreementOperationSetting.getNumberTimesOverLimitType(),
                agreementOperationSetting.getClosingDateType(),
                agreementOperationSetting.getClosingDateAtr(),
                agreementOperationSetting.getYearlyWorkTableAtr(),
                agreementOperationSetting.getAlarmListAtr()
        )).orElseGet(AgreementOperationSettingDto::new);
    }
}
