package nts.uk.ctx.at.record.dom.workrule.shiftmater;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import mockit.Injectable;
import nts.arc.testing.exception.BusinessExceptionAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.WorkInformation.Require;
import nts.uk.ctx.at.shared.dom.workrule.ShiftMaterInstanceHelper;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ColorCodeChar6;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterName;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.Remarks;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterDisInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;

public class ShiftMaterTest {
	@Injectable
	Require requireWorkInfo;

	@Test
	public void checkError() {
		ShiftMaster shiftMater = ShiftMaterInstanceHelper.getShiftMaterEmpty();
		BusinessExceptionAssert.id("Msg_1608", ()->shiftMater.checkError(requireWorkInfo));
		BusinessExceptionAssert.id("Msg_1609", ()->shiftMater.checkError(requireWorkInfo));
		BusinessExceptionAssert.id("Msg_435", ()->shiftMater.checkError(requireWorkInfo));
		BusinessExceptionAssert.id("Msg_434", ()->shiftMater.checkError(requireWorkInfo));
	}
	
	@Test
	public void change() {
		ShiftMaster shiftMater = ShiftMaterInstanceHelper.getShiftMaterEmpty();
		WorkInformation workInfor = new WorkInformation(null, "workTypeCode");
		ShiftMasterDisInfor displayInfor = new ShiftMasterDisInfor(new ShiftMasterName("name"), 
				new ColorCodeChar6("color"), null);	
		assertThatThrownBy(() -> 
			shiftMater.change(displayInfor, workInfor)
		).isInstanceOf(RuntimeException.class);
	}

	@Test
	public void validate() {
		ShiftMasterName name = new ShiftMasterName("name");
		ColorCodeChar6 color = new ColorCodeChar6("color");
		Remarks remarks = null;
		ShiftMasterDisInfor displayInfor = new ShiftMasterDisInfor(name, color, remarks);	
		assertThat(displayInfor.getName()).isEqualTo(name);
		assertThat(displayInfor.getColor()).isEqualTo(color);
		assertThat(displayInfor.getRemarks().isPresent()).isFalse();
	}
}
