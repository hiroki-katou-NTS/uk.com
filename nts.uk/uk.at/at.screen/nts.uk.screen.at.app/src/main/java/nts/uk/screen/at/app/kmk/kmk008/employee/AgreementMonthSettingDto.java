package nts.uk.screen.at.app.kmk.kmk008.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementMonthSettingDto {

    /** 年月 */
    private YearMonth yearMonthValue;

    /** １ヶ月時間 */
    private OneMonthErrorAlarmTime oneMonthTime;

    public static AgreementMonthSettingDto setData(Optional<AgreementMonthSetting> data){

        return data.map(setting -> new AgreementMonthSettingDto(
                setting.getYearMonthValue(),
                setting.getOneMonthTime()
        )).orElseGet(AgreementMonthSettingDto::new);
    }
}
