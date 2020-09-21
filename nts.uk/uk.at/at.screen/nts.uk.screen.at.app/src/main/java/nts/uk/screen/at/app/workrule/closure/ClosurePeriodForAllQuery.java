package nts.uk.screen.at.app.workrule.closure;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureResultModel;
import nts.uk.ctx.at.shared.app.query.workrule.closure.WorkClosureQueryProcessor;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 全ての締めの処理年月と締め期間を取得する
 *
 * @author Le Huu Dat
 */
@Stateless
public class ClosurePeriodForAllQuery {
    @Inject
    private WorkClosureQueryProcessor workClosureQueryProcessor;

    @Inject
    private ShClosurePub shClosurePub;

    public List<CurrentClosurePeriod> get(String cid) {
        List<CurrentClosurePeriod> listDate = new ArrayList<>();
        // アルゴリズム「会社の締めを取得する」を実行する
        // Lấy RQ 140
        List<ClosureResultModel> closures = workClosureQueryProcessor.findClosureByReferenceDate(GeneralDate.today());
        // 取得した締めIDのリストでループする
        for (ClosureResultModel closure : closures) {
            // アルゴリズム「処理年月と締め期間を取得する」を実行する
            // (Thực thi xử lý lấy thời gian quyết toán và tháng năm xử lý)
            Optional<PresentClosingPeriodExport> closingPeriodOpt = shClosurePub.find(cid, closure.getClosureId());
            if (closingPeriodOpt.isPresent()) {
                PresentClosingPeriodExport closingPeriod = closingPeriodOpt.get();
                listDate.add(new CurrentClosurePeriod(closure.getClosureId(), closingPeriod.getProcessingYm(),
                        closingPeriod.getClosureStartDate(), closingPeriod.getClosureEndDate()));
            }
        }

        return listDate.stream().sorted(Comparator.comparing(CurrentClosurePeriod::getClosureId)).collect(Collectors.toList());
    }
}
