package nts.uk.file.at.infra.holidayconfirmationtable;

import com.aspose.cells.*;
import com.aspose.pdf.HorizontalAlignment;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.sys.portal.dom.enums.MenuAtr;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.file.at.app.export.holidayconfirmationtable.*;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class AsposeHolidayConfirmationTableGenerator extends AsposeCellsReportGenerator implements HolidayConfirmationTableGenerator {
    @Inject
    private StandardMenuRepository standardMenuRepo;

    private static final String TEMPLATE_FILE = "report/KDR004_template.xlsx";
    private static final String FILE_EXTENSION = ".xlsx";

    private static final int SUB_HEADER_ROW = 0;
    private static final String USER_GUIDE_COL = "J";
    private static final String EXPIRATION_COL = "Q";

    private static final int HEADER_ROW = 1;
    private static final int COLUMN_ER = 0;
    private static final int COLUMN_EMP = 1;
    private static final int COLUMN_CARRY_FORWARD = 2;
    private static final int COLUMN_OCCURRENCE = 3;
    private static final int COLUMN_USED = 4;
    private static final int COLUMN_REMAINING = 5;
    private static final int COLUMN_UNDIGESTED = 6;

    private static final int TEMPLATE_ROWS = 13;
    private static final int MAX_ROW_PER_PAGE = 51;

    @Override
    public void generate(FileGeneratorContext generatorContext, Kdr004DataSource dataSource) {
        List<StandardMenu> menus = standardMenuRepo.findBySystem(dataSource.getCompanyInfo().getCompanyId(), 1);
        String menuName = menus.stream().filter(i -> i.getMenuAtr() == MenuAtr.Menu && i.getProgramId().equals("KDR004"))
                .findFirst().map(i -> i.getDisplayName().v()).orElse(TextResource.localize("KDR004_100"));
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            this.setPageHeader(reportContext, dataSource, menuName);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet sheet = worksheets.get(0);
            sheet.setName(menuName);
            this.printSubHeader(sheet.getCells(), dataSource);
            this.printHeader(sheet.getCells());
            this.printData(sheet, dataSource);
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(menuName + FILE_EXTENSION)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private void setPageHeader(AsposeCellsReportContext reportContext, Kdr004DataSource dataSource, String menuName) {
        reportContext.setHeader(0, "&7&\"MS ゴシック\"" + dataSource.getCompanyInfo().getCompanyName());
        reportContext.setHeader(1, "&12&\"MS ゴシック,Bold\"" + menuName);
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm");
        reportContext.setHeader(2, "&7&\"MS ゴシック\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");
    }

    private void printSubHeader(Cells cells, Kdr004DataSource dataSource) {
        cells.get(USER_GUIDE_COL + (SUB_HEADER_ROW + 1)).setValue(TextResource.localize("KDR004_20"));
        cells.get(EXPIRATION_COL + (SUB_HEADER_ROW + 1)).setValue(TextResource.localize("KDR004_123") + dataSource.getComSubstVacationSetting().map(i -> i.getSetting().getExpirationDate().nameId).orElse(""));
    }

    private void printHeader(Cells cells) {
        cells.get(HEADER_ROW, COLUMN_EMP).setValue(TextResource.localize("KDR004_21"));
        cells.get(HEADER_ROW, COLUMN_CARRY_FORWARD).setValue(TextResource.localize("KDR004_22"));
        cells.get(HEADER_ROW, COLUMN_OCCURRENCE).setValue(TextResource.localize("KDR004_23"));
        cells.get(HEADER_ROW, COLUMN_USED).setValue(TextResource.localize("KDR004_24"));
        cells.get(HEADER_ROW, COLUMN_REMAINING).setValue(TextResource.localize("KDR004_25"));
        cells.get(HEADER_ROW, COLUMN_UNDIGESTED).setValue(TextResource.localize("KDR004_26"));
        cells.get(HEADER_ROW, 7).setValue(TextResource.localize("KDR004_27"));
        cells.get(HEADER_ROW, 9).setValue(TextResource.localize("KDR004_28"));
        cells.get(HEADER_ROW, 10).setValue(TextResource.localize("KDR004_29"));
        cells.get(HEADER_ROW, 11).setValue(TextResource.localize("KDR004_30"));
        cells.get(HEADER_ROW, 12).setValue(TextResource.localize("KDR004_31"));
        cells.get(HEADER_ROW, 13).setValue(TextResource.localize("KDR004_32"));
        cells.get(HEADER_ROW, 14).setValue(TextResource.localize("KDR004_33"));
        cells.get(HEADER_ROW, 15).setValue(TextResource.localize("KDR004_34"));
        cells.get(HEADER_ROW, 16).setValue(TextResource.localize("KDR004_35"));
        cells.get(HEADER_ROW, 17).setValue(TextResource.localize("KDR004_36"));
        cells.get(HEADER_ROW, 18).setValue(TextResource.localize("KDR004_37"));
    }

    private void printData(Worksheet sheet, Kdr004DataSource dataSource) {
        Cells cells = sheet.getCells();
        Map<String, List<HolidayConfirmationTableContent>> mapData = dataSource.getContents().stream().collect(Collectors.groupingBy(HolidayConfirmationTableContent::getHierarchicalCode));
        int row = 3, count = 0;
        List<Integer> wkpIndexes = new ArrayList<>();
        List<Integer> empIndexes = new ArrayList<>();
        List<String> wkpCodes = mapData.keySet().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        for (String wkpCode : wkpCodes) {
            // copy for next workplace
            cells.insertRow(row);
            try {
                cells.copyRows(cells, row + 1, row, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // page break by workplace
            if (dataSource.getPageBreak() == 2 && wkpCodes.indexOf(wkpCode) != 0) {
                sheet.getHorizontalPageBreaks().add(row);
                count = 0;
            }
            List<HolidayConfirmationTableContent> contents = mapData.get(wkpCode);
            contents.sort(Comparator.comparing(HolidayConfirmationTableContent::getEmployeeCode));
            cells.get(row, 0).setValue(TextResource.localize("KDR004_38") + wkpCode + " " + contents.get(0).getWorkplaceName());
            wkpIndexes.add(row);
            row++;
            count++;

            for (HolidayConfirmationTableContent content : contents) {
                if (dataSource.isLinking()) {
                    // print linked content
                    content.getHolidayAcquisitionInfo().get().getLinkingInfos().sort(Comparator.comparing(LinkingInfo::getOccurrenceDate));
                    int col = 9;
                    int size = content.getHolidayAcquisitionInfo().get().getLinkingInfos().size();
                    int loops = size / 10 + 1;

                    // page break by employee
                    if (dataSource.getPageBreak() == 1) {
                        if (contents.indexOf(content) != 0) {
                            cells.insertRow(row);
                            int lastWkpRow = wkpIndexes.get(wkpIndexes.size() - 1);
                            try {
                                cells.copyRows(cells, lastWkpRow, row, 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            sheet.getHorizontalPageBreaks().add(row);
                            wkpIndexes.add(row);
                            row++;
                        } else {
                            if (wkpCodes.indexOf(wkpCode) != 0) {
                                int lastWkpRow = wkpIndexes.get(wkpIndexes.size() - 1);
                                sheet.getHorizontalPageBreaks().add(lastWkpRow);
                            }
                        }
                        count = 1;
                    }

                    for (int loop = 0; loop < loops; loop++) {
                        cells.insertRows(row, 2);
                        try {
                            cells.copyRows(cells, loop == loops - 1 ? row + 7 : row + 5, row, 2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (loop == 0) {
                            this.printCommonContent(cells, row, content);
                            empIndexes.add(row);
                            cells.get(row, 7).setValue(TextResource.localize("KDR004_39"));
                        }
                        cells.get(row, 8).setValue(TextResource.localize("KDR004_41"));
                        cells.get(row + 1, 8).setValue(TextResource.localize("KDR004_42"));
                        for (int i = 0; i < 10; i++) {
                            int idx = loop * 10 + i;
                            if (idx < size) {
                                LinkingInfo linkingInfo = content.getHolidayAcquisitionInfo().get().getLinkingInfos().get(idx);
                                if (i > 0) {
                                    LinkingInfo prevLinkingInfo = content.getHolidayAcquisitionInfo().get().getLinkingInfos().get(idx - 1);
                                    if (linkingInfo.getOccurrenceDate().equals(prevLinkingInfo.getOccurrenceDate())
                                            && linkingInfo.getUseDateNumber() == 0.5
                                            && prevLinkingInfo.getUseDateNumber() == 0.5) {
                                        cells.merge(row, col + i - 1, 1, 2);
                                        Style style = cells.get(row, col + i - 1).getStyle();
                                        style.setHorizontalAlignment(HorizontalAlignment.Center);
                                        cells.get(row, col + i - 1).setStyle(style);
                                    } else {
                                        cells.get(row, col + i).setValue(this.formatDate(linkingInfo.getOccurrenceDate(), linkingInfo.getUseDateNumber(), dataSource.getHowToPrintDate()));
                                    }
                                    if (linkingInfo.getUseDate().equals(prevLinkingInfo.getUseDate())
                                            && linkingInfo.getUseDateNumber() == 0.5
                                            && prevLinkingInfo.getUseDateNumber() == 0.5) {
                                        cells.merge(row + 1, col + i - 1, 1, 2);
                                        Style style = cells.get(row + 1, col + i - 1).getStyle();
                                        style.setHorizontalAlignment(HorizontalAlignment.Center);
                                        cells.get(row + 1, col + i - 1).setStyle(style);
                                    } else {
                                        cells.get(row + 1, col + i).setValue(this.formatDate(linkingInfo.getUseDate(), linkingInfo.getUseDateNumber(), dataSource.getHowToPrintDate()));
                                    }
                                } else {
                                    cells.get(row, col + i).setValue(this.formatDate(linkingInfo.getOccurrenceDate(), linkingInfo.getUseDateNumber(), dataSource.getHowToPrintDate()));
                                    cells.get(row + 1, col + i).setValue(this.formatDate(linkingInfo.getUseDate(), linkingInfo.getUseDateNumber(), dataSource.getHowToPrintDate()));
                                }
                            } else {
                                break;
                            }
                        }
                        row += 2;
                        this.checkPageBreak(sheet, cells, wkpIndexes, empIndexes, row, count);
                    }
                }

                content.getHolidayAcquisitionInfo().get().getOccurrenceAcquisitionDetails().sort(Comparator.comparing(i -> i.getDate().getDayoffDate().get()));
                int col = 9;
                int size = content.getHolidayAcquisitionInfo().get().getOccurrenceAcquisitionDetails().size();
                int loops = size / 10 + 1;

                for (int loop = 0; loop < loops; loop++) {
                    cells.insertRows(row, 2);
                    try {
                        cells.copyRows(cells, loop == loops - 1 ? row + 13 : row + 11, row, 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (loop == 0) {
                        if (!dataSource.isLinking()) {
                            this.printCommonContent(cells, row, content);
                            empIndexes.add(row);
                        }
                        cells.get(row, 7).setValue(TextResource.localize("KDR004_40"));
                    }
                    cells.get(row, 8).setValue(TextResource.localize("KDR004_41"));
                    cells.get(row + 1, 8).setValue(TextResource.localize("KDR004_42"));
                    for (int i = 0; i < 10; i++) {
                        int idx = loop * 10 + i;
                        if (idx < size) {
                            OccurrenceAcquisitionDetail acquisitionDetail = content.getHolidayAcquisitionInfo().get().getOccurrenceAcquisitionDetails().get(idx);
                            if (acquisitionDetail.getOccurrenceDigCls() == OccurrenceDigClass.OCCURRENCE)
                                cells.get(row, col + i).setValue(this.formatNoLinkedDate(acquisitionDetail, dataSource.getHowToPrintDate()));
                            else
                                cells.get(row + 1, col + i).setValue(this.formatNoLinkedDate(acquisitionDetail, dataSource.getHowToPrintDate()));
                        } else {
                            break;
                        }
                    }
                    row += 2;
                    this.checkPageBreak(sheet, cells, wkpIndexes, empIndexes, row, count);
                }
            }
        }
        if (!dataSource.isLinking()) {
            cells.deleteColumn(7);
        }
        // remove template rows
        cells.deleteRows(row, TEMPLATE_ROWS);
    }

    private void printCommonContent(Cells cells, int row, HolidayConfirmationTableContent content) {
        cells.get(row, COLUMN_ER).setValue(content.getHolidayAcquisitionInfo().get().isError() ? "ER" : null);
        cells.get(row, COLUMN_EMP).setValue(content.getEmployeeCode() + " " + content.getEmployeeName());
        cells.get(row, COLUMN_CARRY_FORWARD).setValue(String.format("%.1f", content.getHolidayAcquisitionInfo().get().getCarryForwardNumber()));
        cells.get(row, COLUMN_OCCURRENCE).setValue(String.format("%.1f", content.getHolidayAcquisitionInfo().get().getOccurrencesNumber()));
        cells.get(row, COLUMN_USED).setValue(String.format("%.1f", content.getHolidayAcquisitionInfo().get().getUsedNumber()));

        cells.get(row, COLUMN_REMAINING).setValue(String.format("%.1f", content.getHolidayAcquisitionInfo().get().getRemainingNumber()));
        if (content.getHolidayAcquisitionInfo().get().getRemainingNumber() < 0) {
            Style remainingStyle = cells.get(row, COLUMN_REMAINING).getStyle();
            Font remainingFont = remainingStyle.getFont();
            remainingFont.setColor(Color.getRed());
            cells.get(row, COLUMN_REMAINING).setStyle(remainingStyle);
        }

        cells.get(row, COLUMN_UNDIGESTED).setValue(String.format("%.1f", content.getHolidayAcquisitionInfo().get().getUndigestedNumber()));
        if (content.getHolidayAcquisitionInfo().get().getUndigestedNumber() < 0) {
            Style remainingStyle = cells.get(row, COLUMN_UNDIGESTED).getStyle();
            Font remainingFont = remainingStyle.getFont();
            remainingFont.setColor(Color.getRed());
            cells.get(row, COLUMN_UNDIGESTED).setStyle(remainingStyle);
        }
    }

    /**
     *
     * @param date
     * @param howToPrintDate 1: YYYY/MM/DD, 0: MM/DD
     * @return
     */
    private String formatDate(GeneralDate date, double usedNumber, int howToPrintDate) {
        StringBuilder formattedDate = new StringBuilder();
        if (howToPrintDate == 0) {
            formattedDate.append(date.toString("MM/dd"));
        } else {
            formattedDate.append(date.toString());
        }
        if (usedNumber != 1.0) {
            formattedDate.append(TextResource.localize("KDR004_120"));
        }
        return formattedDate.toString();
    }

    /**
     *
     * @param detail
     * @param howToPrintDate 1: YYYY/MM/DD, 0: MM/DD
     * @return
     */
    private String formatNoLinkedDate(OccurrenceAcquisitionDetail detail, int howToPrintDate) {
        StringBuilder formattedDate = new StringBuilder();
        if (howToPrintDate == 0) {
            formattedDate.append(detail.getDate().getDayoffDate().get().toString("MM/dd"));
        } else {
            formattedDate.append(detail.getDate().getDayoffDate().get().toString());
        }
        if (detail.getOccurrencesUseNumber() != 1.0) {
            formattedDate.append(TextResource.localize("KDR004_120"));
        }
        if (detail.getStatus() == MngDataStatus.SCHEDULE || detail.getStatus() == MngDataStatus.NOTREFLECTAPP) {
            formattedDate.insert(0, "(");
            formattedDate.append(")");
        }
        if (detail.getExpiringThisMonth().isPresent() && detail.getExpiringThisMonth().get()) {
            formattedDate.insert(0, "[");
            formattedDate.append("]");
        }
        return formattedDate.toString();
    }

    private void checkPageBreak(Worksheet sheet, Cells cells, List<Integer> wkpIndexes, List<Integer> empIndexes, int row, int count) {
        if (count + 2 > MAX_ROW_PER_PAGE) {
            int lastEmpRow = empIndexes.get(empIndexes.size() - 1);
            int lastWkpRow = wkpIndexes.get(wkpIndexes.size() - 1);
            if (lastEmpRow == lastWkpRow + 1) {
                sheet.getHorizontalPageBreaks().add(lastWkpRow);
            } else {
                sheet.getHorizontalPageBreaks().add(lastEmpRow);
                cells.insertRow(lastEmpRow);
                try {
                    cells.copyRows(cells, lastWkpRow, lastEmpRow, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                wkpIndexes.add(lastEmpRow);
                empIndexes.set(empIndexes.size() - 1, lastEmpRow + 1);
                row++;
            }
            count = row - wkpIndexes.get(wkpIndexes.size() - 1);
        } else {
            count += 2;
        }
    }
}
