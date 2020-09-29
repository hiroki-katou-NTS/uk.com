package nts.uk.screen.at.app.ksm003.find;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.standardtime.UpperAgreementSetting;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementTimeOfCompanyDto {

    //TODO có vẻ như đang update domain

    // 基本設定
    private String basicSettingId;
    // 労働制 2
    private LaborSystemtAtr laborSystemAtr;
    // 上限規制
    private UpperAgreementSetting upperAgreementSetting;
    //３６協定基本設定 3
    private BasicAgreementSetting basicAgreementSetting;

    public static AgreementTimeOfCompanyDto setData(Optional<AgreementTimeOfCompany> data){
        if (data != null){
            return new AgreementTimeOfCompanyDto();
        }
//        return data.map(x -> new AgreementTimeOfCompanyDto(
//                x.getBasicSettingId(),
//                x.getLaborSystemAtr(),
//                x.getUpperAgreementSetting(),
//                x.getBasicAgreementSetting()
//        )).orElseGet(AgreementTimeOfCompanyDto::new);
        // TODO wait to change domain from Nittsu
        return new AgreementTimeOfCompanyDto();
    }
}
