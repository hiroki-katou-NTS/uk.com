package nts.uk.ctx.at.schedule.dom.shift.specificdaysetting;

import java.util.Arrays;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.OneDaySpecificItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;

/**
 * 職場特定日設定のUTコード
 * @author lan_lt
 *
 */
public class WorkplaceSpecificDateItemTest {

	@Test
	public void testGetter() {
		
		val domain = new CompanySpecificDateItem( "workplaceId"
				, GeneralDate.today()
				, new OneDaySpecificItem( Arrays.asList(new SpecificDateItemNo(1)))
				);
		NtsAssert.invokeGetters(domain);
		
	}
	
}
