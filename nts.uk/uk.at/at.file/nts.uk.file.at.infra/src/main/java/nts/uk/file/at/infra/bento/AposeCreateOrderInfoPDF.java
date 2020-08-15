package nts.uk.file.at.infra.bento;

import com.aspose.cells.*;
import com.aspose.pdf.Rows;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.*;
import nts.uk.file.at.app.export.bento.CreateOrderInfoPDFGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AposeCreateOrderInfoPDF extends AsposeCellsReportGenerator implements CreateOrderInfoPDFGenerator {

    private static final String TEMPLATE_TOTAL_FILE = "report/KMR004.xlsx";
    private static final String TEMPLATE_DETAIL_FILE = "report/KMR004_Template_Detail.xlsx";
    private static final String FILE_NAME = "KMR004予約確認一覧_";
    private static final String TOTAL_BOOK = "合計書";
    private static final String STATEMENT_SLIP = "明細票";
    private static final String REPORT_FILE_EXTENSION_PDF = ".pdf";


    private static final String PERIOD_LABEL = TextResource.localize("KMR004_27");
    private static final String TOTAL_LABEL = TextResource.localize("KMR004_32");

    /**
     * Total
     */
    private static final String ORDINAL_LABEL = TextResource.localize("KMR004_28");
    private static final String BENTO_NAME_LABEL = TextResource.localize("KMR004_29");
    private static final String AMOUNT_LABEL = TextResource.localize("KMR004_30");
    private static final String QUANTITY_LABEL = TextResource.localize("KMR004_31");
    //Work Place
    private static final String WOKR_PLACE_LABEL = TextResource.localize("KMR004_38");
    ////Work Location
    private static final String WOKR_LOCATION_LABEL = TextResource.localize("KMR004_38");

    /**
     * Detail
     */

    private static final String EMP_CODE = TextResource.localize("KMR004_33");
    private static final String EMP_NAME = TextResource.localize("KMR004_34");
    private static final String QUANTITY = TextResource.localize("KMR004_35");
    private static final String IS_CHECK = TextResource.localize("KMR004_36");

//    private static final String EMP_CODE = "社員コード";
//    private static final String EMP_NAME = "社員名";
//    private static final String QUANTITY = "数量";
//    private static final String IS_CHECK = "チェック";


    @Override
    public void generate(FileGeneratorContext generatorContext, OrderInfoDto data) {
        if(data.getDetailTittle() == null | "".equals(data.getDetailTittle()))
            handleTotalTemplate(generatorContext, data);
        else
            handleDetailTemplate(generatorContext, data);
    }

    private void handleTotalTemplate(FileGeneratorContext generatorContext, OrderInfoDto data){
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_TOTAL_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            printDataTotalFormat(worksheets,data);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(generatorContext,
                    FILE_NAME + TOTAL_BOOK + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION_PDF));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void handleDetailTemplate(FileGeneratorContext generatorContext, OrderInfoDto data){
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_DETAIL_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            printDataDetailFormat(worksheets,data);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(generatorContext,
                    FILE_NAME + STATEMENT_SLIP + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION_PDF));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void settingPage(Worksheet worksheet, String companyName, String Tittle){
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.PORTRAIT);
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
        pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 " + Tittle);
        pageSetup.setFitToPagesTall(1);
        pageSetup.setFitToPagesWide(1);
    }

    private void printDataTotalFormat(WorksheetCollection worksheets, OrderInfoDto data) {
        try {
            Worksheet worksheet = worksheets.get(0);
            settingPage(worksheet, data.getCompanyName(), TOTAL_BOOK);
            Cells cells = worksheet.getCells();
            int startIndex = 5; int endPage = 0;
            Style[] headerStyle = new Style[5];
            Style[][] bodyStyle = new Style[2][5];
            Style[] footerStyle = new Style[5];
            for(int i = 0; i < 5; ++i){
                headerStyle[i] = cells.get(4,i).getStyle();
                bodyStyle[0][i] = cells.get(5,i).getStyle();
                bodyStyle[1][i] = cells.get(6,i).getStyle();
                footerStyle[i] = cells.get(45,i).getStyle();
            }

            cells.get(0,3).setValue(data.getCompanyName());
            //Object headOffice = cells.get(0,6).getValue();
            cells.get(0,6).setValue(data.getCompanyName());
            List<TotalOrderInfoDto> dataPrint = data.getTotalOrderInfoDtoList();
            for(TotalOrderInfoDto dataRow : dataPrint){
                handleBodyTotalFormat(dataRow,startIndex, cells, endPage);
            }

        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void handleBodyTotalFormat(TotalOrderInfoDto dataRow, int startIndex, Cells cells, int endPage){
        int total = 0;
        for(int i = 0; i < dataRow.getBentoTotalDto().size(); ++i){
            BentoTotalDto bentoTotalDto = dataRow.getBentoTotalDto().get(i);
            int amount = bentoTotalDto.getAmount() * bentoTotalDto.getQuantity();
            cells.get(startIndex+i,3).setValue(i+1);
            cells.get(startIndex+i,5).setValue(bentoTotalDto.getName());
            cells.get(startIndex+i,6).setValue(amount);
            cells.get(startIndex+i,7).setValue(bentoTotalDto.getQuantity());
            cells.get(startIndex+i,8).setValue(bentoTotalDto.getUnit());
            total += amount;
            //cells.get(startIndex+i,9).setValue(bentoTotalDto.getUnit());
        }
        cells.get(45,6).setValue(total);
        cells.deleteRows(startIndex + dataRow.getBentoTotalDto().size() - 1,40 - dataRow.getBentoTotalDto().size());
        endPage += startIndex + dataRow.getBentoTotalDto().size();
    }
    private void printDataDetailFormat(WorksheetCollection worksheets, OrderInfoDto data) {
        try {
            Worksheet worksheet = worksheets.get(0);
            settingPage(worksheet, data.getCompanyName(), STATEMENT_SLIP);
            Cells cells = worksheet.getCells();
            int startIndex = 5;
            int lastIndex = startIndex + 3;
            Style[][] headerStyle = new Style[2][12];
            Style[][] bodyStyle = new Style[2][12];
            Style[] footerStyle = new Style[12];
            for(int i = 0; i < 12; ++i){
                headerStyle[0][i] = cells.get(3,i).getStyle();
                headerStyle[1][i] = cells.get(4,i).getStyle();
                bodyStyle[0][i] = cells.get(5,i).getStyle();
                bodyStyle[1][i] = cells.get(6,i).getStyle();
                footerStyle[i] = cells.get(7,i).getStyle();
            }
            //Object headOffice = cells.get(0,6).getValue();
            cells.get(0,3).setValue(data.getCompanyName());
            cells.get(0,6).setValue(data.getCompanyName());
            List<BentoReservedInfoDto> dataPrint = new ArrayList<>();
            for(DetailOrderInfoDto i : data.getDetailOrderInfoDtoList()){
                dataPrint.addAll(i.getBentoReservedInfoDtos());
            }
            //DetailOrderInfoDto dataRow = dataPrint.get(0);

            for(BentoReservedInfoDto item : dataPrint){
                List<BentoReservationInfoForEmpDto> bodyData = item.getBentoReservationInfoForEmpList();
                if(bodyData.size() < 1)
                    continue;
                //set Bento Name
                cells.get(0, startIndex - 2).setValue(item.getBentoName());

                for(int i = 0; i < 3; ++i){
                    cells.get(0 + i*4, startIndex - 1).setValue(item.getBentoName());
                    cells.get(1+ i*4, startIndex - 1).setValue(item.getBentoName());
                    cells.get(2+ i*4, startIndex - 1).setValue(item.getBentoName());
                    cells.get(3+ i*4, startIndex - 1).setValue(item.getBentoName());
                }
                int row = (bodyData.size()/3);
                if(row > 2){
                    for(int i = 0; i < 2; ++i){
                        for(int j = 0; j < 3; ++j){
                            cells.get(startIndex + i,0 + j*4).setValue(bodyData.get(i*3 + j).getEmpCode());
                            cells.get(startIndex + i,1 + j*4).setValue(bodyData.get(i*3 + j).getEmpName());
                            cells.get(startIndex + i,2 + j*4).setValue(bodyData.get(i*3 + j).getQuantity());
                        }
                        lastIndex++;
                    }
                    for(int i = 2; i < row; ++i){
                        cells.insertRow(startIndex + i);
                        int copyFrom = (i%2==0) ? startIndex : (startIndex + 1);
                        try {
                            cells.copyRow(cells,copyFrom,startIndex + i);
                        }catch (Exception e){
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }

                        for(int j = 0; j < 3; ++j){
                            cells.get(startIndex + i,0 + j*4).setValue(bodyData.get(i*3 + j).getEmpCode());
                            cells.get(startIndex + i,1 + j*4).setValue(bodyData.get(i*3 + j).getEmpName());
                            cells.get(startIndex + i,2 + j*4).setValue(bodyData.get(i*3 + j).getQuantity());
                            //cells.get(startIndex + i,3 + j*4).setValue(bodyData.get(i*3 + j).getEmpCode());
                        }
                        lastIndex++;
                    }
                }

//                int mod = bodyData.size() - (row * 3);
//                for(int i = 0; i < mod; ++i){
//                    cells.get(startIndex + i,0 + i*4).setValue(bodyData.get(i*3 + j).getEmpCode());
//                    cells.get(startIndex + i,1 + i*4).setValue(bodyData.get(i*3 + j).getEmpName());
//                    cells.get(startIndex + i,2 + i*4).setValue(bodyData.get(i*3 + j).getQuantity());
//                }

                startIndex = lastIndex + 3;
                lastIndex = startIndex + 3;
            }


        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
    private String formatString(String s){
        return s.replaceAll("\n", " ");
    }

}
