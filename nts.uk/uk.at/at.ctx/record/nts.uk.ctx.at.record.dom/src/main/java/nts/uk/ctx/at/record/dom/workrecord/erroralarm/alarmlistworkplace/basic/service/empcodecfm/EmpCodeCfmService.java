package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.empcodecfm;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicCheckName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.ctx.at.shared.dom.adapter.employment.AffPeriodEmpCodeImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmpCdNameImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック(基本).アルゴリズム.マスタチェック(基本)の集計処理.雇用コード確認
 *
 * @author Le Huu Dat
 */
@Stateless
public class EmpCodeCfmService {

    @Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;

    /**
     * 雇用コード確認
     *
     * @param cid            会社ID
     * @param name           アラーム項目名
     * @param displayMessage 表示するメッセージ.
     * @param empInfoMap     Map＜職場ID、List＜社員情報＞＞
     * @param period         期間
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> confirm(String cid, BasicCheckName name, DisplayMessage displayMessage,
                                          Map<String, List<EmployeeInfoImported>> empInfoMap, DatePeriod period) {
        // 空欄のリスト「抽出結果」を作成する。
        List<ExtractResultDto> results = new ArrayList<>();

        // 会社IDから雇用コード一覧を取得する。
        List<EmpCdNameImport> employments = shareEmploymentAdapter.findAll(cid);

        for (Map.Entry<String, List<EmployeeInfoImported>> empInfo : empInfoMap.entrySet()) {
            List<EmployeeInfoImported> empInfos = empInfo.getValue();
            List<String> employeeIds = empInfos.stream().map(EmployeeInfoImported::getSid)
                    .collect(Collectors.toList());
            // 社員ID（List）と指定期間から社員の雇用履歴を取得
            List<SharedSidPeriodDateEmploymentImport> employmentHistList = this.shareEmploymentAdapter
                    .getEmpHistBySidAndPeriodRequire(new CacheCarrier(), employeeIds, period);

            for (SharedSidPeriodDateEmploymentImport empHist : employmentHistList) {
                // 取得したList＜雇用コード＞にループ中の所属期間と雇用コード．雇用コードを存在するかチェック
                for (AffPeriodEmpCodeImport affEmp : empHist.getAffPeriodEmpCodeExports()) {
                    boolean exist = employments.stream()
                            .anyMatch(x -> x.getCode().equals(affEmp.getEmploymentCode()));
                    if (exist) continue;

                    EmployeeInfoImported empItem = empInfos.stream()
                            .filter(x -> x.getSid().equals(empHist.getEmployeeId())).findFirst().get();
                    // 「アラーム値メッセージ」を作成します。
                    String message = TextResource.localize("KAL020_1", empItem.getEmployeeCode(),
                            empItem.getEmployeeName(), affEmp.getEmploymentCode());

                    // ドメインオブジェクト「抽出結果」を作成します。
                    ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                            new AlarmValueDate(Integer.valueOf(period.start().toString("yyyyMMdd")),
                                    Optional.of(Integer.valueOf(period.end().toString("yyyyMMdd")))),
                            name.v(),
                            Optional.ofNullable(TextResource.localize("KAL020_11")),
                            Optional.of(new MessageDisplay(displayMessage.v())),
                            empInfo.getKey()
                    );

                    // リスト「抽出結果」に作成した「抽出結果」を追加する。
                    results.add(result);
                }
            }
        }

        // リスト「抽出結果」を返す。
        return results;
    }
}
