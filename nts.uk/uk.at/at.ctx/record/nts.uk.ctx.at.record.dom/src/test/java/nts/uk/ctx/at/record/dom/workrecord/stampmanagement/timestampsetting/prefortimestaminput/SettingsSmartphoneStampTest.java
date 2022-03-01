package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.EmployeeStampingAreaRestrictionSettingHelper;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaLimit;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.adapter.AcquireWorkLocationEmplAdapter;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone.SettingsSmartphoneStamp;

/**
 * 
 * @author chungnt
 *
 */
@RunWith(JMockit.class)
public class SettingsSmartphoneStampTest {
	
	@Injectable
	private SettingsSmartphoneStamp.Require settingsSmartphoneStampRequire;
	@Injectable
	private WorkLocationRepository repository;
	@Injectable
	private AcquireWorkLocationEmplAdapter adapter;
	@Injectable
	private StampingAreaRestriction.Require stampingAreaRestrictionRequire;
	
	private static final String EMPLOYEE_ID = "employeeId";
	private static final String CONTRACT_CD = "contractCd";
	private static final String COMPANY_ID = "companyId";

	@Test
	public void getters() {
		SettingsSmartphoneStamp settingsSmartphoneStamp = SettingsSmartphoneStampHelper.getSettingsSmartphoneStampDefault();
		NtsAssert.invokeGetters(settingsSmartphoneStamp);
	}
	
	@Test
	public void setters() {
		SettingsSmartphoneStamp settingsSmartphoneStamp = SettingsSmartphoneStampHelper.getSettingsSmartphoneStampDefault();
		settingsSmartphoneStamp.setPageLayoutSettings(new ArrayList<StampPageLayout>());
		settingsSmartphoneStamp.setStampingAreaRestriction(new StampingAreaRestriction(nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr.NOT_USE, StampingAreaLimit.NO_AREA_RESTRICTION));
		assertThat(settingsSmartphoneStamp.getPageLayoutSettings()).isEmpty();
		assertThat(settingsSmartphoneStamp.getStampingAreaRestriction().getUseLocationInformation()).isEqualTo(nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr.NOT_USE);
		assertThat(settingsSmartphoneStamp.getStampingAreaRestriction().getStampingAreaLimit()).isEqualTo(StampingAreaLimit.NO_AREA_RESTRICTION);
	}
	
	@Test
	public void testLayoutSettingsNull() {
			SettingsSmartphoneStamp settingsSmartphoneStamp = SettingsSmartphoneStampHelper.getSettingsSmartphoneStampDefault();
		
		 Optional<ButtonSettings> optButtonSetting = settingsSmartphoneStamp.getDetailButtonSettings(new StampButton(new PageNo(1), new ButtonPositionNo(1)));
		 
		 assertThat(optButtonSetting).isEmpty();
	}
	
	@Test
	public void testLayoutSettings() {
		SettingsSmartphoneStamp settingsSmartphoneStamp = SettingsSmartphoneStampHelper.getSettingsSmartphoneStamp();
		
		Optional<ButtonSettings> optButtonSetting = settingsSmartphoneStamp.getDetailButtonSettings(new StampButton(new PageNo(1), new ButtonPositionNo(1)));
		Optional<ButtonSettings> optButtonSetting1 = SettingsSmartphoneStampHelper.getOPTButtonSeting();
		
		assertThat(optButtonSetting.get().getAudioType().value).isEqualTo(optButtonSetting1.get().getAudioType().value);
		assertThat(optButtonSetting.get().getButtonDisSet().getBackGroundColor().v()).isEqualTo(optButtonSetting1.get().getButtonDisSet().getBackGroundColor().v());
		assertThat(optButtonSetting.get().getButtonDisSet().getButtonNameSet()).isNotEqualTo(optButtonSetting1.get().getButtonDisSet().getButtonNameSet());
		assertThat(optButtonSetting.get().getButtonPositionNo()).isEqualTo(optButtonSetting1.get().getButtonPositionNo());
	}
	
	@Test
	public void testCheckCanStampAreasNotSetting() {
		
		SettingsSmartphoneStamp settingsSmartphoneStamp = SettingsSmartphoneStampHelper.getSettingsSmartphoneStampDefault();
		
		new Expectations() {
			{
				settingsSmartphoneStampRequire.findByEmployeeId(EMPLOYEE_ID);
				result = Optional.empty();
			}
		};
		
		Optional<WorkLocation> rs = settingsSmartphoneStamp.checkCanStampAreas(repository, adapter, settingsSmartphoneStampRequire, new ContractCode(CONTRACT_CD),COMPANY_ID, EMPLOYEE_ID, new GeoCoordinate(0, 0));
		
		assertThat(rs).isNotPresent();
	}
	
	@Test
	public void testCheckCanStampAreasHasSetting() {
		
		SettingsSmartphoneStamp settingsSmartphoneStamp = SettingsSmartphoneStampHelper.getSettingsSmartphoneStampDefault();
		
		new Expectations() {
			{
				settingsSmartphoneStampRequire.findByEmployeeId(EMPLOYEE_ID);
				result = Optional.of(EmployeeStampingAreaRestrictionSettingHelper.getStampDefault());
			}
		};
		
		Optional<WorkLocation> rs = settingsSmartphoneStamp.checkCanStampAreas(repository, adapter,
				settingsSmartphoneStampRequire, new ContractCode(CONTRACT_CD), COMPANY_ID, EMPLOYEE_ID,
				new GeoCoordinate(0, 0));
		
		assertThat(rs).isNotPresent();
	}
	
	
	@Test
	public void insert() {
		SettingsSmartphoneStamp settingsSmartphoneStamp = SettingsSmartphoneStampHelper.getSettingsSmartphoneStampDefault();
		StampPageLayout pageLayout = StampSettingPersonHelper.crePageLayout(1, 2);
		
		settingsSmartphoneStamp.addPage(pageLayout);
		assertThat(settingsSmartphoneStamp.getPageLayoutSettings().stream().filter(x -> x.getPageNo().v() == 2)).isNotEmpty();
	}
	
	@Test
	public void update_succes() {
		SettingsSmartphoneStamp settingsSmartphoneStamp = SettingsSmartphoneStampHelper.getSettingsSmartphoneStamp();
		StampPageLayout pageLayout = StampSettingPersonHelper.crePageLayout(1, 2);

		settingsSmartphoneStamp.updatePage(pageLayout);
		assertThat(settingsSmartphoneStamp.getPageLayoutSettings().stream().filter(x -> x.getStampPageName().v().equals("NEW_DUMMY2"))).isNotEmpty();
	}

	@Test
	public void delete() {
		SettingsSmartphoneStamp settingsSmartphoneStamp = SettingsSmartphoneStampHelper.getSettingsSmartphoneStamp();

		settingsSmartphoneStamp.deletePage();
		assertThat(settingsSmartphoneStamp.getPageLayoutSettings()).isEmpty();
	}
	
}
