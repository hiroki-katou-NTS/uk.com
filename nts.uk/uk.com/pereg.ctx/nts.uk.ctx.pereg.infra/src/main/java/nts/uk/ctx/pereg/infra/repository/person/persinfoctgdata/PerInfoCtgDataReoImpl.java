/**
 * 
 */
package nts.uk.ctx.pereg.infra.repository.person.persinfoctgdata;

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
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.pereg.infra.entity.person.personinfoctgdata.PpemtCtgData;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author danpv
 *
 */
@Stateless
public class PerInfoCtgDataReoImpl extends JpaRepository implements PerInfoCtgDataRepository {

	private static final String GET_BY_CTGID_PID = "select cd from PpemtCtgData cd"
			+ " where cd.pInfoCtgId = :pInfoCtgId and cd.pId = :pId";

	@Override
	public Optional<PerInfoCtgData> getByRecordId(String recordId) {
		PpemtCtgData entity = this.queryProxy().find(recordId, PpemtCtgData.class).get();
		return Optional.of(new PerInfoCtgData(entity.recordId, entity.pInfoCtgId, entity.pId));
	}

	@Override
	public List<PerInfoCtgData> getByPerIdAndCtgId(String perId, String ctgId) {
		List<PpemtCtgData> datas = this.queryProxy().query(GET_BY_CTGID_PID, PpemtCtgData.class)
				.setParameter("pInfoCtgId", ctgId).setParameter("pId", perId).getList();
		if(datas == null) return new ArrayList<>();
		return datas.stream().map(entity -> new PerInfoCtgData(entity.recordId, entity.pInfoCtgId, entity.pId))
				.collect(Collectors.toList());

	}
	private PpemtCtgData toEntity(PerInfoCtgData domain){
		return new PpemtCtgData(domain.getRecordId(), domain.getPersonInfoCtgId(), domain.getPersonId());
	}
	
	private void updateEntity(PerInfoCtgData domain, PpemtCtgData entity){
		entity.recordId = domain.getRecordId();
		entity.pInfoCtgId = domain.getPersonInfoCtgId();
		entity.pId = domain.getPersonId();
	}
	/**
	 * Add person info category data ドメインモデル「個人情報カテゴリデータ」を新規登録する
	 * @param data
	 */
	@Override
	public void addCategoryData(PerInfoCtgData data) {
		Optional<PpemtCtgData> existItem = this.queryProxy().find(data.getRecordId(), PpemtCtgData.class);
		if (!existItem.isPresent()){
			this.commandProxy().insert(toEntity(data));
		}
	}
	/**
	 * Update person info category data ドメインモデル「個人情報カテゴリデータ」を更新する
	 * @param data
	 */
	@Override
	public void updateCategoryData(PerInfoCtgData data) {
		Optional<PpemtCtgData> existItem = this.queryProxy().find(data.getRecordId(), PpemtCtgData.class);
		if (!existItem.isPresent()){
			throw new RuntimeException("invalid PerInfoCtgData");
		}
		// Update entity
		updateEntity(data, existItem.get());
		// Update 
		this.commandProxy().update(existItem.get());
	}

	@Override
	public void deleteCategoryData(PerInfoCtgData data) {
		this.commandProxy().remove(PpemtCtgData.class,data.getRecordId());
		
	}

	@Override
	@SneakyThrows
	public List<PerInfoCtgData> getAllByPidsAndCtgId(List<String> pids, String ctgId) {
		List<PerInfoCtgData> result = new ArrayList<>();
		CollectionUtil.split(pids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM PPEMT_CTG_DATA WHERE P_INFO_CTG_ID = ? AND PID IN (" + NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString( 1 , ctgId);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString( 2 + i, subList.get(i));
				}
				
				List<PerInfoCtgData> empInfoContact = new NtsResultSet(stmt.executeQuery()).getList(r -> {
					return new PerInfoCtgData(r.getString("RECORD_ID"), r.getString("P_INFO_CTG_ID"), r.getString("PID"));
				});
				
				result.addAll(empInfoContact);
				
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		
		return result;
	}

	@Override
	public void addAll(List<PerInfoCtgData> domains) {
		String INS_SQL = "INSERT INTO PPEMT_CTG_DATA (INS_DATE, INS_CCD, INS_SCD, INS_PG, "
				+ " UPD_DATE,  UPD_CCD,  UPD_SCD, UPD_PG, RECORD_ID, P_INFO_CTG_ID, PID)"
				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL, RECORD_ID_VAL,"
				+ " P_INFO_CTG_ID_VAL, PID_VAL); ";
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
			sql = sql.replace("PID_VAL", "'" + c.getPersonId() +"'");
			
			sb.append(sql);
		});
		int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

	@Override
	public void updateAll(List<PerInfoCtgData> domains) {
		String UP_SQL = "UPDATE PPEMT_CTG_DATA SET  UPD_DATE = UPD_DATE_VAL,  UPD_CCD = UPD_CCD_VAL,  UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
				+ "  RECORD_ID = RECORD_ID_VAL, P_INFO_CTG_ID = P_INFO_CTG_ID_VAL, PID = PID_VAL"
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
			sql = sql.replace("P_INFO_CTG_ID_VAL", "'" + c.getPersonInfoCtgId() +"'");
			sql = sql.replace("PID_VAL", "'" + c.getPersonId() +"'");
			
			sb.append(sql);
		});
		int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

}
