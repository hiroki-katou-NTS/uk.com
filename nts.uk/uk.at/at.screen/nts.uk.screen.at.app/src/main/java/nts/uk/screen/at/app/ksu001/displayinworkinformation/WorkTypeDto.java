/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.ksu001.displayinworkinformation;

import java.util.List;
import java.util.stream.Collectors;

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


	/* 会社ID */
	public String companyId;

	/* 勤務種類コード */
	public String workTypeCode;

	/* 勤務種類記号名 */
	public String symbolicName;

	/* 勤務種類名称 */
	public String name;

	/* 勤務種類略名 */
	public String abbreviationName;

	/* 勤務種類備考 */
	public String memo;

	// 1日の勤務
	public DailyWorkDto dailyWork;

	// 廃止区分
	public int deprecate;

	// 出勤率の計算
	public int calculateMethod;

	public List<WorkTypeSetDto> workTypeSetList;

	public Integer dispOrder;

	public WorkTypeDto(WorkType workType) {
		super();
		this.companyId = workType.getCompanyId();
		this.workTypeCode = workType.getWorkTypeCode() == null ? null : workType.getWorkTypeCode().v();
		this.symbolicName = workType.getSymbolicName() == null ? null : workType.getSymbolicName().v();
		this.name = workType.getName()  == null ? null :  workType.getName().v();
		this.abbreviationName = workType.getAbbreviationName() == null ? null : workType.getAbbreviationName().v();
		this.memo = workType.getMemo() == null ? null : workType.getMemo().v();
		this.dailyWork = new DailyWorkDto(workType.getDailyWork());
		this.deprecate = workType.getDeprecate() == null ? null : workType.getDeprecate().value ;
		this.calculateMethod = workType.getCalculateMethod()  == null ? null : workType.getCalculateMethod().value ;
		this.dispOrder = workType.getDispOrder();
		List<WorkTypeSetDto> workTypeSetList = workType.getWorkTypeSetList().stream().map(mapper -> {
			return new WorkTypeSetDto(mapper);
		}).collect(Collectors.toList());
		this.workTypeSetList = workTypeSetList;
	}

	
	
}
