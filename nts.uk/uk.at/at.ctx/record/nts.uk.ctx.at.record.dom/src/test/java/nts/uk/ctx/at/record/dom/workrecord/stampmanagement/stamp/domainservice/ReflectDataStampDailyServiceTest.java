package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mockit.Injectable;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampHelper;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.ReflectDataStampDailyService.Require;
/**
 * 
 * @author tutk
 *
 */
public class ReflectDataStampDailyServiceTest {

	@Injectable
	private Require require;
	
	@Test
	public void testReflectDataStampDailyService() {
		String employeeId = "employeeId";
		Stamp stamp = StampHelper.getStampDefault();
		assertThat(ReflectDataStampDailyService.getJudgment(require, employeeId, stamp).isPresent()).isFalse();
	}

}
