package nts.uk.ctx.at.shared.dom.scherec.supportmanagement;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

@RunWith(JMockit.class)
public class CopySupportAllowOrganizationServiceTest {
	
	@Injectable
	private CopySupportAllowOrganizationService.Require require;
	
	/**
	 * target: copy
	 * pattern:	複写先組織が登録された(重複)
	 * 			上書きするか = false (上書きしない)
	 */
	@Test
	public void testCopy_isOverwrite_false() {
		
		val isOverwrite = true;//上書きする
		
		val destinationOrg = TargetOrgIdenInfor.createFromTargetUnit( TargetOrganizationUnit.WORKPLACE , "workplace_id" );
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( (TargetOrgIdenInfor) any );
				result = true;
			}
		};
		
		//Act
		val result = CopySupportAllowOrganizationService.copy( require, Collections.emptyList() , destinationOrg, isOverwrite );
		
		//Assert
		assertThat( result ).isEmpty();
		
	}
	
	/**
	 * target: copy
	 * pattern:	複写先組織が未登録
	 * 			対象組織情報  == 複写先組織	
	 * 			単位 = 職場
	 * excepted: empty
	 */
	@Test
	public void testCopy_copySoures_and_destinationTargetOrg_wokplace_duplicate() {
		
		val isOverwrite = true;//上書きする
		val targetOrg_1 = Hepler.createWorkplaceInfo( "workplace_id_1" );
		val supportAllowOrg_1 = Hepler.createWorkplaceInfo( "supportAllowOrg_1" );
		val supportAllowOrg_2 = Hepler.createWorkplaceInfo( "supportAllowOrg_2" );
		val supportAllowOrg_3 = Hepler.createWorkplaceInfo(  "supportAllowOrg_3" );
		val copySources = Arrays.asList(
				SupportAllowOrganization.create( targetOrg_1, supportAllowOrg_1 )
			,	SupportAllowOrganization.create( targetOrg_1, supportAllowOrg_2 )
			,	SupportAllowOrganization.create( targetOrg_1, supportAllowOrg_3 )
				);
		
		val destinationOrg = Hepler.createWorkplaceInfo("workplace_id_1" );
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( (TargetOrgIdenInfor) any );
				result = false;
			}
		};
		
		//Act
		val result = CopySupportAllowOrganizationService.copy( require, copySources, destinationOrg, isOverwrite );
		
		//Assert
		assertThat( result ).isEmpty();
		
	}
	
	/**
	 * target: copy
	 * pattern:	複写先組織が未登録
	 * 			対象組織情報  == 複写先組織	
	 * 			単位 = 職場グルーブ
	 * excepted: empty
	 */
	@Test
	public void testCopy_copySoures_and_destinationTargetOrg_wokplaceGroup_duplicate() {
		
		val isOverwrite = true;//上書きする
		val targetOrg_1 = Hepler.createWorkplaceGroupInfo("workplaceGroup_id_1" );
		val supportAllowOrg_1 = Hepler.createWorkplaceGroupInfo( "supportAllowOrg_1" );
		val supportAllowOrg_2 = Hepler.createWorkplaceGroupInfo( "supportAllowOrg_2" );
		val supportAllowOrg_3 = Hepler.createWorkplaceGroupInfo( "supportAllowOrg_3" );
		val copySources = Arrays.asList(
				SupportAllowOrganization.create( targetOrg_1, supportAllowOrg_1 )
			,	SupportAllowOrganization.create( targetOrg_1, supportAllowOrg_2 )
			,	SupportAllowOrganization.create( targetOrg_1, supportAllowOrg_3 )
				);
		
		val destinationOrg = Hepler.createWorkplaceGroupInfo( "workplaceGroup_id_1" );
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( (TargetOrgIdenInfor) any );
				result = false;
			}
		};
		
		//Act
		val result = CopySupportAllowOrganizationService.copy( require, copySources, destinationOrg, isOverwrite );
		
		//Assert
		assertThat( result ).isEmpty();
		
	}
	
	/**
	 * target: copy
	 * pattern:	複写先組織が未登録
	 * 			単位 = 職場グルーブ
	 * excepted: success
	 */
	@Test
	public void testCopy_wokplaceGroup_destination_unregister() {
		
		val isOverwrite = true;
		val destinationOrg = Hepler.createWorkplaceGroupInfo( "workplaceGroup_id_1" );
		
		val targetOrg_1 = Hepler.createWorkplaceGroupInfo("workplaceGroup_id_1" );
		val targetOrg_2 = Hepler.createWorkplaceGroupInfo("workplaceGroup_id_2" );
		val targetOrg_3 = Hepler.createWorkplaceGroupInfo("workplaceGroup_id_3" );
		val supportableOrg_1 = Hepler.createWorkplaceGroupInfo( "supportAllowOrg_1" );
		val supportableOrg_2 = Hepler.createWorkplaceGroupInfo( "supportAllowOrg_2" );
		val supportableOrg_3 = Hepler.createWorkplaceGroupInfo( "supportAllowOrg_3" );
		
		val supportAllowOrg_1 = SupportAllowOrganization.create( targetOrg_1, supportableOrg_1 );
		val supportAllowOrg_2 = SupportAllowOrganization.create( targetOrg_2, supportableOrg_2 );
		val supportAllowOrg_3 = SupportAllowOrganization.create( targetOrg_3, supportableOrg_3 );
		val copySources = Arrays.asList( supportAllowOrg_1, supportAllowOrg_2, supportAllowOrg_3 );
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( destinationOrg );
				result = false;
				
				supportAllowOrg_2.copy( destinationOrg );
				times = 1;
				
				supportAllowOrg_3.copy( destinationOrg );
				times = 1;
			}
		};
		
		val destinationSupportAllowOrg_2 = supportAllowOrg_2.copy( destinationOrg );
		val destinationSupportAllowOrg_3 = supportAllowOrg_3.copy( destinationOrg );
		val copyResults = Arrays.asList( destinationSupportAllowOrg_2, destinationSupportAllowOrg_3 );
		
		//Act
		NtsAssert.atomTask(
				() -> CopySupportAllowOrganizationService.copy( require, copySources, destinationOrg, isOverwrite ).get(),
				any -> require.registerSupportAllowOrganization( copyResults ) );
	}
	
	/**
	 * target: copy
	 * pattern:	複写先組織が未登録
	 * 			単位 = 職場
	 * excepted: success
	 */
	@Test
	public void testCopy_wokplace_destination_unregister() {
		
		val isOverwrite = true;
		val destinationOrg = Hepler.createWorkplaceInfo( "workplace_id_1" );
		
		val targetOrg_1 = Hepler.createWorkplaceInfo("workplace_id_1" );
		val targetOrg_2 = Hepler.createWorkplaceInfo("workplace_id_2" );
		val targetOrg_3 = Hepler.createWorkplaceInfo("workplace_id_3" );
		val supportableOrg_1 = Hepler.createWorkplaceInfo( "supportAllowOrg_1" );
		val supportableOrg_2 = Hepler.createWorkplaceInfo( "supportAllowOrg_2" );
		val supportableOrg_3 = Hepler.createWorkplaceInfo( "supportAllowOrg_3" );
		
		val supportAllowOrg_1 = SupportAllowOrganization.create( targetOrg_1, supportableOrg_1 );
		val supportAllowOrg_2 = SupportAllowOrganization.create( targetOrg_2, supportableOrg_2 );
		val supportAllowOrg_3 = SupportAllowOrganization.create( targetOrg_3, supportableOrg_3 );
		val copySources = Arrays.asList( supportAllowOrg_1, supportAllowOrg_2, supportAllowOrg_3 );
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( destinationOrg );
				result = false;
				
				supportAllowOrg_2.copy( destinationOrg );
				times = 1;
				
				supportAllowOrg_3.copy( destinationOrg );
				times = 1;
			}
		};
		
		val destinationSupportAllowOrg_2 = supportAllowOrg_2.copy( destinationOrg );
		val destinationSupportAllowOrg_3 = supportAllowOrg_3.copy( destinationOrg );
		val copyResults = Arrays.asList( destinationSupportAllowOrg_2, destinationSupportAllowOrg_3 );
		
		//Act
		NtsAssert.atomTask(
				() -> CopySupportAllowOrganizationService.copy( require, copySources, destinationOrg, isOverwrite ).get(),
				any -> require.registerSupportAllowOrganization( copyResults ) );
	}	
	

	/**
	 * target: copy
	 * pattern:	複写先組織が未登録
	 * 			単位 = 職場グルーブ
	 * 			上書きする か= true
	 * excepted: success
	 */
	@Test
	public void testCopy_wokplaceGroup_isOverwrite_true_destination_registered() {
		
		val isOverwrite = true;//上書きする
		val destinationOrg = Hepler.createWorkplaceGroupInfo( "workplaceGroup_id_1" );
		
		val targetOrg_1 = Hepler.createWorkplaceGroupInfo("workplaceGroup_id_1" );
		val targetOrg_2 = Hepler.createWorkplaceGroupInfo("workplaceGroup_id_2" );
		val targetOrg_3 = Hepler.createWorkplaceGroupInfo("workplaceGroup_id_3" );
		val supportableOrg_1 = Hepler.createWorkplaceGroupInfo( "supportAllowOrg_1" );
		val supportableOrg_2 = Hepler.createWorkplaceGroupInfo( "supportAllowOrg_2" );
		val supportableOrg_3 = Hepler.createWorkplaceGroupInfo( "supportAllowOrg_3" );
		
		val supportAllowOrg_1 = SupportAllowOrganization.create( targetOrg_1, supportableOrg_1 );
		val supportAllowOrg_2 = SupportAllowOrganization.create( targetOrg_2, supportableOrg_2 );
		val supportAllowOrg_3 = SupportAllowOrganization.create( targetOrg_3, supportableOrg_3 );
		val copySources = Arrays.asList( supportAllowOrg_1, supportAllowOrg_2, supportAllowOrg_3 );
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( destinationOrg );
				result = true;
				
				supportAllowOrg_2.copy( destinationOrg );
				times = 1;
				
				supportAllowOrg_3.copy( destinationOrg );
				times = 1;
			}
		};
		
		val destinationSupportAllowOrg_2 = supportAllowOrg_2.copy( destinationOrg );
		val destinationSupportAllowOrg_3 = supportAllowOrg_3.copy( destinationOrg );
		val copyResults = Arrays.asList( destinationSupportAllowOrg_2, destinationSupportAllowOrg_3 );
		
		//Act
		NtsAssert.atomTask(
					() -> CopySupportAllowOrganizationService.copy( require, copySources, destinationOrg, isOverwrite ).get()
				,	any -> require.deleteSupportAllowOrganization( targetOrg_1 )
				,	any -> require.registerSupportAllowOrganization( copyResults ) );
	}
	
	/**
	 * target: copy
	 * pattern:	複写先組織が未登録
	 * 			単位 = 職場
	 * 			上書きする か= true
	 * excepted: success
	 */
	@Test
	public void testCopy_wokplace_isOverwrite_true_destination_registered() {
		
		val isOverwrite = true;//上書きする
		val destinationOrg = Hepler.createWorkplaceInfo( "workplace_id_1" );
		
		val targetOrg_1 = Hepler.createWorkplaceInfo("workplace_id_1" );
		val targetOrg_2 = Hepler.createWorkplaceInfo("workplace_id_2" );
		val targetOrg_3 = Hepler.createWorkplaceInfo("workplace_id_3" );
		val supportableOrg_1 = Hepler.createWorkplaceInfo( "supportAllowOrg_1" );
		val supportableOrg_2 = Hepler.createWorkplaceInfo( "supportAllowOrg_2" );
		val supportableOrg_3 = Hepler.createWorkplaceInfo( "supportAllowOrg_3" );
		
		val supportAllowOrg_1 = SupportAllowOrganization.create( targetOrg_1, supportableOrg_1 );
		val supportAllowOrg_2 = SupportAllowOrganization.create( targetOrg_2, supportableOrg_2 );
		val supportAllowOrg_3 = SupportAllowOrganization.create( targetOrg_3, supportableOrg_3 );
		val copySources = Arrays.asList( supportAllowOrg_1, supportAllowOrg_2, supportAllowOrg_3 );
		
		new Expectations() {
			{
				require.existsSupportAllowOrganization( destinationOrg );
				result = true;
				
				supportAllowOrg_2.copy( destinationOrg );
				times = 1;
				
				supportAllowOrg_3.copy( destinationOrg );
				times = 1;
			}
		};
		
		val destinationSupportAllowOrg_2 = supportAllowOrg_2.copy( destinationOrg );
		val destinationSupportAllowOrg_3 = supportAllowOrg_3.copy( destinationOrg );
		val copyResults = Arrays.asList( destinationSupportAllowOrg_2, destinationSupportAllowOrg_3 );
		
		//Act
		NtsAssert.atomTask(
				() -> CopySupportAllowOrganizationService.copy( require, copySources, destinationOrg, isOverwrite ).get(),
				any -> require.registerSupportAllowOrganization( copyResults ) );
	}
	
	/**
	 * target: copy
	 * pattern:	
	 * 			複写先組織, 複写元の応援可能組織 .単位 が違う
	 * excepted: Msg_2299
	 */
	public void testCopy_unit_different() {
		
		val isOverwrite = true;//上書きする
		val destinationOrg = Hepler.createWorkplaceGroupInfo( "workplaceGroupId_1" );//複写先組織.単位 = 職場グルーブ
		
		val targetOrg_1 = Hepler.createWorkplaceInfo("workplace_id_1" );
		val targetOrg_2 = Hepler.createWorkplaceInfo("workplace_id_2" );
		val targetOrg_3 = Hepler.createWorkplaceInfo("workplace_id_3" );
		val supportableOrg_1 = Hepler.createWorkplaceInfo( "supportAllowOrg_1" );//複写元.単位 = 職場
		val supportableOrg_2 = Hepler.createWorkplaceGroupInfo( "supportAllowOrg_2" );//複写元.単位 = 職場グルーブ
		val supportableOrg_3 = Hepler.createWorkplaceGroupInfo( "supportAllowOrg_3" );//複写元.単位 = 職場グルーブ
		
		val supportAllowOrg_1 = SupportAllowOrganization.create( targetOrg_1, supportableOrg_1 );
		val supportAllowOrg_2 = SupportAllowOrganization.create( targetOrg_2, supportableOrg_2 );
		val supportAllowOrg_3 = SupportAllowOrganization.create( targetOrg_3, supportableOrg_3 );
		val copySources = Arrays.asList( supportAllowOrg_1, supportAllowOrg_2, supportAllowOrg_3 );	
		
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
