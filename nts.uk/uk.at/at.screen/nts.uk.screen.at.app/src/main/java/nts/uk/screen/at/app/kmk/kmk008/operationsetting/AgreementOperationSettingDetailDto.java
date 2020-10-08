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
public class AgreementOperationSettingDetailDto {

    /** ３６協定起算月 **/
    private int startingMonth;

    /** 締め日 **/
    private int closureDate;

    /** 特別条項申請を使用する **/
    private boolean specialConditionApplicationUse;

    /** 年間の特別条項申請を使用する **/
    private boolean yearSpecicalConditionApplicationUse;

}
