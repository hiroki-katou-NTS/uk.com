package nts.uk.ctx.at.shared.app.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.daily.dailyperformance.classification.DoWork;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.JobAutoCalSettingRepository;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.PositionAutoCalSettingExport;
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
        List<PositionAutoCalSettingExport> positionAutoCalSetting = jobAutoCalSettingRepository.getPositionSettingToExport(companyId);
        List <MasterData> datas = new ArrayList<>();
        positionAutoCalSetting.forEach(item -> {
            Map<String, Object> data = new HashMap<>();
            data.put(KMK006_79, item.getPositionCode());
            data.put(KMK006_80, item.getPositionName());
            if(WorkplaceAutoCalSettingExportImpl.REGISTERED.equals(item.getRegistered())) {
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
