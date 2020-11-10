package nts.uk.ctx.at.function.ws.outputworkstatustable;


import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.outputworkstatustable.*;
import nts.uk.ctx.at.function.app.query.outputworkstatustable.*;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.FormOutputItemName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailSelectionAttendanceItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("at/function/kwr")
@Produces("application/json")
public class OutputItemSettingWebService extends WebService {

    @Inject
    private GetOutputItemSettingQuery settingQuery;

    @Inject
    private GetBeginningMonthOfCompany beginningMonthOfCompany;

    @Inject
    private GetDetailOutputSettingWorkStatusQuery detailOutputSettingWorkStatusQuery;

    @Inject
    private CreateConfigdetailCommandHandler createConfigdetailCommandHandler;

    @Inject
    private UpdateSettingDetailCommandHandler updateSettingDetailCommandHandler;

    @Inject
    private DeleteDetailsOfTheWorkCommandHandler deleteDetailsOfTheWorkCommandHandler;

    @Inject
    private DuplicateSettingDetailCommandHandler duplicateSettingDetailCommandHandler;

    @Inject
    private CheckDailyPerformAuthorQuery checkDailyPerformAuthorQuery;
    @POST
    @Path("003/a/listworkstatus")
    public List<WorkStatusOutputDto> getListWorkStatus(int setting) {
        return settingQuery.getListWorkStatus(EnumAdaptor.valueOf(setting, SettingClassificationCommon.class));
    }
    @POST
    @Path("getbeginmonthofcompany")
    public CompanyDto getBeginningMonth(String cid) {
        return beginningMonthOfCompany.getBeginningMonthOfCompany(cid);
    }
    @POST
    @Path("checkdailyauthor")
    public boolean checkDailyPerformAuthor(String roleId) {
        return checkDailyPerformAuthorQuery.checkDailyPerformAuthor(roleId);
    }
    @POST
    @Path("003/b/detailworkstatus")
    public WorkStatusOutputSettingDto getDetailWorkStatus(String settingId) {
        WorkStatusOutputSettingDto rs = new WorkStatusOutputSettingDto();
        val itemList = new ArrayList<ItemDto>();
        val workStatusOutputSettings = detailOutputSettingWorkStatusQuery.getDetail(settingId);
        if(workStatusOutputSettings!=null){
            workStatusOutputSettings.getOutputItem().forEach(e ->
                itemList.add(new ItemDto(
                        e.getRank(),
                        e.getName()!=null ? e.getName().v():null,
                        e.isPrintTargetFlag(),
                        e.getIndependentCalculaClassification()!=null?e.getIndependentCalculaClassification().value:0,
                        e.getIndependentCalculaClassification()!=null?e.getIndependentCalculaClassification().name():null,
                        e.getDailyMonthlyClassification()!=null? e.getDailyMonthlyClassification().value:0,
                        e.getDailyMonthlyClassification()!=null? e.getDailyMonthlyClassification().name():null,
                        e.getItemDetailAttributes()!=null? e.getItemDetailAttributes().value:0,
                        e.getItemDetailAttributes()!=null? e.getItemDetailAttributes().name():null,
                        e.getSelectedAttendanceItemList().stream().map(i -> new AttendanceItemDto(
                                i.getOperator()!=null?i.getOperator().value:0,
                                i.getOperator()!=null?i.getOperator().name():null,
                                i.getAttendanceItemId()
                        )).collect(Collectors.toCollection(ArrayList::new))
                ))
            );
             rs = new  WorkStatusOutputSettingDto(
                    workStatusOutputSettings.getSettingId(),
                    workStatusOutputSettings.getSettingDisplayCode().v(),
                    workStatusOutputSettings.getSettingName().v(),
                    workStatusOutputSettings.getEmployeeId(),
                    workStatusOutputSettings.getStandardFreeDivision().value,
                    workStatusOutputSettings.getStandardFreeDivision().name(),
                    itemList
            );
        }
       return rs;
    }
    @POST
    @Path("003/b/create")
    public void create(CreateConfigdetailDto dto) {
        if (dto != null) {
            List<OutputItem> outputItemList = new ArrayList<>();
            dto.getOutputItemList().forEach(e -> {
                val selectedAttItemList =  e.getSelectedAttItemList().stream().map(i -> new OutputItemDetailSelectionAttendanceItem(
                        EnumAdaptor.valueOf(i.getOperator(), OperatorsCommonToForms.class),
                        i.getAttendanceItemId()
                )).collect(Collectors.toList());
                outputItemList.add(new OutputItem(
                        e.getRank(),
                        new FormOutputItemName(e.getName()),
                        e.isPrintTargetFlag(),
                        EnumAdaptor.valueOf(e.getIndependentCalClassic(), IndependentCalculationClassification.class),
                        EnumAdaptor.valueOf(e.getDailyMonthlyClassic(), DailyMonthlyClassification.class),
                        EnumAdaptor.valueOf(e.getItemDetailAtt(), CommonAttributesOfForms.class),
                        selectedAttItemList
                       ));
            });

            CreateConfigdetailCommand command = new CreateConfigdetailCommand(
                    dto.getCode(),
                    dto.getName(),
                    dto.getSettingCategory(),
                    outputItemList
            );
            this.createConfigdetailCommandHandler.handle(command);
        }
    }

    @POST
    @Path("003/b/update")
    public void update(UpdateSettingDetailDto dto) {
        if (dto != null) {
            List<OutputItem> outputItemList = new ArrayList<>();
            dto.getOutputItemList().forEach(e -> {
                outputItemList.add(new OutputItem(
                        e.getRank(),
                        new FormOutputItemName(e.getName()),
                        e.isPrintTargetFlag(),
                        EnumAdaptor.valueOf(e.getIndependentCalClassic(), IndependentCalculationClassification.class),
                        EnumAdaptor.valueOf(e.getDailyMonthlyClassic(), DailyMonthlyClassification.class),
                        EnumAdaptor.valueOf(e.getItemDetailAtt(), CommonAttributesOfForms.class),
                        e.getSelectedAttItemList().stream().map(i -> new OutputItemDetailSelectionAttendanceItem(
                                EnumAdaptor.valueOf(i.getOperator(), OperatorsCommonToForms.class),
                                i.getAttendanceItemId()
                        )).collect(Collectors.toList())));
            });

            UpdateSettingDetailCommand command = new UpdateSettingDetailCommand(
                    dto.getSettingId(),
                    dto.getCode(),
                    dto.getName(),
                    dto.getSettingCategory(),
                    outputItemList
            );
            this.updateSettingDetailCommandHandler.handle(command);
        }
    }

    @POST
    @Path("003/b/delete")
    public void delete(String settingId ) {
        val command = new DeleteDetailsOfTheWorkCommand(settingId);
        this.deleteDetailsOfTheWorkCommandHandler.handle(command);
    }

    @POST
    @Path("003/c/duplicate")
    public void duplicate(DuplicateSettingDetailCommand command) {
        this.duplicateSettingDetailCommandHandler.handle(command);
    }
}