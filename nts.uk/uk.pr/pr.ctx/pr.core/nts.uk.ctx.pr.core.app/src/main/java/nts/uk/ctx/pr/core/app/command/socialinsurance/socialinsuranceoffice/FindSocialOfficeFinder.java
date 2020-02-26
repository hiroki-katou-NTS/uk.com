package nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.dto.SociaInsuOfficeDto;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceBusinessAddress;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class FindSocialOfficeFinder {

	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

	public SociaInsuOfficeDto findByCode(String codeId) {
		Optional<SocialInsuranceOffice> data = socialInsuranceOfficeRepository.findByCodeAndCid(AppContexts.user().companyId(), codeId);
		return mappingDto(data);
	}

	private SociaInsuOfficeDto mappingDto(Optional<SocialInsuranceOffice> data) {
		SociaInsuOfficeDto response = new SociaInsuOfficeDto(data.get().getCompanyID(), 
				data.get().getCode().v(), 
				data.get().getName().v(), 
				data.get().getBasicInformation().getShortName().map(PrimitiveValueBase::v).orElse(null), 
				data.get().getBasicInformation().getRepresentativeName().map(PrimitiveValueBase::v).orElse(null), 
				data.get().getBasicInformation().getRepresentativePosition().v(), 
				data.get().getBasicInformation().getMemo().map(PrimitiveValueBase::v).orElse(null), 
				data.get().getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getPostalCode).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null), 
				data.get().getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddress1).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null), 
				data.get().getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddressKana1).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null), 
				data.get().getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddress2).map(x ->x.orElse(null)).map(PrimitiveValueBase::v).orElse(null), 
				data.get().getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddressKana2).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null), 
				data.get().getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getPhoneNumber).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null), 
				data.get().getInsuranceMasterInformation().getWelfarePensionFundNumber().map(PrimitiveValueBase::v).map(Integer::new).orElse(null),
				data.get().getInsuranceMasterInformation().getWelfarePensionOfficeNumber().map(PrimitiveValueBase::v).orElse(null),
				data.get().getInsuranceMasterInformation().getHealthInsuranceOfficeNumber().map(PrimitiveValueBase::v).map(Integer::new).orElse(null),
				data.get().getInsuranceMasterInformation().getHealthInsuranceUnionOfficeNumber().map(PrimitiveValueBase::v).orElse(null), data.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().map(PrimitiveValueBase::v).orElse(null), 
				data.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber2().map(PrimitiveValueBase::v).orElse(null), 
				data.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber1().map(PrimitiveValueBase::v).orElse(null), 
				data.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber2().map(PrimitiveValueBase::v).orElse(null), 
				data.get().getInsuranceMasterInformation().getForMagneticMedia().getHealthInsuranceCityCode().map(PrimitiveValueBase::v).orElse(null), 
				data.get().getInsuranceMasterInformation().getForMagneticMedia().getHealthInsuranceOfficeCode().map(PrimitiveValueBase::v).orElse(null), 
				data.get().getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionCityCode().map(PrimitiveValueBase::v).orElse(null), 
				data.get().getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionOfficeCode().map(PrimitiveValueBase::v).orElse(null),
				data.get().getInsuranceMasterInformation().getForMagneticMedia().getHealthInsurancePrefectureNo().orElse(null), 
				data.get().getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionPrefectureNo().orElse(null));
		
		return response;
	}

}
