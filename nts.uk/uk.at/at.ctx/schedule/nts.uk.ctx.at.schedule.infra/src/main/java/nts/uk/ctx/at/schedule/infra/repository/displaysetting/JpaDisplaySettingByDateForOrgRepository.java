package nts.uk.ctx.at.schedule.infra.repository.displaysetting;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplayRangeType;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDate;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDateForOrg;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDateForOrgRepository;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplayStartTime;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.KscmtDispsetBydateOrg;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaDisplaySettingByDateForOrgRepository extends JpaRepository implements DisplaySettingByDateForOrgRepository {
	
	@Override
	public Optional<DisplaySettingByDateForOrg> get (String companyId, TargetOrgIdenInfor targetOrg) {
		
		String sql = "SELECT * FROM KSCMT_DISPSET_BYDATE_ORG"
				+ " WHERE CID = ?"
				+ " AND TARGET_UNIT = ?"
				+ " AND TARGET_ID = ?";
		
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, companyId);
			stmt.setInt(2, targetOrg.getUnit().value);
			stmt.setString(3, targetOrg.getTargetId());
			
			return new NtsResultSet(stmt.executeQuery()).getSingle(rec -> {
				return new DisplaySettingByDateForOrg (targetOrg
						, new DisplaySettingByDate(DisplayRangeType.of(rec.getInt("RANGE_ATR"))
								, new DisplayStartTime (rec.getInt("START_CLOCK"))
								, new DisplayStartTime (rec.getInt("INIT_START_CLOCK"))));
			});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void insert (String companyId, DisplaySettingByDateForOrg dispSetorg) {
		this.commandProxy().insert(KscmtDispsetBydateOrg.toEntity(companyId, dispSetorg));
	}
	
	@Override
	public void update (String companyId, DisplaySettingByDateForOrg dispSetorg) {
		
		KscmtDispsetBydateOrg entity = KscmtDispsetBydateOrg.toEntity(companyId, dispSetorg);
		
		KscmtDispsetBydateOrg upData = this.queryProxy()
				.find(entity.pk, KscmtDispsetBydateOrg.class)
				.get();
		
		upData.setRangeAtr(dispSetorg.getDispSetting().getDispRange().value);
		upData.setStartClock(dispSetorg.getDispSetting().getDispStart().v());
		upData.setInitStartClock(dispSetorg.getDispSetting().getInitDispStart().v());
		
		this.commandProxy().update(upData);
	}
}
