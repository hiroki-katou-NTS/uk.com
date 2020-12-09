package nts.uk.ctx.at.request.infra.repository.setting.company.mailsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.overtimeworkinstructionmail.MailOtInstruction;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.overtimeworkinstructionmail.MailOtInstructionRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.mailsetting.overtimeworkinstructionmail.KrqmtMailOtInstruct;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaMailOtInstructionRepository extends JpaRepository implements MailOtInstructionRepository{
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private MailOtInstruction toDomain(KrqmtMailOtInstruct entity){
		MailOtInstruction domain = MailOtInstruction.createFromJavaType(entity.companyId, 
				entity.subject, entity.content);
		return domain;
	}
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private static KrqmtMailOtInstruct toEntity(MailOtInstruction domain){
		val entity = new KrqmtMailOtInstruct();
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
	public Optional<MailOtInstruction> getMail() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().find(companyId, KrqmtMailOtInstruct.class).map(c -> toDomain(c));
	}
	/**
	 * update mail holiday instruction
	 * @author yennth
	 */
	@Override
	public void update(MailOtInstruction mail) {
		KrqmtMailOtInstruct entity = toEntity(mail);
		KrqmtMailOtInstruct oldEntity = this.queryProxy().find(entity.companyId, KrqmtMailOtInstruct.class).get();
		oldEntity.content = entity.content;
		oldEntity.subject = entity.subject;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert mail holiday instruction
	 * @author yennth
	 */
	@Override
	public void insert(MailOtInstruction mail) {
		KrqmtMailOtInstruct entity = toEntity(mail);
		this.commandProxy().insert(entity);
	}

}
