package nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChildCareNurseErrorsDto {
	/** 子の看護介護使用数 */
	private  ChildCareNurseUsedNumberDto usedNumber;
	/** 子の看護介護上限日数 */
	private Double limitDays;
	/** 子の看護介護エラー対象年月日 */
	private String ymd;

}
