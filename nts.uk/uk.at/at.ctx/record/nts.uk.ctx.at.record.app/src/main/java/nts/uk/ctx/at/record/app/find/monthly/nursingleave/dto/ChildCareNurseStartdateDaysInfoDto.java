package nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ChildCareNurseStartdateDaysInfoDto {
	/** 子の看護休暇情報（本年）*/
	private ChildCareNurseStartdateInfoDto thisYear;
	/** 子の看護休暇情報（翌年）*/
	private ChildCareNurseStartdateInfoDto nextYear;
}
