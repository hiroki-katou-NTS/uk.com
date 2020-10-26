package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.Value;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.Overtime;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.OvertimeIncludingHoliday;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ScreenDisplayInfo;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.UpperLimitBeforeRaising;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;

@Value
public class ScreenDisplayInfoCommand {
    /**
     * 36協定特別条項の適用申請.画面表示情報.申請時点の時間外時間.対象月度の時間外時間
     * B4_3
     */
    int monthTime;
    /**
     * 36協定特別条項の適用申請.画面表示情報.申請時点の時間外時間（法定休出を含む）.対象月度の時間外時間）
     * B4_3
     */
    int monthTargetTime;
    /**
     * 36協定特別条項の適用申請.画面表示情報.申請時点の時間外時間.対象年度の時間外時間
     * B4_4
     */
    int yearTime;
    /**
     * 36協定特別条項の適用申請.画面表示情報.申請時点の時間外時間（法定休出を含む）.2ヶ月平均の時間外時間
     * B4_5
     */
    int monthAverage2;
    /**
     * 36協定特別条項の適用申請.画面表示情報.申請時点の時間外時間（法定休出を含む）.3ヶ月平均の時間外時間
     * B4_6
     */
    int monthAverage3;
    /**
     * 36協定特別条項の適用申請.画面表示情報.申請時点の時間外時間（法定休出を含む）.4ヶ月平均の時間外時間
     * B4_7
     */
    int monthAverage4;
    /**
     * 36協定特別条項の適用申請.画面表示情報.申請時点の時間外時間（法定休出を含む）.5ヶ月平均の時間外時間
     * B4_8
     */
    int monthAverage5;
    /**
     * 36協定特別条項の適用申請.画面表示情報.申請時点の時間外時間（法定休出を含む）.6ヶ月平均の時間外時間
     * B4_9
     */
    int monthAverage6;
    /**
     * 36協定特別条項の適用申請.画面表示情報.超過月数
     * B4_10
     */
    int exceededNumber;
    /**
     * 36協定特別条項の適用申請.画面表示情報.引き上げる前の上限.1ヶ月の上限
     * B4_11
     */
    int monthError;
    /**
     * 36協定特別条項の適用申請.画面表示情報.引き上げる前の上限.1年間の上限
     * B4_11
     */
    int yearError;

    public ScreenDisplayInfo toScreenDisplayInfo() {
        Overtime overtime = new Overtime(new AgreementOneMonthTime(monthTime), new AgreementOneYearTime(yearTime));
        OvertimeIncludingHoliday overtimeIncludingHoliday = new OvertimeIncludingHoliday(
                new AgreementOneMonthTime(0),
                new AgreementOneMonthTime(0),
                new AgreementOneMonthTime(monthAverage2),
                new AgreementOneMonthTime(0),
                new AgreementOneMonthTime(monthAverage3),
                new AgreementOneMonthTime(0),
                new AgreementOneMonthTime(monthAverage4),
                new AgreementOneMonthTime(0),
                new AgreementOneMonthTime(monthAverage5),
                new AgreementOneMonthTime(monthAverage6),
                new AgreementOneMonthTime(monthTargetTime)
        );
        UpperLimitBeforeRaising upperContents = new UpperLimitBeforeRaising(
                OneMonthErrorAlarmTime.of(
                        new AgreementOneMonthTime(monthError),
                        new AgreementOneMonthTime(0)),
                OneYearErrorAlarmTime.of(
                        new AgreementOneYearTime(yearError),
                        new AgreementOneYearTime(0)),
                new AgreementOneMonthTime(0));
        return ScreenDisplayInfo.create(overtime, overtimeIncludingHoliday, exceededNumber, upperContents);
    }
}
