package nts.uk.file.at.ws.bento;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.file.at.app.export.bento.CreateOrderInfoDataSource;
import nts.uk.file.at.app.export.bento.OrderInfoExportExcelService;
import nts.uk.file.at.app.export.bento.OrderInfoExportPDFService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("order/report")
@Produces(MediaType.APPLICATION_JSON)
public class OrderInfoWebService {

    @Inject
    private OrderInfoExportPDFService exportPDFService;

    @Inject
    private OrderInfoExportExcelService exportExcelService;

    @POST
    @Path("print/pdf")
    public ExportServiceResult generatePdf(CreateOrderInfoDataSource data) {
        return exportPDFService.start(data);
    }

    @POST
    @Path("print/excel")
    public ExportServiceResult generateExcel(CreateOrderInfoDataSource data) {
        return exportExcelService.start(data);
    }
}
