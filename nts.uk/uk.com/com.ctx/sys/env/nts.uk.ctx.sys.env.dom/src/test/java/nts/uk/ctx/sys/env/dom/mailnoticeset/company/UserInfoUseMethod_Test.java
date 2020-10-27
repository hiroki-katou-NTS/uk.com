package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import mockit.Mocked;
import nts.arc.testing.assertion.NtsAssert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserInfoUseMethod_Test {

    @Mocked
    ContactSetting.ContactSettingBuilder contactSettingBuilder;

    @Mocked
    OtherContact.OtherContactBuilder otherContactBuilder;

    @Mocked
    EmailDestinationFunction emailDestinationFunction;

    @Mocked
    SettingContactInformation settingContactInformation;

    SettingContactInformationDto settingContactInformationDto = null;

    List<EmailDestinationFunctionDto> emailDestinationFunctionDtos = null;

    EmailDestinationFunctionDto emailDestinationFunctionDto = null;

    List<OtherContactDto> otherContactDtos = null;

    UserInfoUseMethod_Dto domainDto1 = null;

    UserInfoUseMethod_Dto domainDto2 = null;

    UserInfoUseMethod_ domain1 = null;

    @Before
    public void initTest() {
        emailDestinationFunctionDtos = new ArrayList<>();
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

        otherContactDtos = new ArrayList<>();
        otherContactDtos.add(OtherContactDto.builder()
                .no(1)
                .contactUsageSetting(2)
                .contactName("")
                .build());
        otherContactDtos.add(OtherContactDto.builder()
                .no(2)
                .contactUsageSetting(2)
                .contactName("")
                .build());
        otherContactDtos.add(OtherContactDto.builder()
                .no(3)
                .contactUsageSetting(2)
                .contactName("")
                .build());
        otherContactDtos.add(OtherContactDto.builder()
                .no(4)
                .contactUsageSetting(0)
                .contactName("")
                .build());
        otherContactDtos.add(OtherContactDto.builder()
                .no(5)
                .contactUsageSetting(1)
                .contactName("")
                .build());

        settingContactInformationDto = SettingContactInformationDto.builder()
                .dialInNumber(ContactSettingDto.builder()
                        .contactUsageSetting(2)
                        .updatable(1)
                        .build())
                .companyEmailAddress(ContactSettingDto.builder()
                        .contactUsageSetting(1)
                        .updatable(1)
                        .build())
                .companyMobileEmailAddress(ContactSettingDto.builder()
                        .contactUsageSetting(0)
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
                .extensionNumber(ContactSettingDto.builder()
                        .contactUsageSetting(2)
                        .updatable(1)
                        .build())
                .companyMobilePhone(ContactSettingDto.builder()
                        .contactUsageSetting(1)
                        .updatable(1)
                        .build())
                .personalMobilePhone(ContactSettingDto.builder()
                        .contactUsageSetting(2)
                        .updatable(1)
                        .build())
                .emergencyNumber1(ContactSettingDto.builder()
                        .contactUsageSetting(1)
                        .updatable(1)
                        .build())
                .emergencyNumber2(ContactSettingDto.builder()
                        .contactUsageSetting(2)
                        .updatable(1)
                        .build())
                .otherContacts(otherContactDtos)
                .build();

//        domainDto1 = UserInfoUseMethod_Dto.builder()
//                .settingContactInformation(settingContactInformationDto)
//                .emailDestinationFunctionDtos(emailDestinationFunctionDtos)
//                .useOfLanguage(1)
//                .useOfNotice(1)
//                .useOfPassword(1)
//                .useOfProfile(1)
//                .companyId("000000000000-0001")
//                .build();

        domain1 = UserInfoUseMethod_.createFromMemento(domainDto1);

        domainDto2 = UserInfoUseMethod_Dto.builder().build();
        domain1.setMemento(domainDto2);

        emailDestinationFunction = new EmailDestinationFunction(EmailClassification.valueOf(0), new ArrayList<>());
        emailDestinationFunctionDto = new EmailDestinationFunctionDto(0, new ArrayList<>());

//        settingContactInformation = SettingContactInformation.createFromMemento(settingContactInformationDto);
    }

    @Test
    public void getters() {
        NtsAssert.invokeGetters(domain1);
        NtsAssert.invokeGetters(domainDto2);
        NtsAssert.invokeGetters(settingContactInformation);
    }

    @Test
    public void setters() {
        emailDestinationFunction.setEmailClassification(EmailClassification.valueOf(1));
        emailDestinationFunction.setFunctionIds(new ArrayList<>());
        assertThat(emailDestinationFunctionDto.getEmailClassification().intValue()).isEqualTo(0);
        assertThat(emailDestinationFunctionDto.getFunctionIds().size()).isEqualTo(0);

        settingContactInformation.setDialInNumber(null);
        settingContactInformation.setCompanyEmailAddress(null);
        settingContactInformation.setCompanyMobileEmailAddress(null);
        settingContactInformation.setCompanyMobilePhone(null);
        settingContactInformation.setPersonalEmailAddress(null);
        settingContactInformation.setPersonalMobileEmailAddress(null);
        settingContactInformation.setPersonalMobilePhone(null);
        settingContactInformation.setExtensionNumber(null);
        settingContactInformation.setEmergencyNumber1(null);
        settingContactInformation.setEmergencyNumber2(null);
        settingContactInformation.setCompanyEmailAddress(null);
        settingContactInformation.setOtherContacts(null);

        assertNull(settingContactInformation.getDialInNumber());
        assertNull(settingContactInformation.getCompanyEmailAddress());
        assertNull(settingContactInformation.getCompanyMobileEmailAddress());
        assertNull(settingContactInformation.getCompanyMobilePhone());
        assertNull(settingContactInformation.getPersonalEmailAddress());
        assertNull(settingContactInformation.getPersonalMobileEmailAddress());
        assertNull(settingContactInformation.getPersonalMobilePhone());
        assertNull(settingContactInformation.getEmergencyNumber1());
        assertNull(settingContactInformation.getEmergencyNumber2());
        assertNull(settingContactInformation.getExtensionNumber());
        assertNull(settingContactInformation.getOtherContacts());
    }

    @Test
    public void testEmailClassificationNull() {
        assertNull(EmailClassification.valueOf(10));
    }

    @Test
    public void testContactUsageSettingNull() {
        assertNull(ContactUsageSetting.valueOf(10));
    }

    @Test
    public void testContactSettingDtoToString() {
        contactSettingBuilder = ContactSetting.builder();
        assertEquals("ContactSetting.ContactSettingBuilder(contactUsageSetting=null, updatable=null)", contactSettingBuilder.toString());
    }

    @Test
    public void testOtherContactDtoToString() {
        otherContactBuilder = OtherContact.builder();
        assertEquals("OtherContact.OtherContactBuilder(no=0, contactUsageSetting=null, contactName=null)", otherContactBuilder.toString());
    }

    @Test
    public void testEmailDestinationFunctionToString() {
        EmailDestinationFunction.EmailDestinationFunctionBuilder builder = EmailDestinationFunction.builder();
        assertEquals("EmailDestinationFunction.EmailDestinationFunctionBuilder(emailClassification=null, functionIds=null)", builder.toString());
    }
}