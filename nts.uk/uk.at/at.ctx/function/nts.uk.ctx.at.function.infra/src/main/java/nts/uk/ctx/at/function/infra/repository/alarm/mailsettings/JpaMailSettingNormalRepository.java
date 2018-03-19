package nts.uk.ctx.at.function.infra.repository.alarm.mailsettings;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingNormal;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingNormalRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.mailsettings.KfnmtMailSettingNormal;

@Stateless
public class JpaMailSettingNormalRepository extends JpaRepository implements MailSettingNormalRepository {

	private final String FIND_BY_COMPANY = "SELECT a FROM KfnmtMailSettingNormal a WHERE a.companyID = :companyId";
	
	@Override
	public Optional<MailSettingNormal> findByCompanyId(String companyId) {
		return this.queryProxy().query(FIND_BY_COMPANY, KfnmtMailSettingNormal.class)
				.setParameter("companyId", companyId)
				.getSingle().map(c -> c.toDomain());
	}

	@Override
	public void create(MailSettingNormal mailSettingNormal) {
		this.commandProxy().insert(KfnmtMailSettingNormal.toEntity(
				IdentifierUtil.randomUniqueId(), 
				IdentifierUtil.randomUniqueId(), 
				IdentifierUtil.randomUniqueId(), 
				IdentifierUtil.randomUniqueId(), 
				mailSettingNormal));
	}

	@Override
	public void update(MailSettingNormal mailSettingNormal) {
		KfnmtMailSettingNormal updateEntity = this.queryProxy().query(FIND_BY_COMPANY, KfnmtMailSettingNormal.class)
				.setParameter("companyId", mailSettingNormal.getCompanyID())
				.getSingle().get();
		
		KfnmtMailSettingNormal newEntity = KfnmtMailSettingNormal.toEntity(
				IdentifierUtil.randomUniqueId(),
				IdentifierUtil.randomUniqueId(),
				IdentifierUtil.randomUniqueId(),
				IdentifierUtil.randomUniqueId(),
				mailSettingNormal);
		updateEntity.updateEntity(newEntity);
		
		this.commandProxy().update(updateEntity);
		
	}

}
