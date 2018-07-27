package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCreGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtScheduleMethod;


/**
 * The Class JpaMonthlyPatternWorkScheduleCreGetMemento.
 */
public class JpaMPatternWorkScheCreGetMemento implements MonthlyPatternWorkScheduleCreGetMemento{
	
	/** The kshmt schedule method. */
	private KshmtScheduleMethod kshmtScheduleMethod;
	
	/**
	 * Instantiates a new jpa monthly pattern work schedule cre get memento.
	 *
	 * @param entity the entity
	 */
	public JpaMPatternWorkScheCreGetMemento(KshmtScheduleMethod entity){
		this.kshmtScheduleMethod = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCreGetMemento#getReferenceType()
	 */
//	2018/1/25
//	属性名変更：勤務種類と就業時間帯の参照先
//	　　　　　　→就業時間帯の参照先
//	※月間パターンによる勤務予定作成.就業時間帯の参照先と営業日カレンダーによる勤務予定作成.就業時間帯の参照先は、DB上では同じカラムを更新する
//	列名：REF_WORKING_HOURS
	@Override
	public TimeZoneScheduledMasterAtr getReferenceType() {
		return TimeZoneScheduledMasterAtr.valueOf(this.kshmtScheduleMethod.getRefWorkingHours());
	}
}
