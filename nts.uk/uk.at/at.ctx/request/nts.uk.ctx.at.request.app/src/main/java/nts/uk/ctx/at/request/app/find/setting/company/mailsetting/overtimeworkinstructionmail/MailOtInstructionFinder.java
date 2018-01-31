package nts.uk.ctx.at.request.app.find.setting.company.mailsetting.overtimeworkinstructionmail;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.mailsetting.overtimeworkinstructionmail.MailOtInstruction;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.overtimeworkinstructionmail.MailOtInstructionRepository;

/**
 * find mail ot instruction by companyId
 * @author yennth
 *
 */
@Stateless
public class MailOtInstructionFinder {
	@Inject
	private MailOtInstructionRepository mailRep;
	public MailOtInstructionDto findByComId(){
		Optional<MailOtInstruction> mail = mailRep.getMail();
		if(mail.isPresent()){
			return MailOtInstructionDto.convertToDto(mail.get());
		}
		return null;
	}
}
