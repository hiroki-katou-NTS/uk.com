package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.overtime.schedule;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectWorkInformation;

@RunWith(JMockit.class)
public class SCReflectBeforeOvertimeAppTest {

	@Injectable
	 private ReflectWorkInformation.Require require;
	 
	@Test
	public void test() {
		//SCReflectBeforeOvertimeApp.process(require, overTimeApp, dailyApp, reflectOvertimeBeforeSet);
	}

}
