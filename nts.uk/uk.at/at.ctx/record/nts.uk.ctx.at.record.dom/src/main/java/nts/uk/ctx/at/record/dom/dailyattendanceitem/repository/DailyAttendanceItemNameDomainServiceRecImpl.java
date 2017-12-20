//package nts.uk.ctx.at.record.dom.dailyattendanceitem.repository;
//
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.uk.ctx.at.record.dom.bonuspay.repository.BPTimeItemRepository;
//import nts.uk.ctx.at.record.dom.bonuspay.timeitem.BonusPayTimeItem;
//import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItem;
//import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItemDomainServiceDto;
//import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.FrameNoAdapter;
//import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.FrameNoAdapterDto;
//import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.PremiumItemAdapter;
//import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.PremiumItemDto;
//import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTime;
//import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTimeRepository;
//import nts.uk.shr.com.context.AppContexts;
//import nts.uk.shr.com.context.LoginUserContext;
//
///*
// * NamPT
// * Set name of dailyAttendanceItem
// * 勤怠項目に対応する名称を生成する
// */
//@Stateless
//public class DailyAttendanceItemNameDomainServiceRecImpl implements DailyAttendanceItemNameDomainServiceRec {
//	
//	@Inject
//	private DailyAttendanceItemRepository dailyAttendanceItemRepository;
//
//	@Inject
//	private DivergenceTimeRepository divergenceTimeRepository;
//
//	@Inject
//	private FrameNoAdapter frameNoAdapter;
//
//	@Inject
//	private PremiumItemAdapter premiumItemAdapter;
//
//	@Inject
//	private BPTimeItemRepository bPTimeItemRepository;
//
//	@Override
//	public List<DailyAttendanceItemDomainServiceDto> getNameOfDailyAttendanceItem(List<Integer> dailyAttendanceItemIds) {
//		LoginUserContext login = AppContexts.user();
//		String companyId = login.companyId();
//		
//		List<DailyAttendanceItem> dailyAttendanceItemList = dailyAttendanceItemRepository.getListById(companyId, dailyAttendanceItemIds);
//		
//		// 対応するドメインモデル 「勤怠項目と枠の紐付け」 を取得する
//		List<FrameNoAdapterDto> attendanceItemAndFrameNos = this.frameNoAdapter.getFrameNo(dailyAttendanceItemIds);
//
//		// get list frame No
//		Map<Integer, FrameNoAdapterDto> frameNoMap = attendanceItemAndFrameNos.stream()
//				.collect(Collectors.toMap(FrameNoAdapterDto::getAttendanceItemId, x -> x));
//		List<Integer> frameNos = frameNoMap.values().stream().map(item -> {return item.getFrameNo();}).collect(Collectors.toList());
//
//		// 乖離時間 7
//		Map<Integer, DivergenceTime> divergenceTimes = this.divergenceTimeRepository
//				.getDivergenceTimeName(companyId, frameNos).stream()
//				.collect(Collectors.toMap(DivergenceTime::getDivTimeId, x -> x));
//
//		// 割増項目 4
//		Map<Integer, PremiumItemDto> premiumItemnames = this.premiumItemAdapter.getPremiumItemName(companyId, frameNos)
//				.stream().collect(Collectors.toMap(PremiumItemDto::getDisplayNumber, x -> x));
//
//		// 加給時間項目 5 
//		Map<Integer, BonusPayTimeItem> bonusPayTimeItems = this.bPTimeItemRepository
//				.getListBonusPayTimeItemName(companyId, frameNos).stream()
//				.collect(Collectors.toMap(BonusPayTimeItem::getId, x -> x));
//
//		// 特定加給時間項目 6
//		Map<Integer, BonusPayTimeItem> specialBonusPayTimeItem = this.bPTimeItemRepository
//				.getListSpecialBonusPayTimeItemName(companyId, frameNos).stream()
//				.collect(Collectors.toMap(BonusPayTimeItem::getId, x -> x));
//
//		List<DailyAttendanceItemDomainServiceDto> dailyAttendanceItemDomainServiceDtos = new ArrayList<>();
//		
//		dailyAttendanceItemList.stream().forEach(item -> {
//			if (frameNoMap.containsKey(item.getAttendanceItemId())) {
//				DailyAttendanceItemDomainServiceDto attendanceDto = new DailyAttendanceItemDomainServiceDto();
//				attendanceDto.setAttendanceItemDisplayNumber(item.getDisplayNumber());
//				attendanceDto.setAttendanceItemId(item.getAttendanceItemId());
//				attendanceDto.setAttendanceItemName(item.getAttendanceName().v());
//				if (divergenceTimes.containsKey(frameNoMap.get(item.getAttendanceItemId()))) {
//					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
//							divergenceTimes.get(frameNoMap.get(item.getAttendanceItemId())).getDivTimeName().v()));
//				} else if (premiumItemnames.containsKey(frameNoMap.get(item.getAttendanceItemId()))) {
//					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
//							premiumItemnames.get(frameNoMap.get(item.getAttendanceItemId())).getPremiumItemname()));
//				} else if (bonusPayTimeItems.containsKey(frameNoMap.get(item.getAttendanceItemId()))) {
//					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
//							bonusPayTimeItems.get(frameNoMap.get(item.getAttendanceItemId())).getTimeItemName().v()));
//				} else if (specialBonusPayTimeItem.containsKey(frameNoMap.get(item.getAttendanceItemId()))) {
//					attendanceDto.setAttendanceItemName(
//							MessageFormat.format(attendanceDto.getAttendanceItemName(), specialBonusPayTimeItem
//									.get(frameNoMap.get(item.getAttendanceItemId())).getTimeItemName().v()));
//				}
//				dailyAttendanceItemDomainServiceDtos.add(attendanceDto);
//			}
//		});
//		
//		return dailyAttendanceItemDomainServiceDtos;
//	}
//
//}
