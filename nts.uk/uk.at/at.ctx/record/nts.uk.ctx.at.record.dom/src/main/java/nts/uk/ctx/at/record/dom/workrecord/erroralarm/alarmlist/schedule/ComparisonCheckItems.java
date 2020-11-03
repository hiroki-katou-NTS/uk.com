package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;

import java.util.Optional;

/**
 * Class: 対比チェック項目
 *
 * @author Thanh.LNP
 */
@Getter
@AllArgsConstructor
public class ComparisonCheckItems {
    /**
     * チェック対象
     */
    private Optional<BonusPaySettingCode> checkTarget;

    /**
     * 対比チェック対象
     */
    private Optional<ContrastType> contrastType;

    /**
     * 作成する
     *
     * @param checkTarget  Optional<チェック対象>
     * @param contrastType Optional<対比チェック対象>
     */
    public static ComparisonCheckItems create(Optional<BonusPaySettingCode> checkTarget, Optional<ContrastType> contrastType) {

        return new ComparisonCheckItems(checkTarget, contrastType);
    }
}
