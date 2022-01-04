package nts.uk.ctx.at.record.pubimp.daily.supportwork;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.daily.export.SupportWorkData;
import nts.uk.ctx.at.record.dom.daily.supportwork.DailyPerformanceSupportWorkRepository;
import nts.uk.ctx.at.record.pub.daily.supportwork.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SupportWorkDataPubImpl implements SupportWorkDataPub {
    @Inject
    private DailyPerformanceSupportWorkRepository dailyPerformanceSupportWorkRepo;

    @Override
    public SupportWorkDataExport getSupportWorkDataForWorkingEmployeeByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds) {
        SupportWorkData data = dailyPerformanceSupportWorkRepo.getAllSupportWorkDataForWorkingEmployeesByWorkplaces(companyId, period, workplaceIds);
        return new SupportWorkDataExport(
                data.getOuenWorkTimeOfDailyList().stream().map(i -> new OuenWorkTimeOfDailyExport(i.getEmpId(), i.getYmd(), i.getOuenTimes())).collect(Collectors.toList()),
                data.getOuenWorkTimeSheetOfDailyList().stream().map(i -> new OuenWorkTimeSheetOfDailyExport(i.getEmpId(), i.getYmd(), i.getOuenTimeSheet())).collect(Collectors.toList()),
                data.getAffiliationInforList().stream().map(i -> new AffiliationInforOfDailyPerforExport(i.getEmployeeId(), i.getYmd(), i.getAffiliationInfor())).collect(Collectors.toList())
        );
    }

    @Override
    public SupportWorkDataExport getSupportWorkDataForWorkingEmployeeByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes) {
        SupportWorkData data = dailyPerformanceSupportWorkRepo.getAllSupportWorkDataForWorkingEmployeesByWorkLocations(companyId, period, workLocationCodes);
        return new SupportWorkDataExport(
                data.getOuenWorkTimeOfDailyList().stream().map(i -> new OuenWorkTimeOfDailyExport(i.getEmpId(), i.getYmd(), i.getOuenTimes())).collect(Collectors.toList()),
                data.getOuenWorkTimeSheetOfDailyList().stream().map(i -> new OuenWorkTimeSheetOfDailyExport(i.getEmpId(), i.getYmd(), i.getOuenTimeSheet())).collect(Collectors.toList()),
                data.getAffiliationInforList().stream().map(i -> new AffiliationInforOfDailyPerforExport(i.getEmployeeId(), i.getYmd(), i.getAffiliationInfor())).collect(Collectors.toList())
        );
    }

    @Override
    public SupportWorkDataExport getSupportWorkDataForEmployeeComeToSupportByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds) {
        SupportWorkData data = dailyPerformanceSupportWorkRepo.getAllSupportWorkDataForEmployeesComeToSupportByWorkplaces(companyId, period, workplaceIds);
        return new SupportWorkDataExport(
                data.getOuenWorkTimeOfDailyList().stream().map(i -> new OuenWorkTimeOfDailyExport(i.getEmpId(), i.getYmd(), i.getOuenTimes())).collect(Collectors.toList()),
                data.getOuenWorkTimeSheetOfDailyList().stream().map(i -> new OuenWorkTimeSheetOfDailyExport(i.getEmpId(), i.getYmd(), i.getOuenTimeSheet())).collect(Collectors.toList()),
                data.getAffiliationInforList().stream().map(i -> new AffiliationInforOfDailyPerforExport(i.getEmployeeId(), i.getYmd(), i.getAffiliationInfor())).collect(Collectors.toList())
        );
    }

    @Override
    public SupportWorkDataExport getSupportWorkDataForEmployeeComeToSupportByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes) {
        SupportWorkData data = dailyPerformanceSupportWorkRepo.getAllSupportWorkDataForEmployeesComeToSupportByWorkLocations(companyId, period, workLocationCodes);
        return new SupportWorkDataExport(
                data.getOuenWorkTimeOfDailyList().stream().map(i -> new OuenWorkTimeOfDailyExport(i.getEmpId(), i.getYmd(), i.getOuenTimes())).collect(Collectors.toList()),
                data.getOuenWorkTimeSheetOfDailyList().stream().map(i -> new OuenWorkTimeSheetOfDailyExport(i.getEmpId(), i.getYmd(), i.getOuenTimeSheet())).collect(Collectors.toList()),
                data.getAffiliationInforList().stream().map(i -> new AffiliationInforOfDailyPerforExport(i.getEmployeeId(), i.getYmd(), i.getAffiliationInfor())).collect(Collectors.toList())
        );
    }

    @Override
    public SupportWorkDataExport getSupportWorkDataForEmployeeGoToSupportByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds) {
        SupportWorkData data = dailyPerformanceSupportWorkRepo.getAllSupportWorkDataForEmployeesGoToSupportByWorkplaces(companyId, period, workplaceIds);
        return new SupportWorkDataExport(
                data.getOuenWorkTimeOfDailyList().stream().map(i -> new OuenWorkTimeOfDailyExport(i.getEmpId(), i.getYmd(), i.getOuenTimes())).collect(Collectors.toList()),
                data.getOuenWorkTimeSheetOfDailyList().stream().map(i -> new OuenWorkTimeSheetOfDailyExport(i.getEmpId(), i.getYmd(), i.getOuenTimeSheet())).collect(Collectors.toList()),
                data.getAffiliationInforList().stream().map(i -> new AffiliationInforOfDailyPerforExport(i.getEmployeeId(), i.getYmd(), i.getAffiliationInfor())).collect(Collectors.toList())
        );
    }

    @Override
    public SupportWorkDataExport getSupportWorkDataForEmployeeGoToSupportByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes) {
        SupportWorkData data = dailyPerformanceSupportWorkRepo.getAllSupportWorkDataForEmployeesGoToSupportByWorkLocations(companyId, period, workLocationCodes);
        return new SupportWorkDataExport(
                data.getOuenWorkTimeOfDailyList().stream().map(i -> new OuenWorkTimeOfDailyExport(i.getEmpId(), i.getYmd(), i.getOuenTimes())).collect(Collectors.toList()),
                data.getOuenWorkTimeSheetOfDailyList().stream().map(i -> new OuenWorkTimeSheetOfDailyExport(i.getEmpId(), i.getYmd(), i.getOuenTimeSheet())).collect(Collectors.toList()),
                data.getAffiliationInforList().stream().map(i -> new AffiliationInforOfDailyPerforExport(i.getEmployeeId(), i.getYmd(), i.getAffiliationInfor())).collect(Collectors.toList())
        );
    }
}
