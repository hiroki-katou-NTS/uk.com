package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.MakeShiftMasterService.Require;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@RunWith(JMockit.class)
public class MakeShiftMasterServiceTest {

	@Injectable
	private Require require;

	@Injectable
	private nts.uk.ctx.at.shared.dom.WorkInformation.Require requireWorkinfo;

	@Test
	public void testMakeShiftMater_throw_Msg_1610() {
		String companyId = "companyId";
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		Optional<String> workTimeCode = Optional.empty();
		ShiftMasterDisInfor shiftMasterDisInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		new Expectations() {
			{
				requireWorkinfo.getWorkType(workTypeCode);
				result = Optional.of(new WorkType());
				
				requireWorkinfo.checkNeededOfWorkTimeSetting(workTypeCode);
				result = SetupType.OPTIONAL;
				
				require.checkExists(companyId, // dummy
						workTypeCode, // dummy
						null);// dummy;
				result = true;
			}
		};


		NtsAssert.businessException("Msg_1610", () -> {
			AtomTask persist = MakeShiftMasterService.makeShiftMater(requireWorkinfo, require, companyId,
					shiftMasterCode, workTypeCode,
					workTimeCode,//worktime = null 
					shiftMasterDisInfor);
			persist.run();
		});
	}

	@Test
	public void testMakeShiftMater_throw_Msg_1608() {
		String companyId = "companyId";
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		Optional<String> workTimeCode = Optional.of("workTypeCode");
		ShiftMasterDisInfor shiftMasterDisInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		new Expectations() {
			{
				requireWorkinfo.getWorkType(anyString);
			}
		};
		NtsAssert.businessException("Msg_1608", () -> {
			AtomTask persist = MakeShiftMasterService.makeShiftMater(
					requireWorkinfo, require, 
					companyId,//dummy
					shiftMasterCode, //dummy
					workTypeCode, //dummy
					workTimeCode, shiftMasterDisInfor);//dummy
			persist.run();
		});
	}

	@Test
	public void testMakeShiftMater_throw_Msg_1609() {
		String companyId = "companyId";
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		Optional<String> workTimeCode = Optional.of("workTypeCode");
		ShiftMasterDisInfor shiftMasterDisInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		new Expectations() {
			{
				requireWorkinfo.getWorkType(workTypeCode);
				result = Optional.of(new WorkType());
				
				requireWorkinfo.checkNeededOfWorkTimeSetting(workTypeCode);
				result = SetupType.REQUIRED;
				
				requireWorkinfo.getWorkTime(workTimeCode.get());
			}
		};
		NtsAssert.businessException("Msg_1609", () -> {
			AtomTask persist = MakeShiftMasterService.makeShiftMater(
					requireWorkinfo, require, 
					companyId,//dummy
					shiftMasterCode, //dummy
					workTypeCode, //dummy
					workTimeCode, shiftMasterDisInfor);//dummy
			persist.run();
		});
	}

	@Test
	public void testMakeShiftMater_throw_Msg_435() {
		String companyId = "companyId";
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		Optional<String> workTimeCode = Optional.empty();
		ShiftMasterDisInfor shiftMasterDisInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		new Expectations() {
			{
				requireWorkinfo.getWorkType(workTypeCode);
				result = Optional.of(new WorkType());
				
				requireWorkinfo.checkNeededOfWorkTimeSetting(workTypeCode);
				result = SetupType.REQUIRED;
			}
		};

		NtsAssert.businessException("Msg_435", () -> {
			AtomTask persist = MakeShiftMasterService.makeShiftMater(
					requireWorkinfo, require, 
					companyId,//dummy
					shiftMasterCode, //dummy
					workTypeCode, //dummy
					workTimeCode, shiftMasterDisInfor);//dummy
			persist.run();
		});
	}

	@Test
	public void testMakeShiftMater_throw_Msg_434() {
		String companyId = "companyId";
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";	
		Optional<String> workTimeCode = Optional.of("workTypeCode");
		ShiftMasterDisInfor shiftMasterDisInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);

		new Expectations() {
			{
				requireWorkinfo.getWorkType(workTypeCode);
				result = Optional.of(new WorkType());
				
				requireWorkinfo.checkNeededOfWorkTimeSetting(workTypeCode);
				result = SetupType.NOT_REQUIRED;
			}
		};

		NtsAssert.businessException("Msg_434", () -> {
			AtomTask persist = MakeShiftMasterService.makeShiftMater(
					requireWorkinfo, require, 
					companyId,//dummy
					shiftMasterCode, //dummy
					workTypeCode, //dummy
					workTimeCode, shiftMasterDisInfor);//dummy
			persist.run();
		});
	}
	
	
	@Test
	public void testMakeShiftMater_throw_Msg_3() {
		String companyId = "companyId";
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";	
		String workTimeCode = "workTypeCode";
		ShiftMasterDisInfor shiftMasterDisInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);

		new Expectations() {
			{
				require.checkExistsByCode(companyId, shiftMasterCode);
				result = true;
				
			}
		};

		NtsAssert.businessException("Msg_3", () -> {
			AtomTask persist = MakeShiftMasterService.makeShiftMater(
					requireWorkinfo, require, 
					companyId,//dummy
					shiftMasterCode, //dummy
					workTypeCode, //dummy
					Optional.of(workTimeCode), shiftMasterDisInfor);//dummy
			persist.run();
		});
	}

	@Test
	public void testMakeShiftMater() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode(shiftMasterCode), displayInfor, workTypeCode, null);
		new Expectations() {
			{
				requireWorkinfo.getWorkType(shiftMater.getWorkTypeCode().v());
				result = Optional.of(new WorkType());
				
				requireWorkinfo.checkNeededOfWorkTimeSetting(shiftMater.getWorkTypeCode().v());
				result = SetupType.OPTIONAL;

				require.checkExists(shiftMater.getCompanyId(), // dummy
						shiftMater.getWorkTypeCode().v(), // dummy
						null);// dummy;
				result = false;
			}
		};
		

		NtsAssert.atomTask(() -> MakeShiftMasterService.makeShiftMater(requireWorkinfo, require, shiftMater.getCompanyId(),
				shiftMater.getShiftMasterCode().v(), shiftMater.getWorkTypeCode().v(),
				Optional.empty(), shiftMater.getDisplayInfor()),
				any -> require.insert(shiftMater, shiftMater.getWorkTypeCode().v(), null));
	}

}
