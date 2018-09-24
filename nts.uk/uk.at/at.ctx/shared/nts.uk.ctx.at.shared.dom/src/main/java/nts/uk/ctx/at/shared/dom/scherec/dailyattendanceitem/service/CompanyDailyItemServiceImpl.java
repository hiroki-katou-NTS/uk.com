package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DisplayAndInputControl;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AtItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemNameImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttdItemAuthRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;

@Stateless
public class CompanyDailyItemServiceImpl implements CompanyDailyItemService {

	@Inject
	private DailyAttdItemAuthRepository dailyAttdItemAuthRepository;

	@Inject
	private DailyAttendanceItemRepository dailyAttendanceItemRepository;

	@Inject
	private AtItemNameAdapter atItemNameAdapter;

	@Override
	public List<AttItemNameImport> getDailyItems(String cid, Optional<String> authorityId,
			List<Integer> attendanceItemIds, List<DailyAttendanceAtr> itemAtrs) {
		attendanceItemIds = attendanceItemIds == null ? Collections.emptyList() : attendanceItemIds;
		itemAtrs = itemAtrs == null ? Collections.emptyList() : itemAtrs;
		List<Integer> dailyAttendanceItemIds = new ArrayList<>();
		Map<Integer, AttItemAuthority> authorityMap = new HashMap<Integer, AttItemAuthority>();
		// パラメータ「ロールID」をチェックする (Check the parameter "Roll ID")
		if (authorityId.isPresent()) {
			// ドメインモデル「権限別日次項目制御」を取得する
			Optional<DailyAttendanceItemAuthority> itemAuthority = dailyAttdItemAuthRepository
					.getDailyAttdItemByAttItemId(cid, authorityId.get(), attendanceItemIds);
			if (itemAuthority.isPresent()) {
				dailyAttendanceItemIds = itemAuthority.get().getListDisplayAndInputControl().stream()
						.map(x -> x.getItemDailyID()).collect(Collectors.toList());
				for (DisplayAndInputControl item : itemAuthority.get().getListDisplayAndInputControl()) {
					AttItemAuthority auth = new AttItemAuthority();
					auth.setToUse(item.isToUse());
					auth.setYouCanChangeIt(item.getInputControl().isYouCanChangeIt());
					auth.setCanBeChangedByOthers(item.getInputControl().isCanBeChangedByOthers());
					authorityMap.put(item.getItemDailyID(), auth);
				}
			}
		} else {
			dailyAttendanceItemIds = attendanceItemIds;
		}
		// ドメインモデル「日次の勤怠項目」を取得する
		List<DailyAttendanceItem> dailyItem = dailyAttendanceItemRepository.findByAttendanceItemIdAndAtr(cid,
				dailyAttendanceItemIds, itemAtrs.stream().map(x -> x.value).collect(Collectors.toList()));
		// 取得した勤怠項目の件数をチェックする
		if (dailyItem.isEmpty()) {
			return Collections.emptyList();
		}
		// 勤怠項目に対応する名称を生成する
		List<AttItemNameImport> dailyAttItem = atItemNameAdapter.getNameOfAttendanceItem(
				dailyItem.stream().map(x -> x.getAttendanceItemId()).collect(Collectors.toList()),
				TypeOfItemImport.Daily);
		for (AttItemNameImport att : dailyAttItem) {
			int id = att.getAttendanceItemId();
			if (authorityMap.containsKey(id)) {
				att.setAuthority(authorityMap.get(id));
			}
		}
		return dailyAttItem;
	}

}