package nts.uk.ctx.core.app.find.system.socialinsuranceoffice;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.app.command.socialinsurance.socialinsuranceoffice.dto.CusSociaInsuOfficeDto;
import nts.uk.ctx.core.app.command.socialinsurance.socialinsuranceoffice.dto.SociaInsuOfficeDto;
import nts.uk.ctx.core.app.command.socialinsurance.socialinsuranceoffice.dto.SociaInsuPreInfoDto;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformation;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformationRepository;
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
	
	public List<CusSociaInsuOfficeDto> findByCid() {
		List<SocialInsuranceOffice> data = socialInsuranceOfficeRepository.findByCid(AppContexts.user().companyId());
		List<CusSociaInsuOfficeDto> response = new ArrayList<>();
		response = data.stream().map(x -> new CusSociaInsuOfficeDto(x.getCode().v(), x.getName().v())).collect(Collectors.toList());
		return response;
	}
	
	public SociaInsuOfficeDto findByKey(String code) {
		Optional<SocialInsuranceOffice> data = socialInsuranceOfficeRepository.findByCodeAndCid(AppContexts.user().companyId(), code);
		SociaInsuOfficeDto sociaInsuOfficeDto  = new SociaInsuOfficeDto();
		if(data.isPresent()) {
			sociaInsuOfficeDto.setCompanyID(data.get().getCompanyID());
			sociaInsuOfficeDto.setCode(data.get().getCode().v());
			sociaInsuOfficeDto.setName(data.get().getName().v());
			sociaInsuOfficeDto.setMemo(data.get().getBasicInformation().getMemo().get().v());
			sociaInsuOfficeDto.setRepresentativeName(data.get().getBasicInformation().getRepresentativeName().get().v());
			sociaInsuOfficeDto.setRepresentativePosition(data.get().getBasicInformation().getRepresentativePosition().v());
			sociaInsuOfficeDto.setShortName(data.get().getBasicInformation().getShortName().get().v());
			sociaInsuOfficeDto.setAddress1(data.get().getBasicInformation().getAddress().get().getAddress1().get().v());
			sociaInsuOfficeDto.setAddress2(data.get().getBasicInformation().getAddress().get().getAddress2().get().v());
			sociaInsuOfficeDto.setAddressKana1(data.get().getBasicInformation().getAddress().get().getAddressKana1().get().v());
			sociaInsuOfficeDto.setAddressKana2(data.get().getBasicInformation().getAddress().get().getAddressKana2().get().v());
			sociaInsuOfficeDto.setPhoneNumber(data.get().getBasicInformation().getAddress().get().getPhoneNumber().get().v());
			sociaInsuOfficeDto.setPostalCode(data.get().getBasicInformation().getAddress().get().getPostalCode().get().v());
			sociaInsuOfficeDto.setWelfarePensionCityCode(data.get().getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionCityCode().get().v());
			sociaInsuOfficeDto.setHealthInsuranceCityCode(data.get().getInsuranceMasterInformation().getForMagneticMedia().getHealthInsuranceCityCode().get().v());
			sociaInsuOfficeDto.setHealthInsuranceOfficeCode(data.get().getInsuranceMasterInformation().getForMagneticMedia().getHealthInsuranceOfficeCode().get().v());
			sociaInsuOfficeDto.setWelfarePensionOfficeCode(data.get().getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionOfficeCode().get().v());
			sociaInsuOfficeDto.setHealthInsurancePrefectureNo(data.get().getInsuranceMasterInformation().getForMagneticMedia().getHealthInsurancePrefectureNo().get());
			sociaInsuOfficeDto.setWelfarePensionPrefectureNo(data.get().getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionPrefectureNo().get());
			sociaInsuOfficeDto.setHealthInsuranceOfficeNumber1(data.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().get().v());
			sociaInsuOfficeDto.setHealthInsuranceOfficeNumber2(data.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber2().get().v());
			sociaInsuOfficeDto.setWelfarePensionOfficeNumber1(data.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber1().get().v());
			sociaInsuOfficeDto.setWelfarePensionOfficeNumber2(data.get().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber2().get().v());
		}
		return sociaInsuOfficeDto;
	}
	
	
}
