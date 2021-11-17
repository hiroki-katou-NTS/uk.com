package nts.uk.ctx.at.schedule.dom.shift.specificdaysetting;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.OneDaySpecificItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
/**
 * 全社特定日設定のUTコード
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class CompanySpecificDateItemTest {
	
	@Test
	public void testGetter() {
		
		val domain = new CompanySpecificDateItem( "cid"
				, GeneralDate.today()
				, new OneDaySpecificItem( Arrays.asList(new SpecificDateItemNo(1)))
				);
		NtsAssert.invokeGetters(domain);
		
	}

}
