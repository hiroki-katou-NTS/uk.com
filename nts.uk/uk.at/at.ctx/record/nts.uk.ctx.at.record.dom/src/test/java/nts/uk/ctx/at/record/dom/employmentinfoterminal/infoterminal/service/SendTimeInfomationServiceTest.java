package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendTimeInfomation;

/**
 * @author ThanhNX
 *
 *         時刻情報Test
 */
@RunWith(JMockit.class)
public class SendTimeInfomationServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {

		new MockUp<GeneralDateTime>() {
			@Mock
			public GeneralDateTime now() {
				return GeneralDateTime.ymdhms(2020, 6, 8, 6, 6, 6);
			}
		};

		SendTimeInfomation resultActual = SendTimeInfomationService.send();
		assertThat(resultActual).isEqualTo(new SendTimeInfomation(20, 6, 8, 6, 6, 6, 0));
	}
}
