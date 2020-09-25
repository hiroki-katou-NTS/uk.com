package nts.uk.ctx.at.schedule.infra.repository.displaysetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDateForCompany;
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
	public Optional<DisplaySettingByDateForCompany> get(String companyId){
		
		String sql = "SELECT * FROM KSCMT_DISPSET_BYDATE_CMP WHERE CID = @companyId";
		
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyId", companyId)
				.getSingle(x -> KscmtDispsetBydateCmp.MAPPER.toEntity(x).toDomain());
	}
	
	@Override
	public void insert (String companyId, DisplaySettingByDateForCompany dispSetcmp) {
		this.commandProxy().insert(KscmtDispsetBydateCmp.of(companyId, dispSetcmp));
	}
	
	@Override
	public void update (String companyId, DisplaySettingByDateForCompany dispSetcmp) {
		
		KscmtDispsetBydateCmp upData = this.queryProxy()
				.find(companyId, KscmtDispsetBydateCmp.class)
				.get();
		
		upData.rangeAtr = dispSetcmp.getDispSetting().getDispRange().value;
		upData.startClock = dispSetcmp.getDispSetting().getDispStart().v();
		upData.initStartClock = dispSetcmp.getDispSetting().getInitDispStart().v();
		
		this.commandProxy().update(upData);
	}
}
