package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InitWorkTypeWorkTime_Old {
	
	/**
	 * 初期選択勤務種類
	 */
	private String workTypeCD;
	
	/**
	 * 初期選択勤務種類名称
	 */
	private String workTypeName;
	
	/**
	 * 初期選択就業時間帯
	 */
	private String workTimeCD;
	
	/**
	 * 初期選択就業時間帯名称
	 */
	private String workTimeName;
	
}
