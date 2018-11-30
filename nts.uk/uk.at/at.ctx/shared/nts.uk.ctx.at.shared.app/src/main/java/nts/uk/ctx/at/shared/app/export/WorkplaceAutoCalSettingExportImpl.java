package nts.uk.ctx.at.shared.app.export;

import java.util.*;
import javax.ejb.Stateless;
import javax.inject.Inject;

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

    @Inject
    private AutoCalSettingExport autoCalSettingExport;

    public static final String REGISTERED = "YES";

    private static final String KMK006_76 = "職場コード";
    private static final String KMK006_77 = "職場名";
    private static final String KMK006_78 = "設定済み";

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0) {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KMK006_76, TextResource.localize("KMK006_76"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_77, TextResource.localize("KMK006_77"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_78, TextResource.localize("KMK006_78"),
                ColumnTextAlign.CENTER, "", true));
        columns.addAll(autoCalSettingExport.getHeaderColumns());
        return columns;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String companyId = AppContexts.user().companyId();

        List<WorkPlaceAutoCalSettingExport> workPlaceAutoCalSetting = wkpAutoCalSettingRepository.getWorkPlaceSettingToExport(companyId);
        List <MasterData> datas = new ArrayList<>();
        workPlaceAutoCalSetting.forEach(item -> {
            Map<String, Object> data = new HashMap<>();
                    data.put(KMK006_76, item.getWorkPlaceCode());
                    data.put(KMK006_77, item.getWorkPlaceName());
                    if(REGISTERED.equals(item.getRegistered())) {
                        data.put(KMK006_78, "✓");
                        autoCalSettingExport.putDatas(item, data);
                    } else {
                        data.put(KMK006_78, "");
                        autoCalSettingExport.putNoDatas(data);

                     }
            datas.add(new MasterData(data, null, ""));
        });
        return datas;
    }
}
