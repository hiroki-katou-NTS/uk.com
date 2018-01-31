package nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailholidayinstruction;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.MailHdInstruction;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.MailHdInstructionRepository;
/**
 * find mail holiday instruction by companyId
 * @author yennth
 *
 */
@Stateless
public class MailHdInstructionFinder {
	@Inject
	private MailHdInstructionRepository mailRep;
	public MailHdInstructionDto findByComId(){
		Optional<MailHdInstruction> mail = mailRep.getMail();
		if(mail.isPresent()){
			return MailHdInstructionDto.convertToDto(mail.get());
		}
		return null;
	}
}
