/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * The Class HolidayAddtionSet.
 */
@Getter
// 休暇加算時間設定
public class HolidayAddtionSet extends AggregateRoot implements SerializableWithOptional{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The refer actual work hours. */
	// 実績の就業時間帯を参照する
	private NotUseAtr referActualWorkHours;

	/** The work record. */
	// 勤務実績を参照
	private Optional<ReferWorkRecord> workRecord;
	
	/** The employee information. */
	// 社員情報を参照
	private Optional<ReferEmployeeInformation> employeeInformation;
	
	/** The time holiday addition. */
	/*時間休暇加算*/
	private List<TimeHolidayAdditionSet> timeHolidayAddition;
	
	/** The addition vacation set. */
	// 加算休暇設定
	private LeaveSetAdded additionVacationSet;
	
	private void writeObject(ObjectOutputStream stream){
		writeObjectWithOptional(stream);
	}
	
	private void readObject(ObjectInputStream stream){
		readObjectWithOptional(stream);
	}
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
	}
	
	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param workRecord the work record
	 * @param referActualWorkHours the refer actual work hours
	 * @param employeeInformation the employee information
	 * @param additionVacationSet the addition vacation set
	 * @param timeHolidayAddition the time holiday addition
	 * @return the holiday addtion set
	 */
	public static HolidayAddtionSet createFromJavaType(String companyId, ReferWorkRecord workRecord, NotUseAtr referActualWorkHours, 
														ReferEmployeeInformation employeeInformation, LeaveSetAdded additionVacationSet,
														List<TimeHolidayAdditionSet> timeHolidayAddition) {
		return new HolidayAddtionSet(companyId, 
				workRecord,
				referActualWorkHours,
				employeeInformation,
				additionVacationSet, 
				timeHolidayAddition);
	}

	/**
	 * Instantiates a new holiday addtion set.
	 *
	 * @param companyId the company id
	 * @param workrecord the workrecord
	 * @param referActualWorkHours the refer actual work hours
	 * @param employeeInformation the employee information
	 * @param additionVacationSet the addition vacation set
	 * @param timeHolidayAddition the time holiday addition
	 */
	public HolidayAddtionSet(String companyId, 
			ReferWorkRecord workrecord,
			NotUseAtr referActualWorkHours, 
			ReferEmployeeInformation employeeInformation,
			LeaveSetAdded additionVacationSet,
			List<TimeHolidayAdditionSet> timeHolidayAddition) {
		super();
		this.companyId = companyId;
		this.workRecord = Optional.of(workrecord);
		this.referActualWorkHours = referActualWorkHours;
		this.employeeInformation = Optional.of(employeeInformation);
		this.additionVacationSet = additionVacationSet;
		this.timeHolidayAddition = timeHolidayAddition;
	}
}
