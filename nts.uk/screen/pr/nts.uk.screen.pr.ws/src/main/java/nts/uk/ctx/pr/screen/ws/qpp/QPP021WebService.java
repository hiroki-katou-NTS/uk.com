/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.ws.qpp;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.pr.app.export.payment.PaymentReportPreviewQuery;
import nts.uk.file.pr.app.export.payment.PaymentReportPreviewService;
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
    
    /** The preview report service. */
    @Inject
    private PaymentReportPreviewService previewReportService;
    
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
    
    
    /**
     * Preview refund padding once.
     *
     * @param query the query
     * @return the export service result
     */
    @POST
    @Path("preview/refundpadding/once")
    public ExportServiceResult previewRefundPaddingOnce(PaymentReportPreviewQuery query) {
    	return previewReportService.start(query);
    }
    
    /**
     * Preview refund padding two.
     *
     * @return the export service result
     */
    @POST
    @Path("preview/refundpadding/two")
    public ExportServiceResult previewRefundPaddingTwo(PaymentReportPreviewQuery query) {
    	return previewReportService.start(query);
    }
    
    /**
     * Preview refund padding three.
     *
     * @param query the query
     * @return the export service result
     */
    @POST
    @Path("preview/refundpadding/three")
    public ExportServiceResult previewRefundPaddingThree(PaymentReportPreviewQuery query) {
    	return previewReportService.start(query);
    }
}
