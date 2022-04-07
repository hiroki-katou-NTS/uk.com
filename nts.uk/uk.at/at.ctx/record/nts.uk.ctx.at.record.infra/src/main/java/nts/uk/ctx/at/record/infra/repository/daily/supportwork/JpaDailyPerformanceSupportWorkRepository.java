package nts.uk.ctx.at.record.infra.repository.daily.supportwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

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

            return ots.domain(krcdtDayTsSupSupplInfo);
        }).collect(Collectors.toList());

        return OuenWorkTimeSheetOfDaily.create(es.get(0).pk.sid, es.get(0).pk.ymd, ouenTimeSheet);
    }
}
