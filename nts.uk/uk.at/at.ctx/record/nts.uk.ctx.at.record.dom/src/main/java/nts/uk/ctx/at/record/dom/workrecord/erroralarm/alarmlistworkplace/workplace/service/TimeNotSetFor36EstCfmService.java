package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.service;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.WorkplaceCheckName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Workplace36AgreedHoursRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（職場）.アルゴリズム.36協定目安時間の未設定を確認する
 *
 * @author Le Huu Dat
 */
@Stateless
public class TimeNotSetFor36EstCfmService {

    @Inject
    private Workplace36AgreedHoursRepository workplace36AgreedHoursRepo;

    /**
     * 36協定目安時間の未設定を確認する
     *
     * @param workplaceCheckName アラーム項目名
     * @param displayMessage     表示するメッセージ
     * @param period             期間
     * @param workplaceIds       List＜職場ID＞
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> confirm(WorkplaceCheckName workplaceCheckName,
                                          DisplayMessage displayMessage,
                                          DatePeriod period,
                                          List<String> workplaceIds) {
        // 空欄のリスト「アラーム抽出結果（職場別）」を作成する。
        List<ExtractResultDto> results = new ArrayList<>();

        for (String workplaceId : workplaceIds) {
            // ドメインモデル「職場３６協定時間」を取得する。
            Optional<AgreementTimeOfWorkPlace> agreeGeneralOpt = workplace36AgreedHoursRepo.getByWorkplaceId(workplaceId, LaborSystemtAtr.GENERAL_LABOR_SYSTEM);

            // ドメインモデル「職場３６協定時間」を取得する。
            Optional<AgreementTimeOfWorkPlace> agreeSystemOpt = workplace36AgreedHoursRepo.getByWorkplaceId(workplaceId, LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);

            // 取得したList＜職場３６協定時間＞をチェックする。
            if (!agreeGeneralOpt.isPresent()) {
                // 「アラーム値メッセージ」を作成します。
                String message = TextResource.localize("KAL020_201");

                // 抽出結果を作成する
                ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                        new AlarmValueDate(Integer.valueOf(period.start().toString("yyyyMMdd")), Optional.empty()),
                        workplaceCheckName.v(),
                        Optional.ofNullable(message),
                        Optional.of(new MessageDisplay(displayMessage.v())),
                        workplaceId
                );

                // リスト「抽出結果に作成した抽出結果を追加する
                results.add(result);
            }

            if (!agreeSystemOpt.isPresent()) {
                // 「アラーム値メッセージ」を作成します。
                String message = TextResource.localize("KAL020_209 ");

                // 抽出結果を作成する
                ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                        new AlarmValueDate(Integer.valueOf(period.start().toString("yyyyMMdd")), Optional.empty()),
                        workplaceCheckName.v(),
                        Optional.ofNullable(message),
                        Optional.of(new MessageDisplay(displayMessage.v())),
                        workplaceId
                );

                // リスト「抽出結果に作成した抽出結果を追加する
                results.add(result);
            }
        }

        // リスト「アラーム抽出結果（職場別）」を返す。
        return results;
    }
}
