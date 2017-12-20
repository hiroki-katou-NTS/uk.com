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
import nts.uk.shr.com.i18n.TextResource;

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
				.getDailyAttendanceItem(companyId, dailyAttendanceItemIds).stream().map(item -> {
					String name = item.getAttendanceName();
					if (name.indexOf("{#") >= 0) {
						int startLocation = name.indexOf("{");
						int endLocation = name.indexOf("}");
						name = name.replace(name.substring(startLocation, endLocation + 1),
								TextResource.localize(name.substring(startLocation + 2, endLocation)));
					}
					return new DailyAttendanceItemAdapterDto(item.getCompanyId(), item.getAttendanceItemId(), name,
							item.getDisplayNumber(), item.getUserCanUpdateAtr(), item.getDailyAttendanceAtr(),
							item.getNameLineFeedPosition());
				}).collect(Collectors.toList());

		// 対応するドメインモデル 「勤怠項目と枠の紐付け」 を取得する
		List<AttendanceItemLinking> attendanceItemAndFrameNos = this.attendanceItemLinkingRepository
				.getByAttendanceId(dailyAttendanceItemIds);

		// get list frame No 7
		Map<Integer, AttendanceItemLinking> frameNoDivergenceMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 7 && item.getTypeOfAttendanceItem().value == 1)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		// get list frame No 4
		Map<Integer, AttendanceItemLinking> frameNoPremiumMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 4 && item.getTypeOfAttendanceItem().value == 1)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		// get list frame No 5
		Map<Integer, AttendanceItemLinking> frameNoBonusPayMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 5 && item.getTypeOfAttendanceItem().value == 1)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		// get list frame No 6
		Map<Integer, AttendanceItemLinking> frameNoSpecialBonusPayMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 6 && item.getTypeOfAttendanceItem().value == 1)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		List<Integer> frameNos = attendanceItemAndFrameNos.stream().map(f -> {
			return f.getFrameNo().v();
		}).collect(Collectors.toList());

		// 乖離時間 7
		Map<Integer, DivergenceTimeAdapterDto> divergenceTimes = this.divergenceTimeAdapter
				.getDivergenceTimeName(companyId, frameNos).stream()
				.collect(Collectors.toMap(DivergenceTimeAdapterDto::getDivTimeId, x -> x));

		// 割増項目 4
		Map<Integer, PremiumItemFuncAdapterDto> premiumItemnames = this.premiumItemFuncAdapter
				.getPremiumItemName(companyId, frameNos).stream()
				.collect(Collectors.toMap(PremiumItemFuncAdapterDto::getDisplayNumber, x -> x));

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
			DailyAttendanceItem attendanceDto = new DailyAttendanceItem();
			attendanceDto.setAttendanceItemDisplayNumber(item.getDisplayNumber());
			attendanceDto.setAttendanceItemId(item.getAttendanceItemId());
			attendanceDto.setAttendanceItemName(item.getAttendanceName());
			if (frameNoDivergenceMap.containsKey(item.getAttendanceItemId()) && divergenceTimes
					.containsKey(frameNoDivergenceMap.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						divergenceTimes.get(frameNoDivergenceMap.get(item.getAttendanceItemId()).getFrameNo().v())
								.getDivTimeName()));
				attendanceDto.setFrameCategory(
						frameNoDivergenceMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoDivergenceMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			} else if (frameNoPremiumMap.containsKey(item.getAttendanceItemId()) && premiumItemnames
					.containsKey(frameNoPremiumMap.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						premiumItemnames.get(frameNoPremiumMap.get(item.getAttendanceItemId()).getFrameNo().v())
								.getPremiumItemname()));
				attendanceDto
						.setFrameCategory(frameNoPremiumMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoPremiumMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			} else if (frameNoBonusPayMap.containsKey(item.getAttendanceItemId()) && bonusPayTimeItems
					.containsKey(frameNoBonusPayMap.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						bonusPayTimeItems.get(frameNoBonusPayMap.get(item.getAttendanceItemId()).getFrameNo().v())
								.getTimeItemName().v()));
				attendanceDto
						.setFrameCategory(frameNoBonusPayMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoBonusPayMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			} else if (frameNoSpecialBonusPayMap.containsKey(item.getAttendanceItemId()) && specialBonusPayTimeItem
					.containsKey(frameNoSpecialBonusPayMap.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						specialBonusPayTimeItem
								.get(frameNoSpecialBonusPayMap.get(item.getAttendanceItemId()).getFrameNo().v())
								.getTimeItemName().v()));
				attendanceDto.setFrameCategory(
						frameNoSpecialBonusPayMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoSpecialBonusPayMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			} else {
				attendanceDto.setFrameCategory(0);
				attendanceDto.setTypeOfAttendanceItem(0);
			}
			dailyAttendanceItemDomainServiceDtos.add(attendanceDto);
		});

		return dailyAttendanceItemDomainServiceDtos;
	}

}
