package nts.uk.ctx.at.shared.app.export;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID(value ="WorkplaceSettingExportImpl")
public class WorkplaceSettingExportImpl implements MasterListData{

    private static final String KMK006_76 = "職場コード";
    private static final String KMK006_77 = "職場名";
    private static final String KMK006_78 = "設定済み";
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

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0){
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KMK006_76, TextResource.localize("KMK006_76"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_77, TextResource.localize("KMK006_77"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_78, TextResource.localize("KMK006_78"),
        ColumnTextAlign.CENTER, "", true));
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
        return columns;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query){
        String languageId = query.getLanguageId();
        String companyId = AppContexts.user().companyId();
        List <MasterData> datas = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put(KMK006_76, "");
        data.put(KMK006_77, "");
        data.put(KMK006_78, "");
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
        datas.add(new MasterData(data, null, ""));
        return datas;
    }
}
