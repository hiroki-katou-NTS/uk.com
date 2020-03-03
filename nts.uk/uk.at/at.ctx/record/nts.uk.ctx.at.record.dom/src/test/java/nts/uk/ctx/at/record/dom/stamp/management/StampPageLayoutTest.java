package nts.uk.ctx.at.record.dom.stamp.management;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.Button;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper.Layout.Comment;

public class StampPageLayoutTest {
	
	@Test
	public void checkCreateStampPageLayout_false(){
		NtsAssert.businessException("Msg_1627", () -> {
			new StampPageLayout(
					new PageNo(1),//dummy 
					new StampPageName("DUMMY"),//dummy 
					Comment.DUMMY,//dummy 
					EnumAdaptor.valueOf(0, ButtonLayoutType.class),//dummy
					Collections.emptyList());
		});
	}
	
	@Test
	public void checkCreateStampPageLayout_succes(){
			new StampPageLayout(
					new PageNo(1), 
					new StampPageName("DUMMY"), 
					Comment.DUMMY, 
					EnumAdaptor.valueOf(0, ButtonLayoutType.class),
					Arrays.asList(Button.DUMMY));
	}
	
	@Test
	public void getter(){
		StampPageLayout layout = Layout.DUMMY;
		NtsAssert.invokeGetters(layout);
	}
}
