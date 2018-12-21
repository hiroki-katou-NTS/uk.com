package nts.uk.file.at.app.export.vacation.set.annualpaidleave;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
@DomainID(value="AcquisitionRule")
public class AcquisitionRuleImpl implements MasterListData {

    public static final String KMF001_166 = "項目";
    public static final String KMF001_168 = "Level_1";
    public static final String KMF001_169 = "Level_2";
    public static final String KMF001_167 = "値";

    @Inject
    private AcquisitionRuleExportRepository mAcquisitionRuleExportRepository;

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        return mAcquisitionRuleExportRepository.getAllAcquisitionRule(AppContexts.user().companyId());
    }

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {

        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KMF001_166, TextResource.localize("KMF001_166"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_168, TextResource.localize("KMF001_168"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_169, TextResource.localize("KMF001_169"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_167, TextResource.localize("KMF001_167"),
                ColumnTextAlign.LEFT, "", true));

        return columns;
    }
}
