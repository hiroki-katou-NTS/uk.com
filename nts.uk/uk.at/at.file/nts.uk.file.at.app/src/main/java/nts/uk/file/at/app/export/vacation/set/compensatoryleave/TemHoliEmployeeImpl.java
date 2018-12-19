package nts.uk.file.at.app.export.vacation.set.compensatoryleave;

import nts.uk.file.at.app.export.vacation.set.annualpaidleave.AnnPaidLeaveRepository;
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

public class TemHoliEmployeeImpl implements MasterListData {
    public static final String KMF001_204 = "雇用コード";
    public static final String KMF001_205 = "雇用名";
    public static final String KMF001_223 = "代休設定";
    public static final String KMF001_207 = "代休使用期限";
    public static final String KMF001_208 = "代休の先取り";
    public static final String KMF001_210 = "時間代休の管理";
    public static final String KMF001_211 = "消化単位";


    @Inject
    private TemHoliEmployeeRepository mTemHoliEmployeeRepository;

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        return mTemHoliEmployeeRepository.getAllTemHoliEmployee(AppContexts.user().companyId());
    }

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KMF001_204, TextResource.localize("KMF001_204"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_205, TextResource.localize("KMF001_205"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_223, TextResource.localize("KMF001_223"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_207,TextResource.localize("KMF001_207"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_208, TextResource.localize("KMF001_208"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_210, TextResource.localize("KMF001_210"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_211, TextResource.localize("KMF001_211"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }
}
