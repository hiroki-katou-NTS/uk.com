package nts.uk.screen.at.app.kdw013.query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CalculateTotalWorktimePerDayDto {
	// 作業合計時間
	public List<TotalWorktimeDto> totalWorktime;
}
