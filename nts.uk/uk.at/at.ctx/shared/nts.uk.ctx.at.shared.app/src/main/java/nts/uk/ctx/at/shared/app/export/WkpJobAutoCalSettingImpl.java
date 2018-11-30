package nts.uk.ctx.at.shared.app.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingExport;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID(value ="WkpJobAutoCalSettingImpl")
public class WkpJobAutoCalSettingImpl implements MasterListData{

    @Inject
    private AutoCalSettingExport autoCalSettingExport;

    @Inject
    private WkpJobAutoCalSettingRepository wkpJobAutoCalSettingRepository;

    private static final String KMK006_76 = "職場コード";
    private static final String KMK006_77 = "職場名";
    private static final String KMK006_79 = "職位コード";
    private static final String KMK006_80 = "職位名";
    

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0){
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KMK006_76, TextResource.localize("KMK006_76"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_77, TextResource.localize("KMK006_77"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_79, TextResource.localize("KMK006_79"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_80, TextResource.localize("KMK006_80"),
        ColumnTextAlign.LEFT, "", true));
        columns.addAll(autoCalSettingExport.getHeaderColumns());
        return columns;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query){
        String companyId = AppContexts.user().companyId();
        List<WkpJobAutoCalSettingExport> wkpJobAutoCalSetting = wkpJobAutoCalSettingRepository.getWkpJobSettingToExport(companyId);
        List <MasterData> datas = new ArrayList<>();
        wkpJobAutoCalSetting.forEach(item -> {
            Map<String, Object> data = new HashMap<>();
            data.put(KMK006_76, item.getWorkPlaceCode());
            data.put(KMK006_77, item.getWorkPlaceName());
            data.put(KMK006_79, item.getPositionCode());
            data.put(KMK006_80, item.getPositionName());
            autoCalSettingExport.putDatas(item, data);
            datas.add(new MasterData(data, null, ""));
        });
        return datas;
    }
}
