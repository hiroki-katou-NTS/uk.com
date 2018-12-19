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

/*MasterList KMF001 - H*/
@Stateless
@DomainID(value="ShiftCompany")
public class ComSubstVacationImpl  implements MasterListData {
    public static final String KMF001_224 = "振休の管理";
    public static final String KMF001_225 = "使用期限";
    public static final String KMF001_226 = "振休の先取り";



    @Inject
    private ComSubstVacatRepository mComSubstVacatRepository;
    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        return mComSubstVacatRepository.getAllComSubstVacation(AppContexts.user().companyId());
    }

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KMF001_224, TextResource.localize("KMF001_224"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_225, TextResource.localize("KMF001_225"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_226, TextResource.localize("KMF001_226"),
                ColumnTextAlign.CENTER, "", true));
        return columns;
    }
}
