package nts.uk.ctx.at.request.app.find.application.common.service.other.output;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ActualContentDisplayDto {
	/**
	 * 年月日
	 */
	private String date;
	
	/**
	 * 実績詳細
	 */
	private AchievementDetailDto opAchievementDetail;
	
	public static ActualContentDisplayDto fromDomain(ActualContentDisplay actualContentDisplay) {
		return new ActualContentDisplayDto(
				actualContentDisplay.getDate().toString(), 
				actualContentDisplay.getOpAchievementDetail().map(x -> AchievementDetailDto.fromDomain(x)).orElse(null));
	}
	
	public ActualContentDisplay toDomain() {
		return new ActualContentDisplay(
				GeneralDate.fromString(date, "yyyy/MM/dd"), 
				opAchievementDetail == null ? Optional.empty() : Optional.of(opAchievementDetail.toDomain()));
	} 
}
