package nts.uk.file.at.app.export.ot.autocalsetting.com;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.Registration;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.file.at.app.export.ot.autocalsetting.job.JobAutoCalSettingRepository;
import nts.uk.file.at.app.export.ot.autocalsetting.wkp.WkpAutoCalSettingRepository;
import nts.uk.file.at.app.export.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.*;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

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
    private static final String COMPANY = "会社";
    private static final String WORK_PLACE = "職場";
    private static final String JOB = "職位";
    private static final String WORK_JOB = "復号";

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
        Object[] comAutoCalSetting = comAutoCalSettingExport.getCompanySettingToExport(companyId);
        List <MasterData> datas = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        this.putDatas(comAutoCalSetting, data);
        datas.add(new MasterData(data, null, ""));
        return datas;
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
        String baseDate = query.getData().toString();
        List <MasterData> datas = new ArrayList<>();
            switch (autoCalRegis){
                case WORKPLACE:
                    List<Object[]> workPlaceAutoCalSetting = wkpAutoCalSettingRepository.getWorkPlaceSettingToExport(companyId, baseDate);
                    workPlaceAutoCalSetting.forEach(w -> {
                        Map<String, Object> data = new HashMap<>();
                        data.put(KMK006_71, w[23]);
                        data.put(KMK006_72, w[24]);
                        this.putDatas(w, data);
                        datas.add(new MasterData(data, null, ""));
                    });
                    break;
                case JOB:
                    List<Object[]> positionAutoCalSetting = jobAutoCalSettingRepository.getPositionSettingToExport(companyId , baseDate);
                    positionAutoCalSetting.forEach(j -> {
                        Map<String, Object> data = new HashMap<String, Object>();
                        data.put(KMK006_73, j[23]);
                        data.put(KMK006_74, j[24]);
                        this.putDatas(j, data);
                        datas.add(new MasterData(data, null, ""));
                    });
                    break;
                case WORKJOB:
                    List<Object[]> wkpJobAutoCalSetting = wkpJobAutoCalSettingRepository.getWkpJobSettingToExport(companyId, baseDate);
                    wkpJobAutoCalSetting.forEach(wj -> {
                        Map<String, Object> data = new HashMap<>();
                        data.put(KMK006_71, wj[23]);
                        data.put(KMK006_72, wj[24]);
                        data.put(KMK006_73, wj[25]);
                        data.put(KMK006_74, wj[26]);
                        this.putDatas(wj, data);
                        datas.add(new MasterData(data, null, ""));
                    });
                    break;
            }
            return datas;
    }

    @Override
    public List<SheetData> extraSheets(MasterListExportQuery query){
        return EnumSet.allOf(AutoCalRegis.class).stream().map(item -> new SheetData(this.getData(query, item),
                getHeaderColumns(query, item), null, null, getSheetName(item))).collect(Collectors.toList());
    }

    private void putDatas(Object[] export, Map<String, Object> data) {

        data.put(KMK006_48, EnumAdaptor.valueOf(((BigDecimal) export[0]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_49, EnumAdaptor.valueOf(((BigDecimal) export[1]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_50, EnumAdaptor.valueOf(((BigDecimal) export[2]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_51, EnumAdaptor.valueOf(((BigDecimal) export[3]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_52, EnumAdaptor.valueOf(((BigDecimal) export[4]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_53, EnumAdaptor.valueOf(((BigDecimal) export[5]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_54, EnumAdaptor.valueOf(((BigDecimal) export[6]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_55, EnumAdaptor.valueOf(((BigDecimal) export[7]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_56, EnumAdaptor.valueOf(((BigDecimal) export[8]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_57, EnumAdaptor.valueOf(((BigDecimal) export[9]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_58, EnumAdaptor.valueOf(((BigDecimal) export[10]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_59, EnumAdaptor.valueOf(((BigDecimal) export[11]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_60, EnumAdaptor.valueOf(((BigDecimal) export[12]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_61, EnumAdaptor.valueOf(((BigDecimal) export[13]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_62, EnumAdaptor.valueOf(((BigDecimal) export[14]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_63, EnumAdaptor.valueOf(((BigDecimal) export[15]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_64, EnumAdaptor.valueOf(((BigDecimal) export[16]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_65, EnumAdaptor.valueOf(((BigDecimal) export[17]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_66, TextResource.localize(this.checkUse(((BigDecimal) export[18]).intValue())));
        data.put(KMK006_67, TextResource.localize(this.checkUse(((BigDecimal) export[19]).intValue())));
        data.put(KMK006_68, TextResource.localize(this.checkUse(((BigDecimal) export[20]).intValue())));
        data.put(KMK006_69, TextResource.localize(this.checkUse(((BigDecimal) export[21]).intValue())));
        data.put(KMK006_70, TextResource.localize(this.checkUse(((BigDecimal) export[22]).intValue())));
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
