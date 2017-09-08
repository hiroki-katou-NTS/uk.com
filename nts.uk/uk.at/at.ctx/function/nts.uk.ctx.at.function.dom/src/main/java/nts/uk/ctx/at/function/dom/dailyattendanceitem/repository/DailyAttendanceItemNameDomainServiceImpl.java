package nts.uk.ctx.at.function.dom.dailyattendanceitem.repository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.DailyAttendanceItemAdapter;
import nts.uk.ctx.at.function.dom.adapter.DailyAttendanceItemAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.DivergenceTimeAdapter;
import nts.uk.ctx.at.function.dom.adapter.DivergenceTimeAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.PremiumItemFuncAdapter;
import nts.uk.ctx.at.function.dom.adapter.PremiumItemFuncAdapterDto;
import nts.uk.ctx.at.function.dom.attendanceItemAndFrameLinking.AttendanceItemLinking;
import nts.uk.ctx.at.function.dom.attendanceItemAndFrameLinking.repository.AttendanceItemLinkingRepository;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/*
 * NamPT
 * Set name of dailyAttendanceItem
 * 勤怠項目に対応する名称を生成する
 */
@Stateless
public class DailyAttendanceItemNameDomainServiceImpl implements DailyAttendanceItemNameDomainService {

	@Inject
	private DailyAttendanceItemAdapter dailyAttendanceItemAdapter;

	@Inject
	private AttendanceItemLinkingRepository attendanceItemLinkingRepository;

	@Inject
	private DivergenceTimeAdapter divergenceTimeAdapter;

	@Inject
	private PremiumItemFuncAdapter premiumItemFuncAdapter;

	@Inject
	private BPTimeItemRepository bPTimeItemRepository;

	@Override
	public List<DailyAttendanceItem> getNameOfDailyAttendanceItem(List<Integer> dailyAttendanceItemIds) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<DailyAttendanceItemAdapterDto> dailyAttendanceItems = this.dailyAttendanceItemAdapter
				.getDailyAttendanceItem(companyId, dailyAttendanceItemIds);

		// 対応するドメインモデル 「勤怠項目と枠の紐付け」 を取得する
		List<AttendanceItemLinking> attendanceItemAndFrameNos = this.attendanceItemLinkingRepository
				.getByAttendanceId(dailyAttendanceItemIds);

		// get list frame No
		Map<Integer, AttendanceItemLinking> frameNoMap = attendanceItemAndFrameNos.stream()
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		List<Integer> frameNos = frameNoMap.values().stream().map(item -> {
			return item.getFrameNo().v();
		}).collect(Collectors.toList());

		// 乖離時間 7
		Map<Integer, DivergenceTimeAdapterDto> divergenceTimes = this.divergenceTimeAdapter
				.getDivergenceTimeName(companyId, frameNos).stream()
				.collect(Collectors.toMap(DivergenceTimeAdapterDto::getDivTimeId, x -> x));

		// 割増項目 4
		Map<Integer, PremiumItemFuncAdapterDto> premiumItemnames = this.premiumItemFuncAdapter.getPremiumItemName(companyId, frameNos)
				.stream().collect(Collectors.toMap(PremiumItemFuncAdapterDto::getDisplayNumber, x -> x));
		
		// 加給時間項目 5 
		Map<Integer, BonusPayTimeItem> bonusPayTimeItems = this.bPTimeItemRepository
				.getListBonusPayTimeItemName(companyId, frameNos).stream()
				.collect(Collectors.toMap(BonusPayTimeItem::getId, x -> x));
		
		// 特定加給時間項目 6
		Map<Integer, BonusPayTimeItem> specialBonusPayTimeItem = this.bPTimeItemRepository
				.getListSpecialBonusPayTimeItemName(companyId, frameNos).stream()
				.collect(Collectors.toMap(BonusPayTimeItem::getId, x -> x));
		
		List<DailyAttendanceItem> dailyAttendanceItemDomainServiceDtos = new ArrayList<>();
		
		dailyAttendanceItems.stream().forEach(item -> {
			if (frameNoMap.containsKey(item.getAttendanceItemId())) {
				DailyAttendanceItem attendanceDto = new DailyAttendanceItem();
				attendanceDto.setAttendanceItemDisplayNumber(item.getDisplayNumber());
				attendanceDto.setAttendanceItemId(item.getAttendanceItemId());
				attendanceDto.setAttendanceItemName(item.getAttendanceName());
				if (divergenceTimes.containsKey(frameNoMap.get(item.getAttendanceItemId()))) {
					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
							divergenceTimes.get(frameNoMap.get(item.getAttendanceItemId())).getDivTimeName()));
				} else if (premiumItemnames.containsKey(frameNoMap.get(item.getAttendanceItemId()))) {
					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
							premiumItemnames.get(frameNoMap.get(item.getAttendanceItemId())).getPremiumItemname()));
				} else if (bonusPayTimeItems.containsKey(frameNoMap.get(item.getAttendanceItemId()))) {
					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
							bonusPayTimeItems.get(frameNoMap.get(item.getAttendanceItemId())).getTimeItemName().v()));
				} else if (specialBonusPayTimeItem.containsKey(frameNoMap.get(item.getAttendanceItemId()))) {
					attendanceDto.setAttendanceItemName(
							MessageFormat.format(attendanceDto.getAttendanceItemName(), specialBonusPayTimeItem
									.get(frameNoMap.get(item.getAttendanceItemId())).getTimeItemName().v()));
				}
				dailyAttendanceItemDomainServiceDtos.add(attendanceDto);
			} else {
				DailyAttendanceItem attendanceDto2 = new DailyAttendanceItem();
				attendanceDto2.setAttendanceItemDisplayNumber(item.getDisplayNumber());
				attendanceDto2.setAttendanceItemId(item.getAttendanceItemId());
				attendanceDto2.setAttendanceItemName(item.getAttendanceName());
				dailyAttendanceItemDomainServiceDtos.add(attendanceDto2);
			}
		});

		return dailyAttendanceItemDomainServiceDtos;
	}

}
