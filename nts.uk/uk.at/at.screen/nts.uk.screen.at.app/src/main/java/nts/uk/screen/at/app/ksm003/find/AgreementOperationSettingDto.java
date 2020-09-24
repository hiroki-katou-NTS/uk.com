package nts.uk.screen.at.app.ksm003.find;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.StartingMonthType;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.standardtime.enums.ClosingDateAtr;
import nts.uk.ctx.at.shared.dom.standardtime.enums.ClosingDateType;
import nts.uk.ctx.at.shared.dom.standardtime.enums.TargetSettingAtr;
import nts.uk.ctx.at.shared.dom.standardtime.enums.TimeOverLimitType;

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

        // TODO wait to change domain from Nittsu
//        return data.map(agreementOperationSetting -> new AgreementOperationSettingDto(
//                agreementOperationSetting.getStartingMonth(),
//                agreementOperationSetting.getNumberTimesOverLimitType(),
//                agreementOperationSetting.getClosingDateType(),
//                agreementOperationSetting.getClosingDateAtr(),
//                agreementOperationSetting.getYearlyWorkTableAtr(),
//                agreementOperationSetting.getAlarmListAtr()
//        )).orElseGet(AgreementOperationSettingDto::new);
        return new AgreementOperationSettingDto();
    }
}
