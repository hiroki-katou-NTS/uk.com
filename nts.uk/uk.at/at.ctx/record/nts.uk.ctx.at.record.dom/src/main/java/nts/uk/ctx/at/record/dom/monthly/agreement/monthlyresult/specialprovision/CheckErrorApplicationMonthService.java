package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.MonthlyAppContent;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementExcessInfo;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreementTimeOfMngPeriod;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOverMaxTimes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 1ヶ月申請の超過エラーをチェックする
 *
 * @author Quang.nh1
 */
public class CheckErrorApplicationMonthService {

    /**
     * [1] チェックする
     */
    public static List<ExcessErrorContent> check(Require require, MonthlyAppContent monthlyAppContent) {

        List<ExcessErrorContent> excessErrorInformation = new ArrayList<>();
        GeneralDate baseDate = GeneralDate.today();
        val companyId = AppContexts.user().companyId();

        // 1:年度指定して36協定基本設定を取得する(会社ID, 社員ID, 年月日, 年度) :３６協定基本設定
        BasicAgreementSetting agreementSet = AgreementDomainService.getBasicSet(require, companyId, monthlyAppContent.getApplicant(),baseDate);

        Pair<Boolean, AgreementOneMonthTime> errorResult = agreementSet.getOneMonth().checkErrorTimeExceeded(monthlyAppContent.getErrTime());

        if (errorResult.getKey()){
            ExcessErrorContent oneMonthError = ExcessErrorContent.create(ErrorClassification.ONE_MONTH_MAX_TIME,
                    Optional.of(new AgreementOneMonthTime(errorResult.getRight().v())), Optional.empty(), Optional.empty());
            excessErrorInformation.add(oneMonthError);
        }

        // 3:<call>
        val agreementTimes = new HashMap<YearMonth, AttendanceTimeMonth>();
        agreementTimes.put(monthlyAppContent.getYm(), new AttendanceTimeMonth(monthlyAppContent.getErrTime().v()));
        AgreMaxAverageTimeMulti multiMonthAverage = require.getMaxAverageMulti(monthlyAppContent.getApplicant(),
                baseDate, monthlyAppContent.getYm(),agreementTimes);

        if (multiMonthAverage != null) {
            List<Optional<ExcessErrorContent>> averageExcessError = multiMonthAverage.getAverageTimes().stream().
                    filter(x -> x.getStatus() == AgreMaxTimeStatusOfMonthly.ERROR_OVER).
                    map(c -> createMultipleErrors(c.getPeriod(), multiMonthAverage.getMaxTime().getError())).collect(Collectors.toList());

            averageExcessError.forEach(x -> {
                x.ifPresent(excessErrorInformation::add);
            });
        }

        // 4:<call>
        AgreementTimeYear annualTime = require.timeYear(monthlyAppContent.getApplicant(),baseDate,new Year(monthlyAppContent.getYm().year()),agreementTimes);

        if (annualTime != null){

            if (annualTime.getStatus().value == AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR.value ||
                    annualTime.getStatus().value == AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP.value ||
                    annualTime.getStatus().value == AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR.value ||
                    annualTime.getStatus().value == AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY.value ){
                ExcessErrorContent annualError = ExcessErrorContent.create(ErrorClassification.ONE_MONTH_MAX_TIME,
                        Optional.empty(),Optional.of(new AgreementOneYearTime(annualTime.getRecordTime().getThreshold().getErAlTime().getError().v())), Optional.empty());
                excessErrorInformation.add(annualError);
            }
        }

        // 5:<call>
        AgreementExcessInfo agreementOver = require.algorithm(require,monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));

        if (agreementOver != null &&
                agreementSet.getOverMaxTimes().value <= agreementOver.getExcessTimes() &&
                !agreementOver.getYearMonths().contains(monthlyAppContent.getYm())) {

            ExcessErrorContent error = ExcessErrorContent.create(ErrorClassification.EXCEEDING_MAXIMUM_NUMBER,
                    Optional.empty(), Optional.empty(),Optional.of(agreementSet.getOverMaxTimes()));
            excessErrorInformation.add(error);
        }

        return excessErrorInformation;
    }

    /**
     * [prv-1] 複数平均エラーを作成する
     */
    private static Optional<ExcessErrorContent> createMultipleErrors(YearMonthPeriod period, AgreementOneMonthTime agreementOneMonthTime) {
        switch (period.yearMonthsBetween().size()) {
            case 2:
                return Optional.of(ExcessErrorContent.create(ErrorClassification.TWO_MONTH_MAX_TIME,
                        Optional.of(agreementOneMonthTime), Optional.empty(), Optional.empty()));
            case 3:
                return Optional.of(ExcessErrorContent.create(ErrorClassification.THREE_MONTH_MAX_TIME,
                        Optional.of(agreementOneMonthTime), Optional.empty(), Optional.empty()));
            case 4:
                return Optional.of(ExcessErrorContent.create(ErrorClassification.FOUR_MONTH_MAX_TIME,
                        Optional.of(agreementOneMonthTime), Optional.empty(), Optional.empty()));
            case 5:
                return Optional.of(ExcessErrorContent.create(ErrorClassification.FIVE_MONTH_MAX_TIME,
                        Optional.of(agreementOneMonthTime), Optional.empty(), Optional.empty()));
            case 6:
                return Optional.of(ExcessErrorContent.create(ErrorClassification.SIX_MONTH_MAX_TIME,
                        Optional.of(agreementOneMonthTime), Optional.empty(), Optional.empty()));
            default:
                return Optional.empty();
        }
    }

    public interface Require extends AgreementDomainService.RequireM4,GetAgreementTimeOfMngPeriod.RequireM1 {

        /**
         * [R-1] 複数月平均時間を集計する
         * アルゴリズム.[No.683]指定する年月の時間をもとに36協定時間を集計する(社員ID,基準日,年月,36協定時間)
         */
        AgreMaxAverageTimeMulti getMaxAverageMulti(String sid, GeneralDate baseDate, YearMonth ym,
                                                 Map<YearMonth, AttendanceTimeMonth> agreementTimes);

        /**
         * 	[R-2] 年間時間を集計する
         * 	アルゴリズム.[No.684]指定する年度の時間をもとに36協定時間を集計する(社員ID,基準日,年度,36協定時間)
         */
        AgreementTimeYear timeYear(String sid, GeneralDate baseDate, Year year,
                                           Map<YearMonth, AttendanceTimeMonth> agreementTimes);

        /**
         * [R-3] 超過回数を取得する
         * 	アルゴリズム.[No.458]年間超過回数の取得(社員ID,年度)
         */
        AgreementExcessInfo algorithm(Require require,String employeeId, Year year);

    }

}
