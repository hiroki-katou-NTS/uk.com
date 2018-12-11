package nts.uk.file.at.app.export.vacation.set.annualpaidleave;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID (value="AnnPaidLeave")
public class AnnPaidLeaveImpl implements MasterListData {
	
	public static final String KMF001_1 = "項目";
    public static final String KMF001_2 = "Level_2";
    public static final String KMF001_3 = "Level_3";
    public static final String KMF001_4 = "Level_4";
    public static final String KMF001_5 = "値";
    
    @Inject
    private AnnPaidLeaveRepository annPaidLeaveRepository;
    
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		return annPaidLeaveRepository.getAnPaidLea(AppContexts.user().companyId());
	}
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KMF001_1, TextResource.localize("KMF001_1"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_2, "",
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KMF001_3, "",
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_4, ""	,
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(KMF001_5, TextResource.localize("KMF001_5"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
	}
}
