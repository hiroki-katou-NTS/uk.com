package nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionalItemValueDto {

	@AttendanceItemLayout(layout = "A", jpPropertyName = "値")
	@AttendanceItemValue(itemId = { 641, 642, 643, 644, 645, 646, 647, 648, 649, 650, 651, 652, 653, 654, 655, 656, 657,
			658, 659, 660, 661, 662, 663, 664, 665, 666, 667, 668, 669, 670, 671, 672, 673, 674, 675, 676, 677, 678,
			679, 680, 681, 682, 683, 684, 685, 686, 687, 688, 689, 690, 691, 692, 693, 694, 695, 696, 697, 698, 699,
			700, 701, 702, 703, 704, 705, 706, 707, 708, 709, 710, 711, 712, 713, 714, 715, 716, 717, 718, 719, 720,
			721, 722, 723, 724, 725, 726, 727, 728, 729, 730, 731, 732, 733, 734, 735, 736, 737, 738, 739 })
	private String value;

	private Integer itemNo;
	
	private boolean isTimeItem;
	
	private boolean isTimesItem;
	
	private boolean isAmountItem;
}
