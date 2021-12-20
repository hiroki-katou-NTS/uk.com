package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.license.option.OptionLicense;

/**
 * AggregateRoot: 作業運用設定
 * Ea: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.運用設定.作業運用設定.
 * @author : chinh.hm
 */
@AllArgsConstructor
@Getter
public class TaskOperationSetting extends AggregateRoot {
    // 作業運用方法
    private TaskOperationMethod taskOperationMethod;
    
    
    /**
	 * 	[1] 残業枠NOに対応する日次の勤怠項目を取得する
	 * @return
	 */
//	public List<Integer> getDaiLyAttendanceIdByWork() {
//		return GetAttendanceItemIdService
//	}
    
    /**
     * [3] 実績で作業を利用できるか
     * @param require
     */
    public boolean canWorkUsedWithAchievements(Require require) {
    	OptionLicense optionLicense = require.getOptionLicense();
    	if(!optionLicense.attendance().workload() ||
    	    !optionLicense.attendance().schedule().medical() ||
		 this.taskOperationMethod != TaskOperationMethod.USED_IN_ACHIEVENTS) {
    		return false;
    	}
    	return true;
	}
    
    public static interface Require {
    	/**
    	 * 
    	 * AppContexts.optionLicense();
    	 * @return
    	 */
    	OptionLicense getOptionLicense();
	}
}
