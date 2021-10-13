package nts.uk.screen.at.app.kdw013.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyActualManHrActualTaskDto {
	/** 年月日 */
	private GeneralDate date;

	/** 作業ブロック */
	private List<ManHrPerformanceTaskBlockDto> taskBlocks;
}
