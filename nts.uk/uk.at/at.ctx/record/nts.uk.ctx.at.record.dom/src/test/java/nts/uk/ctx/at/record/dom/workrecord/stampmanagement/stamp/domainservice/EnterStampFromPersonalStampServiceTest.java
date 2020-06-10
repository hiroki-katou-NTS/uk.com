package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampHelper;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EnterStampFromPersonalStampService.Require;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampButton;

/**
 * 
 * @author tutk
 *
 */
@RunWith(JMockit.class)
public class EnterStampFromPersonalStampServiceTest {
	
	@Test
	public void getters() {
		EnterStampFromPersonalStampService enterStampFromPersonalStampService = new EnterStampFromPersonalStampService();
		NtsAssert.invokeGetters(enterStampFromPersonalStampService);
	}

	@Injectable
	private Require require;
	
	@Injectable
	private nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CreateStampDataForEmployeesService.Require requireCreateStamp ;

	/**
	 * require.getStampSet().isPresent() == false

	 */
	@Test
	public void testEnterStampFromPersonalStampService_1() {
		String employeeId = "employeeId";// dummy
		String contractCode = "contractCode";// dummy
		GeneralDateTime stmapDateTime = GeneralDateTime.now();// dummy
		StampButton stampButton = new StampButton(new PageNo(1), new ButtonPositionNo(1));
//		Relieve relieve = StampHelper.getRelieveDefault();// dummy
		RefectActualResult refActualResults = StampHelper.getRefectActualResultDefault();// dummy
//		Optional<GeoCoordinate> positionInfo = Optional.of(StampHelper.getGeoCoordinateDefault());// dummy
//		Optional<EmpInfoTerminalCode> empInfoTerCode = Optional.of(new EmpInfoTerminalCode(1234));// dummy

		new Expectations() {
			{
				require.getStampSet();
			}
		};
		NtsAssert.businessException("Msg_1632", () -> EnterStampFromPersonalStampService.create(require, contractCode, employeeId,
				stmapDateTime, stampButton, refActualResults));

	}

	/**
	 * require.getStampSet().isPresent() == true 
	 * $ボタン詳細設定 = $個人利用の打刻設定.ボタン詳細設定を取得する(ページNO, ボタン位置NO) buttonSet.isPresent() == false;
	 * pageNo = 10 not exist
	 */
	@Test
	public void testEnterStampFromPersonalStampService_2() {
		String employeeId = "employeeId";// dummy
		String contractCode = "contractCode";// dummy
		GeneralDateTime stmapDateTime = GeneralDateTime.now();// dummy
		StampButton stampButton = new StampButton(new PageNo(1), new ButtonPositionNo(1));
//		Relieve relieve = StampHelper.getRelieveDefault();// dummy
		RefectActualResult refActualResults = StampHelper.getRefectActualResultDefault();// dummy
//		Optional<GeoCoordinate> positionInfo = Optional.of(StampHelper.getGeoCoordinateDefault());// dummy
//		Optional<EmpInfoTerminalCode> empInfoTerCode = Optional.of(new EmpInfoTerminalCode(1234));// dummy

		new Expectations() {
			{
				require.getStampSet();
				result = Optional.of(StampSettingPersonHelper.DUMMY);
			}
		};
		NtsAssert.businessException("Msg_1632", () -> EnterStampFromPersonalStampService.create(require, contractCode, employeeId,
				stmapDateTime, stampButton, refActualResults));

	}

	/**
	 * require.getStampSet().isPresent() == true 
	 * $ボタン詳細設定 = $個人利用の打刻設定.ボタン詳細設定を取得する(ページNO, ボタン位置NO) buttonSet.isPresent() == true;
	 * pageNo = 1 exist
	 * buttonPosNo = 1 not exist exist
	 */
	@Test
	public void testEnterStampFromPersonalStampService_3() {
		String employeeId = "employeeId";// dummy
		String contractCode = "contractCode";// dummy
		GeneralDateTime stmapDateTime = GeneralDateTime.now();// dummy
		StampButton stampButton = new StampButton(new PageNo(1), new ButtonPositionNo(10));
//		Relieve relieve = StampHelper.getRelieveDefault();// dummy
		RefectActualResult refActualResults = StampHelper.getRefectActualResultDefault();// dummy
//		Optional<GeoCoordinate> positionInfo = Optional.of(StampHelper.getGeoCoordinateDefault());// dummy
//		Optional<EmpInfoTerminalCode> empInfoTerCode = Optional.of(new EmpInfoTerminalCode(1234));// dummy

		new Expectations() {
			{
				require.getStampSet();
				result = Optional.of(StampSettingPersonHelper.DUMMY);
			}
		};
		NtsAssert.businessException("Msg_1632", () -> EnterStampFromPersonalStampService.create(require, contractCode, employeeId,
				stmapDateTime, stampButton, refActualResults));

	}

	
	/**
	 * require.getStampSet().isPresent() == true 
	 * $ボタン詳細設定 = $個人利用の打刻設定.ボタン詳細設定を取得する(ページNO, ボタン位置NO) buttonSet.isPresent() == true;
	 * 	 * pageNo = 1 exist
	 * buttonPosNo = 1 exist
	 */
	@Test
	public void testEnterStampFromPersonalStampService_4() {
		String employeeId = "employeeId";// dummy
		String contractCode = "contractCode";// dummy
		GeneralDateTime stmapDateTime = GeneralDateTime.now();// dummy
		StampButton stampButton = new StampButton(new PageNo(1), new ButtonPositionNo(1));
//		Relieve relieve = StampHelper.getRelieveDefault();// dummy
		RefectActualResult refActualResults = StampHelper.getRefectActualResultDefault();// dummy
//		Optional<GeoCoordinate> positionInfo = Optional.of(StampHelper.getGeoCoordinateDefault());// dummy
//		Optional<EmpInfoTerminalCode> empInfoTerCode = Optional.of(new EmpInfoTerminalCode(1234));// dummy

		new Expectations() {
			{
				require.getStampSet();
				result = Optional.of(StampSettingPersonHelper.DUMMY);
				
				require.getListStampCard(anyString);
				result = new ArrayList<>();
			}
		};
		NtsAssert.businessException("Msg_1632", () -> EnterStampFromPersonalStampService.create(require, contractCode, employeeId,
				stmapDateTime, stampButton, refActualResults));
	}

}
