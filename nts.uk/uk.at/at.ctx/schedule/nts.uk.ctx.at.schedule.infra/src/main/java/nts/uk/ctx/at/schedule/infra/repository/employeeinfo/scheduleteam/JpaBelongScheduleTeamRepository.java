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
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.scheduleteam.KscmtAffScheduleTeam;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.scheduleteam.KscmtAffScheduleTeamPk;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.scheduleteam.KscmtScheduleTeam;
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
	
	private static final String GET_DATA_51= GET_ALL + "AND c.pk.employeeID = :empID " ;
	
	private static final String GET_DATA_61 = GET_BY_CID + " AND c.WKPGRPID = :WKPGRPID AND c.pk.employeeID = :empID ";
	
	private static final String GET_DATA_62 = GET_BY_CID + " AND c.WKPGRPID = :WKPGRPID AND  c.pk.employeeID IN :empIDs ";
	
	private static final String GET_DATA_7 = " SELECT c.pk.employeeID FROM KscmtAffScheduleTeam c "
											  + " INNER JOIN KscmtScheduleTeam s ON c.pk.CID = s.pk CID AND c.WKPGRPID = s.pkWKPGRPID AND c.scheduleTeamCd = s.scheduleTeamCd  "
											  + " WHERE c.pk.CID = :CID AND c.WKPGRPID = :WKPGRPID AND c.scheduleTeamCd = :scheduleTeamCd "			
											  + " ORDER BY s.dispOrder ASC ";
	
	private static final String GET_DATA_8 = " SELECT s FROM KscmtScheduleTeam s "
											  + " INNER JOIN KscmtAffScheduleTeam a ON s.pk.CID = a.pk.CID AND s.pk.WKPGRPID = a.WKPGRPID AND s.pk.scheduleTeamCd = a.scheduleTeamCd "
									          + " WHERE s.pk.CID = :CID AND a.pk.employeeID = :empID ";
	
	//Optional<BelongScheduleTeam> get(String companyID, String WKPGRPID, List<String> empID) {
	//List<String> getEmpScheduleTeam(String companyID, String WKPGRPID, String scheduleTeamCd)
	@Override
	public void insert(BelongScheduleTeam belongScheduleTeam) {
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
	@SneakyThrows
	@Override
	public void delete(String companyID, String empID, String WKPGRPID, String scheduleTeamCd) {
		String delete = "DELETE FROM KSCMT_AFF_SCHEDULE_TEAM  WHERE CID = ? AND SID = ? AND WKPGRP_ID = ? AND CD = ?";
		PreparedStatement ps1 = this.connection().prepareStatement(delete);
		ps1.setString(1, companyID);
		ps1.setString(2, empID);
		ps1.setString(3, WKPGRPID);
		ps1.setString(4, scheduleTeamCd);
		ps1.executeUpdate();
	}

	@SneakyThrows
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
		   Optional<BelongScheduleTeam> data = this.queryProxy().query(GET_DATA_51, KscmtAffScheduleTeam.class)
					.setParameter("CID", companyID)
					.setParameter("empID", empID)
					.setParameter("WKPGRPID", WKPGRPID)
					.setParameter("scheduleTeamCd", scheduleTeamCd)
					.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public boolean exists(String companyID, String empID, String WKPGRPID, String scheduleTeamCd) {
		 Optional<BelongScheduleTeam> data = get(companyID, empID, WKPGRPID, scheduleTeamCd);
		 if(data.isPresent())
			 return true;
		return false;
	}

	@Override
	public Optional<BelongScheduleTeam> get(String companyID, String WKPGRPID, String empID) {
		
		/*remarks																														
		所属スケジュールチームにUnique制約が存在するため取得件数は必ず1件以下*/										
		 Optional<BelongScheduleTeam> data = this.queryProxy().query(GET_DATA_61, KscmtAffScheduleTeam.class)
				 									.setParameter("CID", companyID)
				 									.setParameter("WKPGRPID", WKPGRPID)
				 									.setParameter("empID", empID)
				 									.getSingle(c->c.toDomain());
				 									
		return data;
	}

	@Override
	public Optional<BelongScheduleTeam> get(String companyID, String WKPGRPID, List<String> empIDs) {
		// 	所属スケジュールチームにUnique制約が存在するため取得件数は必ず1件以下
		 Optional<BelongScheduleTeam> data = this.queryProxy().query(GET_DATA_62, KscmtAffScheduleTeam.class)
					.setParameter("CID", companyID)
					.setParameter("WKPGRPID", WKPGRPID)
					.setParameter("empIDs", empIDs)
					.getSingle(c->c.toDomain());
		 
		return data;
	}

	@Override
	public List<String> getEmpScheduleTeam(String companyID, String WKPGRPID, String scheduleTeamCd) {
		/*社員IDのみを返すため社員名などの情報は取得できない。																
		スケジュールチームと結合しているためチームの有無は判定できるが													
		職場グループが存在するかや社員が職場グループに所属しているかは関知しない。*/
		List<String> data = this.queryProxy().query(GET_DATA_7, KscmtAffScheduleTeam.class)
				.setParameter("CID", companyID)
				.setParameter("WKPGRPID", WKPGRPID)
				.setParameter("scheduleTeamCd", scheduleTeamCd)
				.getList(c ->c.pk.employeeID);
		return data;
	}

	@Override
	public Optional<ScheduleTeam> getScheduleTeam(String companyID, String empID) {
		// 	職場グループ所属情報にUnique制約が存在するため取得件数は必ず1件以下	
		Optional<ScheduleTeam> data = this.queryProxy().query(GET_DATA_8, KscmtScheduleTeam.class)
								.setParameter("CID", companyID)
								.setParameter("empID", empID)
							    .getSingle(c->c.toDomain());	
		return data;
	}

	@Override
	public boolean checkempBelongScheduleTeam(String companyId, String empID) {
		 Optional<ScheduleTeam> data = getScheduleTeam(companyId, empID);
		if(data.isPresent())
			return true;
		return false;
	}

}
