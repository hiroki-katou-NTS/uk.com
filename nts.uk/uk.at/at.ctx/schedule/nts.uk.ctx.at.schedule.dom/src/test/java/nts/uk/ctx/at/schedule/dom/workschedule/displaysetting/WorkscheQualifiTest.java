package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.PersonSymbolQualify;


@RunWith(JMockit.class)
public class WorkscheQualifiTest {
	
	@Test
	public void getter(){
		WorkscheQualifi target = WorkscheQualifiHelper.Dummy;
		NtsAssert.invokeGetters(target);
	}
	@Test
	public void test_listQualificationCD_null(){
		NtsAssert.businessException("Msg_1786",() -> WorkscheQualifi.workScheduleQualification(
				new PersonSymbolQualify("1"), new ArrayList<>()));
	}
	
	@Test
	public void test_getMsg(){
		List<QualificationCD> qualificationCDs = Arrays.asList(new QualificationCD("1"),new QualificationCD("2"),
				new QualificationCD("3"),new QualificationCD("4"),new QualificationCD("5"),new QualificationCD("6"));
		NtsAssert.businessException("Msg_1786",() -> WorkscheQualifi.workScheduleQualification(
				new PersonSymbolQualify("1"), qualificationCDs));
	}
	
	@Test
	public void workScheduleQualification_succes() {
		List<QualificationCD> qualificationCDs = Arrays.asList(new QualificationCD("1"),new QualificationCD("2"));
		WorkscheQualifi qualifi = WorkscheQualifi.workScheduleQualification(new PersonSymbolQualify("1"), qualificationCDs);
		assertNotNull(qualifi);
	}

}
