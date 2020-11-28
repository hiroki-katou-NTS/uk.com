package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check.personeligibleannualleave;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.RCAnnualHolidayManagement;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（日別）.アルゴリズム.マスタチェック(日別)の集計処理.チェック処理."4.年休付与対象者をチェック"
 *
 * @author Le Huu Dat
 */
@Stateless
public class PersonEligibleAnnualLeaveCheckService {

    @Inject
    private RCAnnualHolidayManagement rcAnnualHolidayManagement;

    /**
     * 4.年休付与対象者をチェック
     *
     * @param cid         会社ID
     * @param personInfos List＜個人社員基本情報＞
     * @param period      期間
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> check(String cid, List<PersonEmpBasicInfoImport> personInfos, DatePeriod period) {
        List<ExtractResultDto> results = new ArrayList<>();
        // Input．List＜個人社員基本情報＞をループする
        for (PersonEmpBasicInfoImport personInfo : personInfos) {
            GeneralDate retirementDate = personInfo.getRetirementDate();

            // 次回年休付与日を取得する
            List<NextAnnualLeaveGrant> nextAnnualLeaveGrants = rcAnnualHolidayManagement.acquireNextHolidayGrantDate(
                    new CompanyId(cid), new EmployeeId(personInfo.getEmployeeId()), Optional.of(period.start()));

            // 次回年休付与日をチェック
            for (NextAnnualLeaveGrant next : nextAnnualLeaveGrants){
                if (next.getGrantDate().beforeOrEquals(retirementDate) &&
                        period.start().beforeOrEquals(next.getGrantDate()) &&
                        next.getGrantDate().beforeOrEquals(period.end())){

                    String message = TextResource.localize("KAL020_105", personInfo.getEmployeeCode(), personInfo.getBusinessName());
                    // 抽出結果を作成
                    ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                            new AlarmValueDate(Integer.valueOf(jobEntryDate.toString("yyyyMMdd")), Optional.empty()),
                            null,
                            Optional.ofNullable(TextResource.localize("KAL020_114", jobEntryDate.toString("yyyy/MM/dd"))),
                            Optional.empty(),
                            null
                    );

                    // List＜抽出結果＞に作成した「抽出結果」を追加
                    results.add(result);
                }
            }
        }

        // 作成したList＜抽出結果＞を返す
        return results;
    }
}
