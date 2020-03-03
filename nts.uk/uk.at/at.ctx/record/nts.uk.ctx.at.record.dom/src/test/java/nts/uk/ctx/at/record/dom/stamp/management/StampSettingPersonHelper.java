package nts.uk.ctx.at.record.dom.stamp.management;

import java.util.Arrays;
import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;

/**
 * 
 * @author phongtq
 *
 */
public class StampSettingPersonHelper {
	
	public static StampSettingPerson DUMMY = new StampSettingPerson(
			"000000000000-0001", 
			true, 
			StampScreen.DUMMY, 
			Arrays.asList(Layout.DUMMY));
	
	public static class StampScreen {
		
		public static StampingScreenSet DUMMY = new StampingScreenSet(
				EnumAdaptor.valueOf(0, HistoryDisplayMethod.class), 
				new CorrectionInterval(1), 
				Color.DUMMY, 
				new ResultDisplayTime(1));
		
		public static class Color {
			public static ColorSetting DUMMY = new ColorSetting(new ColorCode("DUMMY"), new ColorCode("DUMMY"));
		}
		
	}
	
	public static class Layout {
		public static StampPageLayout DUMMY = new StampPageLayout(
				new PageNo(1), 
				new StampPageName("DUMMY"), 
				Comment.DUMMY, 
				EnumAdaptor.valueOf(0, ButtonLayoutType.class),
				Arrays.asList(Button.DUMMY));
		public static class Comment{
			public static StampPageComment DUMMY = new StampPageComment(new PageComment("DUMMY"), new ColorCode("DUMMY"));
		} 
		
		public static class Button {
			public static ButtonSettings DUMMY = new ButtonSettings(
					new ButtonPositionNo(1), 
					ButtonSet.DUMMY, 
					Type.DUMMY, 
					EnumAdaptor.valueOf(0, NotUseAtr.class),
					EnumAdaptor.valueOf(0, AudioType.class));
		}
		
		public static class ButtonSet {
			public static ButtonDisSet DUMMY = new ButtonDisSet(
					ButtonNameSetting.DUMMY, 
					new ColorCode("DUMMY"));
		}
		
		public static class ButtonNameSetting {
			public static ButtonNameSet DUMMY = new ButtonNameSet(
					new ColorCode("DUMMY"), Optional.of(new ButtonName("DUMMY")));
		}
		
		public static class Type {
			public static ButtonType DUMMY = new ButtonType(
					EnumAdaptor.valueOf(0, ReservationArt.class), 
					Stamp.DUMMY);
		}
		
		public static class Stamp {
			public static StampType DUMMY = new StampType(
					true, 
					Optional.of(EnumAdaptor.valueOf(0, GoingOutReason.class)), 
					EnumAdaptor.valueOf(0, SetPreClockArt.class),
					EnumAdaptor.valueOf(0, ChangeClockArt.class),
					EnumAdaptor.valueOf(0, ChangeCalArt.class));
		}
	}

}
