package nts.uk.ctx.at.function.ac.supportworkdata;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.*;
import nts.uk.ctx.at.record.pub.daily.supportwork.SupportWorkDataExport;
import nts.uk.ctx.at.record.pub.daily.supportwork.SupportWorkDataPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SupportWorkDataAdapterImpl implements SupportWorkDataAdapter {
    @Inject
    private SupportWorkDataPub supportWorkDataPub;

    @Override
    public SupportWorkDataImport getSupportWorkDataForWorkingEmployeeByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds) {
        SupportWorkDataExport dataExport = supportWorkDataPub.getSupportWorkDataForWorkingEmployeeByWorkplace(companyId, period, workplaceIds);
        return new SupportWorkDataImport(
                dataExport.getOuenWorkTimeOfDailyList().stream().map(i -> new OuenWorkTimeOfDailyImport(i.getEmpId(), i.getYmd(), i.getOuenTimes())).collect(Collectors.toList()),
                dataExport.getOuenWorkTimeSheetOfDailyList().stream().map(i -> new OuenWorkTimeSheetOfDailyImport(i.getEmpId(), i.getYmd(), i.getOuenTimeSheet())).collect(Collectors.toList()),
                dataExport.getAffiliationInforList().stream().map(i -> new AffiliationInforOfDailyPerforImport(i.getEmployeeId(), i.getYmd(), i.getAffiliationInfor())).collect(Collectors.toList())
        );
    }

    @Override
    public SupportWorkDataImport getSupportWorkDataForWorkingEmployeeByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes) {
        SupportWorkDataExport dataExport = supportWorkDataPub.getSupportWorkDataForWorkingEmployeeByWorkLocation(companyId, period, workLocationCodes);
        return new SupportWorkDataImport(
                dataExport.getOuenWorkTimeOfDailyList().stream().map(i -> new OuenWorkTimeOfDailyImport(i.getEmpId(), i.getYmd(), i.getOuenTimes())).collect(Collectors.toList()),
                dataExport.getOuenWorkTimeSheetOfDailyList().stream().map(i -> new OuenWorkTimeSheetOfDailyImport(i.getEmpId(), i.getYmd(), i.getOuenTimeSheet())).collect(Collectors.toList()),
                dataExport.getAffiliationInforList().stream().map(i -> new AffiliationInforOfDailyPerforImport(i.getEmployeeId(), i.getYmd(), i.getAffiliationInfor())).collect(Collectors.toList())
        );
    }

    @Override
    public SupportWorkDataImport getSupportWorkDataForEmployeeComeToSupportByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds) {
        SupportWorkDataExport dataExport = supportWorkDataPub.getSupportWorkDataForEmployeeComeToSupportByWorkplace(companyId, period, workplaceIds);
        return new SupportWorkDataImport(
                dataExport.getOuenWorkTimeOfDailyList().stream().map(i -> new OuenWorkTimeOfDailyImport(i.getEmpId(), i.getYmd(), i.getOuenTimes())).collect(Collectors.toList()),
                dataExport.getOuenWorkTimeSheetOfDailyList().stream().map(i -> new OuenWorkTimeSheetOfDailyImport(i.getEmpId(), i.getYmd(), i.getOuenTimeSheet())).collect(Collectors.toList()),
                dataExport.getAffiliationInforList().stream().map(i -> new AffiliationInforOfDailyPerforImport(i.getEmployeeId(), i.getYmd(), i.getAffiliationInfor())).collect(Collectors.toList())
        );
    }

    @Override
    public SupportWorkDataImport getSupportWorkDataForEmployeeComeToSupportByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes) {
        SupportWorkDataExport dataExport = supportWorkDataPub.getSupportWorkDataForEmployeeComeToSupportByWorkLocation(companyId, period, workLocationCodes);
        return new SupportWorkDataImport(
                dataExport.getOuenWorkTimeOfDailyList().stream().map(i -> new OuenWorkTimeOfDailyImport(i.getEmpId(), i.getYmd(), i.getOuenTimes())).collect(Collectors.toList()),
                dataExport.getOuenWorkTimeSheetOfDailyList().stream().map(i -> new OuenWorkTimeSheetOfDailyImport(i.getEmpId(), i.getYmd(), i.getOuenTimeSheet())).collect(Collectors.toList()),
                dataExport.getAffiliationInforList().stream().map(i -> new AffiliationInforOfDailyPerforImport(i.getEmployeeId(), i.getYmd(), i.getAffiliationInfor())).collect(Collectors.toList())
        );
    }

    @Override
    public SupportWorkDataImport getSupportWorkDataForEmployeeGoToSupportByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds) {
        SupportWorkDataExport dataExport = supportWorkDataPub.getSupportWorkDataForEmployeeGoToSupportByWorkplace(companyId, period, workplaceIds);
        return new SupportWorkDataImport(
                dataExport.getOuenWorkTimeOfDailyList().stream().map(i -> new OuenWorkTimeOfDailyImport(i.getEmpId(), i.getYmd(), i.getOuenTimes())).collect(Collectors.toList()),
                dataExport.getOuenWorkTimeSheetOfDailyList().stream().map(i -> new OuenWorkTimeSheetOfDailyImport(i.getEmpId(), i.getYmd(), i.getOuenTimeSheet())).collect(Collectors.toList()),
                dataExport.getAffiliationInforList().stream().map(i -> new AffiliationInforOfDailyPerforImport(i.getEmployeeId(), i.getYmd(), i.getAffiliationInfor())).collect(Collectors.toList())
        );
    }

    @Override
    public SupportWorkDataImport getSupportWorkDataForEmployeeGoToSupportByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes) {
        SupportWorkDataExport dataExport = supportWorkDataPub.getSupportWorkDataForEmployeeGoToSupportByWorkLocation(companyId, period, workLocationCodes);
        return new SupportWorkDataImport(
                dataExport.getOuenWorkTimeOfDailyList().stream().map(i -> new OuenWorkTimeOfDailyImport(i.getEmpId(), i.getYmd(), i.getOuenTimes())).collect(Collectors.toList()),
                dataExport.getOuenWorkTimeSheetOfDailyList().stream().map(i -> new OuenWorkTimeSheetOfDailyImport(i.getEmpId(), i.getYmd(), i.getOuenTimeSheet())).collect(Collectors.toList()),
                dataExport.getAffiliationInforList().stream().map(i -> new AffiliationInforOfDailyPerforImport(i.getEmployeeId(), i.getYmd(), i.getAffiliationInfor())).collect(Collectors.toList())
        );
    }
}
