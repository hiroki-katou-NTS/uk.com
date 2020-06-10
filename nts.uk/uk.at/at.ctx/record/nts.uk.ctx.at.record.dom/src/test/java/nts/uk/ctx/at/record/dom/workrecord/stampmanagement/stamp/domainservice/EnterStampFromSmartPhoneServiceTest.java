package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampButton;

/**
 * 
 * @author chungnt
 *
 */
@RunWith(JMockit.class)
public class EnterStampFromSmartPhoneServiceTest {

	@Injectable
	private nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EnterStampFromSmartPhoneService.Require require;

	@Test
	public void getters() {
		EnterStampFromSmartPhoneService enterStampFromSmartPhoneService = new EnterStampFromSmartPhoneService();
		NtsAssert.invokeGetters(enterStampFromSmartPhoneService);
	}

	@Test
	public void test_positionInfor_null() {

		TimeStampInputResult inputResult = EnterStampFromSmartPhoneService.create(require, new ContractCode("DUMMY"),
				"DUMMY", GeneralDateTime.now(), new StampButton(new PageNo(1), new ButtonPositionNo(1)),
				Optional.empty(), null);

		// assertThat().isFalse();
	}

	/**
	 * Optional<SettingsSmartphoneStamp> settingSmartPhoneStampOpt = require
	 * .getSmartphoneStampSetting(AppContexts.user().companyId());
	 * 
	 * if (!settingSmartPhoneStampOpt.isPresent()) {
	 * 
	 * throw new BusinessException("Msg_1632"); }
	 */
	@Test
	public void test_settingSmartPhoneStampOpt_isPresent() {

		new Expectations() {
			{
				require.getSmartphoneStampSetting("DUMMY");
			}
		};

		NtsAssert.businessException("Msg_1632",
				() -> EnterStampFromSmartPhoneService.create(require, new ContractCode("DUMMY"), "DUMMY",
						GeneralDateTime.now(), new StampButton(new PageNo(1), new ButtonPositionNo(1)),
						Optional.empty(), null));
	}


}
