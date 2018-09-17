package nts.uk.ctx.core.app.command.socialinsurance.socialinsuranceoffice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.shr.com.context.AppContexts;

@Transactional
@Stateless
public class CreateSocialOfficeCommandHandler extends CommandHandlerWithResult<CreateSocialOfficeCommand,List<String>> {
	
	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	
	@Override
	protected List<String> handle(CommandHandlerContext<CreateSocialOfficeCommand> context) {
		List<String> response = new ArrayList<>();
		CreateSocialOfficeCommand command = context.getCommand();
		Optional<SocialInsuranceOffice> socialInsu = socialInsuranceOfficeRepository.findByCodeAndCid(AppContexts.user().companyId(), command.getCode());
		if(!socialInsu.isPresent()) {
			command.setCompanyID(AppContexts.user().companyId());
			SocialInsuranceOffice socialInuran = mappingEntity(command);
			socialInsuranceOfficeRepository.add(socialInuran);
			response.add(command.getCode());
			response.add(command.getName());
		} else {
			response.add("Msg_3");
		}
		return response;
	}
	
	private SocialInsuranceOffice mappingEntity(CreateSocialOfficeCommand command) {
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
