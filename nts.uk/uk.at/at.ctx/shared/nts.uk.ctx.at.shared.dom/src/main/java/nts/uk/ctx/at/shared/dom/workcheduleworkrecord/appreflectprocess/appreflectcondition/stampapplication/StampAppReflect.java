package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4 refactor4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.打刻申請
 * 打刻申請の反映
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StampAppReflect extends AggregateRoot {
    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 出退勤を反映する
     */
    private NotUseAtr workReflectAtr;

    /**
     * 臨時出退勤を反映する
     */
    private NotUseAtr extraWorkReflectAtr;

    /**
     * 外出時間帯を反映する
     */
    private NotUseAtr goOutReflectAtr;

    /**
     * 育児時間帯を反映する
     */
    private NotUseAtr childCareReflectAtr;

    /**
     * 応援開始、終了を反映する
     */
    private NotUseAtr supportReflectAtr;

    /**
     * 介護時間帯を反映する
     */
    private NotUseAtr careReflectAtr;

    /**
     * 休憩時間帯を反映する
     */
    private NotUseAtr breakReflectAtr;

    public static StampAppReflect create(String companyId, int workReflectAtr, int extraWorkReflectAtr, int goOutReflectAtr, int childCareReflectAtr, int supportReflectAtr, int careReflectAtr, int breakReflectAtr) {
        return new StampAppReflect(
                companyId,
                EnumAdaptor.valueOf(workReflectAtr, NotUseAtr.class),
                EnumAdaptor.valueOf(extraWorkReflectAtr, NotUseAtr.class),
                EnumAdaptor.valueOf(goOutReflectAtr, NotUseAtr.class),
                EnumAdaptor.valueOf(childCareReflectAtr, NotUseAtr.class),
                EnumAdaptor.valueOf(supportReflectAtr, NotUseAtr.class),
                EnumAdaptor.valueOf(careReflectAtr, NotUseAtr.class),
                EnumAdaptor.valueOf(breakReflectAtr, NotUseAtr.class)
        );
    }
}
