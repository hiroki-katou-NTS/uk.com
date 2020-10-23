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
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;


import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
//        RequireImpl require = new RequireImpl(itemServiceAdapter,affComHistAdapter);
//        val data = new DisplayContentReportData();
//        val datePeriod = new DatePeriod(param.getStartDate(),param.getEndDate());
//        val listData = CreateDisplayContentWorkStatusDService.displayContentsOfWorkStatus(require,datePeriod,
//                param.getEmployeeInfoList(),param.getOutputSettings(),param.getWorkPlaceInfo());
//
//        // TODO
       // val periodDate = new DatePeriod(param.getStartDate(), param.getEndDate());
       val  periodDate = new DatePeriod(GeneralDate.today(),GeneralDate.today().addDays(19));
        val data = new DisplayContentReportData(Arrays.asList(
                new DisplayContentWorkStatus(
                        "eplCode01",
                        "eplName01",
                        "wplCode01",
                        "wplName01",
                        Arrays.asList(
                                new OutputItemOneLine(
                                        0D,
                                        "itemName01",
                                        Arrays.asList(
                                                new DailyValue(
                                                        0D,
                                                        EnumAdaptor.valueOf(1, CommonAttributesOfForms.class),
                                                        "ABCABC",
                                                        GeneralDate.today()
                                                ),
                                                new DailyValue(
                                                        0D,
                                                        EnumAdaptor.valueOf(1, CommonAttributesOfForms.class),
                                                        "",
                                                        GeneralDate.today().addDays(1)
                                                )
                                        )
                                ),
                                new OutputItemOneLine(
                                        0D,
                                        "itemName02",
                                        Arrays.asList(
                                                new DailyValue(
                                                        0D,
                                                        EnumAdaptor.valueOf(2, CommonAttributesOfForms.class),
                                                        "",
                                                        GeneralDate.today()
                                                ),
                                                new DailyValue(
                                                        0D,
                                                        EnumAdaptor.valueOf(2, CommonAttributesOfForms.class),
                                                        "",
                                                        GeneralDate.today().addDays(1)
                                                )
                                        )
                                ))
                )
        ), periodDate, 1);
        this.displayGenerator.generate(exportServiceContext.getGeneratorContext(), data);
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
