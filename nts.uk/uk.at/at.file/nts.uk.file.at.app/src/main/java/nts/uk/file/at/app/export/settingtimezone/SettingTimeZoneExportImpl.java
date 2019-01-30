package nts.uk.file.at.app.export.settingtimezone;

import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.shared.app.find.bonuspay.BPUnitUseSettingDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.BPUnitUseSettingFinder;
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

    @Inject
    private BPUnitUseSettingFinder bpUnitUseSettingFinder;


    @Override
    public List<SheetData> extraSheets(MasterListExportQuery query) {
        BPUnitUseSettingDto unitUseSettingDto = this.bpUnitUseSettingFinder.getSetting();
        List<SheetData> sheetDatas = new ArrayList<>();
        SheetData sheetData2 = SheetData.builder()
                .mainData(this.getMasterDatasOfAutoCalSetting())
                .mainDataColumns(this.getHeaderColumnsOfAutoCalSetting())
                .sheetName(TextResource.localize("KMK005_128"))
                .build();
        SheetData sheetData3 = SheetData.builder()
                .mainData(this.getMasterDatasOfSettingTimeZone())
                .mainDataColumns(this.getHeaderColumnsOfSettingTimeZone())
                .sheetName(TextResource.localize("KMK005_129"))
                .build();

        SheetData sheetData4 = SheetData.builder()
                .mainData(this.getMasterDatasSetUpUseCompany())
                .mainDataColumns(this.getHeaderColumnsSetUpUseCompany())
                .sheetName(TextResource.localize("KMK005_130"))
                .build();

        sheetDatas.add(sheetData2);
        sheetDatas.add(sheetData3);
        sheetDatas.add(sheetData4);
        if(unitUseSettingDto == null){
            SheetData sheetData5 = SheetData.builder()
                    .mainData(this.getMasterDatasOfSetSubUseWorkPlace())
                    .mainDataColumns(this.getHeaderColumnsOfSetSubUseWorkPlace())
                    .sheetName(TextResource.localize("KMK005_131"))
                    .build();
            sheetDatas.add(sheetData5);

            SheetData sheetData6 = SheetData.builder()
                    .mainData(this.getMasterDatasOfSetEmployees())
                    .mainDataColumns(this.getHeaderColumnsOfSetEmployees())
                    .sheetName(TextResource.localize("KMK005_132"))
                    .build();
            sheetDatas.add(sheetData6);

            SheetData sheetData7 = SheetData.builder()
                    .mainData(this.getMasterDatasOfSetUsedWorkingHours())
                    .mainDataColumns(this.getHeaderColumnsOfSetUsedWorkingHours())
                    .sheetName(TextResource.localize("KMK005_133"))
                    .build();
            sheetDatas.add(sheetData7);
        }else{
            if(unitUseSettingDto.getWorkplaceUseAtr() == UseAtr.USE.value){
                SheetData sheetData5 = SheetData.builder()
                        .mainData(this.getMasterDatasOfSetSubUseWorkPlace())
                        .mainDataColumns(this.getHeaderColumnsOfSetSubUseWorkPlace())
                        .sheetName(TextResource.localize("KMK005_131"))
                        .build();
                sheetDatas.add(sheetData5);
            }

            if(unitUseSettingDto.getPersonalUseAtr() == UseAtr.USE.value){
                SheetData sheetData6 = SheetData.builder()
                        .mainData(this.getMasterDatasOfSetEmployees())
                        .mainDataColumns(this.getHeaderColumnsOfSetEmployees())
                        .sheetName(TextResource.localize("KMK005_132"))
                        .build();
                sheetDatas.add(sheetData6);
            }

            if(unitUseSettingDto.getWorkingTimesheetUseAtr() == UseAtr.USE.value){
                SheetData sheetData7 = SheetData.builder()
                        .mainData(this.getMasterDatasOfSetUsedWorkingHours())
                        .mainDataColumns(this.getHeaderColumnsOfSetUsedWorkingHours())
                        .sheetName(TextResource.localize("KMK005_133"))
                        .build();
                sheetDatas.add(sheetData7);
            }
        }


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
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_99, TextResource.localize("KMK005_99"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    @Override
    public String mainSheetName() {
        return TextResource.localize("KMK005_127");
    }

    private List<MasterHeaderColumn> getHeaderColumnsOfAutoCalSetting() {
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

    private List<MasterData> getMasterDatasOfAutoCalSetting() {
        String companyId = AppContexts.user().companyId();
        return settingTimeZoneRepository.getAutoCalSetting(companyId);
    }

    private List<MasterHeaderColumn> getHeaderColumnsOfSettingTimeZone() {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_106, TextResource.localize("KMK005_106"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_107, TextResource.localize("KMK005_107"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_135, TextResource.localize("KMK005_135"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_108, TextResource.localize("KMK005_108"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_109, TextResource.localize("KMK005_109"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_110, TextResource.localize("KMK005_110"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_111, TextResource.localize("KMK005_111"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_112, TextResource.localize("KMK005_112"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_113, TextResource.localize("KMK005_113"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_114, TextResource.localize("KMK005_114"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_115, TextResource.localize("KMK005_115"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_116, TextResource.localize("KMK005_116"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_117, TextResource.localize("KMK005_117"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_118, TextResource.localize("KMK005_118"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_119, TextResource.localize("KMK005_119"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_120, TextResource.localize("KMK005_120"),
                ColumnTextAlign.LEFT, "", true));
        return columns;

    }

    private List<MasterData> getMasterDatasOfSettingTimeZone() {
        String companyId = AppContexts.user().companyId();
        return settingTimeZoneRepository.getDetailSettingTimeZone(companyId);
    }

    public List<MasterHeaderColumn> getHeaderColumnsOfSetSubUseWorkPlace() {
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

    private List<MasterData> getMasterDatasOfSetSubUseWorkPlace() {
        String companyId = AppContexts.user().companyId();
        return settingTimeZoneRepository.getInfoSetSubUseWorkPlace(companyId);
    }

    public List<MasterHeaderColumn> getHeaderColumnsOfSetEmployees() {
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

    private List<MasterData> getMasterDatasOfSetEmployees() {
        String companyId = AppContexts.user().companyId();
        return settingTimeZoneRepository.getInfoSetEmployees(companyId);
    }

    public List<MasterHeaderColumn> getHeaderColumnsOfSetUsedWorkingHours() {
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

    private List<MasterData> getMasterDatasOfSetUsedWorkingHours() {
        String companyId = AppContexts.user().companyId();
        return settingTimeZoneRepository.getInfoSetUsedWorkingHours(companyId);
    }

    public List<MasterHeaderColumn> getHeaderColumnsSetUpUseCompany() {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_106, TextResource.localize("KMK005_106"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SettingTimeZoneUtils.KMK005_107, TextResource.localize("KMK005_107"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    private List<MasterData> getMasterDatasSetUpUseCompany() {
        String companyId = AppContexts.user().companyId();
        return settingTimeZoneRepository.getInfoSetUpUseCompany(companyId);
    }

}
