package nts.uk.ctx.at.record.dom.calculationsetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.ActualStampOfPriorityClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.AutoStampForFutureDayClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.AutoStampReflectionClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.BreakSwitchClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.GoBackOutCorrectionClass;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.ReflectWorkingTimeClass;

/**
 * 
 * @author nampt 打刻反映管理 - root
 *
 */
@Getter
public class StampReflectionManagement extends AggregateRoot {

	/*会社ID*/
	private String companyId;

	/*打刻反映管理     休出切替区分*/
	private BreakSwitchClass breakSwitchClass;

	/*打刻反映管理      自動打刻反映区分*/
	private AutoStampReflectionClass autoStampReflectionClass;

	/*打刻反映管理      実打刻と申請の優先区分*/
	private ActualStampOfPriorityClass actualStampOfPriorityClass;

	/*打刻反映管理      就業時間帯反映区分*/
	private ReflectWorkingTimeClass reflectWorkingTimeClass;

	/*打刻反映管理       外出補正区分*/
	private GoBackOutCorrectionClass goBackOutCorrectionClass;

	/*打刻反映管理       未来日区分*/
	private AutoStampForFutureDayClass autoStampForFutureDayClass;
	

	public static StampReflectionManagement createJavaType(String companyId, int breakSwitchClass,
			int autoStampReflectionClass, int actualStampOfPriorityClass,
			int reflectWorkingTimeClass, int goBackOutCorrectionClass,
			int autoStampForFutureDayClass
			){
		return new StampReflectionManagement(companyId,
				EnumAdaptor.valueOf(breakSwitchClass, BreakSwitchClass.class),
				EnumAdaptor.valueOf(autoStampReflectionClass, AutoStampReflectionClass.class),
				EnumAdaptor.valueOf(actualStampOfPriorityClass, ActualStampOfPriorityClass.class),
				EnumAdaptor.valueOf(reflectWorkingTimeClass, ReflectWorkingTimeClass.class),
				EnumAdaptor.valueOf(goBackOutCorrectionClass, GoBackOutCorrectionClass.class),
				EnumAdaptor.valueOf(autoStampForFutureDayClass, AutoStampForFutureDayClass.class)
				);
	}

	public StampReflectionManagement(String companyId, BreakSwitchClass breakSwitchClass,
			AutoStampReflectionClass autoStampReflectionClass, ActualStampOfPriorityClass actualStampOfPriorityClass,
			ReflectWorkingTimeClass reflectWorkingTimeClass, GoBackOutCorrectionClass goBackOutCorrectionClass,
			AutoStampForFutureDayClass autoStampForFutureDayClass
			) {
		super();
		this.companyId = companyId;
		this.breakSwitchClass = breakSwitchClass;
		this.autoStampReflectionClass = autoStampReflectionClass;
		this.actualStampOfPriorityClass = actualStampOfPriorityClass;
		this.reflectWorkingTimeClass = reflectWorkingTimeClass;
		this.goBackOutCorrectionClass = goBackOutCorrectionClass;
		this.autoStampForFutureDayClass = autoStampForFutureDayClass;
	}
	
}
