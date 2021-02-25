package nts.uk.file.at.app.export.ot.autocalsetting.com;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.file.at.app.export.ot.autocalsetting.job.JobAutoCalSettingRepository;
import nts.uk.file.at.app.export.ot.autocalsetting.wkp.WkpAutoCalSettingRepository;
import nts.uk.file.at.app.export.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.*;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@DomainID(value ="AutomaticCalculationSetting")
public class ComAutoCalSettingExportImpl implements MasterListData{

    private static final String KMK006_48 = "法定内残業時間.残業時間.運用方法";
    private static final String KMK006_49 = "法定内残業時間.残業時間.詳細設定";
    private static final String KMK006_50 = "法定内残業時間.深夜時間.運用方法";
    private static final String KMK006_51 = "法定内残業時間.深夜時間.詳細設定";
    private static final String KMK006_52 = "普通残業時間.残業時間.運用方法";
    private static final String KMK006_53 = "普通残業時間.残業時間.詳細設定";
    private static final String KMK006_54 = "普通残業時間.深夜時間.運用方法";
    private static final String KMK006_55 = "普通残業時間.深夜時間.詳細設定";
    private static final String KMK006_56 = "早出残業時間.残業時間.運用方法";
    private static final String KMK006_57 = "早出残業時間.残業時間.詳細設定";
    private static final String KMK006_58 = "早出残業時間.深夜時間.運用方法";
    private static final String KMK006_59 = "早出残業時間.深夜時間.詳細設定";
    private static final String KMK006_60 = "フレックス超過時間.運用方法";
    private static final String KMK006_61 = "フレックス超過時間.詳細設定";
    private static final String KMK006_62 = "休日出勤.休日出勤時間.運用方法";
    private static final String KMK006_63 = "休日出勤.休日出勤時間.詳細設定";
    private static final String KMK006_64 = "休日出勤.深夜時間.運用方法";
    private static final String KMK006_65 = "休日出勤.深夜時間.詳細設定";
    private static final String KMK006_66 = "遅刻早退.遅刻";
    private static final String KMK006_67 = "遅刻早退.早退";
    private static final String KMK006_68 = "加給時間.加給";
    private static final String KMK006_69 = "加給時間.特定加給";
    private static final String KMK006_70 = "乖離時間";
    private static final String KMK006_71 = "職場コード";
    private static final String KMK006_72 = "職場名";
    private static final String KMK006_73 = "設定済み";
    private static final String KMK006_74 = "職位コード";
    private static final String NO_REGIS = "マスタ未登録";
    private static final String SPACE = "";


    @Inject
    private ComAutoCalSettingRepository comAutoCalSettingExport;

    @Inject
    private WkpAutoCalSettingRepository wkpAutoCalSettingRepository;

    @Inject
    private JobAutoCalSettingRepository jobAutoCalSettingRepository;

    @Inject
    private WkpJobAutoCalSettingRepository wkpJobAutoCalSettingRepository;


    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery masterListExportQuery) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KMK006_48, TextResource.localize("KMK006_48"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_49, TextResource.localize("KMK006_49"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_50, TextResource.localize("KMK006_50"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_51, TextResource.localize("KMK006_51"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_52, TextResource.localize("KMK006_52"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_53, TextResource.localize("KMK006_53"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_54, TextResource.localize("KMK006_54"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_55, TextResource.localize("KMK006_55"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_56, TextResource.localize("KMK006_56"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_57, TextResource.localize("KMK006_57"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_58, TextResource.localize("KMK006_58"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_59, TextResource.localize("KMK006_59"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_60, TextResource.localize("KMK006_60"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_61, TextResource.localize("KMK006_61"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_62, TextResource.localize("KMK006_62"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_63, TextResource.localize("KMK006_63"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_64, TextResource.localize("KMK006_64"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_65, TextResource.localize("KMK006_65"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_66, TextResource.localize("KMK006_66"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_67, TextResource.localize("KMK006_67"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_68, TextResource.localize("KMK006_68"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_69, TextResource.localize("KMK006_69"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_70, TextResource.localize("KMK006_70"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query, AutoCalRegis autoCalRegis) {
        List <MasterHeaderColumn> columns = new ArrayList<>();
            switch (autoCalRegis){
                case WORKPLACE:
                    columns.add(new MasterHeaderColumn(KMK006_71, TextResource.localize("KMK006_71"),
                            ColumnTextAlign.LEFT, "", true));
                    columns.add(new MasterHeaderColumn(KMK006_72, TextResource.localize("KMK006_72"),
                            ColumnTextAlign.LEFT, "", true));
                    columns.addAll(this.getHeaderColumns(query));
                    break;
                case JOB:
                    columns.add(new MasterHeaderColumn(KMK006_73, TextResource.localize("KMK006_73"),
                            ColumnTextAlign.LEFT, "", true));
                    columns.add(new MasterHeaderColumn(KMK006_74, TextResource.localize("KMK006_74"),
                            ColumnTextAlign.LEFT, "", true));
                    columns.addAll(this.getHeaderColumns(query));
                    break;
                case WORKJOB:
                    columns.add(new MasterHeaderColumn(KMK006_71, TextResource.localize("KMK006_71"),
                            ColumnTextAlign.LEFT, "", true));
                    columns.add(new MasterHeaderColumn(KMK006_72, TextResource.localize("KMK006_72"),
                            ColumnTextAlign.LEFT, "", true));
                    columns.add(new MasterHeaderColumn(KMK006_73, TextResource.localize("KMK006_73"),
                            ColumnTextAlign.LEFT, "", true));
                    columns.add(new MasterHeaderColumn(KMK006_74, TextResource.localize("KMK006_74"),
                            ColumnTextAlign.LEFT, "", true));
                    columns.addAll(this.getHeaderColumns(query));
                    break;
            }

        return columns;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String companyId = AppContexts.user().companyId();
        List<MasterData> datas = new ArrayList<>();
        Object[] comAutoCalSetting = comAutoCalSettingExport.getCompanySettingToExport(companyId);
        Map<String, MasterCellData> data = new HashMap<>();
        if(comAutoCalSetting != null) {
            this.putDatas(comAutoCalSetting, data);
            datas.add(MasterData.builder().rowData(data).build());
        }
        return datas;
    }

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}

    private String getSheetName(AutoCalRegis autoCalRegis){
        switch (autoCalRegis) {
            case WORKPLACE: return TextResource.localize("KMK006_76");
            case JOB: return TextResource.localize("KMK006_77");
            case WORKJOB: return TextResource.localize("KMK006_78");
        }
        return "";
    }

    private List<MasterData> getData(MasterListExportQuery query, AutoCalRegis autoCalRegis) {
        String companyId = AppContexts.user().companyId();
        String baseDate = "9999-12-31";
        List <MasterData> datas = new ArrayList<>();
            switch (autoCalRegis){
                case WORKPLACE:
                    List<Object[]> workPlaceAutoCalSetting = wkpAutoCalSettingRepository.getWorkPlaceSettingToExport(companyId, baseDate);
                    workPlaceAutoCalSetting.forEach(w -> {
                        Map<String, MasterCellData> data = new HashMap<>();
                        data.put(KMK006_71, MasterCellData.builder()
                                .columnId(KMK006_71)
                                .value(w[23] == null || w[24] == null ? SPACE : w[23])
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());

                        data.put(KMK006_72, MasterCellData.builder()
                                .columnId(KMK006_72)
                                .value(w[24] == null ? NO_REGIS : w[24])
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
                        this.putDatas(w, data);
                        datas.add(MasterData.builder().rowData(data).build());
                    });
                    break;
                case JOB:
                    List<Object[]> positionAutoCalSetting = jobAutoCalSettingRepository.getPositionSettingToExport(companyId , baseDate);
                    positionAutoCalSetting.forEach(j -> {
                        Map<String, MasterCellData> data = new HashMap<String, MasterCellData>();
                        data.put(KMK006_73, MasterCellData.builder()
                                .columnId(KMK006_73)
                                .value(j[23] == null || j[24] == null ? SPACE : j[23])
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
                        data.put(KMK006_74, MasterCellData.builder()
                                .columnId(KMK006_74)
                                .value(j[24] == null ? NO_REGIS : j[24])
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
                        this.putDatas(j, data);
                        datas.add(MasterData.builder().rowData(data).build());
                    });
                    break;
                case WORKJOB:
                    List<Object[]> wkpJobAutoCalSetting = wkpJobAutoCalSettingRepository.getWkpJobSettingToExport(companyId, baseDate);
                    wkpJobAutoCalSetting.forEach(wj -> {
                        Map<String, MasterCellData> data = new HashMap<>();
                        data.put(KMK006_71, MasterCellData.builder()
                                .columnId(KMK006_71)
                                .value(getWorkPlaceCode(wj))
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
                        data.put(KMK006_72, MasterCellData.builder()
                                .columnId(KMK006_72)
                                .value(getWorkPlaceName(wj))
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
                        data.put(KMK006_73, MasterCellData.builder()
                                .columnId(KMK006_73)
                                .value(wj[25] == null || wj[26] == null ? SPACE : wj[25])
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
                        data.put(KMK006_74, MasterCellData.builder()
                                .columnId(KMK006_74)
                                .value(wj[26] == null ? NO_REGIS : wj[26])
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
                        this.putDatas(wj, data);
                        datas.add(MasterData.builder().rowData(data).build());
                    });
                    break;
            }
            return datas;
    }

    private String getWorkPlaceCode(Object[] obj){
        if(obj[23] == null || obj[24] == null) {
            return SPACE;
        }
        if("-".equals(obj[23].toString())) {
            return "";
        }
        return obj[23].toString();
    }
    private String getWorkPlaceName(Object[] obj){
        if(obj[24] == null) {
            return NO_REGIS;
        }
        if("-".equals(obj[24].toString())) {
            return "";
        }
        return obj[24].toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SheetData> extraSheets(MasterListExportQuery query){
        List<SheetData> sheetData = new ArrayList<>();
        if(((Map<String, Boolean>) query.getData()).get("useWkpSet")) {
            SheetData wkp = new SheetData(this.getData(query, AutoCalRegis.WORKPLACE),
                    getHeaderColumns(query, AutoCalRegis.WORKPLACE), null, null, getSheetName(AutoCalRegis.WORKPLACE), MasterListMode.NONE);
            sheetData.add(wkp);
        }
        if(((Map<String, Boolean>) query.getData()).get("useJobSet")) {
            SheetData job = new SheetData(this.getData(query, AutoCalRegis.JOB),
                    getHeaderColumns(query, AutoCalRegis.JOB), null, null, getSheetName(AutoCalRegis.JOB), MasterListMode.NONE);
            sheetData.add(job);
        }
        if(((Map<String, Boolean>) query.getData()).get("useJobwkpSet")) {
            SheetData jobWkp = new SheetData(this.getData(query, AutoCalRegis.WORKJOB),
                    getHeaderColumns(query, AutoCalRegis.WORKJOB), null, null, getSheetName(AutoCalRegis.WORKJOB), MasterListMode.NONE);
            sheetData.add(jobWkp);
        }
        return sheetData;
    }

    private void putDatas(Object[] export, Map<String, MasterCellData> data) {

        data.put(KMK006_48, MasterCellData.builder()
                .columnId(KMK006_48)
                .value(EnumAdaptor.valueOf(((BigDecimal) export[0]).intValue(), AutoCalAtrOvertime.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_49, MasterCellData.builder()
                .columnId(KMK006_49)
                .value(((BigDecimal) export[0]).intValue() == 0 ? "" : EnumAdaptor.valueOf(((BigDecimal) export[1]).intValue(), TimeLimitUpperLimitSetting.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_50, MasterCellData.builder()
                .columnId(KMK006_50)
                .value(EnumAdaptor.valueOf(((BigDecimal) export[2]).intValue(), AutoCalAtrOvertime.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_51, MasterCellData.builder()
                .columnId(KMK006_51)
                .value(((BigDecimal) export[2]).intValue() == 0 ? "" : EnumAdaptor.valueOf(((BigDecimal) export[3]).intValue(), TimeLimitUpperLimitSetting.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_52, MasterCellData.builder()
                .columnId(KMK006_52)
                .value(EnumAdaptor.valueOf(((BigDecimal) export[4]).intValue(), AutoCalAtrOvertime.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_53, MasterCellData.builder()
                .columnId(KMK006_53)
                .value(((BigDecimal) export[4]).intValue() == 0 ? "" : EnumAdaptor.valueOf(((BigDecimal) export[5]).intValue(), TimeLimitUpperLimitSetting.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_54, MasterCellData.builder()
                .columnId(KMK006_54)
                .value(EnumAdaptor.valueOf(((BigDecimal) export[6]).intValue(), AutoCalAtrOvertime.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_55, MasterCellData.builder()
                .columnId(KMK006_55)
                .value(((BigDecimal) export[6]).intValue() == 0 ? "" : EnumAdaptor.valueOf(((BigDecimal) export[7]).intValue(), TimeLimitUpperLimitSetting.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_56, MasterCellData.builder()
                .columnId(KMK006_56)
                .value(EnumAdaptor.valueOf(((BigDecimal) export[8]).intValue(), AutoCalAtrOvertime.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_57, MasterCellData.builder()
                .columnId(KMK006_57)
                .value(((BigDecimal) export[8]).intValue() == 0 ? "" : EnumAdaptor.valueOf(((BigDecimal) export[9]).intValue(), TimeLimitUpperLimitSetting.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_58, MasterCellData.builder()
                .columnId(KMK006_58)
                .value(EnumAdaptor.valueOf(((BigDecimal) export[10]).intValue(), AutoCalAtrOvertime.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_59, MasterCellData.builder()
                .columnId(KMK006_59)
                .value(((BigDecimal) export[10]).intValue() == 0 ? "" : EnumAdaptor.valueOf(((BigDecimal) export[11]).intValue(), TimeLimitUpperLimitSetting.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_60, MasterCellData.builder()
                .columnId(KMK006_60)
                .value(EnumAdaptor.valueOf(((BigDecimal) export[12]).intValue(), AutoCalAtrOvertime.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_61, MasterCellData.builder()
                .columnId(KMK006_61)
                .value(((BigDecimal) export[12]).intValue() == 0 ? "" : EnumAdaptor.valueOf(((BigDecimal) export[13]).intValue(), TimeLimitUpperLimitSetting.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_62, MasterCellData.builder()
                .columnId(KMK006_62)
                .value(EnumAdaptor.valueOf(((BigDecimal) export[14]).intValue(), AutoCalAtrOvertime.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_63, MasterCellData.builder()
                .columnId(KMK006_63)
                .value(((BigDecimal) export[14]).intValue() == 0 ? "" : EnumAdaptor.valueOf(((BigDecimal) export[15]).intValue(), TimeLimitUpperLimitSetting.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_64, MasterCellData.builder()
                .columnId(KMK006_64)
                .value(EnumAdaptor.valueOf(((BigDecimal) export[16]).intValue(), AutoCalAtrOvertime.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_65, MasterCellData.builder()
                .columnId(KMK006_65)
                .value(((BigDecimal) export[16]).intValue() == 0 ? "" : EnumAdaptor.valueOf(((BigDecimal) export[17]).intValue(), TimeLimitUpperLimitSetting.class).description)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_66, MasterCellData.builder()
                .columnId(KMK006_66)
                .value(TextResource.localize(this.checkUse(((BigDecimal) export[18]).intValue())))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_67, MasterCellData.builder()
                .columnId(KMK006_67)
                .value(TextResource.localize(this.checkUse(((BigDecimal) export[19]).intValue())))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_68, MasterCellData.builder()
                .columnId(KMK006_68)
                .value(TextResource.localize(this.checkUse(((BigDecimal) export[20]).intValue())))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_69, MasterCellData.builder()
                .columnId(KMK006_69)
                .value(TextResource.localize(this.checkUse(((BigDecimal) export[21]).intValue())))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(KMK006_70, MasterCellData.builder()
                .columnId(KMK006_70)
                .value(TextResource.localize(this.checkUse(((BigDecimal) export[22]).intValue())))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
    }

    private String checkUse(int obj){
        if (obj == 1) {
            return "KMK006_41";
        } else {
            return "KMK006_42";
        }
    }

    @Override
    public String mainSheetName() {
        return TextResource.localize("KMK006_75");
    }

}
