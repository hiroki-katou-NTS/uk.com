package nts.uk.ctx.at.shared.app.export;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.daily.dailyperformance.classification.DoWork;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingExport;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingRepository;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Stateless
public class AutoCalSettingExport {

    @Inject
    private ComAutoCalSettingRepository comAutoCalSettingExport;

    private static final String KMK006_53 = "運用方法";
    private static final String KMK006_54 = "詳細設定";
    private static final String KMK006_55 = "運用方法";
    private static final String KMK006_56 = "詳細設定";
    private static final String KMK006_57 = "運用方法";
    private static final String KMK006_58 = "詳細設定";
    private static final String KMK006_59 = "運用方法";
    private static final String KMK006_60 = "詳細設定";
    private static final String KMK006_61 = "運用方法";
    private static final String KMK006_62 = "詳細設定";
    private static final String KMK006_63 = "運用方法";
    private static final String KMK006_64 = "詳細設定";
    private static final String KMK006_65 = "運用方法";
    private static final String KMK006_66 = "詳細設定";
    private static final String KMK006_67 = "運用方法";
    private static final String KMK006_68 = "詳細設定";
    private static final String KMK006_69 = "運用方法";
    private static final String KMK006_70 = "詳細設定";
    private static final String KMK006_71 = "遅刻";
    private static final String KMK006_72 = "早退";
    private static final String KMK006_73 = "加給";
    private static final String KMK006_74 = "特定加給";
    private static final String KMK006_75 = "乖離時間";

    public List<MasterHeaderColumn> getHeaderColumns() {
        List<MasterHeaderColumn> columns = new ArrayList<>();
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
        return columns;
    }

    public void putDatas(ComAutoCalSettingExport export, Map<String, Object> data) {

        data.put(KMK006_53, EnumAdaptor.valueOf(export.getLegarOtTimeAtr(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_54, EnumAdaptor.valueOf(export.getLegarOtTimeLimit(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_55, EnumAdaptor.valueOf(export.getLegarMidOtTimeAtr(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_56, EnumAdaptor.valueOf(export.getLegarMidOtTimeLimit(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_57, EnumAdaptor.valueOf(export.getNormalOtTimeAtr(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_58, EnumAdaptor.valueOf(export.getNormalOtTimeLimit(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_59, EnumAdaptor.valueOf(export.getNormalMidOtTimeAtr(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_60, EnumAdaptor.valueOf(export.getNormalMidOtTimeLimit(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_61, EnumAdaptor.valueOf(export.getEarlyOtTimeAtr(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_62, EnumAdaptor.valueOf(export.getEarlyOtTimeLimit(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_63, EnumAdaptor.valueOf(export.getEarlyMidOtTimeAtr(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_64, EnumAdaptor.valueOf(export.getEarlyMidOtTimeLimit(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_65, EnumAdaptor.valueOf(export.getFlexOtTimeAtr(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_66, EnumAdaptor.valueOf(export.getFlexOtTimeLimit(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_67, EnumAdaptor.valueOf(export.getRestTimeAtr(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_68, EnumAdaptor.valueOf(export.getRestTimeLimit(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_69, EnumAdaptor.valueOf(export.getLateNightTimeAtr(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_70, EnumAdaptor.valueOf(export.getLateNightTimeLimit(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_71, EnumAdaptor.valueOf(export.getLeaveLate(), DoWork.class).description);
        data.put(KMK006_72, EnumAdaptor.valueOf(export.getLeaveEarly(), DoWork.class).description);
        data.put(KMK006_73, EnumAdaptor.valueOf(export.getRaiSingCalcAtr(), DoWork.class).description);
        data.put(KMK006_74, EnumAdaptor.valueOf(export.getSpecificRaisingCalcAtr(), DoWork.class).description);
        data.put(KMK006_75, EnumAdaptor.valueOf(export.getDivergence(), DoWork.class).description);
    }

    public void putNoDatas(Map<String, Object> data) {
        data.put(KMK006_53, "");
        data.put(KMK006_54, "");
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
    }
}
