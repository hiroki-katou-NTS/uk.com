package nts.uk.query.app.user.information;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod_;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod_Repository;
import nts.uk.query.app.user.information.setting.*;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UserInformationSettingScreenQuery {
	@Inject
    private UserInfoUseMethod_Repository userInfoUseMethod_repository;
	
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
		Optional<UserInfoUseMethod_> userInfoUseMethod_ = this.userInfoUseMethod_repository.findByCId(loginCid);
		UserInfoUseMethod_Dto userInfoUseMethod_dto = UserInfoUseMethod_Dto.builder().build();
		
		if(!userInfoUseMethod_.isPresent()) {
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

			userInfoUseMethod_dto.setCompanyId(loginCid);
			userInfoUseMethod_dto.setEmailDestinationFunctionDtos(emailDestinationFunctionDtos);
			userInfoUseMethod_dto.setSettingContactInformationDto(settingContactInformation);
			userInfoUseMethod_dto.setUseOfLanguage(1);
			userInfoUseMethod_dto.setUseOfNotice(1);
			userInfoUseMethod_dto.setUseOfPassword(1);
			userInfoUseMethod_dto.setUseOfProfile(1);
		}
		userInfoUseMethod_.ifPresent(method -> method.setMemento(userInfoUseMethod_dto));
		
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
				.userInfoUseMethod_Dto(userInfoUseMethod_dto)
				.mailFunctionDtos(mailFunctionDtos)
				.build();
	}
}
