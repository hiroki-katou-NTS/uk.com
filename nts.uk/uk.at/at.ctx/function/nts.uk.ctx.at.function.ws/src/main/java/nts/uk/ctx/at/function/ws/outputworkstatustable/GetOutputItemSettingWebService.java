package nts.uk.ctx.at.function.ws.outputworkstatustable;


import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.outputworkstatustable.*;
import nts.uk.ctx.at.function.app.query.outputworkstatustable.GetDetailOutputSettingWorkStatusQuery;
import nts.uk.ctx.at.function.app.query.outputworkstatustable.GetOutputItemSettingQuery;
import nts.uk.ctx.at.function.app.query.outputworkstatustable.WorkStatusOutputDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.FormOutputItemName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailSelectionAttendanceItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("at/function/kwr/003")
@Produces("application/json")
public class GetOutputItemSettingWebService extends WebService {

    @Inject
    private GetOutputItemSettingQuery settingQuery;

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

    @POST
    @Path("a/listworkstatus")
    public List<WorkStatusOutputDto> getListWorkStatus(int setting) {
        return settingQuery.getListWorkStatus(EnumAdaptor.valueOf(setting, SettingClassificationCommon.class));
    }

    @POST
    @Path("b/detailworkstatus")
    public WorkStatusOutputSettings getDetailWorkStatus(String settingId) {
        return detailOutputSettingWorkStatusQuery.getDetail(settingId);
    }

    @POST
    @Path("b/create")
    public void create(CreateConfigdetailDto dto) {
        if (dto != null) {
            List<OutputItem> outputItemList = new ArrayList<>();
            dto.getOutputItemList().forEach(e -> {
                outputItemList.add(new OutputItem(
                        e.getRank(),
                        new FormOutputItemName(e.getName()),
                        e.isPrintTargetFlag(),
                        EnumAdaptor.valueOf(e.getIndependentCalculaClassification(), IndependentCalculationClassification.class),
                        EnumAdaptor.valueOf(e.getDailyMonthlyClassification(), DailyMonthlyClassification.class),
                        EnumAdaptor.valueOf(e.getItemDetailAttributes(), CommonAttributesOfForms.class),
                        e.getSelectedAttendanceItemList().stream().map(i -> new OutputItemDetailSelectionAttendanceItem(
                                EnumAdaptor.valueOf(i.getOperator(), OperatorsCommonToForms.class),
                                i.getAttendanceItemId()
                        )).collect(Collectors.toList())));
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
    @Path("b/update")
    public void update(UpdateSettingDetailDto dto) {
        if (dto != null) {
            List<OutputItem> outputItemList = new ArrayList<>();
            dto.getOutputItemList().forEach(e -> {
                outputItemList.add(new OutputItem(
                        e.getRank(),
                        new FormOutputItemName(e.getName()),
                        e.isPrintTargetFlag(),
                        EnumAdaptor.valueOf(e.getIndependentCalculaClassification(), IndependentCalculationClassification.class),
                        EnumAdaptor.valueOf(e.getDailyMonthlyClassification(), DailyMonthlyClassification.class),
                        EnumAdaptor.valueOf(e.getItemDetailAttributes(), CommonAttributesOfForms.class),
                        e.getSelectedAttendanceItemList().stream().map(i -> new OutputItemDetailSelectionAttendanceItem(
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
    @Path("b/delete")
    public void delete(String settingId ) {
        val command = new DeleteDetailsOfTheWorkCommand(settingId);
        this.deleteDetailsOfTheWorkCommandHandler.handle(command);
    }

    @POST
    @Path("c/duplicate")
    public void duplicate(DuplicateSettingDetailCommand command) {
        this.duplicateSettingDetailCommandHandler.handle(command);
    }
}