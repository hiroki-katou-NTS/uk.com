package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class DisplayControlPersonalConditionTest {

	
	@Test
	public void getter(){
		NtsAssert.invokeGetters(DisplayControlPersonalConditionHelper.getData());
	}
	@Test
	public void getMsg(){
		DisplayControlPersonalCondition displayControlPersonalCondition = DisplayControlPersonalConditionHelper.getOptionalData()
				;
		NtsAssert.businessException("Msg_1682", () ->{
			displayControlPersonalCondition.getListConditionDisplayControl(); 
		});
	}
	@Test
	public void success(){
		DisplayControlPersonalCondition displayControlPersonalCondition = DisplayControlPersonalConditionHelper.getData();

		List<PersonInforDisplayControl> result = Arrays.asList(
				new PersonInforDisplayControl(EnumAdaptor.valueOf(ConditionATRWorkSchedule.INSURANCE_STATUS.value, ConditionATRWorkSchedule.class),
						null),
				new PersonInforDisplayControl(EnumAdaptor.valueOf(ConditionATRWorkSchedule.LICENSE_ATR.value, ConditionATRWorkSchedule.class),
						EnumAdaptor.valueOf(1, NotUseAtr.class))
				);

		displayControlPersonalCondition = DisplayControlPersonalCondition.get(displayControlPersonalCondition.getCompanyID(), 
				result, displayControlPersonalCondition.getOtpWorkscheQualifi());
		
		List<ConditionATRWorkSchedule> lstAtr = displayControlPersonalCondition.getListConditionDisplayControl().stream().map(x-> x.getConditionATR()).collect(Collectors.toList());
		
		assertThat(lstAtr.isEmpty()).isFalse();
		assertThat(lstAtr).extracting(x-> x.value).containsExactly(3).isEmpty();
		assertThat(displayControlPersonalCondition.getOtpWorkscheQualifi().isPresent()).isTrue();
		
	}
	//Thieu Unittest cho ham acquireInforDisplayControlPersonalCondition
}


