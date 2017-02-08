package nts.uk.ctx.pr.core.app.command.insurance.social;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.find.insurance.social.dto.SocialInsuranceOfficeDto;
import nts.uk.ctx.pr.core.app.service.insurance.social.SocialInsuranceService;
import nts.uk.ctx.pr.core.dom.insurance.Address;
import nts.uk.ctx.pr.core.dom.insurance.KanaAddress;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;
import nts.uk.ctx.pr.core.dom.insurance.PicName;
import nts.uk.ctx.pr.core.dom.insurance.PicPosition;
import nts.uk.ctx.pr.core.dom.insurance.PotalCode;
import nts.uk.ctx.pr.core.dom.insurance.ShortName;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;

@Stateless
public class UpdateSocialOfficeCommandHandler extends CommandHandler<UpdateSocialOfficeCommand> {

	@Inject
	SocialInsuranceService insuranceSocialService;

	@Inject
	SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UpdateSocialOfficeCommand> command) {
		// TODO Auto-generated method stub
		SocialInsuranceOfficeDto socialInsuranceOfficeDto = command.getCommand().getSocialInsuranceOfficeDto();

		// Convert Dto to Domain
		SocialInsuranceOffice SocialInsuranceOfficeDomain = new SocialInsuranceOffice(
				new CompanyCode(socialInsuranceOfficeDto.getCompanyCode()),
				new OfficeCode(socialInsuranceOfficeDto.getCode()), new OfficeName(socialInsuranceOfficeDto.getName()),
				new ShortName(socialInsuranceOfficeDto.getShortName()),
				new PicName(socialInsuranceOfficeDto.getPicName()),
				new PicPosition(socialInsuranceOfficeDto.getPicPosition()),
				new PotalCode(socialInsuranceOfficeDto.getPotalCode()), socialInsuranceOfficeDto.getPrefecture(),
				new Address(socialInsuranceOfficeDto.getAddress1st()),
				new Address(socialInsuranceOfficeDto.getAddress2nd()),
				new KanaAddress(socialInsuranceOfficeDto.getKanaAddress1st()),
				new KanaAddress(socialInsuranceOfficeDto.getKanaAddress2nd()),
				socialInsuranceOfficeDto.getPhoneNumber(), socialInsuranceOfficeDto.getHealthInsuOfficeRefCode1st(),
				socialInsuranceOfficeDto.getHealthInsuOfficeRefCode2nd(),
				socialInsuranceOfficeDto.getPensionOfficeRefCode1st(),
				socialInsuranceOfficeDto.getPensionOfficeRefCode2nd(),
				socialInsuranceOfficeDto.getWelfarePensionFundCode(),
				socialInsuranceOfficeDto.getOfficePensionFundCode(), socialInsuranceOfficeDto.getHealthInsuCityCode(),
				socialInsuranceOfficeDto.getHealthInsuOfficeSign(), socialInsuranceOfficeDto.getPensionCityCode(),
				socialInsuranceOfficeDto.getPensionOfficeSign(), socialInsuranceOfficeDto.getHealthInsuOfficeCode(),
				socialInsuranceOfficeDto.getHealthInsuAssoCode(), socialInsuranceOfficeDto.getMemo());
		SocialInsuranceOffice findOffice = socialInsuranceOfficeRepository
				.findById(command.getCommand().getSocialInsuranceOfficeDto().getCode().toString());
		if (findOffice == null) {
			// TODO show error message
		} else {
			insuranceSocialService.update(SocialInsuranceOfficeDomain);
		}
		// TODO return item update
		return;
	}

}
