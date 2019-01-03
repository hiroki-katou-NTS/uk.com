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
@DomainID(value ="ApprovalFunctionConfig")
public class ApprovalFunctionConfigExportImpl implements MasterListData{
    
	@Inject
	private ApprovalFunctionConfigRepository repository;
	
	public static final String KAF022_635 = "コード";
    public static final String KAF022_636 = "名称";
    public static final String KAF022_637 = "申請の種類";
    public static final String KAF022_638 = "利用設定";
    public static final String KAF022_639 = "指示が必須";
    public static final String KAF022_640 = "事前必須の設定";
    public static final String KAF022_641 = "時刻計算";
    public static final String KAF022_642 = "外出入力欄を表示する";
    public static final String KAF022_643 = "実績から外出を初期表示する";
    public static final String KAF022_644 = "出退勤時刻の初期表示";
    public static final String KAF022_645 = "残業時間を入力";
    public static final String KAF022_646 = "休出時間を入力";
    public static final String KAF022_647 = "実績を取り消す";
    public static final String KAF022_648 = "申請時に選択";
    public static final String KAF022_649 = "備考";
    
    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0){
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(KAF022_635, TextResource.localize("KAF022_635"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_636, TextResource.localize("KAF022_636"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_637, TextResource.localize("KAF022_637"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_638, TextResource.localize("KAF022_638"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_639, TextResource.localize("KAF022_639"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_640, TextResource.localize("KAF022_640"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_641, TextResource.localize("KAF022_641"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_642, TextResource.localize("KAF022_642"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_643, TextResource.localize("KAF022_643"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_644, TextResource.localize("KAF022_644"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_645, TextResource.localize("KAF022_645"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_646, TextResource.localize("KAF022_646"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_647, TextResource.localize("KAF022_647"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_648, TextResource.localize("KAF022_648"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(KAF022_649, TextResource.localize("KAF022_649"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }
    
    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String cid = AppContexts.user().companyId();
        return repository.getAllApprovalFunctionConfig(cid);
    }
    
    @Override
    public String mainSheetName() {
        return TextResource.localize("Com_Workplace");
    }
}
