package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.AutoCreateStampCardNumberService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardCreateResult;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaLimit;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.RadiusAtr;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.StampMobilePossibleRange;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampHelper;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.WorkInformationStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.AssignmentMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.AudioType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonDisSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonLayoutType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonName;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonNameSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockAtr;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.CorrectionInterval;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.DisplaySettingsStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageComment;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ResultDisplayTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingDateTimeColorOfStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampButton;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageComment;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageName;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SupportWplSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone.SettingsSmartphoneStamp;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author chungnt
 *
 */
@RunWith(JMockit.class)
public class EnterStampFromSmartPhoneServiceTest {

	@Injectable
	private nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EnterStampFromSmartPhoneService.Require require;
	@Injectable
	private nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRepository stampingAreaRepository;
	@Injectable
	private nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository repository;
	@Injectable
	private nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.adapter.AcquireWorkLocationEmplAdapter adapter;

	/**
	 * require.getSmartphoneStampSetting() isPresent() resutl :
	 * BusinessException("Msg_1632")
	 */
	@Test
	public void test_settingSmartPhoneStampOpt_null() {
		
		EnterStampFromSmartPhoneService enterStampFromSmartPhoneService = new EnterStampFromSmartPhoneService();

		ContractCode contractCode = new ContractCode("DUMMY");
		String employeeId = "DUMMY";
		GeneralDateTime dateTime = GeneralDateTime.now();
		StampButton stampButton = new StampButton(new PageNo(1), new ButtonPositionNo(1));

		new Expectations() {
			{
				require.getSmartphoneStampSetting();
				result = Optional.ofNullable(null);
			}
		};

		NtsAssert.businessException("Msg_1632", () -> enterStampFromSmartPhoneService.create(require, "", contractCode,
				employeeId, dateTime, stampButton, Optional.empty(), null));

	}

	/**
	 * $ボタン詳細設定 = $スマホ打刻の打刻設定.ボタン詳細設定を取得する(打刻ボタン) == null resutl :
	 * BusinessException("Msg_1632")
	 */
	@Test
	public void test_buttonSettingOpt_null() {
		EnterStampFromSmartPhoneService enterStampFromSmartPhoneService = new EnterStampFromSmartPhoneService();

		ContractCode contractCode = new ContractCode("DUMMY");
		String employeeId = "DUMMY";
		GeneralDateTime dateTime = GeneralDateTime.now();
		StampButton stampButton = new StampButton(new PageNo(1), new ButtonPositionNo(1));
		GeoCoordinate geoCoordinate = new GeoCoordinate(1, 1);
		DisplaySettingsStampScreen displaySettingsStamScreen = new DisplaySettingsStampScreen(new CorrectionInterval(1),
				new SettingDateTimeColorOfStampScreen(new ColorCode("DUMMY")), new ResultDisplayTime(1));
		List<StampPageLayout> stampPageLayouts = new ArrayList<>();
		SettingsSmartphoneStamp settingsSmartphoneStamp = new SettingsSmartphoneStamp(contractCode.v(),
				displaySettingsStamScreen, stampPageLayouts, true, new StampingAreaRestriction(
						nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr.USE, StampingAreaLimit.NO_AREA_RESTRICTION));

		new Expectations() {
			{
				require.getSmartphoneStampSetting();
				result = Optional.ofNullable(settingsSmartphoneStamp);

				require.findAll(contractCode.v());
				result = Arrays.asList(new WorkLocation(null, new WorkLocationCD("TEST"), null,
						Optional.of(new StampMobilePossibleRange(RadiusAtr.M_1000, geoCoordinate)), null, null,null));
			}
		};

		NtsAssert.businessException("Msg_1632",
				() -> enterStampFromSmartPhoneService.create(require, "", contractCode, employeeId, dateTime,
						stampButton, Optional.of(geoCoordinate), new RefectActualResult(
								new WorkInformationStamp(null, null, Optional.of(new WorkLocationCD("DUMMY")), null),
								null, null, null)));

	}

	@Test
	public void test_buttonSettingOpt_geoCoordinate_null() {
		EnterStampFromSmartPhoneService enterStampFromSmartPhoneService = new EnterStampFromSmartPhoneService();

		ContractCode contractCode = new ContractCode("000000000000");
		String employeeId = "DUMMY";
		GeneralDateTime dateTime = GeneralDateTime.now();
		StampButton stampButton = new StampButton(new PageNo(1), new ButtonPositionNo(1));
		DisplaySettingsStampScreen displaySettingsStamScreen = new DisplaySettingsStampScreen(new CorrectionInterval(1),
				new SettingDateTimeColorOfStampScreen(new ColorCode("DUMMY")), new ResultDisplayTime(1));
		List<StampPageLayout> stampPageLayouts = new ArrayList<>();
		SettingsSmartphoneStamp settingsSmartphoneStamp = new SettingsSmartphoneStamp(contractCode.v(),
				displaySettingsStamScreen, stampPageLayouts, true, new StampingAreaRestriction(
						nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr.USE, StampingAreaLimit.NO_AREA_RESTRICTION));

		new Expectations() {
			{
				require.getSmartphoneStampSetting();
				result = Optional.ofNullable(settingsSmartphoneStamp);
			}
		};

		NtsAssert.businessException("Msg_2096",
				() -> enterStampFromSmartPhoneService.create(require, "", contractCode, employeeId, dateTime,
						stampButton, Optional.empty(), new RefectActualResult(
								new WorkInformationStamp(null, null, Optional.of(new WorkLocationCD("DUMMY")), null),
								null, null, null)));

	}

	/**
	 * $打刻する方法 = 打刻する方法#打刻する方法(ID認証, スマホ打刻)
	 */
	@Test
	public void test() {
		EnterStampFromSmartPhoneService enterStampFromSmartPhoneService = new EnterStampFromSmartPhoneService();

		ContractCode contractCode = new ContractCode("DUMMY");
		String employeeId = "DUMMY";
		GeneralDateTime dateTime = GeneralDateTime.now();
		StampButton stampButton = new StampButton(new PageNo(1), new ButtonPositionNo(1));
		GeoCoordinate geoCoordinate = new GeoCoordinate(1, 1);
		DisplaySettingsStampScreen displaySettingsStamScreen = new DisplaySettingsStampScreen(new CorrectionInterval(1),
				new SettingDateTimeColorOfStampScreen(new ColorCode("DUMMY")), new ResultDisplayTime(1));

		List<StampPageLayout> stampPageLayouts = new ArrayList<>();
		List<ButtonSettings> lstButtonSet = new ArrayList<>();

		StampType stampType = new StampType(true, EnumAdaptor.valueOf(0, GoingOutReason.class),
				EnumAdaptor.valueOf(0, SetPreClockArt.class), EnumAdaptor.valueOf(1, ChangeClockAtr.class),
				EnumAdaptor.valueOf(0, ChangeCalArt.class));

		lstButtonSet.add(new ButtonSettings(new ButtonPositionNo(1), NotUseAtr.USE,
				new ButtonDisSet(new ButtonNameSet(new ColorCode("DUMMY"), new ButtonName("DUMMY")),
						new ColorCode("DUMMY")),
				stampType, AudioType.NONE, Optional.of(SupportWplSet.USE_THE_STAMPED_WORKPLACE),
				Optional.of(AssignmentMethod.SELECT_AT_THE_TIME_OF_STAMPING)));

		lstButtonSet.add(new ButtonSettings(new ButtonPositionNo(1), NotUseAtr.USE,
				new ButtonDisSet(new ButtonNameSet(new ColorCode("DUMMY"), new ButtonName("DUMMY")),
						new ColorCode("DUMMY")),
				stampType, AudioType.NONE, Optional.of(SupportWplSet.USE_THE_STAMPED_WORKPLACE),
				Optional.of(AssignmentMethod.SELECT_AT_THE_TIME_OF_STAMPING)));

		lstButtonSet.add(new ButtonSettings(new ButtonPositionNo(1), NotUseAtr.USE,
				new ButtonDisSet(new ButtonNameSet(new ColorCode("DUMMY"), new ButtonName("DUMMY")),
						new ColorCode("DUMMY")),
				stampType, AudioType.NONE, Optional.of(SupportWplSet.USE_THE_STAMPED_WORKPLACE),
				Optional.of(AssignmentMethod.SELECT_AT_THE_TIME_OF_STAMPING)));

		stampPageLayouts.add(new StampPageLayout(new PageNo(1), new StampPageName("DUMMY"),
				new StampPageComment(new PageComment("DUMMY"), new ColorCode("DUMMY")), ButtonLayoutType.SMALL_8,
				lstButtonSet));

		SettingsSmartphoneStamp settingsSmartphoneStamp = new SettingsSmartphoneStamp(contractCode.v(),
				displaySettingsStamScreen, stampPageLayouts, true, new StampingAreaRestriction(
						nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr.NOT_USE, StampingAreaLimit.NO_AREA_RESTRICTION));

		new Expectations() {
			{
				require.getSmartphoneStampSetting();
				result = Optional.of(settingsSmartphoneStamp);
			}
		};

		StampCardCreateResult stampCardCreateResult = new StampCardCreateResult("1", Optional.empty());

		new MockUp<AutoCreateStampCardNumberService>() {
			@Mock
			public Optional<StampCardCreateResult> create(AutoCreateStampCardNumberService.Require require,
					String employeeID, StampMeans stampMeanss) {
				return Optional.of(stampCardCreateResult);
			}
		};

		TimeStampInputResult timeStampInputResult = enterStampFromSmartPhoneService.create(require, "", contractCode,
				employeeId, dateTime, stampButton, Optional.of(geoCoordinate),
				StampHelper.getRefectActualResultDefault());

		assertThat(timeStampInputResult.getAt()).isNotPresent();
		assertThat(timeStampInputResult.stampDataReflectResult.getAtomTask()).isNotNull();
	}
}
