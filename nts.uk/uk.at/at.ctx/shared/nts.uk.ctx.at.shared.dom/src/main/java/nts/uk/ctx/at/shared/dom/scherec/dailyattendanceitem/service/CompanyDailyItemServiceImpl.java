package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
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
	private DailyAttendanceItemNameAdapter dailyAttendanceItemNameAdapter;
	@Override
	public List<DailyAttendanceItemNameAdapterDto> getDailyItems(String cid, Optional<String> authorityDailyId,
			List<Integer> attendanceItemIds, List<DailyAttendanceAtr> itemAtrs) {
		List<Integer> dailyAttendanceItemIds = new ArrayList<>();
		// パラメータ「ロールID」をチェックする (Check the parameter "Roll ID")
		if (authorityDailyId.isPresent()) {
			// ドメインモデル「権限別日次項目制御」を取得する
			Optional<DailyAttendanceItemAuthority> itemAuthority = dailyAttdItemAuthRepository
					.getDailyAttdItemByAttItemId(cid, authorityDailyId.get(), attendanceItemIds);
			if (itemAuthority.isPresent()) {
				dailyAttendanceItemIds = itemAuthority.get().getListDisplayAndInputControl().stream()
						.map(x -> x.getItemDailyID()).collect(Collectors.toList());
			}
		} else {
			dailyAttendanceItemIds = attendanceItemIds;
		}
		// ドメインモデル「日次の勤怠項目」を取得する
		List<DailyAttendanceItem> dailyItem = dailyAttendanceItemRepository.findByAtrsAndAttItemIds(cid,
				itemAtrs.stream().map(x -> x.value).collect(Collectors.toList()), dailyAttendanceItemIds);
		// 取得した勤怠項目の件数をチェックする
		if(dailyItem.isEmpty()){
			return Collections.emptyList();
		}
		List<DailyAttendanceItemNameAdapterDto> dailyAttItem = dailyAttendanceItemNameAdapter
				.getDailyAttendanceItemName(dailyAttendanceItemIds);
		return dailyAttItem;
	}

}
