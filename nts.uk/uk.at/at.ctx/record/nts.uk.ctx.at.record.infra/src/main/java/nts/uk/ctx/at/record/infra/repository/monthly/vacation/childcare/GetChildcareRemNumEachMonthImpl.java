package nts.uk.ctx.at.record.infra.repository.monthly.vacation.childcare;

import lombok.val;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remainmerge.RemainMerge;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remainmerge.RemainMergeRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.care.CareRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.ChildNursingLeaveStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.ChildcareRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.IGetChildcareRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.NursingCareLeaveMonthlyRemaining;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Stateless
public class GetChildcareRemNumEachMonthImpl implements IGetChildcareRemNumEachMonth {

    @Inject
    private RemainMergeRepository repoRemainMer;

    @Override
    public List<ChildNursingLeaveStatus> getMonthlyConfirmedCareForEmployees(String sid, List<YearMonth> yearMonths) {
        List<ChildNursingLeaveStatus> listOuput = new ArrayList<>();
        Map<YearMonth, List<RemainMerge>> mapRemainMer = repoRemainMer.findBySidsAndYrMons(sid, yearMonths);

        for (Map.Entry<YearMonth, List<RemainMerge>> entry : mapRemainMer.entrySet()) {

            List<RemainMerge> remainMergeList =
                    new ArrayList<>(entry.getValue());
            val output = new ChildNursingLeaveStatus(
                    sid,
                    entry.getKey(),
                    null,
                    null,
                    null,
                    null
            );
            for (val rmm : remainMergeList) {
                AnnLeaRemNumEachMonth annLeaRemNumEachMonth = rmm.getAnnLeaRemNumEachMonth();
                DatePeriod closurePeriod = annLeaRemNumEachMonth.getClosurePeriod();
                ChildcareRemNumEachMonth monChildHdRemain = rmm.getMonChildHdRemain();
                val thisYearUsedInfo = monChildHdRemain.getRemNumEachMonth()
                        .getThisYearUsedInfo();
                YearMonth ym = entry.getKey();

                /**    終了年月日 */
                GeneralDate endDate = closurePeriod.start();
                GeneralDate endDateRemainingMax = GeneralDate.ymd(ym.year(), ym.month(), 1);
                if (thisYearUsedInfo != null) {
                    /** 使用数 */
                    ChildCareNurseUsedNumber usedNumber = thisYearUsedInfo.getUsedNumber();
                    if (usedNumber != null) {
                        /** 日数 */
                        DayNumberOfUse usedDay = usedNumber.getUsedDay();
                        /** 時間 */
                        Optional<TimeOfUse> usedTimes = usedNumber.getUsedTimes();
                        // 使用日数←子の看護休暇月別残数データ．本年使用数．使用数．日数
                        output.setDaysOfUse(output.getDaysOfUse() == null ? 0 : output.getDaysOfUse() +
                                usedDay.v());
                        // 使用時間←子の看護休暇月別残数データ．本年使用数．使用数．時間
                        usedTimes.ifPresent(timeOfUse -> output.setUsageTime(output.getUsageTime() == null ? 0 :
                                output.getUsageTime() + timeOfUse.v()));

                        //※子の看護休暇月別残数データ．残数に限り、合算せずに締め期間．終了日が遅い方のみ保持する
                        if (endDate.afterOrEquals(endDateRemainingMax)) {
                            //残日数:残日数　←子の看護休暇月別残数データ．本年使用数．使用数．日数
                            output.setRemainingDays(usedDay.v());
                            // 残時間:残時間　←子の看護休暇月別残数データ．本年使用数．使用数．時間
                            usedTimes.ifPresent(PrimitiveValueBase::v);
                        }
                    }
                }
            }
            listOuput.add(output);

        }
        return listOuput;
    }

    @Override
    public List<NursingCareLeaveMonthlyRemaining> getObtainMonthlyConfirmedCareForEmployees(String sid, List<YearMonth> yearMonths) {
        List<NursingCareLeaveMonthlyRemaining> listOuput = new ArrayList<>();
        Map<YearMonth, List<RemainMerge>> mapRemainMer = repoRemainMer.findBySidsAndYrMons(sid, yearMonths);
        for (Map.Entry<YearMonth, List<RemainMerge>> entry : mapRemainMer.entrySet()) {
            List<RemainMerge> remainMergeList =
                    new ArrayList<>(entry.getValue());
            val output = new NursingCareLeaveMonthlyRemaining(
                    sid,
                    entry.getKey(),
                    null,
                    null,
                    null,
                    null
            );
            for (int i = 0; i < remainMergeList.size(); i++) {
                RemainMerge rmm = remainMergeList.get(i);
                AnnLeaRemNumEachMonth annLeaRemNumEachMonth = rmm.getAnnLeaRemNumEachMonth();
                DatePeriod closurePeriod = annLeaRemNumEachMonth.getClosurePeriod();
                CareRemNumEachMonth monCareHdRemain = rmm.getMonCareHdRemain();
                val thisYearUsedInfo = monCareHdRemain.getRemNumEachMonth()
                        .getThisYearUsedInfo();
                YearMonth ym = entry.getKey();

                /**    終了年月日 */
                GeneralDate endDate = closurePeriod.start();
                GeneralDate endDateRemainingMax = GeneralDate.ymd(ym.year(), ym.month(), 1);
                if (thisYearUsedInfo == null) {
                    continue;
                }
                /** 使用数 */
                ChildCareNurseUsedNumber usedNumber = thisYearUsedInfo.getUsedNumber();
                if (usedNumber == null) {
                    continue;
                }
                /** 日数 */
                DayNumberOfUse usedDay = usedNumber.getUsedDay();
                /** 時間 */
                Optional<TimeOfUse> usedTimes = usedNumber.getUsedTimes();
                // 使用日数←子の看護休暇月別残数データ．本年使用数．使用数．日数
                output.setDaysOfUse(output.getDaysOfUse() == null ? 0 : output.getDaysOfUse() +
                        usedDay.v());
                // 使用時間←子の看護休暇月別残数データ．本年使用数．使用数．時間
                usedTimes.ifPresent(timeOfUse -> output.setUsageTime(output.getUsageTime() == null ? 0 :
                        output.getUsageTime() + timeOfUse.v()));

                //※子の看護休暇月別残数データ．残数に限り、合算せずに締め期間．終了日が遅い方のみ保持する
                if (endDate.afterOrEquals(endDateRemainingMax)) {
                    //残日数:残日数　←子の看護休暇月別残数データ．本年使用数．使用数．日数
                    output.setRemainingDays(usedDay.v());
                    // 残時間:残時間　←子の看護休暇月別残数データ．本年使用数．使用数．時間
                    usedTimes.ifPresent(PrimitiveValueBase::v);
                }
            }
            listOuput.add(output);

        }
        return listOuput;
    }
}
