package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampHelper;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CreateStampDataForEmployeesService.Require;
/**
 * 
 * @author tutk
 *
 */
@RunWith(JMockit.class)
public class CreateStampDataForEmployeesServiceTest {

	@Injectable
	private Require require;
	
	/**
	 * require.getListStampCard(employeeId) is empty
	 * Throw Msg_433
	 */
	@Test
	public void testCreateStampDataForEmployee_1() {
		String employeeId = "employeeId";//dummy
		GeneralDateTime datetime = GeneralDateTime.now();//dummy
		Relieve relieve = StampHelper.getRelieveDefault();//dummy
		ButtonType buttonType = DomainServiceHeplper.getButtonTypeDefault();//dummy
		Optional<RefectActualResult> refActualResults = Optional.of(StampHelper.getRefectActualResultDefault());//dummy
		Optional<GeoCoordinate> positionInfo = Optional.of(StampHelper.getGeoCoordinateDefault());//dummy
		Optional<EmpInfoTerminalCode> empInfoTerCode =Optional.of(new EmpInfoTerminalCode(1234));//dummy
		
		new Expectations() {
			{
				require.getListStampCard(employeeId);
			}
		};
		NtsAssert.businessException("Msg_433", () -> CreateStampDataForEmployeesService.create(require, employeeId,
				datetime, relieve, buttonType, refActualResults, positionInfo, empInfoTerCode));
	}
	
	/**
	 * require.getListStampCard(employeeId) not empty
	 * 打刻区分を取得する
	 * ButtonType buttonType.getStampType().isPensent() == true;
	 * positionInfo.isPresent() == true
	 */
	@Test
	public void testCreateStampDataForEmployee_2() {
		String employeeId = "employeeId";//dummy
		GeneralDateTime datetime = GeneralDateTime.now();//dummy
		Relieve relieve = StampHelper.getRelieveDefault();//dummy
		ButtonType buttonType = DomainServiceHeplper.getButtonTypeDefault();//dummy
		Optional<RefectActualResult> refActualResults = Optional.of(StampHelper.getRefectActualResultDefault());////dummy
		Optional<GeoCoordinate> positionInfo = Optional.of(StampHelper.getGeoCoordinateDefault());//dummy
		Optional<EmpInfoTerminalCode> empInfoTerCode =Optional.of(new EmpInfoTerminalCode(1234));//dummy
		
		new Expectations() {
			{
				require.getListStampCard(employeeId);
				result = Arrays.asList(
						StampHelper.getStampCardByInput("stampCardId1", "stampNumber1", GeneralDate.today()),
						StampHelper.getStampCardByInput("stampCardId2", "stampNumber2",
								GeneralDate.today().addDays(-10)),
						StampHelper.getStampCardByInput("stampCardId3", "stampNumber3",
								GeneralDate.today().addDays(-3)));
			}
		};
		StampDataReflectResult stampDataReflectResult = CreateStampDataForEmployeesService.create(require, employeeId,
				datetime, relieve, buttonType, refActualResults, positionInfo, empInfoTerCode);
		assertThat(stampDataReflectResult.getReflectDate().isPresent()).isFalse();
		NtsAssert.atomTask(
				() -> stampDataReflectResult.getAtomTask(),
				any -> require.insert((StampRecord) any.get()),
				any -> require.insert((Stamp) any.get())
		);
	}
	
	/**
	 * require.getListStampCard(employeeId) not empty
	 * 打刻区分を取得する
	 * ButtonType buttonType.getStampType().isPensent() == true;
	 * positionInfo.isPresent() == false
	 */
	@Test
	public void testCreateStampDataForEmployee_3() {
		String employeeId = "employeeId";//dummy
		GeneralDateTime datetime = GeneralDateTime.now();//dummy
		Relieve relieve = StampHelper.getRelieveDefault();//dummy
		ButtonType buttonType = DomainServiceHeplper.getButtonTypeDefault();//dummy
		Optional<RefectActualResult> refActualResults = Optional.of(StampHelper.getRefectActualResultDefault());
		Optional<GeoCoordinate> positionInfo = Optional.empty();
		Optional<EmpInfoTerminalCode> empInfoTerCode =Optional.of(new EmpInfoTerminalCode(1234));//dummy
		
		new Expectations() {
			{
				require.getListStampCard(employeeId);
				result = Arrays.asList(
						StampHelper.getStampCardByInput("stampCardId1", "stampNumber1", GeneralDate.today()),
						StampHelper.getStampCardByInput("stampCardId2", "stampNumber2",
								GeneralDate.today().addDays(-10)),
						StampHelper.getStampCardByInput("stampCardId3", "stampNumber3",
								GeneralDate.today().addDays(-3)));
			}
		};
		StampDataReflectResult stampDataReflectResult = CreateStampDataForEmployeesService.create(require, employeeId,
				datetime, relieve, buttonType, refActualResults, positionInfo, empInfoTerCode);
		assertThat(stampDataReflectResult.getReflectDate().isPresent()).isFalse();
		NtsAssert.atomTask(
				() -> stampDataReflectResult.getAtomTask(),
				any -> require.insert((StampRecord) any.get()),
				any -> require.insert((Stamp) any.get())
		);
		
	}
	
	/**
	 * require.getListStampCard(employeeId) not empty
	 * 打刻区分を取得する
	 * ButtonType buttonType.getStampType().isPensent() == false;
	 */
	@Test
	public void testCreateStampDataForEmployee_4() {
		String employeeId = "employeeId";//dummy
		GeneralDateTime datetime = GeneralDateTime.now();//dummy
		Relieve relieve = StampHelper.getRelieveDefault();//dummy
		ButtonType buttonType = DomainServiceHeplper.getButtonTypeHaveStampTypeNull();
		Optional<RefectActualResult> refActualResults = Optional.of(StampHelper.getRefectActualResultDefault());//dummy
		Optional<GeoCoordinate> positionInfo = Optional.of(StampHelper.getGeoCoordinateDefault());//dummy
		Optional<EmpInfoTerminalCode> empInfoTerCode =Optional.of(new EmpInfoTerminalCode(1234));//dummy
		
		new Expectations() {
			{
				require.getListStampCard(employeeId);
				result = Arrays.asList(
						StampHelper.getStampCardByInput("stampCardId1", "stampNumber1", GeneralDate.today()),
						StampHelper.getStampCardByInput("stampCardId2", "stampNumber2",
								GeneralDate.today().addDays(-10)),
						StampHelper.getStampCardByInput("stampCardId3", "stampNumber3",
								GeneralDate.today().addDays(-3)));
			}
		};
		StampDataReflectResult stampDataReflectResult = CreateStampDataForEmployeesService.create(require, employeeId,
				datetime, relieve, buttonType, refActualResults, positionInfo, empInfoTerCode);
		assertThat(stampDataReflectResult.getReflectDate().isPresent()).isFalse();
		NtsAssert.atomTask(
				() -> stampDataReflectResult.getAtomTask(),
				any -> require.insert((StampRecord) any.get())
		);
	}
	
	

}
