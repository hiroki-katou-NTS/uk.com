package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttdItemDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.FrameNoAdapter;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.FrameNoAdapterDto;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.PremiumItemAdapter;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.PremiumItemDto;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTimeRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt
 *
 */
@Stateless
public class AttendanceItemsFinder {

	@Inject
	private DivergenceTimeRepository divergenceTimeRepository;

	@Inject
	private DailyAttendanceItemRepository dailyAttendanceItemRepository;

	@Inject
	private FrameNoAdapter frameNoAdapter;

	@Inject
	private PremiumItemAdapter premiumItemAdapter;

	@Inject
	private BPTimeItemRepository bPTimeItemRepository;

	public List<AttendanceItemDto> find() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		// 勤怠項目
		List<DailyAttendanceItem> dailyAttendanceItems = this.dailyAttendanceItemRepository.getListTobeUsed(companyId,
				1);

		// get list attendanceItemId
		List<Integer> attendanceItemIds = dailyAttendanceItems.stream().map(f -> {
			return f.getAttendanceItemId();
		}).collect(Collectors.toList());

		// 対応するドメインモデル 「勤怠項目と枠の紐付け」 を取得する
		List<FrameNoAdapterDto> attendanceItemAndFrameNos = this.frameNoAdapter.getFrameNo(attendanceItemIds);

		// get list frame No
		// List<Integer> frameNos = attendanceItemAndFrameNos.stream().map(f ->
		// {
		// return f.getFrameNo();
		// }).collect(Collectors.toList());

		Map<Integer, Integer> frameNoMap = attendanceItemAndFrameNos.stream()
				.collect(Collectors.toMap(FrameNoAdapterDto::getAttendanceItemId, FrameNoAdapterDto::getFrameNo));
		List<Integer> frameNos = frameNoMap.values().stream().collect(Collectors.toList());
		// 乖離時間
		Map<Integer, DivergenceTime> divergenceTimes = this.divergenceTimeRepository
				.getDivergenceTimeName(companyId, frameNos).stream()
				.collect(Collectors.toMap(DivergenceTime::getDivTimeId, x -> x));

		// 割増項目
		Map<Integer, PremiumItemDto> premiumItemnames = this.premiumItemAdapter.getPremiumItemName(companyId, frameNos)
				.stream().collect(Collectors.toMap(PremiumItemDto::getDisplayNumber, x -> x));

		// 加給時間項目
		Map<Integer, BonusPayTimeItem> bonusPayTimeItems = this.bPTimeItemRepository.getListBonusPayTimeItemName(companyId, frameNos)
				.stream().collect(Collectors.toMap(BonusPayTimeItem::getId, x -> x));

		// 特定加給時間項目
		Map<Integer, BonusPayTimeItem> specialBonusPayTimeItem = this.bPTimeItemRepository.getListSpecialBonusPayTimeItemName(companyId, frameNos)
				.stream().collect(Collectors.toMap(BonusPayTimeItem::getId, x -> x));

		List<AttendanceItemDto> attendanceItemDtos = new ArrayList<>();

		dailyAttendanceItems.stream().forEach(item -> {
			if (frameNoMap.containsKey(item.getAttendanceItemId())) {
				AttendanceItemDto attendanceDto = new AttendanceItemDto();
				attendanceDto.setAttendanceItemDisplayNumber(item.getDisplayNumber());
				attendanceDto.setAttendanceItemId(item.getAttendanceItemId());
				attendanceDto.setAttendanceItemName(item.getAttendanceName().v());
				if (divergenceTimes.containsKey(item.getAttendanceItemId())) {
					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
							divergenceTimes.get(item.getAttendanceItemId()).getDivTimeName().v()));
				} else if (premiumItemnames.containsKey(item.getAttendanceItemId())){
					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
							premiumItemnames.get(item.getAttendanceItemId()).getPremiumItemname()));
				} else if (bonusPayTimeItems.containsKey(item.getAttendanceItemId())) {
					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
							bonusPayTimeItems.get(item.getAttendanceItemId()).getTimeItemName().v()));
				} else if (specialBonusPayTimeItem.containsKey(item.getAttendanceItemId())){
					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
							specialBonusPayTimeItem.get(item.getAttendanceItemId()).getTimeItemName().v()));
				}
				attendanceItemDtos.add(attendanceDto);
			}
		});

		return attendanceItemDtos;
	}
	
	
	
	public List<AttdItemDto> findAll() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		// 勤怠項目
		List<DailyAttendanceItem> dailyAttendanceItems = this.dailyAttendanceItemRepository.getList(companyId);

		// get list attendanceItemId
		List<Integer> attendanceItemIds = dailyAttendanceItems.stream().map(f -> {
			return f.getAttendanceItemId();
		}).collect(Collectors.toList());

		// 対応するドメインモデル 「勤怠項目と枠の紐付け」 を取得する
		List<FrameNoAdapterDto> attendanceItemAndFrameNos = this.frameNoAdapter.getFrameNo(attendanceItemIds);

		// get list frame No
		// List<Integer> frameNos = attendanceItemAndFrameNos.stream().map(f ->
		// {
		// return f.getFrameNo();
		// }).collect(Collectors.toList());

		Map<Integer, Integer> frameNoMap = attendanceItemAndFrameNos.stream()
				.collect(Collectors.toMap(FrameNoAdapterDto::getAttendanceItemId, FrameNoAdapterDto::getFrameNo));
		List<Integer> frameNos = frameNoMap.values().stream().collect(Collectors.toList());
		// 乖離時間
		Map<Integer, DivergenceTime> divergenceTimes = this.divergenceTimeRepository
				.getDivergenceTimeName(companyId, frameNos).stream()
				.collect(Collectors.toMap(DivergenceTime::getDivTimeId, x -> x));

		// 割増項目
		Map<Integer, PremiumItemDto> premiumItemnames = this.premiumItemAdapter.getPremiumItemName(companyId, frameNos)
				.stream().collect(Collectors.toMap(PremiumItemDto::getDisplayNumber, x -> x));

		// 加給時間項目
		Map<Integer, BonusPayTimeItem> bonusPayTimeItems = this.bPTimeItemRepository.getListBonusPayTimeItemName(companyId, frameNos)
				.stream().collect(Collectors.toMap(BonusPayTimeItem::getId, x -> x));

		// 特定加給時間項目
		Map<Integer, BonusPayTimeItem> specialBonusPayTimeItem = this.bPTimeItemRepository.getListSpecialBonusPayTimeItemName(companyId, frameNos)
				.stream().collect(Collectors.toMap(BonusPayTimeItem::getId, x -> x));

		List<AttdItemDto> attendanceItemDtos = new ArrayList<>();

		dailyAttendanceItems.stream().forEach(item -> {
			if (frameNoMap.containsKey(item.getAttendanceItemId())) {
				AttdItemDto attendanceDto = new AttdItemDto();
				attendanceDto.setDailyAttendanceAtr(item.getDailyAttendanceAtr().value);
				attendanceDto.setNameLineFeedPosition(item.getNameLineFeedPosition());
				attendanceDto.setAttendanceItemId(item.getAttendanceItemId());
				attendanceDto.setAttendanceItemName(item.getAttendanceName().v());
				if (divergenceTimes.containsKey(item.getAttendanceItemId())) {
					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
							divergenceTimes.get(item.getAttendanceItemId()).getDivTimeName().v()));
				} else if (premiumItemnames.containsKey(item.getAttendanceItemId())){
					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
							premiumItemnames.get(item.getAttendanceItemId()).getPremiumItemname()));
				} else if (bonusPayTimeItems.containsKey(item.getAttendanceItemId())) {
					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
							bonusPayTimeItems.get(item.getAttendanceItemId()).getTimeItemName().v()));
				} else if (specialBonusPayTimeItem.containsKey(item.getAttendanceItemId())){
					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
							specialBonusPayTimeItem.get(item.getAttendanceItemId()).getTimeItemName().v()));
				}
				attendanceItemDtos.add(attendanceDto);
			}
		});

		return attendanceItemDtos;
	}
}
