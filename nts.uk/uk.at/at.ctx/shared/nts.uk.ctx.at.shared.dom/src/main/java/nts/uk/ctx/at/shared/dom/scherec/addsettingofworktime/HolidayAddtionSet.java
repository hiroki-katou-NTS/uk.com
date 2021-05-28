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
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class HolidayAddtionSet.
 */
@Getter
@NoArgsConstructor
// 休暇加算時間設定
public class HolidayAddtionSet extends AggregateRoot implements SerializableWithOptional {

    /**
     * Serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * The company id.
     */
    // 会社ID
    private String companyId;

    /**
     * The addition vacation set.
     */
    // 加算休暇設定
    private LeaveSetAdded additionVacationSet;

    // Refactor code Q&A 114177
    // 参照先
    private RefDesForAdditionalTakeLeave reference;

    /**
     * The time holiday addition.
     */
    /*時間休暇加算*/
    private List<TimeHolidayAdditionSet> timeHolidayAddition;

    private void writeObject(ObjectOutputStream stream) {
        writeObjectWithOptional(stream);
    }

    private void readObject(ObjectInputStream stream) {
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
     * @param companyId            the company id
     * @param reference            the reference
     * @param additionVacationSet  the addition vacation set
     * @param timeHolidayAddition  the time holiday addition
     * @return the holiday addtion set
     */
    public static HolidayAddtionSet createFromJavaType(String companyId, RefDesForAdditionalTakeLeave reference, LeaveSetAdded additionVacationSet,
                                                       List<TimeHolidayAdditionSet> timeHolidayAddition) {
        return new HolidayAddtionSet(companyId,
                reference,
                additionVacationSet,
                timeHolidayAddition);
    }

    /**
     * Instantiates a new holiday addtion set.
     *
     * @param companyId                              the company id
     * @param reference                              the reference
     * @param additionVacationSet                    the addition vacation set
     * @param timeHolidayAddition                    the time holiday addition
     */
    public HolidayAddtionSet(String companyId,
                             RefDesForAdditionalTakeLeave reference,
                             LeaveSetAdded additionVacationSet,
                             List<TimeHolidayAdditionSet> timeHolidayAddition) {
        super();
        this.companyId = companyId;
        this.additionVacationSet = additionVacationSet;
        this.timeHolidayAddition = timeHolidayAddition;
        this.reference = reference;
    }

	/**
	 * 時間休暇加算時間を取得する
	 * @param daily 時間休暇使用時間
	 * @param time 相殺対象時間
	 * @return 時間休暇加算時間
	 */
	public AttendanceTime getAddTime(TimevacationUseTimeOfDaily daily, AttendanceTime time) {
		//加算使用時間の計算
		AttendanceTime dailyTime = new AttendanceTime(daily.calcTotalVacationAddTime(Optional.of(this), AdditionAtr.WorkingHoursOnly));
		
		Optional<TimeHolidayAdditionSet> flowSet = this.getTimeHolidayAddition().stream()
				.filter(t -> t.getWorkClass() == WorkClassOfTimeHolidaySet.WORK_FOR_FLOW)
				.findFirst();
		if(!flowSet.isPresent()) {
			return dailyTime;
		}
		
		//加算する時間を判断する
		return flowSet.get().getAddTime(dailyTime, time);
	}
}
