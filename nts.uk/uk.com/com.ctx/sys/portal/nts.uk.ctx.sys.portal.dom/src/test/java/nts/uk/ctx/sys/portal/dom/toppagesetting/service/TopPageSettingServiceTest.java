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
			new TopMenuCode("topMenuCode"),
			new MenuLogin(
				EnumAdaptor.valueOf(0, System.class),
				EnumAdaptor.valueOf(0, MenuClassification.class), 
				new LoginMenuCode("loginMenuCode")
			),
			new SwitchingDate(0)
		);
	
	@Test
	public void testTopPagePersonSettingIsNull() {
		String companyId = "companyId";
		String employeeId = "employeeId";
		String roleSetcode = "roleSetCode";
		Optional<TopPageRoleSetting> topPageRoleSetting = Optional.of(new TopPageRoleSetting(
				companyId, 
				new RoleSetCode(roleSetcode),
				new LoginMenuCode("loginMenuCode"), 
				new TopMenuCode("topMenuCode"), 
				EnumAdaptor.valueOf(0, MenuClassification.class), 
				EnumAdaptor.valueOf(0, System.class),
				new SwitchingDate(0)));
		
		new Expectations() {
			{
				topPagePersonSettingRepo.getByCompanyIdAndEmployeeId(companyId, employeeId);
				result = Optional.empty();
				
				adapter.getLoginRoleSet().getRoleSetCd();
				result = roleSetcode;
				
				topPageRoleSettingRepo.getByCompanyIdAndRoleSetCode(companyId, roleSetcode);
				result = topPageRoleSetting;

			}
		};
		//cut
		Optional<TopPageSettings> domain = domainService.getTopPageSettings(companyId, employeeId);
		assertThat(domain).isNotEmpty();
	}
	
	@Test
	public void testTopPageRoleSettingIsNull() {
		String companyId = "companyId";
		String employeeId = "employeeId";
		Optional<TopPagePersonSetting> topPagePersonSetting = Optional.of(new TopPagePersonSetting(
				"employeeId", 
				new LoginMenuCode("loginMenuCode"), 
				new TopMenuCode("topMenuCode"), 
				EnumAdaptor.valueOf(0, MenuClassification.class), 
				EnumAdaptor.valueOf(0, System.class),
				new SwitchingDate(0)));
		new Expectations() {
			{
				topPagePersonSettingRepo.getByCompanyIdAndEmployeeId(companyId, employeeId);
				result = topPagePersonSetting;
				
			}
		};

		Optional<TopPageSettings> domain = domainService.getTopPageSettings(companyId, employeeId);
		assertThat(domain).isNotEmpty();
		
	}
	
	@Test
	public void testReturnNull() {
		String companyId = "companyId";
		String employeeId = "employeeId";
		new Expectations() {
			{
				topPagePersonSettingRepo.getByCompanyIdAndEmployeeId(companyId, employeeId);
				result = Optional.empty();
				
				adapter.getLoginRoleSet().getRoleSetCd();
				result = "roleSetcode";
				
				topPageRoleSettingRepo.getByCompanyIdAndRoleSetCode(companyId, "roleSetcode");
				result = Optional.empty();

			}
		};

		Optional<TopPageSettings> domain = domainService.getTopPageSettings(companyId, employeeId);
		assertThat(domain).isNotEmpty();
		
	}
	
}
