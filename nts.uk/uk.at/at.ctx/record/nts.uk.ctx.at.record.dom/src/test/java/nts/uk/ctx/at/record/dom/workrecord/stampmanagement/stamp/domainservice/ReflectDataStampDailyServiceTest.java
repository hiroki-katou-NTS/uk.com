package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.NoArgsConstructor;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpDataImport;
import nts.uk.ctx.at.record.dom.dailyresultcreationprocess.creationprocess.creationclass.dailywork.ReflectStampDailyAttdOutput;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
/**
 * 
 * @author tutk
 *
 */

@RunWith(JMockit.class)
public class ReflectDataStampDailyServiceTest {
	
	String employeeId = "employeeId";
	String cid = "cid";
	GeneralDate date = GeneralDate.today();
	List<ErrorMessageInfo> errorMessageInfos = new ArrayList<>();
	ReflectDataStampDailyServiceHelper helper = new ReflectDataStampDailyServiceHelper();
	
	ChangeDailyAttendance changeDailyAtt = new  ChangeDailyAttendance(true,
			true, 
			true,
			true,
			ScheduleRecordClassifi.RECORD, true);
	
	OutputTimeReflectForWorkinfo info = new OutputTimeReflectForWorkinfo();

	// Test all date is error
	@Test
	public void testNotCard(@Mocked ReflectDataStampDailyService.Require require) {

		Stamp stamp = StampHelper.getStampDefault();

		new Expectations() {
			{
				require.getByCardNoAndContractCode((ContractCode) any, (StampNumber) any);
				result = Optional.empty();
			}
		};

		Optional<InfoReflectDestStamp> optional = ReflectDataStampDailyService.getJudgment(require,
				new ContractCode(""), stamp);

		assertThat(optional.isPresent()).isFalse();

	}
		
	// Test all date is error
	//反映対象日がない
	@Test
	public void test_all_date_is_error(@Mocked ReflectDataStampDailyService.Require require) {
		
	
		Stamp stamp = StampHelper.getStampDefault();
		
		new Expectations() {
			{
			   require.getByCardNoAndContractCode((ContractCode)any, (StampNumber) any);
			   result = Optional.of(new StampCard(null, null, employeeId));
			}
		};
		
		Optional<InfoReflectDestStamp> optional = ReflectDataStampDailyService.getJudgment(require, new ContractCode(""), stamp);
		
		assertThat(optional.isPresent()).isFalse();

	}
	
	
	// Test all date is not error and not date is true
	@Test
	public void test2() {
		
		//GeneralDate.ymd(2021, 03, 15)
		Stamp stamp = StampHelper.getStampDefault();
		
		ReflectDataStampDailyServiceRequireImplTest impl = new ReflectDataStampDailyServiceRequireImplTest();
		Optional<InfoReflectDestStamp> optional = ReflectDataStampDailyService
				.getJudgment(impl, new ContractCode(""), stamp);

		assertThat(optional.isPresent()).isTrue();

		assertThat(optional.get().getDate()).isEqualTo(GeneralDate.ymd(2021, 03, 15));
	}
	
	@NoArgsConstructor
	public class ReflectDataStampDailyServiceRequireImplTest implements ReflectDataStampDailyService.Require{

		@Override
		public Optional<StampCard> getByCardNoAndContractCode(ContractCode contractCode, StampNumber stampNumber) {
			return Optional.of(new StampCard(null, null, employeeId));
		}

		@Override
		public Optional<ReflectStampDailyAttdOutput> createDailyDomAndReflectStamp(String cid, String employeeId,
				GeneralDate date, Stamp stamp) {
			if(date.before(GeneralDate.ymd(2021, 03, 15))) {
				return Optional.empty();
			}
			stamp.getImprintReflectionStatus().markAsReflected(GeneralDate.ymd(2021, 03, 15));
			return Optional.of(new ReflectStampDailyAttdOutput(null, ChangeDailyAttendance.createDefault(ScheduleRecordClassifi.RECORD)));
		}

		@Override
		public List<EmpDataImport> getEmpData(List<String> empIDList) {
			return Arrays.asList(new EmpDataImport(cid, "", employeeId, "", Optional.empty()));
		}
		
	}
		
}