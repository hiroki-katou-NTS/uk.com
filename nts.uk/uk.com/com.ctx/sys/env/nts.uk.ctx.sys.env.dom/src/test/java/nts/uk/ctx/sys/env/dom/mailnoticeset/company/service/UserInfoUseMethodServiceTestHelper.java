package nts.uk.ctx.sys.env.dom.mailnoticeset.company.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.sys.env.dom.mailnoticeset.company.ContactSettingDto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.EmailDestinationFunctionDto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.OtherContactDto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SettingContactInformationDto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodDto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInformationUseMethod;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.EmployeeInfoContactImport;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.OtherContactDTO;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.PersonContactImport;

public class UserInfoUseMethodServiceTestHelper {

	public static UserInformationUseMethod initUserInformationUseMethod(Integer contactUsageSetting, Boolean nullOtherContact, Boolean nullSettingContactInfo) {
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
        List<OtherContactDto> otherContactDtos = new ArrayList<>();
        otherContactDtos.add(OtherContactDto.builder()
                .no(1)
                .contactUsageSetting(contactUsageSetting)
                .contactName("")
                .build());
        otherContactDtos.add(OtherContactDto.builder()
                .no(2)
                .contactUsageSetting(contactUsageSetting)
                .contactName("")
                .build());
        otherContactDtos.add(OtherContactDto.builder()
                .no(3)
                .contactUsageSetting(contactUsageSetting)
                .contactName("")
                .build());
        otherContactDtos.add(OtherContactDto.builder()
                .no(4)
                .contactUsageSetting(contactUsageSetting)
                .contactName("")
                .build());
        otherContactDtos.add(OtherContactDto.builder()
                .no(5)
                .contactUsageSetting(contactUsageSetting)
                .contactName("")
                .build());

        if (nullOtherContact) {
        	otherContactDtos = null;
        }
        SettingContactInformationDto settingContactInformationDto = SettingContactInformationDto.builder()
                .dialInNumber(nullSettingContactInfo ? null : ContactSettingDto.builder()
                        .contactUsageSetting(contactUsageSetting)
                        .updatable(1)
                        .build())
                .companyEmailAddress(nullSettingContactInfo ? null : ContactSettingDto.builder()
                        .contactUsageSetting(contactUsageSetting)
                        .updatable(1)
                        .build())
                .companyMobileEmailAddress(nullSettingContactInfo ? null : ContactSettingDto.builder()
                        .contactUsageSetting(contactUsageSetting)
                        .updatable(1)
                        .build())
                .personalEmailAddress(nullSettingContactInfo ? null : ContactSettingDto.builder()
                        .contactUsageSetting(contactUsageSetting)
                        .updatable(1)
                        .build())
                .personalMobileEmailAddress(nullSettingContactInfo ? null : ContactSettingDto.builder()
                        .contactUsageSetting(contactUsageSetting)
                        .updatable(1)
                        .build())
                .extensionNumber(nullSettingContactInfo ? null : ContactSettingDto.builder()
                        .contactUsageSetting(contactUsageSetting)
                        .updatable(1)
                        .build())
                .companyMobilePhone(nullSettingContactInfo ? null : ContactSettingDto.builder()
                        .contactUsageSetting(contactUsageSetting)
                        .updatable(1)
                        .build())
                .personalMobilePhone(nullSettingContactInfo ? null : ContactSettingDto.builder()
                        .contactUsageSetting(contactUsageSetting)
                        .updatable(1)
                        .build())
                .emergencyNumber1(nullSettingContactInfo ? null : ContactSettingDto.builder()
                        .contactUsageSetting(contactUsageSetting)
                        .updatable(1)
                        .build())
                .emergencyNumber2(nullSettingContactInfo ? null : ContactSettingDto.builder()
                        .contactUsageSetting(contactUsageSetting)
                        .updatable(1)
                        .build())
                .otherContacts(otherContactDtos)
                .build();

        UserInfoUseMethodDto memento = UserInfoUseMethodDto.builder()
                .companyId("mock-companyId")
                .useOfProfile(1)
                .useOfPassword(1)
                .useOfNotice(1)
                .useOfLanguage(1)
                .emailDestinationFunctionDtos(emailDestinationFunctionDtos)
                .settingContactInformationDto(settingContactInformationDto)
                .build();
        return UserInformationUseMethod.createFromMemento(memento);
	}
	
	public static PersonContactImport initPersonContactImport(Boolean nullOtherContact) {
		List<OtherContactDTO> otherContactDtos = new ArrayList<>();
        otherContactDtos.add(OtherContactDTO.builder()
                .no(1)
                .isDisplay(true)
                .address("")
                .build());
        otherContactDtos.add(OtherContactDTO.builder()
                .no(2)
                .isDisplay(true)
                .address("")
                .build());
        otherContactDtos.add(OtherContactDTO.builder()
                .no(3)
                .isDisplay(true)
                .address("")
                .build());
        otherContactDtos.add(OtherContactDTO.builder()
                .no(4)
                .isDisplay(true)
                .address("")
                .build());
        otherContactDtos.add(OtherContactDTO.builder()
                .no(5)
                .isDisplay(true)
                .address("")
                .build());
        if (nullOtherContact) {
        	otherContactDtos = null;
        }
		return new PersonContactImport(
				"mock-personalId",
				"test@gmail.com",
				"testMB@gmail.com",
				"012-345-6789",
				"0123",
				"4567",
				Optional.of(true),
				Optional.of(true),
				Optional.of(true),
				Optional.of(true),
				Optional.of(true),
				otherContactDtos
			);
	}
	
	public static EmployeeInfoContactImport initEmployeeInfoContactImport() {
		return new EmployeeInfoContactImport(
				"mock-employeeId",
				"test@gmail.com",
				"testMB@gmail.com",
				"032-456-789",
				"012",
				"12",
				Optional.of(true),
				Optional.of(true),
				Optional.of(true),
				Optional.of(true),
				Optional.of(true)
			);
	}
}
