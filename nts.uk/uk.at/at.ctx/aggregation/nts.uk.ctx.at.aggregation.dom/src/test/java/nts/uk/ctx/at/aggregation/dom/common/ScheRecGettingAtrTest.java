package nts.uk.ctx.at.aggregation.dom.common;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;

/**
 * Test for ScheRecGettingAtr
 * @author kumiko_otake
 */
public class ScheRecGettingAtrTest {

	@Test
	public void test_getters() {
		NtsAssert.invokeGetters( ScheRecGettingAtr.class );
	}


	/**
	 * Target	: isNeedSchedule
	 */
	@Test
	public void test_isNeedSchedule() {

		@SuppressWarnings("serial")
		val expected = new HashMap<ScheRecGettingAtr, Boolean>() {{
			put( ScheRecGettingAtr.ONLY_SCHEDULE,			true );
			put( ScheRecGettingAtr.ONLY_RECORD,				false );
			put( ScheRecGettingAtr.SCHEDULE_WITH_RECORD,	true );
		}};

		// Execute
		val result = Stream.of( ScheRecGettingAtr.values() )
				.collect(Collectors.toMap( e -> e, e -> e.isNeedSchedule() ));

		// Assert
		assertThat( result ).containsExactlyInAnyOrderEntriesOf( expected );

	}

	/**
	 * Target	: isNeedRecord
	 */
	@Test
	public void test_isNeedRecord() {

		@SuppressWarnings("serial")
		val expected = new HashMap<ScheRecGettingAtr, Boolean>() {{
			put( ScheRecGettingAtr.ONLY_SCHEDULE,			false );
			put( ScheRecGettingAtr.ONLY_RECORD,				true );
			put( ScheRecGettingAtr.SCHEDULE_WITH_RECORD,	true );
		}};

		// Execute
		val result = Stream.of( ScheRecGettingAtr.values() )
				.collect(Collectors.toMap( e -> e, e -> e.isNeedRecord() ));

		// Assert
		assertThat( result ).containsExactlyInAnyOrderEntriesOf( expected );

	}


	/**
	 * Target	: toScheRecAtr
	 */
	@Test
	public void test_toScheRecAtr() {

		@SuppressWarnings("serial")
		val expected = new HashMap<ScheRecGettingAtr, ScheRecAtr>() {{
			put( ScheRecGettingAtr.ONLY_SCHEDULE,	ScheRecAtr.SCHEDULE );
			put( ScheRecGettingAtr.ONLY_RECORD,		ScheRecAtr.RECORD );
		}};


		Stream.of( ScheRecGettingAtr.values() )
			.forEach( e -> {
				if( expected.containsKey( e ) ) {
					assertThat( e.toScheRecAtr() ).isEqualTo( expected.get( e ) );
				} else {
					NtsAssert.systemError( () -> e.toScheRecAtr() );
				}
			} );

	}

}
