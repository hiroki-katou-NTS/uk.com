package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.aggregateprocess.clscodecfm;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.ClassificationImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicCheckName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.SClsHistImport;
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
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック(基本).アルゴリズム.マスタチェック(基本)の集計処理.分類コード確認
 *
 * @author Le Huu Dat
 */
@Stateless
public class ClsCodeCfmService {

    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;

    /**
     * 分類コード確認
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

        // 会社IDから分類コード一覧を取得する。
        List<ClassificationImport> classifications = empEmployeeAdapter.getClassificationByCompanyId(cid);

        for (Map.Entry<String, List<EmployeeInfoImported>> empInfo : empInfoMap.entrySet()) {
            List<EmployeeInfoImported> empInfos = empInfo.getValue();
            List<String> employeeIds = empInfos.stream().map(EmployeeInfoImported::getSid)
                    .collect(Collectors.toList());
            // 社員（List）と期間から所属分類履歴を取得する
            List<SClsHistImport> clsHists = this.empEmployeeAdapter
                    .lstClassByEmployeeId(new CacheCarrier(), cid, employeeIds, period);

            for (SClsHistImport clsHist : clsHists) {
                // 取得したList＜分類コード＞にループ中の分類コードを存在するかチェック
                boolean exist = classifications.stream().anyMatch(x -> x.getClassificationCode().equals(clsHist.getClassificationCode()));
                if (exist) continue;

                EmployeeInfoImported empItem = empInfos.stream()
                        .filter(x -> x.getSid().equals(clsHist.getEmployeeId())).findFirst().get();
                // 「アラーム値メッセージ」を作成します。
                String message = TextResource.localize("KAL020_1", empItem.getEmployeeCode(),
                        empItem.getEmployeeName(), clsHist.getClassificationCode());

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

        // リスト「抽出結果」を返す。
        return results;
    }
}
