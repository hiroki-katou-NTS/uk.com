package nts.uk.file.at.app.export.worktype;

import java.util.ArrayList;
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
@DomainID(value = "EmploymentApprovalSetting")
public class EmploymentApprovalSettingExportImpl implements MasterListData {

	@Inject
	private ApprovalFunctionConfigRepository repository;
	
	public static final String KAF022_628 = "コード";
	public static final String KAF022_629 = "名称";
	public static final String KAF022_630 = "申請の種類";
	public static final String KAF022_631 = "申請内容";
	public static final String KAF022_632 = "利用しない";
	public static final String KAF022_633 = "対象勤務種類";

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KAF022_628, TextResource.localize("KAF022_628"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_629, TextResource.localize("KAF022_629"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_630, TextResource.localize("KAF022_630"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_631, TextResource.localize("KAF022_631"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_632, TextResource.localize("KAF022_632"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_633, TextResource.localize("KAF022_633"),
                ColumnTextAlign.LEFT, "", true));
        
		return columns;
	}
	
    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String cid = AppContexts.user().companyId();
        return repository.getAllEmploymentApprovalSetting(cid);
    }
	
    @Override
    public String mainSheetName() {
        return TextResource.localize("Com_Employment");
    }
}
