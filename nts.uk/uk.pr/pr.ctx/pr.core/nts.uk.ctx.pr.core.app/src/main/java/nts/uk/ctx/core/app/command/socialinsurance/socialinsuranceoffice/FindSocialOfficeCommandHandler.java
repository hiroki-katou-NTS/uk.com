package nts.uk.ctx.core.app.command.socialinsurance.socialinsuranceoffice;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.core.app.command.socialinsurance.socialinsuranceoffice.dto.SociaInsuOfficeDto;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.shr.com.context.AppContexts;

@Transactional
@Stateless
public class FindSocialOfficeCommandHandler extends CommandHandlerWithResult<FindSocialOfficeCommand,SociaInsuOfficeDto> {
	
	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	@Override
	protected SociaInsuOfficeDto handle(CommandHandlerContext<FindSocialOfficeCommand> context) {
		FindSocialOfficeCommand command = context.getCommand();
		Optional<SocialInsuranceOffice> data = socialInsuranceOfficeRepository.findByCodeAndCid(AppContexts.user().companyId(), command.getCode());
		return mappingDto(data);
	}

	private SociaInsuOfficeDto mappingDto(Optional<SocialInsuranceOffice> data) {
		SociaInsuOfficeDto sociaInsuOfficeDto = new SociaInsuOfficeDto();
		if (data.isPresent()) {
			sociaInsuOfficeDto.setCompanyID(data.get().getCompanyID());
			sociaInsuOfficeDto.setCode(data.get().getCode().v());
			sociaInsuOfficeDto.setName(data.get().getName().v());
			sociaInsuOfficeDto.setMemo(data.get().getBasicInformation().getMemo().get().v());
			sociaInsuOfficeDto
					.setRepresentativeName(data.get().getBasicInformation().getRepresentativeName().get().v());
			sociaInsuOfficeDto
					.setRepresentativePosition(data.get().getBasicInformation().getRepresentativePosition().v());
			sociaInsuOfficeDto.setShortName(data.get().getBasicInformation().getShortName().get().v());
			sociaInsuOfficeDto.setAddress1(data.get().getBasicInformation().getAddress().get().getAddress1().get().v());
			sociaInsuOfficeDto.setAddress2(data.get().getBasicInformation().getAddress().get().getAddress2().get().v());
			sociaInsuOfficeDto
					.setAddressKana1(data.get().getBasicInformation().getAddress().get().getAddressKana1().get().v());
			sociaInsuOfficeDto
					.setAddressKana2(data.get().getBasicInformation().getAddress().get().getAddressKana2().get().v());
			sociaInsuOfficeDto
					.setPhoneNumber(data.get().getBasicInformation().getAddress().get().getPhoneNumber().get().v());
			sociaInsuOfficeDto
					.setPostalCode(data.get().getBasicInformation().getAddress().get().getPostalCode().get().v());
			sociaInsuOfficeDto.setWelfarePensionCityCode(data.get().getInsuranceMasterInformation()
					.getForMagneticMedia().getWelfarePensionCityCode().get().v());
			sociaInsuOfficeDto.setHealthInsuranceCityCode(data.get().getInsuranceMasterInformation()
					.getForMagneticMedia().getHealthInsuranceCityCode().get().v());
			sociaInsuOfficeDto.setHealthInsuranceOfficeCode(data.get().getInsuranceMasterInformation()
					.getForMagneticMedia().getHealthInsuranceOfficeCode().get().v());
			sociaInsuOfficeDto.setWelfarePensionOfficeCode(data.get().getInsuranceMasterInformation()
					.getForMagneticMedia().getWelfarePensionOfficeCode().get().v());
			sociaInsuOfficeDto.setHealthInsurancePrefectureNo(data.get().getInsuranceMasterInformation()
					.getForMagneticMedia().getHealthInsurancePrefectureNo().get());
			sociaInsuOfficeDto.setWelfarePensionPrefectureNo(data.get().getInsuranceMasterInformation()
					.getForMagneticMedia().getWelfarePensionPrefectureNo().get());
			sociaInsuOfficeDto.setHealthInsuranceOfficeNumber1(data.get().getInsuranceMasterInformation()
					.getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().get().v());
			sociaInsuOfficeDto.setHealthInsuranceOfficeNumber2(data.get().getInsuranceMasterInformation()
					.getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber2().get().v());
			sociaInsuOfficeDto.setWelfarePensionOfficeNumber1(data.get().getInsuranceMasterInformation()
					.getOfficeOrganizeNumber().getWelfarePensionOfficeNumber1().get().v());
			sociaInsuOfficeDto.setWelfarePensionOfficeNumber2(data.get().getInsuranceMasterInformation()
					.getOfficeOrganizeNumber().getWelfarePensionOfficeNumber2().get().v());
		}
		return sociaInsuOfficeDto;
	}

}
