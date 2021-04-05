package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.info;

/** 削除予定 **
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
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcmtChildCareHDInfo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaChildCareLevRemainInfoRepo extends JpaRepository implements ChildCareLeaveRemInfoRepository {

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<ChildCareLeaveRemainingInfo> getChildCareByEmpId(String empId) {
		Optional<KrcmtChildCareHDInfo> entityOpt = this.queryProxy().find(empId, KrcmtChildCareHDInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtChildCareHDInfo entity = entityOpt.get();
			return Optional.of(ChildCareLeaveRemainingInfo.createChildCareLeaveInfo(entity.getSId(), entity.getUseAtr(),
					entity.getUpperLimSetAtr(), entity.getMaxDayThisFiscalYear(), entity.getMaxDayNextFiscalYear()));
		}
		return Optional.empty();
	}

	@Override
	public void add(ChildCareLeaveRemainingInfo obj, String cId) {
		KrcmtChildCareHDInfo entity = new KrcmtChildCareHDInfo(obj.getSId(), cId, obj.isUseClassification() ? 1 : 0,
				obj.getUpperlimitSetting().value,
				obj.getMaxDayForThisFiscalYear().isPresent() ? obj.getMaxDayForThisFiscalYear().get().v() : null,
				obj.getMaxDayForNextFiscalYear().isPresent() ? obj.getMaxDayForNextFiscalYear().get().v() : null);
		this.commandProxy().insert(entity);

	}

	@Override
	public void update(ChildCareLeaveRemainingInfo obj, String cId) {
		Optional<KrcmtChildCareHDInfo> entityOpt = this.queryProxy().find(obj.getSId(), KrcmtChildCareHDInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtChildCareHDInfo entity = entityOpt.get();
			entity.setCId(cId);
			entity.setUseAtr(obj.isUseClassification() ? 1 : 0);
			entity.setUpperLimSetAtr(obj.getUpperlimitSetting().value);
			entity.setMaxDayNextFiscalYear(
					obj.getMaxDayForNextFiscalYear().isPresent() ? obj.getMaxDayForNextFiscalYear().get().v() : null);
			entity.setMaxDayThisFiscalYear(
					obj.getMaxDayForThisFiscalYear().isPresent() ? obj.getMaxDayForThisFiscalYear().get().v() : null);
			this.commandProxy().update(entity);
		}
	}

	@Override
	public List<ChildCareLeaveRemainingInfo> getChildCareByEmpIdsAndCid(String cid, List<String> empIds) {
		List<ChildCareLeaveRemainingInfo> result = new ArrayList<>();
		CollectionUtil.split(empIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCMT_CHILD_CARE_HD_INFO WHERE  CID = ?  AND SID IN ("
					+ NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {

				stmt.setString(1, cid);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(2 + i, subList.get(i));
				}

				List<ChildCareLeaveRemainingInfo> data = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					return ChildCareLeaveRemainingInfo.createChildCareLeaveInfo(rec.getString("SID"),
							rec.getInt("USE_ATR"), rec.getInt("UPPER_LIM_SET_ART"),
							rec.getDouble("MAX_DAY_THIS_FISCAL_YEAR"), rec.getDouble("MAX_DAY_NEXT_FISCAL_YEAR"));
				});
				result.addAll(data);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}

	@Override
	public void addAll(String cid, List<ChildCareLeaveRemainingInfo> domains) {
		String INS_SQL = "INSERT INTO KRCMT_CHILD_CARE_HD_INFO (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
				+ " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG,"
				+ " SID, CID, USE_ATR, UPPER_LIM_SET_ART, MAX_DAY_THIS_FISCAL_YEAR, MAX_DAY_NEXT_FISCAL_YEAR)"
				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
				+ " SID_VAL, CID_VAL, USE_ATR_VAL, UPPER_LIM_SET_ART_VAL, MAX_DAY_THIS_FISCAL_YEAR_VAL, MAX_DAY_NEXT_FISCAL_YEAR_VAL);";
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
			sql = sql.replace("USE_ATR_VAL", c.isUseClassification() ? "1" : "0");
			sql = sql.replace("UPPER_LIM_SET_ART_VAL", ""+c.getUpperlimitSetting().value+"");
			sql = sql.replace("MAX_DAY_THIS_FISCAL_YEAR_VAL", c.getMaxDayForThisFiscalYear().isPresent() ? ""+ c.getMaxDayForThisFiscalYear().get().v()+"" : "null");
			sql = sql.replace("MAX_DAY_NEXT_FISCAL_YEAR_VAL", c.getMaxDayForNextFiscalYear().isPresent() ? ""+ c.getMaxDayForNextFiscalYear().get().v() + "" : "null");
			sb.append(sql);
		});

		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);

	}

	@Override
	public void updateAll(String cid, List<ChildCareLeaveRemainingInfo> domains) {
		String UP_SQL = "UPDATE KRCMT_CHILD_CARE_HD_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
				+ " USE_ATR = USE_ATR_VAL, UPPER_LIM_SET_ART = UPPER_LIM_SET_ART_VAL, MAX_DAY_THIS_FISCAL_YEAR = MAX_DAY_THIS_FISCAL_YEAR_VAL, MAX_DAY_NEXT_FISCAL_YEAR = MAX_DAY_NEXT_FISCAL_YEAR_VAL"
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
			sql = sql.replace("USE_ATR_VAL", c.isUseClassification() ? "1" : "0");
			sql = sql.replace("UPPER_LIM_SET_ART_VAL", ""+c.getUpperlimitSetting().value+"");
			sql = sql.replace("MAX_DAY_THIS_FISCAL_YEAR_VAL", c.getMaxDayForThisFiscalYear().isPresent() ? ""+ c.getMaxDayForThisFiscalYear().get().v()+"" : "null");
			sql = sql.replace("MAX_DAY_NEXT_FISCAL_YEAR_VAL", c.getMaxDayForNextFiscalYear().isPresent() ? ""+ c.getMaxDayForNextFiscalYear().get().v() + "" : "null");

			sb.append(sql);
		});

		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);

	}
}
**/
