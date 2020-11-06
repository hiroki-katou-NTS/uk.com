package nts.uk.ctx.sys.portal.dom.toppagesetting.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.adapter.toppagesetting.LoginRoleSetCodeAdapter;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.toppagesetting.LoginMenuCode;
import nts.uk.ctx.sys.portal.dom.toppagesetting.MenuLogin;
import nts.uk.ctx.sys.portal.dom.toppagesetting.SwitchingDate;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopMenuCode;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSetting;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSettingRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageRoleSetting;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageRoleSettingRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSettings;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetCode;

@RunWith(JMockit.class)
public class TopPageSettingServiceTest {
	
	private static final String COMPANY_ID = "companyId";
	private static final String EMPLOYEE_ID = "employeeId";
	private static final String ROLE_SET_CODE = "roleSetCode";
	private static final String TOP_MENU_CODE = "topMenuCode";
	private static final String LOGIN_MENU_CODE = "loginMenuCode";
	
	@Tested
	private TopPageSettingService domainService;
	
	@Injectable
	private LoginRoleSetCodeAdapter adapter;
	
	@Injectable
	private TopPagePersonSettingRepository topPagePersonSettingRepo;
	
	@Injectable
	private TopPageRoleSettingRepository topPageRoleSettingRepo;
	
	@Mocked
	private static TopPageSettings topPageSetting = new TopPageSettings(
			new TopMenuCode(TOP_MENU_CODE),
			new MenuLogin(
				EnumAdaptor.valueOf(0, System.class),
				EnumAdaptor.valueOf(0, MenuClassification.class), 
				new LoginMenuCode(LOGIN_MENU_CODE)
			),
			new SwitchingDate(0)
		);
	
	@Test
	public void testTopPagePersonSettingIsNull() {
		Optional<TopPageRoleSetting> topPageRoleSetting = Optional.of(new TopPageRoleSetting(
				COMPANY_ID, 
				new RoleSetCode(ROLE_SET_CODE),
				new LoginMenuCode(LOGIN_MENU_CODE), 
				new TopMenuCode(TOP_MENU_CODE), 
				EnumAdaptor.valueOf(0, MenuClassification.class), 
				EnumAdaptor.valueOf(0, System.class),
				new SwitchingDate(0)));
		
		new Expectations() {
			{
				topPagePersonSettingRepo.getByCompanyIdAndEmployeeId(COMPANY_ID, EMPLOYEE_ID);
				result = Optional.empty();
				
				adapter.getLoginRoleSet().getRoleSetCd();
				result = ROLE_SET_CODE;
				
				topPageRoleSettingRepo.getByCompanyIdAndRoleSetCode(COMPANY_ID, ROLE_SET_CODE);
				result = topPageRoleSetting;
			}
		};
		//cut
		Optional<TopPageSettings> domain = this.domainService.getTopPageSettings(COMPANY_ID, EMPLOYEE_ID);
		assertThat(domain).isNotEmpty();
	}
	
	@Test
	public void testTopPageRoleSettingIsNull() {
		Optional<TopPagePersonSetting> topPagePersonSetting = Optional.of(new TopPagePersonSetting(
				EMPLOYEE_ID, 
				new LoginMenuCode(LOGIN_MENU_CODE), 
				new TopMenuCode(TOP_MENU_CODE), 
				EnumAdaptor.valueOf(0, MenuClassification.class), 
				EnumAdaptor.valueOf(0, System.class),
				new SwitchingDate(0)));
		new Expectations() {
			{
				topPagePersonSettingRepo.getByCompanyIdAndEmployeeId(COMPANY_ID, EMPLOYEE_ID);
				result = topPagePersonSetting;
			}
		};

		Optional<TopPageSettings> domain = this.domainService.getTopPageSettings(COMPANY_ID, EMPLOYEE_ID);
		assertThat(domain).isNotEmpty();
		
	}
	
	@Test
	public void testReturnNull() {
		new Expectations() {
			{
				topPagePersonSettingRepo.getByCompanyIdAndEmployeeId(COMPANY_ID, EMPLOYEE_ID);
				result = Optional.empty();
				
				adapter.getLoginRoleSet().getRoleSetCd();
				result = ROLE_SET_CODE;
				
				topPageRoleSettingRepo.getByCompanyIdAndRoleSetCode(COMPANY_ID, ROLE_SET_CODE);
				result = Optional.empty();
			}
		};

		Optional<TopPageSettings> domain = this.domainService.getTopPageSettings(COMPANY_ID, EMPLOYEE_ID);
		assertThat(domain).isNotEmpty();
		
	}
	
}
