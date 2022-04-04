package nts.uk.ctx.at.function.dom.supportworklist.outputsetting;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.DailyAttendanceItemAdapter;
import nts.uk.ctx.at.function.dom.adapter.DailyAttendanceItemAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.SupportWorkDataAdapter;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.SupportWorkDataImport;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkPlaceInforExport;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSetting;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSettingRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class SupportWorkOutputDataRequireImpl implements SupportWorkOutputDataRequire {
    private SupportWorkAggregationSettingRepository supportWorkAggregationSettingRepo;
    private DailyAttendanceItemAdapter dailyAttendanceItemAdapter;
    private WorkplaceAdapter workplaceAdapter;
    private SupportWorkDataAdapter supportWorkDataAdapter;

    @Override
    public Optional<SupportWorkAggregationSetting> getSetting(String companyId) {
        return supportWorkAggregationSettingRepo.get(companyId);
    }

    @Override
    public List<DailyAttendanceItemAdapterDto> getDailyAttendanceItems(String companyId, List<Integer> attendanceItemIds) {
        return dailyAttendanceItemAdapter.getDailyAttendanceItem(companyId, attendanceItemIds);
    }

    @Override
    public List<WorkPlaceInforExport> getWorkplaceInfos(String companyId, List<String> workplaceIds, GeneralDate baseDate) {
        return workplaceAdapter.getWorkplaceInforByWkpIds(companyId, workplaceIds, baseDate);
    }

    @Override
    public SupportWorkDataImport getSupportWorkDataForWorkingEmployeeByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds) {
        return supportWorkDataAdapter.getSupportWorkDataForWorkingEmployeeByWorkplace(companyId, period, workplaceIds);
    }

    @Override
    public SupportWorkDataImport getSupportWorkDataForWorkingEmployeeByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes) {
        return supportWorkDataAdapter.getSupportWorkDataForWorkingEmployeeByWorkLocation(companyId, period, workLocationCodes);
    }

    @Override
    public SupportWorkDataImport getSupportWorkDataForEmployeeComeToSupportByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds) {
        return supportWorkDataAdapter.getSupportWorkDataForEmployeeComeToSupportByWorkplace(companyId, period, workplaceIds);
    }

    @Override
    public SupportWorkDataImport getSupportWorkDataForEmployeeComeToSupportByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes) {
        return supportWorkDataAdapter.getSupportWorkDataForEmployeeComeToSupportByWorkLocation(companyId, period, workLocationCodes);
    }

    @Override
    public SupportWorkDataImport getSupportWorkDataForEmployeeGoToSupportByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds) {
        return supportWorkDataAdapter.getSupportWorkDataForEmployeeGoToSupportByWorkplace(companyId, period, workplaceIds);
    }

    @Override
    public SupportWorkDataImport getSupportWorkDataForEmployeeGoToSupportByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes) {
        return supportWorkDataAdapter.getSupportWorkDataForEmployeeGoToSupportByWorkLocation(companyId, period, workLocationCodes);
    }
}
