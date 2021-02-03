package nts.uk.screen.at.app.kaf021.query.c_d;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.kaf021.query.a.AgreementOperationSettingDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SpecialProvisionOfAgreementAppListDto {
    /**
     * 締め終了日
     */
    private GeneralDate startDate;
    /**
     * 締め開始日
     */
    private GeneralDate endDate;
    /**
     * 申請一覧
     */
    private List<ApplicationListDto> applications;

    private AgreementOperationSettingDto setting;
}
