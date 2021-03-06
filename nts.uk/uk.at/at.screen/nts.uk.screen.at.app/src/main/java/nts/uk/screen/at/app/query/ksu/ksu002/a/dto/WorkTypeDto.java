package nts.uk.screen.at.app.query.ksu.ksu002.a.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 勤務種類 
 * The Class WorkType.
 */

@Getter
@NoArgsConstructor
public class WorkTypeDto {
	/* 勤務種類コード */
	public String workTypeCode;

	/* 勤務種類名称 */
	public String name;

	/* 勤務種類備考 */
	public String memo;

	public WorkTypeDto(WorkType workType) {
		super();
		this.workTypeCode = workType.getWorkTypeCode() == null ? null : workType.getWorkTypeCode().v();
		this.name = workType.getAbbreviationName() == null ? null : workType.getAbbreviationName().v();
		this.memo = workType.getMemo() == null ? null : workType.getMemo().v();
	}
}
