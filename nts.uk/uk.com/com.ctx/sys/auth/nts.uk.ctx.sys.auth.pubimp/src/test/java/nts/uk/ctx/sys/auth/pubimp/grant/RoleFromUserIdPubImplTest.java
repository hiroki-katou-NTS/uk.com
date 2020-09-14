package nts.uk.ctx.sys.auth.pubimp.grant;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import mockit.internal.util.FieldReflection;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.roleset.ApprovalAuthority;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.service.RoleSetService;
import nts.uk.ctx.sys.auth.pub.grant.RoleFromUserIdPub.RoleInfoExport;


@RunWith(JMockit.class)
public class RoleFromUserIdPubImplTest {

//	@Mocked
//	private RoleIndividualGrantRepository roleIndRepo;
//	
//	@Mocked
//	private RoleIndividualGrant roleIndividualGrant;
//	
//	@Mocked
//	private RoleSetService roleSetService;
//	
//	@Mocked
//	private RoleSet roleSet;
//	
//	@Mocked
//	private RoleType roleType;
	
	@Test
	public void testIsInCharge(
			@Mocked RoleIndividualGrantRepository roleIndRepo,
			@Mocked RoleIndividualGrant roleIndividualGrant) {
		RoleFromUserIdPubImpl target = new RoleFromUserIdPubImpl();
		FieldReflection.setField(RoleFromUserIdPubImpl.class, target, "roleIndRepo", roleIndRepo);
//		FieldReflection.setField(RoleFromUserIdPubImpl.class, target, "roleSetService", roleSetService);

		new Expectations() {{
			roleIndRepo.findByUserCompanyRoleTypeDate("userId", "companyId", 3, GeneralDate.today());
			result = Optional.of(roleIndividualGrant);
			
			roleIndividualGrant.getRoleId();
			result = "ロールID";
		}};
		
		Optional<RoleInfoExport> roleInfoIsInCharge = target.getRoleInfoFromUserId("userId", 3, GeneralDate.today(), "companyId");
		assertThat(roleInfoIsInCharge.get().isInCharge()).isEqualTo(true);
		assertThat(roleInfoIsInCharge.get().getRoleId()).isEqualTo("ロールID");
	}
	
	@Test
	public void testIsGeneral(
			@Mocked RoleIndividualGrantRepository roleIndRepo,
			@Mocked RoleSetService roleSetService) {
		RoleFromUserIdPubImpl target = new RoleFromUserIdPubImpl();
		FieldReflection.setField(RoleFromUserIdPubImpl.class, target, "roleIndRepo", roleIndRepo);
		FieldReflection.setField(RoleFromUserIdPubImpl.class, target, "roleSetService", roleSetService);
		
		RoleSet roleSet = new RoleSet("", "", "", ApprovalAuthority.HasRight, "", "", "", "", "", "");
		new Expectations() {{
			roleIndRepo.findByUserCompanyRoleTypeDate("userId", "companyId", 3, GeneralDate.today());
			//result = Optional.empty();
			
			roleSetService.getRoleSetFromUserId("userId", GeneralDate.today(), "companyId");
			result = Optional.of(roleSet);
			
			roleSet.getRoleIDByRoleType(RoleType.EMPLOYMENT);
			result = "ロールID";
		}};
		
		Optional<RoleInfoExport> roleInfoIsGeneral = target.getRoleInfoFromUserId("userId", 3, GeneralDate.today(), "companyId");
		assertThat(roleInfoIsGeneral.get().isInCharge()).isEqualTo(false);
		assertThat(roleInfoIsGeneral.get().getRoleId()).isEqualTo("ロールID");
	}
	
	@Test
	public void testIsNull(
			@Mocked RoleIndividualGrantRepository roleIndRepo,
			@Mocked RoleSetService roleSetService) {
		RoleFromUserIdPubImpl target = new RoleFromUserIdPubImpl();
		FieldReflection.setField(RoleFromUserIdPubImpl.class, target, "roleIndRepo", roleIndRepo);
		FieldReflection.setField(RoleFromUserIdPubImpl.class, target, "roleSetService", roleSetService);


		new Expectations() {{
			roleIndRepo.findByUserCompanyRoleTypeDate("userId", "companyId", 3, GeneralDate.today());
			//result = Optional.empty();
			
			roleSetService.getRoleSetFromUserId("userId", GeneralDate.today(), "comId");
			//result = Optional.empty();
		}};
		
		Optional<RoleInfoExport> roleInfoIsNull = target.getRoleInfoFromUserId("userId", 3, GeneralDate.today(), "companyId");
		assertThat(roleInfoIsNull).isEmpty();
	}
}
