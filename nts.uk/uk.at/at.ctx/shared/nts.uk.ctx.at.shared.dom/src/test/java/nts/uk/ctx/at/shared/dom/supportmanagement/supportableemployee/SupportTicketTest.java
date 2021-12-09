package nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * Test for SupportTicket
 * @author kumiko_otake
 */
public class SupportTicketTest {

	/**
	 * Target	: All getter methods
	 */
	@Test
	public void test_getters() {

		NtsAssert.invokeGetters(new SupportTicket(
				new EmployeeId("dummy-emp-id")
			,	TargetOrgIdenInfor.creatIdentifiWorkplace("dummy-wkp-id")
			,	SupportType.ALLDAY
			,	GeneralDate.ymd( 2021, 12, 1 )
			,	Optional.empty()
		));

	}


	/**
	 * Target	: equals
	 */
	@Test
	public void test_equals() {

		val object = new SupportTicket(
					new EmployeeId("emp-id")
				,	TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("wkp-grp-id")
				,	SupportType.ALLDAY
				,	GeneralDate.ymd( 2021, 12, 1 )
				,	Optional.empty()
			);


		/** 検証 **/
		// True: すべて同じ値
		{
			val target = new SupportTicket(
						new EmployeeId("emp-id")
					,	TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("wkp-grp-id")
					,	SupportType.ALLDAY
					,	GeneralDate.ymd( 2021, 12, 1 )
					,	Optional.empty()
				);
			assertThat( object.equals( target ) ).isTrue();
		}

		// False: 社員ID
		{
			val target = new SupportTicket(
						new EmployeeId("emp-id-diff")
					,	object.getRecipient()
					,	object.getSupportType()
					,	object.getDate()
					,	object.getTimespan()
				);
			assertThat( object.equals( target ) ).isFalse();
		}
		// False: 応援先
		{
			val target = new SupportTicket(
						object.getEmployeeId()
					,	TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("wkp-grp-id-diff")
					,	object.getSupportType()
					,	object.getDate()
					,	object.getTimespan()
				);
			assertThat( object.equals( target ) ).isFalse();
		}
		// False: 応援形式
		{
			val target = new SupportTicket(
						object.getEmployeeId()
					,	object.getRecipient()
					,	SupportType.TIMEZONE
					,	object.getDate()
					,	object.getTimespan()
				);
			assertThat( object.equals( target ) ).isFalse();
		}
		// False: 年月日
		{
			val target = new SupportTicket(
						object.getEmployeeId()
					,	object.getRecipient()
					,	object.getSupportType()
					,	GeneralDate.ymd( 2022, 1, 31 )
					,	object.getTimespan()
				);
			assertThat( object.equals( target ) ).isFalse();
		}
		// False: 時間帯
		{
			val target = new SupportTicket(
						object.getEmployeeId()
					,	object.getRecipient()
					,	object.getSupportType()
					,	object.getDate()
					,	Optional.of( new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 10,  0 ), TimeWithDayAttr.hourMinute( 11,  0 ) ) )
				);
			assertThat( object.equals( target ) ).isFalse();
		}

	}

}
