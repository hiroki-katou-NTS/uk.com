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
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDateForCmp;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDateForCmpRepository;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplayStartTime;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.KscmtDispsetBydateCmp;

/**
 * 
 * @author hiroko_miura
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaDisplaySettingByDateForCmpRepository extends JpaRepository implements DisplaySettingByDateForCmpRepository {
	
	@Override
	public Optional<DisplaySettingByDateForCmp> get(String companyId){
		
		String sql = "SELECT * FROM KSCMT_DISPSET_BYDATE_CMP WHERE CID = ?";
		
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, companyId);
			
			return new NtsResultSet(stmt.executeQuery()).getSingle(rec -> {
				return new DisplaySettingByDateForCmp(
						new DisplaySettingByDate(DisplayRangeType.of(rec.getInt("RANGE_ATR"))
								, new DisplayStartTime (rec.getInt("START_CLOCK"))
								, new DisplayStartTime (rec.getInt("INIT_START_CLOCK"))));
			});
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void insert (String companyId, DisplaySettingByDateForCmp dispSetcmp) {
		this.commandProxy().insert(KscmtDispsetBydateCmp.toEntity(companyId, dispSetcmp));
	}
	
	@Override
	public void update (String companyId, DisplaySettingByDateForCmp dispSetcmp) {
		
		KscmtDispsetBydateCmp entity = KscmtDispsetBydateCmp.toEntity(companyId, dispSetcmp);
		
		KscmtDispsetBydateCmp upData = this.queryProxy()
				.find(entity.companyId, KscmtDispsetBydateCmp.class)
				.get();
		
		upData.setRangeAtr(dispSetcmp.getDispSetting().getDispRange().value);
		upData.setStartClock(dispSetcmp.getDispSetting().getDispStart().v());
		upData.setInitStartClock(dispSetcmp.getDispSetting().getInitDispStart().v());
		
		this.commandProxy().update(upData);
	}
}
