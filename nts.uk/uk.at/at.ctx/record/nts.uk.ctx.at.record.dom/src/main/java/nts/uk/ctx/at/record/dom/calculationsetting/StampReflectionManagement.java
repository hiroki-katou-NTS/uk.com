package nts.uk.ctx.at.record.dom.calculationsetting;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.ActualStampOfPriorityClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.AutoStampForFutureDayClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.AutoStampReflectionClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.BreakSwitchClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.GoBackOutCorrectionClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.ReflectWorkingTimeClass;
import nts.uk.ctx.at.record.dom.stamp.GoOutReason;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;

/**
 * 
 * @author nampt 打刻反映管理 - root
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
	
	private GoOutReason outingAtr;
	
	private BigDecimal maxUseCount;



	public static StampReflectionManagement createJavaType(String companyId, int breakSwitchClass,
			int autoStampReflectionClass, int actualStampOfPriorityClass,
			int reflectWorkingTimeClass, int goBackOutCorrectionClass,
			int managementOfEntrance, int autoStampForFutureDayClass, int outingAtr, BigDecimal maxUseCount){
		return new StampReflectionManagement(companyId,
				EnumAdaptor.valueOf(breakSwitchClass, BreakSwitchClass.class),
				EnumAdaptor.valueOf(autoStampReflectionClass, AutoStampReflectionClass.class),
				EnumAdaptor.valueOf(actualStampOfPriorityClass, ActualStampOfPriorityClass.class),
				EnumAdaptor.valueOf(reflectWorkingTimeClass, ReflectWorkingTimeClass.class),
				EnumAdaptor.valueOf(goBackOutCorrectionClass, GoBackOutCorrectionClass.class),
				EnumAdaptor.valueOf(managementOfEntrance, UseAtr.class),
				EnumAdaptor.valueOf(autoStampForFutureDayClass, AutoStampForFutureDayClass.class),
				EnumAdaptor.valueOf(outingAtr, GoOutReason.class), maxUseCount);
	}



	public StampReflectionManagement(String companyId, BreakSwitchClass breakSwitchClass,
			AutoStampReflectionClass autoStampReflectionClass, ActualStampOfPriorityClass actualStampOfPriorityClass,
			ReflectWorkingTimeClass reflectWorkingTimeClass, GoBackOutCorrectionClass goBackOutCorrectionClass,
			UseAtr managementOfEntrance, AutoStampForFutureDayClass autoStampForFutureDayClass, GoOutReason outingAtr,
			BigDecimal maxUseCount) {
		super();
		this.companyId = companyId;
		this.breakSwitchClass = breakSwitchClass;
		this.autoStampReflectionClass = autoStampReflectionClass;
		this.actualStampOfPriorityClass = actualStampOfPriorityClass;
		this.reflectWorkingTimeClass = reflectWorkingTimeClass;
		this.goBackOutCorrectionClass = goBackOutCorrectionClass;
		this.managementOfEntrance = managementOfEntrance;
		this.autoStampForFutureDayClass = autoStampForFutureDayClass;
		this.outingAtr = outingAtr;
		this.maxUseCount = maxUseCount;
	}
	
}
