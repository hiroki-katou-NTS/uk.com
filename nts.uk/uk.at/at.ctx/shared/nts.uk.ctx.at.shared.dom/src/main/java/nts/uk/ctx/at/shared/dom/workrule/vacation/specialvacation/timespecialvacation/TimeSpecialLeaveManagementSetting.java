package nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;

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
    private TimeVacationDigestUnit timeVacationDigestUnit;
    
    /**
     * [C-0] 時間特別休暇の管理設定 (会社ID,時間休暇消化単位)
     */
    public TimeSpecialLeaveManagementSetting(String companyId, TimeVacationDigestUnit timeVacationDigestUnit) {
		super();
		this.companyId = companyId;
		this.timeVacationDigestUnit = timeVacationDigestUnit;
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
    public List<Integer> getDailyAttdItemsNotAvailable(TimeVacationDigestUnit.Require require){
    	List<Integer> attendanceItemIds = new ArrayList<>();
    	if (!this.isManageTimeVacation(require)) {
    		attendanceItemIds = Arrays.asList(504,516,1123,1124,1127,1128,1131,1132,1135,1136,1145,1146);
    	}
		return attendanceItemIds;
    }

    /**
     * [3]時間休暇が管理するか
     * @param require
     */
    public boolean isManageTimeVacation(TimeVacationDigestUnit.Require require) {
    	return this.timeVacationDigestUnit.isVacationTimeManage(require);
    }
    
    /**
     * [4]利用する休暇時間の消化単位をチェックする
     * @param require
     * @param time 休暇使用時間
     */
    public boolean checkVacationTimeUnitUsed(TimeVacationDigestUnit.Require require, AttendanceTime time) {
      return this.timeVacationDigestUnit.checkDigestUnit(require, time);
    }
}
