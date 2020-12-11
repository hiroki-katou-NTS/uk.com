package nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TempChildCareManagementDto {
	/** 使用数 */
	/** 子の看護介護休暇（使用日数） */
	private Double usedDay;
	
	/** 使用数 */
	/** 子の看護介護休暇（使用時間） */
	private Integer usedTimes;
	
	/**
	 * 作成元区分
	 */
	private String creatorAtr;
	
	/**
	 * 対象日
	 */
	private String ymd;
}
