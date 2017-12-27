package nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 流動勤務時間帯設定
 * @author ken_takasu
 *
 */
public class FluidWorkTimeSetting {

	public List<FluidOverTimeWorkSheet> overTimeWorkSheet;
	
		
	/**
	 * 残業枠Noに一致する流動残業時間帯を取得する
	 * @param workNo
	 * @return
	 */
	public Optional<FluidOverTimeWorkSheet> getMatchWorkNoOverTimeWorkSheet(int overTimeFrameNo) {
		List<FluidOverTimeWorkSheet> timeSheet = this.overTimeWorkSheet.stream().filter(tc -> tc.getOverWorkTimeNo() == overTimeFrameNo).collect(Collectors.toList());
		if(timeSheet.size()>1) {
			throw new RuntimeException("Exist duplicate overTimeFrameNo : " + overTimeFrameNo);
		}else if(timeSheet==null) {
			return Optional.empty();
		}
		return Optional.of(timeSheet.get(0));
	}
	
}
