package nts.uk.ctx.at.record.dom.stamp.management;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.Stamp;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.Type;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ReservationArt;

public class ButtonTypeTest {
	
	@Test
	public void getters() {
		ButtonType buttonType = Type.DUMMY;
		NtsAssert.invokeGetters(buttonType);
	}
	
	@Test
	public void checkStampTypeFalse(){
		ButtonType buttonType = new ButtonType(
				EnumAdaptor.valueOf(0, ReservationArt.class), //dummy
				null);
		assertThat(buttonType.checkStampType()).isFalse();
	}
	
	@Test
	public void checkStampTypeTrue(){
		ButtonType buttonType = new ButtonType(
				EnumAdaptor.valueOf(0, ReservationArt.class), //dummy
				Stamp.DUMMY);
		assertThat(buttonType.checkStampType()).isTrue();
	}
	
}
