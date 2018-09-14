package nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AtItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemNameImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthority;

@Stateless
public class CompanyMonthlyItemServiceImpl implements CompanyMonthlyItemService {

	@Inject
	private MonthlyItemControlByAuthRepository monthlyItemControlByAuthRepository;

	@Inject
	private MonthlyAttendanceItemRepository monthlyAttendanceItemRepository;

	@Inject
	private AtItemNameAdapter atItemNameAdapter;

	@Override
	public List<AttItemNameImport> getMonthlyItems(String cid, Optional<String> authorityId,
			List<Integer> attendanceItemIds, List<MonthlyAttendanceItemAtr> itemAtrs) {
		attendanceItemIds = attendanceItemIds == null ? Collections.emptyList() : attendanceItemIds;
		itemAtrs = itemAtrs == null ? Collections.emptyList() : itemAtrs;
		List<Integer> monthlyAttendanceItemIds = new ArrayList<>();
		// パラメータ「ロールID」をチェックする (Check the parameter "Roll ID")
		if (authorityId.isPresent()) {
			// ドメインモデル「権限別月次項目制御」を取得する
			Optional<MonthlyItemControlByAuthority> itemAuthority = monthlyItemControlByAuthRepository
					.getMonthlyAttdItemByAttItemId(cid, authorityId.get(), attendanceItemIds);
			if (itemAuthority.isPresent()) {
				monthlyAttendanceItemIds = itemAuthority.get().getListDisplayAndInputMonthly().stream()
						.map(x -> x.getItemMonthlyId()).collect(Collectors.toList());
			}
		} else {
			monthlyAttendanceItemIds = attendanceItemIds;
		}
		// ドメインモデル「月次の勤怠項目」を取得する
		List<MonthlyAttendanceItem> monthlyItem = monthlyAttendanceItemRepository.findByAttendanceItemIdAndAtr(cid,
				monthlyAttendanceItemIds, itemAtrs.stream().map(x -> x.value).collect(Collectors.toList()));
		// 取得した勤怠項目の件数をチェックする
		if (monthlyItem.isEmpty()) {
			return Collections.emptyList();
		}
		// 勤怠項目に対応する名称を生成する
		List<AttItemNameImport> monthlyAttItem = atItemNameAdapter
				.getNameOfAttendanceItem(
						monthlyItem.stream().map(x -> x.getAttendanceItemId()).collect(Collectors.toList()),TypeOfItemImport.Monthly);
		return monthlyAttItem;
	}

}
