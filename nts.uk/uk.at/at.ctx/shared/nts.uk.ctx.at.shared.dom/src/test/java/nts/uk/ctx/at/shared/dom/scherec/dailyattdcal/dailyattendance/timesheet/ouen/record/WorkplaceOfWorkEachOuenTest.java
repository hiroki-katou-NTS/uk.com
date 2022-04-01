package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

/**
 * 応援別勤務の勤務先のUTコード
 * @author lan_lt
 *
 */
public class WorkplaceOfWorkEachOuenTest {

	@Test
	public void testGetter() {
		
		val domain = WorkplaceOfWorkEachOuen.create(	new WorkplaceId( "workplaceId" )
													,	new WorkLocationCD( "workLocationCD" )
														);
		NtsAssert.invokeGetters( domain );
		
	}
	
	/**
	 * target: getRecipientOrg
	 */
	@Test
	public void testGetRecipientOrg() {
		
		//職場ID
		{
			val domain = new WorkplaceOfWorkEachOuen(	new WorkplaceId( "workplaceId" )
													,	Optional.empty()
													,	Optional.empty() );
			//act
			val result = domain.getRecipientOrg();
			
			//assert
			assertThat( result.getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE );
			assertThat( result.getWorkplaceId().get() ).isEqualTo( "workplaceId" );
			assertThat( result.getWorkplaceGroupId() ).isEmpty();
		}
		
		//職場グループID
		{
			val domain = new WorkplaceOfWorkEachOuen(	new WorkplaceId( "workplaceId" )
					,	Optional.empty()
					,	Optional.of( String.valueOf( "workplaceGroupID" ) ) );
			
			//act
			val result = domain.getRecipientOrg();
			//assert
			assertThat( result.getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE_GROUP );
			assertThat( result.getWorkplaceId() ).isEmpty();
			assertThat( result.getWorkplaceGroupId().get() ).isEqualTo( "workplaceGroupID" );
		}
	}
}
