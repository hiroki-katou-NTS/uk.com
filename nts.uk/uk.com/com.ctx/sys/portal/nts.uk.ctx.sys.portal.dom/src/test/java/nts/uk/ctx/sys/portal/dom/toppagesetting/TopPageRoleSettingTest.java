package nts.uk.ctx.sys.portal.dom.toppagesetting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mockit.Mocked;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetCode;

public class TopPageRoleSettingTest {
	
	private static final String COMPANY_ID = "companyId";
	private static final String ROLE_SET_CODE = "roleSetCode";
	private static final String TOP_MENU_CODE = "topMenuCode";
	private static final String LOGIN_MENU_CODE = "loginMenuCode";

	@Mocked
	private static TopPageRoleSettingDto mockDto = TopPageRoleSettingDto.builder()
		.companyId(COMPANY_ID)
		.roleSetCode(ROLE_SET_CODE)
		.topMenuCode(TOP_MENU_CODE)
		.loginMenuCode(LOGIN_MENU_CODE)
		.switchingDate(0)
		.system(0)
		.menuClassification(0)
		.build();
	
	@Test
	public void testContructorAndGetter() {
		TopPageRoleSetting domain = new TopPageRoleSetting(
				COMPANY_ID, 
				new RoleSetCode(ROLE_SET_CODE),
				new LoginMenuCode(LOGIN_MENU_CODE), 
				new TopMenuCode(TOP_MENU_CODE), 
				EnumAdaptor.valueOf(0, MenuClassification.class), 
				EnumAdaptor.valueOf(0, System.class),
				new SwitchingDate(0));
		assertThat(domain.getCompanyId()).isEqualTo(COMPANY_ID);
	}
	
	@Test
	public void testAllContructor() {
		TopPageRoleSetting domain = new TopPageRoleSetting(COMPANY_ID, new RoleSetCode(ROLE_SET_CODE));
		assertThat(domain.getCompanyId()).isEqualTo(COMPANY_ID);
		assertThat(domain.getRoleSetCode().v()).isEqualTo(ROLE_SET_CODE);
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
				.companyId(COMPANY_ID)
				.roleSetCode(new RoleSetCode(ROLE_SET_CODE))
				.build();
		
		//Then
		assertThat(domain.getCompanyId()).isEqualTo(COMPANY_ID);
		assertThat(domain.getRoleSetCode().v()).isEqualTo(mockDto.getRoleSetCode());
		
		String domain2 = TopPageRoleSetting.builder()
				.companyId(COMPANY_ID)
				.roleSetCode(new RoleSetCode(ROLE_SET_CODE))
				.toString();
		assertThat(domain2.isEmpty()).isFalse();
	}
}
