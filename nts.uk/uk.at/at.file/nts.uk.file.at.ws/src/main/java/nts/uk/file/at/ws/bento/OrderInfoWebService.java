package nts.uk.file.at.ws.bento;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.*;
import nts.uk.file.at.app.export.bento.OrderInfoExportPDFService;

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
    OrderInfoExportPDFService exportPDFService;

//    @Inject
//    OrderInfoExportExcelService exportExcelService;

    @POST
    @Path("all/pdf")
    public ExportServiceResult generatePdfAll() {
        OrderInfoDto data = fakeData();
        return exportPDFService.start(data);
    }

    @POST
    @Path("detail/pdf")
    public ExportServiceResult generatePdfDetail() {
        OrderInfoDto data = fakeDetailData();
        return exportPDFService.start(data);
    }

    @POST
    @Path("print/excel")
    public ExportServiceResult generateExcel() {

        return null;
    }

    public OrderInfoDto fakeData() {
        String companyName = "Test com name";
        String totalTittle = "Tittle";
        String detailTittle = "";
        List<DetailOrderInfoDto> detailOrderInfoDtoList = Collections.EMPTY_LIST;
        List<TotalOrderInfoDto> totalOrderInfoDtoList = new ArrayList<>();
        GeneralDate reservationDate = GeneralDate.ymd(2020, 8, 10);
        int totalFee = 100000;
        String closedName = "wew wew";
        List<PlaceOfWorkInfoDto> placeOfWorkInfoDtos = Collections.EMPTY_LIST;
        List<BentoTotalDto> bentoTotalDtos = new ArrayList<>();
        for (int i = 1; i < 41; ++i) {
            String unit = (i < 5) ? "枚" : "個";
            int quantity = (int) (Math.random() * (30 - 1 + 1) + 1);
            int amount = 10 * (int) (Math.random() * (20 - 1 + 1) + 1);
            bentoTotalDtos.add(new BentoTotalDto(
                    unit, "Item " + i, quantity, 1, amount
            ));
        }
        totalOrderInfoDtoList.add(new TotalOrderInfoDto(
                reservationDate,"reservationRegisInfo",totalFee,bentoTotalDtos,closedName,placeOfWorkInfoDtos
        ));

        return new OrderInfoDto(companyName,totalTittle,totalOrderInfoDtoList, detailTittle, detailOrderInfoDtoList);
    }

    public OrderInfoDto fakeDetailData() {
        String companyName = "Test com name";
        String totalTittle = "";
        List<TotalOrderInfoDto> totalOrderInfoDtoList = Collections.EMPTY_LIST;
        String detailTittle = "Tittle";
        List<DetailOrderInfoDto> detailOrderInfoDtos = new ArrayList<>();
        GeneralDate reservationDate = GeneralDate.today();
        String reservationRegisInfo = "Card no 10000";
        String closingTimeName = "close 1";
        List<PlaceOfWorkInfoDto> placeOfWorkInfoDtos = Collections.EMPTY_LIST;
        List<BentoReservedInfoDto> bentoReservedInfoDtos = new ArrayList<>();
        List<BentoReservationInfoForEmpDto> bentoReservationInfoForEmpList1 =  new ArrayList<>();
        for(int i = 0; i < 16; ++i){
            String stampCardNo = "card 1";
            int quantity = (int) (Math.random() * (30 - 1 + 1) + 1);
            String empId = "id-"+i;
            String empCode = "code-"+i;
            String empName = "name-"+i;
            bentoReservationInfoForEmpList1.add(new BentoReservationInfoForEmpDto(
                    stampCardNo, quantity, empId, empCode, empName
            ));
        }
        for(int i = 0; i < 2; ++i)
            bentoReservedInfoDtos.add(new BentoReservedInfoDto("item " + i, 3,bentoReservationInfoForEmpList1));

        detailOrderInfoDtos.add(new DetailOrderInfoDto(bentoReservedInfoDtos,reservationDate,reservationRegisInfo,
                closingTimeName,placeOfWorkInfoDtos));
        return new OrderInfoDto(companyName,totalTittle,totalOrderInfoDtoList, detailTittle, detailOrderInfoDtos);
    }
}
