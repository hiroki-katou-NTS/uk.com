package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class JudgmentCriteriaSameStampOfSupportTest {

	@Test
	public void getters() {
		JudgmentCriteriaSameStampOfSupport support = JudgmentCriteriaSameStampOfSupportHelper.getDataDefault1();
		NtsAssert.invokeGetters(support);
	}
	
	@Test
	public void test_constructor_C1() {
		JudgmentCriteriaSameStampOfSupport support = JudgmentCriteriaSameStampOfSupportHelper.getDataDefault2();
		NtsAssert.invokeGetters(support);
	}
	
	@Test
	public void test_func_create() {
		JudgmentCriteriaSameStampOfSupport support = JudgmentCriteriaSameStampOfSupport.create("cid", 1, 1);
		assertThat(support.getCid()).isEqualTo("cid");
		assertThat(support.getSameStampRanceInMinutes().v()).isEqualTo(1);
		assertThat(support.getSupportMaxFrame().v()).isEqualTo(1);
	}
	
	@Test
	public void test_func_checkStampRecognizedAsSame1() {
		TimeWithDayAttr standardStamp = new TimeWithDayAttr(25);
		TimeWithDayAttr targetStamp   = new TimeWithDayAttr(20);
		JudgmentCriteriaSameStampOfSupport support = JudgmentCriteriaSameStampOfSupportHelper.getDataDefault1();
		boolean rs = support.checkStampRecognizedAsSame(standardStamp, targetStamp);
		assertThat(rs).isTrue();
	}
	
	@Test
	public void test_func_checkStampRecognizedAsSame2() {
		TimeWithDayAttr standardStamp = new TimeWithDayAttr(250);
		TimeWithDayAttr targetStamp   = new TimeWithDayAttr(20);
		JudgmentCriteriaSameStampOfSupport support = JudgmentCriteriaSameStampOfSupportHelper.getDataDefault1();
		boolean rs = support.checkStampRecognizedAsSame(standardStamp, targetStamp);
		assertThat(rs).isFalse();
	}

}
