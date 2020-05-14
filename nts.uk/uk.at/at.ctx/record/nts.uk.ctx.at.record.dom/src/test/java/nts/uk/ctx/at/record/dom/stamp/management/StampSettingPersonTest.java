package nts.uk.ctx.at.record.dom.stamp.management;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.Button;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.Comment;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonLayoutType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageName;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.StampScreen;

/**
 * 
 * @author phongtq
 *
 */
@RunWith(JMockit.class)
public class StampSettingPersonTest {


//	@Test
//	public void testGetButtonSet_false() {
//
//		StampSettingPerson settingPerson = StampSettingPersonHelper.DUMMY;
//		assertThat(settingPerson.getButtonSet(0, 0)).isNotPresent();
//	}
//
//	@Test
//	public void testGetButtonSet_succes() {
//
//		StampSettingPerson settingPerson = StampSettingPersonHelper.DUMMY;
//		assertThat(settingPerson.getButtonSet(1, 1)).isPresent();
//	}
//
//	@Test
//	public void testGetButtonSet_succes2() {
//
//		StampSettingPerson settingPerson = StampSettingPersonHelper.DUMMY;
//		assertThat(settingPerson.getButtonSet(1, 0)).isNotPresent();
//	}


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
		assertThat(settingPerson.getLstStampPageLayout().stream().filter(x -> x.getStampPageName().v().equals("NEW_DUMMY2"))).isNotEmpty();
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
		StampSettingPerson person = new StampSettingPerson("000000000000-0001", true, StampScreen.DUMMY, null,null);
		NtsAssert.invokeGetters(person);
	}
}
