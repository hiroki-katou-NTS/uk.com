package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcmtAnnLeaBasicInfo;

@Stateless
public class JpaAnnLeaEmpBasicInfoRepo extends JpaRepository implements AnnLeaEmpBasicInfoRepository {
	
//	private static final String SELECT_ALL = "SELECT si FROM KrcmtAnnLeaBasicInfo si WHERE si.sid IN :listEmployeeId ";
	@Override
	public Optional<AnnualLeaveEmpBasicInfo> get(String employeeId) {
		Optional<KrcmtAnnLeaBasicInfo> entityOpt = this.queryProxy().find(employeeId, KrcmtAnnLeaBasicInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtAnnLeaBasicInfo ent = entityOpt.get();
			return Optional.of(AnnualLeaveEmpBasicInfo.createFromJavaType(ent.sid, ent.workDaysPerYear,
					ent.workDaysBeforeIntro, ent.grantTableCode, ent.grantStandardDate));
		}
		return Optional.empty();
	}
	
	// chuyển sang jdbc, tăng tốc độ
	public List<AnnualLeaveEmpBasicInfo> getAll(String cid, List<String> sids) {
		List<AnnualLeaveEmpBasicInfo> result = new ArrayList<>();
		
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCMT_ANNLEA_INFO WHERE CID = ? AND SID IN (" + NtsStatement.In.createParamsString(subList) + ")";
			
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString( 1, cid);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString( i + 1, subList.get(i));
				}
				
				List<AnnualLeaveEmpBasicInfo> annualLeavelst = new NtsResultSet(stmt.executeQuery()).getList(r -> {
					KrcmtAnnLeaBasicInfo entity= new KrcmtAnnLeaBasicInfo();
					entity.sid = r.getString("SID");
					entity.cid = r.getString("CID");
					entity.workDaysPerYear = r.getInt("WORK_DAYS_PER_YEAR");
					entity.workDaysBeforeIntro = r.getInt("WORK_DAYS_BEFORE_INTRO");
					entity.grantTableCode = r.getString("GRANT_TABLE_CODE");
					entity.grantStandardDate = r.getGeneralDate("GRANT_STANDARD_DATE");
					return entity.toDomain();
				});
				result.addAll(annualLeavelst);
				
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		
		return result;
	}

	@Override
	public void add(AnnualLeaveEmpBasicInfo basicInfo) {
		KrcmtAnnLeaBasicInfo entity = new KrcmtAnnLeaBasicInfo();
		entity.sid = basicInfo.getEmployeeId();
		entity.cid = basicInfo.getCompanyId();
		entity.workDaysPerYear = basicInfo.getWorkingDaysPerYear().isPresent()
				? basicInfo.getWorkingDaysPerYear().get().v() : null;
		entity.workDaysBeforeIntro = basicInfo.getWorkingDayBeforeIntroduction().isPresent()
				? basicInfo.getWorkingDayBeforeIntroduction().get().v() : null; 
		entity.grantTableCode = basicInfo.getGrantRule().getGrantTableCode().v();
		entity.grantStandardDate = basicInfo.getGrantRule().getGrantStandardDate();
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(AnnualLeaveEmpBasicInfo basicInfo) {
		Optional<KrcmtAnnLeaBasicInfo> entityOpt = this.queryProxy().find(basicInfo.getEmployeeId(),
				KrcmtAnnLeaBasicInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtAnnLeaBasicInfo ent = entityOpt.get();
			ent.workDaysPerYear = basicInfo.getWorkingDaysPerYear().isPresent()
					? basicInfo.getWorkingDaysPerYear().get().v() : null;
			ent.workDaysBeforeIntro = basicInfo.getWorkingDayBeforeIntroduction().isPresent()
					? basicInfo.getWorkingDayBeforeIntroduction().get().v() : null;
			ent.grantTableCode = basicInfo.getGrantRule().getGrantTableCode().v();
			ent.grantStandardDate = basicInfo.getGrantRule().getGrantStandardDate();
			this.commandProxy().update(ent);
		} else {
			add(basicInfo);
		}
	}

	@Override
	public void delete(String employeeId) {
		Optional<KrcmtAnnLeaBasicInfo> entityOpt = this.queryProxy().find(employeeId, KrcmtAnnLeaBasicInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtAnnLeaBasicInfo ent = entityOpt.get();
			this.commandProxy().remove(ent);
		}

	}
}
