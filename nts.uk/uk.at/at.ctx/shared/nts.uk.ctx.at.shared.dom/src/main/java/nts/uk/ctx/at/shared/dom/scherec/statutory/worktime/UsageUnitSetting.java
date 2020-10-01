/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime;


import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class UsageUnitSetting.
 */
// 労働時間と日数の設定の利用単位の設定
@Getter
public class UsageUnitSetting extends AggregateRoot {
	
	/** The company id. */
	///** 会社ID. */
	private CompanyId companyId;

	/** The employee. */
	// 社員の労働時間と日数の管理をする
	private boolean employee;

	/** The work place. */
	// 職場の労働時間と日数の管理をする
	private boolean workPlace;

	/** The employment. */
	// 雇用の労働時間と日数の管理をする
	private boolean employment;

	/**
	 * Instantiates a new usage unit setting.
	 *
	 * @param memento the memento
	 */
	public UsageUnitSetting(UsageUnitSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employee = memento.getEmployee();
		this.workPlace = memento.getWorkPlace();
		this.employment = memento.getEmployment();
	}
	
	//テスト用に作成
	public UsageUnitSetting(CompanyId companyId,boolean employee,boolean workPlace,boolean employment) {
		this.companyId = companyId;
		this.employee = employee;
		this.workPlace = workPlace;
		this.employment = employment;
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(UsageUnitSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployee(this.employee);
		memento.setWorkPlace(this.workPlace);
		memento.setEmployment(this.employment);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsageUnitSetting other = (UsageUnitSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}
	
	
//	/**
//	 * 日の法定労働時間を取得（通常、変形用）
//	 * @param workingSystem
//	 * @param shainRegularLaborTime
//	 * @param shainTransLaborTime
//	 * @param wkpRegularLaborTime
//	 * @param wkpTransLaborTime
//	 * @param empRegularLaborTime
//	 * @param empTransLaborTime
//	 * @param comRegularLaborTime
//	 * @param comTransLaborTime
//	 * @return
//	 */
//	public DailyUnit getDailyUnit(WorkingSystem workingSystem,
//								  Optional<ShainRegularLaborTime> shainRegularLaborTime,
//								  Optional<ShainTransLaborTime> shainTransLaborTime,
//								  Optional<WkpRegularLaborTime> wkpRegularLaborTime,
//								  Optional<WkpTransLaborTime> wkpTransLaborTime,
//								  Optional<EmpRegularLaborTime> empRegularLaborTime,
//								  Optional<EmpTransLaborTime> empTransLaborTime,
//								  Optional<ComRegularLaborTime> comRegularLaborTime,
//								  Optional<ComTransLaborTime> comTransLaborTime) {
//		
//		DailyUnit result = new DailyUnit(new TimeOfDay(0));
//		if(workingSystem.isRegularWork()||workingSystem.isVariableWorkingTimeWork()) {
//			//取得する単位を取得
//			Optional<WorkingTimeSetting> workingTimeSetting = getWorkingTimeSetting(workingSystem,
//																					shainRegularLaborTime,
//																					shainTransLaborTime,
//																					wkpRegularLaborTime,
//																					wkpTransLaborTime,
//																					empRegularLaborTime,
//																					empTransLaborTime,
//																					comRegularLaborTime,
//																					comTransLaborTime);
//			if(workingTimeSetting.isPresent()) {
//				DailyUnit calced = workingTimeSetting.get().getDailyTime();
//				return calced.getDailyTime()!=null?calced:result;
//			}
//		}
//		return result;
//	}
//	
//	
//	/**
//	 * 取得する単位を取得
//	 * @param workingSystem
//	 * @param shainRegularLaborTime
//	 * @param shainTransLaborTime
//	 * @param wkpRegularLaborTime
//	 * @param wkpTransLaborTime
//	 * @param empRegularLaborTime
//	 * @param empTransLaborTime
//	 * @param comRegularLaborTime
//	 * @param comTransLaborTime
//	 * @return
//	 */
//	public Optional<WorkingTimeSetting> getWorkingTimeSetting(WorkingSystem workingSystem,
//													Optional<ShainRegularLaborTime> shainRegularLaborTime,
//													Optional<ShainTransLaborTime> shainTransLaborTime,
//													Optional<WkpRegularLaborTime> wkpRegularLaborTime,
//													Optional<WkpTransLaborTime> wkpTransLaborTime,
//													Optional<EmpRegularLaborTime> empRegularLaborTime,
//													Optional<EmpTransLaborTime> empTransLaborTime,
//													Optional<ComRegularLaborTime> comRegularLaborTime,
//													Optional<ComTransLaborTime> comTransLaborTime
//													) {
//		WorkingTimeSetting result = null;
//		if(this.employee) {//社員の労働時間を管理する場合
//			result = getShainWorkingTimeSetting(workingSystem,shainRegularLaborTime,shainTransLaborTime);
//			if(result==null) {//取得できなかった場合　職場の設定を取得
//				result = getWkpWorkingTimeSetting(workingSystem,wkpRegularLaborTime,wkpTransLaborTime);
//				if(result==null) {//取得できない場合　雇用別設定の取得
//					result = getEmpWorkingTimeSetting(workingSystem,empRegularLaborTime,empTransLaborTime);
//					if(result==null) {//取得できない場合　会社別設定の取得
//						result = getComWorkingTimeSetting(workingSystem,comRegularLaborTime,comTransLaborTime);
//					}
//				}
//			}
//			return Optional.ofNullable(result);
//		}
//		if(this.workPlace) {
//			result = getWkpWorkingTimeSetting(workingSystem,wkpRegularLaborTime,wkpTransLaborTime);
//			if(result==null) {//取得できない場合　雇用別設定の取得
//				result = getEmpWorkingTimeSetting(workingSystem,empRegularLaborTime,empTransLaborTime);
//				if(result==null) {//取得できない場合　会社別設定の取得
//					result = getComWorkingTimeSetting(workingSystem,comRegularLaborTime,comTransLaborTime);
//				}
//			}
//			return Optional.ofNullable(result);
//		}
//		if(this.employment) {
//			result = getEmpWorkingTimeSetting(workingSystem,empRegularLaborTime,empTransLaborTime);
//			if(result==null) {//取得できない場合　会社別設定の取得
//				result = getComWorkingTimeSetting(workingSystem,comRegularLaborTime,comTransLaborTime);
//			}
//			return Optional.ofNullable(result);
//		}
//		result = getComWorkingTimeSetting(workingSystem,comRegularLaborTime,comTransLaborTime);
//		return Optional.ofNullable(result);
//	}
//	
//	
//	/**
//	 * 社員別設定を取得
//	 * @param workingSystem
//	 * @param shainRegularLaborTime
//	 * @param shainTransLaborTime
//	 * @return
//	 */
//	private WorkingTimeSetting getShainWorkingTimeSetting(WorkingSystem workingSystem,
//														  Optional<ShainRegularLaborTime> shainRegularLaborTime,
//														  Optional<ShainTransLaborTime> shainTransLaborTime) {
//		if(workingSystem.isRegularWork()) {//通常勤務　の場合
//			return shainRegularLaborTime.isPresent()?shainRegularLaborTime.get().getWorkingTimeSet():null;
//		}else if(workingSystem.isVariableWorkingTimeWork()) {//変形労働勤務　の場合
//			return shainTransLaborTime.isPresent()?shainTransLaborTime.get().getWorkingTimeSet():null;
//		}
//		return null;
//	}
//	
//	
//	/**
//	 * 職場の設定を取得
//	 * @param workingSystem
//	 * @param wkpRegularLaborTime
//	 * @param wkpTransLaborTime
//	 * @return
//	 */
//	private WorkingTimeSetting getWkpWorkingTimeSetting(WorkingSystem workingSystem,
//														Optional<WkpRegularLaborTime> wkpRegularLaborTime,
//														Optional<WkpTransLaborTime> wkpTransLaborTime) {
//		if(workingSystem.isRegularWork()) {//通常勤務　の場合
//			return wkpRegularLaborTime.isPresent()?wkpRegularLaborTime.get().getWorkingTimeSet():null;
//		}else if(workingSystem.isVariableWorkingTimeWork()) {//変形労働勤務　の場合
//			return wkpTransLaborTime.isPresent()?wkpTransLaborTime.get().getWorkingTimeSet():null;
//		}
//		return null;
//	}
//	
//	
//	/**
//	 * 雇用別設定の取得
//	 * @param workingSystem
//	 * @param empRegularLaborTime
//	 * @param empTransLaborTime
//	 * @return
//	 */
//	private WorkingTimeSetting getEmpWorkingTimeSetting(WorkingSystem workingSystem,
//														Optional<EmpRegularLaborTime> empRegularLaborTime,
//														Optional<EmpTransLaborTime> empTransLaborTime) {
//		if(workingSystem.isRegularWork()) {//通常勤務　の場合
//			return empRegularLaborTime.isPresent()?empRegularLaborTime.get().getWorkingTimeSet():null;
//		}else if(workingSystem.isVariableWorkingTimeWork()) {//変形労働勤務　の場合
//			return empTransLaborTime.isPresent()?empTransLaborTime.get().getWorkingTimeSet():null;
//		}
//		return null;
//	}
//	
//	
//	/**
//	 * 会社別設定の取得
//	 * @param workingSystem
//	 * @param comRegularLaborTime
//	 * @param comTransLaborTime
//	 * @return
//	 */
//	private WorkingTimeSetting getComWorkingTimeSetting(WorkingSystem workingSystem,
//														Optional<ComRegularLaborTime> comRegularLaborTime,
//														Optional<ComTransLaborTime> comTransLaborTime) {
//		if(workingSystem.isRegularWork()) {//通常勤務　の場合
//			return comRegularLaborTime.isPresent()?comRegularLaborTime.get().getWorkingTimeSet():null;
//		}else if(workingSystem.isVariableWorkingTimeWork()) {//変形労働勤務　の場合
//			return comTransLaborTime.isPresent()?comTransLaborTime.get().getWorkingTimeSet():null;
//		}
//		return null;
//	}
	

}
