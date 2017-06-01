package nts.uk.ctx.at.shared.infra.repository.worktime;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.TimeDayAtr;
import nts.uk.ctx.at.shared.dom.worktime.WorkMethodSetting;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeAbName;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDay;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeName;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeNightShift;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeSymbol;
import nts.uk.ctx.at.shared.infra.entity.worktime.KwtdtWorkTimeDay;
import nts.uk.ctx.at.shared.infra.entity.worktime.KwtmpWorkTimePK;
import nts.uk.ctx.at.shared.infra.entity.worktime.KwtmtWorkTime;
import nts.uk.ctx.at.shared.infra.entity.worktime.KwtstWorkTimeSet;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Stateless
public class JpaWorkTimeRepository extends JpaRepository implements WorkTimeRepository{

	private final String findByList = "SELECT a FROM KwtmtWorkTime a "
			+ "WHERE a.kwtmpWorkTimePK.companyID = :companyID "
			+ "AND a.kwtmpWorkTimePK.workTimeCD IN :workTimeCDs";
	
	@Override
	public Optional<WorkTime> findByCode(String companyID, String workTimeCD) {
		return this.queryProxy().find(new KwtmpWorkTimePK(companyID, workTimeCD), KwtmtWorkTime.class).map(x -> convertToDomainWorkTime(x));
	}

	@Override
	public List<WorkTime> findByCodeList(String companyID, List<String> workTimeCDs) {
		return this.queryProxy().query(findByList, KwtmtWorkTime.class)
			.setParameter("companyID", companyID).setParameter("workTimeCDs", workTimeCDs)
			.getList(x -> convertToDomainWorkTime(x));
	}
	
	/**
	 * convert Work Time entity object to Work Time domain object
	 * @param kwtmtWorkTime Work Time entity object
	 * @return Work Time domain object
	 */
	private WorkTime convertToDomainWorkTime(KwtmtWorkTime kwtmtWorkTime){
		return new WorkTime(
				kwtmtWorkTime.kwtmpWorkTimePK.companyID, 
				new WorkTimeCode(kwtmtWorkTime.kwtmpWorkTimePK.workTimeCD), 
				kwtmtWorkTime.sortBy, 
				new WorkTimeName(kwtmtWorkTime.name), 
				new WorkTimeAbName(kwtmtWorkTime.abName), 
				new WorkTimeSymbol(kwtmtWorkTime.symbol), 
				kwtmtWorkTime.remarks, 
				EnumAdaptor.valueOf(kwtmtWorkTime.displayAtr, UseSetting.class), 
				EnumAdaptor.valueOf(kwtmtWorkTime.methodAtr, WorkMethodSetting.class),
				//convertToDomainWorkTimeSet(kwtmtWorkTime.kwtstWorkTimeSet) 
				null
		);
	}
	
	/**
	 * convert Work Time Set entity object to Work Time Set domain object
	 * @param kwtstWorkTimeSet Work Time Set entity object
	 * @return Work Time Set domain object
	 */
	private WorkTimeSet convertToDomainWorkTimeSet(KwtstWorkTimeSet kwtstWorkTimeSet){
		return new WorkTimeSet(
				kwtstWorkTimeSet.kwtspWorkTimeSetPK.companyID, 
				kwtstWorkTimeSet.kwtspWorkTimeSetPK.workTimeSetCD, 
				kwtstWorkTimeSet.kwtspWorkTimeSetPK.workTimeCD, 
				kwtstWorkTimeSet.rangeTimeDay, 
				kwtstWorkTimeSet.additionSetCD, 
				EnumAdaptor.valueOf(kwtstWorkTimeSet.nightShiftAtr, WorkTimeNightShift.class), 
				kwtstWorkTimeSet.startDateTime, 
				EnumAdaptor.valueOf(kwtstWorkTimeSet.startDateAtr, TimeDayAtr.class), 
				null
				//convertToDomainWorkTimeDay(kwtstWorkTimeSet.kwtdtWorkTimeDay)
		);
	}
	
	/**
	 * convert Work Time Day entity object to Work Time Day domain object
	 * @param kwtdtWorkTimeDay Work Time Day entity object
	 * @return Work Time Day domain object
	 */
	private WorkTimeDay convertToDomainWorkTimeDay(KwtdtWorkTimeDay kwtdtWorkTimeDay){
		return new WorkTimeDay(
				kwtdtWorkTimeDay.kwtdpWorkTimeDayPK.companyID, 
				kwtdtWorkTimeDay.kwtdpWorkTimeDayPK.timeDayCD, 
				kwtdtWorkTimeDay.kwtdpWorkTimeDayPK.workTimeSetCD, 
				kwtdtWorkTimeDay.kwtdpWorkTimeDayPK.timeNumberCnt, 
				kwtdtWorkTimeDay.a_m_StartTime, 
				EnumAdaptor.valueOf(kwtdtWorkTimeDay.a_m_StartAtr, TimeDayAtr.class), 
				kwtdtWorkTimeDay.a_m_EndTime, 
				EnumAdaptor.valueOf(kwtdtWorkTimeDay.a_m_EndAtr, TimeDayAtr.class),
				EnumAdaptor.valueOf(kwtdtWorkTimeDay.a_m_UseAtr, UseSetting.class),
				kwtdtWorkTimeDay.p_m_StartTime, 
				EnumAdaptor.valueOf(kwtdtWorkTimeDay.p_m_StartAtr, TimeDayAtr.class),
				kwtdtWorkTimeDay.p_m_EndTime, 
				EnumAdaptor.valueOf(kwtdtWorkTimeDay.p_m_EndAtr, TimeDayAtr.class),
				EnumAdaptor.valueOf(kwtdtWorkTimeDay.p_m_Use_Atr, UseSetting.class)
		);
	}

}
