package nts.uk.file.at.app.export.shift.daily;

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
import java.util.Map;

@Stateless
@DomainID(value = "RegisterPattern")
public class DailyPatternExportImpl implements MasterListData {
    @Inject
    private DailyPatternExRepository mDailyPatternExRepository;

    private List<MasterData> masterData = new ArrayList<MasterData>();
    private static final String KSM003_37 = "コードカラム";
    private static final String KSM003_38 = "名称カラム";
    private static final String KSM003_39 = "勤務種類カラム";
    private static final String KSM003_40 = "就業時間帯カラム";
    private static final String KSM003_41 = "期間カラム";

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(
                new MasterHeaderColumn(KSM003_37, TextResource.localize("KSM003_37"), ColumnTextAlign.LEFT, "", true));
        columns.add(
                new MasterHeaderColumn(KSM003_38, TextResource.localize("KSM003_38"), ColumnTextAlign.LEFT, "", true));
        columns.add(
                new MasterHeaderColumn(KSM003_39, TextResource.localize("KSM003_39"), ColumnTextAlign.LEFT, "", true));
        columns.add(
                new MasterHeaderColumn(KSM003_40, TextResource.localize("KSM003_40"), ColumnTextAlign.LEFT, "", true));
        columns.add(
                new MasterHeaderColumn(KSM003_41, TextResource.localize("KSM003_41"), ColumnTextAlign.LEFT, "",true));

        return columns;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        return mDailyPatternExRepository.findAllDailyPattern();
    }
}
