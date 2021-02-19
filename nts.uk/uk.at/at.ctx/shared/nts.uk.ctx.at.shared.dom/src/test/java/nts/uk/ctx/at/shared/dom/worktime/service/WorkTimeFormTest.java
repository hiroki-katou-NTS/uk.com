package nts.uk.ctx.at.shared.dom.worktime.service;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;

/**
 * Test for WorkTimeForm
 * @author kumiko_otake
 *
 */
@RunWith(JMockit.class)
public class WorkTimeFormTest {

	@Test
	public void testGetters() {
		NtsAssert.invokeGetters(WorkTimeForm.class);
	}


	/**
	 * Target	: from( 勤務形態, 就業時間帯の設定方法 )
	 * Pattern	: 勤務形態 -> フレックス勤務
	 */
	@Test
	public void testFromWorkFormAndMethod_WorkFormIsFlex() {

		// Execute
		val result = Stream.of( WorkTimeMethodSet.values() )
						.map( e -> WorkTimeForm.from( WorkTimeDailyAtr.FLEX_WORK , e ) )
						.collect(Collectors.toList());

		// Assertion
		assertThat( result ).containsOnly( WorkTimeForm.FLEX );

	}

	/**
	 * Target	: from( 勤務形態, 就業時間帯の設定方法 )
	 * Pattern	: 勤務形態 -> 通常勤務
	 */
	@Test
	public void testFromWorkFormAndMethod_WorkFormIsRegular() {

		// Expected
		val expected = new HashMap<WorkTimeMethodSet, WorkTimeForm>();
		{
			expected.put( WorkTimeMethodSet.FIXED_WORK,		WorkTimeForm.FIXED );
			expected.put( WorkTimeMethodSet.FLOW_WORK,		WorkTimeForm.FLOW );
			expected.put( WorkTimeMethodSet.DIFFTIME_WORK,	WorkTimeForm.TIMEDIFFERENCE );
		}

		// Execute
		val result = Stream.of( WorkTimeMethodSet.values() )
						.collect(Collectors.toMap( e -> e, e -> WorkTimeForm.from( WorkTimeDailyAtr.REGULAR_WORK, e ) ));

		// Assertion
		assertThat( result ).containsExactlyInAnyOrderEntriesOf( expected );
	}

}
