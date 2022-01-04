package nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkDetails;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.List;
import java.util.Optional;

/**
 * 日毎の応援勤務データ
 */
@AllArgsConstructor
@Getter
public class SupportWorkDataOfDay {
    /**
     * 年月日
     */
    private GeneralDate date;

    /**
     * List<応援勤務明細>
     */
    private List<SupportWorkDetails> supportWorkDetailsList;

    /**
     * 1日合計詳細
     */
    private Optional<TotalValueDetail> totalDetailOfDay;

    /**
     * [C-1] 新規作成
     * @param companyId 会社ID
     * @param date 年月日
     * @param supportWorkDetails List<応援勤務明細>
     * @param displayTotalOfDay 1日合計を表示する
     * @return 日毎の応援勤務データ
     */
    public static SupportWorkDataOfDay create(SupportWorkOutputDataRequire require, String companyId, GeneralDate date, List<SupportWorkDetails> supportWorkDetails, NotUseAtr displayTotalOfDay) {
        Optional<TotalValueDetail> totalDetail;
        if (displayTotalOfDay == NotUseAtr.NOT_USE) {
            totalDetail = Optional.empty();
        } else {
            totalDetail = Optional.of(TotalValueDetail.create(
                    require,
                    companyId,
                    supportWorkDetails
            ));
        }
        return new SupportWorkDataOfDay(date, supportWorkDetails, totalDetail);
    }
}
