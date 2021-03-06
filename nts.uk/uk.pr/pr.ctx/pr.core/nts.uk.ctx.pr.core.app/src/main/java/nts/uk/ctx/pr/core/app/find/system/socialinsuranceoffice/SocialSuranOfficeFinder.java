package nts.uk.ctx.pr.core.app.find.system.socialinsuranceoffice;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.dto.CusSociaInsuOfficeDto;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.dto.SociaInsuOfficeDto;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.dto.SociaInsuPreInfoDto;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceBusinessAddress;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformation;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SocialSuranOfficeFinder {
	
	
	@Inject
	private SocialInsurancePrefectureInformationRepository socialInsurancePrefectureInformationRepository;
	
	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	
	public List<SociaInsuPreInfoDto> findAll() {
		List<SociaInsuPreInfoDto> response = new ArrayList<>();
		List<SocialInsurancePrefectureInformation> data = socialInsurancePrefectureInformationRepository.findByHistory();
		response = data.stream().map(x -> new SociaInsuPreInfoDto(x.getNo(), x.getPrefectureName().v())).collect(Collectors.toList());
		return response;
	}
	public List<SociaInsuOfficeDto> findAllIncludeInfo() {
		return socialInsuranceOfficeRepository.findByCid(AppContexts.user().companyId()).stream().map(item -> this.mappingDto(item)).collect(Collectors.toList());
	}

	
	public List<CusSociaInsuOfficeDto> findByCid() {
		List<SocialInsuranceOffice> data = socialInsuranceOfficeRepository.findByCid(AppContexts.user().companyId());
		List<CusSociaInsuOfficeDto> response = new ArrayList<>();
		response = data.stream().map(x -> new CusSociaInsuOfficeDto(x.getCode().v(), x.getName().v())).collect(Collectors.toList());
		return response;
	}
	
	public SociaInsuOfficeDto findByKey(String code) {
		Optional<SocialInsuranceOffice> data = socialInsuranceOfficeRepository.findByCodeAndCid(AppContexts.user().companyId(), code);
		return mappingDto(data.get());
	}
	
	private SociaInsuOfficeDto mappingDto(SocialInsuranceOffice data) {
		SociaInsuOfficeDto response = new SociaInsuOfficeDto(data.getCompanyID(),
				data.getCode().v(),
				data.getName().v(),
				data.getBasicInformation().getShortName().map(PrimitiveValueBase::v).orElse(null),
				data.getBasicInformation().getRepresentativeName().map(PrimitiveValueBase::v).orElse(null),
				data.getBasicInformation().getRepresentativePosition().v(),
				data.getBasicInformation().getMemo().map(PrimitiveValueBase::v).orElse(null),
				data.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getPostalCode).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null),
				data.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddress1).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null),
				data.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddressKana1).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null),
				data.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddress2).map(x ->x.orElse(null)).map(PrimitiveValueBase::v).orElse(null),
				data.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddressKana2).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null),
				data.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getPhoneNumber).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null),
				data.getInsuranceMasterInformation().getWelfarePensionFundNumber().map(PrimitiveValueBase::v).map(Integer::new).orElse(null),
				data.getInsuranceMasterInformation().getWelfarePensionOfficeNumber().map(PrimitiveValueBase::v).orElse(null),
				data.getInsuranceMasterInformation().getHealthInsuranceOfficeNumber().map(PrimitiveValueBase::v).map(Integer::new).orElse(null),
				data.getInsuranceMasterInformation().getHealthInsuranceUnionOfficeNumber().map(PrimitiveValueBase::v).orElse(null), data.getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().map(PrimitiveValueBase::v).orElse(null),
				data.getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber2().map(PrimitiveValueBase::v).orElse(null),
				data.getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber1().map(PrimitiveValueBase::v).orElse(null),
				data.getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber2().map(PrimitiveValueBase::v).orElse(null),
				data.getInsuranceMasterInformation().getForMagneticMedia().getHealthInsuranceCityCode().map(PrimitiveValueBase::v).orElse(null),
				data.getInsuranceMasterInformation().getForMagneticMedia().getHealthInsuranceOfficeCode().map(PrimitiveValueBase::v).orElse(null),
				data.getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionCityCode().map(PrimitiveValueBase::v).orElse(null),
				data.getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionOfficeCode().map(PrimitiveValueBase::v).orElse(null),
				data.getInsuranceMasterInformation().getForMagneticMedia().getHealthInsurancePrefectureNo().orElse(null),
				data.getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionPrefectureNo().orElse(null));
		
		return response;
	}

}
