package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

/*
UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).就業時間の加算設定.休暇取得時加算時間の参照先
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;

import java.util.Optional;

@NoArgsConstructor
@Getter
public class RefDesForAdditionalTakeLeave {

    // 会社一律加算時間
    private BreakdownTimeDay comUniformAdditionTime;
    // 参照先設定
    private VacationAdditionTimeRef referenceSet;
    // 個人別設定参照先
    private Optional<VacationSpecifiedTimeRefer> referIndividualSet;

    public RefDesForAdditionalTakeLeave(BreakdownTimeDay comUniformAdditionTime,
                                        int referenceSet,
                                        Integer referIndividualSet) {
        this.comUniformAdditionTime = comUniformAdditionTime;
        this.referenceSet = EnumAdaptor.valueOf(referenceSet, VacationAdditionTimeRef.class);
        this.referIndividualSet = referenceSet != VacationAdditionTimeRef.REFER_PERSONAL_SET.value ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(referIndividualSet, VacationSpecifiedTimeRefer.class));
    }

}
