package nts.uk.file.at.app.export.worktype;

import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.*;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class PreparationBeforeApplyExport {

    private static final String KAF022_454 = "項目";
    private static final String KAF022_455 = "値";
    private static final String KAF022_512 = "設定対象";
    private static final String KAF022_513 = "既定の理由に設定する";
    private static final String KAF022_514 = "定型理由";
    private static final String KAF022_516 = "コード";
    private static final String KAF022_517 = "職場名";
    private static final String KAF022_518 = "上位職場にサーチ設定";
    private static final String NO_REGIS = "マスタ未登録";
    private static final String COLUMN_NO_HEADER_1 = "COLUMN_NO_HEADER_1";
    private static final String COLUMN_NO_HEADER_2 = "COLUMN_NO_HEADER_2";
    private static final String COLUMN_NO_HEADER_3 = "COLUMN_NO_HEADER_3";
    private static final int SIZE_OVER_TIME = 14;
    private static final int SIZE_OVER_TIME2 = 17;
    private static final int SIZE_HOLIDAY_APP = 24;
    private static final int SIZE_WORK_APP = 10;
    private static final int SIZE_COMMON_APP = 11;
    private static final int SIZE_PAYOUT_APP = 13;
    private static final int SIZE_APPROVE = 9;
    private static final int SIZE_REFLECT_APP = 17;

    public List<MasterHeaderColumn> getHeaderColumnsReaSon() {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KAF022_512, TextResource.localize("KAF022_512"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_513, TextResource.localize("KAF022_513"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_514, TextResource.localize("KAF022_514"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    public List<MasterHeaderColumn> getHeaderColumnsJob(){
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KAF022_516, TextResource.localize("KAF022_516"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_517, TextResource.localize("KAF022_517"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_518, TextResource.localize("KAF022_518"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    public List<MasterHeaderColumn> getHeaderColumnsOverTime(){
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KAF022_454, TextResource.localize("KAF022_454"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(COLUMN_NO_HEADER_1, "",
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_455, TextResource.localize("KAF022_455"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    public List<MasterHeaderColumn> getHeaderColumnsReflectApp(){
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KAF022_454, TextResource.localize("KAF022_454"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(COLUMN_NO_HEADER_1, "",
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(COLUMN_NO_HEADER_2, "",
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(COLUMN_NO_HEADER_3, "",
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_455, TextResource.localize("KAF022_455"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    public List<MasterHeaderColumn> getHeaderColumnsCommon(){
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KAF022_454, TextResource.localize("KAF022_454"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(COLUMN_NO_HEADER_1, "",
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(COLUMN_NO_HEADER_2, "",
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_455, TextResource.localize("KAF022_455"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    public List<MasterData> getDataJob(List<Object[]> obj) {
        List<MasterData> datas = new ArrayList<>();
        obj.forEach(j -> {
            if(j[0] != null) {
                Map<String, MasterCellData> data = new HashMap<>();
                data.put(KAF022_516, MasterCellData.builder()
                        .columnId(KAF022_516)
                        .value(j[0])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());
                data.put(KAF022_517, MasterCellData.builder()
                        .columnId(KAF022_517)
                        .value(j[1] == null ? NO_REGIS : j[1])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());
                data.put(KAF022_518, MasterCellData.builder()
                        .columnId(KAF022_518)
                        .value(EnumAdaptor.valueOf(((BigDecimal)j[2]).intValue(), NotUseAtr.class).nameId)
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());
                datas.add(MasterData.builder().rowData(data).build());
            }
        });
        return datas;
    }

    public List<MasterData> getDataReaSon(List<Object[]> obj) {
        List<MasterData> datas = new ArrayList<>();
        obj.forEach(r -> {
            Map<String, MasterCellData> data = new HashMap<>();
            if(r[14] != null) {
                data.put(KAF022_512, MasterCellData.builder()
                        .columnId(KAF022_512)
                        .value(EnumAdaptor.valueOf(((BigDecimal) r[14]).intValue(), ApplicationType.class).nameId)
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());
                data.put(KAF022_513, MasterCellData.builder()
                        .columnId(KAF022_513)
                        .value(((BigDecimal) r[16]).intValue() == 1 ? "○" : "-")
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());
                data.put(KAF022_514, MasterCellData.builder()
                        .columnId(KAF022_514)
                        .value(r[15])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                        .build());
                datas.add(MasterData.builder().rowData(data).build());
            }
        });
        return datas;
    }

    public List<MasterData> getDataOverTime(List<Object[]> obj) {
        List<MasterData> datas = new ArrayList<>();
        obj.forEach(ot -> {
            for(int i = 0; i< SIZE_OVER_TIME; i++) {
                if(ot[0] != null) {
                    Map<String, MasterCellData> data = new HashMap<>();
                    data.put(KAF022_454, MasterCellData.builder()
                            .columnId(KAF022_454)
                            .value(this.getTextOverTime(i, 0))
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                            .build());
                    data.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                            .columnId(COLUMN_NO_HEADER_1)
                            .value(this.getTextOverTime(i, 1))
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                            .build());
                    data.put(KAF022_455, MasterCellData.builder()
                            .columnId(KAF022_455)
                            .value(this.getValueOT(i, ot))
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                            .build());
                    datas.add(MasterData.builder().rowData(data).build());
                }
            }
        });
        return datas;
    }

    public List<MasterData> getDataHoliDayApp(List<Object[]> obj) {
        List<MasterData> datas = new ArrayList<>();
        for (int i = 0; i < SIZE_HOLIDAY_APP; i++) {
            Map<String, MasterCellData> data = new HashMap<>();
            data.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(this.getTextHolidayApp(i, 0))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_1)
                    .value(this.getTextHolidayApp(i, 1))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_2)
                    .value(this.getTextHolidayApp(i, 2))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(this.getValueHolidayApp(i, obj.get(0)))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datas.add(MasterData.builder().rowData(data).build());
        }
        return datas;
    }

    public List<MasterData> getDataWorkChange(List<Object[]> obj) {
        List<MasterData> datas = new ArrayList<>();
        for (int i = 0; i < SIZE_WORK_APP; i++) {
            Map<String, MasterCellData> data = new HashMap<>();
            data.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(this.getTextWorkChange(i, 0))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_1)
                    .value(this.getTextWorkChange(i, 1))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_2)
                    .value(this.getTextWorkChange(i, 2))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(this.getValueWorkChange(i, obj.get(0)))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT).backgroundColor(getColorWorkChange(i, obj.get(0))))
                    .build());
            datas.add(MasterData.builder().rowData(data).build());
        }
        return datas;
    }

    public List<MasterData> getDataCommon(List<Object[]> obj) {
        List<MasterData> datas = new ArrayList<>();
        for (int i = 0; i < SIZE_COMMON_APP; i++) {
            Map<String, MasterCellData> data = new HashMap<>();
            data.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(this.getTextCommon(i, 0))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_1)
                    .value(this.getTextCommon(i, 1))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_2)
                    .value(this.getTextCommon(i, 2))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(this.getValueCommon(i, obj.get(0)))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .style(MasterCellStyle.build().backgroundColor(getColorCommon(i, obj.get(0))))
                    .build());
            datas.add(MasterData.builder().rowData(data).build());
        }
        return datas;
    }

    private String getValueCommon(int line, Object[] obj){
        switch (line) {
            case 0:
                return ((BigDecimal)obj[52]).intValue() == 1 ? TextResource.localize("KAF022_196")  : TextResource.localize("KAF022_195");
            case 1:
                if(((BigDecimal)obj[53]).intValue() == 3) {
                    return TextResource.localize("KAF022_198");
                }
                if(((BigDecimal)obj[53]).intValue() == 2) {
                    return TextResource.localize("KAF022_199");
                }
                if(((BigDecimal)obj[53]).intValue() == 1) {
                    return TextResource.localize("KAF022_200");
                }
                return TextResource.localize("KAF022_201");
            case 2:
                return obj[54].toString();
            case 3:
                return obj[55].toString();
            case 4:
                return ((BigDecimal)obj[56]).intValue() == 1 ? "○" : "-";
            case 5:
                return obj[57].toString();
            case 6:
                return obj[58].toString();
            case 7:
                return ((BigDecimal)obj[59]).intValue() == 1 ? "○" : "-";
            case 8:
                return ((BigDecimal)obj[60]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 9:
                if(((BigDecimal)obj[61]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_175");
                }
                return ((BigDecimal)obj[61]).intValue() == 1 ? TextResource.localize("KAF022_174") : TextResource.localize("KAF022_173");
            case 10:
                if(((BigDecimal)obj[62]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_175");
                }
                return ((BigDecimal)obj[62]).intValue() == 1 ? TextResource.localize("KAF022_174") : TextResource.localize("KAF022_173");
        }
        return "";
    }

    public List<MasterData> getDataOverTime2(List<Object[]> obj) {
        List<MasterData> datas = new ArrayList<>();
        obj.forEach(ot -> {
            if (ot[64] != null && ((BigDecimal) ot[64]).intValue() == 6) {
                for (int i = 0; i < SIZE_OVER_TIME2; i++) {
                    Map<String, MasterCellData> data = new HashMap<>();
                    data.put(KAF022_454, MasterCellData.builder()
                            .columnId(KAF022_454)
                            .value(this.getTextOverTime2(i, 0))
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                            .build());
                    data.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                            .columnId(COLUMN_NO_HEADER_1)
                            .value(this.getTextOverTime2(i, 1))
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                            .build());
                    data.put(KAF022_455, MasterCellData.builder()
                            .columnId(KAF022_455)
                            .value(this.getValueOT2(i, ot))
                            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                            .build());
                    datas.add(MasterData.builder().rowData(data).build());
                }
            }
        });
        return datas;
    }

    public List<MasterData> getDataReflectApp(List<Object[]> obj) {
        List<MasterData> datas = new ArrayList<>();
        for (int i = 0; i < SIZE_REFLECT_APP; i++) {
            Map<String, MasterCellData> data = new HashMap<>();
            data.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(this.getTextReflectApp(i, 0))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_1)
                    .value(this.getTextReflectApp(i, 1))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_2)
                    .value(this.getTextReflectApp(i, 2))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(COLUMN_NO_HEADER_3, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_3)
                    .value(this.getTextReflectApp(i, 3))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(this.getValueReflectApp(i, obj.get(0)))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datas.add(MasterData.builder().rowData(data).build());
        }
        return datas;
    }

    private String getValueReflectApp(int line, Object[] obj){
        switch (line) {
            case 0:
                return ((BigDecimal)obj[66]).intValue() == 1 ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
            case 1:
                if(((BigDecimal)obj[95]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_408");
                }
                return ((BigDecimal)obj[95]).intValue() == 1 ? TextResource.localize("KAF022_407") : TextResource.localize("KAF022_409");
            case 2:
                if(((BigDecimal)obj[96]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_44");
                }
                return ((BigDecimal)obj[96]).intValue() == 1 ? TextResource.localize("KAF022_43") : TextResource.localize("KAF022_42");
            case 3:
                if(((BigDecimal)obj[99]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_44");
                }
                return ((BigDecimal)obj[99]).intValue() == 1 ? TextResource.localize("KAF022_43") : TextResource.localize("KAF022_42");
            case 4:
                return ((BigDecimal)obj[100]).intValue() == 1 ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
            case 5:
                return ((BigDecimal)obj[106]).intValue() == 1 ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
            case 6:
                return ((BigDecimal)obj[97]).intValue() == 1 ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
            case 7:
                return ((BigDecimal)obj[101]).intValue() == 1 ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
            case 8:
                return ((BigDecimal)obj[90]).intValue() == 1 ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
            case 9:
                return ((BigDecimal)obj[98]).intValue() == 1 ? TextResource.localize("Enum_BreakReflect_REFLEC_SHIFT_BREAK") : TextResource.localize("Enum_BreakReflect_REFLEC_GO_OUT");
            case 10:
                return ((BigDecimal)obj[43]).intValue() == 1 ? TextResource.localize("KAF022_416") : TextResource.localize("KAF022_409");
            case 11:
                return ((BigDecimal)obj[91]).intValue() == 1 ? TextResource.localize("KAF022_414") : TextResource.localize("KAF022_85");
            case 12:
                return ((BigDecimal)obj[92]).intValue() == 1 ? TextResource.localize("KAF022_85") : TextResource.localize("KAF022_84");
            case 13:
                return ((BigDecimal)obj[93]).intValue() == 1 ? TextResource.localize("KAF022_396") : TextResource.localize("KAF022_141");
            case 14:
                return ((BigDecimal)obj[93]).intValue() == 1 ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
            case 15:
                return ((BigDecimal)obj[93]).intValue() == 1 ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
            case 16:
                return ((BigDecimal)obj[93]).intValue() == 1 ? TextResource.localize("KAF022_44") : TextResource.localize("KAF022_396");
        }
        return "";
    }

    private String getTextReflectApp(int line, int column){
        if (column == 0) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_602");
                case 4:
                    return TextResource.localize("KAF022_607");
                case 9:
                    return TextResource.localize("KAF022_616");
                case 13:
                    return TextResource.localize("KAF022_621");
            }
        }
        if (column == 1) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_603");
                case 1:
                    return TextResource.localize("KAF022_604");
                case 2:
                    return TextResource.localize("KAF022_605");
                case 3:
                    return TextResource.localize("KAF022_606");
                case 4:
                    return TextResource.localize("KAF022_608");
                case 6:
                    return TextResource.localize("KAF022_611");
                case 10:
                    return TextResource.localize("KAF022_617");
                case 11:
                    return TextResource.localize("KAF022_618");
                case 12:
                    return TextResource.localize("KAF022_619");
                case 13:
                    return TextResource.localize("KAF022_620");
                case 14:
                    return TextResource.localize("KAF022_622");
                case 15:
                    return TextResource.localize("KAF022_624");
            }
        }
        if (column == 2) {
            switch (line) {
                case 4:
                    return TextResource.localize("KAF022_609");
                case 5:
                    return TextResource.localize("KAF022_610");
                case 6:
                    return TextResource.localize("KAF022_612");
                case 7:
                    return TextResource.localize("KAF022_613");
                case 8:
                    return TextResource.localize("KAF022_614");
                case 14:
                    return TextResource.localize("KAF022_623");
                case 15:
                    return TextResource.localize("KAF022_625");
                case 16:
                    return TextResource.localize("KAF022_626");
            }
        }
        if(column == 3 && line == 9) {
            return TextResource.localize("KAF022_615");
        }
        return "";
    }



    public List<MasterData> getDataApprove(List<Object[]> obj) {
        List<MasterData> datas = new ArrayList<>();
        for (int i = 0; i < SIZE_APPROVE; i++) {
            Map<String, MasterCellData> data = new HashMap<>();
            data.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(this.getTextApproveList(i, 0))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_1)
                    .value(this.getTextApproveList(i, 1))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                    .columnId(COLUMN_NO_HEADER_2)
                    .value(this.getTextApproveList(i, 2))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(this.getValueApproveList(i, obj.get(0)))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            datas.add(MasterData.builder().rowData(data).build());
        }
        return datas;
    }

    private String getValueApproveList(int line, Object[] obj){
        switch (line) {
            case 0:
                return EnumAdaptor.valueOf(((BigDecimal)obj[82]).intValue(), NotUseAtr.class).nameId;
            case 1:
                return ((BigDecimal)obj[83]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 2:
                return obj[84].toString() + TextResource.localize("KAF022_600");
            case 3:
                return ((BigDecimal)obj[85]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 4:
                return ((BigDecimal)obj[86]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 5:
                return ((BigDecimal)obj[87]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 6:
                return ((BigDecimal)obj[88]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 7:
                return ((BigDecimal)obj[89]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 8:
                return ((BigDecimal)obj[90]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
        }
        return "";
    }

    private String getTextApproveList(int line, int column){
        if (column == 0) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_587");
                case 1:
                    return TextResource.localize("KAF022_589");

            }
        }
        if (column == 1) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_588");
                case 1:
                    return TextResource.localize("KAF022_590");
                case 2:
                    return TextResource.localize("KAF022_591");
                case 3:
                    return TextResource.localize("KAF022_591");
                case 6:
                    return TextResource.localize("KAF022_596");
            }
        }
        if (column == 2) {
            switch (line) {
                case 3:
                    return TextResource.localize("KAF022_593");
                case 4:
                    return TextResource.localize("KAF022_594");
                case 5:
                    return TextResource.localize("KAF022_595");
                case 6:
                    return TextResource.localize("KAF022_597");
                case 7:
                    return TextResource.localize("KAF022_598");
                case 8:
                    return TextResource.localize("KAF022_599");
            }
        }
        return "";
    }

    public List<MasterData> getDataPayout(List<Object[]> obj) {
        List<MasterData> datas = new ArrayList<>();
        for (int i = 0; i < SIZE_PAYOUT_APP; i++) {
            Map<String, MasterCellData> data = new HashMap<>();
            data.put(KAF022_454, MasterCellData.builder()
                    .columnId(KAF022_454)
                    .value(this.getTextPayout(i, 0))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(COLUMN_NO_HEADER_1, MasterCellData.builder()
                    .columnId(KAF022_513)
                    .value(this.getTextPayout(i, 1))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(COLUMN_NO_HEADER_2, MasterCellData.builder()
                    .columnId(KAF022_513)
                    .value(this.getTextWorkChange(i, 2))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
            data.put(KAF022_455, MasterCellData.builder()
                    .columnId(KAF022_455)
                    .value(this.getValuePayout(i, obj.get(0)))
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT).backgroundColor(getColorPayout(i, obj.get(0))))
                    .build());
            datas.add(MasterData.builder().rowData(data).build());
        }
        return datas;
    }

    private String getValuePayout(int line, Object[] obj){
        switch (line) {
            case 0:
                if(((BigDecimal)obj[70]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_171");
                }
                return ((BigDecimal)obj[70]).intValue() == 1 ? TextResource.localize("KAF022_100") : TextResource.localize("KAF022_101");
            case 1:
                return ((BigDecimal)obj[71]).intValue() == 1 ? TextResource.localize("KAF022_420") : TextResource.localize("KAF022_421");
            case 2:
                return obj[72].toString();
            case 3:
                return obj[73].toString();
            case 4:
                return ((BigDecimal)obj[74]).intValue() == 1 ? "○" : "-";
            case 5:
                return obj[75].toString();
            case 6:
                return obj[76].toString();
            case 7:
                return ((BigDecimal)obj[77]).intValue() == 1 ? "○" : "-";
            case 8:
                return ((BigDecimal)obj[78]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 9:
                return ((BigDecimal)obj[79]).intValue() == 1 ? TextResource.localize("KAF022_292") : TextResource.localize("KAF022_291");
            case 10:
                if(((BigDecimal)obj[80]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_175");
                }
                return ((BigDecimal)obj[80]).intValue() == 1 ? TextResource.localize("KAF022_174") : TextResource.localize("KAF022_173");
            case 11:
                return ((BigDecimal)obj[67]).intValue() == 1 ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
            case 12:
                return ((BigDecimal)obj[81]).intValue() == 1 ? TextResource.localize("KAF022_272") : TextResource.localize("KAF022_273");
        }
        return "";
    }

    private String getTextPayout(int line, int column){
        if (column == 0) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_520");
                case 9:
                    return TextResource.localize("KAF022_531");
                case 8:
                    return TextResource.localize("KAF022_526");

            }
        }
        if (column == 1) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_538");
                case 1:
                    return TextResource.localize("KAF022_540");
                case 2:
                    return TextResource.localize("KAF022_582");
                case 5:
                    return TextResource.localize("KAF022_583");
                case 8:
                    return TextResource.localize("KAF022_527");
                case 9:
                    return TextResource.localize("KAF022_584");
                case 10:
                    return TextResource.localize("KAF022_536");
                case 11:
                    return TextResource.localize("KAF022_545");
                case 12:
                    return TextResource.localize("KAF022_585");
            }
        }
        if (column == 2) {
            switch (line) {
                case 3:
                    return TextResource.localize("KAF022_568");
                case 4:
                    return TextResource.localize("KAF022_569");
                case 7:
                    return TextResource.localize("KAF022_569");
                case 6:
                    return TextResource.localize("KAF022_568");
            }
        }
        return "";
    }

    private String getColorPayout(int line, Object[] obj){
        switch (line) {
            case 3:
                return obj[46].toString();
            case 6:
                return obj[49].toString();
        }
        return "";
    }

    private String getValueOT2(int line, Object[] obj){
        switch (line) {
            case 0:
                return ((BigDecimal)obj[64]).intValue() == 1 ? TextResource.localize("KAF022_222") : TextResource.localize("KAF022_221");
            case 1:
                if(((BigDecimal)obj[65]).intValue() == 3) {
                    return TextResource.localize("KAF022_198");
                }
                if(((BigDecimal)obj[65]).intValue() == 2) {
                    return TextResource.localize("KAF022_199");
                }
                if(((BigDecimal)obj[65]).intValue() == 1) {
                    return TextResource.localize("KAF022_200");
                }
                return TextResource.localize("KAF022_201");
            case 2:
                return ((BigDecimal)obj[66]).intValue() == 1 ? TextResource.localize("KAF022_392") : TextResource.localize("KAF022_439");
            case 3:
                return ((BigDecimal)obj[2]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 4:
                return ((BigDecimal)obj[3]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 5:
                return ((BigDecimal)obj[4]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 6:
                return ((BigDecimal)obj[5]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 7:
                return ((BigDecimal)obj[6]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 8:
                return ((BigDecimal)obj[6]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 9:
                return ((BigDecimal)obj[6]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 10:
                return ((BigDecimal)obj[68]).intValue() == 1 ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
            case 11:
                return ((BigDecimal)obj[69]).intValue() == 1 ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
            case 12:
                return ((BigDecimal)obj[9]).intValue() == 1 ? TextResource.localize("KAF022_174") : TextResource.localize("KAF022_173");
            case 13:
                if(((BigDecimal)obj[10]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_175");
                }
                return ((BigDecimal)obj[10]).intValue() == 1 ? TextResource.localize("KAF022_174") : TextResource.localize("KAF022_173");
            case 14:
                return ((BigDecimal)obj[11]).intValue() == 0 ? TextResource.localize("KAF022_139") : TextResource.localize("KAF022_140");
            case 15:
                return ((BigDecimal)obj[12]).intValue() == 1 ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
            case 16:
                if(((BigDecimal)obj[13]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_175");
            }
                return ((BigDecimal)obj[13]).intValue() == 1 ? TextResource.localize("KAF022_174") : TextResource.localize("KAF022_173");
            case 17:
                return ((BigDecimal)obj[67]).intValue() == 1 ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
        }
        return "";
    }

    private String getTextOverTime2(int line, int column){
        if (column == 0) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_520");
                case 6:
                    return TextResource.localize("KAF022_526");
                case 10:
                    return TextResource.localize("KAF022_531");
            }
        }
        if (column == 1) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_576");
                case 1:
                    return TextResource.localize("KAF022_577");
                case 2:
                    return TextResource.localize("KAF022_578");
                case 3:
                    return TextResource.localize("KAF022_523");
                case 4:
                    return TextResource.localize("KAF022_524");
                case 5:
                    return TextResource.localize("KAF022_525");
                case 6:
                    return TextResource.localize("KAF022_527");
                case 7:
                    return TextResource.localize("KAF022_528");
                case 8:
                    return TextResource.localize("KAF022_529");
                case 9:
                    return TextResource.localize("KAF022_530");
                case 10:
                    return TextResource.localize("KAF022_531");
                case 11:
                    return TextResource.localize("KAF022_579");
                case 12:
                    return TextResource.localize("KAF022_580");
                case 13:
                    return TextResource.localize("KAF022_532");
                case 14:
                    return TextResource.localize("KAF022_533");
                case 15:
                    return TextResource.localize("KAF022_534");
                case 16:
                    return TextResource.localize("KAF022_536");
                case 17:
                    return TextResource.localize("KAF022_545");
            }
        }
        return "";
    }

    private String getTextCommon(int line, int column){
        if (column == 0) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_520");
                case 8:
                    return TextResource.localize("KAF022_526");
                case 9:
                    return TextResource.localize("KAF022_531");
            }
        }
        if (column == 1) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_572");
                case 1:
                    return TextResource.localize("KAF022_573");
                case 2:
                    return TextResource.localize("KAF022_567");
                case 5:
                    return TextResource.localize("KAF022_570");
                case 8:
                    return TextResource.localize("KAF022_569");
                case 9:
                    return TextResource.localize("KAF022_570");
                case 10:
                    return TextResource.localize("KAF022_568");
            }
        }

        if (column == 2) {
            switch (line) {
                case 3:
                    return TextResource.localize("KAF022_568");
                case 4:
                    return TextResource.localize("KAF022_569");
                case 6:
                    return TextResource.localize("KAF022_568");
                case 7:
                    return TextResource.localize("KAF022_569");
            }
        }
        return "";
    }

    private String getTextWorkChange(int line, int column){
        if (column == 0) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_520");
                case 9:
                    return TextResource.localize("KAF022_526");
            }
        }
        if (column == 1) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_565");
                case 1:
                    return TextResource.localize("KAF022_540");
                case 2:
                    return TextResource.localize("KAF022_566");
                case 3:
                    return TextResource.localize("KAF022_567");
                case 6:
                    return TextResource.localize("KAF022_570");
                case 9:
                    return TextResource.localize("KAF022_527");
            }
        }
        if (column == 2) {
            switch (line) {
                case 4:
                    return TextResource.localize("KAF022_568");
                case 5:
                    return TextResource.localize("KAF022_569");
                case 7:
                    return TextResource.localize("KAF022_568");
                case 8:
                    return TextResource.localize("KAF022_569");
            }
        }
        return "";
    }

    private String getValueWorkChange(int line, Object[] obj){
        switch (line) {
            case 0:
                return ((BigDecimal)obj[42]).intValue() == 1 ? TextResource.localize("KAF022_392") : TextResource.localize("KAF022_391");
            case 1:
                return ((BigDecimal)obj[43]).intValue() == 1 ? TextResource.localize("KAF022_420") : TextResource.localize("KAF022_421");
            case 2:
                return ((BigDecimal)obj[44]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 3:
                return obj[45].toString();
            case 4:
                return obj[46].toString();
            case 5:
                return ((BigDecimal)obj[47]).intValue() == 1 ? "○" : "-";
            case 6:
                return obj[48].toString();
            case 7:
                return obj[49].toString();
            case 8:
                return ((BigDecimal)obj[50]).intValue() == 1 ? "○" : "-";
            case 9:
                return ((BigDecimal)obj[51]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
        }
        return "";
    }

    private String getColorCommon(int line, Object[] obj){
        switch (line) {
            case 3:
                return obj[55].toString();
            case 6:
                return obj[58].toString();
        }
        return "";
    }

    private String getColorWorkChange(int line, Object[] obj){
        switch (line) {
            case 4:
                return obj[46].toString();
            case 7:
                return obj[49].toString();
        }
        return "";
    }

    private String getTextHolidayApp(int line, int column){
        if (column == 0) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_520");
                case 6:
                    return TextResource.localize("KAF022_526");
                case 7:
                    return TextResource.localize("KAF022_531");
                case 17:
                    return TextResource.localize("KAF022_555");
            }
        }
        if (column == 1) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_538");
                case 1:
                    return TextResource.localize("KAF022_539");
                case 2:
                    return TextResource.localize("KAF022_540");
                case 3:
                    return TextResource.localize("KAF022_541");
                case 4:
                    return TextResource.localize("KAF022_542");
                case 5:
                    return TextResource.localize("KAF022_543");
                case 6:
                    return TextResource.localize("KAF022_527");
                case 7:
                    return TextResource.localize("KAF022_544");
                case 8:
                    return TextResource.localize("KAF022_545");
                case 9:
                    return TextResource.localize("KAF022_546");
                case 10:
                    return TextResource.localize("KAF022_547");
                case 11:
                    return TextResource.localize("KAF022_548");
                case 16:
                    return TextResource.localize("KAF022_554");
                case 17:
                    return TextResource.localize("KAF022_556");
                case 18:
                    return TextResource.localize("KAF022_557");
                case 19:
                    return TextResource.localize("KAF022_558");
                case 20:
                    return TextResource.localize("KAF022_559");
                case 21:
                    return TextResource.localize("KAF022_560");
                case 22:
                    return TextResource.localize("KAF022_561");
                case 23:
                    return TextResource.localize("KAF022_562");
                case 24:
                    return TextResource.localize("KAF022_563");
            }
        }

        if (column == 2) {
            switch (line) {
                case 11:
                    return TextResource.localize("KAF022_549");
                case 12:
                    return TextResource.localize("KAF022_550");
                case 13:
                    return TextResource.localize("KAF022_551");
                case 14:
                    return TextResource.localize("KAF022_552");
                case 15:
                    return TextResource.localize("KAF022_553");
            }
        }
        return "";
    }

    private String getValueHolidayApp(int line, Object[] obj){
        switch (line) {
            case 0:
                if(((BigDecimal)obj[17]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_171");
                }
                return ((BigDecimal)obj[17]).intValue() == 1 ? TextResource.localize("KAF022_100") : TextResource.localize("KAF022_101");
            case 1:
                return ((BigDecimal)obj[18]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 2:
                return ((BigDecimal)obj[19]).intValue() == 1 ? TextResource.localize("KAF022_420") : TextResource.localize("KAF022_421");
            case 3:
                return ((BigDecimal)obj[20]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 4:
                return ((BigDecimal)obj[21]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 5:
                return ((BigDecimal)obj[22]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 6:
                return ((BigDecimal)obj[23]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 7:
                if(((BigDecimal)obj[24]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_175");
                }
                return ((BigDecimal)obj[24]).intValue() == 1 ? TextResource.localize("KAF022_174") : TextResource.localize("KAF022_173");
            case 8:
                return ((BigDecimal)obj[25]).intValue() == 1 ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
            case 9:
                return ((BigDecimal)obj[26]).intValue() == 1 ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
            case 10:
                return ((BigDecimal)obj[27]).intValue() == 1 ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
            case 11:
                return ((BigDecimal)obj[28]).intValue() == 1 ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
            case 12:
                return ((BigDecimal)obj[29]).intValue() == 1 ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
            case 13:
                return ((BigDecimal)obj[30]).intValue() == 1 ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
            case 14:
                return ((BigDecimal)obj[31]).intValue() == 1 ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
            case 15:
                return ((BigDecimal)obj[32]).intValue() == 1 ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
            case 16:
                if(((BigDecimal)obj[33]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_175");
                }
                return ((BigDecimal)obj[33]).intValue() == 1 ? TextResource.localize("KAF022_174") : TextResource.localize("KAF022_173");
            case 17:
                return obj[34].toString();
            case 18:
                return obj[35].toString();
            case 19:
                return obj[36].toString();
            case 20:
                return obj[37].toString();
            case 21:
                return obj[38].toString();
            case 22:
                return obj[39].toString();
            case 23:
                return obj[40].toString();
            case 24:
                return obj[41].toString();
        }
        return "";
    }

    private String getTextOverTime(int line, int column){
        if (column == 1) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_521");
                case 1:
                    return TextResource.localize("KAF022_522");
                case 2:
                    return TextResource.localize("KAF022_523");
                case 3:
                    return TextResource.localize("KAF022_524");
                case 4:
                    return TextResource.localize("KAF022_525");
                case 5:
                    return TextResource.localize("KAF022_526");
                case 6:
                    return TextResource.localize("KAF022_527");
                case 7:
                    return TextResource.localize("KAF022_528");
                case 8:
                    return TextResource.localize("KAF022_529");
                case 9:
                    return TextResource.localize("KAF022_530");
                case 10:
                    return TextResource.localize("KAF022_531");
                case 11:
                    return TextResource.localize("KAF022_532");
                case 12:
                    return TextResource.localize("KAF022_533");
                case 13:
                    return TextResource.localize("KAF022_534");
            }
        }
        if (column == 0) {
            switch (line) {
                case 0:
                    return TextResource.localize("KAF022_520");
                case 5:
                    return TextResource.localize("KAF022_526");
                case 9:
                    return TextResource.localize("KAF022_531");
            }
        }
        return "";
    }

    private String getValueOT(int line, Object obj[]){
        switch (line) {
            case 0:
                return ((BigDecimal)obj[0]).intValue() == 1 ? TextResource.localize("KAF022_389") : TextResource.localize("KAF022_390");
            case 1:
                if(((BigDecimal)obj[0]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_137");
                }
                return ((BigDecimal)obj[0]).intValue() == 1 ? TextResource.localize("KAF022_136") : TextResource.localize("KAF022_37");
            case 2:
                return ((BigDecimal)obj[2]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 3:
                return ((BigDecimal)obj[3]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 4:
                return ((BigDecimal)obj[4]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 5:
                return ((BigDecimal)obj[5]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 6:
                return ((BigDecimal)obj[6]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 7:
                return ((BigDecimal)obj[7]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 8:
                return ((BigDecimal)obj[8]).intValue() == 1 ? TextResource.localize("KAF022_36") : TextResource.localize("KAF022_37");
            case 9:
                return ((BigDecimal)obj[9]).intValue() == 1 ? TextResource.localize("KAF022_174") : TextResource.localize("KAF022_173");
            case 10:
                if(((BigDecimal)obj[10]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_175");
                }
                return ((BigDecimal)obj[10]).intValue() == 1 ? TextResource.localize("KAF022_174") : TextResource.localize("KAF022_173");
            case 11:
                return ((BigDecimal)obj[13]).intValue() == 0 ? TextResource.localize("KAF022_139") : TextResource.localize("KAF022_140");
            case 12:
                return ((BigDecimal)obj[105]).intValue() == 1 ? TextResource.localize("KAF022_175") : TextResource.localize("KAF022_173");
            case 13:
                if(((BigDecimal)obj[10]).intValue() == 2 ) {
                    return TextResource.localize("KAF022_175");
                }
                return ((BigDecimal)obj[24]).intValue() == 1 ? TextResource.localize("KAF022_174") : TextResource.localize("KAF022_173");
        }
        return "";
    }

}
