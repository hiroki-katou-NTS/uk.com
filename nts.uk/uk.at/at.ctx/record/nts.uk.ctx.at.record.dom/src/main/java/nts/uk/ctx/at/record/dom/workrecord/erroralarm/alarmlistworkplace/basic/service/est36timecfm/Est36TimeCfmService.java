package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.est36timecfm;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeCompanyRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicCheckName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック(基本).アルゴリズム.マスタチェック(基本)の集計処理.３６協定目安時間確認
 *
 * @author Le Huu Dat
 */
@Stateless
public class Est36TimeCfmService {

    @Inject
    private AgreementTimeCompanyRepository agreementTimeCompanyRepo;

    /**
     * ３６協定目安時間確認
     *
     * @param cid            会社ID
     * @param name           アラーム項目名
     * @param displayMessage 表示するメッセージ.
     * @param period         期間
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> confirm(String cid, BasicCheckName name, DisplayMessage displayMessage, DatePeriod period) {
        List<ExtractResultDto> results = new ArrayList<>();

        // ドメインモデル「会社３６協定時間」を取得する。
        Optional<AgreementTimeOfCompany> generalSettingOpt = agreementTimeCompanyRepo.find(cid,
                LaborSystemtAtr.GENERAL_LABOR_SYSTEM);

        // ドメインモデル「会社３６協定時間」を取得する。
        Optional<AgreementTimeOfCompany> systemSettingOpt = agreementTimeCompanyRepo.find(cid,
                LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);

        // 取得したデータをチェック
        if (generalSettingOpt.isPresent()) {
            // 「アラーム値メッセージ」を作成
            String message = TextResource.localize("KAL020_16");

            // ドメインオブジェクト「抽出結果」を作成して返す
            ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                    new AlarmValueDate(Integer.valueOf(period.start().toString("yyyyMMdd")), Optional.empty()),
                    name.v(),
                    Optional.ofNullable(TextResource.localize("KAL020_17")),
                    Optional.of(new MessageDisplay(displayMessage.v())),
                    null
            );
            results.add(result);
        }

        if (systemSettingOpt.isPresent()) {
            // 「アラーム値メッセージ」を作成
            String message = TextResource.localize("KAL020_18");

            // ドメインオブジェクト「抽出結果」を作成して返す
            ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                    new AlarmValueDate(Integer.valueOf(period.start().toString("yyyy/MM/dd")), Optional.empty()),
                    name.v(),
                    Optional.ofNullable(TextResource.localize("KAL020_17")),
                    Optional.of(new MessageDisplay(displayMessage.v())),
                    null
            );
            results.add(result);
        }

        // リスト「抽出結果」を返す。
        return results;
    }
}
