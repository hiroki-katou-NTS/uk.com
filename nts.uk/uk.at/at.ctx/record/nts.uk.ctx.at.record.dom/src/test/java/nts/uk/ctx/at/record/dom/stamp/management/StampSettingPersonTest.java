package nts.uk.ctx.at.record.dom.stamp.management;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.Button;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.ButtonSet;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.Comment;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.Type;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.StampScreen;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author phongtq
 *
 */
@RunWith(JMockit.class)
public class StampSettingPersonTest {

	@Test
	public void testGetButtonSet_false() {

		StampSettingPerson settingPerson = new StampSettingPerson(
				"000000000000-0001", // dummy
				true, // dummy
				StampScreen.DUMMY,
				Arrays.asList(new StampPageLayout(
						new PageNo(1), 				
						new StampPageName("DUMMY"), // dummy
						Comment.DUMMY,				// dummy
						EnumAdaptor.valueOf(0, ButtonLayoutType.class),	// dummy 
						Arrays.asList(new ButtonSettings(
							new ButtonPositionNo(1), 
							ButtonSet.DUMMY, 
							Type.DUMMY, 
							EnumAdaptor.valueOf(0, NotUseAtr.class),
							EnumAdaptor.valueOf(0, AudioType.class))))));
		
		assertThat(settingPerson.getButtonSet(0, 0)).isNotPresent();
	}
	
	@Test
	public void testGetButtonSet_succes() {

		StampSettingPerson settingPerson = new StampSettingPerson(
				"000000000000-0001", // dummy
				true, // dummy
				StampScreen.DUMMY,
				Arrays.asList(new StampPageLayout(
						new PageNo(1), 				
						new StampPageName("DUMMY"), 
						Comment.DUMMY,				
						EnumAdaptor.valueOf(0, ButtonLayoutType.class),	// dummy 
						Arrays.asList(new ButtonSettings(
							new ButtonPositionNo(1), 
							ButtonSet.DUMMY, 
							Type.DUMMY, 
							EnumAdaptor.valueOf(0, NotUseAtr.class),
							EnumAdaptor.valueOf(0, AudioType.class))))));
		assertThat(settingPerson.getButtonSet(1, 1)).isPresent();
	}
	
	@Test
	public void testGetButtonSet_succes2() {

		StampSettingPerson settingPerson = new StampSettingPerson(
				"000000000000-0001", // dummy
				true, // dummy
				StampScreen.DUMMY,
				Arrays.asList(new StampPageLayout(
						new PageNo(1), 				
						new StampPageName("DUMMY"), 
						Comment.DUMMY,				
						EnumAdaptor.valueOf(0, ButtonLayoutType.class),	// dummy 
						Arrays.asList(new ButtonSettings(
							new ButtonPositionNo(1), 
							ButtonSet.DUMMY, 
							Type.DUMMY, 
							EnumAdaptor.valueOf(0, NotUseAtr.class),
							EnumAdaptor.valueOf(0, AudioType.class))))));
		assertThat(settingPerson.getButtonSet(1, 0)).isNotPresent();
	}


	@Test
	public void testGetButtonSet_filter() {
		
		StampSettingPerson settingPerson = new StampSettingPerson(
				"000000000000-0001", //dummy
				true, 				 //dummy
				StampScreen.DUMMY,	 //dummy
				Arrays.asList(new StampPageLayout(
					new PageNo(1), 
					new StampPageName("DUMMY"), 
					Comment.DUMMY, 
					EnumAdaptor.valueOf(0, ButtonLayoutType.class),
					Arrays.asList(Button.DUMMY))));

		assertThat(settingPerson.getLstStampPageLayout().get(0).getLstButtonSet())
				.extracting(b -> b.getButtonPositionNo().v(),
						b -> b.getButtonDisSet().getButtonNameSet().getButtonName().get().v(),
						b -> b.getButtonDisSet().getButtonNameSet().getTextColor().v(),
						b -> b.getButtonDisSet().getBackGroundColor().v(),
						b -> b.getButtonType().getStampType().isChangeHalfDay(),
						b -> b.getButtonType().getStampType().getChangeClockArt().value,
						b -> b.getButtonType().getStampType().getChangeCalArt().value,
						b -> b.getButtonType().getStampType().getGoOutArt().get().value,
						b -> b.getButtonType().getStampType().getSetPreClockArt().value, 
						b -> b.getUsrArt().value,
						b -> b.getAudioType().value)
				.containsExactly(tuple(1, "DUMMY", "DUMMY", "DUMMY", true, 0, 0, 0, 0, 0, 0)).first().isNotNull();
	}
	
	@Test
	public void testGetButtonSet_filter_second() {
		
		StampSettingPerson settingPerson = new StampSettingPerson(
				"000000000000-0001", //dummy
				true, 				 //dummy
				StampScreen.DUMMY,	 
				Arrays.asList(Layout.DUMMY));

		assertThat(settingPerson.getLstStampPageLayout())
				.extracting(b -> b.getPageNo().v(),
						b -> b.getStampPageName().v(),
						b -> b.getStampPageComment().getPageComment().v(),
						b -> b.getStampPageComment().getCommentColor().v(),
						b -> b.getButtonLayoutType().value)
				.containsExactly(tuple(1, "DUMMY", "DUMMY", "DUMMY", 0));
	}
	

	@Test
	public void insert() {
		
		List<StampPageLayout> lstStampPage = new ArrayList<>();
		List<ButtonSettings> lstButtonSet = new ArrayList<>();
		List<ButtonSettings> lstButtonSetAdd = new ArrayList<>();

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

		lstButtonSetAdd.add(new ButtonSettings(
				new ButtonPositionNo(2), 
				ButtonSet.DUMMY, 
				Type.DUMMY,
				EnumAdaptor.valueOf(0, NotUseAtr.class), 
				EnumAdaptor.valueOf(0, AudioType.class)));

		StampSettingPerson settingPerson = new StampSettingPerson(
				"000000000000-0001", 
				true, 
				StampScreen.DUMMY,
				lstStampPage);
		
		StampPageLayout pageLayout = new StampPageLayout(
				new PageNo(2), 
				new StampPageName("DUMMY2"),
				new StampPageComment(
						new PageComment("DUMMY"), 
						new ColorCode("DUMMY")),
				EnumAdaptor.valueOf(0, ButtonLayoutType.class), 
				lstButtonSetAdd);
		settingPerson.insert(pageLayout);
	}

	@Test
	public void update_succes() {
		
		List<StampPageLayout> lstStampPage = new ArrayList<>();
		List<ButtonSettings> lstButtonSet = new ArrayList<>();
		List<ButtonSettings> lstButtonSetAdd = new ArrayList<>();

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

		lstButtonSetAdd.add(new ButtonSettings(
				new ButtonPositionNo(2), 
				ButtonSet.DUMMY, Type.DUMMY,
				EnumAdaptor.valueOf(0, NotUseAtr.class), 
				EnumAdaptor.valueOf(0, AudioType.class)));

		StampSettingPerson settingPerson = new StampSettingPerson(
				"000000000000-0001", 
				true, 
				StampScreen.DUMMY,
				lstStampPage);
		StampPageLayout pageLayout = new StampPageLayout(
				new PageNo(3), 
				new StampPageName("DUMMY2"),
				new StampPageComment(
						new PageComment("DUMMY"), 
						new ColorCode("DUMMY")),
				EnumAdaptor.valueOf(0, ButtonLayoutType.class), 
				lstButtonSetAdd);

		settingPerson.update(pageLayout);
	}
	

	@Test
	public void update_fasle() {
		
		List<StampPageLayout> lstStampPage = new ArrayList<>();
		List<ButtonSettings> lstButtonSet = new ArrayList<>();
		List<ButtonSettings> lstButtonSetAdd = new ArrayList<>();

		lstButtonSet.add(new ButtonSettings(
				new ButtonPositionNo(1), 
				ButtonSet.DUMMY, 
				Type.DUMMY,
				EnumAdaptor.valueOf(0, NotUseAtr.class), 
				EnumAdaptor.valueOf(0, AudioType.class)));

		lstStampPage.add(new StampPageLayout(
				new PageNo(2), 
				new StampPageName("DUMMY"),
				new StampPageComment(
						new PageComment("DUMMY"), 
						new ColorCode("DUMMY")),
				EnumAdaptor.valueOf(0, ButtonLayoutType.class), 
				lstButtonSet));
		
		lstStampPage.add(new StampPageLayout(
				new PageNo(1 ), 
				new StampPageName("DUMMY"),
				new StampPageComment(
						new PageComment("DUMMY"), 
						new ColorCode("DUMMY")),
				EnumAdaptor.valueOf(0, ButtonLayoutType.class), 
				lstButtonSet));

		lstButtonSetAdd.add(new ButtonSettings(
				new ButtonPositionNo(3), 
				ButtonSet.DUMMY, 
				Type.DUMMY,
				EnumAdaptor.valueOf(0, NotUseAtr.class), 
				EnumAdaptor.valueOf(0, AudioType.class)));

		StampSettingPerson settingPerson = new StampSettingPerson(
				"000000000000-0001", 
				true, 
				StampScreen.DUMMY,
				lstStampPage);
		
		StampPageLayout pageLayout = new StampPageLayout(
				new PageNo(1), 
				new StampPageName("DUMMY2"),
				new StampPageComment(
						new PageComment("DUMMY"), 
						new ColorCode("DUMMY")),
				EnumAdaptor.valueOf(0, ButtonLayoutType.class), 
				lstButtonSetAdd);
		settingPerson.update(pageLayout);
	}
	
	@Test
	public void delete() {
		
		List<StampPageLayout> lstStampPage = new ArrayList<>();
		List<ButtonSettings> lstButtonSet = new ArrayList<>();
		List<ButtonSettings> lstButtonSetAdd = new ArrayList<>();

		lstButtonSet.add(new ButtonSettings(
				new ButtonPositionNo(1), 
				ButtonSet.DUMMY, Type.DUMMY,
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

		lstButtonSetAdd.add(new ButtonSettings(
				new ButtonPositionNo(2), 
				ButtonSet.DUMMY, Type.DUMMY,
				EnumAdaptor.valueOf(0, NotUseAtr.class), 
				EnumAdaptor.valueOf(0, AudioType.class)));

		StampSettingPerson settingPerson = new StampSettingPerson(
				"000000000000-0001", 
				true, 
				StampScreen.DUMMY,
				lstStampPage);


		settingPerson.delete(1);
	}

	@Test
	public void getters() {
		StampSettingPerson settingPerson = StampSettingPersonHelper.DUMMY;
		NtsAssert.invokeGetters(settingPerson);
	}
	
	@Test
	public void cons(){
		StampSettingPerson person = new StampSettingPerson("000000000000-0001", 
				true, 
				StampScreen.DUMMY);
		NtsAssert.invokeGetters(person);
	}
}
