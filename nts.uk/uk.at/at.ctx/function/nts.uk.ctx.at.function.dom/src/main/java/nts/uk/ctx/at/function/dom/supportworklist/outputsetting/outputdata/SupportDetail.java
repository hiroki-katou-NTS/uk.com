package nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkDetails;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;

import java.util.List;

/**
 * 応援内訳詳細
 */
@AllArgsConstructor
@Getter
public class SupportDetail {
    /**
     * 	応援先
     */
    private String supportDestination;

    /**
     * 	合計値詳細
     */
    private TotalValueDetail totalValueDetail;

    /**
     * [C-1] 応援内訳を計算する
     * @param companyId 会社ID
     * @param supportDestination 応援先
     * @param supportWorkDetails List<応援勤務明細>
     * @return 応援内訳詳細
     */
    public static SupportDetail create(SupportWorkOutputDataRequire require, String companyId, String supportDestination, List<SupportWorkDetails> supportWorkDetails) {
        return new SupportDetail(
                supportDestination,
                TotalValueDetail.create(
                        require,
                        companyId,
                        supportWorkDetails
                )
        );
    }
}
