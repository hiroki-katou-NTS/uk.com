package nts.uk.screen.hr.app.databeforereflecting.find;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnlb
 *
 */
@Data
@Builder
public class RetirementRelartedDto {
	// ・公開日
	private GeneralDate releaseDate;
	// ・解雇予告日アラーム
	private boolean dismissalNoticeDateAlarm;
	// ・解雇予告日チェック処理
	private boolean dismissalNoticeDateCheckProcess;
	// 解雇手当有無
	private List<DismissalNoticeConditionDto> dismissalNoticeConditions;
	// 解雇予告日
	private List<DismissalNoticeDateDto> dismissalNoticeDates;
}
