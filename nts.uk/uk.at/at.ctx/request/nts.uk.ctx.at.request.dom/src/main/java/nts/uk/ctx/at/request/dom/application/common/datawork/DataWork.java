package nts.uk.ctx.at.request.dom.application.common.datawork;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataWork {	
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
}
