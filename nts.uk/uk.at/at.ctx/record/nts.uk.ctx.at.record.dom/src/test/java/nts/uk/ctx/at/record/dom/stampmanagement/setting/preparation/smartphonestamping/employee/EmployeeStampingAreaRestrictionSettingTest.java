package nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction.Require;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;

@RunWith(JMockit.class)
public class EmployeeStampingAreaRestrictionSettingTest {
	@Injectable
	private Require require;

	@Test
	public void getters() {	
		EmployeeStampingAreaRestrictionSetting domain = EmployeeStampingAreaRestrictionSettingHelper.getStampDefault();
		NtsAssert.invokeGetters(domain);
	}
	/*
	 * 
	 *return @打刻エリア制限.打刻してもいいエリアかチェックする(require,契約コード,会社ID
	 * */
	@Test
	public void checkAreaStampAgroot() {
		StampingAreaRestriction domain = EmployeeStampingAreaRestrictionSettingHelper.createStamp();
		EmployeeStampingAreaRestrictionSetting areaRestrictionSetting = new EmployeeStampingAreaRestrictionSetting("dummy", domain);
		Optional<GeoCoordinate> stampLocationInfor = Optional.ofNullable(getGeoCoordinateDefault());//dummy
		Optional<WorkLocation> worklocation = areaRestrictionSetting.checkAreaStamp(
																					require,
																					EmployeeStampingAreaRestrictionSettingHelper.CONTRACTCD,
																					EmployeeStampingAreaRestrictionSettingHelper.CID,
																					EmployeeStampingAreaRestrictionSettingHelper.SID,
																					stampLocationInfor);
		assertThat(worklocation).isEmpty();
	}
	
	public static GeoCoordinate getGeoCoordinateDefault() {
		return new GeoCoordinate(1, 2);
		
	}
	
}
