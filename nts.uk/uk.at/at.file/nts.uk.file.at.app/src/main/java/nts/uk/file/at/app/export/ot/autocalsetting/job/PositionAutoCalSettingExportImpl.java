package nts.uk.file.at.app.export.ot.autocalsetting.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.file.at.app.export.ot.autocalsetting.AutoCalSettingExport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID(value ="PositionAutoCalSetting")
public class PositionAutoCalSettingExportImpl implements MasterListData{

    @Inject
    private JobAutoCalSettingRepository jobAutoCalSettingRepository;

    @Inject
    private AutoCalSettingExport autoCalSettingExport;

    private static final String KMK006_79 = "職位コード";
    private static final String KMK006_80 = "職位名";
    private static final String KMK006_78 = "設定済み";

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0) {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KMK006_79, TextResource.localize("KMK006_79"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_80, TextResource.localize("KMK006_80"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMK006_78, TextResource.localize("KMK006_78"),
        ColumnTextAlign.CENTER, "", true));
        columns.addAll(autoCalSettingExport.getHeaderColumns());
        return columns;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String companyId = AppContexts.user().companyId();
        String baseDate = query.getData().toString().substring(0,10);
        List<Object[]> positionAutoCalSetting = jobAutoCalSettingRepository.getPositionSettingToExport(companyId , baseDate);
        if(positionAutoCalSetting.size() == 0){
            throw new BusinessException("Msg_1480");
        }
        List <MasterData> datas = new ArrayList<>();
        positionAutoCalSetting.forEach(item -> {
            Map<String, Object> data = new HashMap<>();
            data.put(KMK006_79, item[23]);
            data.put(KMK006_80, item[24]);
            data.put(KMK006_78, "✓");
            autoCalSettingExport.putDatas(item, data);
            datas.add(new MasterData(data, null, ""));
        });
        return datas;
    }
}
