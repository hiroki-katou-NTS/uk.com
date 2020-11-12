package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class Personal schedule creation.<br>
 * Domain 個人スケジュール作成
 *
 * @author nws-minhnb
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonalScheduleCreation extends DomainObject {

	/**
	 * The Personal schedule creation period.<br>
	 * 作成期間
	 */
	private PersonalScheduleCreationPeriod perSchedulePeriod;

	/**
	 * The Personal schedule creation classification.<br>
	 * 個人スケジュール作成区分
	 */
	private NotUseAtr perScheduleCls;

//	/**
//	 * 対象社員
//	 */
//	private PersonalScheduleCreationTarget target;

	/**
	 * The Create new employee schedule.<br>
	 * 新入社員を作成
	 */
	private NotUseAtr createNewEmpSched;

	/**
	 * Instantiates a new <code>PersonalScheduleCreation</code>.
	 *
	 * @param perSchedulePeriod the personal schedule creation period
	 * @param perScheduleCls    the personal schedule creation classification
	 * @param createNewEmpSched the create new employee schedule
	 */
	public PersonalScheduleCreation(PersonalScheduleCreationPeriod perSchedulePeriod, int perScheduleCls, int createNewEmpSched) {
		this.perSchedulePeriod = perSchedulePeriod;
		this.perScheduleCls = EnumAdaptor.valueOf(perScheduleCls, NotUseAtr.class);
		this.createNewEmpSched = EnumAdaptor.valueOf(createNewEmpSched, NotUseAtr.class);
	}

}
