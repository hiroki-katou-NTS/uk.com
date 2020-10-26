package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import mockit.Mocked;
import nts.arc.testing.assertion.NtsAssert;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import nts.uk.shr.com.enumcommon.NotUseAtr;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserInfoUseMethod_Test {

    SettingContactInformationDto settingContactInformationDto = null;

    List<EmailDestinationFunctionDto> emailDestinationFunctionDtos = null;

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
                .contactUsageSetting(ContactUsageSetting.INDIVIDUAL_SELECT.value)
                .contactName("")
                .build());
        otherContactDtos.add(OtherContactDto.builder()
                .no(2)
                .contactUsageSetting(ContactUsageSetting.INDIVIDUAL_SELECT.value)
                .contactName("")
                .build());
        otherContactDtos.add(OtherContactDto.builder()
                .no(3)
                .contactUsageSetting(ContactUsageSetting.INDIVIDUAL_SELECT.value)
                .contactName("")
                .build());
        otherContactDtos.add(OtherContactDto.builder()
                .no(4)
                .contactUsageSetting(ContactUsageSetting.INDIVIDUAL_SELECT.value)
                .contactName("")
                .build());
        otherContactDtos.add(OtherContactDto.builder()
                .no(5)
                .contactUsageSetting(ContactUsageSetting.INDIVIDUAL_SELECT.value)
                .contactName("")
                .build());

        settingContactInformationDto = SettingContactInformationDto.builder()
                .dialInNumber(ContactSettingDto.builder()
                        .contactUsageSetting(ContactUsageSetting.INDIVIDUAL_SELECT.value)
                        .updatable(1)
                        .build())
                .companyEmailAddress(ContactSettingDto.builder()
                        .contactUsageSetting(ContactUsageSetting.INDIVIDUAL_SELECT.value)
                        .updatable(1)
                        .build())
                .companyMobileEmailAddress(ContactSettingDto.builder()
                        .contactUsageSetting(ContactUsageSetting.INDIVIDUAL_SELECT.value)
                        .updatable(1)
                        .build())
                .personalEmailAddress(ContactSettingDto.builder()
                        .contactUsageSetting(ContactUsageSetting.INDIVIDUAL_SELECT.value)
                        .updatable(1)
                        .build())
                .personalMobileEmailAddress(ContactSettingDto.builder()
                        .contactUsageSetting(ContactUsageSetting.INDIVIDUAL_SELECT.value)
                        .updatable(1)
                        .build())
                .extensionNumber(ContactSettingDto.builder()
                        .contactUsageSetting(ContactUsageSetting.INDIVIDUAL_SELECT.value)
                        .updatable(1)
                        .build())
                .companyMobilePhone(ContactSettingDto.builder()
                        .contactUsageSetting(ContactUsageSetting.INDIVIDUAL_SELECT.value)
                        .updatable(1)
                        .build())
                .personalMobilePhone(ContactSettingDto.builder()
                        .contactUsageSetting(ContactUsageSetting.INDIVIDUAL_SELECT.value)
                        .updatable(1)
                        .build())
                .emergencyNumber1(ContactSettingDto.builder()
                        .contactUsageSetting(ContactUsageSetting.DO_NOT_USE.value)
                        .updatable(1)
                        .build())
                .emergencyNumber2(ContactSettingDto.builder()
                        .contactUsageSetting(ContactUsageSetting.USE.value)
                        .updatable(1)
                        .build())
                .otherContacts(otherContactDtos)
                .build();

        domainDto1 = UserInfoUseMethod_Dto.builder()
                .settingContactInformation(settingContactInformationDto)
                .emailDestinationFunctions(emailDestinationFunctionDtos)
                .useOfLanguage(1)
                .useOfNotice(1)
                .useOfPassword(1)
                .useOfProfile(1)
                .companyId("000000000000-0001")
                .build();

        domain1 = UserInfoUseMethod_.createFromMemento(domainDto1);

        domainDto2 = UserInfoUseMethod_Dto.builder().build();
        domain1.setMemento(domainDto2);
    }

    @Test
    public void getters() {
        NtsAssert.invokeGetters(domain1);
        NtsAssert.invokeGetters(domainDto2);
    }

//    @Test
//    public void testDefault() {
//    	ContactSettingDtoBuilder builder = ContactSettingDto.builder();
//        assertEquals(builder.toString(), builder.getClass().getName() + "@" + Integer.toHexString(builder.hashCode()));
//        ContactSettingDto dto = builder
//                .contactUsageSetting(ContactUsageSetting.INDIVIDUAL_SELECT.value)
//                .updatable(1)
//                .build();
//        assertEquals(dto, new ContactSettingDto(ContactUsageSetting.INDIVIDUAL_SELECT.value, 1));
//    }

    @Test
    public void testEmailClassificationNull() {
        assertNull(EmailClassification.valueOf(10));
    }

    @Test
    public void testContactUsageSettingNull() {
        assertNull(ContactUsageSetting.valueOf(10));
    }

    @Test
    public void testContactSetting() {

    }
}
