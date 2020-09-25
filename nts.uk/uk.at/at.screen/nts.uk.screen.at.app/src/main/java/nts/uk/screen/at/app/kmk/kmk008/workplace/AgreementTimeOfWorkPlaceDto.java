package nts.uk.screen.at.app.kmk.kmk008.workplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.standardtime.enums.LaborSystemtAtr;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementTimeOfWorkPlaceDto {

    // 労働制 2
    private LaborSystemtAtr laborSystemAtr;

    //３６協定基本設定 3
    private BasicAgreementSetting basicAgreementSetting;

    public static AgreementTimeOfWorkPlaceDto setData(Optional<AgreementTimeOfWorkPlace> data){
        if (!data.isPresent()){
            return new AgreementTimeOfWorkPlaceDto();
        }
        return data.map(x -> new AgreementTimeOfWorkPlaceDto(
                x.getLaborSystemAtr(),
                x.getSetting()
        )).orElseGet(AgreementTimeOfWorkPlaceDto::new);
    }
}
