package nts.uk.ctx.at.record.dom.stamp.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.stamp.management.AudioType;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonDisSet;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonLayoutType;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonName;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonNameSet;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonSettings;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonType;
import nts.uk.ctx.at.record.dom.stamp.management.ChangeCalArt;
import nts.uk.ctx.at.record.dom.stamp.management.ChangeClockArt;
import nts.uk.ctx.at.record.dom.stamp.management.ColorSetting;
import nts.uk.ctx.at.record.dom.stamp.management.CorrectionInterval;
import nts.uk.ctx.at.record.dom.stamp.management.GoingOutReason;
import nts.uk.ctx.at.record.dom.stamp.management.HistoryDisplayMethod;
import nts.uk.ctx.at.record.dom.stamp.management.PageComment;
import nts.uk.ctx.at.record.dom.stamp.management.PageNo;
import nts.uk.ctx.at.record.dom.stamp.management.ReservationArt;
import nts.uk.ctx.at.record.dom.stamp.management.ResultDisplayTime;
import nts.uk.ctx.at.record.dom.stamp.management.SetPreClockArt;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageComment;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageLayout;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageName;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPerson;
import nts.uk.ctx.at.record.dom.stamp.management.StampType;
import nts.uk.ctx.at.record.dom.stamp.management.StampingScreenSet;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.Button;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.ButtonSet;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.Comment;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.Type;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
					new ColorCode("DUMMY"), new ButtonName("DUMMY"));
		}
		
		public static class Type {
			public static ButtonType DUMMY = new ButtonType(
					EnumAdaptor.valueOf(0, ReservationArt.class), 
					Stamp.DUMMY);
		}
		
		public static class Stamp {
			public static StampType DUMMY = new StampType(
					true, 
					EnumAdaptor.valueOf(0, GoingOutReason.class), 
					EnumAdaptor.valueOf(0, SetPreClockArt.class),
					EnumAdaptor.valueOf(1, ChangeClockArt.class),
					EnumAdaptor.valueOf(0, ChangeCalArt.class));
		}
	}
	
	public static StampSettingPerson settingPerson(){
		List<StampPageLayout> lstStampPage = new ArrayList<>();
		List<ButtonSettings> lstButtonSet = new ArrayList<>();

		lstButtonSet.add(new ButtonSettings(
				new ButtonPositionNo(1), 
				ButtonSet.DUMMY, 
				Type.DUMMY,
				EnumAdaptor.valueOf(0, NotUseAtr.class), 
				EnumAdaptor.valueOf(0, AudioType.class)));

		lstStampPage.add(new StampPageLayout(
				new PageNo(1), 
				new StampPageName("DUMMY"),
				new StampPageComment(
						new PageComment("DUMMY"), 
						new ColorCode("DUMMY")),
				EnumAdaptor.valueOf(0, ButtonLayoutType.class), 
				lstButtonSet));
		
		lstStampPage.add(new StampPageLayout(
				new PageNo(2), 
				new StampPageName("DUMMY"),
				new StampPageComment(
						new PageComment("DUMMY"), 
						new ColorCode("DUMMY")),
				EnumAdaptor.valueOf(0, ButtonLayoutType.class), 
				lstButtonSet));
		
		lstStampPage.add(new StampPageLayout(
				new PageNo(4), 
				new StampPageName("DUMMY"),
				new StampPageComment(
						new PageComment("DUMMY"), 
						new ColorCode("DUMMY")),
				EnumAdaptor.valueOf(0, ButtonLayoutType.class), 
				lstButtonSet));


		StampSettingPerson settingPerson = new StampSettingPerson(
				"000000000000-0001", 
				true, 
				StampScreen.DUMMY,
				lstStampPage);
		return settingPerson;
	}
	
	public static StampPageLayout crePageLayout(int buttonPositionNo,int pageNo){
		List<ButtonSettings> lstButtonSetAdd = new ArrayList<>();
		lstButtonSetAdd.add(new ButtonSettings(
				new ButtonPositionNo(buttonPositionNo), 
				ButtonSet.DUMMY, 
				Type.DUMMY,
				EnumAdaptor.valueOf(0, NotUseAtr.class), 
				EnumAdaptor.valueOf(0, AudioType.class)));
		StampPageLayout pageLayout = new StampPageLayout(
				new PageNo(pageNo), 
				new StampPageName("NEW_DUMMY2"),
				new StampPageComment(
						new PageComment("DUMMY"), 
						new ColorCode("DUMMY")),
				EnumAdaptor.valueOf(0, ButtonLayoutType.class), 
				lstButtonSetAdd);
		return pageLayout;
	}
 

}
