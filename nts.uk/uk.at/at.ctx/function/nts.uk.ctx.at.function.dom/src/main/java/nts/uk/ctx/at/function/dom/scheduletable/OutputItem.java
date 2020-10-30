package nts.uk.ctx.at.function.dom.scheduletable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 出力項目
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.スケジュール表.出力項目
 * @author dan_pv
 *
 */
@Value
public class OutputItem implements DomainValue {

	/**
	 * 	追加列情報の利用区分
	 */
	private final NotUseAtr additionalColumnUseAtr;
	
	/**
	 * シフト背景色の表示区分	
	 */
	private final NotUseAtr shiftBackgroundColorUseAtr;
	
	/**
	 * 	実績表示区分
	 */
	private final NotUseAtr dailyDataDisplayAtr;
	
	/**
	 * 	詳細リスト	
	 */
	private final List<OneRowOutputItem> details;
	
	/**
	 * 作る
	 * @param additionalColumnUseAtr
	 * @param shiftBackgroundColorUseAtr
	 * @param dailyDataDisplayAtr
	 * @param details
	 * @return
	 */
	public static OutputItem create(
			NotUseAtr additionalColumnUseAtr,
			NotUseAtr shiftBackgroundColorUseAtr,
			NotUseAtr dailyDataDisplayAtr,
			List<OneRowOutputItem> details
			) {
		
		// inv-1  1 <= 詳細リスト.size <= 10
		if ( details.isEmpty() || details.size() > 10 ) {
			throw new BusinessException("Msg_1975");
		}
		
		// inv-2 詳細リストにある個人情報項目が重複しないこと。
		List<ScheduleTablePersonalInfoItem> personalInfoList = details.stream().map( row -> {
			List<ScheduleTablePersonalInfoItem> oneRowpersonalInfos = new ArrayList<>();
			if ( row.getPersonalInfo().isPresent()) oneRowpersonalInfos.add(row.getPersonalInfo().get());
			if (row.getAdditionalInfo().isPresent()) oneRowpersonalInfos.add(row.getAdditionalInfo().get());
			
			return oneRowpersonalInfos;
		})
		.flatMap(List::stream)
		.collect(Collectors.toList());
		
		if ( personalInfoList.isEmpty() ) {
			throw new BusinessException("Msg_2006");
		}
		
		if ( personalInfoList.size() != new HashSet<>(personalInfoList).size() ) {
			throw new BusinessException("Msg_1972");
		}
		
		// inv-3  詳細リストにある勤怠項目が重複しないこと。
		List<ScheduleTableAttendanceItem> attendanceItemList = details.stream()
				.filter( row -> row.getAttendanceItem().isPresent())
				.map( row -> row.getAttendanceItem().get())
				.collect(Collectors.toList());
		
		if ( attendanceItemList.isEmpty() ) {
			throw new BusinessException("Msg_2007");
		}
		
		if ( attendanceItemList.size() != new HashSet<>(attendanceItemList).size() ) {
			throw new BusinessException("Msg_1973");
		}
		
		return new OutputItem(additionalColumnUseAtr, shiftBackgroundColorUseAtr, dailyDataDisplayAtr, details);
	}
	
}
