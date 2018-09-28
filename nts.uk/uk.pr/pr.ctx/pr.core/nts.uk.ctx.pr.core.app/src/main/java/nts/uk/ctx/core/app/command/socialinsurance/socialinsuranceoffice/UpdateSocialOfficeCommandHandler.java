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
public class UpdateSocialOfficeCommandHandler extends CommandHandlerWithResult<UpdateSocialOfficeCommand, List<String>> {
	
	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	
	@Override
	protected List<String> handle(CommandHandlerContext<UpdateSocialOfficeCommand> context) { 
		List<String> response = new ArrayList<>();
		UpdateSocialOfficeCommand command = context.getCommand();
		Optional<SocialInsuranceOffice> socialInsu = socialInsuranceOfficeRepository.findByCodeAndCid(AppContexts.user().companyId(), command.getCode());
		if(socialInsu.isPresent()) {
			command.setCompanyID(AppContexts.user().companyId());
			SocialInsuranceOffice socialInuran = mappingEntity(command);
			socialInsuranceOfficeRepository.update(socialInuran);
			response.add(command.getCode());
			response.add(socialInuran.getName().v());
		}
		return response;
	}
	
	
	private SocialInsuranceOffice mappingEntity(UpdateSocialOfficeCommand command) {
		SocialInsuranceOffice socialInuran = new SocialInsuranceOffice(command.getCompanyID(), command.getCode(),
				command.getName(), command.getShortName().isEmpty() ? null : command.getShortName(), command.getRepresentativeName().isEmpty() ? null : command.getRepresentativeName(),
				command.getRepresentativePosition(), command.getPostalCode().isEmpty() ? null : command.getPostalCode(),command.getAddress1().isEmpty() ? null : command.getAddress1(),
				command.getAddressKana1().isEmpty() ? null : command.getAddressKana1(), command.getAddress2().isEmpty() ? null : command.getAddress2(), command.getAddressKana2().isEmpty() ? null : command.getAddressKana2(), command.getPhoneNumber().isEmpty() ? null : command.getPhoneNumber() ,
				command.getMemo().isEmpty() ? null : command.getAddress1(), command.getWelfarePensionFundNumber(), command.getWelfarePensionOfficeNumber().isEmpty() ? null : command.getWelfarePensionOfficeNumber() ,
				command.getHealthInsuranceOfficeNumber(), command.getHealthInsuranceUnionOfficeNumber().isEmpty() ? null : command.getHealthInsuranceUnionOfficeNumber() ,
				command.getHealthInsuranceOfficeNumber1().isEmpty() ? null : command.getHealthInsuranceOfficeNumber1(), command.getHealthInsuranceOfficeNumber2().isEmpty() ? null : command.getHealthInsuranceOfficeNumber2(),
				command.getWelfarePensionOfficeNumber1().isEmpty() ? null : command.getWelfarePensionOfficeNumber1(), command.getWelfarePensionOfficeNumber2().isEmpty() ? null : command.getWelfarePensionOfficeNumber2(),
				command.getHealthInsuranceCityCode().isEmpty() ? null : command.getHealthInsuranceCityCode(), command.getHealthInsuranceOfficeCode().isEmpty() ? null : command.getHealthInsuranceOfficeCode(),
				command.getWelfarePensionCityCode().isEmpty() ? null : command.getWelfarePensionCityCode(), command.getWelfarePensionOfficeCode().isEmpty() ? null : command.getWelfarePensionOfficeCode(),
				command.getHealthInsurancePrefectureNo(), command.getWelfarePensionPrefectureNo());
		return socialInuran;
	}

}
