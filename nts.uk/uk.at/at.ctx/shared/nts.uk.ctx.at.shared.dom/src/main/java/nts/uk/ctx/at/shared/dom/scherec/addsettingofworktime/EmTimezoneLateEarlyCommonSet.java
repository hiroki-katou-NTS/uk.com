package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).就業時間の加算設定.遅刻早退を就業時間から控除する
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmTimezoneLateEarlyCommonSet {

    // 就業時間から控除する
    private boolean deduct;

    // 申請により取り消した場合も控除する
    private boolean deductByApp;

}
