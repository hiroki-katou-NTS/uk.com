package nts.uk.screen.at.app.kmk.kmk008.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementTimeOfCompanyDto {

    // 労働制 2
    private LaborSystemtAtr laborSystemAtr;

    //３６協定基本設定 3
    private BasicAgreementSetting basicAgreementSetting;

    public static AgreementTimeOfCompanyDto setData(Optional<AgreementTimeOfCompany> data){
        if (!data.isPresent()){
            return new AgreementTimeOfCompanyDto();
        }
        return data.map(x -> new AgreementTimeOfCompanyDto(
                x.getLaborSystemAtr(),
                x.getSetting()
        )).orElseGet(AgreementTimeOfCompanyDto::new);
    }
}
