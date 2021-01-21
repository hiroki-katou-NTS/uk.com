package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor4 refactor 4
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
