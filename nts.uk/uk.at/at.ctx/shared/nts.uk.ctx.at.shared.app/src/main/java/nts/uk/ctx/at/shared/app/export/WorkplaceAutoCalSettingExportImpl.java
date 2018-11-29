package nts.uk.ctx.at.shared.app.export;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.daily.dailyperformance.classification.DoWork;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSettingRepository;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WorkPlaceAutoCalSettingExport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID(value ="WorkplaceAutoCalSetting")
public class WorkplaceAutoCalSettingExportImpl implements MasterListData{

    @Inject
    private WkpAutoCalSettingRepository wkpAutoCalSettingRepository;
    private static final String REGISTERED = "YES";

    private static final String KMK006_53 = "職場コード";
    private static final String KMK006_54 = "職場名";
    private static final String KMK006_55 = "設定済み";
    private static final String KMK006_56 = "運用方法";
    private static final String KMK006_57 = "詳細設定";
    private static final String KMK006_58 = "運用方法";
    private static final String KMK006_59 = "詳細設定";
    private static final String KMK006_60 = "運用方法";
    private static final String KMK006_61 = "詳細設定";
    private static final String KMK006_62 = "運用方法";
    private static final String KMK006_63 = "詳細設定";
    private static final String KMK006_64 = "運用方法";
    private static final String KMK006_65 = "詳細設定";
    private static final String KMK006_66 = "運用方法";
    private static final String KMK006_67 = "詳細設定";
    private static final String KMK006_68 = "運用方法";
    private static final String KMK006_69 = "詳細設定";
    private static final String KMK006_70 = "運用方法";
    private static final String KMK006_71 = "詳細設定";
    private static final String KMK006_72 = "運用方法";
    private static final String KMK006_73 = "詳細設定";
    private static final String KMK006_74 = "遅刻";
    private static final String KMK006_75 = "早退";
    private static final String KMK006_76 = "加給";
    private static final String KMK006_77 = "特定加給";
    private static final String KMK006_78 = "乖離時間";

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0) {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KMK006_53, TextResource.localize("KMK006_53"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_54, TextResource.localize("KMK006_54"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_55, TextResource.localize("KMK006_55"),
                ColumnTextAlign.CENTER, "", true));
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
        columns.add(new MasterHeaderColumn(KMK006_71, TextResource.localize("KMK006_71"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_72, TextResource.localize("KMK006_72"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_73, TextResource.localize("KMK006_73"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_74, TextResource.localize("KMK006_74"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_75, TextResource.localize("KMK006_75"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_76, TextResource.localize("KMK006_76"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_77, TextResource.localize("KMK006_77"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_78, TextResource.localize("KMK006_78"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String languageId = query.getLanguageId();
        String companyId = AppContexts.user().companyId();

        List<WorkPlaceAutoCalSettingExport> workPlaceAutoCalSetting = wkpAutoCalSettingRepository.getWorkPlaceSettingToExport(companyId);
        List <MasterData> datas = new ArrayList<>();
        workPlaceAutoCalSetting.forEach(item -> {
            Map<String, Object> data = new HashMap<>();
                    data.put(KMK006_53, item.getWorkPlaceCode());
                    data.put(KMK006_54, item.getWorkPlaceName());
                    if(REGISTERED.equals(item.getRegistered())) {
                        data.put(KMK006_55, "✓");
                        data.put(KMK006_56, EnumAdaptor.valueOf(item.getLegarOttimeAtr(), AutoCalAtrOvertime.class).description);
                        data.put(KMK006_57, EnumAdaptor.valueOf(item.getLegarOtTimeLimit(), TimeLimitUpperLimitSetting.class).description);
                        data.put(KMK006_58, EnumAdaptor.valueOf(item.getLegarMidOtTimeAtr(), AutoCalAtrOvertime.class).description);
                        data.put(KMK006_59, EnumAdaptor.valueOf(item.getLegarMidOtTimeLimit(), TimeLimitUpperLimitSetting.class).description);
                        data.put(KMK006_60, EnumAdaptor.valueOf(item.getNormalOtTimeAtr(), AutoCalAtrOvertime.class).description);
                        data.put(KMK006_61, EnumAdaptor.valueOf(item.getNormalOtTimeLimit(), TimeLimitUpperLimitSetting.class).description);
                        data.put(KMK006_62, EnumAdaptor.valueOf(item.getNormalMidOtTimeAtr(), AutoCalAtrOvertime.class).description);
                        data.put(KMK006_63, EnumAdaptor.valueOf(item.getNormalMidOtTimeLimit(), TimeLimitUpperLimitSetting.class).description);
                        data.put(KMK006_64, EnumAdaptor.valueOf(item.getEarlyOtTimeAtr(), AutoCalAtrOvertime.class).description);
                        data.put(KMK006_65, EnumAdaptor.valueOf(item.getEarlyOtTimeLimit(), TimeLimitUpperLimitSetting.class).description);
                        data.put(KMK006_66, EnumAdaptor.valueOf(item.getEarlyMidOtTimeAtr(), AutoCalAtrOvertime.class).description);
                        data.put(KMK006_67, EnumAdaptor.valueOf(item.getEarlyMidOtTimeLimit(), TimeLimitUpperLimitSetting.class).description);
                        data.put(KMK006_68, EnumAdaptor.valueOf(item.getFlexOtTimeAtr(), AutoCalAtrOvertime.class).description);
                        data.put(KMK006_69, EnumAdaptor.valueOf(item.getFlexOtTimeLimit(), TimeLimitUpperLimitSetting.class).description);
                        data.put(KMK006_70, EnumAdaptor.valueOf(item.getRestTimeAtr(), AutoCalAtrOvertime.class).description);
                        data.put(KMK006_71, EnumAdaptor.valueOf(item.getRestTimeLimit(), TimeLimitUpperLimitSetting.class).description);
                        data.put(KMK006_72, EnumAdaptor.valueOf(item.getLateNightTimeAtr(), AutoCalAtrOvertime.class).description);
                        data.put(KMK006_73, EnumAdaptor.valueOf(item.getLateNightTimeLimit(), TimeLimitUpperLimitSetting.class).description);
                        data.put(KMK006_74, EnumAdaptor.valueOf(item.getLeaveLate(), DoWork.class).description);
                        data.put(KMK006_75, EnumAdaptor.valueOf(item.getLeaveEarly(), DoWork.class).description);
                        data.put(KMK006_76, EnumAdaptor.valueOf(item.getRaiSingCalcAtr(), DoWork.class).description);
                        data.put(KMK006_77, EnumAdaptor.valueOf(item.getSpecificRaisingCalcAtr(), DoWork.class).description);
                        data.put(KMK006_78, EnumAdaptor.valueOf(item.getDivergence(), DoWork.class).description);
                    } else {
                        data.put(KMK006_55, "");
                        data.put(KMK006_56, "");
                        data.put(KMK006_57, "");
                        data.put(KMK006_58, "");
                        data.put(KMK006_59, "");
                        data.put(KMK006_60, "");
                        data.put(KMK006_61, "");
                        data.put(KMK006_62, "");
                        data.put(KMK006_63, "");
                        data.put(KMK006_64, "");
                        data.put(KMK006_65, "");
                        data.put(KMK006_66, "");
                        data.put(KMK006_67, "");
                        data.put(KMK006_68, "");
                        data.put(KMK006_69, "");
                        data.put(KMK006_70, "");
                        data.put(KMK006_71, "");
                        data.put(KMK006_72, "");
                        data.put(KMK006_73, "");
                        data.put(KMK006_74, "");
                        data.put(KMK006_75, "");
                        data.put(KMK006_76, "");
                        data.put(KMK006_77, "");
                        data.put(KMK006_78, "");
                     }
            datas.add(new MasterData(data, null, ""));
        });
        return datas;
    }
}
