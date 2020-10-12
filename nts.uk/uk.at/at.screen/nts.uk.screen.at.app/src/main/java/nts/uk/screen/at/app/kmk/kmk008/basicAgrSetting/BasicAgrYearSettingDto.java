package nts.uk.screen.at.app.kmk.kmk008.basicAgrSetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicAgrYearSettingDto {

    //K4_5
    //３６協定基本設定.３６協定1年間.特例条項による上限.上限時間
    private int oneYearUpperLimit;

    public static BasicAgrYearSettingDto setData(BasicAgreementSetting data){
        if (data == null){
            return new BasicAgrYearSettingDto();
        }
        return new BasicAgrYearSettingDto(data.getOneYear().getSpecConditionLimit().getUpperLimit().v());
    }
}
