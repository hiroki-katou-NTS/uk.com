package nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction.Require;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
/**
 * Test 打刻エリア制限 
 * 
 * @author vandv_NWS
 *
 */
@RunWith(JMockit.class)
public class StampingAreaRestrictionTest {
	@Injectable
	private Require require;
	
	final String cId = StampingAreaRestrictionTestHelp.CID;
	final String sId = StampingAreaRestrictionTestHelp.SID;
	final String contractCD = StampingAreaRestrictionTestHelp.CONTRACTCD;
	
	@Test
	public void getters() {
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.iseUseLocation();
		NtsAssert.invokeGetters(domain);
	}
	/*
	 * 
	 * 打刻位置.isEmpty
	 * */
	@Test
	public void specifyAreaTest() {
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.isUserGeoCoordinate();
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(StampingAreaRestrictionTestHelp.getGeoCoordinateDefaultNo());//dummy
		NtsAssert.businessException("Msg_2096",()-> domain.checkAreaStamp(require, contractCD, cId, sId,stampLocationInfor));
	}	
	/*
	 * if @位置情報を利用する == しない
	 * */
	@Test
	public void checkUseLocation() {
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(StampingAreaRestrictionTestHelp.getGeoCoordinateDefault());//dummy
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.notUseLoacationAndUseOnlyWorkplace();
		Optional<WorkLocation> result  =  domain.checkAreaStamp(require, contractCD, cId, sId,stampLocationInfor );
		assertThat(result).isEmpty();
	}	
	/*
	 * [prv-1] 所属職場に紐づける勤務場所を特定する
	 * */
	@Test
	public void identifyWorkLocation() {
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(StampingAreaRestrictionTestHelp.getGeoCoordinateDefault());//dummy
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.useOnlyWorkplaceAndLoacation();
		
		new Expectations() {
			{
				require.getWorkPlaceOfEmpl(sId,GeneralDate.today());
			}	
		};
		NtsAssert.businessException("Msg_427",()-> domain.checkAreaStamp(require, contractCD, cId, sId,stampLocationInfor));
	}	
	/*
	 * if @制限方法 == 所属職場のみ許可
	 * 
	 * */
	@Test
	public void setValueidentifyWorkLocation() {
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(StampingAreaRestrictionTestHelp.getGeoCoordinateDefault());//dummy
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.useOnlyWorkplaceAndLoacation();
		new Expectations() {
			{
				require.getWorkPlaceOfEmpl(sId,GeneralDate.today());
				result = Optional.of(StampingAreaRestrictionTestHelp.createWorklocation(contractCD, "dummy", "dummy")); 
			}
			{
				require.findByWorkPlace(contractCD, cId, "dummy");
				result = new ArrayList<>();
			}
		};
		NtsAssert.businessException("Msg_2095",()-> domain.checkAreaStamp(require, contractCD, cId, sId,stampLocationInfor));
	}
	/*
	 * $場所一覧 = require.全ての勤務場所を取得する(契約コード)
	 * 
	 * */
	@Test
	public void checkfindAll() {
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(StampingAreaRestrictionTestHelp.getGeoCoordinateDefault());//dummy
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.useWithinAreaAndLoacation();
		
		NtsAssert.businessException("Msg_2095",()-> domain.checkAreaStamp(require, contractCD, cId, sId,stampLocationInfor));
	}
	/*
	 * if @制限方法 <> エリア制限しない　&&　$勤務場所.isEmpty
	 * 
	 * */	
	@Test
	public void setValueFindAll() {
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.useWithinAreaAndLoacation();
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(StampingAreaRestrictionTestHelp.getGeoCoordinateDefault());//dummy
		new Expectations() {
			{
			require.findAll(contractCD);
			result = StampingAreaRestrictionTestHelp.createDataForFindAll("dummy", "dummy", "dummy");
			}	
		};
		Optional<WorkLocation> lisst = domain.checkAreaStamp(require, contractCD, cId, sId,stampLocationInfor);
		assertThat(lisst).isNotEmpty();
	}
	
	@Test
	public void setValueForWork() {
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.notUseAreanAndUseOnlyWorkplace();
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(StampingAreaRestrictionTestHelp.getGeoCoordinateDefault());//dummy
		new Expectations() {
			{
			require.findAll(contractCD);
			result = StampingAreaRestrictionTestHelp.createDataForFindAll("dummy", "dummy", "dummy");
			}	
		};
		Optional<WorkLocation> lisst = domain.checkAreaStamp(require, contractCD, cId, sId,stampLocationInfor);
		assertThat(lisst).isNotEmpty();
	}
	
	@Test
	public void setValueForWorkEmty() {
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.notUseAreanAndUseOnlyWorkplace();
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(StampingAreaRestrictionTestHelp.getGeoCoordinateDefault());//dummy
		new Expectations() {
			{
			require.findAll(contractCD);
			result = null;
			}	
		};
		Optional<WorkLocation> lisst = domain.checkAreaStamp(require, contractCD, cId, sId,stampLocationInfor);
		assertThat(lisst).isNotEmpty();
	}
	
	
}
