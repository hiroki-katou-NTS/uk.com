package nts.uk.file.at.ws.bento;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.*;
import nts.uk.file.at.app.export.bento.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
