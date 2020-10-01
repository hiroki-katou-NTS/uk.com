package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime;

import java.util.Optional;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 人件費・時間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.スケジュール集計.人件費・時間.人件費・時間
 * @author dan_pv
 *
 */
@Value
public class LaborCostAndTime implements DomainValue{

    /**
     * 	利用区分
     */
    private final NotUseAtr useClassification;
    
    /**
     * 	時間	
     */
    private final NotUseAtr time;
    
    /**
     * 人件費
     */
    private final NotUseAtr laborCost;
    
    /**
     * 予算		
     */
    private final Optional<NotUseAtr> bugget;
	
}
