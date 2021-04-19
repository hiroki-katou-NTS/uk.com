package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
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
     * @param checkTarget  チェック対象
     * @param contrastType 対比チェック対象
     */
    public static ComparisonCheckItems create(String checkTarget, Integer contrastType) {
        Optional<BonusPaySettingCode> checkTargetOpt = checkTarget != null ? Optional.of(new BonusPaySettingCode(checkTarget)) : Optional.empty();

        Optional<ContrastType> contrastTypeOpt = contrastType != null ? Optional.of(EnumAdaptor.valueOf(contrastType, ContrastType.class)) : Optional.empty();

        return new ComparisonCheckItems(checkTargetOpt, contrastTypeOpt);
    }
}
