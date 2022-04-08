package nts.uk.file.com.infra.generate;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.aspose.cells.*;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval.ErrorContent;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
import nts.uk.file.com.app.EmployeeUnregisterOutputDataSoure;
import nts.uk.file.com.app.EmployeeUnregisterOutputGenerator;
import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.query.model.workplace.WorkplaceInfoImport;
import nts.uk.query.model.workplace.WorkplaceModel;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AsposeEmployeeUnregisterOutputReportGenerator extends AsposeCellsReportGenerator
        implements EmployeeUnregisterOutputGenerator {

    private static final String TEMPLATE_FILE = "report/承認ルート未登録社員一覧.xlsx";

    private static final String REPORT_FILE_NAME = "承認ルート未登録社員一覧.xlsx";

    private static final String FONT_NAME = "ＭＳ Ｐゴシック";

    @Override
    public void generate(FileGeneratorContext generatorContext, EmployeeUnregisterOutputDataSoure dataSource) {
        val designer = this.createContext(TEMPLATE_FILE);
        Workbook workbook = designer.getWorkbook();
        WorksheetCollection worksheets = workbook.getWorksheets();
        Worksheet worksheet = worksheets.get(0);

        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setHeader(0, "&9&\"" + FONT_NAME + "\"" + dataSource.getCompanyName());
        pageSetup.setPrintTitleRows("$2:$3");

        this.printHeader(worksheet.getCells(), dataSource.getBaseDate());
        this.printData(worksheet.getCells(), dataSource);

        designer.getDesigner().setWorkbook(workbook);
        designer.processDesigner();

        designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME)));
    }

    private void printHeader(Cells cells, GeneralDate baseDate) {
        cells.get(0, 0).setValue(TextResource.localize("CMM018_201") + baseDate.toString("yyyy/MM/dd"));
        cells.get(1, 0).setValue(TextResource.localize("CMM018_202"));
        cells.get(1, 2).setValue(TextResource.localize("CMM018_203"));
        cells.get(1, 4).setValue(TextResource.localize("CMM018_204"));
        cells.get(1, 5).setValue(TextResource.localize("CMM018_205"));
        cells.get(2, 5).setValue(TextResource.localize("CMM018_206"));
        cells.get(2, 6).setValue(TextResource.localize("CMM018_207"));
        cells.get(2, 7).setValue(TextResource.localize("CMM018_208"));
        cells.get(2, 8).setValue(TextResource.localize("CMM018_209"));
        cells.get(1, 9).setValue(TextResource.localize("CMM018_210"));
    }

    private void printData(Cells cells, EmployeeUnregisterOutputDataSoure dataSource) {
        AtomicInteger row = new AtomicInteger(3);

        double height = cells.getRowHeight(row.get());

        Style style = new Style();
        style.getFont().setName("ＭＳ Ｐゴシック");
        style.getFont().setSize(9);
        style.setShrinkToFit(true);
        style.setPattern(BackgroundType.SOLID);
        style.setVerticalAlignment(TextAlignmentType.CENTER);

        cells.deleteRows(3, 30);

        Map<String, EmployeeInformation> employeeInformationMap = dataSource.getEmployeeInfors().stream().collect(Collectors.toMap(EmployeeInformation::getEmployeeId, Function.identity()));

        Map<String, List<EmployeeUnregisterOutput>> mapByWkp = dataSource.getEmployeeUnregisterOutputLst().stream()
                .collect(Collectors.groupingBy(i -> employeeInformationMap.get(i.getEmployeeId()).getWorkplace().map(WorkplaceModel::getWorkplaceCode).orElse("")));
        mapByWkp.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey())).forEach(entry1 -> {
            style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);
            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);

            cells.get(row.get(), 0).setValue(entry1.getKey());
            cells.get(row.get(), 0).setStyle(style);

            cells.get(row.get(), 1).setValue(employeeInformationMap.get(entry1.getValue().get(0).getEmployeeId()).getWorkplace().map(WorkplaceModel::getWorkplaceName).orElse(""));
            cells.get(row.get(), 1).setStyle(style);

            Map<String, List<EmployeeUnregisterOutput>> mapByEmp = entry1.getValue().stream().collect(Collectors.groupingBy(i -> employeeInformationMap.get(i.getEmployeeId()).getEmployeeCode()));
            mapByEmp.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey())).forEach(entry2 -> {
                style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);
                style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);

                cells.get(row.get(), 2).setValue(entry2.getKey());
                cells.get(row.get(), 2).setStyle(style);

                cells.get(row.get(), 3).setValue(employeeInformationMap.get(entry2.getValue().get(0).getEmployeeId()).getBusinessName());
                cells.get(row.get(), 3).setStyle(style);

                entry2.getValue().forEach(appr -> {
                    if (appr.getErrorContents().isEmpty()) {
                        appr.getErrorContents().add(new ErrorContent(1, null)); // add temporary element to loop
                    }
                    appr.getErrorContents().forEach(error -> {
                        style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);

                        cells.get(row.get(), 4).setValue(appr.getAppTarget());
                        style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOUBLE);
                        cells.get(row.get(), 4).setStyle(style);

                        if (appr.getRoute() != null) {
                            if (appr.getRoute() == 1) {
                                cells.get(row.get(), 5).setValue(TextResource.localize("CMM018_211"));
                            } else if (appr.getRoute() == 2) {
                                cells.get(row.get(), 5).setValue(TextResource.localize("CMM018_212"));
                            } else {
                                cells.get(row.get(), 5).setValue(TextResource.localize("CMM018_213"));
                            }
                        }
                        style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);
                        cells.get(row.get(), 5).setStyle(style);

                        if (appr.getWorkplaceId().isPresent()) {
                            String wkpName = dataSource.getWorkplaceInfos().stream()
                                    .filter(i -> i.getWorkplaceId().equals(appr.getWorkplaceId().get())).findFirst()
                                    .map(WorkplaceInfoImport::getWorkplaceName).orElse("");
                            cells.get(row.get(), 6).setValue(wkpName);
                        }
                        cells.get(row.get(), 6).setStyle(style);

                        if (appr.getCommon() != null) {
                            cells.get(row.get(), 7).setValue(appr.getCommon() ? TextResource.localize("CMM018_214") : TextResource.localize("CMM018_215"));
                        }
                        cells.get(row.get(), 7).setStyle(style);

                        if (error.getErrorFlag() != null) {
                            cells.get(row.get(), 8).setValue(error.getApprovalPhaseOrder());
                        }
                        cells.get(row.get(), 8).setStyle(style);

                        if (error.getErrorFlag() == null) {
                            cells.get(row.get(), 9).setValue(TextResource.localize("CMM018_216"));
                        } else if (error.getErrorFlag() == ErrorFlag.NO_APPROVER) {
                            cells.get(row.get(), 9).setValue(TextResource.localize("CMM018_217"));
                        } else if (error.getErrorFlag() == ErrorFlag.APPROVER_UP_10) {
                            cells.get(row.get(), 9).setValue(TextResource.localize("CMM018_218"));
                        }
                        style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
                        cells.get(row.get(), 9).setStyle(style);

                        style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);
                        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.NONE);
                        if (cells.get(row.get(), 0).getValue() == null) {
                            cells.get(row.get(), 0).setStyle(style);
                            cells.get(row.get(), 1).setStyle(style);
                        }
                        if (cells.get(row.get(), 2).getValue() == null) {
                            cells.get(row.get(), 2).setStyle(style);
                            cells.get(row.get(), 3).setStyle(style);
                        }

                        cells.setRowHeight(row.get(), height);

                        row.getAndIncrement();

                        if (row.get() % 2 != 0) {
                            style.setForegroundColor(Color.getWhite());
                        } else {
                            style.setForegroundColor(Color.fromArgb(221, 235, 247));
                        }
                    });
                });
            });
        });
    }
}
