/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.ws.qpp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.pr.app.export.insurance.SocialInsuranceQuery;
import nts.uk.file.pr.app.export.insurance.SocialInsuranceReportService;

/**
 * The Class QPP018WebService.
 */
@Path("/screen/pr/QPP018")
@Produces("application/json")
public class QPP018WebService extends WebService {

    @Inject
    private SocialInsuranceReportService reportService;
    
    @Inject
    SocialInsuranceQueryProcessor socialInsuQueryProcessor;
    
    /**
     * Gets the all.
     *
     * @return the all
     */
    @POST
    @Path("findAllOffice")
    public List<InsuranceOfficeDto> getAll() {
        return socialInsuQueryProcessor.findAllOffice();
    }
    
    @POST
    @Path("saveAsPdf")
    public ExportServiceResult exportDataToPdf(SocialInsuranceQuery query) {
         return reportService.start(query);
    }
}
