package nts.uk.file.at.app.export.worktype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppAcceptLimitDay;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

@Stateless
@DomainID(value ="PreparationBeforeApply")
public class PreparationBeforeApplyExportImpl implements MasterListData{


    private static final String COLUMN_NO_HEADER_1 = "COLUMN_NO_HEADER_1";
    private static final String COLUMN_NO_HEADER_2 = "COLUMN_NO_HEADER_2";
    private static final String COLUMN_NO_HEADER_3 = "COLUMN_NO_HEADER_3";
    private static final String COLUMN_NO_HEADER_4 = "COLUMN_NO_HEADER_4";
    private static final String KAF022_454 = "項目";
    private static final String KAF022_455 = "値";
    private static final String KAF022_468 = "利用する";
    private static final String KAF022_478 = "定型理由の表示";
    private static final String KAF022_479 = "申請理由の表示";
    private static final int ROW_SIZE_A4 = 4;
    private static final int ROW_SIZE_A5 = 3;
    private static final int ROW_SIZE_A7 = 7;
    private static final int ROW_SIZE_A7_BOTTOM = 3;
    private static final int ROW_SIZE_A8_TOP = 6;
    private static final int ROW_SIZE_A8_CENTER = 2;
    private static final int ROW_SIZE_A9 = 6;
    private static final int ROW_SIZE_A10 = 2;
    private static final int ROW_SIZE_A13 = 8;

    @Inject
    private PreparationBeforeApplyRepository preparationBeforeApplyRepository;

    @Inject
    private PreparationBeforeApplyExport preparationBeforeApply;

    @Inject
    private ApprovalFunctionConfigExport approvalFunctionConfigExport;

    @Inject
    private ApprovalFunctionConfigRepository approvalRepository;

    @Inject
    private EmploymentApprovalSettingExport employmentApprovalSettingExport;


    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0){
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KAF022_454, TextResource.localize("KAF022_454"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(COLUMN_NO_HEADER_1, TextResource.localize(""),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(COLUMN_NO_HEADER_2, TextResource.localize(""),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(COLUMN_NO_HEADER_3, TextResource.localize(""),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(COLUMN_NO_HEADER_4, TextResource.localize(""),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_455, TextResource.localize("KAF022_455"),
        ColumnTextAlign.LEFT, "", true));
        return columns;
    }
	
	private  List<MasterData> putDatasA4(Object[] export ) {
        List<MasterData> datasA4 = new ArrayList<>();
        for(int i= 0 ; i< ROW_SIZE_A4; i++) {
            Map<String, MasterCellData> dataA4 = new HashMap<>();
            dataA4.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(((Long) export[0]).intValue() == 1 && i == 0 ? TextResource.localize("KAF022_456") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA4.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_1)
                    .value( i== 0 ? export[1] : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA4.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_2)
                    .value(this.getTextA4(i))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA4.put(COLUMN_NO_HEADER_3, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_3)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA4.put(COLUMN_NO_HEADER_4, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_4)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA4.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(getValueA4(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(getColumnTextAlignA4(i)))
                    .build());
          datasA4.add(MasterData.builder().rowData(dataA4).build());
        }
        return datasA4;
    }

    private  List<MasterData> putDatasA5(Object[] export ) {
        List<MasterData> datasA5 = new ArrayList<>();
        for(int i= 0 ; i< ROW_SIZE_A5; i++) {
            Map<String, MasterCellData> dataA5 = new HashMap<>();
            dataA5.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value( i == 0 ? TextResource.localize("KAF022_460") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA5.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_1)
                    .value(this.getTextA5(i))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA5.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_2)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA5.put(COLUMN_NO_HEADER_3, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_3)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA5.put(COLUMN_NO_HEADER_4, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_4)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA5.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(getValueA5(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datasA5.add(MasterData.builder().rowData(dataA5).build());
        }
        return datasA5;
    }

    private  List<MasterData> putDatasA6(List<Object[]> export ) {
        List<MasterData> datasA6 = new ArrayList<>();
        EnumSet.allOf(ApplicationType.class)
                .forEach(i -> {
                    String appName = getValueA6(i, export);
                    if (appName != null) {
                        Map<String, MasterCellData> dataA6 = new HashMap<>();
                        dataA6.put(KAF022_454, MasterCellData.builder()
                                .columnId(KAF022_454)
                                .value(getTextA6(i) ? TextResource.localize("KAF022_464") : "")
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
                        dataA6.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                                .columnId(COLUMN_NO_HEADER_1)
                                .value(getTextA6(i) ? TextResource.localize("KAF022_465") : "")
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
                        dataA6.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                                .columnId(COLUMN_NO_HEADER_2)
                                .value(i.name)
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
                        dataA6.put(COLUMN_NO_HEADER_3, MasterCellData.builder()
                                .columnId(COLUMN_NO_HEADER_3)
                                .value("")
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
                        dataA6.put(COLUMN_NO_HEADER_4, MasterCellData.builder()
                                .columnId(COLUMN_NO_HEADER_3)
                                .value("")
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
                        dataA6.put(KAF022_455, MasterCellData.builder()
                                .columnId(KAF022_455)
                                .value(appName)
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
                        datasA6.add(MasterData.builder().rowData(dataA6).build());
                    }
                });
        return datasA6;
    }

    private  List<MasterData> putDatasA7(Object[] export ) {
        List<MasterData> datasA7 = new ArrayList<>();
        for(int i = 0; i< ROW_SIZE_A7; i++) {
            Map<String, MasterCellData> dataA7 = new HashMap<>();
            dataA7.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(i == 0 ? TextResource.localize("KAF022_464") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_1)
                    .value(i == 0 ? PreparationBeforeApplyExport.getTextApplication(((BigDecimal)export[27]).intValue()) : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_2)
                    .value(getTextA7Top(i,2))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(COLUMN_NO_HEADER_3, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_3)
                    .value(getTextA7Top(i,0))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(COLUMN_NO_HEADER_4, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_4)
                    .value(getTextA7Top(i,1))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(getValueA7Top(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(getColumnTextAlign(i)))
                    .build());
            datasA7.add(MasterData.builder().rowData(dataA7).build());
        }
        return datasA7;
    }

    private  List<MasterData> putDatasA7Bottom(Object[] export ) {
        List<MasterData> datasA7 = new ArrayList<>();
        for(int i = 0;i< ROW_SIZE_A7_BOTTOM ; i++) {
            Map<String, MasterCellData> dataA7 = new HashMap<>();
            dataA7.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_1)
                    .value(i == 0 ? PreparationBeforeApplyExport.getTextApplication(((BigDecimal)export[27]).intValue()) : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_2)
                    .value(getTextA7Bottom(i,0))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(COLUMN_NO_HEADER_3, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_3)
                    .value(getTextA7Bottom(i,1))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(COLUMN_NO_HEADER_4, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_4)
                    .value(i == 1 ? TextResource.localize("KAF022_470") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA7.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(getValueA7Bottom(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(i == 1 ? ColumnTextAlign.RIGHT : ColumnTextAlign.LEFT))
                    .build());
            datasA7.add(MasterData.builder().rowData(dataA7).build());
        }
        return datasA7;
    }

    private  List<MasterData> putDatasA8(Object[] export ) {
        List<MasterData> datasA8 = new ArrayList<>();
        for(int i = 0; i< ROW_SIZE_A8_TOP; i++) {
            Map<String, MasterCellData> dataA8Top = new HashMap<>();
            dataA8Top.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value( i == 0 && ((Long)export[21]).intValue() == 1 ? TextResource.localize("KAF022_464")  : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Top.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_1)
                    .value( i == 0 ? PreparationBeforeApplyExport.getTextApplication(((BigDecimal)export[27]).intValue()) : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Top.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_2)
                    .value(this.getTextA8Top(i,0))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Top.put(COLUMN_NO_HEADER_3, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_3)
                    .value(getTextA8Top(i, 1))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Top.put(COLUMN_NO_HEADER_4, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_4)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA8Top.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(getValueA8Top(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datasA8.add(MasterData.builder().rowData(dataA8Top).build());
        }
        return datasA8;
    }


    private List<MasterData> putDatasA8Center(List<Object[]> export) {
        List<MasterData> datasA8Center = new ArrayList<>();
        EnumSet.allOf(HolidayAppType.class).forEach(item -> {
            Object[] obj = getDataA8Center(export, item);
            if(obj != null) {
                for (int i = 0; i < ROW_SIZE_A8_CENTER; i++) {
                    Map<String, MasterCellData> dataA8Center = new HashMap<>();
                    dataA8Center.put(KAF022_454, MasterCellData.builder()
                            .columnId(KAF022_454)
                            .value("")
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                            .build());
                    dataA8Center.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                            .columnId(COLUMN_NO_HEADER_1)
                            .value("")
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                            .build());
                    dataA8Center.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                            .columnId(COLUMN_NO_HEADER_2)
                            .value(i == 0 ? TextResource.localize(item.name) : "")
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                            .build());
                    dataA8Center.put(COLUMN_NO_HEADER_3, MasterCellData.builder()
                            .columnId(COLUMN_NO_HEADER_3)
                            .value(i == 0 ? TextResource.localize(KAF022_478) : TextResource.localize(KAF022_479))
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                            .build());
                    dataA8Center.put(COLUMN_NO_HEADER_4, MasterCellData.builder()
                            .columnId(COLUMN_NO_HEADER_4)
                            .value(TextResource.localize(KAF022_468))
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                            .build());
                    dataA8Center.put(KAF022_455, MasterCellData.builder()
                            .columnId(KAF022_455)
                            .value(getValueA8Center(i, obj))
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                            .build());
                    datasA8Center.add(MasterData.builder().rowData(dataA8Center).build());
                }
            }
        });
        return datasA8Center;
    }

    private  List<MasterData> putDatasA9(Object[] export ) {
        List<MasterData> datasA9 = new ArrayList<>();
        for(int i = 0; i< ROW_SIZE_A9; i++) {
            Map<String, MasterCellData> dataA9 = new HashMap<>();
            dataA9.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(i == 0 ? TextResource.localize("KAF022_485") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA9.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_1)
                    .value(this.getTextA9(i))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA9.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_2)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA9.put(COLUMN_NO_HEADER_3, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_3)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA9.put(COLUMN_NO_HEADER_4, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_4)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA9.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(this.getValueA9(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datasA9.add(MasterData.builder().rowData(dataA9).build());
        }
        return datasA9;
    }

    private  List<MasterData> putDatasA10(Object[] export ) {
        List<MasterData> datasA10 = new ArrayList<>();
        for(int i = 0; i< ROW_SIZE_A10; i++) {
            Map<String, MasterCellData> dataA10 = new HashMap<>();
            dataA10.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(i == 0 ? TextResource.localize("KAF022_492") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA10.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_1)
                    .value(i == 0 ? TextResource.localize("KAF022_493") : TextResource.localize("KAF022_494"))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA10.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_2)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA10.put(COLUMN_NO_HEADER_3, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_3)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA10.put(COLUMN_NO_HEADER_4, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_4)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA10.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(getValueEnum(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datasA10.add(MasterData.builder().rowData(dataA10).build());
        }
        return datasA10;
    }

    private  List<MasterData> putDatasA11(List<Object[]> export ) {
        List<MasterData> datasA11 = new ArrayList<>();
        Map<String, MasterCellData> dataA11 = new HashMap<>();
        dataA11.put(KAF022_454, MasterCellData.builder()
                .columnId(KAF022_454)
                .value(TextResource.localize("KAF022_495"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA11.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                .columnId(COLUMN_NO_HEADER_1)
                .value(TextResource.localize("KAF022_496"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA11.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                .columnId(COLUMN_NO_HEADER_2)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA11.put(COLUMN_NO_HEADER_3, MasterCellData.builder()
                .columnId(COLUMN_NO_HEADER_3)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA11.put(COLUMN_NO_HEADER_4, MasterCellData.builder()
                .columnId(COLUMN_NO_HEADER_4)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA11.put(KAF022_455, MasterCellData.builder()
                .columnId(KAF022_455)
                .value(getValueA11(export))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        datasA11.add(MasterData.builder().rowData(dataA11).build());
        return datasA11;
    }

    private  List<MasterData> putDatasA12(Object[] export ) {
        List<MasterData> datasA12 = new ArrayList<>();
        Map<String, MasterCellData> dataA12 = new HashMap<>();
        dataA12.put(KAF022_454, MasterCellData.builder()
                .columnId(KAF022_454)
                .value(TextResource.localize("KAF022_497"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA12.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                .columnId(COLUMN_NO_HEADER_1)
                .value(TextResource.localize("KAF022_498"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA12.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                .columnId(COLUMN_NO_HEADER_2)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA12.put(COLUMN_NO_HEADER_3, MasterCellData.builder()
                .columnId(COLUMN_NO_HEADER_3)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA12.put(COLUMN_NO_HEADER_4, MasterCellData.builder()
                .columnId(COLUMN_NO_HEADER_4)
                .value("")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        dataA12.put(KAF022_455, MasterCellData.builder()
                .columnId(KAF022_455)
                .value(((BigDecimal) export[12]).intValue() == 1 ? TextResource.localize("KAF022_75") : TextResource.localize("KAF022_82"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        datasA12.add(MasterData.builder().rowData(dataA12).build());
        return datasA12;
    }

    private  List<MasterData> putDatasA13(Object[] export ) {
        List<MasterData> datasA13 = new ArrayList<>();
        for(int i = 0; i< ROW_SIZE_A13; i++) {
            Map<String, MasterCellData> dataA13 = new HashMap<>();
            dataA13.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(i == 0 ? TextResource.localize("KAF022_499") : "")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA13.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_1)
                    .value(this.getTextA13(i))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA13.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_2)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA13.put(COLUMN_NO_HEADER_3, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_3)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA13.put(COLUMN_NO_HEADER_4, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_4)
                    .value("")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            dataA13.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(this.getValueA13(i, export))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datasA13.add(MasterData.builder().rowData(dataA13).build());
        }
        return datasA13;
    }

    private String getValueA11(List<Object[]> obj){
        StringBuilder value = new StringBuilder();
        obj.forEach(item ->{
                if(item[19] != null) {
                    value.append(PreparationBeforeApplyExport.getTextApplication(((BigDecimal)item[20]).intValue()));
                    value.append(",");
                }
        });
        if(value.length() != 0) {
            value.deleteCharAt(value.length() - 1);
        }
        return value.toString();
    }

    private String getValueA6(ApplicationType appType, List<Object[]> obj){
         Optional<Object[]> temp = obj.stream().filter(i -> i[36] != null ? appType.value == ((BigDecimal) i[36]).intValue() : appType.value == -1).findFirst();
         if(temp.isPresent()) {
             return temp.get()[37] != null ? temp.get()[37].toString() + TextResource.localize("KAF022_653") : "";
         }
        return null;
    }

    private boolean getTextA6(ApplicationType appType){
        return (appType == ApplicationType.OVER_TIME_APPLICATION);
    }

    private String getTextA4(int i){
        if(i == 0) {
            return TextResource.localize("KAF022_457");
        }
        if(i == 1) {
            return TextResource.localize("KAF022_458");
        }
        if(i == 2) {
            return TextResource.localize("KAF022_459");
        }
        return "";
    }

    private String getValueA4(int i, Object[] obj){
        if(i == 0) {
            return obj[2].toString();
        }
        if(i == 1) {
            return ((BigDecimal)obj[3]).intValue() == 1 ? "○" : "-";
        }
        if(i == 2 && ((BigDecimal)obj[3]).intValue() == 1) {
            return ((BigDecimal) obj[4]).intValue() == 0 ? TextResource.localize("KAF022_321") + TextResource.localize("KAF022_508") : TextResource.localize("KAF022_322") + TextResource.localize("KAF022_508");
        }
        if(i == 3 && ((BigDecimal)obj[3]).intValue() == 1) {
            return getTextDeadLine(((BigDecimal) obj[5]).intValue());
        }
        return "";
    }

    private ColumnTextAlign getColumnTextAlignA4(int line){
        if(line == 3) {
            return ColumnTextAlign.RIGHT;
        }
        return ColumnTextAlign.LEFT;
    }
    
    private String getTextDeadLine(int value){
        int text = 323 + value;
        String resource = "KAF022_" + text;
        return TextResource.localize(resource) + TextResource.localize("KAF022_509");
    }

    private String getTextA5(int i){
        if(i == 0) {
            return TextResource.localize("KAF022_461");
        }
        if(i == 1) {
            return TextResource.localize("KAF022_462");
        }
        return TextResource.localize("KAF022_463");
    }

    private String getValueA5(int i, Object[] obj){
        if(i == 0) {
            if(((BigDecimal)obj[6]).intValue() == 0) {
                return TextResource.localize("KAF022_403");
            }
            return TextResource.localize("KAF022_404");
        }
        if(i == 1) {
            if(((BigDecimal)obj[13]).intValue() == 1) {
                return TextResource.localize("KAF022_272");
            }
            return TextResource.localize("KAF022_273");
        }
        if(i == 2) {
            if(((BigDecimal)obj[7]).intValue() == 1) {
                return TextResource.localize("KAF022_389");
            }
            return TextResource.localize("KAF022_390");
        }
        return "";
    }

    private String getTextA7Top(int i, int type){
        if(i == 0 && type == 0) {
            return TextResource.localize("KAF022_468");
        }
        if(i == 1 && type == 0) {
            return TextResource.localize("KAF022_469");
        }
        if(i == 2 && type == 0) {
            return TextResource.localize("KAF022_470");
        }
        if(i == 3 && type == 0) {
            return TextResource.localize("KAF022_472");
        }
        if(i == 6 && type == 0) {
            return TextResource.localize("KAF022_477");
        }
        if(i == 2 && type == 1) {
            return TextResource.localize("KAF022_471");
        }
        if(i == 3 && type == 1) {
            return TextResource.localize("KAF022_473");
        }
        if(i == 4 && type == 1) {
            return TextResource.localize("KAF022_474");
        }
        if(i == 5 && type == 1) {
            return TextResource.localize("KAF022_475");
        }
        if(i == 0 && type == 2) {
            return TextResource.localize("KAF022_467");
        }
        if(i == 6 && type == 2) {
            return TextResource.localize("KAF022_476");
        }
        return "";
    }

    private String getTextA7Bottom(int i, int column){
        if(i == 0 && column == 0) {
            return TextResource.localize("KAF022_467");
        }
        if(i == 2 && column == 0) {
            return TextResource.localize("KAF022_476");
        }
        if(i == 0 && column == 1) {
            return TextResource.localize("KAF022_468");
        }
        if(i == 2 && column == 1) {
            return TextResource.localize("KAF022_477");
        }
        if(i == 4 && column == 1) {
            return TextResource.localize("KAF022_474");
        }
        return "";
    }

    private String getTextA8Top(int i, int type){
        if(i == 0 && type == 0) {
            return TextResource.localize("KAF022_478");
        }
        if(i == 1 && type == 0) {
            return TextResource.localize("KAF022_479");
        }
        if(i == 2 && type == 0) {
            return TextResource.localize("KAF022_480");
        }
        if(i == 3 && type == 0) {
            return TextResource.localize("KAF022_482");
        }
        if(i == 4 && type == 0) {
            return TextResource.localize("KAF022_483");
        }
        if(i == 5 && type == 0) {
            return TextResource.localize("KAF022_484");
        }
        if((i == 0 && type == 1) || (i == 1 && type == 1)) {
            return TextResource.localize("KAF022_468");
        }
        if((i == 2 && type == 1) || (i == 3 && type == 1) || (i == 5 && type == 1)) {
            return TextResource.localize("KAF022_481");
        }
        return "";
    }

    private String getTextA9(int i){
        if(i == 0 ) {
            return TextResource.localize("KAF022_486");
        }
        if(i == 1) {
            return TextResource.localize("KAF022_487");
        }
        if(i == 2) {
            return TextResource.localize("KAF022_488");
        }
        if(i == 3) {
            return TextResource.localize("KAF022_489");
        }
        if(i == 4) {
            return TextResource.localize("KAF022_490");
        }
        return TextResource.localize("KAF022_491");
    }

    private String getValueA9(int i, Object[] obj){
        if(i == 0 && obj[42] != null) {
            return ((BigDecimal)obj[42]).intValue() == 1 ? TextResource.localize("KAF022_100") : TextResource.localize("KAF022_101");
        }
        if(i == 1 && obj[43] != null) {
            return ((BigDecimal)obj[43]).intValue() == 1 ? TextResource.localize("KAF022_100") : TextResource.localize("KAF022_101");
        }
        if(i == 2 && obj[44] != null) {
            return ((BigDecimal)obj[44]).intValue() == 1 ? TextResource.localize("KAF022_437") : TextResource.localize("KAF022_438");
        }
        if(i == 3 && obj[45] != null) {
            return ((BigDecimal)obj[45]).intValue() == 1 ? TextResource.localize("KAF022_437") : TextResource.localize("KAF022_438");
        }
        if(i == 4 && obj[46] != null) {
            return ((BigDecimal)obj[46]).intValue() == 1 ? TextResource.localize("KAF022_437") : TextResource.localize("KAF022_438");
        }
        if(i == 5 && obj[47] != null) {
            return ((BigDecimal)obj[47]).intValue() == 1 ? TextResource.localize("KAF022_437") : TextResource.localize("KAF022_438");

        }
        return "";
    }

    private String getTextA13(int i){
        if(i == 0 ) {
            return TextResource.localize("KAF022_500");
        }
        if(i == 1) {
            return TextResource.localize("KAF022_501");
        }
        if(i == 2) {
            return TextResource.localize("KAF022_502");
        }
        if(i == 3) {
            return TextResource.localize("KAF022_503");
        }
        if(i == 4) {
            return TextResource.localize("KAF022_504");
        }
        if(i == 5) {
            return TextResource.localize("KAF022_505");
        }
        if(i == 6) {
            return TextResource.localize("KAF022_506");
        }
        if(i == 7) {
            return TextResource.localize("KAF022_507");
        }
        return "";
    }

    private String getValueA13(int i, Object[] obj){
        if(i == 0 && obj[8] != null) {
            return obj[8].toString();
        }
        if(i == 1 && obj[9] != null) {
            return obj[9].toString();
        }
        if(i == 2 && obj[10] != null) {
            return obj[10].toString();
        }
        if(i == 3 && obj[11] != null) {
            return obj[11].toString();
        }
        if(i == 4 && obj[14] != null) {
            return obj[14].toString();

        }
        if(i == 5 && obj[15] != null) {
            return obj[15].toString();
        }
        if(i == 6 && obj[16] != null) {
            return obj[16].toString();

        }
        if(i == 7 && obj[49] != null) {
            return ((BigDecimal)obj[49]).intValue() == 1 ? TextResource.localize("KAF022_100") : TextResource.localize("KAF022_101");
        }
        return "";
    }

    private ColumnTextAlign getColumnTextAlign(int line){
        if(line == 2 || line == 3 || line == 4 || line == 5) {
            return ColumnTextAlign.RIGHT;
        }
        return ColumnTextAlign.LEFT;
    }

    private String getValueA7Top(int i, Object[] obj){
        if(i == 0 && obj[28] != null ) {
            return ((BigDecimal)obj[28]).intValue() == 1 ? "○" : "-";
        }
        if(i == 1 && obj[23] != null ) {
            if (((BigDecimal) obj[28]).intValue() == 1) {
                return (((BigDecimal) obj[23]).intValue() == 1) ? TextResource.localize("KAF022_63") : TextResource.localize("KAF022_66");
            }
        }
        if(i == 2 && obj[29] != null && ((BigDecimal) obj[23]).intValue() == 1) {
            return ((BigDecimal)obj[28]).intValue() == 1 ? EnumAdaptor.valueOf(((BigDecimal) obj[29]).intValue(), AppAcceptLimitDay.class).name + TextResource.localize("KAF022_510") : "";
        }
        if(i == 3 && obj[24] != null && ((BigDecimal) obj[23]).intValue() == 0) {
            return ((BigDecimal)obj[28]).intValue() == 1 ? convertToTime(((BigDecimal) obj[24]).intValue()) + TextResource.localize("KAF022_510") : "";
        }
        if(i == 4 && obj[25] != null && ((BigDecimal) obj[23]).intValue() == 0) {
            return ((BigDecimal)obj[28]).intValue() == 1 ? convertToTime(((BigDecimal)obj[25]).intValue()) : "";
        }
        if(i == 5 && obj[26] != null && ((BigDecimal) obj[23]).intValue() == 0) {
            return ((BigDecimal)obj[28]).intValue() == 1 ? convertToTime(((BigDecimal) obj[26]).intValue()) : "";
        }
        if(i == 6 && obj[30] != null) {
            return ((BigDecimal)obj[30]).intValue() == 1 ? "○" : "-";
        }
        return "";
    }

    private String convertToTime(int param){
        int m = param / 60;
        int s = param % 60;
        return m > 9 ? String.format("%02d:%02d", m, s) : String.format("%2d:%02d", m, s);
    }

    private String getValueA8Top(int i, Object[] obj){
        if(i == 0 && obj[51] != null ) {
            return ((BigDecimal)obj[51]).intValue() == 1 ? "○" : "-";
        }
        if(i == 1 && obj[33] != null ) {
            return ((BigDecimal)obj[33]).intValue() == 1 ? "○" : "-";
        }
        if(i == 2 && obj[31] != null ) {
            return ((BigDecimal)obj[31]).intValue() == 1 ? "○" : "-";
        }
        if(i == 3 && obj[32] != null ) {
            return ((BigDecimal)obj[32]).intValue() == 1 ? "○" : "-";
        }
        if(i == 4 && obj[50] != null ) {
            if( ((BigDecimal)obj[50]).intValue() == 2 ) {
                return TextResource.localize("Enum_PrePostInitialAtr_NO_CHOISE");
            }
            if( ((BigDecimal)obj[50]).intValue() == 1) {
                return TextResource.localize("Enum_PrePostInitialAtr_POST");
            }
            return TextResource.localize("Enum_PrePostInitialAtr_PRE");
        }
        if(i == 5 && obj[34] != null ) {
            return ((BigDecimal)obj[34]).intValue() == 1 ? "○" : "-";
        }
        return "";
    }
    private Object[] getDataA8Center(List<Object[]> obj, HolidayAppType holidayType){
        Optional<Object[]> temp = obj.stream().filter(item -> item[39] != null ? holidayType.value == ((BigDecimal) item[39]).intValue() : holidayType.value == -1).findFirst();
        return temp.orElse(null);
    }

    private String getValueA8Center(int line, Object obj[]){
        if(line == 0) {
            return ((BigDecimal)obj[40]).intValue() == 1 ? "○" : "-";
        }
        if(line == 1) {
            return ((BigDecimal)obj[41]).intValue() == 1 ? "○" : "-";
        }
        return "";
    }



    private String getValueEnum(int i, Object[] obj) {
        if(i == 0 && obj[18] != null) {
            // 表示する
            //表示する
            return ((BigDecimal)obj[18]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
        }
        if(i == 1 && obj[48] != null) {
            return ((BigDecimal)obj[48]).intValue() == 1 ? TextResource.localize("KAF022_75") : TextResource.localize("KAF022_82");
        }

        return "";
    }

    private String getValueA7Bottom(int i, Object[] obj){
        if(i == 0 && obj[28] != null) {
            return ((BigDecimal)obj[28]).intValue() == 1 ? "○" : "-";
        }
        if(i== 1 && obj[29] != null) {
            return ((BigDecimal)obj[28]).intValue() == 1 ? EnumAdaptor.valueOf(((BigDecimal) obj[29]).intValue(), AppAcceptLimitDay.class).name + TextResource.localize("KAF022_510"): "";
        }
        if(i == 2 && obj[30] != null) {
            return ((BigDecimal)obj[30]).intValue() == 1 ? "○" : "-";
        }
        return "";
    }

    public List<MasterHeaderColumn> getHeaderColumns(Sheet sheet) {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        switch (sheet) {
            case JOB:
                columns.addAll(preparationBeforeApply.getHeaderColumnsJob());
                break;
            case REASON:
                columns.addAll(preparationBeforeApply.getHeaderColumnsReaSon());
                break;
            case OVER_TIME:
                columns.addAll(preparationBeforeApply.getHeaderColumnsOverTime());
                break;
            case HOLIDAY_APP:
                columns.addAll(preparationBeforeApply.getHeaderColumnsCommon());
                break;
            case WORK_CHANGE:
                columns.addAll(preparationBeforeApply.getHeaderColumnsCommon());
                break;
            case COMMON_APP:
                columns.addAll(preparationBeforeApply.getHeaderColumnsCommon());
                break;
            case OVER_TIME2:
                columns.addAll(preparationBeforeApply.getHeaderColumnsOverTime());
                break;
            case PAYOUT_APP:
                columns.addAll(preparationBeforeApply.getHeaderColumnsCommon());
                break;
            case APPROVAL_LIST:
                columns.addAll(preparationBeforeApply.getHeaderColumnsCommon());
                break;
            case REFLECT_APP:
                columns.addAll(preparationBeforeApply.getHeaderColumnsReflectApp());
                break;
            case EMP_APPROVE:
                columns.addAll(employmentApprovalSettingExport.getHeaderColumnsEmpApprove());
                break;
            case APPROVAL_CONFIG:
                columns.addAll(approvalFunctionConfigExport.getHeaderColumnsApproveConfig());
                break;
        }
        return columns;
    }

    private List<MasterData> getData(Sheet sheet, List<Object[]> extraData) {
        String companyId = AppContexts.user().companyId();
        String baseDate = "9999-12-31";
        List <MasterData> datas = new ArrayList<>();
        switch (sheet){
            case REASON:
                datas.addAll(preparationBeforeApply.getDataReaSon(extraData));
                break;
            case JOB:
                List<Object[]> preparationBefore = preparationBeforeApplyRepository.getJob(companyId, baseDate);
                datas.addAll(preparationBeforeApply.getDataJob(preparationBefore));
                break;
            case OVER_TIME:
                datas.addAll(preparationBeforeApply.getDataOverTime(extraData));
                break;
            case HOLIDAY_APP:
                datas.addAll(preparationBeforeApply.getDataHoliDayApp(extraData));
                break;
            case WORK_CHANGE:
                datas.addAll(preparationBeforeApply.getDataWorkChange(extraData));
                break;
            case COMMON_APP:
                datas.addAll(preparationBeforeApply.getDataCommon(extraData));
                break;
            case OVER_TIME2:
                datas.addAll(preparationBeforeApply.getDataOverTime2(extraData));
                break;
            case PAYOUT_APP:
                datas.addAll(preparationBeforeApply.getDataPayout(extraData));
                break;
            case APPROVAL_LIST:
                datas.addAll(preparationBeforeApply.getDataApprove(extraData));
                break;
            case REFLECT_APP:
                datas.addAll(preparationBeforeApply.getDataReflectApp(extraData));
                break;
            case EMP_APPROVE:
                List<Object[]> empApproval = approvalRepository.getAllEmploymentApprovalSetting(companyId);
                datas.addAll(empApproval.stream().map(e -> employmentApprovalSettingExport.getDataEmploymentApprovalSetting(e)).collect(Collectors.toList()));
                break;
            case APPROVAL_CONFIG:
                List<Object[]> approvalCongif = approvalRepository.getAllApprovalFunctionConfig(companyId, baseDate);
                datas.addAll(approvalCongif.stream().map(i -> approvalFunctionConfigExport.getDataApprovalConfig(i)).collect(Collectors.toList()));
                break;
        }
        return datas;
    }



    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String companyId = AppContexts.user().companyId();
        List<MasterData> datas = new ArrayList<>();
        List<MasterData> datasA4 = new ArrayList<>();
        List<MasterData> datasA5 = new ArrayList<>();
        List<MasterData> datasA6 = new ArrayList<>();
        List<MasterData> datasA7 = new ArrayList<>();
        List<MasterData> datasA7Bottom = new ArrayList<>();
        List<MasterData> datasA8Top = new ArrayList<>();
        List<MasterData> datasA8Center = new ArrayList<>();
        List<MasterData> datasA8Bottom = new ArrayList<>();
        List<MasterData> datasA9 = new ArrayList<>();
        List<MasterData> datasA10 = new ArrayList<>();
        List<MasterData> datasA11 = new ArrayList<>();
        List<MasterData> datasA12 = new ArrayList<>();
        List<MasterData> datasA13 = new ArrayList<>();
        List<Object[]> preparationBefore = preparationBeforeApplyRepository.getChangePerInforDefinitionToExport(companyId);
        preparationBefore.forEach(item->{
            if(item[0] != null) {
                datasA4.addAll(this.putDatasA4(item));
                if (item[6] != null && ((Long)item[0]).intValue() == 1 ){
                    datasA5.addAll(this.putDatasA5(item));
                }
            }
            if(item[21] != null && item[27] != null && ((BigDecimal)item[27]).intValue() == 0) {
                datasA7.addAll(this.putDatasA7(item));
            }
            if(item[21] != null && item[27] != null && ((BigDecimal)item[27]).intValue() != 0) {
                datasA7Bottom.addAll(this.putDatasA7Bottom(item));
            }
            if(item[27] != null) {
                if (((BigDecimal) item[27]).intValue() == 1 || ((BigDecimal) item[27]).intValue() == 0) {
                    datasA8Top.addAll(this.putDatasA8(item));
                } else {
                    datasA8Bottom.addAll(this.putDatasA8(item));
                }
            }
            if(item[6] != null && ((Long)item[21]).intValue() == 1) {
                datasA9.addAll(this.putDatasA9(item));
                datasA10.addAll(this.putDatasA10(item));
            }
            if(item[12] != null) {
                datasA12.addAll(this.putDatasA12(item));
            }
            if(item[49] != null){
                datasA13.addAll(this.putDatasA13(item));
            }
        });
        datasA8Center.addAll(this.putDatasA8Center(preparationBefore));
        datasA6.addAll(this.putDatasA6(preparationBefore));
        datasA11.addAll(this.putDatasA11(preparationBefore));
        datas.addAll(datasA4);
        datas.addAll(datasA5);
        datas.addAll(datasA6);
        datas.addAll(datasA7);
        datas.addAll(datasA7Bottom);
        datas.addAll(datasA8Top);
        datas.addAll(datasA8Center);
        datas.addAll(datasA8Bottom);
        datas.addAll(datasA9);
        datas.addAll(datasA10);
        datas.addAll(datasA11);
        datas.addAll(datasA12);
        datas.addAll(datasA13);
        return datas;
    }

    @Override
    public List<SheetData> extraSheets(MasterListExportQuery query){
        List<SheetData> sheetData = new ArrayList<>();
        List<Object[]> extraData = preparationBeforeApplyRepository.getExtraData(AppContexts.user().companyId());
        EnumSet.allOf(Sheet.class)
                .forEach( i ->{
                    SheetData extra = new SheetData(this.getData(i,extraData),
                            getHeaderColumns(i), null, null, getSheetName(i), MasterListMode.NONE);
                    sheetData.add(extra);
                });
        return sheetData;
    }

    @Override
    public String mainSheetName() {
        return TextResource.localize("KAF022_453");
    }

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}

    private String getSheetName(Sheet sheet){
        switch (sheet) {
            case JOB: return TextResource.localize("KAF022_515");
            case REASON: return TextResource.localize("KAF022_511");
            case OVER_TIME: return TextResource.localize("KAF022_519");
            case HOLIDAY_APP: return TextResource.localize("KAF022_537");
            case WORK_CHANGE: return TextResource.localize("KAF022_564");
            case COMMON_APP: return TextResource.localize("KAF022_571");
            case OVER_TIME2: return TextResource.localize("KAF022_575");
            case PAYOUT_APP: return TextResource.localize("KAF022_581");
            case APPROVAL_LIST: return TextResource.localize("KAF022_586");
            case REFLECT_APP: return TextResource.localize("KAF022_601");
            case EMP_APPROVE: return TextResource.localize("KAF022_627");
            case APPROVAL_CONFIG: return TextResource.localize("KAF022_634");
        }
        return "";
    }
}
