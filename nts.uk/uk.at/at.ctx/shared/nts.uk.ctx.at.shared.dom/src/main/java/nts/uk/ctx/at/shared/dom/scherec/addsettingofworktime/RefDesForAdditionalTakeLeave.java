package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.util.Optional;

/*
UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).就業時間の加算設定.休暇取得時加算時間の参照先
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

@NoArgsConstructor
@Getter
public class RefDesForAdditionalTakeLeave {

    // 会社一律加算時間
    private BreakDownTimeDay comUniformAdditionTime;
    // 参照先設定
    private VacationAdditionTimeRef referenceSet;
    // 個人別設定参照先
    private Optional<VacationSpecifiedTimeRefer> referIndividualSet;

    public RefDesForAdditionalTakeLeave(BreakDownTimeDay comUniformAdditionTime,
                                        int referenceSet,
                                        Integer referIndividualSet) {
        this.comUniformAdditionTime = comUniformAdditionTime;
        this.referenceSet = EnumAdaptor.valueOf(referenceSet, VacationAdditionTimeRef.class);
        this.referIndividualSet = referenceSet != VacationAdditionTimeRef.REFER_PERSONAL_SET.value ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(referIndividualSet, VacationSpecifiedTimeRefer.class));
    }

    /** 休暇加算時間の取得 */
    public BreakDownTimeDay getVacationAddTime(Require require, String cid, String sid,
    		Optional<WorkTimeCode> workTimeCode, GeneralDate baseDate) {
    	
    	/** 参照先を確認 */
    	if (this.referenceSet != VacationAdditionTimeRef.REFER_PERSONAL_SET) {
    		
    		/** 就業時間帯コードOptional．emptyチェック */
    		if (workTimeCode.isPresent()) {

    		    /** 所定時間を取得する */
    			return getVacationAddTimeFromWorkTime(require, cid, workTimeCode);
    		}
    		
    		/** 参照先設定で分岐 */
    		if (this.referenceSet == VacationAdditionTimeRef.REFER_ACTUAL_WORK_OR_COM_SET) {
    			
    			/** 会社一律の設定を取得 */
    			return comUniformAdditionTime;
    		}
    	}
    	
    	/** 個人別設定参照先を確認 */
    	if(referIndividualSet.get() == VacationSpecifiedTimeRefer.WORK_HOUR_DUR_WEEKDAY) {
    		
    		/** 平日時集票時間帯を参照 */
    		return getVacationAddTimeForWeekend(require, cid, sid, baseDate);
    	}
    	
    	/** 個人情報休暇加算時間を参照 */
    	val inviBrd = getVacationAddTimeForIndividual(require, sid, baseDate);

    	/** 1日の時間内訳を返す */
    	return inviBrd.orElse(comUniformAdditionTime);
    }
    
    /** 平日時就業時間帯を参照 */
    private BreakDownTimeDay getVacationAddTimeForWeekend(RequireM2 require, String cid, String sid, GeneralDate baseDate) {
    	
    	/** 社員の労働条件を取得する */
    	val workCond = require.workingConditionItem(sid, baseDate);
    	
    	/** 平日時の就業時間帯コードを取得する */ 
    	val workTimeCode = workCond.flatMap(c -> c.getWorkCategory().getWeekdayTime().getWorkTimeCode());
    	if (workTimeCode.isPresent()) {
    		
    		/** 所定時間を取得する */
    		return getVacationAddTimeFromWorkTime(require, cid, workTimeCode);
    	}
    	
    	/** 1日の時間内訳を０で返す */
    	return zeroBrdTimeDay();
    }

    /** 個人情報休暇加算時間を参照 */
    private Optional<BreakDownTimeDay> getVacationAddTimeForIndividual(RequireM3 require, String sid, GeneralDate baseDate) {
    	
    	/** 社員の労働条件を取得する */
    	val workCond = require.workingConditionItem(sid, baseDate);
    	
    	/** 休暇加算時間利用区分を確認 */
    	if (workCond.map(c -> c.getVacationAddedTimeAtr()).orElse(NotUseAtr.NOTUSE) == NotUseAtr.NOTUSE) 
    		return Optional.empty();
    	
    	return workCond.map(c -> {
    		
    		/** 休暇加算時間設定を取得する */
    		return c.getHolidayAddTimeSet().orElseGet(() -> zeroBrdTimeDay());
    	});
    }

    /** 所定時間を取得する */
	private BreakDownTimeDay getVacationAddTimeFromWorkTime(RequireM1 require, String cid,
			Optional<WorkTimeCode> workTimeCode) {
		/** 所定時間を取得 */
		val preSet = require.predetemineTimeSetting(cid, workTimeCode.get().v());
		if (preSet.isPresent()) {
			
			/** 所定時間．就業加算時間を取得 */
			return preSet.get().getPredTime().getAddTime();
		}
		
		/** 1日の時間内訳を０で設定 */
		return zeroBrdTimeDay();
	}

	private BreakDownTimeDay zeroBrdTimeDay() {
		return new BreakDownTimeDay(AttendanceTime.ZERO, AttendanceTime.ZERO, AttendanceTime.ZERO);
	}
	
    public static interface RequireM3 extends Require0 {

    }
	
    public static interface RequireM2 extends Require0, RequireM1 {

    }
    
    public static interface RequireM1 {
    	
    	Optional<PredetemineTimeSetting> predetemineTimeSetting(String cid, String workTimeCode);
    }
    
    public static interface Require0 {

    	Optional<WorkingConditionItem> workingConditionItem(String sid, GeneralDate baseDate);
    }
	
	
    public static interface Require extends RequireM2, RequireM3 {

    }
}
