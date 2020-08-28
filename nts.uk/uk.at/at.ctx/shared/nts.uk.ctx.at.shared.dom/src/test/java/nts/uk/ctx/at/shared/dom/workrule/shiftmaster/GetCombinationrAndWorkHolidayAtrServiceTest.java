package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.GetCombinationrAndWorkHolidayAtrService.Require;
@RunWith(JMockit.class)
public class GetCombinationrAndWorkHolidayAtrServiceTest {
	@Injectable
	private Require require;

	/**
	 * require.コードでシフトマスタ取得(会社ID, シフトマスタコードリスト) is empty
	 */
	@Test
	public void testGetCode() {
		String companyID = "cid";
		List<String> lstShiftMasterCd = Arrays.asList("001","002");
		new Expectations() {
			{
				require.getByListEmp(companyID, lstShiftMasterCd);
			}
		};
		assertTrue(GetCombinationrAndWorkHolidayAtrService
				.getCode(require, companyID, lstShiftMasterCd).isEmpty());
		
	}
	
	/**
	 * require.コードでシフトマスタ取得(会社ID, シフトマスタコードリスト) not empty
	 */
	@Test
	public void testGetCode_1() {
		String companyID = "cid";
		List<String> lstShiftMasterCd = Arrays.asList("001","002");
		
		List<ShiftMaster> listShiftMaster = new ArrayList<>();
			ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name1"),new ColorCodeChar6("color1"), null);
			ShiftMasterDisInfor displayInfor2 =  new ShiftMasterDisInfor(new ShiftMasterName("name2"),new ColorCodeChar6("color2"), null);
			
			ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode("1"), displayInfor, "1","1");
			ShiftMaster shiftMater2 = new ShiftMaster("companyId",new ShiftMasterCode("2"), displayInfor2, "2","2");
			
			listShiftMaster.add(shiftMater);
			listShiftMaster.add(shiftMater2);
		
		new Expectations() {
			{
				require.getByListEmp(companyID, lstShiftMasterCd);
				result = listShiftMaster;
			}
		};
		new MockUp<nts.uk.ctx.at.shared.dom.WorkInformation>() {
			@Mock
			public Optional<WorkStyle> getWorkStyle(nts.uk.ctx.at.shared.dom.WorkInformation.Require requireWorkinfo) {
				return Optional.of(WorkStyle.ONE_DAY_REST);
			}
		};
		Map<ShiftMaster,Optional<WorkStyle>> datas = GetCombinationrAndWorkHolidayAtrService
				.getCode(require, companyID, lstShiftMasterCd);
		assertFalse(datas.isEmpty());
		
		assertThat(datas.keySet().stream()
				.sorted((x,y) -> x.getShiftMasterCode().v().compareTo(y.getShiftMasterCode().v())))
				.extracting(d -> d.getShiftMasterCode().v())
				.containsExactly("001", "002");
		
		assertThat(datas.values()).extracting(d -> d.get().value).containsExactly(0, 0);
	}
	
	/**
	 * require.勤務情報でマスタリスト取得(会社ID, 勤務情報リスト) is empty
	 */
	@Test
	public void testGetbyWorkInfo() {
		String companyID = "cid";
		List<WorkInformation> lstWorkInformation = ShiftMasterHelper.getListWorkInformationByNumber(2);
		new Expectations() {
			{
				require.getByListWorkInfo(companyID,lstWorkInformation);
			}
		};
		assertTrue(GetCombinationrAndWorkHolidayAtrService
				.getbyWorkInfo(require, companyID, lstWorkInformation).isEmpty());
		
	}
	
	/**
	 * require.勤務情報でマスタリスト取得(会社ID, 勤務情報リスト) is empty
	 */
	@Test
	public void testGetbyWorkInfo_1() {
		String companyID = "cid";
		
			List<WorkInformation> lstWorkInformation = new ArrayList<>();
			WorkInformation workInformation = new WorkInformation("001", "001");
			WorkInformation workInformation2 = new WorkInformation("002", "002");
			lstWorkInformation.add(workInformation);
			lstWorkInformation.add(workInformation2);
		
			List<ShiftMaster> listShiftMaster = new ArrayList<>();
			ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name1"),new ColorCodeChar6("color1"), null);
			ShiftMasterDisInfor displayInfor2 =  new ShiftMasterDisInfor(new ShiftMasterName("name2"),new ColorCodeChar6("color2"), null);
			ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode("1"), displayInfor, "1","1");
			ShiftMaster shiftMater2 = new ShiftMaster("companyId",new ShiftMasterCode("2"), displayInfor2, "2","2");
			listShiftMaster.add(shiftMater);
			listShiftMaster.add(shiftMater2);
			
		new Expectations() {
			{
				require.getByListWorkInfo(companyID,lstWorkInformation);
				result = listShiftMaster;
			}
		};
		new MockUp<nts.uk.ctx.at.shared.dom.WorkInformation>() {
			@Mock
			public Optional<WorkStyle> getWorkStyle(nts.uk.ctx.at.shared.dom.WorkInformation.Require requireWorkinfo) {
				return Optional.of(WorkStyle.ONE_DAY_REST);
			}
		};
		Map<ShiftMaster,Optional<WorkStyle>> datas = GetCombinationrAndWorkHolidayAtrService
				.getbyWorkInfo(require, companyID, lstWorkInformation);
		assertFalse(datas.isEmpty());
		
		assertThat(datas.keySet().stream().sorted((x,y) -> x.getShiftMasterCode().v()
				.compareTo(y.getShiftMasterCode().v())))
				.extracting(d -> d.getShiftMasterCode().v())
				.containsExactly("001", "002");
		
		assertThat(datas.values()).extracting(d -> d.get().value).containsExactly(0, 0);

	}

}
