package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.HalfdayAnnualLeaveMax;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.MaxMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.MaxTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.TimeAnnualLeaveMax;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

/**
 * 年休上限データ
 * @author masaaki_jinno
 *
 */
public class TestAnnLeaMaxDataRepository_1  extends JpaRepository implements AnnLeaMaxDataRepository {

	@Override
	public Optional<AnnualLeaveMaxData> get(String employeeId) {
//		Optional<KrcmtAnnLeaMax> entityOpt = this.queryProxy().find(employeeId, KrcmtAnnLeaMax.class);
//		if (entityOpt.isPresent()) {
//			KrcmtAnnLeaMax ent = entityOpt.get();
//			return Optional.of(AnnualLeaveMaxData.createFromJavaType(ent.sid, ent.maxTimes, ent.usedTimes,
//					ent.maxMinutes, ent.usedMinutes));
//		}
//		return Optional.empty();
//		System.out.print("要実装");
//		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
//	    System.out.println(className);
//	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
//        System.out.println(methodName);
//        return Optional.empty();
        
//        // 社員ID
//        String employeeId = "";
        
        // 会社ID
		String companyId = "";

		// 半日年休上限
		HalfdayAnnualLeaveMax halfdayAnnualLeaveMax = new HalfdayAnnualLeaveMax();
		MaxTimes maxTimes = new MaxTimes(5);
		UsedTimes usedTimes = new UsedTimes(0);
		halfdayAnnualLeaveMax.update(maxTimes, usedTimes);
		
		// 時間年休上限
		TimeAnnualLeaveMax timeAnnualLeaveMax = new TimeAnnualLeaveMax();
		MaxMinutes maxMinutes = new MaxMinutes(40); // 上限時間
		UsedMinutes usedMinutes = new UsedMinutes(0); // 使用時間
		timeAnnualLeaveMax.update(maxMinutes, usedMinutes);
		
		// 年休上限データ
		AnnualLeaveMaxData a = TestAnnualLeaveMaxData.createAnnualLeaveMaxData(
				employeeId, companyId, 
				Optional.of(halfdayAnnualLeaveMax),
				Optional.of(timeAnnualLeaveMax));
        
        return Optional.of(a);
	}

	@Override
	public void add(AnnualLeaveMaxData maxData) {
//		KrcmtAnnLeaMax entity = new KrcmtAnnLeaMax();
//		entity.sid = maxData.getEmployeeId();
//		entity.cid = maxData.getCompanyId();
//		if ( maxData.getHalfdayAnnualLeaveMax().isPresent()) {
//			HalfdayAnnualLeaveMax halfday = maxData.getHalfdayAnnualLeaveMax().get();
//			entity.maxTimes = halfday.getMaxTimes().v();
//			entity.usedTimes = halfday.getUsedTimes().v();
//			entity.remainingTimes = halfday.getRemainingTimes().v();
//		}
//		if ( maxData.getTimeAnnualLeaveMax().isPresent()) {
//			TimeAnnualLeaveMax time = maxData.getTimeAnnualLeaveMax().get();
//			entity.maxMinutes = time.getMaxMinutes().v();
//			entity.usedMinutes = time.getUsedMinutes().v();
//			entity.remainingMinutes = time.getRemainingMinutes().v();
//		}
//		this.commandProxy().insert(entity);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        
	}

	@Override
	public void update(AnnualLeaveMaxData maxData) {
//		Optional<KrcmtAnnLeaMax> entityOpt = this.queryProxy().find(maxData.getEmployeeId(), KrcmtAnnLeaMax.class);
//		if (entityOpt.isPresent()) {
//			KrcmtAnnLeaMax entity = entityOpt.get();
//			if ( maxData.getHalfdayAnnualLeaveMax().isPresent()) {
//				HalfdayAnnualLeaveMax halfday = maxData.getHalfdayAnnualLeaveMax().get();
//				entity.maxTimes = halfday.getMaxTimes().v();
//				entity.usedTimes = halfday.getUsedTimes().v();
//				entity.remainingTimes = halfday.getRemainingTimes().v();
//			} else {
//				entity.maxTimes = null;
//				entity.usedTimes = null;
//				entity.remainingTimes = null;
//			}
//			if ( maxData.getTimeAnnualLeaveMax().isPresent()) {
//				TimeAnnualLeaveMax time = maxData.getTimeAnnualLeaveMax().get();
//				entity.maxMinutes = time.getMaxMinutes().v();
//				entity.usedMinutes = time.getUsedMinutes().v();
//				entity.remainingMinutes = time.getRemainingMinutes().v();
//			} else {
//				entity.maxMinutes = null;
//				entity.usedMinutes = null;
//				entity.remainingMinutes = null;
//			}
//			this.commandProxy().update(entity);
//		} else {
//			add(maxData);
//		}
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        
	}

	@Override
	public void delete(String employeeId) {
//		Optional<KrcmtAnnLeaMax> entityOpt = this.queryProxy().find(employeeId, KrcmtAnnLeaMax.class);
//		if (entityOpt.isPresent()) {
//			KrcmtAnnLeaMax entity = entityOpt.get();
//			this.commandProxy().remove(entity);
//		}
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}

	@Override
	public List<AnnualLeaveMaxData> getAll(String cid, List<String> sids) {
//		List<AnnualLeaveMaxData> result = new ArrayList<>();
//		
//		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
//			String sql = "SELECT * FROM KRCMT_ANNLEA_MAX WHERE CID = ? AND SID IN (" + NtsStatement.In.createParamsString(subList) + ")";
//			
//			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
//				stmt.setString( 1, cid);
//				for (int i = 0; i < subList.size(); i++) {
//					stmt.setString( 2 + i, subList.get(i));
//				}
//				
//				List<AnnualLeaveMaxData> annualLeavelst = new NtsResultSet(stmt.executeQuery()).getList(r -> {
//					return AnnualLeaveMaxData.createFromJavaType( r.getString("SID"),  r.getInt("MAX_TIMES"), r.getInt("USED_TIMES"),
//							r.getInt("MAX_MINUTES"), r.getInt("USED_MINUTES"));
//				});
//				result.addAll(annualLeavelst);
//				
//			}catch (SQLException e) {
//				throw new RuntimeException(e);
//			}
//		});
//		
//		return result;
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}

	@Override
	public void addAll(List<AnnualLeaveMaxData> domains) {
//		String INS_SQL = "INSERT INTO KRCMT_ANNLEA_MAX (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
//				+ " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG," + " SID, CID, MAX_TIMES, USED_TIMES, REMAINING_TIMES,"
//				+ " MAX_MINUTES, USED_MINUTES, REMAINING_MINUTES)"
//				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
//				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL," 
//				+ " SID_VAL, CID_VAL, MAX_TIMES_VAL, USED_TIMES_VAL, REMAINING_TIMES_VAL,"
//				+ " MAX_MINUTES_VAL, USED_MINUTES_VAL,  REMAINING_MINUTES_VAL); ";
//		String insCcd = AppContexts.user().companyCode();
//		String insScd = AppContexts.user().employeeCode();
//		String insPg = AppContexts.programId();
//		
//		String updCcd = insCcd;
//		String updScd = insScd;
//		String updPg = insPg;
//		StringBuilder sb = new StringBuilder();
//		domains.stream().forEach(c ->{
//			String sql = INS_SQL;
//			sql = sql.replace("INS_DATE_VAL", "'" + GeneralDateTime.now() + "'");
//			sql = sql.replace("INS_CCD_VAL", "'" + insCcd + "'");
//			sql = sql.replace("INS_SCD_VAL", "'" + insScd + "'");
//			sql = sql.replace("INS_PG_VAL", "'" + insPg + "'");
//
//			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
//			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
//			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
//			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");
//			
//			sql = sql.replace("SID_VAL", "'" + c.getEmployeeId() + "'");
//			sql = sql.replace("CID_VAL", "'"+ c.getCompanyId() + "'");
//			
//			Optional<HalfdayAnnualLeaveMax> maxData = c.getHalfdayAnnualLeaveMax();
//			if ( maxData.isPresent()) {
//				HalfdayAnnualLeaveMax halfday = maxData.get();
//				sql = sql.replace("MAX_TIMES_VAL",  ""+ halfday.getMaxTimes().v()+ "");
//				sql = sql.replace("USED_TIMES_VAL", "" + halfday.getUsedTimes().v()+ "");
//				sql = sql.replace("REMAINING_TIMES_VAL", "" + halfday.getRemainingTimes().v()+ "");
//				
//			}else {
//				sql = sql.replace("MAX_TIMES_VAL",  "null");
//				sql = sql.replace("USED_TIMES_VAL", "null");
//				sql = sql.replace("REMAINING_TIMES_VAL", "null");
//			}
//			
//			Optional<TimeAnnualLeaveMax> timeAnnualLeaveMax = c.getTimeAnnualLeaveMax();
//			if ( timeAnnualLeaveMax.isPresent()) {
//				TimeAnnualLeaveMax time = timeAnnualLeaveMax.get();
//				sql = sql.replace("MAX_MINUTES_VAL", "" + time.getMaxMinutes().v()+ "");
//				sql = sql.replace("USED_MINUTES_VAL", "" + time.getUsedMinutes().v()+ "");
//				sql = sql.replace("REMAINING_MINUTES_VAL", "" + time.getRemainingMinutes().v()+ "");
//			}else {
//				sql = sql.replace("MAX_MINUTES_VAL", "null");
//				sql = sql.replace("USED_MINUTES_VAL", "null");
//				sql = sql.replace("REMAINING_MINUTES_VAL", "null");
//			}
//			sb.append(sql);
//		});
//		
//		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
//		System.out.println(records);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        
		
	}

	@Override
	public void updateAll(List<AnnualLeaveMaxData> domains) {
//		String UP_SQL = "UPDATE KRCMT_ANNLEA_MAX SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
//				+ " MAX_TIMES = MAX_TIMES_VAL , USED_TIMES = USED_TIMES_VAL, REMAINING_TIMES = REMAINING_TIMES_VAL, "
//				+ " MAX_MINUTES = MAX_MINUTES_VAL, USED_MINUTES = USED_MINUTES_VAL, REMAINING_MINUTES = REMAINING_MINUTES_VAL"
//				+ " WHERE SID = SID_VAL AND CID = CID_VAL;";
//		String updCcd = AppContexts.user().companyCode();
//		String updScd = AppContexts.user().employeeCode();
//		String updPg = AppContexts.programId();
//		StringBuilder sb = new StringBuilder();
//		domains.stream().forEach(c ->{
//			String sql = UP_SQL;
//			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
//			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
//			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
//			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");
//			
//			sql = sql.replace("SID_VAL", "'" + c.getEmployeeId() + "'");
//			sql = sql.replace("CID_VAL", "'"+ c.getCompanyId() + "'");
//			
//			Optional<HalfdayAnnualLeaveMax> maxData = c.getHalfdayAnnualLeaveMax();
//			if ( maxData.isPresent()) {
//				HalfdayAnnualLeaveMax halfday = maxData.get();
//				sql = sql.replace("MAX_TIMES_VAL",  ""+ halfday.getMaxTimes().v()+ "");
//				sql = sql.replace("USED_TIMES_VAL", "" + halfday.getUsedTimes().v()+ "");
//				sql = sql.replace("REMAINING_TIMES_VAL", "" + halfday.getRemainingTimes().v()+ "");
//				
//			}else {
//				sql = sql.replace("MAX_TIMES_VAL",  "null");
//				sql = sql.replace("USED_TIMES_VAL", "null");
//				sql = sql.replace("REMAINING_TIMES_VAL", "null");
//			}
//			
//			Optional<TimeAnnualLeaveMax> timeAnnualLeaveMax = c.getTimeAnnualLeaveMax();
//			if ( timeAnnualLeaveMax.isPresent()) {
//				TimeAnnualLeaveMax time = timeAnnualLeaveMax.get();
//				sql = sql.replace("MAX_MINUTES_VAL", "" + time.getMaxMinutes().v()+ "");
//				sql = sql.replace("USED_MINUTES_VAL", "" + time.getUsedMinutes().v()+ "");
//				sql = sql.replace("REMAINING_MINUTES_VAL", "" + time.getRemainingMinutes().v()+ "");
//			}else {
//				sql = sql.replace("MAX_MINUTES_VAL", "null");
//				sql = sql.replace("USED_MINUTES_VAL", "null");
//				sql = sql.replace("REMAINING_MINUTES_VAL", "null");
//			}
//			sb.append(sql);
//		});
//		
//		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
//		System.out.println(records);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
		
	}

}
