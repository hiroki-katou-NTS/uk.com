package nts.uk.screen.at.app.kdw006.j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * ScreenQuery: 画面利用内容を取得する
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW006_前準備.J：工数入力画面フォーマット設定.メニュー別OCD.画面利用内容を取得する
 * 
 * @author chungnt
 *
 */

@Stateless
public class GetScreenUsageDetails {

	@Inject
	private GetDisplayFormat getDisplayFormat;

	@Inject
	private DailyAttendanceItemRepository dailyAttendanceItemRepository;

	@Inject
	private AcquireManHourRecordItems acquireManHourRecordItems;

	public GetScreenUsageDetailsDto get() {
		GetScreenUsageDetailsDto result = new GetScreenUsageDetailsDto();
		List<DailyAttendanceItemDto> dailyItem = new ArrayList<>();
		
		//Get Data
		GetDisplayFormatDto getDisplayFormatDto = this.getDisplayFormat.get();
		List<DailyAttendanceItem> attendents = this.dailyAttendanceItemRepository
				.getListById(AppContexts.user().companyId(), getAttendanceId());
		List<AcquireManHourRecordItemsDto> hourRecordItemsDtos = this.acquireManHourRecordItems.get();
		
		// Mapping Dto
		result.setManHourInputDisplayFormat(getDisplayFormatDto);
		dailyItem = attendents.stream().map(m -> {
			return new DailyAttendanceItemDto(m.getCompanyId(), m.getAttendanceItemId(), m.getAttendanceName().v(),
					m.getDisplayNumber(), m.getUserCanUpdateAtr().value, m.getDailyAttendanceAtr().value,
					m.getNameLineFeedPosition(), m.getMasterType().map(t -> t.value).orElse(null),
					m.getPrimitiveValue().map(p -> p.value).orElse(null),
					m.getDisplayName().map(n -> n.v()).orElse(""));
		}).collect(Collectors.toList());
		
		result.setDailyAttendanceItem(dailyItem);
		result.setManHourRecordItem(hourRecordItemsDtos);

		return result;
	}

	private List<Integer> getAttendanceId() {
		Integer[] attendanceId = { 216, 221, 226, 231, 236, 241, 246, 251, 256, 261, 266, 271, 276, 281, 286, 291, 296,
				301, 306, 311, 439, 444, 449, 454, 459, 532, 535, 559, 592, 598, 604, 610, 641, 642, 643, 644, 645, 646,
				647, 648, 649, 650, 651, 652, 653, 654, 655, 656, 657, 658, 659, 660, 661, 662, 663, 664, 665, 666, 667,
				668, 669, 670, 671, 672, 673, 674, 675, 676, 677, 678, 679, 680, 681, 682, 683, 684, 685, 686, 687, 688,
				689, 690, 691, 692, 693, 694, 695, 696, 697, 698, 699, 700, 701, 702, 703, 704, 705, 706, 707, 708, 709,
				710, 711, 712, 713, 714, 715, 716, 717, 718, 719, 720, 721, 722, 723, 724, 725, 726, 727, 728, 729, 730,
				731, 732, 733, 734, 735, 736, 737, 738, 739, 740, 802, 807, 812, 817, 822 };

		List<Integer> result = Arrays.asList(attendanceId);
		return result;
	}
}
