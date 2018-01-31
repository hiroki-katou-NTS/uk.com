package nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction;

import java.util.Optional;
/**
 * the mail holiday instruction repository interface
 * @author yennth
 *
 */
public interface MailHdInstructionRepository {
	/**
	 * get mail holiday instruction by company id
	 * @return
	 * @author yennth
	 */
	Optional<MailHdInstruction> getMail();
	/**
	 * update mail holiday instruction 
	 * @param mail
	 * @author yennth
	 */
	void update(MailHdInstruction mail);
	/**
	 * insert mail holiday instruction
	 * @param mail
	 * @author yennth
	 */
	void insert(MailHdInstruction mail);
}
