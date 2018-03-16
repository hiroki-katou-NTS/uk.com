package nts.uk.ctx.at.function.infra.repository.alarm.mailsettings;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingAutomatic;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingAutomaticRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.mailsettings.KfnmtMailSettingAutomatic;

@Stateless
public class JpaMailSettingAutomaticRepository extends JpaRepository implements MailSettingAutomaticRepository{
	
	private final String FIND_BY_COMPANY = "SELECT a FROM KfnmtMailSettingAutomatic a WHERE a.companyID = :companyId";
	@Override
	public Optional<MailSettingAutomatic> findByCompanyId(String companyId) {
		return this.queryProxy().query(FIND_BY_COMPANY, KfnmtMailSettingAutomatic.class)
				.setParameter("companyId", companyId)
				.getSingle().map(c -> c.toDomain());
	}

	@Override
	public void create(MailSettingAutomatic mailSettingAutomatic) {
		this.commandProxy().insert(KfnmtMailSettingAutomatic.toEntity(
				IdentifierUtil.randomUniqueId(), 
				IdentifierUtil.randomUniqueId(), 
				IdentifierUtil.randomUniqueId(), 
				IdentifierUtil.randomUniqueId(), 
				mailSettingAutomatic));
	}

	@Override
	public void update(MailSettingAutomatic mailSettingAutomatic) {
		KfnmtMailSettingAutomatic updateEntity = this.queryProxy().query(FIND_BY_COMPANY, KfnmtMailSettingAutomatic.class)
				.setParameter("companyId", mailSettingAutomatic.getCompanyID())
				.getSingle().get();

		KfnmtMailSettingAutomatic newEntity = KfnmtMailSettingAutomatic.toEntity(
				IdentifierUtil.randomUniqueId(),
				IdentifierUtil.randomUniqueId(),
				IdentifierUtil.randomUniqueId(),
				IdentifierUtil.randomUniqueId(),
				mailSettingAutomatic);
		updateEntity.updateEntity(newEntity);
		
		this.commandProxy().update(updateEntity);
	}

}
