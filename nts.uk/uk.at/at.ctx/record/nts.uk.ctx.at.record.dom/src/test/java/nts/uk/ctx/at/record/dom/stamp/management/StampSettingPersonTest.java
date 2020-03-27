package nts.uk.ctx.at.record.dom.stamp.management;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonLayoutType;
import nts.uk.ctx.at.record.dom.stamp.management.PageNo;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageLayout;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageName;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPerson;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.Button;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.Comment;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.StampScreen;

/**
 * 
 * @author phongtq
 *
 */
@RunWith(JMockit.class)
public class StampSettingPersonTest {

	@Test
	public void testGetButtonSet_false() {

		StampSettingPerson settingPerson = StampSettingPersonHelper.DUMMY;
		assertThat(settingPerson.getButtonSet(0, 0)).isNotPresent();
	}

	@Test
	public void testGetButtonSet_succes() {

		StampSettingPerson settingPerson = StampSettingPersonHelper.DUMMY;
		assertThat(settingPerson.getButtonSet(1, 1)).isPresent();
	}

	@Test
	public void testGetButtonSet_succes2() {

		StampSettingPerson settingPerson = StampSettingPersonHelper.DUMMY;
		assertThat(settingPerson.getButtonSet(1, 0)).isNotPresent();
	}

	@Test
	public void testGetButtonSet_filter() {

		StampSettingPerson settingPerson = new StampSettingPerson("000000000000-0001", // dummy
				true, // dummy
				StampScreen.DUMMY, // dummy
				Arrays.asList(new StampPageLayout(new PageNo(1), new StampPageName("DUMMY"), Comment.DUMMY,
						EnumAdaptor.valueOf(0, ButtonLayoutType.class), Arrays.asList(Button.DUMMY))));

		assertThat(settingPerson.getLstStampPageLayout().get(0).getLstButtonSet())
				.extracting(b -> b.getButtonPositionNo().v(),
						b -> b.getButtonDisSet().getButtonNameSet().getButtonName().get().v(),
						b -> b.getButtonDisSet().getButtonNameSet().getTextColor().v(),
						b -> b.getButtonDisSet().getBackGroundColor().v(),
						b -> b.getButtonType().getStampType().get().isChangeHalfDay(),
						b -> b.getButtonType().getStampType().get().getChangeClockArt().value,
						b -> b.getButtonType().getStampType().get().getChangeCalArt().value,
						b -> b.getButtonType().getStampType().get().getGoOutArt().get().value,
						b -> b.getButtonType().getStampType().get().getSetPreClockArt().value, b -> b.getUsrArt().value,
						b -> b.getAudioType().value)
				.containsExactly(tuple(1, "DUMMY", "DUMMY", "DUMMY", true, 0, 0, 0, 0, 0, 0)).first().isNotNull();
	}

	@Test
	public void testGetButtonSet_filter_second() {

		StampSettingPerson settingPerson = new StampSettingPerson("000000000000-0001", // dummy
				true, // dummy
				StampScreen.DUMMY, Arrays.asList(Layout.DUMMY));

		assertThat(settingPerson.getLstStampPageLayout())
				.extracting(b -> b.getPageNo().v(), b -> b.getStampPageName().v(),
						b -> b.getStampPageComment().getPageComment().v(),
						b -> b.getStampPageComment().getCommentColor().v(), b -> b.getButtonLayoutType().value)
				.containsExactly(tuple(1, "DUMMY", "DUMMY", "DUMMY", 0));
	}

	@Test
	public void insert() {

		StampSettingPerson settingPerson = StampSettingPersonHelper.settingPerson();
		StampPageLayout pageLayout = StampSettingPersonHelper.crePageLayout(2, 3);

		settingPerson.insert(pageLayout);
		assertThat(settingPerson.getLstStampPageLayout().stream().filter(x -> x.getPageNo().v() == 3)).isNotEmpty();
	}
	
	@Test
	public void update_succes() {

		StampSettingPerson settingPerson = StampSettingPersonHelper.settingPerson();
		StampPageLayout pageLayout = StampSettingPersonHelper.crePageLayout(1, 1);

		settingPerson.update(pageLayout);
		assertThat(settingPerson.getLstStampPageLayout().stream().filter(x -> x.getPageNo().v() == 1)).isNotEmpty();
	}

	@Test
	public void delete() {

		StampSettingPerson settingPerson = StampSettingPersonHelper.settingPerson();

		settingPerson.delete(1);
		assertThat(settingPerson.getLstStampPageLayout().stream().filter(x -> x.getPageNo().v() == 1)).isEmpty();
	}

	@Test
	public void getters() {
		StampSettingPerson settingPerson = StampSettingPersonHelper.DUMMY;
		NtsAssert.invokeGetters(settingPerson);
	}

	@Test
	public void cons() {
		StampSettingPerson person = new StampSettingPerson("000000000000-0001", true, StampScreen.DUMMY);
		NtsAssert.invokeGetters(person);
	}
}
