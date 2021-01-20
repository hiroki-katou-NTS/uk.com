package nts.uk.ctx.at.function.ws.arbitraryperiodsummarytable;


import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import java.util.List;

@Builder
@Data
public class OutputSettingDetailArbitraryDto {
    // ID: GUID
    private final String id;

    // コード: 出力項目設定コード
    private String code;

    // 印刷する勤怠項目リスト: 印刷する勤怠項目
    private List<AttendanceItemToPrint> outputItemList;

    // 名称:  出力項目設定名称
    private String name;

    // 定型自由区分: 帳票共通の設定区分
    private SettingClassificationCommon standardFreeClassification;

    // 社員ID: 社員ID
    private String employeeId;
}
