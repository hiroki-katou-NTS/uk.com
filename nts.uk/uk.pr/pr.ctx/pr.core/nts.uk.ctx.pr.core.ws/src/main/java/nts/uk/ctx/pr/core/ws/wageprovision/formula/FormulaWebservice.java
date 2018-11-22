package nts.uk.ctx.pr.core.ws.wageprovision.formula;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.formula.*;
import nts.uk.ctx.pr.core.app.find.wageprovision.formula.*;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.MasterUseDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("ctx/pr/core/wageprovision/formula")
@Produces("application/json")
public class FormulaWebservice extends WebService {

    @Inject
    private FormulaFinder formulaFinder;

    @Inject
    private BasicCalculationFormulaFinder basicCalculationFormulaFinder;

    @Inject
    private BasicFormulaSettingFinder basicFormulaSettingFinder;

    @Inject
    private DetailFormulaSettingFinder detailFormulaSettingFinder;

    @Inject
    private AddFormulaCommandHandler addFormulaCommandHandler;

    @Inject
    private UpdateFormulaSettingCommandHandler updateFormulaSettingCommandHandler;

    @Inject
    private AddFormulaHistoryCommandHandler addFormulaHistoryCommandHandler;

    @Inject
    private UpdateFormulaHistoryCommandHandler updateFormulaHistoryCommandHandler;

    @Inject
    private RemoveFormulaHistoryCommandHandler removeFormulaHistoryCommandHandler;

    @POST
    @Path("getAllFormula")
    public List<FormulaDto> getAllFormula() {
        return formulaFinder.getAllFormulaAndHistory();
    }

    @POST
    @Path("getFormulaSettingByHistoryID/{historyID}")
    public FormulaSettingDto getAllFormula(@PathParam("historyID") String historyID) {
        return new FormulaSettingDto(basicFormulaSettingFinder.getBasicFormulaSettingByHistoryID(historyID), detailFormulaSettingFinder.getDetailFormulaSettingByHistoryID(historyID), basicCalculationFormulaFinder.getBasicCalculationFormulaByHistoryID(historyID));
    }

    @POST
    @Path("addFormula")
    public void addFormula(FormulaCommand command) {
        addFormulaCommandHandler.handle(command);
    }

    @POST
    @Path("updateFormulaSetting")
    public void updateFormulaSetting(FormulaCommand command) {
        updateFormulaSettingCommandHandler.handle(command);
    }

    @POST
    @Path("addFormulaHistory")
    public void addHistory(FormulaCommand command) {
        addFormulaHistoryCommandHandler.handle(command);
    }

    @POST
    @Path("editFormulaHistory")
    public void editFormulaHistory(FormulaCommand command) {
        updateFormulaHistoryCommandHandler.handle(command);
    }

    @POST
    @Path("deleteFormulaHistory")
    public void deleteFormulaHistory(FormulaCommand command) {
        removeFormulaHistoryCommandHandler.handle(command);
    }

    @POST
    @Path("getMasterUseInfo/{masterUseClassification}")
    public List<MasterUseDto> getMasterUseInfo(@PathParam("masterUseClassification")int masterUseClassification) {
        return formulaFinder.getMasterUseInfo(masterUseClassification);
    }
}
