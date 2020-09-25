package nts.uk.screen.at.app.kmk.kmk008.employment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.standardtime.enums.LaborSystemtAtr;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementTimeOfEmploymentDto {

    // 労働制 3
    private LaborSystemtAtr laborSystemAtr;

    // 	３６協定基本設定
    private BasicAgreementSetting basicAgreementSetting;

    public static AgreementTimeOfEmploymentDto setData(Optional<AgreementTimeOfEmployment> data){
        if (!data.isPresent()){
            return new AgreementTimeOfEmploymentDto();
        }
        return data.map(x -> new AgreementTimeOfEmploymentDto(
                x.getLaborSystemAtr(),
                x.getSetting()
        )).orElseGet(AgreementTimeOfEmploymentDto::new);
    }
}
