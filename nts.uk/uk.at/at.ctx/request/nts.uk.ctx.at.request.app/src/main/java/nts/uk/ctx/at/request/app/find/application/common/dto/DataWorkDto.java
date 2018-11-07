package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.datawork.DataWork;


@AllArgsConstructor
@Value
public class DataWorkDto {
	/**
	 * 選択可能な勤務種類コード
	 */
	List<String> workTypeCodes;
	/**
	 * 選択可能な就業時間帯コード
	 */
	List<String> workTimeCodes;
	/**
	 * 選択中の勤務種類コード
	 */
	String selectedWorkTypeCd;
	/**
	 * 選択中の勤務種類名
	 */	
	String selectedWorkTypeName;
	/**
	 * 選択中の就業時間帯コード
	 */
	String selectedWorkTimeCd;
	/**
	 * 選択中の就業時間帯名
	 */
	String selectedWorkTimeName;
	
	Integer startTime1;
	
	Integer endTime1;
	
	public static DataWorkDto fromDomain(DataWork domain) {
		return new DataWorkDto(domain.getWorkTypeCodes(), domain.getWorkTimeCodes(), domain.getSelectedWorkTypeCd(),
				domain.getSelectedWorkTypeName(), domain.getSelectedWorkTimeCd(), domain.getSelectedWorkTimeName(),
				domain.getStartTime1(), domain.getEndTime1());
	}
}
