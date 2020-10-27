package nts.uk.ctx.sys.auth.dom.employee.contact;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mockit.Mocked;


public class EmployeeContactTest {

	@Mocked
	EmployeeContactDto mockDto = EmployeeContactDto.builder()
			.employeeId("employeeId")
			.mailAddress("mailAddress")
			.isMailAddressDisplay(true)
			.seatDialIn("seatDialIn")
			.isSeatDialInDisplay(true)
			.seatExtensionNumber("seatExtensionNumber")
			.isSeatExtensionNumberDisplay(true)
			.mobileMailAddress("mobileMailAddress")
			.isMobileMailAddressDisplay(true)
			.cellPhoneNumber("cellPhoneNumber")
			.isCellPhoneNumberDisplay(true)
			.build();
	
	@Test
	public void createFromMementoAndGetMemento() {
		// when
		EmployeeContact domain = EmployeeContact.createFromMemento(mockDto);

		// then
        assertThat(domain.getEmployeeId()).isEqualTo(mockDto.getEmployeeId());
        assertThat(domain.getMailAddress().get().v()).isEqualTo(mockDto.getMailAddress());
        assertThat(domain.getIsMailAddressDisplay().get()).isEqualTo( mockDto.getIsMailAddressDisplay());
        assertThat(domain.getSeatDialIn().get().v()).isEqualTo(mockDto.getSeatDialIn());
        assertThat(domain.getIsSeatDialInDisplay().get()).isEqualTo(mockDto.getIsSeatDialInDisplay());
        assertThat(domain.getSeatExtensionNumber().get().v()).isEqualTo(mockDto.getSeatExtensionNumber());
        assertThat(domain.getIsSeatDialInDisplay().get()).isEqualTo(mockDto.getIsSeatDialInDisplay());
        assertThat(domain.getMobileMailAddress().get().v() ).isEqualTo( mockDto.getMobileMailAddress());
        assertThat(domain.getIsMobileMailAddressDisplay().get()).isEqualTo(mockDto.getIsMobileMailAddressDisplay());
        assertThat(domain.getCellPhoneNumber().get().v() ).isEqualTo(mockDto.getCellPhoneNumber());
        assertThat(domain.getIsCellPhoneNumberDisplay().get()).isEqualTo(mockDto.getIsCellPhoneNumberDisplay());
	}
	
	@Test
	public void setMemento() {
		//given
		EmployeeContactDto avtarDto = EmployeeContactDto.builder().build();
		EmployeeContact domain = EmployeeContact.createFromMemento(mockDto);
		
		//when
		domain.setMemento(avtarDto);
		
		//then
			assertThat(domain.getEmployeeId()).isEqualTo(mockDto.getEmployeeId());
	        assertThat(domain.getMailAddress().get().v()).isEqualTo(mockDto.getMailAddress());
	        assertThat(domain.getIsMailAddressDisplay().get()).isEqualTo( mockDto.getIsMailAddressDisplay());
	        assertThat(domain.getSeatDialIn().get().v()).isEqualTo(mockDto.getSeatDialIn());
	        assertThat(domain.getIsSeatDialInDisplay().get()).isEqualTo(mockDto.getIsSeatDialInDisplay());
	        assertThat(domain.getSeatExtensionNumber().get().v()).isEqualTo(mockDto.getSeatExtensionNumber());
	        assertThat(domain.getIsSeatExtensionNumberDisplay().get()).isEqualTo(mockDto.getIsSeatExtensionNumberDisplay());
	        assertThat(domain.getMobileMailAddress().get().v() ).isEqualTo( mockDto.getMobileMailAddress());
	        assertThat(domain.getIsMobileMailAddressDisplay().get()).isEqualTo(mockDto.getIsMobileMailAddressDisplay());
	        assertThat(domain.getCellPhoneNumber().get().v() ).isEqualTo(mockDto.getCellPhoneNumber());
	        assertThat(domain.getIsCellPhoneNumberDisplay().get()).isEqualTo(mockDto.getIsCellPhoneNumberDisplay());
		}
	
	@Test
	public void getMementoNull() {
		//given
		EmployeeContactDto mockDtoNull = EmployeeContactDto.builder()
				.employeeId("employeeId")
				.build();
		// when
		EmployeeContact domain = new EmployeeContact();
		domain.getMemento(mockDtoNull);

		// then
		assertThat(domain.getEmployeeId()).isEqualTo(mockDtoNull.getEmployeeId());
        assertThat(domain.getMailAddress().orElse(null)).isEqualTo(null);
        assertThat(domain.getIsMailAddressDisplay().get()).isEqualTo( false);
        assertThat(domain.getSeatDialIn().orElse(null)).isEqualTo(null);
        assertThat(domain.getIsSeatDialInDisplay().get()).isEqualTo(false);
        assertThat(domain.getSeatExtensionNumber().orElse(null)).isEqualTo(null);
        assertThat(domain.getIsSeatDialInDisplay().get()).isEqualTo(false);
        assertThat(domain.getMobileMailAddress().orElse(null)).isEqualTo(null);
        assertThat(domain.getIsMobileMailAddressDisplay().get()).isEqualTo(false);
        assertThat(domain.getCellPhoneNumber().orElse(null)).isEqualTo(null);
        assertThat(domain.getIsCellPhoneNumberDisplay().get()).isEqualTo(false);
	}
}
