package nts.uk.ctx.at.schedule.infra.repository.displaysetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDateForCmp;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDateForCmpRepository;
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
		
		String sql = "SELECT * FROM KSCMT_DISPSET_BYDATE_CMP WHERE CID = @companyId";
		
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyId", companyId)
				.getSingle(x -> KscmtDispsetBydateCmp.MAPPER.toEntity(x).toDomain());
	}
	
	@Override
	public void insert (String companyId, DisplaySettingByDateForCmp dispSetcmp) {
		this.commandProxy().insert(KscmtDispsetBydateCmp.of(companyId, dispSetcmp));
	}
	
	@Override
	public void update (String companyId, DisplaySettingByDateForCmp dispSetcmp) {
		
		KscmtDispsetBydateCmp entity = KscmtDispsetBydateCmp.of(companyId, dispSetcmp);
		
		KscmtDispsetBydateCmp upData = this.queryProxy()
				.find(entity.companyId, KscmtDispsetBydateCmp.class)
				.get();
		
		upData.setRangeAtr(dispSetcmp.getDispSetting().getDispRange().value);
		upData.setStartClock(dispSetcmp.getDispSetting().getDispStart().v());
		upData.setInitStartClock(dispSetcmp.getDispSetting().getInitDispStart().v());
		
		this.commandProxy().update(upData);
	}
}
