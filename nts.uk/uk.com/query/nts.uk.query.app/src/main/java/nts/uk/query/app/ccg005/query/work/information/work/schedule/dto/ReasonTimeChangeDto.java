package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReasonTimeChangeDto {
	// 時刻変更手段
	private Integer timeChangeMeans;

	// 打刻方法
	private Integer engravingMethod;
}
