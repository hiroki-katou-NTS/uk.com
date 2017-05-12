/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.ws.qpp;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.pr.app.export.payment.PaymentReportQuery;
import nts.uk.file.pr.app.export.payment.PaymentReportService;

/**
 * The Class QPP018WebService.
 */
@Path("/screen/pr/QPP021")
@Produces("application/json")
public class QPP021WebService extends WebService {

    /** The report service. */
    @Inject
    private PaymentReportService reportService;
    
    /**
     * Export data to pdf.
     *
     * @param query the query
     * @return the export service result
     */
    @POST
    @Path("saveAsPdf")
    public ExportServiceResult exportDataToPdf(PaymentReportQuery query) {
         return reportService.start(query);
    }
}
