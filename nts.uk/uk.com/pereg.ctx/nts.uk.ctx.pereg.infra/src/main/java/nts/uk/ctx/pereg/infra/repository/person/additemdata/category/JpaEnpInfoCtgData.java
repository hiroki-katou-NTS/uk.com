package nts.uk.ctx.pereg.infra.repository.person.additemdata.category;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmpInfoCtgData;
import nts.uk.ctx.pereg.infra.entity.person.additemdata.category.PpemtSyaDataCtg;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaEnpInfoCtgData extends JpaRepository implements EmInfoCtgDataRepository {

	private static final String SELECT_EMP_DATA_BY_SID_AND_CTG_ID = "SELECT e FROM PpemtSyaDataCtg e"
			+ " WHERE e.employeeId = :employeeId AND e.personInfoCtgId = :personInfoCtgId";

	private static final String SELECT_EMP_DATA_BY_CTG_ID_LST = "SELECT e FROM PpemtSyaDataCtg e"
			+ " WHERE e.personInfoCtgId IN :personInfoCtgId";

	private EmpInfoCtgData toDomain(PpemtSyaDataCtg entity) {
		return new EmpInfoCtgData(entity.recordId, entity.personInfoCtgId, entity.employeeId);
	}

	@Override
	public List<EmpInfoCtgData> getByEmpIdAndCtgId(String employeeId, String categoryId) {
		List<PpemtSyaDataCtg> lstEntities = this.queryProxy()
				.query(SELECT_EMP_DATA_BY_SID_AND_CTG_ID, PpemtSyaDataCtg.class)
				.setParameter("employeeId", employeeId).setParameter("personInfoCtgId", categoryId).getList();
		if (lstEntities == null)
			return new ArrayList<>();
		return lstEntities.stream().map(x -> toDomain(x)).collect(Collectors.toList());
	}

	// sonnlb code start

	private PpemtSyaDataCtg toEntity(EmpInfoCtgData domain) {
		return new PpemtSyaDataCtg(domain.getRecordId(), domain.getPersonInfoCtgId(), domain.getEmployeeId());
	}

	private void updateEntity(EmpInfoCtgData domain, PpemtSyaDataCtg entity) {
		entity.personInfoCtgId = domain.getPersonInfoCtgId();
		entity.employeeId = domain.getEmployeeId();
	}

	@Override
	public void addCategoryData(EmpInfoCtgData domain) {
		Optional<PpemtSyaDataCtg> existItem = this.queryProxy().find(domain.getRecordId(),
				PpemtSyaDataCtg.class);
		if (!existItem.isPresent()) {
			this.commandProxy().insert(toEntity(domain));
		}
		

	}

	// sonnlb code end

	@Override
	public void updateEmpInfoCtgData(EmpInfoCtgData domain) {
		Optional<PpemtSyaDataCtg> existItem = this.queryProxy().find(domain.getRecordId(),
				PpemtSyaDataCtg.class);
		if (!existItem.isPresent()) {
			throw new RuntimeException("invalid EmpInfoCtgData");
		}
		updateEntity(domain, existItem.get());

		this.commandProxy().update(existItem.get());
	}

	@Override
	public void deleteEmpInfoCtgData(String recordId) {
		Optional<PpemtSyaDataCtg> existItem = this.queryProxy().find(recordId, PpemtSyaDataCtg.class);
		if (!existItem.isPresent()) {
			return;
		}
		this.commandProxy().remove(PpemtSyaDataCtg.class, recordId);
	}

	@Override
	public List<EmpInfoCtgData> getByEmpIdAndCtgId(List<String> ctgId) {
		List<PpemtSyaDataCtg> lstEntities = new ArrayList<>();
		CollectionUtil.split(ctgId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			lstEntities.addAll(this.queryProxy()
				.query(SELECT_EMP_DATA_BY_CTG_ID_LST, PpemtSyaDataCtg.class)
				.setParameter("personInfoCtgId", subList)
				.getList());
		});
		return lstEntities.stream().map(x -> toDomain(x)).collect(Collectors.toList());
	}

	@Override
	@SneakyThrows
	public List<EmpInfoCtgData> getBySidsAndCtgId(List<String> sids, String ctgId) {
		List<PpemtSyaDataCtg> lstEntities = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM PPEMT_SYA_DATA_CTG WHERE PER_INFO_CTG_ID = ? AND SID IN (" + NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString( 1, ctgId);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString( 2 + i, subList.get(i));
				}
				
				List<PpemtSyaDataCtg> empInfoContact = new NtsResultSet(stmt.executeQuery()).getList(r -> {
					return new PpemtSyaDataCtg(r.getString("RECORD_ID"), r.getString("PER_INFO_CTG_ID"), r.getString("SID"));
				});
				
				lstEntities.addAll(empInfoContact);
				
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		
		return lstEntities.stream().map(x -> toDomain(x)).collect(Collectors.toList());
	}

	@Override
	public void addAll(List<EmpInfoCtgData> domains) {
		String INS_SQL = "INSERT INTO PPEMT_SYA_DATA_CTG (INS_DATE, INS_CCD, INS_SCD, INS_PG,"
				+ "  UPD_DATE,  UPD_CCD,  UPD_SCD, UPD_PG,"
				+ "  RECORD_ID, PER_INFO_CTG_ID, SID) "
				+ "  VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				+ "  UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL, RECORD_ID_VAL,"
				+ "  PER_INFO_CTG_ID_VAL, SID_VAL); ";
		
		
    	GeneralDateTime insertTime = GeneralDateTime.now();
    	String insCcd = AppContexts.user().companyCode();
    	String insScd = AppContexts.user().employeeCode();
    	String insPg = AppContexts.programId();
		String updCcd = insCcd;
		String updScd = insScd;
		String updPg =  insPg;
		StringBuilder sb = new StringBuilder();
		domains.stream().forEach(c ->{
			String sql = INS_SQL;
			sql = sql.replace("INS_DATE_VAL", "'" + insertTime +"'");
			sql = sql.replace("INS_CCD_VAL", "'" + insCcd +"'");
			sql = sql.replace("INS_SCD_VAL", "'" + insScd +"'");
			sql = sql.replace("INS_PG_VAL", "'" + insPg +"'");
			
			sql = sql.replace("UPD_DATE_VAL", "'" + insertTime +"'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd +"'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd +"'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg +"'");
			
			sql = sql.replace("RECORD_ID_VAL", "'" + c.getRecordId() +"'");
			sql = sql.replace("P_INFO_CTG_ID_VAL", "'" + c.getPersonInfoCtgId() +"'");
			sql = sql.replace("SID_VAL", "'" + c.getEmployeeId() +"'");
			
			sb.append(sql);
		});
		int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

	@Override
	public void updateAll(List<EmpInfoCtgData> domains) {
		String UP_SQL = "UPDATE PPEMT_SYA_DATA_CTG SET  UPD_DATE = UPD_DATE_VAL,  UPD_CCD = UPD_CCD_VAL,  UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
				+ "  RECORD_ID = RECORD_ID_VAL, PER_INFO_CTG_ID = PER_INFO_CTG_ID_VAL, SID = SID_VAL"
				+ "  WHERE  RECORD_ID = RECORD_ID_VAL; ";
    	GeneralDateTime insertTime = GeneralDateTime.now();
		String updCcd = AppContexts.user().companyCode();
		String updScd = AppContexts.user().employeeCode();
		String updPg =  AppContexts.programId();
		StringBuilder sb = new StringBuilder();
		domains.stream().forEach(c ->{
			String sql = UP_SQL;
			
			sql = sql.replace("UPD_DATE_VAL", "'" + insertTime +"'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd +"'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd +"'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg +"'");
			
			sql = sql.replace("RECORD_ID_VAL", "'" + c.getRecordId() +"'");
			sql = sql.replace("PER_INFO_CTG_ID_VAL", "'" + c.getPersonInfoCtgId() +"'");
			sql = sql.replace("SID_VAL", "'" + c.getEmployeeId() +"'");
			
			sb.append(sql);
		});
		int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

}
