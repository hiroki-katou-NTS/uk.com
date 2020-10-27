package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareDataRepo;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcmtCareHDData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaLeaveForCareDataRepo extends JpaRepository implements LeaveForCareDataRepo {

	@Override
	public Optional<LeaveForCareData> getCareByEmpId(String empId) {
		Optional<KrcmtCareHDData> entity = this.queryProxy().find(empId, KrcmtCareHDData.class);
		if (entity.isPresent()) {
			return Optional.of(LeaveForCareData.getCareHDRemaining(entity.get().getSId(), entity.get().getUserDay()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void add(LeaveForCareData obj, String cId) {
		KrcmtCareHDData childCare = new KrcmtCareHDData(obj.getSId(), cId, obj.getNumOfUsedDay().v());
		this.commandProxy().insert(childCare);
	}

	@Override
	public void update(LeaveForCareData obj, String cId) {
		Optional<KrcmtCareHDData> entityOpt = this.queryProxy().find(obj.getSId(), KrcmtCareHDData.class);
		if (entityOpt.isPresent()) {
			KrcmtCareHDData entity = entityOpt.get();
			entity.setCId(cId);
			entity.setUserDay(obj.getNumOfUsedDay().v());
			this.commandProxy().update(entity);
		}
	}

	@Override
	public List<LeaveForCareData> getCareByEmpIds(String cid, List<String> empIds) {
		List<LeaveForCareData> result = new ArrayList<>();
		CollectionUtil.split(empIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCDT_CARE_HD_REMAIN WHERE  CID = ?  AND SID IN ("
					+ NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {

				stmt.setString(1, cid);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(2 + i, subList.get(i));
				}

				List<LeaveForCareData> data = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					return LeaveForCareData.getCareHDRemaining(rec.getString("SID"),
							rec.getDouble("USED_DAYS"));
				});
				result.addAll(data);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}
	
	@Override
	public void addAll(String cid, List<LeaveForCareData> domains) {
		String INS_SQL = "INSERT INTO KRCDT_CARE_HD_REMAIN (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
				+ " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG," 
				+ " SID, CID, USED_DAYS)"
				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
				+ " SID_VAL, CID_VAL, USED_DAYS_VAL);";
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

			sql = sql.replace("SID_VAL", "'" +  c.getSId() + "'");
			sql = sql.replace("CID_VAL", "'" + cid + "'");
			sql = sql.replace("USED_DAYS_VAL", "" + c.getNumOfUsedDay().v()+ "");

			sb.append(sql);
		});

		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

	@Override
	public void updateAll(String cid, List<LeaveForCareData> domains) {
		String UP_SQL = "UPDATE KRCDT_CARE_HD_REMAIN SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
				+ " USED_DAYS = USED_DAYS_VAL"
				+ " WHERE SID = SID_VAL AND CID = CID_VAL;";
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

			sql = sql.replace("SID_VAL", "'" +  c.getSId() + "'");
			sql = sql.replace("CID_VAL", "'" + cid + "'");
			sql = sql.replace("USED_DAYS_VAL", "" + c.getNumOfUsedDay().v()+ "");
			
			sb.append(sql);
		});

		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}



}
