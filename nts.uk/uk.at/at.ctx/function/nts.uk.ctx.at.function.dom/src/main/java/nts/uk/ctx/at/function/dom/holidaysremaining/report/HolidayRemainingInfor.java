package nts.uk.ctx.at.function.dom.holidaysremaining.report;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.child.ChildNursingLeaveThisMonthFutureSituation;
import nts.uk.ctx.at.function.dom.adapter.child.NursingCareLeaveThisMonthFutureSituation;
import nts.uk.ctx.at.function.dom.adapter.holidayover60h.AggrResultOfHolidayOver60hImport;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.*;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialVacationImported;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialVacationImportedKdr;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReserveHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReservedYearHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.RsvLeaUsedCurrentMonImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.CurrentHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.StatusHolidayImported;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.ChildNursingLeaveStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.NursingCareLeaveMonthlyRemaining;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureInfo;

@Setter
@Getter
public class HolidayRemainingInfor {

    // RequestList369
    private Optional<GeneralDate> grantDate;
    // RequestList281
    private List<AnnLeaGrantNumberImported> listAnnLeaGrantNumber;
    // RequestList265
    private AnnLeaveOfThisMonthImported annLeaveOfThisMonth;
    // RequestList255
    private List<AnnualLeaveUsageImported> listAnnualLeaveUsage;
    // RequestList363
    private List<AnnLeaveUsageStatusOfThisMonthImported> listAnnLeaveUsageStatusOfThisMonth;
    // RequestList268
    private ReserveHolidayImported reserveHoliday;
    // RequestList258
    private List<ReservedYearHolidayImported> listReservedYearHoliday;
    // RequestList364
    private List<RsvLeaUsedCurrentMonImported> listRsvLeaUsedCurrentMon;
    // RequestList269
    private List<CurrentHolidayImported> listCurrentHoliday;
    // RequestList259
    private List<StatusHolidayImported> listStatusHoliday;
    // RequestList270
    private List<CurrentHolidayRemainImported> listCurrentHolidayRemain;
    // RequestList260
    private List<StatusOfHolidayImported> listStatusOfHoliday;
    // RequestList273
    private Map<Integer, SpecialVacationImported> mapSpecialVacation;
    private Map<YearMonth, Map<Integer, SpecialVacationImported>> mapSPVaCrurrentMonth;

    // RequestList263
    private Map<Integer, List<SpecialHolidayImported>> mapListSpecialHoliday;
    // RequestList206
    private ChildNursingLeaveCurrentSituationImported childNursingLeave;
    // RequestList207
    private NursingLeaveCurrentSituationImported nursingLeave;
    //
    private CurrentHolidayImported currentHolidayLeft;

    private CurrentHolidayRemainImported currentHolidayRemainLeft;
    private SubstituteHolidayAggrResult substituteHolidayAggrResult;
    //RequestList204
    private CompenLeaveAggrResult compenLeaveAggrResult;
    //RQ 677
    private AggrResultOfHolidayOver60hImport aggrResultOfHolidayOver60h;
    //363
    List<AggrResultOfAnnualLeaveEachMonthKdr> rs363New;
    List<SpecialHolidayRemainDataOutputKdr> getSpeHdOfConfMonVer2;
    Map<YearMonth,SubstituteHolidayAggrResult> substituteHolidayAggrResultsRight;
    Optional<ClosureInfo> closureInforOpt;
    Map<YearMonth, Map<Integer, SpecialVacationImportedKdr>> lstMap273CurrMon;
    Map<Integer, SpecialVacationImportedKdr> map273New;
    List<ChildNursingLeaveStatus> monthlyConfirmedCareForEmployees;
    List<NursingCareLeaveMonthlyRemaining> obtainMonthlyConfirmedCareForEmployees;
    ChildNursingLeaveThisMonthFutureSituation childCareRemNumWithinPeriodLeft;
    List<ChildNursingLeaveThisMonthFutureSituation> childCareRemNumWithinPeriodRight;

    List<NursingCareLeaveThisMonthFutureSituation> nursingCareLeaveThisMonthFutureSituationRight;
    NursingCareLeaveThisMonthFutureSituation nursingCareLeaveThisMonthFutureSituationLeft ;

    public HolidayRemainingInfor(Optional<GeneralDate> grantDate,
                                 List<AnnLeaGrantNumberImported> listAnnLeaGrantNumber,
                                 AnnLeaveOfThisMonthImported annLeaveOfThisMonth,
                                 List<AnnualLeaveUsageImported> listAnnualLeaveUsage,
                                 List<AnnLeaveUsageStatusOfThisMonthImported> listAnnLeaveUsageStatusOfThisMonth,
                                 ReserveHolidayImported reserveHoliday,
                                 List<ReservedYearHolidayImported> listReservedYearHoliday,
                                 List<RsvLeaUsedCurrentMonImported> listRsvLeaUsedCurrentMon,
                                 List<CurrentHolidayImported> listCurrentHoliday,
                                 List<StatusHolidayImported> listStatusHoliday,
                                 List<CurrentHolidayRemainImported> listCurrentHolidayRemain,
                                 List<StatusOfHolidayImported> listStatusOfHoliday,
                                 Map<Integer, SpecialVacationImported> mapSpecialVacation,
                                 Map<YearMonth, Map<Integer, SpecialVacationImported>> mapSPVaCrurrentMonth,
                                 Map<Integer, List<SpecialHolidayImported>> mapListSpecialHoliday,
                                 ChildNursingLeaveCurrentSituationImported childNursingLeave,
                                 NursingLeaveCurrentSituationImported nursingLeave,
                                 CurrentHolidayImported currentHolidayLeft,
                                 CurrentHolidayRemainImported currentHolidayRemainLeft,
                                 SubstituteHolidayAggrResult substituteHolidayAggrResult,
                                 CompenLeaveAggrResult compenLeaveAggrResult,
                                 AggrResultOfHolidayOver60hImport aggrResultOfHolidayOver60h,
                                 List<AggrResultOfAnnualLeaveEachMonthKdr> rs363New,
                                 List<SpecialHolidayRemainDataOutputKdr> getSpeHdOfConfMonVer2,
                                 Map<YearMonth,SubstituteHolidayAggrResult> substituteHolidayAggrResultsRight,
                                 Optional<ClosureInfo> closureInforOpt,
                                 Map<YearMonth, Map<Integer, SpecialVacationImportedKdr>> lstMap273CurrMon,
                                 Map<Integer, SpecialVacationImportedKdr> map273New,
                                 List<ChildNursingLeaveStatus> monthlyConfirmedCareForEmployees,
                                 List<NursingCareLeaveMonthlyRemaining> obtainMonthlyConfirmedCareForEmployees,
                                 ChildNursingLeaveThisMonthFutureSituation childCareRemNumWithinPeriodLeft,
                                 List<ChildNursingLeaveThisMonthFutureSituation> childCareRemNumWithinPeriodRight,
                                 List<NursingCareLeaveThisMonthFutureSituation> nursingCareLeaveThisMonthFutureSituationRight,
                                 NursingCareLeaveThisMonthFutureSituation nursingCareLeaveThisMonthFutureSituationLeft

    ) {
        super();
        this.grantDate = grantDate;
        this.listAnnLeaGrantNumber = listAnnLeaGrantNumber;
        this.annLeaveOfThisMonth = annLeaveOfThisMonth;
        this.listAnnualLeaveUsage = listAnnualLeaveUsage;
        this.listAnnLeaveUsageStatusOfThisMonth = listAnnLeaveUsageStatusOfThisMonth;
        this.reserveHoliday = reserveHoliday;
        this.listReservedYearHoliday = listReservedYearHoliday;
        this.listRsvLeaUsedCurrentMon = listRsvLeaUsedCurrentMon;
        this.listCurrentHoliday = listCurrentHoliday;
        this.listStatusHoliday = listStatusHoliday;
        this.listCurrentHolidayRemain = listCurrentHolidayRemain;
        this.listStatusOfHoliday = listStatusOfHoliday;
        this.mapSpecialVacation = mapSpecialVacation;
        this.mapSPVaCrurrentMonth = mapSPVaCrurrentMonth;
        this.mapListSpecialHoliday = mapListSpecialHoliday;
        this.childNursingLeave = childNursingLeave;
        this.nursingLeave = nursingLeave;
        this.currentHolidayLeft = currentHolidayLeft;
        this.currentHolidayRemainLeft = currentHolidayRemainLeft;
        this.substituteHolidayAggrResult = substituteHolidayAggrResult;
        this.compenLeaveAggrResult = compenLeaveAggrResult;
        this.aggrResultOfHolidayOver60h = aggrResultOfHolidayOver60h;
        this.rs363New = rs363New;
        this.getSpeHdOfConfMonVer2 = getSpeHdOfConfMonVer2;
        this.substituteHolidayAggrResultsRight = substituteHolidayAggrResultsRight;
        this.closureInforOpt = closureInforOpt;
        this.lstMap273CurrMon = lstMap273CurrMon;
        this.map273New = map273New;
        this.monthlyConfirmedCareForEmployees = monthlyConfirmedCareForEmployees;
        this.obtainMonthlyConfirmedCareForEmployees = obtainMonthlyConfirmedCareForEmployees;
        this.childCareRemNumWithinPeriodRight = childCareRemNumWithinPeriodRight;
        this.childCareRemNumWithinPeriodLeft = childCareRemNumWithinPeriodLeft;
        this.nursingCareLeaveThisMonthFutureSituationLeft = nursingCareLeaveThisMonthFutureSituationLeft;
        this.nursingCareLeaveThisMonthFutureSituationRight = nursingCareLeaveThisMonthFutureSituationRight;
    }
}
