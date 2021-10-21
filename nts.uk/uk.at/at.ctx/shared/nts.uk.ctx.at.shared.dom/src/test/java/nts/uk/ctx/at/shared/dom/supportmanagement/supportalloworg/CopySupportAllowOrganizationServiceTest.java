package nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.CopySupportAllowOrganizationService;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

@RunWith(JMockit.class)
public class CopySupportAllowOrganizationServiceTest {
	
	@Injectable
	private CopySupportAllowOrganizationService.Require require;
	
	@Test
	public void testCopy_isOverwrite_false() {
		
		val isOverwrite = false; //上書きする
		
		val targetOrg_1 = Hepler.createWorkplaceInfo( "org_1" );//複写元
		val supportAllowOrg_2 = Hepler.createWorkplaceInfo( "org_2" );
		val supportAllowOrg_3 = Hepler.createWorkplaceInfo( "org_3" );
		val copySources = Arrays.asList(
				SupportAllowOrganization.create( targetOrg_1, supportAllowOrg_2 )
			,	SupportAllowOrganization.create( targetOrg_1, supportAllowOrg_3 )
				);
		
		val destinationOrg = TargetOrgIdenInfor.createFromTargetUnit( TargetOrganizationUnit.WORKPLACE , "org_1" );
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( (TargetOrgIdenInfor) any );
				result = true;
			}
		};
		
		//Act
		val result = CopySupportAllowOrganizationService.copy( require, copySources , destinationOrg, isOverwrite );
		
		//Assert
		assertThat( result ).isEmpty();
		
	}
	
	@Test
	public void testCopy_copySoures_and_destinationTargetOrg_wokplace_duplicate() {
		
		val isOverwrite = true;//上書きする
		val targetOrg_1 = Hepler.createWorkplaceInfo( "org_1" );//複写元
		val supportAllowOrg_2 = Hepler.createWorkplaceInfo( "org_2" );
		val supportAllowOrg_3 = Hepler.createWorkplaceInfo( "org_3" );
		val supportAllowOrg_4 = Hepler.createWorkplaceInfo(  "org_4" );
		val copySources = Arrays.asList(
				SupportAllowOrganization.create( targetOrg_1, supportAllowOrg_2 )
			,	SupportAllowOrganization.create( targetOrg_1, supportAllowOrg_3 )
			,	SupportAllowOrganization.create( targetOrg_1, supportAllowOrg_4 )
				);
		
		val destinationOrg = Hepler.createWorkplaceInfo("org_1" );//複写先
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( (TargetOrgIdenInfor) any );
				result = true;
			}
		};
		
		//Act
		val result = CopySupportAllowOrganizationService.copy( require, copySources, destinationOrg, isOverwrite );
		
		//Assert
		assertThat( result ).isEmpty();
		
	}
	
	@Test
	public void testCopy_copySoures_and_destinationTargetOrg_wokplaceGroup_duplicate() {
		
		val isOverwrite = false;//上書きする
		val targetOrg_1 = Hepler.createWorkplaceGroupInfo( "groupOrg_1" );//複写元
		val supportAllowOrg_2 = Hepler.createWorkplaceGroupInfo( "groupOrg_2" );
		val supportAllowOrg_3 = Hepler.createWorkplaceGroupInfo( "groupOrg_3" );
		val supportAllowOrg_4 = Hepler.createWorkplaceGroupInfo( "groupOrg_4" );
		val copySources = Arrays.asList(
				SupportAllowOrganization.create( targetOrg_1, supportAllowOrg_2 )
			,	SupportAllowOrganization.create( targetOrg_1, supportAllowOrg_3 )
			,	SupportAllowOrganization.create( targetOrg_1, supportAllowOrg_4 )
				);
		
		val destinationOrg = Hepler.createWorkplaceGroupInfo( "groupOrg_1" );//複写先
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( (TargetOrgIdenInfor) any );
				result = true;
			}
		};
		
		//Act
		val result = CopySupportAllowOrganizationService.copy( require, copySources, destinationOrg, isOverwrite );
		
		//Assert
		assertThat( result ).isEmpty();
		
	}
	
	@Test
	public void testCopy_wokplaceGroup_successful() {
		
		val isOverwrite = true;
		val destinationOrg = Hepler.createWorkplaceGroupInfo( "groupOrg_5" );//複写先
		
		val targetOrgSource = Hepler.createWorkplaceGroupInfo("groupOrg_1" );
		val supportableOrg_2 = Hepler.createWorkplaceGroupInfo( "groupOrg_2" );
		val supportableOrg_3 = Hepler.createWorkplaceGroupInfo( "groupOrg_3" );
		val supportableOrg_4 = Hepler.createWorkplaceGroupInfo( "groupOrg_4" );
		
		val supportAllowOrg_2 = SupportAllowOrganization.create( targetOrgSource, supportableOrg_2 );
		val supportAllowOrg_3 = SupportAllowOrganization.create( targetOrgSource, supportableOrg_3 );
		val supportAllowOrg_4 = SupportAllowOrganization.create( targetOrgSource, supportableOrg_4 );
		val copySources = Arrays.asList( supportAllowOrg_2, supportAllowOrg_3, supportAllowOrg_4 );//複写元
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( destinationOrg );
				result = false;
				
				supportAllowOrg_2.copy( destinationOrg );
				times = 1;
				
				supportAllowOrg_3.copy( destinationOrg );
				times = 1;
				
				supportAllowOrg_4.copy( destinationOrg );
				times = 1;
			}
		};
		
		//Act
		NtsAssert.atomTask(
				() -> CopySupportAllowOrganizationService.copy( require, copySources, destinationOrg, isOverwrite ).get(),
				any -> require.registerSupportAllowOrganization( any.get() ) );
		
	}
	
	@Test
	public void testCopy_wokplace_successful() {
		
		val isOverwrite = true;
		val destinationOrg = Hepler.createWorkplaceInfo( "org_5" );
		
		val sourceOrg = Hepler.createWorkplaceInfo("org_1" );
		val supportableOrg_2 = Hepler.createWorkplaceInfo( "org_2" );
		val supportableOrg_3 = Hepler.createWorkplaceInfo( "org_3" );
		val supportableOrg_4 = Hepler.createWorkplaceInfo( "org_4" );
		
		val supportAllowOrg_2 = SupportAllowOrganization.create( sourceOrg, supportableOrg_2 );
		val supportAllowOrg_3 = SupportAllowOrganization.create( sourceOrg, supportableOrg_3 );
		val supportAllowOrg_4 = SupportAllowOrganization.create( sourceOrg, supportableOrg_4 );
		val copySources = Arrays.asList( supportAllowOrg_2, supportAllowOrg_3, supportAllowOrg_4 );
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( destinationOrg );
				result = false;
				
				supportAllowOrg_2.copy( destinationOrg );
				times = 1;
				
				supportAllowOrg_3.copy( destinationOrg );
				times = 1;
				
				supportAllowOrg_4.copy( destinationOrg );
				times = 1;
			}
		};
		
		//Act
		NtsAssert.atomTask(
				() -> CopySupportAllowOrganizationService.copy( require, copySources, destinationOrg, isOverwrite ).get(),
				any -> require.registerSupportAllowOrganization( any.get() ) );
	}	
	

	@Test
	public void testCopy_successfull_overwrite_true() {
		
		val isOverwrite = true;//上書きする
		val destinationOrg = Hepler.createWorkplaceGroupInfo( "groupOrg_5" );
		
		val sourceOrg = Hepler.createWorkplaceGroupInfo("groupOrg_1" );
		val supportableOrg_2 = Hepler.createWorkplaceGroupInfo( "groupOrg_2" );
		val supportableOrg_3 = Hepler.createWorkplaceGroupInfo( "groupOrg_3" );
		val supportableOrg_4 = Hepler.createWorkplaceGroupInfo( "groupOrg_4" );
		
		val supportAllowOrg_2 = SupportAllowOrganization.create( sourceOrg, supportableOrg_2 );
		val supportAllowOrg_3 = SupportAllowOrganization.create( sourceOrg, supportableOrg_3 );
		val supportAllowOrg_4 = SupportAllowOrganization.create( sourceOrg, supportableOrg_4 );
		val copySources = Arrays.asList( supportAllowOrg_2, supportAllowOrg_3, supportAllowOrg_4 );
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( destinationOrg );
				result = true;
				
				supportAllowOrg_2.copy( destinationOrg );
				times = 1;
				
				supportAllowOrg_3.copy( destinationOrg );
				times = 1;
				
				supportAllowOrg_4.copy( destinationOrg );
				times = 1;
			}
		};
		
		//Act
		NtsAssert.atomTask(
					() -> CopySupportAllowOrganizationService.copy( require, copySources, destinationOrg, isOverwrite ).get()
				,	any -> require.deleteSupportAllowOrganization( any.get() )
				,	any -> require.registerSupportAllowOrganization( any.get() ) );
	}
	
	@Test
	public void testCopy_supportableOrg_is_filtered() {
		
		val isOverwrite = false;//上書きする
		val destinationOrg = Hepler.createWorkplaceInfo( "org_2" );//複写先
		
		val sourceOrg = Hepler.createWorkplaceInfo("org_1" );
		val supportableOrg_2 = Hepler.createWorkplaceInfo( "org_2" );//複写元.応援可能組織
		val supportableOrg_3 = Hepler.createWorkplaceInfo( "org_3" );
		val supportableOrg_4 = Hepler.createWorkplaceInfo( "org_4" );
		
		val supportAllowOrg_2 = SupportAllowOrganization.create( sourceOrg, supportableOrg_2 );
		val supportAllowOrg_3 = SupportAllowOrganization.create( sourceOrg, supportableOrg_3 );
		val supportAllowOrg_4 = SupportAllowOrganization.create( sourceOrg, supportableOrg_4 );
		val copySources = Arrays.asList( supportAllowOrg_2, supportAllowOrg_3, supportAllowOrg_4 );
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( destinationOrg );
				result = false;
				
				supportAllowOrg_3.copy( destinationOrg );
				times = 1;
				
				supportAllowOrg_4.copy( destinationOrg );
				times = 1;
			}
		};
		
		//Act
		NtsAssert.atomTask(
				() -> CopySupportAllowOrganizationService.copy( require, copySources, destinationOrg, isOverwrite ).get(),
				any -> require.registerSupportAllowOrganization( any.get() ) );
	}
	
	public void testCopy_unit_different() {
		
		val isOverwrite = true;//上書きする
		//複写先組織.単位 = 職場グルーブ
		val destinationOrg = Hepler.createWorkplaceGroupInfo( "workplaceGroup_id" );
		
		//複写元.単位 = 職場
		val sourceOrg = Hepler.createWorkplaceInfo( "org_1" );
		val supportableOrg_2 = Hepler.createWorkplaceInfo( "org_2" );
		val supportableOrg_3 = Hepler.createWorkplaceInfo( "org_3" );
		
		val supportAllowOrg_2 = SupportAllowOrganization.create( sourceOrg, supportableOrg_2 );
		val supportAllowOrg_3 = SupportAllowOrganization.create( sourceOrg, supportableOrg_3 );
		val copySources = Arrays.asList( supportAllowOrg_2, supportAllowOrg_3 );	
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( destinationOrg );
				result = true;
			}
		};
		
		NtsAssert.businessException( "Msg_2299", () ->{
			CopySupportAllowOrganizationService.copy(require, copySources, destinationOrg, isOverwrite);
		});
	}
	
	private static class Hepler{
		
		/**
		 * 職場IDから対象組織識別情報を作る
		 * @param workplaceId 職場ID
		 * @return
		 */
		public static TargetOrgIdenInfor createWorkplaceInfo(String workplaceId) {
			
			return TargetOrgIdenInfor.creatIdentifiWorkplace( workplaceId );
			
		}
		
		/**
		 * 職場グルーブIDから対象組織識別情報を作る
		 * @param workplaceGroupId 職場グルーブID
		 * @return
		 */
		public static TargetOrgIdenInfor createWorkplaceGroupInfo(String workplaceGroupId) {

			return TargetOrgIdenInfor.creatIdentifiWorkplaceGroup( workplaceGroupId );
			
		}
		
	}

}
