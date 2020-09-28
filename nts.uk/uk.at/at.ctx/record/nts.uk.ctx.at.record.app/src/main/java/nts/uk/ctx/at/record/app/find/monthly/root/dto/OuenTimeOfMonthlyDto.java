package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceAmountMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.OuenTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.OuenWorkAggregateDetail;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.OuenWorkAggregateFrameDetail;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の応援時間 */
public class OuenTimeOfMonthlyDto implements ItemConst {

	/** 集計枠集計明細: 作業集計枠明細 */
	@AttendanceItemLayout(jpPropertyName = FRAME, layout = LAYOUT_A, listMaxLength = 99, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<OuenWorkAggregateFrameDetailDto> frames;

	/** その他集計明細: 応援作業集計明細  */
	@AttendanceItemLayout(jpPropertyName = OTHER, layout = LAYOUT_B)
	private OuenWorkAggregateDetailDto other;
	
	public static OuenTimeOfMonthlyDto from(OuenTimeOfMonthly domain) {
		
		return new OuenTimeOfMonthlyDto(
				ConvertHelper.mapTo(domain.getFrames(), f -> OuenWorkAggregateFrameDetailDto.from(f)),
				OuenWorkAggregateDetailDto.from(domain.getOther()));
	}
	
	public OuenTimeOfMonthly domain() {

		OuenTimeOfMonthly domain = OuenTimeOfMonthly.create(other == null 
					? OuenWorkAggregateDetail.empty() : other.domain()); 
		
		if (frames != null) {
			frames.stream().forEach(f -> {
				domain.add(f.domain());
			});
		}
		
		return domain;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	/** 応援作業集計明細 */
	public static class OuenWorkAggregateDetailDto {
		
		/**　総集計時間: 勤怠月間時間　*/
		@AttendanceItemValue(type = ValueType.TIME)
		@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A)
		private int time;
		
		/**　合計金額: 勤怠月間金額　*/
		@AttendanceItemValue(type = ValueType.AMOUNT_NUM)
		@AttendanceItemLayout(jpPropertyName = AMOUNT, layout = LAYOUT_B)
		private int amount;
		
		public static OuenWorkAggregateDetailDto from(OuenWorkAggregateDetail domain) {
			
			return new OuenWorkAggregateDetailDto(
					domain.getTotalTime().valueAsMinutes(), 
					domain.getTotalAmount().v());
		}
		
		public OuenWorkAggregateDetail domain() {
			
			return OuenWorkAggregateDetail.create(
					new AttendanceTimeMonth(time), 
					new AttendanceAmountMonth(amount));
		}
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	/** 作業集計枠明細 */
	public static class OuenWorkAggregateFrameDetailDto {

		/**　集計枠No: int　*/
		private int no;

		/**　集計明細: 応援作業集計明細　*/
		@AttendanceItemLayout(jpPropertyName = DETAIL, layout = LAYOUT_A)
		private OuenWorkAggregateDetailDto detail;
		
		public static OuenWorkAggregateFrameDetailDto from(OuenWorkAggregateFrameDetail domain) {
			
			return new OuenWorkAggregateFrameDetailDto(domain.getFrameNo(), 
						OuenWorkAggregateDetailDto.from(domain.getDetail()));
		}
		
		public OuenWorkAggregateFrameDetail domain() {
			
			return OuenWorkAggregateFrameDetail.create(no, 
					detail == null ? OuenWorkAggregateDetail.empty() : detail.domain());
		}
	}
}
