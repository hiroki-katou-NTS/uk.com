package nts.uk.ctx.at.record.pubimp.monthly.vacation.childcarenurse.care;

import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.care.GetRemainingNumberCareService;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.*;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.*;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 実装：期間中の介護休暇残数を取得
 *
 * @author yuri_tamakoshi
 */
@Stateless
public class GetRemainingNumberCarePubImpl implements GetRemainingNumberCare {
    @Inject
    private GetRemainingNumberCareService getRemainingNumberCareService;

    /**
     * 期間中の介護休暇残数を取得
     *
     * @param employeeId                   社員ID
     * @param period                       集計期間
     * @param performReferenceAtr          実績のみ参照区分(月次モード orその他)
     * @param criteriaDate                 基準日
     * @param isOverWrite                  上書きフラグ(Optional)
     * @param tempCareDataforOverWriteList List<上書き用の暫定管理データ>(Optional)
     * @param prevCareLeave                前回の子の看護休暇の集計結果<Optional>
     * @param createAtr                    作成元区分(Optional)
     * @param periodOverWrite              上書き対象期間(Optional)
     * @return 子の看護介護休暇集計結果
     */
    @Override
    public ChildCareNursePeriodExport getCareRemNumWithinPeriod(
            String employeeId,
            DatePeriod period,
            InterimRemainMngMode performReferenceAtr,
            GeneralDate criteriaDate,
            Optional<Boolean> isOverWrite,
            Optional<List<TmpChildCareNurseMngWorkExport>> tempCareDataforOverWriteList,
            Optional<ChildCareNursePeriodExport> prevCareLeave,
            Optional<CreateAtr> createAtr,
            Optional<GeneralDate> periodOverWrite) {
        List<TmpChildCareNurseMngWork> tmpChildCareNurseMngWorks = tempCareDataforOverWriteList.isPresent()
                ? tempCareDataforOverWriteList.get().stream().map(TmpChildCareNurseMngWorkExport::toDomain).collect(Collectors.toList())
                : null;
        AggrResultOfChildCareNurse result = getRemainingNumberCareService.getCareRemNumWithinPeriod(
                employeeId,
                period,
                performReferenceAtr,
                criteriaDate,
                isOverWrite,
                Optional.ofNullable(tmpChildCareNurseMngWorks),
                prevCareLeave.map(ChildCareNursePeriodExport::toDomain),
                createAtr,
                periodOverWrite
        );


        // 固定値を返す（一時対応）
        return mapToPub(result);
    }

    // Exportから変換
    private ChildCareNursePeriodExport mapToPub(AggrResultOfChildCareNurse c) {
        return new ChildCareNursePeriodExport(
                c.getChildCareNurseErrors().stream().map(i -> ChildCareNurseErrorsExport.of(i.getUsedNumber(), i.getLimitDays().v(), i.getYmd())).collect(Collectors.toList()),
                c.getAsOfPeriodEnd(),
                ChildCareNurseStartdateDaysInfoExport.of(
                        mapToPub(c.getStartdateDays().getThisYear()),
                        c.getStartdateDays().getNextYear().map(ny -> mapToPub(ny))),
                c.isStartDateAtr(),
                ChildCareNurseAggrPeriodDaysInfoExport.of(
                        mapToPubAggrPeriodInfo(c.getAggrperiodinfo().getThisYear()),
                        c.getAggrperiodinfo().getNextYear().map(ny -> mapToPubAggrPeriodInfo(ny))));
    }

    //  起算日からの休暇情報
    private ChildCareNurseStartdateInfoExport mapToPub(ChildCareNurseStartdateInfo domain) {
        return ChildCareNurseStartdateInfoExport.of(
                domain.getUsedDays(),
                ChildCareNurseRemainingNumberExport.of(
                        domain.getRemainingNumber().getUsedDays().v(),
                        domain.getRemainingNumber().getUsedTime().map(PrimitiveValueBase::v)),
                domain.getLimitDays().v());
    }

    // 集計期間の休暇情報
    private ChildCareNurseAggrPeriodInfoExport mapToPubAggrPeriodInfo(ChildCareNurseAggrPeriodInfo domain) {
        return ChildCareNurseAggrPeriodInfoExport.of(
                domain.getUsedCount().v(),
                domain.getUsedDays().v(),
                domain.getAggrPeriodUsedNumber()
        );
    }


}
