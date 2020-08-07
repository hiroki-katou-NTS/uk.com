package nts.uk.ctx.at.schedule.infra.repository.employeeinfo.scheduleteam;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.aspose.pdf.Collection;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.scheduleteam.KscmtAffScheduleTeam;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.scheduleteam.KscmtAffScheduleTeamPk;
import nts.uk.shr.com.context.AppContexts;

/**
 * 所属スケジュールチームRepository			
 * @author HieuLt
 *
 */
@Stateless
public class JpaBelongScheduleTeamRepository extends JpaRepository implements BelongScheduleTeamRepository{

	private static final String SELECT = " SELECT c FROM KscmtAffScheduleTeam c ";
	
	private static final String GET_BY_CID = SELECT +  " WHERE c.pk.CID = :CID " ;

	private static final String GET_BY_KEY = GET_BY_CID +" AND c.pk.employeeID = :empID "; 

	
	private static final String GET_ALL = GET_BY_CID +" AND c.WKPGRPID = :WKPGRPID AND c.scheduleTeamCd = :scheduleTeamCd "; 
	
	private static final String GET_BY_LIST_EMPID = GET_BY_CID + "AND c.pk.employeeID IN :empIDs" ;
	
	//private static final String DELETE_BY_WKPGRPID = " DELETE FROM KscmtAffScheduleTeam c  WHERE c.pk.CID = :CID AND c.scheduleTeamCd = :scheduleTeamCd AND c.WKPGRPID = :WKPGRPID " ;
	
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

	@SneakyThrows
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public void delete(String companyID, String empID) {
		String delete = "DELETE FROM KSCMT_AFF_SCHEDULE_TEAM WHERE CID = ? AND SID = ?";
		PreparedStatement ps1 = this.connection().prepareStatement(delete);
		ps1.setString(1, companyID);
		ps1.setString(2, empID);
		ps1.executeUpdate();

	}

	@SneakyThrows
	@Override
	public void delete(String companyID, String WKPGRPID, String scheduleTeamCd) {

		String delete = "DELETE FROM KSCMT_AFF_SCHEDULE_TEAM WHERE CID = ? AND WKPGRP_ID = ? AND CD = ? ";
		PreparedStatement ps1 = this.connection().prepareStatement(delete);
		ps1.setString(1, companyID);
		ps1.setString(2, WKPGRPID);
		ps1.setString(3, scheduleTeamCd);
		
		ps1.executeUpdate();
//		List<BelongScheduleTeam> lst = getAll(companyID, WKPGRPID, scheduleTeamCd);
//		List<KscmtAffScheduleTeam> data = lst.stream().map(x -> new KscmtAffScheduleTeam(
//				new KscmtAffScheduleTeamPk(companyID, x.getEmployeeID()) ,
//				x.getWKPGRPID(), x.getScheduleTeamCd().v())).collect(Collectors.toList());
//		this.commandProxy().removeAll(data);
		
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
