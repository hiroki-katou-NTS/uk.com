package nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.休暇.特別休暇.時間特別休暇
 * 時間特別休暇の管理設定
 */
@NoArgsConstructor
@Getter
@Setter
public class TimeSpecialLeaveManagementSetting extends AggregateRoot {
    // 会社ID
    private String companyId;

    // 時間休暇消化単位
    private TimeDigestiveUnit timeDigestiveUnit;

    // 管理区分
    private ManageDistinct manageType;
    
    /**
     * C-0 Nhờ nws sửa lại khi update theo tài liệu mới
     */
    public TimeSpecialLeaveManagementSetting(String companyId, TimeDigestiveUnit timeDigestiveUnit,
			ManageDistinct manageType) {
		super();
		this.companyId = companyId;
		this.timeDigestiveUnit = timeDigestiveUnit;
		this.manageType = manageType;
	}
    
    /**
     * [1] 時間特別休暇に対応する日次の勤怠項目を取得する
     */
    public List<Integer> getDailyAttdItemsCorrespondSpecialLeave(){
    	return Arrays.asList(543,504,516,1123,1124,1127,1128,1131,1132,1135,1136,1145,1146);
    }
    
    /**
     * [2] 利用できない日次の勤怠項目を取得する
     */
    public List<Integer> getDailyAttdItemsNotAvailable(){
    	if (manageType == ManageDistinct.NO) { // Nhờ NWS sửa theo tài liệu mới
    		return Arrays.asList(504,516,1123,1124,1127,1128,1131,1132,1135,1136,1145,1146);
    	}
		return new ArrayList<>();
    }
}
