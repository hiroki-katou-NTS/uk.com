package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.PasswordForRICOH;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopier;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class StampSettingOfRICOHCopierHelper {

	public static StampSettingOfRICOHCopier CreateStampSettingOfRICOHCopier() {
		List<ButtonSettings> buttonSettings = new ArrayList<>();
		
		StampType stampType = new StampType(
				true, 
				EnumAdaptor.valueOf(0, GoingOutReason.class), 
				EnumAdaptor.valueOf(0, SetPreClockArt.class), 
				EnumAdaptor.valueOf(1, ChangeClockAtr.class),
				EnumAdaptor.valueOf(0, ChangeCalArt.class));
		
		buttonSettings.add(new ButtonSettings(new ButtonPositionNo(1),
				NotUseAtr.NOT_USE,
				new ButtonDisSet(new ButtonNameSet(new ColorCode("DUMMY"), new ButtonName("DUMMY")), new ColorCode("DUMMY")),
				stampType,
				AudioType.GOOD_JOB,
				Optional.of(SupportWplSet.SELECT_AT_THE_TIME_OF_STAMPING),
				Optional.of(AssignmentMethod.SELECT_AT_THE_TIME_OF_STAMPING)));
		
		List<StampPageLayout> pageLayoutSettings = new ArrayList<StampPageLayout>(); 
		
		pageLayoutSettings.add(new StampPageLayout(new PageNo(1),
				new StampPageName("PAGE 1"),
				new StampPageComment(new PageComment("DUMMY"), new ColorCode("DUMMY")),
				ButtonLayoutType.LARGE_2_SMALL_4,
				buttonSettings));
		pageLayoutSettings.add(new StampPageLayout(new PageNo(2),
				new StampPageName("PAGE 2"),
				new StampPageComment(new PageComment("DUMMY"), new ColorCode("DUMMY")),
				ButtonLayoutType.LARGE_2_SMALL_4,
				buttonSettings));
		
		return new StampSettingOfRICOHCopier("000000", new PasswordForRICOH("0"), pageLayoutSettings, 
				new DisplaySettingsStampScreen(new CorrectionInterval(1), 
						new SettingDateTimeColorOfStampScreen(new ColorCode("DUMMY")),
						new ResultDisplayTime(1)
					)
			);
	}
}
