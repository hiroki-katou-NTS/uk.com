package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service;

import java.util.List;

public interface NarrowDownListDailyAttdItemPub {
	public List<Integer> get(String companyId, List<Integer> listAttdId);
}
