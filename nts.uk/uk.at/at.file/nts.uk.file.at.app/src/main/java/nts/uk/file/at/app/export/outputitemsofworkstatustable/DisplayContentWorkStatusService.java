package nts.uk.file.at.app.export.outputitemsofworkstatustable;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AffComHistAdapter;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemServiceAdapter;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.CreateDisplayContentWorkStatusDService;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.StatusOfEmployee;


import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DisplayContentWorkStatusService extends ExportService<DisplayContentWorkStatusRequest> {
    @Inject
    private DisplayWorkStatusReportGenerator displayGenerator;

    @Inject
    private CreateDisplayContentWorkStatusDService createWorktatusDService;

    @Inject
    private AttendanceItemServiceAdapter itemServiceAdapter;
    @Inject
    private AffComHistAdapter affComHistAdapter;


    @Override
    protected void handle(ExportServiceContext<DisplayContentWorkStatusRequest> exportServiceContext) {
        val res = exportServiceContext.getQuery();
        
        val data = new DisplayContentReportData();
        this.displayGenerator.generate(exportServiceContext.getGeneratorContext(),data);
    }
    public class RequireImpl implements  CreateDisplayContentWorkStatusDService.Require{

        @Override
        public List<StatusOfEmployee> getListAffComHistByListSidAndPeriod(List<String> sid, DatePeriod datePeriod) {
            return affComHistAdapter.getListAffComHist(sid,datePeriod);
        }

        @Override
        public AttendanceResultDto getValueOf(String employeeId, GeneralDate workingDate, Collection<Integer> itemIds) {
            return itemServiceAdapter.getValueOf(employeeId,workingDate,itemIds);
        }
    }
}
