package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class JudgmentCriteriaSameStampOfSupportTest {

	@Test
	public void getters() {
		JudgmentCriteriaSameStampOfSupport support = JudgmentCriteriaSameStampOfSupportHelper.getDataDefault();
		NtsAssert.invokeGetters(support);
	}
	
	@Test
	public void test_func_checkStampRecognizedAsSame1() {
		TimeWithDayAttr standardStamp = new TimeWithDayAttr(25);
		TimeWithDayAttr targetStamp   = new TimeWithDayAttr(20);
		JudgmentCriteriaSameStampOfSupport support = JudgmentCriteriaSameStampOfSupportHelper.getDataDefault();
		boolean rs = support.checkStampRecognizedAsSame(standardStamp, targetStamp);
		assertThat(rs).isTrue();
	}
	
	@Test
	public void test_func_checkStampRecognizedAsSame2() {
		TimeWithDayAttr standardStamp = new TimeWithDayAttr(250);
		TimeWithDayAttr targetStamp   = new TimeWithDayAttr(20);
		JudgmentCriteriaSameStampOfSupport support = JudgmentCriteriaSameStampOfSupportHelper.getDataDefault();
		boolean rs = support.checkStampRecognizedAsSame(standardStamp, targetStamp);
		assertThat(rs).isFalse();
	}

}
