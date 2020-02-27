package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class ShiftMasterDisInforTest {

	// if remarks == null
	@Test
	public void validate() {
		ShiftMasterName name = new ShiftMasterName("name");// dummy
		ColorCodeChar6 color = new ColorCodeChar6("color");// dummy
		Remarks remarks = null;
		ShiftMasterDisInfor displayInfor = new ShiftMasterDisInfor(name, color, remarks);
		assertThat(displayInfor.getName()).isEqualTo(name);
		assertThat(displayInfor.getColor()).isEqualTo(color);
		assertThat(displayInfor.getRemarks().isPresent()).isFalse();
	}

	// if remarks != null
	@Test
	public void validate2() {
		ShiftMasterName name = new ShiftMasterName("name");// dummy
		ColorCodeChar6 color = new ColorCodeChar6("color");// dummy
		Remarks remarks = new Remarks("remarks");
		ShiftMasterDisInfor displayInfor = new ShiftMasterDisInfor(name, color, remarks);
		assertThat(displayInfor.getName()).isEqualTo(name);
		assertThat(displayInfor.getColor()).isEqualTo(color);
		assertThat(displayInfor.getRemarks().isPresent()).isTrue();
	}

	@Test
	public void getters() {
		ShiftMasterDisInfor shiftMasterDisInfor = ShiftMasterInstanceHelper.getShiftMasterDisInforEmpty();
		NtsAssert.invokeGetters(shiftMasterDisInfor);
	}

}
