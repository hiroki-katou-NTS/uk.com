package nts.uk.ctx.sys.portal.dom.toppagesetting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mockit.Mocked;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetCode;

public class TopPageRoleSettingTest {

	@Mocked
	private static TopPageRoleSettingDto mockDto = TopPageRoleSettingDto.builder()
		.companyId("companyId")
		.roleSetCode("roleSetCode")
		.topMenuCode("topMenuCode")
		.loginMenuCode("loginMenuCode")
		.switchingDate(0)
		.system(0)
		.menuClassification(0)
		.build();
	
	@Test
	public void testContructorAndGetter() {
		TopPageRoleSetting domain = new TopPageRoleSetting(
				"companyId", 
				new RoleSetCode("roleSetCode"),
				new LoginMenuCode("loginMenuCode"), 
				new TopMenuCode("topMenuCode"), 
				EnumAdaptor.valueOf(0, MenuClassification.class), 
				EnumAdaptor.valueOf(0, System.class),
				new SwitchingDate(0));
		assertThat(domain.getCompanyId()).isEqualTo("companyId");
	}
	
	@Test
	public void testAllContructor() {
		TopPageRoleSetting domain = new TopPageRoleSetting("companyId", new RoleSetCode("roleSetCode"));
		assertThat(domain.getCompanyId()).isEqualTo("companyId");
		assertThat(domain.getRoleSetCode().v()).isEqualTo("roleSetCode");
	}
	
	@Test
	public void createFromMementoAndGetMemento() {
		//When
		TopPageRoleSetting domain = TopPageRoleSetting.createFromMemento(mockDto);
		//Then
		assertThat(domain.getCompanyId()).isEqualTo(mockDto.getCompanyId());
		assertThat(domain.getRoleSetCode().v()).isEqualTo(mockDto.getRoleSetCode());
	}
	
	@Test
	public void testSetMemento() {
		//Given
		TopPageRoleSettingDto dto = TopPageRoleSettingDto.builder().build();
		TopPageRoleSetting domain = TopPageRoleSetting.createFromMemento(mockDto);
		
		//When
		domain.setMemento(dto);
		
		//Then
		assertThat(domain.getCompanyId()).isEqualTo(mockDto.getCompanyId());
		assertThat(domain.getRoleSetCode().v()).isEqualTo(mockDto.getRoleSetCode());
	}
	
	@Test
	public void testMementoNull() {
		//Given
		TopPageRoleSettingDto dtoNull = TopPageRoleSettingDto.builder().build();
		
		//When
		TopPageRoleSetting domain = TopPageRoleSetting.createFromMemento(dtoNull);
		
		//Then
		assertThat(domain.getCompanyId()).isEqualTo(null);
		assertThat(domain.getRoleSetCode().v()).isEqualTo("");
	}
	
	@Test
	public void testBuilder() {
		//When
		TopPageRoleSetting domain = TopPageRoleSetting.builder()
				.companyId("companyId")
				.roleSetCode(new RoleSetCode("roleSetCode"))
				.build();
		
		//Then
		assertThat(domain.getCompanyId()).isEqualTo("companyId");
		assertThat(domain.getRoleSetCode().v()).isEqualTo(mockDto.getRoleSetCode());
		
		String domain2 = TopPageRoleSetting.builder()
				.companyId("companyId")
				.roleSetCode(new RoleSetCode("roleSetCode"))
				.toString();
		assertThat(domain2.isEmpty()).isFalse();
	}
}
