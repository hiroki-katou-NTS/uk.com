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
	public void insert (String companyId, DisplaySettingByDateForOrganization dispSetOrg) {
		this.commandProxy().insert(KscmtDispsetBydateOrg.of(companyId, dispSetOrg));
	}
	
	@Override
	public void update (String companyId, DisplaySettingByDateForOrganization dispSetOrg) {
		KscmtDispsetBydateOrgPk pk = new KscmtDispsetBydateOrgPk();
		pk.companyId = companyId;
		pk.targetUnit = dispSetOrg.getTargetOrg().getUnit().value;
		pk.targetId = dispSetOrg.getTargetOrg().getTargetId();
		
		KscmtDispsetBydateOrg upData = this.queryProxy()
				.find(pk, KscmtDispsetBydateOrg.class)
				.get();
		
		upData.rangeAtr = dispSetOrg.getDispSetting().getDispRange().value;
		upData.startClock = dispSetOrg.getDispSetting().getDispStart().v();
		upData.initStartClock = dispSetOrg.getDispSetting().getInitDispStart().v();
		
		this.commandProxy().update(upData);
	}
}
