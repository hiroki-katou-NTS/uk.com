package nts.uk.screen.at.ws.kmr.kmr001.b;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.reservation.bento.BentoReservationWithEmpCommand;
import nts.uk.ctx.at.record.app.command.reservation.bento.DeleteReservationCorrectCommandHandler;
import nts.uk.ctx.at.record.app.command.reservation.bento.RegisterErrorMessage;
import nts.uk.ctx.at.record.app.command.reservation.bento.RegisterReservationCorrectCommandHandler;
import nts.uk.ctx.at.record.app.query.reservation.RegisterNewReservationCommand;
import nts.uk.ctx.at.record.app.query.reservation.RegisterNewReserCommandHandler;
import nts.uk.ctx.at.record.app.query.reservation.ReservationQueryOuput;
import nts.uk.ctx.at.record.app.query.reservation.ReservationSettingQuery;
import nts.uk.ctx.at.record.app.query.reservation.StartNewReservationQuery;
import nts.uk.ctx.at.record.app.query.reservation.StartReservationCorrectOutput;
import nts.uk.ctx.at.record.app.query.reservation.StartReservationCorrectParam;
import nts.uk.ctx.at.record.app.query.reservation.StartReservationCorrectQuery;
import nts.uk.screen.at.app.reservation.BentoJoinReservationSetting;
import nts.uk.screen.at.app.reservation.BentoMenuJoinBentoSettingDto;
import nts.uk.screen.at.app.reservation.BentoMenuSetScreenProcessor;
import nts.uk.screen.at.app.reservation.BentoRequest;
import nts.uk.screen.at.app.reservation.WorkLocationDto;

@Path("at/record/reservation/bento-menu")
@Produces("application/json")
public class BentoMenuWebService extends WebService{

    @Inject
    private BentoMenuSetScreenProcessor bentoMenuSetScreenProcessor;
    
    @Inject
    private ReservationSettingQuery reservationSettingQuery;
    
    @Inject
    private StartReservationCorrectQuery startReservationCorrectQuery;
    
    @Inject
    private RegisterReservationCorrectCommandHandler registerReservationCorrectCommand;
    
    @Inject
    private DeleteReservationCorrectCommandHandler deleteReservationCorrectCommandHandler;
    
    @Inject
    private StartNewReservationQuery startNewReservationQuery;
    
    @Inject
    private RegisterNewReserCommandHandler registerNewReservationQuery;

    @POST
    @Path("getbentomenu")
    public BentoMenuJoinBentoSettingDto getReservation() {
        return this.bentoMenuSetScreenProcessor.findDataBentoMenu();
    }

    @POST
    @Path("getbentomenubyhist")
    public BentoJoinReservationSetting getBentoMenu(BentoRequest request) {
        return this.bentoMenuSetScreenProcessor.getBentoMenuByHist(request);
    }

    @POST
    @Path("getworklocation")
    public List<WorkLocationDto> getWorklocation() {
        return this.bentoMenuSetScreenProcessor.getWorkLocationByContr();
    }
    
    @POST
    @Path("start")
    public ReservationQueryOuput start() {
        return reservationSettingQuery.getReservationSetting();
    }
    
    // Screen B
    @POST
    @Path("startCorrect")
    public StartReservationCorrectOutput startReservationCorrect(StartReservationCorrectParam param) {
        return startReservationCorrectQuery.startReservationCorrection(
                GeneralDate.fromString(param.getCorrectionDate(), "yyyy/MM/dd"), 
                GeneralDate.fromString(param.getReservationDate(), "yyyy/MM/dd"), 
                param.getFrameNo(), 
                param.getExtractCondition(), 
                param.getEmployeeIds());
    }
    
    @POST
    @Path("registerCorrect")
    public List<RegisterErrorMessage> registerCorrectReservation(List<BentoReservationWithEmpCommand> param) {
        return registerReservationCorrectCommand
                .register(param.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
    }
    
    @POST
    @Path("removeReservation")
    public List<RegisterErrorMessage> removeCorrectReservation(List<BentoReservationWithEmpCommand> param) {
        return deleteReservationCorrectCommandHandler.delete(param);
    }
    
    // Screen C
    @POST
    @Path("startNewReservation")
    public StartReservationCorrectOutput startNewReservation(StartReservationCorrectParam param) {
        return startNewReservationQuery.startNewReservation(
                GeneralDate.fromString(param.getCorrectionDate(), "yyyy/MM/dd"), 
                param.getFrameNo(), 
                param.getFrameName(), 
                param.getEmployeeIds());
    }
    
    @POST
    @Path("registerNewReservation")
    public void registerNewReservation(RegisterNewReservationCommand param) {
        registerNewReservationQuery.register(
                param.getFrameNo(), 
                GeneralDate.fromString(param.getCorrectionDate(), "yyyy/MM/dd"), 
                param.getBentoReservations().stream().map(x -> x.toDomain()).collect(Collectors.toList()));
    }
}
