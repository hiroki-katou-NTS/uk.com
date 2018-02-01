package nts.uk.ctx.at.request.dom.setting.company.mailsetting.overtimeworkinstructionmail;

import java.util.Optional;

/**
 * the mail holiday instruction repository interface
 * @author yennth
 *
 */
public interface MailOtInstructionRepository {
	/**
	 * get mail holiday instruction by company id
	 * @return
	 * @author yennth
	 */
	Optional<MailOtInstruction> getMail();
	/**
	 * update mail holiday instruction 
	 * @param mail
	 * @author yennth
	 */
	void update(MailOtInstruction mail);
	/**
	 * insert mail holiday instruction
	 * @param mail
	 * @author yennth
	 */
	void insert(MailOtInstruction mail);
}
