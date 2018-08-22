package nts.uk.ctx.at.function.dom.attendanceitemname.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OvertimeDto {
	/** The super holiday 60 H occurs. */
	// 60H超休が発生する
	private boolean superHoliday60HOccurs;
	
	/** The use classification. */
	// 使用区分
	private int useClassification;
	
	/** The name. */
	//名称
	private String name;

	/** The overtime. */
	// 超過時間
	private int overtime;
	
	/** The overtime no. */
	// 超過時間NO
	private int overtimeNo;

	public OvertimeDto(boolean superHoliday60HOccurs, int useClassification, String name, int overtime,
			int overtimeNo) {
		super();
		this.superHoliday60HOccurs = superHoliday60HOccurs;
		this.useClassification = useClassification;
		this.name = name;
		this.overtime = overtime;
		this.overtimeNo = overtimeNo;
	}
	
	
}
