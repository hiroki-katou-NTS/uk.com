package nts.uk.ctx.at.shared.infra.repository.worktimeset;

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

	private final String findWorkTimeSetByList = "SELECT a FROM KwtstWorkTimeSet a "
			+ "WHERE a.kwtspWorkTimeSetPK.companyID = :companyID "
			+ "AND a.kwtspWorkTimeSetPK.workTimeSetID IN :workTimeSetIDs";
	
	private final String findWorkTimeSetByStart = "SELECT DISTINCT a FROM KwtstWorkTimeSet a, KwtdtWorkTimeDay b "
			+ "WHERE a.kwtspWorkTimeSetPK.companyID = :companyID "
			+ "AND a.kwtspWorkTimeSetPK.workTimeSetID IN :workTimeSetIDs "
			+ "AND a.kwtspWorkTimeSetPK.companyID = b.kwtdpWorkTimeDayPK.companyID "
			+ "AND a.kwtspWorkTimeSetPK.workTimeSetID = b.kwtdpWorkTimeDayPK.workTimeSetID "
			+ "AND b.a_m_StartAtr = :a_m_StartAtr "
			+ "AND b.a_m_StartClock = :a_m_StartClock";
	
	private final String findWorkTimeSetByEnd = "SELECT DISTINCT a FROM KwtstWorkTimeSet a, KwtdtWorkTimeDay b "
			+ "WHERE a.kwtspWorkTimeSetPK.companyID = :companyID "
			+ "AND a.kwtspWorkTimeSetPK.workTimeSetID IN :workTimeSetIDs "
			+ "AND a.kwtspWorkTimeSetPK.companyID = b.kwtdpWorkTimeDayPK.companyID "
			+ "AND a.kwtspWorkTimeSetPK.workTimeSetID = b.kwtdpWorkTimeDayPK.workTimeSetID "
			+ "AND b.p_m_EndAtr = :p_m_EndAtr "
			+ "AND b.p_m_EndClock = :p_m_EndClock";
	
	private final String findWorkTimeSetByStartAndEnd = "SELECT DISTINCT a FROM KwtstWorkTimeSet a, KwtdtWorkTimeDay b "
			+ "WHERE a.kwtspWorkTimeSetPK.companyID = :companyID "
			+ "AND a.kwtspWorkTimeSetPK.workTimeSetID IN :workTimeSetIDs "
			+ "AND a.kwtspWorkTimeSetPK.companyID = b.kwtdpWorkTimeDayPK.companyID "
			+ "AND a.kwtspWorkTimeSetPK.workTimeSetID = b.kwtdpWorkTimeDayPK.workTimeSetID "
			+ "AND b.a_m_StartAtr = :a_m_StartAtr "
			+ "AND b.a_m_StartClock = :a_m_StartClock "
			+ "AND b.p_m_EndAtr = :p_m_EndAtr "
			+ "AND b.p_m_EndClock = :p_m_EndClock";
	
	@Override
	public Optional<WorkTimeSet> findByCode(String companyID, String workTimeSetID) {
		return this.queryProxy().find(new KwtspWorkTimeSetPK(companyID, workTimeSetID), KwtstWorkTimeSet.class)
				.map(x -> convertToDomainWorkTimeSet(x));
	}

	@Override
	public List<WorkTimeSet> findByCodeList(String companyID, List<String> workTimeSetIDs) {
		return this.queryProxy().query(findWorkTimeSetByList, KwtstWorkTimeSet.class)
				.setParameter("companyID", companyID)
				.setParameter("workTimeSetIDs", workTimeSetIDs)
				.getList(x -> convertToDomainWorkTimeSet(x));
	}

	@Override
	public List<WorkTimeSet> findByStart(String companyID, List<String> workTimeSetIDs, int startAtr, int startClock) {
		return this.queryProxy().query(findWorkTimeSetByStart, KwtstWorkTimeSet.class)
				.setParameter("companyID", companyID)
				.setParameter("workTimeSetIDs", workTimeSetIDs)
				.setParameter("a_m_StartAtr", startAtr)
				.setParameter("a_m_StartClock", startClock)
				.getList(x -> convertToDomainWorkTimeSet(x));
	}

	@Override
	public List<WorkTimeSet> findByEnd(String companyID, List<String> workTimeSetIDs, int endAtr, int endClock) {
		return this.queryProxy().query(findWorkTimeSetByEnd, KwtstWorkTimeSet.class)
				.setParameter("companyID", companyID)
				.setParameter("workTimeSetIDs", workTimeSetIDs)
				.setParameter("p_m_EndAtr", endAtr)
				.setParameter("p_m_EndClock", endClock)
				.getList(x -> convertToDomainWorkTimeSet(x));
	}

	@Override
	public List<WorkTimeSet> findByStartAndEnd(String companyID, List<String> workTimeSetIDs, int startAtr,
			int startClock, int endAtr, int endClock) {
		return this.queryProxy().query(findWorkTimeSetByStartAndEnd, KwtstWorkTimeSet.class)
				.setParameter("companyID", companyID)
				.setParameter("workTimeSetIDs", workTimeSetIDs)
				.setParameter("a_m_StartAtr", startAtr)
				.setParameter("a_m_StartClock", startClock)
				.setParameter("p_m_EndAtr", endAtr)
				.setParameter("p_m_EndClock", endClock)
				.getList(x -> convertToDomainWorkTimeSet(x));
	}
	
	/**
	 * convert Work Time Set entity object to Work Time Set domain object
	 * @param kwtstWorkTimeSet Work Time Set entity object
	 * @return Work Time Set domain object
	 */
	private WorkTimeSet convertToDomainWorkTimeSet(KwtstWorkTimeSet kwtstWorkTimeSet){
		return new WorkTimeSet(
				kwtstWorkTimeSet.kwtspWorkTimeSetPK.companyID, 
				kwtstWorkTimeSet.rangeTimeDay,
				kwtstWorkTimeSet.kwtspWorkTimeSetPK.workTimeSetID, 
				kwtstWorkTimeSet.additionSetID, 
				EnumAdaptor.valueOf(kwtstWorkTimeSet.nightShiftAtr, WorkTimeNightShift.class), 
				kwtstWorkTimeSet.kwtdtWorkTimeDay.stream().map(x -> convertToDomainWorkTimeDay(x)).collect(Collectors.toList()),
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
			kwtdtWorkTimeDay.kwtdpWorkTimeDayPK.timeDayID, 
			kwtdtWorkTimeDay.kwtdpWorkTimeDayPK.workTimeSetID, 
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
