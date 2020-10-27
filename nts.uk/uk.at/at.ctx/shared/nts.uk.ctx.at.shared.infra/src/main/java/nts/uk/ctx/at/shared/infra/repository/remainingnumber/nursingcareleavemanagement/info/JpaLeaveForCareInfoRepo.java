package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.info;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.ChildCareLeaveRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveDataInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.LeaveForCareInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.LeaveForCareInfoRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcmtCareHDInfo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaLeaveForCareInfoRepo extends JpaRepository implements LeaveForCareInfoRepository {
	
	private final static String SELECT_CARE_INFO_DATA_BY_SID = String.join(" ",
			"SELECT i.SID AS ISID, i.USE_ATR AS IUSE_ATR, i.UPPER_LIM_SET_ART AS IUPPER_LIM_SET_ART, i.MAX_DAY_THIS_FISCAL_YEAR AS IMAX_DAY_THIS_FISCAL_YEAR, i.MAX_DAY_NEXT_FISCAL_YEAR as IMAX_DAY_NEXT_FISCAL_YEAR,",
			"d.SID AS DSID, d.USED_DAYS AS DUSED_DAYS,",
			"ci.SID AS CISID, ci.USE_ATR AS CIUSE_ATR, i.UPPER_LIM_SET_ART AS CIUPPER_LIM_SET_ART, i.MAX_DAY_THIS_FISCAL_YEAR AS CIMAX_DAY_THIS_FISCAL_YEAR, i.MAX_DAY_NEXT_FISCAL_YEAR as CIMAX_DAY_NEXT_FISCAL_YEAR,",
			"cd.SID AS CDSID, cd.USED_DAYS as CDUSED_DAYS",
			"FROM KRCDT_HDNURSING_INFO i",
			"LEFT JOIN KRCDT_CARE_HD_REMAIN d",
			"ON i.SID = d.SID AND i.CID = '{cid}'",
			"LEFT JOIN KRCMT_CHILD_CARE_HD_INFO ci",
			"ON ci.SID = i.SID AND ci.CID = '{cid}'",
			"LEFT JOIN KRCDT_CHILDCARE_HD_REMAIN cd",
			"ON cd.SID = i.SID AND cd.CID = '{cid}'",
			"WHERE i.SID = '{sid}'"); 
	
	@Override
	public Optional<LeaveForCareInfo> getCareByEmpId(String empId) {
		Optional<KrcmtCareHDInfo> entityOpt = this.queryProxy().find(empId, KrcmtCareHDInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtCareHDInfo entity = entityOpt.get();
			return Optional.of(LeaveForCareInfo.createCareLeaveInfo(entity.getSId(), entity.getUseAtr(),
					entity.getUpperLimSetAtr(), entity.getMaxDayThisFiscalYear(), entity.getMaxDayNextFiscalYear()));
		}
		return Optional.empty();
	}

	@Override
	public void add(LeaveForCareInfo obj, String cId) {
		KrcmtCareHDInfo entity = new KrcmtCareHDInfo(obj.getSId(), cId, obj.isUseClassification() ? 1 : 0,
				obj.getUpperlimitSetting().value,
				obj.getMaxDayForThisFiscalYear().isPresent() ? obj.getMaxDayForThisFiscalYear().get().v() : null,
				obj.getMaxDayForNextFiscalYear().isPresent() ? obj.getMaxDayForNextFiscalYear().get().v() : null);
		this.commandProxy().insert(entity);

	}

	@Override
	public void update(LeaveForCareInfo obj, String cId) {
		Optional<KrcmtCareHDInfo> entityOpt = this.queryProxy().find(obj.getSId(), KrcmtCareHDInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtCareHDInfo entity = entityOpt.get();
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
	public Optional<CareLeaveDataInfo> getCareInfoDataBysId(String empId) {
		String sqlString = SELECT_CARE_INFO_DATA_BY_SID
				.replaceAll("\\{sid\\}", empId)
				.replaceAll("\\{cid\\}", AppContexts.user().companyId());
		
		@SuppressWarnings("unchecked")
		List<Object[]> queryResult =  this.getEntityManager().createNativeQuery(sqlString).getResultList();
		
		if(queryResult != null && queryResult.get(0) != null) {
			Object[] record = queryResult.get(0);
			
			if(record[0] != null && record[5] != null && record[7] != null && record[12] != null) {
				LeaveForCareInfo leaveForCareInfo = LeaveForCareInfo
						.createCareLeaveInfo(record[0].toString(), 
								Integer.parseInt(record[1].toString()), 
								Integer.parseInt(record[2].toString()),
								Double.parseDouble(record[3].toString()),
								Double.parseDouble(record[4].toString()));
				
				LeaveForCareData leaveForCareData = LeaveForCareData.getCareHDRemaining(record[5].toString(), Double.parseDouble(record[6].toString()));
				
				ChildCareLeaveRemainingInfo childCareLeaveRemainingInfo =  ChildCareLeaveRemainingInfo.createChildCareLeaveInfo(record[7].toString(),  
						Integer.parseInt(record[7].toString()), 
						Integer.parseInt(record[8].toString()),
						Double.parseDouble(record[10].toString()),
						Double.parseDouble(record[11].toString()));
				
				ChildCareLeaveRemainingData childCareLeaveRemainingData = ChildCareLeaveRemainingData.getChildCareHDRemaining(record[12].toString(), Double.parseDouble(record[13].toString()));
				
				return Optional.ofNullable(new CareLeaveDataInfo(leaveForCareInfo, leaveForCareData, childCareLeaveRemainingInfo , childCareLeaveRemainingData));
			}
		}
		
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	
	@Override
	public List<LeaveForCareInfo> getCareByEmpIdsAndCid(String cid, List<String> empIds) {
		List<LeaveForCareInfo> result = new ArrayList<>();
		CollectionUtil.split(empIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCDT_HDNURSING_INFO WHERE  CID = ?  AND SID IN ("
					+ NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {

				stmt.setString(1, cid);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(2 + i, subList.get(i));
				}

				List<LeaveForCareInfo> data = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					return LeaveForCareInfo.createCareLeaveInfo(rec.getString("SID"),
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
	public void addAll(String cid, List<LeaveForCareInfo> domains) {
		String INS_SQL = "INSERT INTO KRCDT_HDNURSING_INFO (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
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
	public void updateAll(String cid, List<LeaveForCareInfo> domains) {
		String UP_SQL = "UPDATE KRCDT_HDNURSING_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
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

	@Override
	public List<CareLeaveDataInfo> getAllCareInfoDataBysId(String cid, List<String> sids) {
		List<CareLeaveDataInfo> result = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = String.join(" ",
					"SELECT i.SID AS ISID, i.USE_ATR AS IUSE_ATR, i.UPPER_LIM_SET_ART AS IUPPER_LIM_SET_ART, i.MAX_DAY_THIS_FISCAL_YEAR AS IMAX_DAY_THIS_FISCAL_YEAR, i.MAX_DAY_NEXT_FISCAL_YEAR as IMAX_DAY_NEXT_FISCAL_YEAR,",
					"d.SID AS DSID, d.USED_DAYS AS DUSED_DAYS,",
					"ci.SID AS CISID, ci.USE_ATR AS CIUSE_ATR, i.UPPER_LIM_SET_ART AS CIUPPER_LIM_SET_ART, i.MAX_DAY_THIS_FISCAL_YEAR AS CIMAX_DAY_THIS_FISCAL_YEAR, i.MAX_DAY_NEXT_FISCAL_YEAR as CIMAX_DAY_NEXT_FISCAL_YEAR,",
					"cd.SID AS CDSID, cd.USED_DAYS as CDUSED_DAYS",
					"FROM KRCDT_HDNURSING_INFO i",
					"LEFT JOIN KRCDT_CARE_HD_REMAIN d",
					"ON i.SID = d.SID AND i.CID = CID_VAL",
					"LEFT JOIN KRCMT_CHILD_CARE_HD_INFO ci",
					"ON ci.SID = i.SID AND ci.CID = CID_VAL",
					"LEFT JOIN KRCDT_CHILDCARE_HD_REMAIN cd",
					"ON cd.SID = i.SID AND cd.CID = CID_VAL",
					"WHERE i.SID IN (",  NtsStatement.In.createParamsString(subList) + ")");
			sql = sql.replace("CID_VAL", "'"+ cid + "'");
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				//stmt.setString(1, cid);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(1 + i, subList.get(i));
				}

				List<CareLeaveDataInfo> data = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					LeaveForCareInfo leaveForCareInfo =  LeaveForCareInfo.createCareLeaveInfo(
							rec.getString("ISID"),
							rec.getInt("IUSE_ATR"), 
							rec.getInt("IUPPER_LIM_SET_ART"),
							rec.getDouble("IMAX_DAY_THIS_FISCAL_YEAR"), 
							rec.getDouble("IMAX_DAY_NEXT_FISCAL_YEAR"));
					
					LeaveForCareData leaveForCareData = LeaveForCareData.getCareHDRemaining(rec.getString("DSID"), rec.getDouble("DUSED_DAYS"));
					
					ChildCareLeaveRemainingInfo childCareLeaveRemainingInfo =  ChildCareLeaveRemainingInfo.createChildCareLeaveInfo(
							rec.getString("CISID"),  
							rec.getInt("CIUSE_ATR"), 
							rec.getInt("CIUPPER_LIM_SET_ART"),
							rec.getDouble("CIMAX_DAY_THIS_FISCAL_YEAR"),
							rec.getDouble("CIMAX_DAY_NEXT_FISCAL_YEAR"));
					
					ChildCareLeaveRemainingData childCareLeaveRemainingData = ChildCareLeaveRemainingData.getChildCareHDRemaining(rec.getString("CDSID"), rec.getDouble("CDUSED_DAYS"));
					return new CareLeaveDataInfo(leaveForCareInfo, leaveForCareData, childCareLeaveRemainingInfo , childCareLeaveRemainingData);
				});
				result.addAll(data);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}

	@Override
	public List<CareLeaveDataInfo> getAllCareInfoDataBysIdCps013(String cid, List<String> sids,
			Map<String, Object> enums) {

		List<CareLeaveDataInfo> result = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = String.join(" ",
					"SELECT i.SID AS ISID, i.USE_ATR AS IUSE_ATR, i.UPPER_LIM_SET_ART AS IUPPER_LIM_SET_ART, i.MAX_DAY_THIS_FISCAL_YEAR AS IMAX_DAY_THIS_FISCAL_YEAR, i.MAX_DAY_NEXT_FISCAL_YEAR as IMAX_DAY_NEXT_FISCAL_YEAR,",
					"d.SID AS DSID, d.USED_DAYS AS DUSED_DAYS,",
					"ci.SID AS CISID, ci.USE_ATR AS CIUSE_ATR, ci.UPPER_LIM_SET_ART AS CIUPPER_LIM_SET_ART, ci.MAX_DAY_THIS_FISCAL_YEAR AS CIMAX_DAY_THIS_FISCAL_YEAR, ci.MAX_DAY_NEXT_FISCAL_YEAR as CIMAX_DAY_NEXT_FISCAL_YEAR,",
					"cd.SID AS CDSID, cd.USED_DAYS as CDUSED_DAYS",
					"FROM KRCDT_HDNURSING_INFO i",
					"LEFT JOIN KRCDT_CARE_HD_REMAIN d",
					"ON i.SID = d.SID AND i.CID = CID_VAL",
					"LEFT JOIN KRCMT_CHILD_CARE_HD_INFO ci",
					"ON ci.SID = i.SID AND ci.CID = CID_VAL",
					"LEFT JOIN KRCDT_CHILDCARE_HD_REMAIN cd",
					"ON cd.SID = i.SID AND cd.CID = CID_VAL",
					"WHERE i.SID IN (",  NtsStatement.In.createParamsString(subList) + ")");
			sql = sql.replace("CID_VAL", "'"+ cid + "'");
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				//stmt.setString(1, cid);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(1 + i, subList.get(i));
				}

				List<CareLeaveDataInfo> data = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					LeaveForCareInfo leaveForCareInfo =  LeaveForCareInfo.createCareLeaveInfoCps013(
							rec.getString("ISID"),
							rec.getInt("IUSE_ATR"), 
							rec.getInt("IUPPER_LIM_SET_ART"),
							rec.getDouble("IMAX_DAY_THIS_FISCAL_YEAR"), 
							rec.getDouble("IMAX_DAY_NEXT_FISCAL_YEAR"));
					
					enums.put("IS00380", rec.getInt("IUSE_ATR"));
					enums.put("IS00381", rec.getInt("IUPPER_LIM_SET_ART"));
					
					LeaveForCareData leaveForCareData = LeaveForCareData.getCareHDRemaining(rec.getString("DSID"), rec.getDouble("DUSED_DAYS"));
					
					ChildCareLeaveRemainingInfo childCareLeaveRemainingInfo =  ChildCareLeaveRemainingInfo.createChildCareLeaveInfoCps013(
							rec.getString("CISID"),  
							rec.getInt("CIUSE_ATR"), 
							rec.getInt("CIUPPER_LIM_SET_ART"),
							rec.getDouble("CIMAX_DAY_THIS_FISCAL_YEAR"),
							rec.getDouble("CIMAX_DAY_NEXT_FISCAL_YEAR"));
					enums.put("IS00375", rec.getInt("CIUSE_ATR"));
					enums.put("IS00376", rec.getInt("CIUPPER_LIM_SET_ART"));
					
					ChildCareLeaveRemainingData childCareLeaveRemainingData = ChildCareLeaveRemainingData.getChildCareHDRemaining(rec.getString("CDSID"), rec.getDouble("CDUSED_DAYS"));
					return new CareLeaveDataInfo(leaveForCareInfo, leaveForCareData, childCareLeaveRemainingInfo , childCareLeaveRemainingData);
				});
				result.addAll(data);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}
}
