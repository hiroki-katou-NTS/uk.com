package nts.uk.screen.at.app.kmk.kmk008.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumConstant;

import java.util.List;

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
