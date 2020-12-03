package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting.ReqSettingBuilder;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@RunWith(JMockit.class)
public class DeleteRequestSettingTimeRecordServiceTest {

	@Injectable
	private DeleteRequestSettingTimeRecordService.Require require;

	/*
	 * テストしたい内容
	 * 
	 * →値を更新しない
	 * 
	 * 準備するデータ
	 * 
	 * →設定がない
	 */
	@Test
	public void testNoUpdate() {

		new Expectations() {
			{
				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = Optional.empty();

			}
		};

		val actualResult = DeleteRequestSettingTimeRecordService.process(require, new EmpInfoTerminalCode("1"),
				new ContractCode("1"));

		actualResult.run();
		new Verifications() {{
			require.updateSetting((TimeRecordReqSetting) any);
			times = 0;  
		}};
	}

	/*
	 * テストしたい内容
	 * 
	 * →データを削除せず、値を更新するだけです
	 * 
	 * 準備するデータ
	 * 
	 * →設定がない
	 */
	@Test
	public void test() {

		new Expectations() {
			{
				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = Optional.of(new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"),
						new CompanyId("1"), "1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()).build());

			}
		};

		val actualResult = DeleteRequestSettingTimeRecordService.process(require, new EmpInfoTerminalCode("1"),
				new ContractCode("1"));

		NtsAssert.atomTask(() -> actualResult, any -> require.updateSetting(any.get()));
	}

}
