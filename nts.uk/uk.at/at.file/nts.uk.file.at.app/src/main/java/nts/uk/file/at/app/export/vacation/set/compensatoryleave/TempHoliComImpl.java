package nts.uk.file.at.app.export.vacation.set.compensatoryleave;

import nts.uk.file.at.app.export.vacation.set.subst.EmplYearlyRetenSetRepository;
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
@DomainID(value="TempHolidaysCom")
public class TempHoliComImpl implements MasterListData {
    public static final String KMF001_206 = "代休の管理";
    public static final String KMF001_207 = "代休使用期限";
    public static final String KMF001_208 = "代休の先取り";
    public static final String KMF001_209 = "代休期限チェック月数";
    public static final String KMF001_210 = "時間代休の管理";
    public static final String KMF001_211 = "消化単位";
    public static final String KMF001_212 = "代休の発生に必要な休日出勤時間";
    public static final String KMF001_213 = "代休出勤時間の条件";
    public static final String KMF001_214 = "指定した時間を代休とする１日";
    public static final String KMF001_215 = "指定した時間を代休とする半日";
    public static final String KMF001_216 = "一定時間を超えたら代休とする";
    public static final String KMF001_217 = "代休の発生に必要な残業時間";
    public static final String KMF001_218 = "代休残業時間の条件";
    public static final String KMF001_219 = "指定した時間を代休とする１日";
    public static final String KMF001_220 = "指定した時間を代休とする半日";
    public static final String KMF001_221 = "一定時間を超えたら代休とする";

    @Inject
    private TempHoliComImplRepository mTempHoliComImplRepository;
    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        return mTempHoliComImplRepository.getAllTemHoliCompany(AppContexts.user().companyId());
    }

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KMF001_206, TextResource.localize("KMF001_206"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_207, TextResource.localize("KMF001_207"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_208, TextResource.localize("KMF001_208"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_209, TextResource.localize("KMF001_209"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_210, TextResource.localize("KMF001_210"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_211, TextResource.localize("KMF001_211"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_212, TextResource.localize("KMF001_212"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_213, TextResource.localize("KMF001_213"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_214, TextResource.localize("KMF001_214"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_215, TextResource.localize("KMF001_215"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_216, TextResource.localize("KMF001_216"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_217, TextResource.localize("KMF001_217"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_218, TextResource.localize("KMF001_218"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_219, TextResource.localize("KMF001_219"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_220, TextResource.localize("KMF001_220"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_221, TextResource.localize("KMF001_221"),
                ColumnTextAlign.CENTER, "", true));
        return columns;
    }
}
