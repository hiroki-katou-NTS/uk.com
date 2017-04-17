/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.ws.qpp;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.screen.app.query.qpp018.SocialInsuranceQueryProcessor;
import nts.uk.ctx.pr.screen.app.query.qpp018.dto.InsuranceOfficeDto;
import nts.uk.file.pr.app.export.insurance.salary.SalarySocialInsuranceQuery;
import nts.uk.file.pr.app.export.insurance.salary.SalarySocialInsuranceReportService;

/**
 * The Class QPP018WebService.
 */
@Path("/screen/pr/QPP018")
@Produces("application/json")
public class QPP018WebService extends WebService {

    /** The report service. */
    @Inject
    private SalarySocialInsuranceReportService reportService;
    
    /** The social insu query processor. */
    @Inject
    SocialInsuranceQueryProcessor socialInsuQueryProcessor;
    
    
    /**
     * Find all office.
     *
     * @return the list
     */
    @POST
    @Path("findAllOffice")
    public List<InsuranceOfficeDto> findAllOffice() {
        return socialInsuQueryProcessor.findAllOffice();
    }
    
    /**
     * Export data to pdf.
     *
     * @param query the query
     * @return the export service result
     */
    @POST
    @Path("saveAsPdf")
    public ExportServiceResult exportDataToPdf(SalarySocialInsuranceQuery query) {
         return reportService.start(query);
    }
}
