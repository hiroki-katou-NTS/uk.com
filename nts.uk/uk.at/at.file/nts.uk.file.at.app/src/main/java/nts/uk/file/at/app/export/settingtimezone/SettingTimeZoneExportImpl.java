package nts.uk.file.at.app.export.settingtimezone;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.*;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


@DomainID(value = "SettingTimeZone")
@Stateless
public class SettingTimeZoneExportImpl implements MasterListData {


    @Inject
    private SettingTimeZoneRepository settingTimeZoneRepository;


    @Override
    public List<SheetData> extraSheets(MasterListExportQuery query) {
        List<SheetData> sheetDatas = new ArrayList<>();
        SheetData sheetData2 = SheetData.builder()
                .mainData(this.getMasterDatasSheet2())
                .mainDataColumns(this.getHeaderColumnsSheet2())
                .sheetName(TextResource.localize("KMK005_128"))
                .build();
        SheetData sheetData3 = SheetData.builder()
                .mainData(this.getMasterDatasSheet3())
                .mainDataColumns(this.getHeaderColumnsSheet3())
                .sheetName(TextResource.localize("KMK005_129"))
                .build();

        SheetData sheetData4 = SheetData.builder()
                .mainData(this.getMasterDatasSheet4())
                .mainDataColumns(this.getHeaderColumnsSheet4())
                .sheetName(TextResource.localize("KMK005_130"))
                .build();

        SheetData sheetData5 = SheetData.builder()
                .mainData(this.getMasterDatasSheet5())
                .mainDataColumns(this.getHeaderColumnsSheet5())
                .sheetName(TextResource.localize("KMK005_131"))
                .build();

        SheetData sheetData6 = SheetData.builder()
                .mainData(this.getMasterDatasSheet6())
                .mainDataColumns(this.getHeaderColumnsSheet6())
                .sheetName(TextResource.localize("KMK005_132"))
                .build();

        SheetData sheetData7 = SheetData.builder()
                .mainData(this.getMasterDatasSheet7())
                .mainDataColumns(this.getHeaderColumnsSheet7())
                .sheetName(TextResource.localize("KMK005_133"))
                .build();

        sheetDatas.add(sheetData2);
        sheetDatas.add(sheetData3);
        sheetDatas.add(sheetData4);
        sheetDatas.add(sheetData5);
        sheetDatas.add(sheetData6);
        sheetDatas.add(sheetData7);
        return sheetDatas;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String companyId = AppContexts.user().companyId();
        return settingTimeZoneRepository.getListSpecialBonusPayTimeItem(companyId);
    }

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_135, TextResource.localize("KMK005_135"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_96, TextResource.localize("KMK005_96"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_97, TextResource.localize("KMK005_97"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_98, TextResource.localize("KMK005_98"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_99, TextResource.localize("KMK005_98"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    @Override
    public String mainSheetName() {
        return TextResource.localize("KMK005_127");
    }

    private List<MasterHeaderColumn> getHeaderColumnsSheet2() {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_97, TextResource.localize("KMK005_97"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_100, TextResource.localize("KMK005_100"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_101, TextResource.localize("KMK005_101"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_102, TextResource.localize("KMK005_102"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_99, TextResource.localize("KMK005_99"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_103, TextResource.localize("KMK005_103"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_104, TextResource.localize("KMK005_104"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_105, TextResource.localize("KMK005_105"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    private List<MasterData> getMasterDatasSheet2() {
        String companyId = AppContexts.user().companyId();
        return settingTimeZoneRepository.getAutoCalSetting(companyId);
    }

    private List<MasterHeaderColumn> getHeaderColumnsSheet3() {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_99, TextResource.localize("KMK005_97"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_100, TextResource.localize("KMK005_100"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_101, TextResource.localize("KMK005_101"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_102, TextResource.localize("KMK005_102"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_103, TextResource.localize("KMK005_99"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_104, TextResource.localize("KMK005_103"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_105, TextResource.localize("KMK005_104"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_106, TextResource.localize("KMK005_105"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_107, TextResource.localize("KMK005_97"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_110, TextResource.localize("KMK005_100"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_111, TextResource.localize("KMK005_101"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_112, TextResource.localize("KMK005_102"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_113, TextResource.localize("KMK005_99"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_114, TextResource.localize("KMK005_103"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_115, TextResource.localize("KMK005_104"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_116, TextResource.localize("KMK005_105"),
                ColumnTextAlign.LEFT, "", true));
        return columns;

    }

    private List<MasterData> getMasterDatasSheet3() {
        String companyId = AppContexts.user().companyId();
        return settingTimeZoneRepository.getDetailSettingTimeZone(companyId);
    }

    public List<MasterHeaderColumn> getHeaderColumnsSheet4() {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_121, TextResource.localize("KMK005_121"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_122, TextResource.localize("KMK005_122"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_106, TextResource.localize("KMK005_106"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_107, TextResource.localize("KMK005_107"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    private List<MasterData> getMasterDatasSheet4() {
        String companyId = AppContexts.user().companyId();
        return settingTimeZoneRepository.getInfoSetSubUseWorkPlace(companyId);
    }

    public List<MasterHeaderColumn> getHeaderColumnsSheet5() {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_123, TextResource.localize("KMK005_123"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_124, TextResource.localize("KMK005_124"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_106, TextResource.localize("KMK005_106"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_107, TextResource.localize("KMK005_107"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    private List<MasterData> getMasterDatasSheet5() {
        String companyId = AppContexts.user().companyId();
        return settingTimeZoneRepository.getInfoSetEmployees(companyId);
    }

    public List<MasterHeaderColumn> getHeaderColumnsSheet6() {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_125, TextResource.localize("KMK005_125"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_126, TextResource.localize("KMK005_126"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_106, TextResource.localize("KMK005_106"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_107, TextResource.localize("KMK005_107"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    private List<MasterData> getMasterDatasSheet6() {
        String companyId = AppContexts.user().companyId();
        return settingTimeZoneRepository.getInfoSetUsedWorkingHours(companyId);
    }

    public List<MasterHeaderColumn> getHeaderColumnsSheet7() {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_106, TextResource.localize("KMK005_106"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_107, TextResource.localize("KMK005_107"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    private List<MasterData> getMasterDatasSheet7() {
        String companyId = AppContexts.user().companyId();
        return settingTimeZoneRepository.getInfoSetUsedWorkingHours(companyId);
    }

}
