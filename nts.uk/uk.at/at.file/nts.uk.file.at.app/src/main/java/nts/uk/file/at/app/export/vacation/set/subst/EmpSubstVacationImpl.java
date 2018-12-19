package nts.uk.file.at.app.export.vacation.set.subst;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class EmpSubstVacationImpl implements MasterListData {
    public static final String KMF001_204 = "利用設定";
    public static final String KMF001_205 = "保持できる年数";
    public static final String KMF001_224 = "上限日数";
    public static final String KMF001_225 = "出勤日数として加算";
    public static final String KMF001_226 = "出勤日数として加算";

    @Inject
    private EmpSubstVacaRepository mEmpSubstVacaRepository;
    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        return mEmpSubstVacaRepository.getAllEmpSubstVacation(AppContexts.user().companyId());
    }

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KMF001_204, TextResource.localize("KMF001_204"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_205, TextResource.localize("KMF001_205"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_224, TextResource.localize("KMF001_224"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_225, TextResource.localize("KMF001_225"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_226, TextResource.localize("KMF001_226"),
                ColumnTextAlign.CENTER, "", true));
        return columns;
    }
}

