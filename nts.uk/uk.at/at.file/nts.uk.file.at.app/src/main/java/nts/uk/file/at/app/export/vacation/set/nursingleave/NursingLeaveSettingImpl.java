package nts.uk.file.at.app.export.vacation.set.nursingleave;

import nts.uk.file.at.app.export.vacation.set.sixtyhours.Com60HourVacaRepository;
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

public class NursingLeaveSettingImpl implements MasterListData {
    public static final String KMF001_166 = "項目";
    public static final String KMF001_168 = "Level_1";
    public static final String KMF001_169= "Level_2";
    public static final String KMF001_170= "Level_3";
    public static final String KMF001_167 = "値";






    @Inject
    private NursingLeaveSetRepository mNursingLeaveSetRepository;
    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        return mNursingLeaveSetRepository.getAllNursingLeaveSetting(AppContexts.user().companyId());
    }

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KMF001_166, TextResource.localize("KMF001_166"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_168, TextResource.localize(""),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_169, TextResource.localize(""),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_170, TextResource.localize(""),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_167, TextResource.localize("KMF001_167"),
                ColumnTextAlign.CENTER, "", true));
        return columns;
    }
}


