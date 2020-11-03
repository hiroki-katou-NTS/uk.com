package nts.uk.screen.at.app.kmk.kmk008.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.StartingMonthType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreementOperationSettingDto {

    /** ３６協定起算月 **/
    private List<EnumConstant> startingMonthEnum;

    /** 締め日 **/
    private List<EnumConstant> closureDateEnum;

    private AgreementOperationSettingDetailDto agreementOperationSettingDetailDto;

}
