package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

import java.util.List;
import java.util.Optional;

/**
 * 勤務サイクルの反映設定
 */
@Getter
@AllArgsConstructor
public class WorkCycleRefSetting {

    /** 勤務サイクルコード */
    private final WorkCycleCode workCycleCode;

    /** 反映順序 */
    private final List<WorkCreateMethod> refOrder;

    /** スライド日数 */
    private final int numOfSlideDays;

    /** 法定休日の勤務種類 */
    private final Optional<WorkTypeCode> legalHolidayCd;

    /** 法定外休日の勤務種類 */
    private final Optional<WorkTypeCode> nonStatutoryHolidayCd;

    /** 祝日の勤務種類 */
    private final Optional<WorkTypeCode> holidayCd;
    
    /**
     * [C-0] 勤務サイクルの反映設定(勤務サイクルコード, 反映順序, スライド日数,法定休日の勤務種類, 法定外休日の勤務種類, 祝日の勤務種類)
     * @param code
     * @param refOrder
     * @param numOfSlideDays
     * @param legalHolidayCd
     * @param nonStatutoryHolidayCd
     * @param holidayCd
     */
    public WorkCycleRefSetting(
    		String code, 
    		List<WorkCreateMethod> refOrder, 
    		int numOfSlideDays, 
    		String legalHolidayCd, 
    		String nonStatutoryHolidayCd, 
    		String holidayCd) {
    	
        this.workCycleCode = new WorkCycleCode(code);
        this.refOrder = refOrder;
        this.numOfSlideDays = numOfSlideDays;
        this.legalHolidayCd = legalHolidayCd == null ? Optional.empty() : Optional.of(new WorkTypeCode(legalHolidayCd));
        this.nonStatutoryHolidayCd = nonStatutoryHolidayCd == null ? Optional.empty() : Optional.of(new WorkTypeCode(nonStatutoryHolidayCd));
        this.holidayCd = holidayCd == null ? Optional.empty() : Optional.of(new WorkTypeCode(holidayCd));
    }

    /**
     * 作る
     * @param workCycleCode 勤務サイクルコード
     * @param refOrder 反映順序
     * @param numOfSlideDays スライド日数
     * @param legalHolidayCd 法定休日の勤務種類
     * @param nonStatutoryHolidayCd 法定外休日の勤務種類
     * @param holidayCd 祝日の勤務種類
     * @return 勤務サイクルの反映設定
     */
    public static WorkCycleRefSetting create(
    		WorkCycleCode workCycleCode,
    		List<WorkCreateMethod> refOrder,
    		int numOfSlideDays,
    		Optional<WorkTypeCode> legalHolidayCd,
    		Optional<WorkTypeCode> nonStatutoryHolidayCd,
    		Optional<WorkTypeCode> holidayCd) {
    	
    	if ( refOrder.contains(WorkCreateMethod.WEEKLY_WORK) ) {
    		if (!legalHolidayCd.isPresent()) {
    			throw new BusinessException("Msg_2193");
    		}
    		
    		if (!nonStatutoryHolidayCd.isPresent()) {
    			throw new BusinessException("Msg_2194");
    		}
    	}
    	
    	if ( refOrder.contains(WorkCreateMethod.PUB_HOLIDAY) && !holidayCd.isPresent()) {
    		throw new BusinessException("Msg_2195");
    	}
    	
    	return new WorkCycleRefSetting(workCycleCode, refOrder, numOfSlideDays, legalHolidayCd, nonStatutoryHolidayCd, holidayCd);
    }

}
