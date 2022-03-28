package nts.uk.ctx.at.record.infra.repository.daily.supportwork;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.daily.export.SupportWorkData;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.supportwork.DailyPerformanceSupportWorkRepository;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.WorkDetailData;
import nts.uk.ctx.at.record.infra.entity.daily.ouen.KrcdtDayOuenTime;
import nts.uk.ctx.at.record.infra.entity.daily.ouen.KrcdtDayOuenTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.ouen.KrcdtDayOuenTimeSheet;
import nts.uk.ctx.at.record.infra.entity.daily.timezone.KrcdtDayTsSupSupplInfo;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.*;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.*;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;
import org.apache.commons.lang3.tuple.Pair;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaDailyPerformanceSupportWorkRepository extends JpaRepository implements DailyPerformanceSupportWorkRepository {
    @Inject
    private OuenWorkTimeOfDailyRepo ouenWorkTimeOfDailyRepo;

    @Inject
    private OuenWorkTimeSheetOfDailyRepo ouenWorkTimeSheetOfDailyRepo;

    @Inject
    private AffiliationInforOfDailyPerforRepository affiliationInforOfDailyPerforRepo;

    @Override
    public List<WorkDetailData> getWorkDetailData(List<String> employeeIds, List<String> workplaceIds, DatePeriod period) {
        return ouenWorkTimeSheetOfDailyRepo.getWorkDetailData(employeeIds, workplaceIds, period);
    }

    @Override
    public void delete(String employeeId, GeneralDate date, int supportWorkFrameNo) {
        commandProxy().remove(KrcdtDayOuenTime.class, new KrcdtDayOuenTimePK(employeeId, date, supportWorkFrameNo));
        commandProxy().remove(KrcdtDayOuenTimeSheet.class, new KrcdtDayOuenTimePK(employeeId, date, supportWorkFrameNo));
    }

    @Override
    public SupportWorkData getAllSupportWorkDataForWorkingEmployeesByWorkplaces(String companyId, DatePeriod period, List<String> workplaceIds) {
        if (CollectionUtil.isEmpty(workplaceIds))
            return new SupportWorkData(
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );

        List<OuenWorkTimeOfDaily> ouenWorkTimeOfDailyList = new ArrayList<>();
        List<OuenWorkTimeSheetOfDaily> ouenWorkTimeSheetOfDailyList = new ArrayList<>();
        List<AffiliationInforOfDailyPerfor> affiliationInforList = new ArrayList<>();

        List<KrcdtDayOuenTimeSheet> ouenTimeSheets = this.queryProxy()
                .query("select o from KrcdtDayOuenTimeSheet o where o.companyId = :companyId " +
                        "and o.pk.ymd >= :startDate and o.pk.ymd <= :endDate and o.workplaceId in :workplaceIds", KrcdtDayOuenTimeSheet.class)
                .setParameter("companyId", companyId)
                .setParameter("startDate", period.start())
                .setParameter("endDate", period.end())
                .setParameter("workplaceIds", workplaceIds)
                .getList();

        getDataCommon(ouenWorkTimeOfDailyList, ouenWorkTimeSheetOfDailyList, affiliationInforList, ouenTimeSheets, period);

        return new SupportWorkData(
                ouenWorkTimeOfDailyList,
                ouenWorkTimeSheetOfDailyList,
                affiliationInforList
        );
    }

    @Override
    public SupportWorkData getAllSupportWorkDataForWorkingEmployeesByWorkLocations(String companyId, DatePeriod period, List<String> workLocationCodes) {
        if (CollectionUtil.isEmpty(workLocationCodes))
            return new SupportWorkData(
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );

        List<OuenWorkTimeOfDaily> ouenWorkTimeOfDailyList = new ArrayList<>();
        List<OuenWorkTimeSheetOfDaily> ouenWorkTimeSheetOfDailyList = new ArrayList<>();
        List<AffiliationInforOfDailyPerfor> affiliationInforList = new ArrayList<>();

        List<KrcdtDayOuenTimeSheet> ouenTimeSheets = this.queryProxy()
                .query("select o from KrcdtDayOuenTimeSheet o where o.companyId = :companyId " +
                        "and o.pk.ymd >= :startDate and o.pk.ymd <= :endDate and o.workLocationCode in :workLocationCodes", KrcdtDayOuenTimeSheet.class)
                .setParameter("companyId", companyId)
                .setParameter("startDate", period.start())
                .setParameter("endDate", period.end())
                .setParameter("workLocationCodes", workLocationCodes)
                .getList();

        getDataCommon(ouenWorkTimeOfDailyList, ouenWorkTimeSheetOfDailyList, affiliationInforList, ouenTimeSheets, period);

        return new SupportWorkData(
                ouenWorkTimeOfDailyList,
                ouenWorkTimeSheetOfDailyList,
                affiliationInforList
        );
    }

    @Override
    public SupportWorkData getAllSupportWorkDataForEmployeesComeToSupportByWorkplaces(String companyId, DatePeriod period, List<String> workplaceIds) {
        if (CollectionUtil.isEmpty(workplaceIds))
            return new SupportWorkData(
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );

        List<OuenWorkTimeOfDaily> ouenWorkTimeOfDailyList = new ArrayList<>();
        List<OuenWorkTimeSheetOfDaily> ouenWorkTimeSheetOfDailyList = new ArrayList<>();
        List<AffiliationInforOfDailyPerfor> affiliationInforList = new ArrayList<>();

        List<KrcdtDayOuenTimeSheet> ouenTimeSheets = this.queryProxy()
                .query("select o from KrcdtDayOuenTimeSheet o, KrcdtDayAffInfo i where o.pk.sid = i.krcdtDaiAffiliationInfPK.employeeId and o.pk.ymd = i.krcdtDaiAffiliationInfPK.ymd " +
                        "and o.companyId = :companyId and o.pk.ymd >= :startDate and o.pk.ymd <= :endDate and o.workplaceId in :workplaceIds and o.workplaceId != i.workplaceID", KrcdtDayOuenTimeSheet.class)
                .setParameter("companyId", companyId)
                .setParameter("startDate", period.start())
                .setParameter("endDate", period.end())
                .setParameter("workplaceIds", workplaceIds)
                .getList();

        getDataCommon(ouenWorkTimeOfDailyList, ouenWorkTimeSheetOfDailyList, affiliationInforList, ouenTimeSheets, period);

        return new SupportWorkData(
                ouenWorkTimeOfDailyList,
                ouenWorkTimeSheetOfDailyList,
                affiliationInforList
        );
    }

    @Override
    public SupportWorkData getAllSupportWorkDataForEmployeesComeToSupportByWorkLocations(String companyId, DatePeriod period, List<String> workLocationCodes) {
        if (CollectionUtil.isEmpty(workLocationCodes))
            return new SupportWorkData(
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );

        List<OuenWorkTimeOfDaily> ouenWorkTimeOfDailyList = new ArrayList<>();
        List<OuenWorkTimeSheetOfDaily> ouenWorkTimeSheetOfDailyList = new ArrayList<>();
        List<AffiliationInforOfDailyPerfor> affiliationInforList = new ArrayList<>();

        List<KrcdtDayOuenTimeSheet> ouenTimeSheets = this.queryProxy()
                .query("select o from KrcdtDayOuenTimeSheet o, KrcdtDayAffInfo i where o.pk.sid = i.krcdtDaiAffiliationInfPK.employeeId and o.pk.ymd = i.krcdtDaiAffiliationInfPK.ymd " +
                        "and o.companyId = :companyId and o.pk.ymd >= :startDate and o.pk.ymd <= :endDate and o.workLocationCode in :workLocationCodes ", KrcdtDayOuenTimeSheet.class)
//                        "and o.workLocationCode != i.workLocationCode", KrcdtDayOuenTimeSheet.class) TODO: i doesn't have work location code yet
                .setParameter("companyId", companyId)
                .setParameter("startDate", period.start())
                .setParameter("endDate", period.end())
                .setParameter("workLocationCodes", workLocationCodes)
                .getList();

        getDataCommon(ouenWorkTimeOfDailyList, ouenWorkTimeSheetOfDailyList, affiliationInforList, ouenTimeSheets, period);

        return new SupportWorkData(
                ouenWorkTimeOfDailyList,
                ouenWorkTimeSheetOfDailyList,
                affiliationInforList
        );
    }

    @Override
    public SupportWorkData getAllSupportWorkDataForEmployeesGoToSupportByWorkplaces(String companyId, DatePeriod period, List<String> workplaceIds) {
        if (CollectionUtil.isEmpty(workplaceIds))
            return new SupportWorkData(
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );

        List<OuenWorkTimeOfDaily> ouenWorkTimeOfDailyList = new ArrayList<>();
        List<OuenWorkTimeSheetOfDaily> ouenWorkTimeSheetOfDailyList = new ArrayList<>();
        List<AffiliationInforOfDailyPerfor> affiliationInforList = new ArrayList<>();

        List<KrcdtDayOuenTimeSheet> ouenTimeSheets = this.queryProxy()
                .query("select o from KrcdtDayOuenTimeSheet o, KrcdtDayAffInfo i where o.pk.sid = i.krcdtDaiAffiliationInfPK.employeeId and o.pk.ymd = i.krcdtDaiAffiliationInfPK.ymd " +
                        "and o.companyId = :companyId and o.pk.ymd >= :startDate and o.pk.ymd <= :endDate and i.workplaceID in :workplaceIds and o.workplaceId != i.workplaceID", KrcdtDayOuenTimeSheet.class)
                .setParameter("companyId", companyId)
                .setParameter("startDate", period.start())
                .setParameter("endDate", period.end())
                .setParameter("workplaceIds", workplaceIds)
                .getList();

        getDataCommon(ouenWorkTimeOfDailyList, ouenWorkTimeSheetOfDailyList, affiliationInforList, ouenTimeSheets, period);

        return new SupportWorkData(
                ouenWorkTimeOfDailyList,
                ouenWorkTimeSheetOfDailyList,
                affiliationInforList
        );
    }

    @Override
    public SupportWorkData getAllSupportWorkDataForEmployeesGoToSupportByWorkLocations(String companyId, DatePeriod period, List<String> workLocationCodes) {
        if (CollectionUtil.isEmpty(workLocationCodes))
            return new SupportWorkData(
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );

        List<OuenWorkTimeOfDaily> ouenWorkTimeOfDailyList = new ArrayList<>();
        List<OuenWorkTimeSheetOfDaily> ouenWorkTimeSheetOfDailyList = new ArrayList<>();
        List<AffiliationInforOfDailyPerfor> affiliationInforList = new ArrayList<>();

        List<KrcdtDayOuenTimeSheet> ouenTimeSheets = this.queryProxy()
                .query("select o from KrcdtDayOuenTimeSheet o, KrcdtDayAffInfo i where o.pk.sid = i.krcdtDaiAffiliationInfPK.employeeId and o.pk.ymd = i.krcdtDaiAffiliationInfPK.ymd " +
                        "and o.companyId = :companyId and o.pk.ymd >= :startDate and o.pk.ymd <= :endDate ", KrcdtDayOuenTimeSheet.class)
//                        "and i.workLocationCode in :workLocationCodes and o.workLocationCode != i.workLocationCode", KrcdtDayOuenTimeSheet.class) TODO: i doesn't have work location code yet
                .setParameter("companyId", companyId)
                .setParameter("startDate", period.start())
                .setParameter("endDate", period.end())
                .setParameter("workLocationCodes", workLocationCodes)
                .getList();

        getDataCommon(ouenWorkTimeOfDailyList, ouenWorkTimeSheetOfDailyList, affiliationInforList, ouenTimeSheets, period);

        return new SupportWorkData(
                ouenWorkTimeOfDailyList,
                ouenWorkTimeSheetOfDailyList,
                affiliationInforList
        );
    }

    @Override
    public List<OuenWorkTimeSheetOfDaily> get(String employeeId, List<GeneralDate> dates) {
        return ouenWorkTimeSheetOfDailyRepo.get(employeeId, dates);
    }

    private void getDataCommon(List<OuenWorkTimeOfDaily> ouenWorkTimeOfDailyList,
                        List<OuenWorkTimeSheetOfDaily> ouenWorkTimeSheetOfDailyList,
                        List<AffiliationInforOfDailyPerfor> affiliationInforList,
                        List<KrcdtDayOuenTimeSheet> ouenTimeSheets,
                        DatePeriod period) {
        List<String> employeeIds = ouenTimeSheets.stream().map(o -> o.pk.sid).distinct().collect(Collectors.toList());

        if (!employeeIds.isEmpty()) {
            List<KrcdtDayTsSupSupplInfo> krcdtDayTsSupSupplInfos = queryProxy()
                    .query("SELECT o FROM KrcdtDayTsSupSupplInfo o WHERE o.pk.sid IN :sids AND o.pk.date >= :startDate AND o.pk.date <= :endDate", KrcdtDayTsSupSupplInfo.class)
                    .setParameter("sids", employeeIds)
                    .setParameter("startDate", period.start())
                    .setParameter("endDate", period.end())
                    .getList();
            ouenWorkTimeSheetOfDailyList.addAll(ouenTimeSheets.stream()
                    .collect(Collectors.groupingBy(e -> Pair.of(e.pk.sid, e.pk.ymd)))
                    .entrySet().stream()
                    .map(entry -> toDomain(
                            entry.getValue(),
                            krcdtDayTsSupSupplInfos.stream().filter(i -> i.pk.sid.equals(entry.getKey().getLeft()) && i.pk.date.equals(entry.getKey().getRight())).collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList()));

            List<OuenWorkTimeOfDaily> ouenWorkTimeOfDailies = ouenWorkTimeOfDailyRepo.find(employeeIds, period);
            if (!ouenWorkTimeOfDailies.isEmpty()) {
                ouenWorkTimeOfDailyList.addAll(ouenWorkTimeOfDailies);
            }

            List<AffiliationInforOfDailyPerfor> affInforOfDailyPerfors = affiliationInforOfDailyPerforRepo.finds(employeeIds, period);
            if (!affInforOfDailyPerfors.isEmpty()) {
                affiliationInforList.addAll(affInforOfDailyPerfors);
            }
        }
    }

    private OuenWorkTimeSheetOfDaily toDomain(List<KrcdtDayOuenTimeSheet> es, List<KrcdtDayTsSupSupplInfo> infos) {
        List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet = es.stream().map(ots -> {
            Optional<KrcdtDayTsSupSupplInfo> krcdtDayTsSupSupplInfo = infos.stream()
                    .filter(i -> i.pk.sid.equals(ots.pk.sid) && i.pk.date.equals(ots.pk.ymd) && i.pk.supNo == ots.pk.ouenNo)
                    .findFirst();

            return OuenWorkTimeSheetOfDailyAttendance.create(
                    SupportFrameNo.of(ots.pk.ouenNo),
                    WorkContent.create(
                            WorkplaceOfWorkEachOuen.create(new WorkplaceId(ots.workplaceId), new WorkLocationCD(ots.workLocationCode)),
                            (ots.workCd1 == null && ots.workCd2 == null && ots.workCd3 == null && ots.workCd4 == null && ots.workCd5 == null) ? Optional.empty() :
                                    Optional.of(WorkGroup.create(ots.workCd1, ots.workCd2, ots.workCd3, ots.workCd4, ots.workCd5)),
                            krcdtDayTsSupSupplInfo.map(si -> toWorkSuppInfo(si))),
                    TimeSheetOfAttendanceEachOuenSheet.create(
                            new WorkNo(ots.workNo),
                            Optional.of(new WorkTimeInformation(
                                    new ReasonTimeChange(
                                            ots.startTimeChangeWay == null ? TimeChangeMeans.REAL_STAMP : EnumAdaptor.valueOf(ots.startTimeChangeWay, TimeChangeMeans.class),
                                            ots.startStampMethod == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(ots.startStampMethod, EngravingMethod.class))),
                                    ots.startTime == null ? null : new TimeWithDayAttr(ots.startTime))),
                            Optional.of(new WorkTimeInformation(
                                    new ReasonTimeChange(
                                            ots.endTimeChangeWay == null ? TimeChangeMeans.REAL_STAMP : EnumAdaptor.valueOf(ots.endTimeChangeWay, TimeChangeMeans.class),
                                            ots.endStampMethod == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(ots.endStampMethod, EngravingMethod.class))),
                                    ots.endTime == null ? null : new TimeWithDayAttr(ots.endTime)))), Optional.empty());
        }).collect(Collectors.toList());

        return OuenWorkTimeSheetOfDaily.create(es.get(0).pk.sid, es.get(0).pk.ymd, ouenTimeSheet);
    }

    private WorkSuppInfo toWorkSuppInfo(KrcdtDayTsSupSupplInfo ts) {
        //補足時間情報
        List<SuppInfoTimeItem> suppInfoTimeItems = new ArrayList<>();
        SuppInfoTimeItem suppInfoTimeItem1 = new SuppInfoTimeItem(new SuppInfoNo(1), ts.supplInfoTime1 == null ? null: new AttendanceTime(ts.supplInfoTime1));
        SuppInfoTimeItem suppInfoTimeItem2 = new SuppInfoTimeItem(new SuppInfoNo(2), ts.supplInfoTime2 == null ? null: new AttendanceTime(ts.supplInfoTime2));
        SuppInfoTimeItem suppInfoTimeItem3 = new SuppInfoTimeItem(new SuppInfoNo(3), ts.supplInfoTime3 == null ? null: new AttendanceTime(ts.supplInfoTime3));
        SuppInfoTimeItem suppInfoTimeItem4 = new SuppInfoTimeItem(new SuppInfoNo(4), ts.supplInfoTime4 == null ? null: new AttendanceTime(ts.supplInfoTime4));
        SuppInfoTimeItem suppInfoTimeItem5 = new SuppInfoTimeItem(new SuppInfoNo(5), ts.supplInfoTime5 == null ? null: new AttendanceTime(ts.supplInfoTime5));
        suppInfoTimeItems.add(suppInfoTimeItem1);
        suppInfoTimeItems.add(suppInfoTimeItem2);
        suppInfoTimeItems.add(suppInfoTimeItem3);
        suppInfoTimeItems.add(suppInfoTimeItem4);
        suppInfoTimeItems.add(suppInfoTimeItem5);

        //補足数値情報
        List<SuppInfoNumItem> suppInfoNumItems = new ArrayList<>();
        SuppInfoNumItem suppInfoNumItem1 = new SuppInfoNumItem(new SuppInfoNo(1), ts.supplInfoNumber1 == null ? null : new SuppNumValue(ts.supplInfoNumber1));
        SuppInfoNumItem suppInfoNumItem2 = new SuppInfoNumItem(new SuppInfoNo(2), ts.supplInfoNumber2 == null ? null : new SuppNumValue(ts.supplInfoNumber2));
        SuppInfoNumItem suppInfoNumItem3 = new SuppInfoNumItem(new SuppInfoNo(3), ts.supplInfoNumber3 == null ? null : new SuppNumValue(ts.supplInfoNumber3));
        SuppInfoNumItem suppInfoNumItem4 = new SuppInfoNumItem(new SuppInfoNo(4), ts.supplInfoNumber4 == null ? null : new SuppNumValue(ts.supplInfoNumber4));
        SuppInfoNumItem suppInfoNumItem5 = new SuppInfoNumItem(new SuppInfoNo(5), ts.supplInfoNumber5 == null ? null : new SuppNumValue(ts.supplInfoNumber5));
        suppInfoNumItems.add(suppInfoNumItem1);
        suppInfoNumItems.add(suppInfoNumItem2);
        suppInfoNumItems.add(suppInfoNumItem3);
        suppInfoNumItems.add(suppInfoNumItem4);
        suppInfoNumItems.add(suppInfoNumItem5);

        //補足コメント情報
        List<SuppInfoCommentItem> suppInfoCommentItems = new ArrayList<>();
        SuppInfoCommentItem suppInfoCommentItem1 = new SuppInfoCommentItem(new SuppInfoNo(1), ts.supplInfoComment1 == null ? null :  new WorkSuppComment(ts.supplInfoComment1));
        SuppInfoCommentItem suppInfoCommentItem2 = new SuppInfoCommentItem(new SuppInfoNo(2), ts.supplInfoComment2 == null ? null :  new WorkSuppComment(ts.supplInfoComment2));
        SuppInfoCommentItem suppInfoCommentItem3 = new SuppInfoCommentItem(new SuppInfoNo(3), ts.supplInfoComment3 == null ? null :  new WorkSuppComment(ts.supplInfoComment3));
        SuppInfoCommentItem suppInfoCommentItem4 = new SuppInfoCommentItem(new SuppInfoNo(4), ts.supplInfoComment4 == null ? null :  new WorkSuppComment(ts.supplInfoComment4));
        SuppInfoCommentItem suppInfoCommentItem5 = new SuppInfoCommentItem(new SuppInfoNo(5), ts.supplInfoComment5 == null ? null :  new WorkSuppComment(ts.supplInfoComment5));
        suppInfoCommentItems.add(suppInfoCommentItem1);
        suppInfoCommentItems.add(suppInfoCommentItem2);
        suppInfoCommentItems.add(suppInfoCommentItem3);
        suppInfoCommentItems.add(suppInfoCommentItem4);
        suppInfoCommentItems.add(suppInfoCommentItem5);

        //補足選択項目情報
        List<SuppInfoSelectionItem> suppInfoSelectionItems = new ArrayList<>();
        SuppInfoSelectionItem suppInfoSelectionItem1 = new SuppInfoSelectionItem(new SuppInfoNo(1), ts.supplInfoCode1 == null ? null : new ChoiceCode(ts.supplInfoCode1));
        SuppInfoSelectionItem suppInfoSelectionItem2 = new SuppInfoSelectionItem(new SuppInfoNo(2), ts.supplInfoCode2 == null ? null :  new ChoiceCode(ts.supplInfoCode2));
        SuppInfoSelectionItem suppInfoSelectionItem3 = new SuppInfoSelectionItem(new SuppInfoNo(3), ts.supplInfoCode3 == null ? null :  new ChoiceCode(ts.supplInfoCode3));
        SuppInfoSelectionItem suppInfoSelectionItem4 = new SuppInfoSelectionItem(new SuppInfoNo(4), ts.supplInfoCode4 == null ? null :  new ChoiceCode(ts.supplInfoCode4));
        SuppInfoSelectionItem suppInfoSelectionItem5 = new SuppInfoSelectionItem(new SuppInfoNo(5), ts.supplInfoCode5 == null ? null :  new ChoiceCode(ts.supplInfoCode5));
        suppInfoSelectionItems.add(suppInfoSelectionItem1);
        suppInfoSelectionItems.add(suppInfoSelectionItem2);
        suppInfoSelectionItems.add(suppInfoSelectionItem3);
        suppInfoSelectionItems.add(suppInfoSelectionItem4);
        suppInfoSelectionItems.add(suppInfoSelectionItem5);

        return new WorkSuppInfo(suppInfoTimeItems, suppInfoNumItems, suppInfoCommentItems, suppInfoSelectionItems);
    }
}
