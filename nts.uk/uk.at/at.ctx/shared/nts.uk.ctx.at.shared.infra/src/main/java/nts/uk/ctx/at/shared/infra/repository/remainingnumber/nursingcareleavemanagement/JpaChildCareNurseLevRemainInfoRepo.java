package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdNursingInfo;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdNursingInfoPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * 子の看護介護リポジトリ（共通クラス）
 * @author masaaki_jinno
 *
 */
@Stateless
public class JpaChildCareNurseLevRemainInfoRepo extends JpaRepository {

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	protected Optional<KrcdtHdNursingInfo> getByEmpIdAndNursingType(String empId, Integer nursingType) {
		KrcdtHdNursingInfoPK key = new KrcdtHdNursingInfoPK(empId, nursingType);
		Optional<KrcdtHdNursingInfo> entityOpt = this.queryProxy().find(key, KrcdtHdNursingInfo.class);
		return entityOpt;
	}

	protected void add(KrcdtHdNursingInfo entity) {
		this.commandProxy().insert(entity);
	}

	protected void update(KrcdtHdNursingInfo entity) {
		Optional<KrcdtHdNursingInfo> entityOpt = this.queryProxy().find(entity.getPk(), KrcdtHdNursingInfo.class);
		if (entityOpt.isPresent()) {
			this.commandProxy().update(entity);
		}
	}

	protected List<NursingCareLeaveRemainingInfo> getDataByEmpIdsAndCidAndNursingCategory(
			String cid, List<String> empIds, NursingCategory nursingType) {

		List<NursingCareLeaveRemainingInfo> result = new ArrayList<>();
		CollectionUtil.split(empIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCDT_HDNURSING_INFO WHERE  CID = ?  AND SID IN ("
					+ NtsStatement.In.createParamsString(subList) + ") AND NURSING_TYPE = ?";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {

				stmt.setString(1, cid);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(2 + i, subList.get(i));
				}
				stmt.setInt(1 + subList.size() + 1, nursingType.value);

				List<NursingCareLeaveRemainingInfo> data = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					if ( nursingType.equals(NursingCategory.ChildNursing)) {
						return ChildCareLeaveRemainingInfo.createChildCareLeaveInfo(rec.getString("SID"),
								rec.getInt("USE_ATR"), rec.getInt("UPPER_LIM_SET_ART"),
								rec.getInt("MAX_DAY_THIS_FISCAL_YEAR"), rec.getInt("MAX_DAY_NEXT_FISCAL_YEAR"));
					} else  if ( nursingType.equals(NursingCategory.Nursing)) {
						return CareLeaveRemainingInfo.createCareLeaveInfo(rec.getString("SID"),
								rec.getInt("USE_ATR"), rec.getInt("UPPER_LIM_SET_ART"),
								rec.getInt("MAX_DAY_THIS_FISCAL_YEAR"), rec.getInt("MAX_DAY_NEXT_FISCAL_YEAR"));
					} else {
						throw new RuntimeException();
					}
				});
				result.addAll(data);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}

	public void addAllList(String cid, List<NursingCareLeaveRemainingInfo> domains) {
		String INS_SQL = "INSERT INTO KRCDT_HDNURSING_INFO (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
				+ " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG, EXCLUS_VER, CONTRACT_CD,"
				+ " SID, CID, NURSING_TYPE, USE_ATR, UPPER_LIM_SET_ART, MAX_DAY_THIS_FISCAL_YEAR, MAX_DAY_NEXT_FISCAL_YEAR)"
				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL, EXCLUS_VER_VAL, CONTRACT_CD_VAL,"
				+ " SID_VAL, CID_VAL, NURSING_TYPE_VAL, USE_ATR_VAL, UPPER_LIM_SET_ART_VAL, MAX_DAY_THIS_FISCAL_YEAR_VAL, MAX_DAY_NEXT_FISCAL_YEAR_VAL);";
		String insCcd = AppContexts.user().companyCode();
		String insScd = AppContexts.user().employeeCode();
		String insPg = AppContexts.programId();

		String updCcd = insCcd;
		String updScd = insScd;
		String updPg = insPg;
		
		String contractCd = AppContexts.user().contractCode();
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
			
			sql = sql.replace("EXCLUS_VER_VAL", "0");
			sql = sql.replace("CONTRACT_CD_VAL", "'" + contractCd + "'");

			sql = sql.replace("SID_VAL", "'" +  c.getSId() + "'");
			sql = sql.replace("CID_VAL", "'" + cid + "'");
			sql = sql.replace("NURSING_TYPE_VAL", "'" + String.valueOf(c.getLeaveType().value) + "'");
			sql = sql.replace("USE_ATR_VAL", c.isUseClassification() ? "1" : "0");
			sql = sql.replace("UPPER_LIM_SET_ART_VAL", ""+c.getUpperlimitSetting().value+"");
			sql = sql.replace("MAX_DAY_THIS_FISCAL_YEAR_VAL", c.getMaxDayForThisFiscalYear().isPresent() ? ""+ c.getMaxDayForThisFiscalYear().get().v()+"" : "null");
			sql = sql.replace("MAX_DAY_NEXT_FISCAL_YEAR_VAL", c.getMaxDayForNextFiscalYear().isPresent() ? ""+ c.getMaxDayForNextFiscalYear().get().v() + "" : "null");
			sb.append(sql);
		});

		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);

	}

	public void updateAllList(String cid, List<NursingCareLeaveRemainingInfo> domains) {
		String UP_SQL = "UPDATE KRCDT_HDNURSING_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
				+ " USE_ATR = USE_ATR_VAL, UPPER_LIM_SET_ART = UPPER_LIM_SET_ART_VAL, MAX_DAY_THIS_FISCAL_YEAR = MAX_DAY_THIS_FISCAL_YEAR_VAL, MAX_DAY_NEXT_FISCAL_YEAR = MAX_DAY_NEXT_FISCAL_YEAR_VAL"
				+ " WHERE SID = SID_VAL AND CID = CID_VAL AND NURSING_TYPE = NURSING_TYPE_VAL;";
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
			sql = sql.replace("USE_ATR_VAL", c.isUseClassification() ? "1" : "0");
			sql = sql.replace("NURSING_TYPE_VAL", "'" + String.valueOf(c.getLeaveType().value) + "'");
			sql = sql.replace("UPPER_LIM_SET_ART_VAL", ""+c.getUpperlimitSetting().value+"");
			sql = sql.replace("MAX_DAY_THIS_FISCAL_YEAR_VAL", c.getMaxDayForThisFiscalYear().isPresent() ? ""+ c.getMaxDayForThisFiscalYear().get().v()+"" : "null");
			sql = sql.replace("MAX_DAY_NEXT_FISCAL_YEAR_VAL", c.getMaxDayForNextFiscalYear().isPresent() ? ""+ c.getMaxDayForNextFiscalYear().get().v() + "" : "null");

			sb.append(sql);
		});

		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);

	}
}

