package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ReservationArt;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author tutk
 *
 */
public class StampRecordTest {

	@Test
	public void getters() {
		StampRecord stampRecord = StampRecordHelper.getStampRecord();
		NtsAssert.invokeGetters(stampRecord);
	}
	
	@Test
	public void test() {
		
		StampNumber stampNumber = new StampNumber("stampNumber");
		GeneralDateTime stampDateTime = GeneralDateTime.now();
		boolean stampArt = false;
		ReservationArt revervationAtr = ReservationArt.valueOf(0);
		Optional<EmpInfoTerminalCode> empInfoTerCode = Optional.of(new EmpInfoTerminalCode(1000)); 
		StampRecord stampRecord  = new StampRecord(new ContractCode(AppContexts.user().contractCode()),stampNumber, stampDateTime, new StampTypeDisplay(""), empInfoTerCode);
		assertThat(stampRecord.getStampNumber()).isEqualTo(stampNumber);
		assertThat(stampRecord.getStampDateTime()).isEqualTo(stampDateTime);
		//assertThat(stampRecord.isStampArt()).isEqualTo(stampArt);
		assertThat(stampRecord.getEmpInfoTerCode().get()).isEqualTo(empInfoTerCode.get());
	}

}
