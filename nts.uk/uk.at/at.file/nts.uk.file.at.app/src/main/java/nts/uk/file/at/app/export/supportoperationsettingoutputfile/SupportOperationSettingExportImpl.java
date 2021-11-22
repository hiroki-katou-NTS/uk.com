package nts.uk.file.at.app.export.supportoperationsettingoutputfile;

import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.*;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@DomainID("SupportOperationSettingExport")
public class SupportOperationSettingExportImpl implements MasterListData {

    @Inject
    private SupportOperationSettingRepository supportOperationSettingRepository;

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(SupportOperationSettingColumn.KHA001_3, TextResource.localize("KHA001_3"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SupportOperationSettingColumn.KHA001_7, TextResource.localize("KHA001_7"),
                ColumnTextAlign.LEFT, "", true));

        return columns;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String cid = AppContexts.user().companyId();
        List<MasterData> datas = new ArrayList<>();
        SupportOperationSetting supportOSExportData = supportOperationSettingRepository.get(cid);
        if (supportOSExportData == null) {
            return datas;
        }
        datas.add(toData(supportOSExportData));
        return datas;
    }

    @Override
    public MasterListMode mainSheetMode(){
        return MasterListMode.NONE;
    }

    private MasterData toData(SupportOperationSetting x) {
        Map<String, MasterCellData> data = new HashMap<>();
        data.put(SupportOperationSettingColumn.KHA001_3, MasterCellData.builder()
                .columnId(SupportOperationSettingColumn.KHA001_3)
                .value(x.isUsed() ? TextResource.localize("KHA001_10") : TextResource.localize("KHA001_11"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
        data.put(SupportOperationSettingColumn.KHA001_7, MasterCellData.builder()
                .columnId(SupportOperationSettingColumn.KHA001_7)
                .value(x.isUsed()? x.getMaxNumberOfSupportOfDay().v():"-")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
        return MasterData.builder().rowData(data).build();
    }

    @Override
    public String mainSheetName() {
        return TextResource.localize("KHA001_50");
    }
}
