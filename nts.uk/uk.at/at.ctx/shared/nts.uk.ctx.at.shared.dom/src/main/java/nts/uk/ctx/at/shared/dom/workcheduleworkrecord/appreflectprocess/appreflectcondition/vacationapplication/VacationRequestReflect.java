package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * refactor4 refactor 4
 * 休暇申請の反映
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.休暇系申請.休暇申請
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VacationRequestReflect extends AggregateRoot {
    private String companyId;

}
