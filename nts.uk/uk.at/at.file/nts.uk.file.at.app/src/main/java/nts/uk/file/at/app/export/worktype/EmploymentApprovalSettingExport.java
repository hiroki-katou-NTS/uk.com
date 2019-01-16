package nts.uk.file.at.app.export.worktype;

import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.*;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class EmploymentApprovalSettingExport {
	
	private static final String KAF022_628 = "コード";
	private static final String KAF022_629 = "名称";
	private static final String KAF022_630 = "申請の種類";
	private static final String KAF022_631 = "申請内容";
	private static final String KAF022_632 = "利用しない";
	private static final String KAF022_633 = "対象勤務種類";

	public List<MasterHeaderColumn> getHeaderColumnsEmpApprove() {
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

    public MasterData getDataEmploymentApprovalSetting(Object[] r) {
        Map<String, MasterCellData> data = new HashMap<>();
        data.put(EmploymentApprovalSettingExport.KAF022_628,
                MasterCellData.builder().columnId(EmploymentApprovalSettingExport.KAF022_628).value(r[0])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(EmploymentApprovalSettingExport.KAF022_629,
                MasterCellData.builder().columnId(EmploymentApprovalSettingExport.KAF022_629).value(r[1])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(EmploymentApprovalSettingExport.KAF022_630,
                MasterCellData.builder().columnId(EmploymentApprovalSettingExport.KAF022_630).value(r[2])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(EmploymentApprovalSettingExport.KAF022_631,
                MasterCellData.builder().columnId(EmploymentApprovalSettingExport.KAF022_631).value(r[3])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(EmploymentApprovalSettingExport.KAF022_632,
                MasterCellData.builder().columnId(EmploymentApprovalSettingExport.KAF022_632).value(r[4])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(EmploymentApprovalSettingExport.KAF022_633,
                MasterCellData.builder().columnId(EmploymentApprovalSettingExport.KAF022_633).value(r[5])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

        return MasterData.builder().rowData(data).build();
    }

}
