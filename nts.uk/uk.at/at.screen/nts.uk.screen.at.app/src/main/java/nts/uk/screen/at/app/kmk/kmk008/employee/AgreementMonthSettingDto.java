package nts.uk.screen.at.app.kmk.kmk008.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementMonthSettingDto {

    /** 年月 */
    private int yearMonthValue;

    /** １ヶ月時間 */
    private int errorOneMonth;

    private int alarmOneMonth;

    public static AgreementMonthSettingDto setData(Optional<AgreementMonthSetting> data){

        return data.map(setting -> new AgreementMonthSettingDto(
                setting.getYearMonthValue().v(),
                setting.getOneMonthTime().getError().v(),
                setting.getOneMonthTime().getAlarm().v()
        )).orElseGet(AgreementMonthSettingDto::new);
    }
}
