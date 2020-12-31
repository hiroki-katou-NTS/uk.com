package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.service.check.unapprove;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.ApprovalPhaseStateImport;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.ApprovalRootStateAdapter;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.ApprovalRootStateImport;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.申請承認.アルゴリズム.申請承認の集計処理.固定抽出条件をチェック.未承認をチェックする
 *
 * @author Le Huu Dat
 */
@Stateless
public class UpapproveCheckService {

    @Inject
    private ApprovalRootStateAdapter approvalRootStateAdapter;

    /**
     * 未承認をチェックする
     *
     * @param employeeInfo     List＜社員情報＞
     * @param period           期間
     * @param approvalPhaseNum 承認フェーズ番号
     * @return 抽出結果
     */
    public ExtractResultDto check(List<EmployeeInfoImport> employeeInfo, DatePeriod period, int approvalPhaseNum) {
        // 対象者と期間から承認ルートインスタンスを取得する
        //(Lấy [承認ルートインスタンス] từ employee và period)
        List<String> employeeIds = employeeInfo.stream().map(EmployeeInfoImport::getSid).distinct().collect(Collectors.toList());
        List<ApprovalRootStateImport> appRoot = approvalRootStateAdapter.findByEmployeesAndPeriod(employeeIds, period, 0);

        // 承認フェーズインスタンス[INPUT.承認フェーズ番号].承認区分＝【未承認】の件数をチェックする
        //(Check số data 承認フェーズインスタンス có [INPUT.approvePhaseNo].承認区分＝【未承認】)
        List<ApprovalPhaseStateImport> appPhases = appRoot.stream().flatMap(x -> x.getListApprovalPhaseState().stream())
                .filter(x -> x.getPhaseOrder() == approvalPhaseNum && x.getApprovalAtr() == 0).collect(Collectors.toList());
        int count = appPhases.size();

        ExtractResultDto result = null;
        if (count > 0) {
            // 抽出結果を作成
            result = new ExtractResultDto(new AlarmValueMessage(TextResource.localize("KAL020_500", String.valueOf(count))),
                    new AlarmValueDate(String.valueOf(period.start().toString("yyyyMMdd")),
                            Optional.of(String.valueOf(period.end().toString("yyyyMMdd")))),
                    null,
                    Optional.ofNullable(TextResource.localize("KAL020_504", String.valueOf(approvalPhaseNum), String.valueOf(count))),
                    Optional.empty(),
                    null);
        }


        return result;
    }
}
