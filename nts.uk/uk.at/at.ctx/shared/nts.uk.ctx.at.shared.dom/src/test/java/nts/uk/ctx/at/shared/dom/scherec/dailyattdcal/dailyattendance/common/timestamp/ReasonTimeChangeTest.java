package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ReasonTimeChangeTest {
	
	@Test
	public void testCreateByAutomaticSet() {
		
		ReasonTimeChange result = ReasonTimeChange.createByAutomaticSet();
		
		assertThat(result.getTimeChangeMeans()).isEqualTo(TimeChangeMeans.AUTOMATIC_SET);
		assertThat(result.getEngravingMethod()).isEmpty();
	}

}
