package nts.uk.file.at.app.export.worktype;

import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.*;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class ApprovalFunctionConfigExport {
	
	private static final String KAF022_635 = "コード";
    private static final String KAF022_636 = "名称";
    private static final String KAF022_637 = "申請の種類";
    private static final String KAF022_638 = "利用設定";
    private static final String KAF022_639 = "指示が必須";
    private static final String KAF022_640 = "事前必須の設定";
    private static final String KAF022_641 = "時刻計算";
    private static final String KAF022_642 = "外出入力欄を表示する";
    private static final String KAF022_643 = "実績から外出を初期表示する";
    private static final String KAF022_644 = "出退勤時刻の初期表示";
    private static final String KAF022_645 = "残業時間を入力";
    private static final String KAF022_646 = "休出時間を入力";
    private static final String KAF022_647 = "実績を取り消す";
    private static final String KAF022_648 = "申請時に選択";
    private static final String KAF022_649 = "備考";

    public List<MasterHeaderColumn> getHeaderColumnsApproveConfig() {
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

    public MasterData getDataApprovalConfig(Object[] r) {
        Map<String, MasterCellData> data = new HashMap<>();
        data.put(ApprovalFunctionConfigExport.KAF022_635,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_635).value(r[0])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(ApprovalFunctionConfigExport.KAF022_636,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_636).value(r[1])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(ApprovalFunctionConfigExport.KAF022_637,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_637).value(r[2])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(ApprovalFunctionConfigExport.KAF022_638,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_638).value(r[3])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(ApprovalFunctionConfigExport.KAF022_639,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_639).value(r[4])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(ApprovalFunctionConfigExport.KAF022_640,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_640).value(r[5])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(ApprovalFunctionConfigExport.KAF022_641,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_641).value(r[6])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(ApprovalFunctionConfigExport.KAF022_642,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_642).value(r[7])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(ApprovalFunctionConfigExport.KAF022_643,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_643).value(r[8])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(ApprovalFunctionConfigExport.KAF022_644,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_644).value(r[9])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(ApprovalFunctionConfigExport.KAF022_645,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_645).value(r[10])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(ApprovalFunctionConfigExport.KAF022_646,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_646).value(r[11])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(ApprovalFunctionConfigExport.KAF022_647,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_647).value(r[12])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(ApprovalFunctionConfigExport.KAF022_648,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_648).value(r[13])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        data.put(ApprovalFunctionConfigExport.KAF022_649,
                MasterCellData.builder().columnId(ApprovalFunctionConfigExport.KAF022_649).value(r[14])
                        .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
        return MasterData.builder().rowData(data).build();
    }


}
