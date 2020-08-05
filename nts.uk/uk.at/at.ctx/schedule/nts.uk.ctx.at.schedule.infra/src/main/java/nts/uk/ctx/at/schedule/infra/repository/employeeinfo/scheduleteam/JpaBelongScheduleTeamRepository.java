package nts.uk.ctx.at.schedule.infra.repository.employeeinfo.scheduleteam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import com.aspose.pdf.Collection;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.scheduleteam.KscmtAffScheduleTeam;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.scheduleteam.KscmtAffScheduleTeamPk;

/**
 * 所属スケジュールチームRepository			
 * @author HieuLt
 *
 */
@Stateless
public class JpaBelongScheduleTeamRepository extends JpaRepository implements BelongScheduleTeamRepository{

	private static final String SELECT = " SELECT c FROM KscmtAffScheduleTeam c ";
	
	private static final String GET_BY_CID = SELECT +  " WHERE c.pk.CID = :CID " ;

	private static final String GET_BY_KEY = GET_BY_CID +" AND c.pk.SID = :empID "; 

	
	private static final String GET_ALL = GET_BY_CID +" AND c.WKPGRPID = :WKPGRPID AND c.scheduleTeamCd = :scheduleTeamCd "; 
	
	private static final String GET_BY_LIST_EMPID = GET_BY_CID + "AND c.pk.employeeID IN :empIDs" ;
	
	//private static final String GET_
	
	@Override
	public void insert(BelongScheduleTeam belongScheduleTeam) {
		KscmtAffScheduleTeam affScheduleTeam = KscmtAffScheduleTeam.toEntity(belongScheduleTeam);
		this.commandProxy().insert(KscmtAffScheduleTeam.toEntity(belongScheduleTeam));
		
	}

	@Override
	public void update(BelongScheduleTeam belongScheduleTeam) {
		Optional<KscmtAffScheduleTeam> entity = this.queryProxy()
				.query(GET_BY_KEY, KscmtAffScheduleTeam.class)
				.getSingle();
		if(entity.isPresent()){
			KscmtAffScheduleTeam newEntity = entity.get();
			newEntity.fromEntity(belongScheduleTeam);
			this.commandProxy().update(newEntity);
		}
		
	}

	@Override
	public void delete(String companyID, String empID, String WKPGRPID, String scheduleTeamCd) {
		// TODO Auto-generated method stub
		//this.commandProxy().remove
	}

	@Override
	public void delete(String companyID, String empID) {
		this.commandProxy().remove(KscmtAffScheduleTeam.class, new KscmtAffScheduleTeamPk(companyID, empID) );		

	}

	@Override
	public void delete(String companyID, String WKPGRPID, String scheduleTeamCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BelongScheduleTeam> getAll(String companyID, String WKPGRPID, String scheduleTeamCd) {
		return this.queryProxy().query(GET_ALL, KscmtAffScheduleTeam.class)
				.setParameter("CID", companyID)
				.setParameter("WKPGRPID", WKPGRPID)
				.setParameter("scheduleTeamCd", scheduleTeamCd)
				.getList(c ->c.toDomain());
	}

	@Override
	public Optional<BelongScheduleTeam> get(String companyID, String empID) {
		
		return this.queryProxy().query(GET_BY_KEY, KscmtAffScheduleTeam.class)
				.setParameter("CID", companyID)
				.setParameter("empID", empID)
				.getSingle(c->c.toDomain());
	}

	@Override
	public List<BelongScheduleTeam> get(String companyID, List<String> empIDs) {
		if(CollectionUtil.isEmpty(empIDs)){
			return new ArrayList<>();
		}
		return this.queryProxy().query(GET_BY_LIST_EMPID, KscmtAffScheduleTeam.class)
				.setParameter("CID", companyID)
				.setParameter("empIDs", empIDs)
				.getList(c ->c.toDomain());
	}

	@Override
	public boolean exists(String companyID, String empID) {
		Optional<BelongScheduleTeam> data = queryProxy().query(GET_BY_KEY, KscmtAffScheduleTeam.class)
				.setParameter("CID", companyID)
				.setParameter("empID", empID)
				.getSingle(c->c.toDomain());
		if(data.isPresent())
			return true;
		return false;
	}

	@Override
	public Optional<BelongScheduleTeam> get(String companyID, String empID, String WKPGRPID, String scheduleTeamCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String companyID, String empID, String WKPGRPID, String scheduleTeamCd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<BelongScheduleTeam> get(String companyID, String WKPGRPID, String empID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<BelongScheduleTeam> get(String companyID, String WKPGRPID, List<String> empID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getEmpScheduleTeam(String companyID, String WKPGRPID, String scheduleTeamCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<BelongScheduleTeam> getScheduleTeam(String companyID, String empID) {
		// TODO Auto-generated method stub	
		return null;
	}

	@Override
	public boolean checkempBelongScheduleTeam(String companyId, String empID) {
		Optional<BelongScheduleTeam> data = queryProxy().query(GET_BY_KEY, KscmtAffScheduleTeam.class)
				.setParameter("CID", companyId)
				.setParameter("empID", empID)
				.getSingle(c->c.toDomain());
		if(data.isPresent())
			return true;
		return false;
	}

}
