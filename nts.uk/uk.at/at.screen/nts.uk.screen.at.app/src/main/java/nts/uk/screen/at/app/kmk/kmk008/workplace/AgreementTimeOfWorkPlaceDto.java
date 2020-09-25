package nts.uk.screen.at.app.kmk.kmk008.workplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.UpperAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementTimeOfWorkPlaceDto {

    // 基本設定
    private String basicSettingId;
    // 労働制 2
    private LaborSystemtAtr laborSystemAtr;
    // 上限規制
    private UpperAgreementSetting upperAgreementSetting;
    //３６協定基本設定 3
    private BasicAgreementSetting basicAgreementSetting;

    public static AgreementTimeOfWorkPlaceDto setData(Optional<AgreementTimeOfWorkPlace> data){
        if (!data.isPresent()){
            return new AgreementTimeOfWorkPlaceDto();
        }
        return data.map(x -> new AgreementTimeOfWorkPlaceDto(
                x.getBasicSettingId(),
                x.getLaborSystemAtr(),
                x.getUpperAgreementSetting(),
                x.getBasicAgreementSetting()
        )).orElseGet(AgreementTimeOfWorkPlaceDto::new);
    }
}
