package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.MonthlyAppContent;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementExcessInfo;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AgreementOneMonthTime;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.BasicAgreementSettings;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 弁当予約設定を登録する
 *
 * @author Quang.nh1
 */
public class CheckErrorApplicationMonthService {

    /**
     * [1] チェックする
     */
    public static List<ExcessErrorContent> check(Require require, MonthlyAppContent monthlyAppContent) {

        List<ExcessErrorContent> excessErrorInformation = new ArrayList<>();
        val baseDate = GeneralDate.today();
        val companyId = AppContexts.user().companyId();
        val employeeId = AppContexts.user().employeeId();

        //TODO unknow get WorkingSystem
        WorkingSystem workingSystem = EnumAdaptor.valueOf(1, WorkingSystem.class);

        // 1:年度指定して36協定基本設定を取得する(会社ID, 社員ID, 年月日, 年度) :３６協定基本設定
        BasicAgreementSettings agreementSet = AgreementDomainService.getBasicSet(require, companyId, employeeId, baseDate, workingSystem);

        val errorResult = agreementSet.getUpperAgreementSetting().getUpperMonth();

        ExcessErrorContent oneMonthError = ExcessErrorContent.create(EnumAdaptor.valueOf(ErrorClassification.ONE_MONTH_MAX_TIME.value, ErrorClassification.class),
                Optional.of(new AgreementOneMonthTime(errorResult.v())), Optional.empty(), Optional.empty());
        excessErrorInformation.add(oneMonthError);

        // 3:<call>
        Optional<AgreMaxAverageTimeMulti> multiMonthAverage = require.getMaxAverageMulti(companyId, employeeId, baseDate, monthlyAppContent.getYm());

        if (multiMonthAverage.isPresent()) {
            List<Optional<ExcessErrorContent>> averageExcessError = multiMonthAverage.get().getAverageTimeList().stream().
                    filter(x -> x.getStatus() == AgreMaxTimeStatusOfMonthly.EXCESS_MAXTIME).
                    map(c -> createMultipleErrors(c.getPeriod(), new AgreementOneMonthTime(multiMonthAverage.get().getMaxTime().v()))).collect(Collectors.toList());
            averageExcessError.forEach(x -> {
                x.ifPresent(excessErrorInformation::add);
            });
        }

        // TODO check lại annualTime.get().getStatus().value đang không đúng
        //TODO thiếu case (OR $年間時間.状態 == 特別条項の上限時間超過)
        // 4:<call>
        Optional<AgreementTimeYear> annualTime = require.timeYear(companyId, employeeId,baseDate,new Year(monthlyAppContent.getYm().year()));

        if (annualTime.isPresent()){

            if (annualTime.get().getStatus().value == AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR.value ||
                    annualTime.get().getStatus().value == AgreementTimeStatusOfMonthly.NORMAL_SPECIAL.value ||
                    annualTime.get().getStatus().value == AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR.value){
                ExcessErrorContent annualError = ExcessErrorContent.create(EnumAdaptor.valueOf(ErrorClassification.ONE_MONTH_MAX_TIME.value, ErrorClassification.class),
                        Optional.empty(),Optional.of(new AgreementOneMonthTime(errorResult.v())), Optional.empty());
                excessErrorInformation.add(annualError);
            }
        }

        // 5:<call>
        AgreementExcessInfo agreementOver = require.algorithm(monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));

        //TODO xem lại cách get agreementSet.getBasicAgreementSetting()
        if (agreementOver != null && agreementSet.getBasicAgreementSetting().getNumberTimesOverLimitType().value <= agreementOver.getExcessTimes() &&
                !agreementOver.getYearMonths().contains(monthlyAppContent.getYm())) {

            ExcessErrorContent error = ExcessErrorContent.create(EnumAdaptor.valueOf(ErrorClassification.EXCEEDING_MAXIMUM_NUMBER.value, ErrorClassification.class),
                    Optional.empty(), Optional.empty(),Optional.of(agreementSet.getBasicAgreementSetting().getNumberTimesOverLimitType()));
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
                return Optional.of(ExcessErrorContent.create(EnumAdaptor.valueOf(ErrorClassification.TWO_MONTH_MAX_TIME.value, ErrorClassification.class),
                        Optional.of(new AgreementOneMonthTime(agreementOneMonthTime.v())), Optional.empty(), Optional.empty()));
            case 3:
                return Optional.of(ExcessErrorContent.create(EnumAdaptor.valueOf(ErrorClassification.THREE_MONTH_MAX_TIME.value, ErrorClassification.class),
                        Optional.of(new AgreementOneMonthTime(agreementOneMonthTime.v())), Optional.empty(), Optional.empty()));
            case 4:
                return Optional.of(ExcessErrorContent.create(EnumAdaptor.valueOf(ErrorClassification.FOUR_MONTH_MAX_TIME.value, ErrorClassification.class),
                        Optional.of(new AgreementOneMonthTime(agreementOneMonthTime.v())), Optional.empty(), Optional.empty()));
            case 5:
                return Optional.of(ExcessErrorContent.create(EnumAdaptor.valueOf(ErrorClassification.FIVE_MONTH_MAX_TIME.value, ErrorClassification.class),
                        Optional.of(new AgreementOneMonthTime(agreementOneMonthTime.v())), Optional.empty(), Optional.empty()));
            case 6:
                return Optional.of(ExcessErrorContent.create(EnumAdaptor.valueOf(ErrorClassification.SIX_MONTH_MAX_TIME.value, ErrorClassification.class),
                        Optional.of(new AgreementOneMonthTime(agreementOneMonthTime.v())), Optional.empty(), Optional.empty()));
            default:
                return Optional.empty();
        }
    }

    public interface Require extends AgreementDomainService.RequireM3 {

        /**
         * [R-1] 複数月平均時間を集計する
         * アルゴリズム.[No.683]指定する年月の時間をもとに36協定時間を集計する(社員ID,基準日,年月,36協定時間)
         */
        Optional<AgreMaxAverageTimeMulti> getMaxAverageMulti(String companyId, String employeeId, GeneralDate criteria, YearMonth yearMonth);

        /**
         * 	[R-2] 年間時間を集計する
         * 	アルゴリズム.[No.684]指定する年度の時間をもとに36協定時間を集計する(社員ID,基準日,年度,36協定時間)
         * 	RequestList549
         */
        Optional<AgreementTimeYear> timeYear(String companyId, String employeeId, GeneralDate criteria, Year year);

        /**
         * [R-3] 超過回数を取得する
         * 	アルゴリズム.[No.458]年間超過回数の取得(社員ID,年度)
         */
        AgreementExcessInfo algorithm(String employeeId, Year year);


    }

}
