package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import javax.ejb.Stateless;
import java.util.List;

/**
 * AggregateRoot: 勤務状況の出力設定
 * @author : chinh.hm
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkStatusOutputSettings extends AggregateRoot {

    // 	設定ID 	(GUID)
    private String settingId;

    //  設定表示コード ->(出力項目設定コード)
    private ExportSettingCode settingDisplayCode;

    // 	設定名称 ->(出力項目設定名称)
    private OutputItemSettingName settingName;

    // 	社員ID
    private String employeeId;

    // 	定型自由区分
    private SettingClassificationCommon designateFreeClassing;

    // 	出力項目リスト

    private List<OutputItem> outputItem;

    //  [C-0] 勤怠状況の出力設定を作成する

    // 	[1]　定型選択の重複をチェックする
    boolean  checkDuplicateStandardSelections(Require require,String yearHolidayCode,String cid){
        return require.checkTheStandard(yearHolidayCode,cid);
    }

    // [2]　自由設定の重複をチェックする
    boolean checkDuplicateFreeSettings(Require require,String yearHolidayCode,String cid,String employeeId){
        return require.checkFreedom(yearHolidayCode,cid,employeeId);
    }

    public interface Require{
        // 	[R-1]　定型をチェックする-> 勤務状況表の出力項目Repository.	exist(コード、ログイン会社ID)
        boolean checkTheStandard(String code,String cid);
        // 	[R-2]  自由をチェックする->	勤務状況表の出力項目Repository.	exist(コード、ログイン会社ID、ログイン社員ID)
        boolean checkFreedom(String code,String cid,String employeeId);

    }

}
