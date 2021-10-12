package nts.uk.ctx.at.shared.dom.scherec.supportmanagement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

@RunWith(JMockit.class)
public class SupportAllowOrganizationTest {
	
	@Test
	public void testGetter() {
		
		val targetOrg = Helper.createTargetOrgIdenInfor( TargetOrganizationUnit.WORKPLACE );//対象組織情報の単位 = 職場
		val supportableOrganization = Helper.createTargetOrgIdenInfor( TargetOrganizationUnit.WORKPLACE );//応援可能組織 = 職場グルーブ
		val supportAllowOrg = SupportAllowOrganization.create( targetOrg, supportableOrganization );
		
		//Act
		NtsAssert.invokeGetters(supportAllowOrg);
	
	}
	/**
	 * target:  create
	 * pattern: @対象組織情報.単位 != @応援可能組織.単位
	 * excepted: Msg_2299
	 */
	@Test
	public void testCreate_inv_1() {
		
		//対象組織情報の単位 = 職場, 応援可能組織 = 職場グルーブ
		{
			val targetOrg = Helper.createTargetOrgIdenInfor( TargetOrganizationUnit.WORKPLACE );
			val supportableOrganization = Helper.createTargetOrgIdenInfor( TargetOrganizationUnit.WORKPLACE_GROUP );
			
			//Act
			NtsAssert.businessException( "Msg_2299", () ->{
				
				SupportAllowOrganization.create( targetOrg, supportableOrganization );
				
			});
			
		}
		
		//対象組織情報の単位 = 職場グルーブ, 応援可能組織 = 職場
		{
			val targetOrg = Helper.createTargetOrgIdenInfor( TargetOrganizationUnit.WORKPLACE_GROUP );
			val supportableOrganization = Helper.createTargetOrgIdenInfor( TargetOrganizationUnit.WORKPLACE );
			
			//Act
			NtsAssert.businessException( "Msg_2299", () ->{
				
				SupportAllowOrganization.create( targetOrg, supportableOrganization );
				
			});
			
		}
	}	

	/**
	 * target:  create
	 * pattern: 職場ID同じ
	 * excepted: Msg_2146
	 */
	@Test
	public void testCreate_inv_2_same_workplace() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplace_ID" );
		val supportableOrganization = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplace_ID" );
		
		//Act
		NtsAssert.businessException( "Msg_2146", () ->{
			
			SupportAllowOrganization.create( targetOrg, supportableOrganization );
			
		});
		
	}
	
	/**
	 * target:  create
	 * pattern: 職場グルーブID同じ
	 * excepted: Msg_2146
	 */
	@Test
	public void testCreate_inv_2_same_workplaceGroup() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup( "workplaceGroup_ID" );
		val supportableOrganization = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup( "workplaceGroup_ID" );
		
		//Act
		NtsAssert.businessException( "Msg_2146", () ->{
			
			SupportAllowOrganization.create( targetOrg, supportableOrganization );
			
		});
		
	}
	

	
	/**
	 * target:  create
	 * pattern: エラーがない、単位　＝　職場
	 * excepted: success
	 */
	@Test
	public void testCreate_success_case_workplace() {
		val targetOrg = TargetOrgIdenInfor.createFromTargetUnit( TargetOrganizationUnit.WORKPLACE, "workplace_1" );
		val supportableOrganization = TargetOrgIdenInfor.createFromTargetUnit( TargetOrganizationUnit.WORKPLACE, "workplace_2" );
		
		//Act
		val result = SupportAllowOrganization.create(targetOrg, supportableOrganization);
		
		//Assert
		assertThat( result.getTargetOrg().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE );
		assertThat( result.getTargetOrg().getWorkplaceId().get() ).isEqualTo( "workplace_1" );
		assertThat( result.getTargetOrg().getWorkplaceGroupId() ).isEmpty();
		
		assertThat( result.getSupportableOrganization().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE );
		assertThat( result.getSupportableOrganization().getWorkplaceId().get() ).isEqualTo( "workplace_2" );
		assertThat( result.getSupportableOrganization().getWorkplaceGroupId() ).isEmpty();
		
	}
	
	/**
	 * target:  create
	 * pattern: エラーがない, 単位　＝　職場職グルーブ
	 * excepted: success
	 */
	@Test
	public void testCreate_success_case_workplaceGroup() {
		val targetOrg = TargetOrgIdenInfor.createFromTargetUnit( TargetOrganizationUnit.WORKPLACE_GROUP, "workplaceGroup_1" );
		val supportableOrganization = TargetOrgIdenInfor.createFromTargetUnit( TargetOrganizationUnit.WORKPLACE_GROUP, "workplaceGroup_2" );
		
		//Act
		val result = SupportAllowOrganization.create(targetOrg, supportableOrganization);
		
		//Assert
		assertThat( result.getTargetOrg().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE_GROUP );
		assertThat( result.getTargetOrg().getWorkplaceGroupId().get() ).isEqualTo( "workplaceGroup_1" );
		assertThat( result.getTargetOrg().getWorkplaceId() ).isEmpty();
		
		assertThat( result.getSupportableOrganization().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE_GROUP );
		assertThat( result.getSupportableOrganization().getWorkplaceGroupId().get() ).isEqualTo( "workplaceGroup_2" );
		assertThat( result.getSupportableOrganization().getWorkplaceId() ).isEmpty();
		
	}
	
	/**
	 * target: copy
	 * pattern: 職場
	 */
	@Test
	public void testCopy_workplace_success() {
		
		val targetOrgSource = TargetOrgIdenInfor.createFromTargetUnit( TargetOrganizationUnit.WORKPLACE, "workplace_source_1" );//DUMMY
		val supportableOrg = TargetOrgIdenInfor.createFromTargetUnit( TargetOrganizationUnit.WORKPLACE, "workplace_source_2" );
		val copySource = SupportAllowOrganization.create( targetOrgSource, supportableOrg );
		
		val destinationTargetOrg = TargetOrgIdenInfor.createFromTargetUnit( TargetOrganizationUnit.WORKPLACE, "workplace_destination" );
		
		//Act
		val result = copySource.copy( destinationTargetOrg );
		
		//Assert
		assertThat( result.getTargetOrg().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE );
		assertThat( result.getTargetOrg().getWorkplaceId().get() ).isEqualTo( "workplace_destination" );
		assertThat( result.getTargetOrg().getWorkplaceGroupId() ).isEmpty();
		
		assertThat( result.getSupportableOrganization().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE );
		assertThat( result.getSupportableOrganization().getWorkplaceId().get() ).isEqualTo( "workplace_source_2" );
		assertThat( result.getSupportableOrganization().getWorkplaceGroupId() ).isEmpty();
		
	}
	
	/**
	 * target: copy
	 * pattern: 職場グルーブ
	 */
	@Test
	public void testCopy_workplaceGroup_success() {
		
		val targetOrgSource = TargetOrgIdenInfor.createFromTargetUnit(
					TargetOrganizationUnit.WORKPLACE_GROUP//DUMMY
				,	"workplaceGroup_source_1" );//DUMMY
		val supportableOrg = TargetOrgIdenInfor.createFromTargetUnit(
					TargetOrganizationUnit.WORKPLACE_GROUP
				,	"workplaceGroup_supportableOrg" );
		val copySource = SupportAllowOrganization.create( targetOrgSource, supportableOrg );
		
		val destinationTargetOrg = TargetOrgIdenInfor.createFromTargetUnit(
					TargetOrganizationUnit.WORKPLACE_GROUP
				,	"workplaceGroup_source_1_destination" );
		
		//Act
		val result = copySource.copy( destinationTargetOrg );
		
		//Assert
		assertThat( result.getTargetOrg().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE_GROUP );
		assertThat( result.getTargetOrg().getWorkplaceGroupId().get() ).isEqualTo( "workplaceGroup_source_1_destination" );
		assertThat( result.getTargetOrg().getWorkplaceId() ).isEmpty();
		
		assertThat( result.getSupportableOrganization().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE_GROUP );
		assertThat( result.getSupportableOrganization().getWorkplaceGroupId().get() ).isEqualTo( "workplaceGroup_supportableOrg" );
		
	}
	
	/**
	 * target: copy
	 * pattern: 単位が違う
	 * excepted: Msg_2299
	 */
	@Test
	public void testCopy_unit_different() {
		
		val targetOrgSource = TargetOrgIdenInfor.createFromTargetUnit(
					TargetOrganizationUnit.WORKPLACE_GROUP//DUMMY
				,	"workplaceGroup_source_1" );//DUMMY
		val supportableOrg = TargetOrgIdenInfor.createFromTargetUnit(
					TargetOrganizationUnit.WORKPLACE_GROUP//単位= 職場グルーブ
				,	"workplaceGroup_supportableOrg" );
		val copySource = SupportAllowOrganization.create( targetOrgSource, supportableOrg );
		
		val destinationTargetOrg = TargetOrgIdenInfor.createFromTargetUnit(
					TargetOrganizationUnit.WORKPLACE//単位= 職場
				,	"workplaceGroup_source_1_destination" );
		
		//Act
		NtsAssert.businessException( "Msg_2299", () ->{
			
			copySource.copy( destinationTargetOrg );
			
		});
	}
	
	/**
	 * target: copy
	 * pattern: 単位が同じ、職場IDも同じ
	 * excepted: Msg_2146
	 */
	@Test
	public void testCopy_same_unit_same_workplace_id() {
		
		val targetOrgSource = TargetOrgIdenInfor.createFromTargetUnit(
					TargetOrganizationUnit.WORKPLACE//DUMMY
				,	"workplaceGroup_source_1" );//DUMMY
		val supportableOrg = TargetOrgIdenInfor.createFromTargetUnit(
					TargetOrganizationUnit.WORKPLACE
				,	"workplace_Id" );
		val copySource = SupportAllowOrganization.create( targetOrgSource, supportableOrg );
		
		val destinationTargetOrg = TargetOrgIdenInfor.createFromTargetUnit(
					TargetOrganizationUnit.WORKPLACE//単位= 職場
				,	"workplace_Id" );
		
		//Act
		NtsAssert.businessException( "Msg_2146", () ->{
			
			copySource.copy( destinationTargetOrg );
			
		});
	}
	
	/**
	 * target: copy
	 * pattern: 単位が同じ、職場グルーブIDも同じ
	 * excepted: Msg_2146
	 */
	@Test
	public void testCopy_same_unit_same_workplaceGroup_id() {
		
		val targetOrgSource = TargetOrgIdenInfor.createFromTargetUnit(
					TargetOrganizationUnit.WORKPLACE_GROUP//DUMMY
				,	"workplaceGroup_source_1" );//DUMMY
		val supportableOrg = TargetOrgIdenInfor.createFromTargetUnit(
					TargetOrganizationUnit.WORKPLACE_GROUP
				,	"workplaceGroup_Id" );
		val copySource = SupportAllowOrganization.create( targetOrgSource, supportableOrg );
		
		val destinationTargetOrg = TargetOrgIdenInfor.createFromTargetUnit(
					TargetOrganizationUnit.WORKPLACE_GROUP
				,	"workplaceGroup_Id" );
		
		//Act
		NtsAssert.businessException( "Msg_2146", () ->{
			
			copySource.copy( destinationTargetOrg );
			
		});
	}
	
	private static class Helper{
	
	/**
	 * 対象組織識別情報を作る
	 * @param unit 単位
	 * @return
	 */
	public static TargetOrgIdenInfor createTargetOrgIdenInfor( TargetOrganizationUnit unit ) {
		return TargetOrgIdenInfor.createFromTargetUnit( unit, IdentifierUtil.randomUniqueId() );
	}
	
	}
}
