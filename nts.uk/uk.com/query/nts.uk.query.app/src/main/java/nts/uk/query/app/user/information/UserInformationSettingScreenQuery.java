package nts.uk.query.app.user.information;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInformationUseMethod;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInformationUseMethodRepository;
import nts.uk.query.app.user.information.setting.*;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UserInformationSettingScreenQuery {
	@Inject
    private UserInformationUseMethodRepository userInformationUseMethodrepository;
	
	@Inject
	private MailFunctionRepository mailFunctionRepository;

	/**
	 * ユーザ情報の設定を取得する
	 * @return
	 */
	public UserInformationSettingDto getUserInformationSettings() {
		String loginCid = AppContexts.user().companyId();
		
		/**
		 * Step get(会社ID): ユーザ情報の利用方法
		 */
		Optional<UserInformationUseMethod> userInformationUseMethod = this.userInformationUseMethodrepository.findByCId(loginCid);
		UserInformationUseMethodDto userInformationUseMethodDto = UserInformationUseMethodDto.builder().build();
		
		if(!userInformationUseMethod.isPresent()) {
			List<EmailDestinationFunctionDto> emailDestinationFunctionDtos = new ArrayList<>();
			emailDestinationFunctionDtos.add(EmailDestinationFunctionDto.builder()
					.emailClassification(0)
					.functionIds(new ArrayList<>())
					.build());
			emailDestinationFunctionDtos.add(EmailDestinationFunctionDto.builder()
					.emailClassification(1)
					.functionIds(new ArrayList<>())
					.build());
			emailDestinationFunctionDtos.add(EmailDestinationFunctionDto.builder()
					.emailClassification(2)
					.functionIds(new ArrayList<>())
					.build());
			emailDestinationFunctionDtos.add(EmailDestinationFunctionDto.builder()
					.emailClassification(3)
					.functionIds(new ArrayList<>())
					.build());

			List<OtherContactDto> otherContacts = new ArrayList<OtherContactDto>();
			otherContacts.add(OtherContactDto.builder()
					.no(1)
					.contactUsageSetting(2)
					.contactName("")
					.build());
			otherContacts.add(OtherContactDto.builder()
					.no(2)
					.contactUsageSetting(2)
					.contactName("")
					.build());
			otherContacts.add(OtherContactDto.builder()
					.no(3)
					.contactUsageSetting(2)
					.contactName("")
					.build());
			otherContacts.add(OtherContactDto.builder()
					.no(4)
					.contactUsageSetting(2)
					.contactName("")
					.build());
			otherContacts.add(OtherContactDto.builder()
					.no(5)
					.contactUsageSetting(2)
					.contactName("")
					.build());
			
			SettingContactInformationDto settingContactInformation = SettingContactInformationDto.builder()
					.dialInNumber(ContactSettingDto.builder()
							.contactUsageSetting(2)
							.updatable(1)
							.build())
					.companyEmailAddress(ContactSettingDto.builder()
							.contactUsageSetting(2)
							.updatable(1)
							.build())
					.companyMobileEmailAddress(ContactSettingDto.builder()
							.contactUsageSetting(2)
							.updatable(1)
							.build())
					.companyMobilePhone(ContactSettingDto.builder()
							.contactUsageSetting(2)
							.updatable(1)
							.build())
					.personalEmailAddress(ContactSettingDto.builder()
							.contactUsageSetting(2)
							.updatable(1)
							.build())
					.personalMobileEmailAddress(ContactSettingDto.builder()
							.contactUsageSetting(2)
							.updatable(1)
							.build())
					.personalMobilePhone(ContactSettingDto.builder()
							.contactUsageSetting(2)
							.updatable(1)
							.build())
					.extensionNumber(ContactSettingDto.builder()
							.contactUsageSetting(2)
							.updatable(1)
							.build())
					.emergencyNumber1(ContactSettingDto.builder()
							.contactUsageSetting(2)
							.updatable(1)
							.build())
					.emergencyNumber2(ContactSettingDto.builder()
							.contactUsageSetting(2)
							.updatable(1)
							.build())
					.otherContacts(otherContacts)
					.build();

			userInformationUseMethodDto.setCompanyId(loginCid);
			userInformationUseMethodDto.setEmailDestinationFunctionDtos(emailDestinationFunctionDtos);
			userInformationUseMethodDto.setSettingContactInformationDto(settingContactInformation);
			userInformationUseMethodDto.setUseOfLanguage(1);
			userInformationUseMethodDto.setUseOfNotice(1);
			userInformationUseMethodDto.setUseOfPassword(1);
			userInformationUseMethodDto.setUseOfProfile(1);
		}
		userInformationUseMethod.ifPresent(method -> method.setMemento(userInformationUseMethodDto));
		
		/**
		 * Step get(): List<メール機能>
		 */
		List<MailFunction> mailFunctions = this.mailFunctionRepository.findAll();
		List<MailFunctionDto> mailFunctionDtos = mailFunctions.stream()
				.map(m -> {
					MailFunctionDto dto = new MailFunctionDto();
					m.saveToMemento(dto);
					return dto;
				})
				.collect(Collectors.toList());
		
		return UserInformationSettingDto.builder()
				.userInformationUseMethodDto(userInformationUseMethodDto)
				.mailFunctionDtos(mailFunctionDtos)
				.build();
	}
}
