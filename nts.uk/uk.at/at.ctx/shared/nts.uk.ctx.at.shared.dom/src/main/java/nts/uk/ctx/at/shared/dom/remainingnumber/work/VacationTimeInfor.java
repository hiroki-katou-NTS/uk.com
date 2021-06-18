package nts.uk.ctx.at.shared.dom.remainingnumber.work;

import java.util.List;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;

/**
 * 時間休暇使用時間
 * 
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VacationTimeInfor {

	/** 使用対象 */
	private AppTimeType timeType;
	/** 作成元区分 */
	private CreateAtr createData;
	/** 勤務種類コード */
	private String workTypeCode;
	/** 時間 */
	private List<VacationUsageTimeDetail> vacationUsageTimeDetails;
	
	//dùng method này lấy tổng times, lỡ sau thay đổi gì thì tiện hơn
	public int getTotalTimes() {
		return this.getVacationUsageTimeDetails().stream().flatMapToInt(x -> IntStream.of(x.getTimes())).sum();
	}
}
