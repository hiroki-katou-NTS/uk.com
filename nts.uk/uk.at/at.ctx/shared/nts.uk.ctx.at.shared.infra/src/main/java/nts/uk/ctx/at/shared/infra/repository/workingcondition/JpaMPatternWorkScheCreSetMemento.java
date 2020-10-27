package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCreSetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondScheMeth;


/**
 * The Class JpaMonthlyPatternWorkScheduleCreSetMemento.
 */
public class JpaMPatternWorkScheCreSetMemento implements MonthlyPatternWorkScheduleCreSetMemento{

	/** The kshmt schedule method. */
	private KshmtWorkcondScheMeth kshmtWorkcondScheMeth;
	
	/**
	 * Instantiates a new jpa monthly pattern work schedule cre set memento.
	 *
	 * @param entity the entity
	 */
	public JpaMPatternWorkScheCreSetMemento(KshmtWorkcondScheMeth entity){
		this.kshmtWorkcondScheMeth = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCreSetMemento#setReferenceType(nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr)
	 */
//	2018/1/25
//	属性名変更：勤務種類と就業時間帯の参照先
//	　　　　　　→就業時間帯の参照先
//	※月間パターンによる勤務予定作成.就業時間帯の参照先と営業日カレンダーによる勤務予定作成.就業時間帯の参照先は、DB上では同じカラムを更新する
//	列名：REF_WORKING_HOURS
	@Override
	public void setReferenceType(TimeZoneScheduledMasterAtr referenceType) {
		if (referenceType != null) {
			this.kshmtWorkcondScheMeth.setRefWorkingHours(referenceType.value);
		}
	}
}
