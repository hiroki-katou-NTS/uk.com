package nts.uk.file.at.infra.bento;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.OrderInfoDto;
import nts.uk.file.at.app.export.bento.OrderInfoExcelGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AposeCreateOrderInfoExcel extends AsposeCellsReportGenerator implements OrderInfoExcelGenerator {

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

    @Override
    public void generate(FileGeneratorContext generatorContext, OrderInfoDto data) {

    }


}
