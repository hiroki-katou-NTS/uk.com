package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util;

import java.util.regex.Pattern;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemIdContainer;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

public class RecordHandler implements ItemConst {

	private static final String SEPERATE_PATTERN = Pattern.quote(ItemConst.DEFAULT_SEPERATOR);

	protected String getGroup(ItemValue c) {
		if (StringUtil.isNullOrEmpty(c.path(), false)) {
			c.withPath(AttendanceItemIdContainer.getPath(c.itemId(), AttendanceItemType.DAILY_ITEM));
		}
		String[] paths = c.path().split(SEPERATE_PATTERN);
		return paths[0];
	}
}
