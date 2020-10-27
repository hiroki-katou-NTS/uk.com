package nts.uk.ctx.sys.auth.dom.personal.contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;

import mockit.Mocked;


public class PersonalContactTest {
	
	@Mocked
	static EmergencyContactDto mockEmergencyContact1;
	
	@Mocked
	static EmergencyContactDto mockEmergencyContact2;
	
	@Mocked
	static List<OtherContactDto> mockOtherContacts;
	
	@Mocked
	static  PersonalContactDto mockPsDto;
	
	
	@BeforeClass
	public static void beforeTest() {
		mockEmergencyContact1 = EmergencyContactDto.builder()
				.contactName("contactName1")
				.phoneNumber("phoneNumber1")
				.remark("remark1")
				.build();
		mockEmergencyContact2 = EmergencyContactDto.builder()
				.contactName("contactName2")
				.phoneNumber("phoneNumber2")
				.remark("remark2")
				.build();
		mockOtherContacts = new ArrayList<OtherContactDto>();
		
		mockOtherContacts.add(OtherContactDto.builder()
				.otherContactNo(1)
				.isDisplay(true)
				.address("address1")
				.build());
		
		mockOtherContacts.add(OtherContactDto.builder()
				.otherContactNo(2)
				.isDisplay(true)
				.address("address2")
				.build());
		
		mockOtherContacts.add(OtherContactDto.builder()
				.otherContactNo(3)
				.isDisplay(true)
				.address("address3")
				.build());
		
		mockOtherContacts.add(OtherContactDto.builder()
				.otherContactNo(4)
				.isDisplay(true)
				.address("address4")
				.build());
		
		mockOtherContacts.add(OtherContactDto.builder()
				.otherContactNo(5)
				.isDisplay(true)
				.address("address5")
				.build());
		
		mockPsDto = PersonalContactDto.builder()
				.personalId("personalId")
				.mailAddress("mailAddress")
				.isMailAddressDisplay(true)
				.mobileEmailAddress("mobileEmailAddress")
				.isMobileEmailAddressDisplay(true)
				.phoneNumber("phoneNumber")
				.isPhoneNumberDisplay(true)
				.emergencyContact1(mockEmergencyContact1)
				.isEmergencyContact1Display(true)
				.emergencyContact2(mockEmergencyContact2)
				.isEmergencyContact2Display(true)
				.otherContacts(mockOtherContacts)
				.build();
	}
	
    public boolean emergencyContactEquals(EmergencyContact object1, EmergencyContact object2) {
    	return object1.getRemark().v().equalsIgnoreCase(object2.getRemark().v())
	    		&& object1.getContactName().v().equalsIgnoreCase(object2.getContactName().v())
	    		&& object1.getPhoneNumber().v().equalsIgnoreCase(object2.getPhoneNumber().v());
    	
    }
    
	@Test
	public void createFromMementoAndGetMemento() {
		// when
		PersonalContact domain = PersonalContact.createFromMemento(mockPsDto);

		// then
        assertThat(domain.getPersonalId()).isEqualTo(mockPsDto.getPersonalId());
        assertThat(domain.getMailAddress().get().v()).isEqualTo(mockPsDto.getMailAddress());
        assertThat(domain.getIsMailAddressDisplay().get()).isEqualTo( mockPsDto.getIsMailAddressDisplay());
        assertThat(domain.getMobileEmailAddress().get().v()).isEqualTo(mockPsDto.getMobileEmailAddress());
        assertThat(domain.getIsMobileEmailAddressDisplay().get()).isEqualTo(mockPsDto.getIsMobileEmailAddressDisplay());
        assertThat(domain.getPhoneNumber().get().v()).isEqualTo(mockPsDto.getPhoneNumber());
        assertThat(domain.getIsPhoneNumberDisplay().get()).isEqualTo(mockPsDto.getIsPhoneNumberDisplay());
        assertTrue(emergencyContactEquals(mockPsDto.getEmergencyContact1(),domain.getEmergencyContact1().get()));
        assertThat(domain.getIsEmergencyContact1Display().get()).isEqualTo(mockPsDto.getIsEmergencyContact1Display());
        assertTrue(emergencyContactEquals(mockPsDto.getEmergencyContact2(),domain.getEmergencyContact2().get()));
        assertThat(domain.getIsEmergencyContact2Display().get()).isEqualTo(mockPsDto.getIsEmergencyContact2Display());
	}
	
	@Test
	public void setMemento() {
		//given
		PersonalContactDto nullDto = PersonalContactDto.builder().build();
		PersonalContact domain = PersonalContact.createFromMemento(mockPsDto);
		
		//when
		domain.setMemento(nullDto);
		
		//then
		 assertThat(domain.getPersonalId()).isEqualTo(mockPsDto.getPersonalId());
	        assertThat(domain.getMailAddress().get().v()).isEqualTo(mockPsDto.getMailAddress());
	        assertThat(domain.getIsMailAddressDisplay().get()).isEqualTo( mockPsDto.getIsMailAddressDisplay());
	        assertThat(domain.getMobileEmailAddress().get().v()).isEqualTo(mockPsDto.getMobileEmailAddress());
	        assertThat(domain.getIsMobileEmailAddressDisplay().get()).isEqualTo(mockPsDto.getIsMobileEmailAddressDisplay());
	        assertThat(domain.getPhoneNumber().get().v()).isEqualTo(mockPsDto.getPhoneNumber());
	        assertThat(domain.getIsPhoneNumberDisplay().get()).isEqualTo(mockPsDto.getIsPhoneNumberDisplay());
	        assertTrue(emergencyContactEquals(mockPsDto.getEmergencyContact1(),domain.getEmergencyContact1().get()));
	        assertThat(domain.getIsEmergencyContact1Display().get()).isEqualTo(mockPsDto.getIsEmergencyContact1Display());
	        assertTrue(emergencyContactEquals(mockPsDto.getEmergencyContact2(),domain.getEmergencyContact2().get()));
	        assertThat(domain.getIsEmergencyContact2Display().get()).isEqualTo(mockPsDto.getIsEmergencyContact2Display());
	        assertThat(domain.getOtherContacts().size()).isEqualTo(mockPsDto.getOtherContacts().size());
		}
	
	@Test
	public void getMementoNull() {
		//given
		PersonalContactDto mockDtoNull = PersonalContactDto.builder()
				.personalId("personalId")
				.emergencyContact1(EmergencyContactDto.builder().build())
				.emergencyContact2(EmergencyContactDto.builder().build())
				.otherContacts(new ArrayList<OtherContactDto>())
				.build();
		// when
		PersonalContact domain = new PersonalContact();
		domain.getMemento(mockDtoNull);

		// then
		assertThat(domain.getPersonalId()).isEqualTo(mockDtoNull.getPersonalId());
        assertThat(domain.getMailAddress().orElse(null)).isEqualTo(null);
        assertThat(domain.getIsMailAddressDisplay().get()).isEqualTo( false);
        assertThat(domain.getMobileEmailAddress().orElse(null)).isEqualTo(null);
        assertThat(domain.getIsMobileEmailAddressDisplay().get()).isEqualTo(false);
        assertThat(domain.getPhoneNumber().orElse(null)).isEqualTo(null);
        assertThat(domain.getIsPhoneNumberDisplay().get()).isEqualTo(false);
        assertThat(domain.getEmergencyContact1().get().getContactName().v()).isEqualTo("");
        assertThat(domain.getIsEmergencyContact1Display().get()).isEqualTo(false);
        assertThat(domain.getEmergencyContact2().get().getContactName().v()).isEqualTo("");
        assertThat(domain.getIsEmergencyContact2Display().get()).isEqualTo(false);
        assertThat(domain.getOtherContacts().size()).isEqualTo(0);
	}
	
	@Test
	public void emergencyContactBuilderToString() {
		String contact = EmergencyContact.builder()
				.remark(new Remark("remark"))
				.contactName(new ContactName("contactName"))
				.phoneNumber(new PhoneNumber("phoneNumber"))
				.toString();
		assertTrue(!contact.equals(null));
	}
	
	@Test
	public void otherContactBuilderToString() {
		String contact = OtherContact.builder()
				.otherContactNo(1)
				.isDisplay(Optional.ofNullable(false))
				.address(new OtherContactAddress("address"))
				.toString();
		assertTrue(!contact.equals(null));
	}
}
