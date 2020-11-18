package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.aggregateprocess.publichdcfm;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicCheckName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック(基本).アルゴリズム.マスタチェック(基本)の集計処理.公休日数確認
 *
 * @author Le Huu Dat
 */
@Stateless
public class PublicHdCfmService {

    @Inject
    private PublicHolidaySettingRepository publicHolidaySettingRepo;
    @Inject
    private CompanyMonthDaySettingRepository comMonthDaySettingRepo;

    /**
     * 公休日数確認
     *
     * @param cid            会社ID
     * @param name           アラーム項目名
     * @param displayMessage 表示するメッセージ.
     * @param period         期間
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> confirm(String cid, BasicCheckName name, DisplayMessage displayMessage, DatePeriod period) {
        // 空欄のリスト「抽出結果」を作成する。
        List<ExtractResultDto> results = new ArrayList<>();

        // ドメインモデル「公休設定」を取得する。
        Optional<PublicHolidaySetting> publicHdSettingOpt = publicHolidaySettingRepo.findByCID(cid);

        // Object＜公休設定＞をチェックする。
        if (!publicHdSettingOpt.isPresent()) {
            return results;
        }

        // 「Input．期間．開始日．年」から「Input．期間．終了日．年」までループする。
        int startYear = period.start().year();
        int endYear = period.end().year();
        while (startYear <= endYear) {
            // ドメインモデル「会社月間日数設定」を取得する。
            Optional<CompanyMonthDaySetting> comMonDaySetOpt = comMonthDaySettingRepo.findByYear(new CompanyId(cid), new Year(startYear));
            if (comMonDaySetOpt.isPresent()) continue;

            // 「アラーム値メッセージ」を作成します。
            String message = TextResource.localize("KAL020_10", AppContexts.user().companyCode());

            // ドメインオブジェクト「抽出結果」を作成してリスト「抽出結果」に追加
            ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                    new AlarmValueDate(startYear, Optional.empty()),
                    name.v(),
                    Optional.ofNullable(TextResource.localize("KAL020_15")),
                    Optional.of(new MessageDisplay(displayMessage.v())),
                    null
            );
            results.add(result);

            startYear++;
        }

        // リスト「抽出結果」を返す。
        return results;
    }
}
