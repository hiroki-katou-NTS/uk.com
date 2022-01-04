package nts.uk.ctx.at.record.infra.repository.daily.supportwork;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.daily.export.SupportWorkData;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.supportwork.DailyPerformanceSupportWorkRepository;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.WorkDetailData;
import nts.uk.ctx.at.record.infra.entity.daily.ouen.KrcdtDayOuenTime;
import nts.uk.ctx.at.record.infra.entity.daily.ouen.KrcdtDayOuenTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.ouen.KrcdtDayOuenTimeSheet;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
        return new SupportWorkData(
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Override
    public SupportWorkData getAllSupportWorkDataForWorkingEmployeesByWorkLocations(String companyId, DatePeriod period, List<String> workLocationCodes) {
        return new SupportWorkData(
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Override
    public SupportWorkData getAllSupportWorkDataForEmployeesComeToSupportByWorkplaces(String companyId, DatePeriod period, List<String> workplaceIds) {
        return new SupportWorkData(
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Override
    public SupportWorkData getAllSupportWorkDataForEmployeesComeToSupportByWorkLocations(String companyId, DatePeriod period, List<String> workLocationCodes) {
        return new SupportWorkData(
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Override
    public SupportWorkData getAllSupportWorkDataForEmployeesGoToSupportByWorkplaces(String companyId, DatePeriod period, List<String> workplaceIds) {
        return new SupportWorkData(
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Override
    public SupportWorkData getAllSupportWorkDataForEmployeesGoToSupportByWorkLocations(String companyId, DatePeriod period, List<String> workLocationCodes) {
        return new SupportWorkData(
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Override
    public List<OuenWorkTimeSheetOfDaily> get(String employeeId, List<GeneralDate> dates) {
        return ouenWorkTimeSheetOfDailyRepo.get(employeeId, dates);
    }
}
