package nts.uk.file.at.app.export.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AffComHistAdapter;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemServiceAdapter;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.*;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.ExportExcelDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.OutPutWorkStatusContent;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;


import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DisplayContentWorkStatusService extends ExportService<DisplayContentWorkStatusRequest> {
    @Inject
    private DisplayWorkStatusReportGenerator displayGenerator;
    @Inject
    private AttendanceItemServiceAdapter itemServiceAdapter;
    @Inject
    private AffComHistAdapter affComHistAdapter;


    @Override
    protected void handle(ExportServiceContext<DisplayContentWorkStatusRequest> exportServiceContext) {
        val param = exportServiceContext.getQuery();
        RequireImpl require = new RequireImpl(itemServiceAdapter,affComHistAdapter);
        val datePeriod = new DatePeriod(param.getStartDate(),param.getEndDate());
        val listData = CreateDisplayContentWorkStatusDService.displayContentsOfWorkStatus(require,datePeriod,
                param.getEmployeeInfoList(),param.getOutputSettings(),param.getWorkPlaceInfo());
        val periodDate = new DatePeriod(param.getStartDate(), param.getEndDate());

        val listRs = new ArrayList<ExportExcelDto>();
        for (int i = 0; i < listData.size() ; i++) {
            val wplCode = listData.get(i).getWorkPlaceCode();
            val item = listData.stream().filter(e->e.getWorkPlaceCode().equals(wplCode)).map(j->new DisplayContentWorkStatus(
                    j.getEmployeeCode(),
                    j.getEmployeeName(),
                    j.getWorkPlaceCode(),
                    j.getWorkPlaceName(),
                    j.getOutputItemOneLines()
            )).collect(Collectors.toList());
            listRs.add(new ExportExcelDto(
                    listData.get(i).getWorkPlaceCode(),
                    listData.get(i).getWorkPlaceName(),
                    item
            ));
        }
        val result = new OutPutWorkStatusContent(
                listRs,
                periodDate,
                param.getMode()
        );

        this.displayGenerator.generate(exportServiceContext.getGeneratorContext(), result);
    }

    @AllArgsConstructor
    public class RequireImpl implements CreateDisplayContentWorkStatusDService.Require {
        private AttendanceItemServiceAdapter itemServiceAdapter;
        private AffComHistAdapter affComHistAdapter;
        @Override
        public List<StatusOfEmployee> getListAffComHistByListSidAndPeriod(List<String> sid, DatePeriod datePeriod) {
            return affComHistAdapter.getListAffComHist(sid, datePeriod);
        }
        @Override
        public AttendanceResultDto getValueOf(String employeeId, GeneralDate workingDate, Collection<Integer> itemIds) {
            return itemServiceAdapter.getValueOf(employeeId, workingDate, itemIds);
        }
    }

}
