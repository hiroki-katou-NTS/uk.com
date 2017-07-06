package nts.uk.ctx.at.shared.infra.repository.worktimeset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.worktimeset.TimeDayAtr;
import nts.uk.ctx.at.shared.dom.worktimeset.WorkTimeDay;
import nts.uk.ctx.at.shared.dom.worktimeset.WorkTimeNightShift;
import nts.uk.ctx.at.shared.dom.worktimeset.WorkTimeSet;
import nts.uk.ctx.at.shared.dom.worktimeset.WorkTimeSetRepository;
import nts.uk.ctx.at.shared.infra.entity.worktimeset.KwtdtWorkTimeDay;
import nts.uk.ctx.at.shared.infra.entity.worktimeset.KwtspWorkTimeSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktimeset.KwtstWorkTimeSet;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Stateless
public class JpaWorkTimeSetRepository extends JpaRepository implements WorkTimeSetRepository{
	
	private final String findWorkTimeSetByCompanyID = "SELECT DISTINCT a FROM KwtstWorkTimeSet a JOIN FETCH a.kwtdtWorkTimeDay b "
			+ "WHERE a.kwtspWorkTimeSetPK.companyID = :companyID";
	
	private final String findWorkTimeSetByList = "SELECT DISTINCT a FROM KwtstWorkTimeSet a JOIN FETCH a.kwtdtWorkTimeDay b "
			+ "WHERE a.kwtspWorkTimeSetPK.companyID = :companyID "
			+ "AND a.kwtspWorkTimeSetPK.siftCD IN :siftCDs";
	
	private final String findWorkTimeSetByStart = "SELECT DISTINCT a FROM KwtstWorkTimeSet a JOIN FETCH a.kwtdtWorkTimeDay b "
			+ "WHERE a.kwtspWorkTimeSetPK.companyID = :companyID "
			+ "AND a.kwtspWorkTimeSetPK.companyID = b.kwtdpWorkTimeDayPK.companyID "
			+ "AND a.kwtspWorkTimeSetPK.siftCD = b.kwtdpWorkTimeDayPK.siftCD "
			+ "AND b.a_m_StartAtr = :a_m_StartAtr "
			+ "AND b.a_m_StartClock = :a_m_StartClock "
			+ "AND a.kwtspWorkTimeSetPK.siftCD IN :siftCDs ";
	
	private final String findWorkTimeSetByEnd = "SELECT DISTINCT a FROM KwtstWorkTimeSet a JOIN FETCH a.kwtdtWorkTimeDay b "
			+ "WHERE a.kwtspWorkTimeSetPK.companyID = :companyID "
			+ "AND a.kwtspWorkTimeSetPK.companyID = b.kwtdpWorkTimeDayPK.companyID "
			+ "AND a.kwtspWorkTimeSetPK.siftCD = b.kwtdpWorkTimeDayPK.siftCD "
			+ "AND b.p_m_EndAtr = :p_m_EndAtr "
			+ "AND b.p_m_EndClock = :p_m_EndClock "
			+ "AND a.kwtspWorkTimeSetPK.siftCD IN :siftCDs ";
	
	private final String findWorkTimeSetByStartAndEnd = "SELECT DISTINCT a FROM KwtstWorkTimeSet a JOIN FETCH a.kwtdtWorkTimeDay b "
			+ "WHERE a.kwtspWorkTimeSetPK.companyID = :companyID "
			+ "AND a.kwtspWorkTimeSetPK.companyID = b.kwtdpWorkTimeDayPK.companyID "
			+ "AND a.kwtspWorkTimeSetPK.siftCD = b.kwtdpWorkTimeDayPK.siftCD "
			+ "AND b.a_m_StartAtr = :a_m_StartAtr "
			+ "AND b.a_m_StartClock = :a_m_StartClock "
			+ "AND b.p_m_EndAtr = :p_m_EndAtr "
			+ "AND b.p_m_EndClock = :p_m_EndClock "
			+ "AND a.kwtspWorkTimeSetPK.siftCD IN :siftCDs ";
	
	@Override
	public List<WorkTimeSet> findByCompanyID(String companyID) {
		return this.queryProxy().query(findWorkTimeSetByCompanyID, KwtstWorkTimeSet.class).setParameter("companyID", companyID).getList(x -> convertToDomainWorkTimeSet(x));
	}
	
	@Override
	public Optional<WorkTimeSet> findByCode(String companyID, String siftCD) {
		return this.queryProxy().find(new KwtspWorkTimeSetPK(companyID, siftCD), KwtstWorkTimeSet.class)
				.map(x -> convertToDomainWorkTimeSet(x));
	}

	@Override
	public List<WorkTimeSet> findByCodeList(String companyID, List<String> siftCDs) {
		List<WorkTimeSet> result = new ArrayList<WorkTimeSet>();
		int i = 0;
		while(siftCDs.size()-(i+500)>0) {
			List<String> subCodelist = siftCDs.subList(i, i+500);
			List<WorkTimeSet> subResult = this.queryProxy().query(findWorkTimeSetByList, KwtstWorkTimeSet.class)
					.setParameter("companyID", companyID).setParameter("siftCDs", subCodelist)
					.getList(x -> convertToDomainWorkTimeSet(x));
			result.addAll(subResult);
			i+=500;
		}
		List<WorkTimeSet> lastResult = this.queryProxy().query(findWorkTimeSetByList, KwtstWorkTimeSet.class)
				.setParameter("companyID", companyID).setParameter("siftCDs", siftCDs.subList(i, siftCDs.size()))
				.getList(x -> convertToDomainWorkTimeSet(x));
		result.addAll(lastResult);
		return result;
	}

	@Override
	public List<WorkTimeSet> findByStart(String companyID, List<String> siftCDs, int startAtr, int startClock) {
		List<WorkTimeSet> result = new ArrayList<WorkTimeSet>();
		int i = 0;
		while(siftCDs.size()-(i+500)>0) {
			List<String> subCodelist = siftCDs.subList(i, i+500);
			List<WorkTimeSet> subResult = this.queryProxy().query(findWorkTimeSetByStart, KwtstWorkTimeSet.class)
					.setParameter("companyID", companyID).setParameter("siftCDs", subCodelist)
					.setParameter("a_m_StartAtr", startAtr).setParameter("a_m_StartClock", startClock)
					.getList(x -> convertToDomainWorkTimeSet(x));
			result.addAll(subResult);
			i+=500;
		}
		List<WorkTimeSet> lastResult = this.queryProxy().query(findWorkTimeSetByStart, KwtstWorkTimeSet.class)
				.setParameter("companyID", companyID).setParameter("siftCDs", siftCDs.subList(i, siftCDs.size()))
				.setParameter("a_m_StartAtr", startAtr).setParameter("a_m_StartClock", startClock)
				.getList(x -> convertToDomainWorkTimeSet(x));
		result.addAll(lastResult);
		return result;
	}

	@Override
	public List<WorkTimeSet> findByEnd(String companyID, List<String> siftCDs, int endAtr, int endClock) {
		List<WorkTimeSet> result = new ArrayList<WorkTimeSet>();
		int i = 0;
		while(siftCDs.size()-(i+500)>0) {
			List<String> subCodelist = siftCDs.subList(i, i+500);
			List<WorkTimeSet> subResult = this.queryProxy().query(findWorkTimeSetByEnd, KwtstWorkTimeSet.class)
					.setParameter("companyID", companyID).setParameter("siftCDs", subCodelist)
					.setParameter("p_m_EndAtr", endAtr).setParameter("p_m_EndClock", endClock)
					.getList(x -> convertToDomainWorkTimeSet(x));
			result.addAll(subResult);
			i+=500;
		}
		List<WorkTimeSet> lastResult = this.queryProxy().query(findWorkTimeSetByEnd, KwtstWorkTimeSet.class)
				.setParameter("companyID", companyID).setParameter("siftCDs", siftCDs.subList(i, siftCDs.size()))
				.setParameter("p_m_EndAtr", endAtr).setParameter("p_m_EndClock", endClock)
				.getList(x -> convertToDomainWorkTimeSet(x));
		result.addAll(lastResult);
		return result;
	}

	@Override
	public List<WorkTimeSet> findByStartAndEnd(String companyID, List<String> siftCDs, int startAtr, int startClock, int endAtr, int endClock) {
		List<WorkTimeSet> result = new ArrayList<WorkTimeSet>();
		int i = 0;
		while(siftCDs.size()-(i+500)>0) {
			List<String> subCodelist = siftCDs.subList(i, i+500);
			List<WorkTimeSet> subResult = this.queryProxy().query(findWorkTimeSetByStartAndEnd, KwtstWorkTimeSet.class)
					.setParameter("companyID", companyID).setParameter("siftCDs", subCodelist)
					.setParameter("a_m_StartAtr", startAtr).setParameter("a_m_StartClock", startClock)
					.setParameter("p_m_EndAtr", endAtr).setParameter("p_m_EndClock", endClock)
					.getList(x -> convertToDomainWorkTimeSet(x));
			result.addAll(subResult);
			i+=500;
		}
		List<WorkTimeSet> lastResult = this.queryProxy().query(findWorkTimeSetByStartAndEnd, KwtstWorkTimeSet.class)
				.setParameter("companyID", companyID).setParameter("siftCDs", siftCDs.subList(i, siftCDs.size()))
				.setParameter("a_m_StartAtr", startAtr).setParameter("a_m_StartClock", startClock)
				.setParameter("p_m_EndAtr", endAtr).setParameter("p_m_EndClock", endClock)
				.getList(x -> convertToDomainWorkTimeSet(x));
		result.addAll(lastResult);
		return result;
	}
	
	/**
	 * convert Work Time Set entity object to Work Time Set domain object
	 * @param kwtstWorkTimeSet Work Time Set entity object
	 * @return Work Time Set domain object
	 */
	private WorkTimeSet convertToDomainWorkTimeSet(KwtstWorkTimeSet kwtstWorkTimeSet){
		WorkTimeDay workTimeDay1 = null;
		WorkTimeDay workTimeDay2 = null;
		List<WorkTimeDay> workTimeDays = kwtstWorkTimeSet.kwtdtWorkTimeDay.stream().map(x -> convertToDomainWorkTimeDay(x)).collect(Collectors.toList());
		if(workTimeDays.size()>=2){
			workTimeDay1 = workTimeDays.get(0);
			workTimeDay2 = workTimeDays.get(1);
		} else if(workTimeDays.size()>=1){
			workTimeDay1 = workTimeDays.get(0);
		}
		return new WorkTimeSet(
				kwtstWorkTimeSet.kwtspWorkTimeSetPK.companyID, 
				kwtstWorkTimeSet.rangeTimeDay,
				kwtstWorkTimeSet.kwtspWorkTimeSetPK.siftCD, 
				kwtstWorkTimeSet.additionSetID, 
				EnumAdaptor.valueOf(kwtstWorkTimeSet.nightShiftAtr, WorkTimeNightShift.class), 
				workTimeDay1,
				workTimeDay2,
				kwtstWorkTimeSet.startDateClock, 
				kwtstWorkTimeSet.predetermineAtr
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
			kwtdtWorkTimeDay.kwtdpWorkTimeDayPK.siftCD, 
			kwtdtWorkTimeDay.kwtdpWorkTimeDayPK.timeNumberCnt, 
			EnumAdaptor.valueOf(kwtdtWorkTimeDay.useAtr, UseSetting.class),
			kwtdtWorkTimeDay.a_m_StartClock, 
			EnumAdaptor.valueOf(kwtdtWorkTimeDay.a_m_StartAtr, TimeDayAtr.class), 
			kwtdtWorkTimeDay.a_m_EndClock, 
			EnumAdaptor.valueOf(kwtdtWorkTimeDay.a_m_EndAtr, TimeDayAtr.class),
			kwtdtWorkTimeDay.p_m_StartClock, 
			EnumAdaptor.valueOf(kwtdtWorkTimeDay.p_m_StartAtr, TimeDayAtr.class),
			kwtdtWorkTimeDay.p_m_EndClock, 
			EnumAdaptor.valueOf(kwtdtWorkTimeDay.p_m_EndAtr, TimeDayAtr.class)
		);
	}
}
