package nts.uk.file.at.app.export.ot.autocalsetting;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Stateless
public class AutoCalSettingExport {

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

    public void putDatas(Object[] export, Map<String, Object> data) {

        data.put(KMK006_53, EnumAdaptor.valueOf(((BigDecimal) export[0]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_54, EnumAdaptor.valueOf(((BigDecimal) export[1]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_55, EnumAdaptor.valueOf(((BigDecimal) export[2]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_56, EnumAdaptor.valueOf(((BigDecimal) export[3]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_57, EnumAdaptor.valueOf(((BigDecimal) export[4]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_58, EnumAdaptor.valueOf(((BigDecimal) export[5]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_59, EnumAdaptor.valueOf(((BigDecimal) export[6]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_60, EnumAdaptor.valueOf(((BigDecimal) export[7]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_61, EnumAdaptor.valueOf(((BigDecimal) export[8]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_62, EnumAdaptor.valueOf(((BigDecimal) export[9]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_63, EnumAdaptor.valueOf(((BigDecimal) export[10]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_64, EnumAdaptor.valueOf(((BigDecimal) export[11]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_65, EnumAdaptor.valueOf(((BigDecimal) export[12]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_66, EnumAdaptor.valueOf(((BigDecimal) export[13]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_67, EnumAdaptor.valueOf(((BigDecimal) export[14]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_68, EnumAdaptor.valueOf(((BigDecimal) export[15]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_69, EnumAdaptor.valueOf(((BigDecimal) export[16]).intValue(), AutoCalAtrOvertime.class).description);
        data.put(KMK006_70, EnumAdaptor.valueOf(((BigDecimal) export[17]).intValue(), TimeLimitUpperLimitSetting.class).description);
        data.put(KMK006_71, TextResource.localize(this.checkUse(((BigDecimal) export[18]).intValue())));
        data.put(KMK006_72, TextResource.localize(this.checkUse(((BigDecimal) export[19]).intValue())));
        data.put(KMK006_73, TextResource.localize(this.checkUse(((BigDecimal) export[20]).intValue())));
        data.put(KMK006_74, TextResource.localize(this.checkUse(((BigDecimal) export[21]).intValue())));
        data.put(KMK006_75, TextResource.localize(this.checkUse(((BigDecimal) export[22]).intValue())));
    }

    private String checkUse(Object obj){
        if (((Integer) obj).intValue() == 1) {
            return "KMK006_41";
        } else {
            return "KMK006_42";
        }
    }
}
