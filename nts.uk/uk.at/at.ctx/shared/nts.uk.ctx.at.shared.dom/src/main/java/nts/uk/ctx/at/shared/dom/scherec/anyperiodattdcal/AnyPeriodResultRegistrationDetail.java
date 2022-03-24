package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.task.tran.AtomTask;

import java.util.List;

/**
 * 任意期間実績の登録内容
 */
@AllArgsConstructor
@Getter
public class AnyPeriodResultRegistrationDetail {
    // 永続化処理
    private List<AtomTask> processes;

    // 修正詳細
    private AnyPeriodActualResultCorrectionDetail correctionDetail;
}
