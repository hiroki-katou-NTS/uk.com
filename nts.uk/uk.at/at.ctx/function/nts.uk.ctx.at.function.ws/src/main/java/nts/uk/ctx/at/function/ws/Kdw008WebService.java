package nts.uk.ctx.at.function.ws;

import nts.uk.ctx.at.function.app.command.modifyanyperiod.*;
import nts.uk.ctx.at.function.app.find.modifyanyperiod.ModifyAnyPeriodDto;
import nts.uk.ctx.at.function.app.find.modifyanyperiod.ModifyAnyPeriodFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/function/kdw/008/a")
@Produces("application/json")
public class Kdw008WebService {
    @Inject
    private AddModifyAnyPeriodCommandHandler addModifyAnyPeriodCommandHandler;

    @Inject
    private DeleteModifyAnyPeriodCommandHandler deleteModifyAnyPeriodCommandHandler;

    @Inject
    private DeleteModifyAnyPeriodSheetCommandHandler deleteModifyAnyPeriodSheetCommandHandler;

    @Inject
    private UpdateModifyAnyPeriodCommandHandler updateModifyAnyPeriodCommandHandler;

    @Inject
    private ModifyAnyPeriodFinder modifyAnyPeriodFinder;

    @Inject
    private DuplicateModifyAnyPeriodCommandHandler duplicateModifyAnyPeriodCommandHandler;

    @POST
    @Path("add")
    public void addNew(AddModifyAnyPeriodCommand command) {
        addModifyAnyPeriodCommandHandler.handle(command);
    }

    @POST
    @Path("duplicate")
    public void duplicate(DuplicateModifyAnyPeriodCommand command) {
        duplicateModifyAnyPeriodCommandHandler.handle(command);
    }

    @POST
    @Path("delete")
    public void delete(DeleteModifyAnyPeriodCommand command) {
        deleteModifyAnyPeriodCommandHandler.handle(command);
    }

    @POST
    @Path("delete/sheet")
    public void deleteSheet(DeleteModifyAnyPeriodSheetCommand command) {
        deleteModifyAnyPeriodSheetCommandHandler.handle(command);
    }

    @POST
    @Path("update")
    public void update(UpdateModifyAnyPeriodCommand command) {
        updateModifyAnyPeriodCommandHandler.handle(command);
    }

    @POST
    @Path("findAll")
    public List<ModifyAnyPeriodDto> getAll() {
        return modifyAnyPeriodFinder.getAllModifyAnyPeriod();
    }

    @POST
    @Path("findByCode/{code}")
    public ModifyAnyPeriodDto getAll(@PathParam("code") String code) {
        return modifyAnyPeriodFinder.getByCode(code);
    }
}
