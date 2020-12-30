package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.service;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.WorkplaceCheckName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceConfigInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（職場）.アルゴリズム.基準時間の未設定の確認
 *
 * @author Le Huu Dat
 */
@Stateless
public class NoRefTimeCfmService {

    @Inject
    private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;
    @Inject
    private WorkplaceConfigInfoAdapter workplaceConfigInfoAdapter;

    /**
     * 基準時間の未設定の確認
     *
     * @param cid                会社ID
     * @param workplaceCheckName アラーム項目名
     * @param displayMessage     表示するメッセージ
     * @param ymPeriod           期間
     * @param workplaceIds       List＜職場ID＞
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> confirm(String cid,
                                          WorkplaceCheckName workplaceCheckName,
                                          DisplayMessage displayMessage,
                                          YearMonthPeriod ymPeriod,
                                          List<String> workplaceIds) {
        // 空欄のリスト「抽出結果」を作成する。
        List<ExtractResultDto> results = new ArrayList<>();

        // ドメインモデル「職場別月単位労働時間」を取得する。
        List<MonthlyWorkTimeSetWkp> monthTimeWpAll = monthlyWorkTimeSetRepo.findWorkplace(cid, workplaceIds);

        // 「Input．期間．開始日．年」から「Input．期間．終了日．年」までループする。
        int startYear = ymPeriod.start().year();
        int endYear = ymPeriod.end().year();
        while (startYear <= endYear) {
            // Input．List＜職場ID＞をループする
            for (String workplaceId : workplaceIds) {
                // 担当の「職場別月単位労働時間」を絞り込む
                int year = startYear;
                List<MonthlyWorkTimeSetWkp> monthTimeWps = monthTimeWpAll.stream().filter(x -> x.getWorkplaceId().equals(workplaceId)
                        && x.getYm().year() == year).collect(Collectors.toList());

                if (CollectionUtil.isEmpty(monthTimeWps)) {
                    // 職場IDから職場の情報をすべて取得する //TODO Q&A 38606
                    List<WorkplaceInfor> wpInfos = workplaceConfigInfoAdapter.getWorkplaceInforByWkpIds(cid,
                            Collections.singletonList(workplaceId), GeneralDate.ymd(startYear, ymPeriod.start().month(), 1));
                    String wpCode = "";
                    if (!CollectionUtil.isEmpty(wpInfos)) {
                        wpCode = wpInfos.get(0).getWorkplaceCode();
                    }

                    // 「アラーム値メッセージ」を作成します。
                    String message = TextResource.localize("KAL020_200", String.valueOf(startYear), wpCode);

                    // ドメインオブジェクト「抽出結果」を作成してリスト「抽出結果」に追加
                    ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                            new AlarmValueDate(String.valueOf(startYear), Optional.empty()),
                            workplaceCheckName.v(),
                            Optional.ofNullable(TextResource.localize("KAL020_208", String.valueOf(startYear))),
                            Optional.of(new MessageDisplay(displayMessage.v())),
                            workplaceId
                    );
                    results.add(result);
                }
            }
            startYear++;
        }

        // リスト「抽出結果」を返す。
        return results;
    }
}
