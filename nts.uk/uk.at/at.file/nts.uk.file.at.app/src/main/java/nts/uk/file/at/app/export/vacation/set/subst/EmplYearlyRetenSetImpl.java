package nts.uk.file.at.app.export.vacation.set.subst;

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
@DomainID(value="EmplYearlyRetenSet")
public class EmplYearlyRetenSetImpl implements MasterListData {
    public static final String KMF001_204 = "雇用コード";
    public static final String KMF001_205 = "名称";
    public static final String KMF001_200 = "利用設定";
    public static final String KMF001_201 = "保持できる年数";
    public static final String KMF001_202 = "上限日数";


    @Inject
    private EmplYearlyRetenSetRepository mRetenYearlySetRepository;
    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        return mRetenYearlySetRepository.getAllEmplYearlyRetenSet(AppContexts.user().companyId());
    }

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KMF001_204, TextResource.localize("KMF001_204"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_205, TextResource.localize("KMF001_205"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_200, TextResource.localize("KMF001_200"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_201, TextResource.localize("KMF001_201"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_202, TextResource.localize("KMF001_202"),
                ColumnTextAlign.CENTER, "", true));
        return columns;
    }
}
