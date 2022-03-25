package nts.uk.screen.at.ws.kfp002;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.anyperiodcorrection.formatsetting.columnwidthsetting.GridColWidthCommand;
import nts.uk.ctx.at.function.app.command.anyperiodcorrection.formatsetting.columnwidthsetting.RegisterGridColWidthCommandHandler;
import nts.uk.ctx.at.function.app.find.resultsperiod.optionalaggregationperiod.AnyAggrPeriodDto;
import nts.uk.ctx.at.function.app.query.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatDto;
import nts.uk.ctx.at.function.app.query.anyperiodcorrection.formatsetting.columnwidthsetting.AnyPeriodCorrectionColumnWidthDto;
import nts.uk.ctx.at.function.app.query.anyperiodcorrection.formatsetting.columnwidthsetting.AnyPeriodCorrectionGridColumnWidthQuery;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.CalculatedItemDetail;
import nts.uk.screen.at.app.command.kfp002.CorrectAnyPeriodResultsScreenCommand;
import nts.uk.screen.at.app.command.kfp002.CorrectAnyPeriodResultsScreenCommandHandler;
import nts.uk.screen.at.app.query.kfp002.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/screen/kfp002")
@Produces("application/json")
public class Kfp002WebService extends WebService {
    @Inject
    private GetAnyPeriodAggrFrameScreenQuery frameScreenQuery;

    @Inject
    private GetAnyPeriodDisplayFormatScreenQuery displayFormatScreenQuery;

    @Inject
    private GetAnyPeriodActualResultsScreenQuery actualResultsScreenQuery;

    @Inject
    private AnyPeriodCorrectionGridColumnWidthQuery columnWidthQuery;

    @Inject
    private RegisterGridColWidthCommandHandler registerColWidthHandler;

    @Inject
    private CorrectAnyPeriodResultsScreenCommandHandler correctResultHandler;

    @Inject
    private GetAllAnyPeriodCorrectionFormatScreenQuery allFormatScreenQuery;

    @POST
    @Path("a/frames")
    public List<AnyAggrPeriodDto> getAnyPeriodAggrFrames() {
        return frameScreenQuery.getAnyPeriodAggrFrames();
    }

    @POST
    @Path("a/display")
    public AnyPeriodCorrectionDisplayDto getDisplayFormat(String frameCode) {
        return displayFormatScreenQuery.getDisplayFormat(frameCode);
    }

    @POST
    @Path("a/columns")
    public List<AnyPeriodCorrectionColumnWidthDto> getColumnWidths() {
        return columnWidthQuery.getGridColWidths();
    }

    @POST
    @Path("a/register-col-width")
    public void registerColWidth(List<GridColWidthCommand> commands) {
        registerColWidthHandler.handle(commands);
    }

    @POST
    @Path("a/results")
    public AnyPeriodResultDataDto getActualResults(AnyPeriodResultParam param) {
        return actualResultsScreenQuery.getActualResults(param.getFrameCode(), param.getEmployeeIds(), param.getItemIds());
    }

    @POST
    @Path("a/correct")
    public List<CalculatedItemDetail> registerCorrections(CorrectAnyPeriodResultsScreenCommand command) {
        return correctResultHandler.handle(command);
    }

    @POST
    @Path("b/formats")
    public List<AnyPeriodCorrectionFormatDto> getAllFormat() {
        return allFormatScreenQuery.getAllFormat();
    }
}
