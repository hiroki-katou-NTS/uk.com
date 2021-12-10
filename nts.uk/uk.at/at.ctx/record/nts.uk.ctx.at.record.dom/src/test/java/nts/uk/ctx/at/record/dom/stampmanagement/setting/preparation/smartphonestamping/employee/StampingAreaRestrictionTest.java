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
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.getStampDefault();
		NtsAssert.invokeGetters(domain);
	}
	
	@Test
	public void specifyAreaTest() {
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.specifyArea();
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(StampingAreaRestrictionTestHelp.getGeoCoordinateDefaultNo());//dummy
		NtsAssert.businessException("Msg_2096",()-> domain.checkAreaStamp(require, contractCD, cId, sId,stampLocationInfor));
	}	
	
	@Test
	public void check1() {
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(StampingAreaRestrictionTestHelp.getGeoCoordinateDefault());//dummy
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.check2();
		Optional<WorkLocation> result  =  domain.checkAreaStamp(require, contractCD, cId, sId,stampLocationInfor );
		assertThat(result).isEmpty();
		
	}	
	@Test
	public void check2() {
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(StampingAreaRestrictionTestHelp.getGeoCoordinateDefault());//dummy
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.check1();
		
		new Expectations() {
			{
				require.getWorkPlaceOfEmpl(sId,GeneralDate.today());
				
			}	
		};
		NtsAssert.businessException("Msg_427",()-> domain.checkAreaStamp(require, contractCD, cId, sId,stampLocationInfor));

	}	
	
	//check throw 2059
	@Test
	public void check3() {
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(StampingAreaRestrictionTestHelp.getGeoCoordinateDefault());//dummy
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.check1();
		
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
	
	@Test
	public void check4() {
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(StampingAreaRestrictionTestHelp.getGeoCoordinateDefault());//dummy
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.check1_2();
		
		NtsAssert.businessException("Msg_2095",()-> domain.checkAreaStamp(require, contractCD, cId, sId,stampLocationInfor));
	}
	
	@Test
	public void checkSetValue() {
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.check1_2();
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
	public void check5() {
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(StampingAreaRestrictionTestHelp.getGeoCoordinateDefault());//dummy
		StampingAreaRestriction domain = StampingAreaRestrictionTestHelp.check1_3();
		Optional<WorkLocation> result  =  domain.checkAreaStamp(require, contractCD, cId, sId,stampLocationInfor );
		assertThat(result).isEmpty();
	}
}
