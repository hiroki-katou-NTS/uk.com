package nts.uk.ctx.at.record.dom.calculationsetting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.ActualStampOfPriorityClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.AutoStampReflectionClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.BreakSwitchClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.GoBackOutCorrectionClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.ReflectWorkingTimeClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.AutoStampForFutureDayClass;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;

/**
 * 
 * @author nampt
 * 打刻反映管理 - root
 *
 */
@Getter
public class StampReflectionManagement extends AggregateRoot {

	private String companyId;
	
	private BreakSwitchClass breakSwitchClass;
	
	private AutoStampReflectionClass autoStampReflectionClass;
	
	private ActualStampOfPriorityClass actualStampOfPriorityClass;
	
	private ReflectWorkingTimeClass reflectWorkingTimeClass;
	
	private GoBackOutCorrectionClass goBackOutCorrectionClass;
	
	private UseAtr managementOfEntrance;
	
	private AutoStampForFutureDayClass autoStampForFutureDayClass;
}
