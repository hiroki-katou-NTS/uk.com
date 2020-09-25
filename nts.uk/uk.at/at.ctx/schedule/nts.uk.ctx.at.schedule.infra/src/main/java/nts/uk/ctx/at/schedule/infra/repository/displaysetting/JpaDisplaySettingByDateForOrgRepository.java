package nts.uk.ctx.at.schedule.infra.repository.displaysetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDateForOrganization;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDateForOrgRepository;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.KscmtDispsetBydateOrg;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.KscmtDispsetBydateOrgPk;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaDisplaySettingByDateForOrgRepository extends JpaRepository implements DisplaySettingByDateForOrgRepository {
	
	@Override
	public Optional<DisplaySettingByDateForOrganization> get (String companyId, TargetOrgIdenInfor targetOrg) {
		
		String sql = "SELECT * FROM KSCMT_DISPSET_BYDATE_ORG"
				+ " WHERE CID = @companyId"
				+ " AND TARGET_UNIT = @targetUnit"
				+ " AND TARGET_ID = @targetId";
		
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targetOrg.getUnit().value)
				.paramString("targetId", targetOrg.getTargetId())
				.getSingle(x -> KscmtDispsetBydateOrg.MAPPER.toEntity(x).toDomain());
	}
	
	@Override
	public void insert (String companyId, DisplaySettingByDateForOrganization dispSetorg) {
		this.commandProxy().insert(KscmtDispsetBydateOrg.of(companyId, dispSetorg));
	}
	
	@Override
	public void update (String companyId, DisplaySettingByDateForOrganization dispSetorg) {
		KscmtDispsetBydateOrgPk pk = new KscmtDispsetBydateOrgPk();
		pk.companyId = companyId;
		pk.targetUnit = dispSetorg.getTargetOrg().getUnit().value;
		pk.targetId = dispSetorg.getTargetOrg().getTargetId();
		
		KscmtDispsetBydateOrg upData = this.queryProxy()
				.find(pk, KscmtDispsetBydateOrg.class)
				.get();
		
		upData.rangeAtr = dispSetorg.getDispSetting().getDispRange().value;
		upData.startClock = dispSetorg.getDispSetting().getDispStart().v();
		upData.initStartClock = dispSetorg.getDispSetting().getInitDispStart().v();
		
		this.commandProxy().update(upData);
	}
}
