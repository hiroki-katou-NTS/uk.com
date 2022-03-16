package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.cache.DPCorrectionStateParam;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitialDisplayEmployeeDto {
	// List＜社員ID＞
	private List<String> lstEmpId;
	
	// 日別実績の修正の状態
	private DPCorrectionStateParam param;
}
