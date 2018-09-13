package nts.uk.ctx.core.app.command.socialinsurance.socialinsuranceoffice;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.shr.com.context.AppContexts;

@Transactional
@Stateless
public class UpdateSocialOfficeCommandHandler extends CommandHandler<UpdateSocialOfficeCommand> {
	
	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	
	@Override
	protected void handle(CommandHandlerContext<UpdateSocialOfficeCommand> context) {
		UpdateSocialOfficeCommand command = context.getCommand();
		
		Optional<SocialInsuranceOffice> socialInsu = socialInsuranceOfficeRepository.findById(AppContexts.user().companyId(), command.getCode());
		if(socialInsu.isPresent()) {
			SocialInsuranceOffice socialInuran = mappingEntity(command);
			socialInsuranceOfficeRepository.update(socialInuran);
		}
	}
	
	private SocialInsuranceOffice mappingEntity(UpdateSocialOfficeCommand command) {
		SocialInsuranceOffice socialInuran = new SocialInsuranceOffice(command.getCompanyID(), command.getCode(),
				command.getName(), command.getShortName(), command.getRepresentativeName(),
				command.getRepresentativePosition(), command.getPostalCode(), command.getAddress1(),
				command.getAddressKana1(), command.getAddress2(), command.getAddressKana2(), command.getPhoneNumber(),
				command.getMemo(), command.getWelfarePensionFundNumber(), command.getWelfarePensionOfficeNumber(),
				command.getHealthInsuranceOfficeNumber(), command.getHealthInsuranceUnionOfficeNumber(),
				command.getHealthInsuranceOfficeNumber1(), command.getHealthInsuranceOfficeNumber2(),
				command.getWelfarePensionOfficeNumber1(), command.getWelfarePensionOfficeNumber2(),
				command.getHealthInsuranceCityCode(), command.getHealthInsuranceOfficeCode(),
				command.getWelfarePensionCityCode(), command.getWelfarePensionOfficeCode(),
				command.getHealthInsurancePrefectureNo(), command.getWelfarePensionPrefectureNo());
		return socialInuran;
	}
	

}
