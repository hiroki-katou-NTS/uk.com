package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;

import java.io.FileNotFoundException;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

@RunWith(JMockit.class)
public class ConvertNRLRemoteReceiveToObjectServiceTest {

	@Injectable
	private ConvertNRLRemoteReceiveToObjectService.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws FileNotFoundException {

        String payload = "NRL-m,200,9@基本設定,ボリューム,sp_vol,num,1,5,0:9,0@@@@@sp_vol,68";
		Optional<AtomTask> actualResult = ConvertNRLRemoteReceiveToObjectService.convertData(require, new ContractCode(""), new EmpInfoTerminalCode(""), "", payload);

		NtsAssert.atomTask(() -> actualResult.get(),
				any -> require.removeTRSetFormatList((EmpInfoTerminalCode) (any.get()), (ContractCode) (any.get())),
				any -> require.insert((ContractCode) (any.get()), (TimeRecordSetFormatList) (any.get())));
	}

}
