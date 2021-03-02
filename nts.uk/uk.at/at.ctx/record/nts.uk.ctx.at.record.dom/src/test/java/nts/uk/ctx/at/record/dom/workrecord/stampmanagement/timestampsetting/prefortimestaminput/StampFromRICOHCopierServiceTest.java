package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampHelper;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampFromRICOHCopierService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampFromRICOHCopierService.Require;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopier;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class StampFromRICOHCopierServiceTest {
	
	@Injectable
	private Require require;
	
	/**
	 * employeeId is null
	 */
	@Test
	public void testStampFromRICOHCopierService_1() {
		
		new Expectations() {
			{
				require.getStampSettingOfRICOHCopier("000002");
				result = Optional.empty();
			}
		};
		
		NtsAssert.businessException("Msg_1632", () -> StampFromRICOHCopierService.create(
				require, 
				"000002", 
				new ContractCode("000"), 
				"000", 
				GeneralDateTime.fromString("2020-02-02 20:00:00.0", "yyyy-MM-dd HH:mm:ss.s"), 
				new StampButton(new PageNo(1), new ButtonPositionNo(1)), 
				StampHelper.getRefectActualResultDefault()));
	}
	
	@Test
	public void testStampFromRICOHCopierService_2() {
		
		StampSettingOfRICOHCopier stampSettingOfRICOHCopier = StampSettingOfRICOHCopierHelper.CreateStampSettingOfRICOHCopier();
		List<ButtonSettings> buttonSettings = new ArrayList<>();
		
		buttonSettings.add(new ButtonSettings(new ButtonPositionNo(1),
				new ButtonDisSet(new ButtonNameSet(new ColorCode("DUMMY"), new ButtonName("DUMMY")), new ColorCode("DUMMY")),
				new ButtonType(ReservationArt.CANCEL_RESERVATION, Optional.empty()),
				NotUseAtr.NOT_USE,
				AudioType.GOOD_JOB,
				Optional.of(SupportWplSet.SELECT_AT_THE_TIME_OF_STAMPING)));
		
		stampSettingOfRICOHCopier.addPage(new StampPageLayout(new PageNo(1),
				new StampPageName("DUMMY"),
				new StampPageComment(new PageComment("DUMMY"), new ColorCode("DUMMY")),
				ButtonLayoutType.LARGE_2_SMALL_4,
				buttonSettings));	
		
		new Expectations() {
			{
				require.getStampSettingOfRICOHCopier("000001");
				result = Optional.of(stampSettingOfRICOHCopier);
			}
		};
		
		NtsAssert.businessException("Msg_1632", () -> StampFromRICOHCopierService.create(
				require, 
				"000001", 
				new ContractCode("000"), 
				"000", 
				GeneralDateTime.fromString("2020-02-02 20:00:00.0", "yyyy-MM-dd HH:mm:ss.s"), 
				new StampButton(new PageNo(1), new ButtonPositionNo(2)), 
				StampHelper.getRefectActualResultDefault()));
	}
	
	@Test
	public void testStampFromRICOHCopierService_3() {
		
		StampSettingOfRICOHCopier stampSettingOfRICOHCopier = StampSettingOfRICOHCopierHelper.CreateStampSettingOfRICOHCopier();
		List<ButtonSettings> buttonSettings = new ArrayList<>();
		
		buttonSettings.add(new ButtonSettings(new ButtonPositionNo(1),
				new ButtonDisSet(new ButtonNameSet(new ColorCode("DUMMY"), new ButtonName("DUMMY")), new ColorCode("DUMMY")),
				new ButtonType(ReservationArt.CANCEL_RESERVATION, Optional.empty()),
				NotUseAtr.NOT_USE,
				AudioType.GOOD_JOB,
				Optional.of(SupportWplSet.SELECT_AT_THE_TIME_OF_STAMPING)));
		
		stampSettingOfRICOHCopier.addPage(new StampPageLayout(new PageNo(1),
				new StampPageName("DUMMY"),
				new StampPageComment(new PageComment("DUMMY"), new ColorCode("DUMMY")),
				ButtonLayoutType.LARGE_2_SMALL_4,
				buttonSettings));	
		
		new Expectations() {
			{
				require.getStampSettingOfRICOHCopier("000001");
				result = Optional.of(stampSettingOfRICOHCopier);
			}
		};
		
		StampFromRICOHCopierService.create(
				require, 
				"000001", 
				new ContractCode("000"), 
				"000", 
				GeneralDateTime.fromString("2020-02-02 20:00:00.0", "yyyy-MM-dd HH:mm:ss.s"), 
				new StampButton(new PageNo(1), new ButtonPositionNo(1)), 
				StampHelper.getRefectActualResultDefault());
	}
	
}
