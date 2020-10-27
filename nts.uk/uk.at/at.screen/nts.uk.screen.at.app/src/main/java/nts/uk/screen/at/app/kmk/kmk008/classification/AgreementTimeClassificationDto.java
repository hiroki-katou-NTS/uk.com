package nts.uk.screen.at.app.kmk.kmk008.classification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;


import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementTimeClassificationDto {

    // 会社ID 1
    private String companyId;

    // 分類コード
    private String classificationCode;

    // 労働制 3
    private LaborSystemtAtr laborSystemAtr;

    // 	３６協定基本設定
    private BasicAgreementSetting basicAgreementSetting;

    public static AgreementTimeClassificationDto setData(Optional<AgreementTimeOfClassification> data){
        if (!data.isPresent()){
            return new AgreementTimeClassificationDto();
        }
        return data.map(x -> new AgreementTimeClassificationDto(
                x.getCompanyId(),
                x.getClassificationCode().v(),
                x.getLaborSystemAtr(),
                x.getSetting()
        )).orElseGet(AgreementTimeClassificationDto::new);
    }
}
