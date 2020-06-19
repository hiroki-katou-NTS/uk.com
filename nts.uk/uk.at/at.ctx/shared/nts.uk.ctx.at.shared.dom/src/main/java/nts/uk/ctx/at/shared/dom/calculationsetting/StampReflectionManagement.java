/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculationsetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class StampReflectionManagement.
 * 打刻反映管理
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
	

	/**
	 * Creates the java type.
	 *
	 * @param companyId the company id
	 * @param breakSwitchClass the break switch class
	 * @param autoStampReflectionClass the auto stamp reflection class
	 * @param actualStampOfPriorityClass the actual stamp of priority class
	 * @param reflectWorkingTimeClass the reflect working time class
	 * @param goBackOutCorrectionClass the go back out correction class
	 * @param autoStampForFutureDayClass the auto stamp for future day class
	 * @return the stamp reflection management
	 */
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

	/**
	 * Instantiates a new stamp reflection management.
	 *
	 * @param companyId the company id
	 * @param breakSwitchClass the break switch class
	 * @param autoStampReflectionClass the auto stamp reflection class
	 * @param actualStampOfPriorityClass the actual stamp of priority class
	 * @param reflectWorkingTimeClass the reflect working time class
	 * @param goBackOutCorrectionClass the go back out correction class
	 * @param autoStampForFutureDayClass the auto stamp for future day class
	 */
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
