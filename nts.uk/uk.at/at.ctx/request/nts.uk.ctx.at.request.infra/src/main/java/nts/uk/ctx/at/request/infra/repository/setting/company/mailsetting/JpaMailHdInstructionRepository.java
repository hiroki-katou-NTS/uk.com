package nts.uk.ctx.at.request.infra.repository.setting.company.mailsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.MailHdInstruction;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.MailHdInstructionRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.mailsetting.mailholidayinstruction.KrqmtMailHdInstruction;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class JpaMailHdInstructionRepository extends JpaRepository implements MailHdInstructionRepository{
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private MailHdInstruction toDomain(KrqmtMailHdInstruction entity){
		MailHdInstruction domain = MailHdInstruction.createFromJavaType(entity.companyId, 
				entity.subject, entity.content);
		return domain;
	}
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private static KrqmtMailHdInstruction toEntity(MailHdInstruction domain){
		val entity = new KrqmtMailHdInstruction();
		entity.companyId = domain.getCompanyId();
		entity.content = domain.getContent() == null ? null : domain.getContent().v();
		entity.subject = domain.getSubject() == null ? null : domain.getSubject().v();
		return entity;
	}
	/**
	 * get mail holiday instruction by company id
	 * @author yennth
	 */
	@Override
	public Optional<MailHdInstruction> getMail() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().find(companyId, KrqmtMailHdInstruction.class).map(c -> toDomain(c));
	}
	/**
	 * update mail holiday instruction
	 * @author yennth
	 */
	@Override
	public void update(MailHdInstruction mail) {
		KrqmtMailHdInstruction entity = toEntity(mail);
		KrqmtMailHdInstruction oldEntity = this.queryProxy().find(entity.companyId, KrqmtMailHdInstruction.class).get();
		oldEntity.content = entity.content;
		oldEntity.subject = entity.subject;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert mail holiday instruction
	 * @author yennth
	 */
	@Override
	public void insert(MailHdInstruction mail) {
		KrqmtMailHdInstruction entity = toEntity(mail);
		this.commandProxy().insert(entity);
	}

}
