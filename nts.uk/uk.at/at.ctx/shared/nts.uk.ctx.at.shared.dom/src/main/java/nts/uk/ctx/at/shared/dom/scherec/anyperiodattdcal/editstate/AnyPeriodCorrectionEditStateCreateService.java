package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任意期間修正の編集状態を作成する
 */
@Stateless
public class AnyPeriodCorrectionEditStateCreateService {

    /**
     * 作成する
     * @param anyPeriodTotalFrameCode 任意集計枠コード
     * @param correctingEmployeeId 修正者
     * @param targetEmployeeId 対象社員
     * @param itemIds 編集項目リスト
     * @return 編集状態リスト
     */
    public List<AnyPeriodCorrectionEditingState> create(String anyPeriodTotalFrameCode, String correctingEmployeeId, String targetEmployeeId, List<Integer> itemIds) {
        return itemIds.stream().map(i -> AnyPeriodCorrectionEditingState.create(
                correctingEmployeeId,
                targetEmployeeId,
                anyPeriodTotalFrameCode,
                i
        )).collect(Collectors.toList());
    }
}
