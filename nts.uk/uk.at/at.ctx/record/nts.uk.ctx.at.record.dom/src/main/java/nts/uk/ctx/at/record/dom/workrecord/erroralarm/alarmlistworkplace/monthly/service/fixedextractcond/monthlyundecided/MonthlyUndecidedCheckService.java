package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.fixedextractcond.monthlyundecided;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.manageactualsituation.approval.monthly.MonthlyApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.managectualsituation.ApprovalStatus;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureInfo;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.月次のアラームチェック.アルゴリズム.月次の集計処理.固定抽出条件をチェック.月次データ未確定をチェックする
 *
 * @author Le Huu Dat
 */
@Stateless
public class MonthlyUndecidedCheckService {

    @Inject
    private MonthlyApprovalProcess monthlyApprovalProcess;
    @Inject
    private ClosureRepository closureRepository;
    @Inject
    private ClosureEmploymentRepository closureEmpRepo;

    /**
     * 月次データ未確定をチェックする
     *
     * @param cid             会社ID
     * @param empInfosByWpMap Map＜職場ID、List＜社員情報＞＞
     * @param closures        List＜締め＞
     * @param ym              年月
     * @param approvalProcess 承認処理の利用設定
     * @return
     */
    public List<ExtractResultDto> check(String cid, Map<String, List<EmployeeInfoImported>> empInfosByWpMap,
                                        List<ClosureInfo> closures, YearMonth ym,
                                        Optional<ApprovalProcess> approvalProcess) {

        List<ExtractResultDto> results = new ArrayList<>();
        // Input．Map＜職場ID、List＜社員情報＞＞をループする
        for (Map.Entry<String, List<EmployeeInfoImported>> empInfosByWp : empInfosByWpMap.entrySet()) {
            for (ClosureInfo closure : closures) {
                // 締め開始日と締め日を取得する
                DatePeriod closurePeriod = ClosureService.getClosurePeriod(ClosureService.createRequireM1(closureRepository, closureEmpRepo),
                        closure.getClosureId().value, ym);

                List<EmployeeInfoImported> empInfos = empInfosByWp.getValue();
                for (EmployeeInfoImported empInfo : empInfos) {
                    // 対象月の月の承認が済んでいるかチェックする
                    ApprovalStatus status = monthlyApprovalProcess.monthlyApprovalCheck(cid, empInfo.getSid(), ym.v(),
                            closure.getClosureId().value, closurePeriod.start(), approvalProcess, Collections.emptyList());

                    // 承認が済んでいるをチェック
                    if (ApprovalStatus.APPROVAL.equals(status)) continue;

                    // 抽出結果を作成
                    ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(TextResource.localize("KAL020_400", closure.getClosureName().v())),
                            new AlarmValueDate(String.valueOf(ym.v()), Optional.empty()),
                            null,
                            Optional.ofNullable(TextResource.localize("KAL020_400", closure.getClosureName().v())),
                            Optional.empty(),
                            empInfosByWp.getKey());
                    results.add(result);
                    break;
                }
            }
        }

        return results;
    }
}
