package nts.uk.query.app.user.information;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.ContactName;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.ContactSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.ContactUsageSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.EmailClassification;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.EmailDestinationFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.OtherContact;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SettingContactInformation;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod_;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod_Repository;
import nts.uk.query.app.user.information.setting.MailFunctionDto;
import nts.uk.query.app.user.information.setting.UserInfoUseMethod_Dto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class UserInformationSettingScreenQuery {
	@Inject
    private UserInfoUseMethod_Repository userInfoUseMethod_repository;
	
	@Inject
	private MailFunctionRepository mailFunctionRepository;

	public UserInformationSettingDto getUserInformationSettings() {
		String loginCid = AppContexts.user().companyId();
		Optional<UserInfoUseMethod_> userInfoUseMethod_ = this.userInfoUseMethod_repository.findByCId(loginCid);
		UserInfoUseMethod_Dto userInfoUseMethodDto = UserInfoUseMethod_Dto.builder().build();
		
		if(!userInfoUseMethod_.isPresent()) {
			List<EmailDestinationFunction> emailDestinationFunctionDtos = new ArrayList<EmailDestinationFunction>();
			emailDestinationFunctionDtos.add(EmailDestinationFunction.builder()
					.emailClassification(EmailClassification.valueOf(0))
					.functionIds(new ArrayList<FunctionId>())
					.build());
			emailDestinationFunctionDtos.add(EmailDestinationFunction.builder()
					.emailClassification(EmailClassification.valueOf(1))
					.functionIds(new ArrayList<FunctionId>())
					.build());
			emailDestinationFunctionDtos.add(EmailDestinationFunction.builder()
					.emailClassification(EmailClassification.valueOf(2))
					.functionIds(new ArrayList<FunctionId>())
					.build());
			emailDestinationFunctionDtos.add(EmailDestinationFunction.builder()
					.emailClassification(EmailClassification.valueOf(3))
					.functionIds(new ArrayList<FunctionId>())
					.build());
			
			SettingContactInformation settingContactInformation = new SettingContactInformation();
			settingContactInformation.setDialInNumber(ContactSetting.builder()
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.updatable(Optional.ofNullable(NotUseAtr.valueOf(0)))
					.build());
			settingContactInformation.setCompanyEmailAddress(ContactSetting.builder()
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.updatable(Optional.ofNullable(NotUseAtr.valueOf(0)))
					.build());
			settingContactInformation.setCompanyMobileEmailAddress(ContactSetting.builder()
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.updatable(Optional.ofNullable(NotUseAtr.valueOf(0)))
					.build());
			settingContactInformation.setPersonalEmailAddress(ContactSetting.builder()
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.updatable(Optional.ofNullable(NotUseAtr.valueOf(0)))
					.build());
			settingContactInformation.setPersonalMobileEmailAddress(ContactSetting.builder()
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.updatable(Optional.ofNullable(NotUseAtr.valueOf(0)))
					.build());
			settingContactInformation.setExtensionNumber(ContactSetting.builder()
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.updatable(Optional.ofNullable(NotUseAtr.valueOf(0)))
					.build());
			settingContactInformation.setCompanyMobilePhone(ContactSetting.builder()
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.updatable(Optional.ofNullable(NotUseAtr.valueOf(0)))
					.build());
			settingContactInformation.setPersonalMobilePhone(ContactSetting.builder()
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.updatable(Optional.ofNullable(NotUseAtr.valueOf(0)))
					.build());
			settingContactInformation.setEmergencyNumber1(ContactSetting.builder()
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.updatable(Optional.ofNullable(NotUseAtr.valueOf(0)))
					.build());
			settingContactInformation.setEmergencyNumber2(ContactSetting.builder()
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.updatable(Optional.ofNullable(NotUseAtr.valueOf(0)))
					.build());
			
			List<OtherContact> otherContacts = new ArrayList<OtherContact>();
			otherContacts.add(OtherContact.builder()
					.no(1)
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.contactName(new ContactName(""))
					.build());
			otherContacts.add(OtherContact.builder()
					.no(2)
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.contactName(new ContactName(""))
					.build());
			otherContacts.add(OtherContact.builder()
					.no(3)
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.contactName(new ContactName(""))
					.build());
			otherContacts.add(OtherContact.builder()
					.no(4)
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.contactName(new ContactName(""))
					.build());
			otherContacts.add(OtherContact.builder()
					.no(5)
					.contactUsageSetting(ContactUsageSetting.DO_NOT_USE)
					.contactName(new ContactName(""))
					.build());
			
			settingContactInformation.setOtherContacts(otherContacts);
			
			userInfoUseMethodDto.setCompanyId(loginCid);
			userInfoUseMethodDto.setEmailDestinationFunctions(emailDestinationFunctionDtos);
			userInfoUseMethodDto.setSettingContactInformation(settingContactInformation);
			userInfoUseMethodDto.setUseOfLanguage(0);
			userInfoUseMethodDto.setUseOfProfile(0);
			userInfoUseMethodDto.setUseOfNotice(0);
			userInfoUseMethodDto.setUseOfPassword(0);
		}
		userInfoUseMethod_.ifPresent(method -> method.setMemento(userInfoUseMethodDto));
		
		List<MailFunction> mailFunctions = this.mailFunctionRepository.findAll();
		List<MailFunctionDto> mailFunctionDtos = mailFunctions.stream()
				.map(m -> {
					MailFunctionDto dto = new MailFunctionDto();
					m.saveToMemento(dto);
					return dto;
				})
				.collect(Collectors.toList());
		
		return UserInformationSettingDto.builder()
				.userInfoUseMethod_Dto(userInfoUseMethodDto)
				.mailFunctionDtos(mailFunctionDtos)
				.build();
	}
}
