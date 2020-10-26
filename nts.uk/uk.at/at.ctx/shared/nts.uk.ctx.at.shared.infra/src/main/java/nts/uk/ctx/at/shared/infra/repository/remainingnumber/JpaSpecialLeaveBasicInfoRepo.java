package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.spLea.basicInfo.KrcmtSpecialLeaveInfo;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.spLea.basicInfo.KrcmtSpecialLeaveInfoPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaSpecialLeaveBasicInfoRepo extends JpaRepository implements SpecialLeaveBasicInfoRepository {

	private static final String FIND_QUERY = "SELECT s FROM KrcmtSpecialLeaveInfo s WHERE s.key.employeeId = :employeeId ";

	private static final String FIND_QUERY_BYSIDCD = String.join(" ", FIND_QUERY, "AND s.key.spLeaveCD = :spLeaveCD");
	
	private static final String QUERY_BY_SID_LEAVECD_ISUSE = "SELECT s FROM KrcmtSpecialLeaveInfo s"
			+ " WHERE s.key.employeeId = :employeeId "
			+ " AND s.key.spLeaveCD = :spLeaveCD "
			+ " AND s.useCls = :useCls";
	
	@Override
	public List<SpecialLeaveBasicInfo> listSPLeav(String sid) {
		List<SpecialLeaveBasicInfo> result = this.queryProxy().query(FIND_QUERY, KrcmtSpecialLeaveInfo.class)
				.setParameter("employeeId", sid).getList(item -> {
					return toDomain(item);
				});
		return result;
	}

	@Override
	public void add(SpecialLeaveBasicInfo domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	@Override
	public void update(SpecialLeaveBasicInfo domain) {
		KrcmtSpecialLeaveInfoPK key = new KrcmtSpecialLeaveInfoPK(domain.getSID(), domain.getSpecialLeaveCode().v());
		Optional<KrcmtSpecialLeaveInfo> entity = this.queryProxy().find(key, KrcmtSpecialLeaveInfo.class);
		if (!entity.isPresent()) {
			return;
		}

		updateEntity(domain, entity.get());

		this.commandProxy().update(entity.get());

	}

	@Override
	public void delete(String sID, int spLeavCD) {
		KrcmtSpecialLeaveInfoPK key = new KrcmtSpecialLeaveInfoPK(sID, spLeavCD);
		this.commandProxy().remove(KrcmtSpecialLeaveInfo.class, key);
	}

	/**
	 * Update to entity
	 * 
	 * @param domain
	 * @return
	 */
	private void updateEntity(SpecialLeaveBasicInfo domain, KrcmtSpecialLeaveInfo entity) {
		entity.useCls = domain.getUsed().value;
		entity.appSetting = domain.getApplicationSet().value;
		entity.grantDate = domain.getGrantSetting().getGrantDate();
		entity.grantNumber = null;
		if (domain.getGrantSetting().getGrantDays().isPresent()) {
			entity.grantNumber = domain.getGrantSetting().getGrantDays().get().v();
		}
		if (domain.getGrantSetting().getGrantTable().isPresent()) {
			if (domain.getGrantSetting().getGrantTable().get().equals(""))
				entity.grantTable = null;
			else
				entity.grantTable = domain.getGrantSetting().getGrantTable().get().v();
		} else {
			entity.grantTable = null;
		}
	}

	/**
	 * Convert to entity
	 * 
	 * @param domain
	 * @return
	 */
	private KrcmtSpecialLeaveInfo toEntity(SpecialLeaveBasicInfo domain) {
		KrcmtSpecialLeaveInfo entity = new KrcmtSpecialLeaveInfo();
		entity.cID = domain.getCID();
		KrcmtSpecialLeaveInfoPK key = new KrcmtSpecialLeaveInfoPK(domain.getSID(), domain.getSpecialLeaveCode().v());
		entity.key = key;
		entity.useCls = domain.getUsed().value;
		entity.appSetting = domain.getApplicationSet().value;
		entity.grantDate = domain.getGrantSetting().getGrantDate();
		if (domain.getGrantSetting().getGrantDays().isPresent()) {
			entity.grantNumber = domain.getGrantSetting().getGrantDays().get().v();
		}
		if (domain.getGrantSetting().getGrantTable().isPresent()) {
			if (domain.getGrantSetting().getGrantTable().get().equals(""))
				entity.grantTable = null;
			else
				entity.grantTable = domain.getGrantSetting().getGrantTable().get().v();
		}

		return entity;
	}

	/**
	 * Convert to domain
	 * 
	 * @param entity
	 * @return
	 */
	private SpecialLeaveBasicInfo toDomain(KrcmtSpecialLeaveInfo entity) {
		return new SpecialLeaveBasicInfo(entity.cID, entity.key.employeeId, entity.key.spLeaveCD, entity.useCls,
				entity.appSetting, entity.grantDate, entity.grantNumber, entity.grantTable);
	}

	@Override
	public Optional<SpecialLeaveBasicInfo> getBySidLeaveCd(String sid, int spLeaveCD) {
		Optional<SpecialLeaveBasicInfo> result = this.queryProxy()
				.query(FIND_QUERY_BYSIDCD, KrcmtSpecialLeaveInfo.class).setParameter("employeeId", sid)
				.setParameter("spLeaveCD", spLeaveCD).getSingle(item -> toDomain(item));
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<SpecialLeaveBasicInfo> getBySidLeaveCdUser(String sid, int spLeaveCD, UseAtr use) {
		Optional<SpecialLeaveBasicInfo> result = this.queryProxy()
				.query(QUERY_BY_SID_LEAVECD_ISUSE, KrcmtSpecialLeaveInfo.class)
				.setParameter("employeeId", sid)
				.setParameter("spLeaveCD", spLeaveCD)
				.setParameter("useCls", use.value)				
				.getSingle(item -> toDomain(item));
		return result;
	}

	@Override
	@SneakyThrows
	public List<SpecialLeaveBasicInfo> getAllBySidsLeaveCd(String cid, List<String> sids, int spLeaveCD) {
		List<KrcmtSpecialLeaveInfo> entities = new ArrayList<>();
		
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCMT_SPECIAL_LEAVE_INFO WHERE CID = ? AND SPECIAL_LEAVE_CD = ? AND SID IN ("+ NtsStatement.In.createParamsString(subList) + ")";
			
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString( 1, cid);
				stmt.setInt( 2, spLeaveCD);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString( 3 + i, subList.get(i));
				}
				
				List<KrcmtSpecialLeaveInfo> result = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					KrcmtSpecialLeaveInfo entity = new KrcmtSpecialLeaveInfo();
					entity.key = new KrcmtSpecialLeaveInfoPK(rec.getString("SID"), rec.getInt("SPECIAL_LEAVE_CD"));
					entity.cID = rec.getString("CID");
					entity.useCls = rec.getInt("USE_ATR");
					entity.appSetting = rec.getInt("APPLICATION_SET");
					entity.grantDate = rec.getGeneralDate("GRANT_DATE");
					entity.grantNumber = rec.getInt("GRANTED_DAYS");
					entity.grantTable =  rec.getString("GRANT_TABLE");
					return entity;
				});
				entities.addAll(result);
				
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		
		return entities.stream().map(ent -> toDomain(ent)).collect(Collectors.toList());
	}

	@Override
	public void addAll(List<SpecialLeaveBasicInfo> domains) {
		String INS_SQL = "INSERT INTO KRCMT_SPECIAL_LEAVE_INFO (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
				+ " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG," 
				+ " SID, SPECIAL_LEAVE_CD, CID, USE_ATR, APPLICATION_SET, GRANT_DATE, GRANTED_DAYS, GRANT_TABLE)"
				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
				+ " SID_VAL, SPECIAL_LEAVE_CD_VAL, CID_VAL, "
				+ " USE_ATR_VAL, APPLICATION_SET_VAL, GRANT_DATE_VAL, GRANTED_DAYS_VAL, GRANT_TABLE_VAL); ";
		String insCcd = AppContexts.user().companyCode();
		String insScd = AppContexts.user().employeeCode();
		String insPg = AppContexts.programId();
		
		String updCcd = insCcd;
		String updScd = insScd;
		String updPg = insPg;
		StringBuilder sb = new StringBuilder();
		domains.stream().forEach(c -> {
			String sql = INS_SQL;
			sql = sql.replace("INS_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("INS_CCD_VAL", "'" + insCcd + "'");
			sql = sql.replace("INS_SCD_VAL", "'" + insScd + "'");
			sql = sql.replace("INS_PG_VAL", "'" + insPg + "'");

			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");

			sql = sql.replace("SID_VAL", "'" + c.getSID() + "'");
			sql = sql.replace("SPECIAL_LEAVE_CD_VAL", "" + c.getSpecialLeaveCode().v()+ "");
			sql = sql.replace("CID_VAL", "'" + c.getCID() + "'");
			
			sql = sql.replace("USE_ATR_VAL", "" + c.getUsed().value+ "");
			sql = sql.replace("APPLICATION_SET_VAL", "" + c.getApplicationSet().value+ "");
			sql = sql.replace("GRANT_DATE_VAL", "'" + c.getGrantSetting().getGrantDate() + "'");
			if (c.getGrantSetting().getGrantDays().isPresent()) {
				sql = sql.replace("GRANTED_DAYS_VAL", "" +   c.getGrantSetting().getGrantDays().get().v() + "");
			}else {
				sql = sql.replace("GRANTED_DAYS_VAL", "null");
			}
			
			if (c.getGrantSetting().getGrantTable().isPresent()) {
				if (c.getGrantSetting().getGrantTable().get().equals(""))
					sql = sql.replace("GRANT_TABLE_VAL", "null");
				else
					sql = sql.replace("GRANT_TABLE_VAL", "'"+ c.getGrantSetting().getGrantTable().get().v()+"'" );
			}
			sb.append(sql);
		});

		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

	@Override
	public void updateAll(List<SpecialLeaveBasicInfo> domains) {
		String UP_SQL = "UPDATE KRCMT_SPECIAL_LEAVE_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
				+ " USE_ATR = USE_ATR_VAL , APPLICATION_SET = APPLICATION_SET_VAL, GRANT_DATE = GRANT_DATE_VAL, "
				+ " GRANTED_DAYS = GRANTED_DAYS_VAL, GRANT_TABLE = GRANT_TABLE_VAL"
				+ " WHERE SID = SID_VAL AND SPECIAL_LEAVE_CD = SPECIAL_LEAVE_CD_VAL AND  CID = CID_VAL;";
		String updCcd = AppContexts.user().companyCode();
		String updScd = AppContexts.user().employeeCode();
		String updPg = AppContexts.programId();
		StringBuilder sb = new StringBuilder();
		domains.stream().forEach(c -> {
			String sql = UP_SQL;

			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");

			sql = sql.replace("SID_VAL", "'" + c.getSID() + "'");
			sql = sql.replace("SPECIAL_LEAVE_CD_VAL", "" + c.getSpecialLeaveCode().v()+ "");
			sql = sql.replace("CID_VAL", "'" + c.getCID() + "'");
			
			sql = sql.replace("USE_ATR_VAL", "" + c.getUsed().value+ "");
			sql = sql.replace("APPLICATION_SET_VAL", "" + c.getApplicationSet().value+ "");
			sql = sql.replace("GRANT_DATE_VAL", "'" + c.getGrantSetting().getGrantDate() + "'");
			if (c.getGrantSetting().getGrantDays().isPresent()) {
				sql = sql.replace("GRANTED_DAYS_VAL", "" +   c.getGrantSetting().getGrantDays().get().v() + "");
			}else {
				sql = sql.replace("GRANTED_DAYS_VAL", "null");
			}
			
			if (c.getGrantSetting().getGrantTable().isPresent()) {
				if (c.getGrantSetting().getGrantTable().get().equals(""))
					sql = sql.replace("GRANT_TABLE_VAL", "null");
				else
					sql = sql.replace("GRANT_TABLE_VAL", "'"+ c.getGrantSetting().getGrantTable().get().v()+"'" );
			}
			sb.append(sql);
		});

		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);		
	}

	@Override
	public List<Object[]> getAllBySidsAndLeaveCd(String cid, List<String> sids, int spLeaveCD) {
		List<Object[]> entities = new ArrayList<>();
		
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCMT_SPECIAL_LEAVE_INFO WHERE CID = ? AND SPECIAL_LEAVE_CD = ? AND SID IN ("+ NtsStatement.In.createParamsString(subList) + ")";
			
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString( 1, cid);
				stmt.setInt( 2, spLeaveCD);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString( 3 + i, subList.get(i));
				}
				
				List<Object[]> result = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					Object[] object = new Object[] {
							rec.getString("SID"),
							rec.getInt("SPECIAL_LEAVE_CD"),
							rec.getString("CID"),
							rec.getInt("USE_ATR"),
							rec.getInt("APPLICATION_SET"),
							rec.getGeneralDate("GRANT_DATE"),
							rec.getInt("GRANTED_DAYS"),
							rec.getString("GRANT_TABLE")
					};
					return object;
				});
				entities.addAll(result);
				
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		
		return entities;
	}

}
