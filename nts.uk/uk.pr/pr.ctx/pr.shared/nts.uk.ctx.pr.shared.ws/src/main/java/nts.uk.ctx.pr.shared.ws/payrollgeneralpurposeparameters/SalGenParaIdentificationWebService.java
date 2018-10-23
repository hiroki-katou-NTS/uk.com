package nts.uk.ctx.pr.shared.ws.payrollgeneralpurposeparameters;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters.AddSalGenParaValueCommandHandler;
import nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters.SalGenParaYeahMonthValueCommand;
import nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters.*;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaDateParams;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("shared/payrollgeneralpurposeparameters")
@Produces("application/json")
public class SalGenParaIdentificationWebService extends WebService {

    @Inject
    private SalGenParaIdentificationFinder mSalGenParaIdentificationFinder;

    @Inject
    private SalGenParamOptionsFinder mSalGenParamOptionsFinder;

    @Inject
    private AddSalGenParaValueCommandHandler mAddSalGenParaValueCommandHandler;

    @Inject
    private SalGenParaYMHistFinder mSalGenParaYMHistFinder;

    @Inject
    private SalGenParaDateHistFinder mSalGenParaDateHistFinder;

    @Inject
    private SalGenParaValueFinder mSalGenParaValueFinder;

//    @POST
//    @Path("getOccAccIsPrRate/{hisId}")
//    public List<OccAccIsPrRateDto> getOccAccIsPrRate(@PathParam("hisId") String hisId) {
//        return occAccIsPrRateFinder.getAllOccAccIsPrRate(hisId);
//    }

    @POST
    @Path("getAllSalGenParaIdentification")
    public List<SalGenParaIdentificationDto> getAllSalGenParaIdentification() {
        return mSalGenParaIdentificationFinder.getAllSalGenParaIdentification();
    }
    @POST
    @Path("getSalGenParamOptions/{paraNo}")
    public List<SalGenParamOptionsDto> getSalGenParamOptions(@PathParam("paraNo") String paraNo) {
        return mSalGenParamOptionsFinder.getAllSalGenParamOptions(paraNo);
    }
    @POST
    @Path("getSalGenParaValue/{hisId}")
    public SalGenParaValueDto getSalGenParaValue(@PathParam("hisId") String hisId) {
        return mSalGenParaValueFinder.getAllSalGenParaValue(hisId);
    }
    @POST
    @Path("getSalGenParaDateHistory/{hisId}")
    public List<SalGenParaDateHistDto> getSalGenParaDateHistory(@PathParam("hisId") String hisId) {
        return mSalGenParaDateHistFinder.getAllSalGenParaDateHist(hisId);
    }
    @POST
    @Path("getSalGenParaYearMonthHistory/{paraNo}")
    public List<SalGenParaYMHistDto> getSalGenParaYearMonthHistory(@PathParam("paraNo") String paraNo) {
        return mSalGenParaYMHistFinder.getAllSalGenParaYMHist(paraNo);
    }
    @POST
    @Path("addSelectionProcess")
    public void addSelectionProcess(SalGenParaYeahMonthValueCommand command) {
        mAddSalGenParaValueCommandHandler.handle(command);
    }
    @POST
    @Path("getListHistory")
    public List<SalGenParaDateHistDto> getListHistory(SalGenParaDateParams params) {
        return mSalGenParaDateHistFinder.getListHistory(params.getParaNo(), GeneralDate.fromString(params.getStartDate(),"yyyy-MM-dd"), GeneralDate.fromString(params.getEndDate(),"yyyy-MM-dd"));
    }
//
//    @POST
//    @Path("updateOccAccInsurBus")
//    public void updateOccAccInsurBus(UpdateNameOfEachBusinessCommand command) {
//        updateNameOfEachBusinessCommandHandler.handle(command);
//    }




}