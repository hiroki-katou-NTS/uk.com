package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor4 refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.残業・休日出勤
 * その他項目の反映
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OthersReflect extends DomainObject {
    /**
     * 乖離理由を反映する
     */
    private NotUseAtr reflectDivergentReasonAtr;

//    private NotUseAtr reflectOptionalItemsAtr;

    /**
     * 加給時間を反映する
     */
    private NotUseAtr reflectPaytimeAtr;
}
