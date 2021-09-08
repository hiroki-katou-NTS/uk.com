package nts.uk.ctx.at.record.app.find.stamp.management;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampDataOfEmployeesDto;

@Getter
@Setter
@NoArgsConstructor
public class DisplayScreenStampingResultDto {
	
	/** 
	 * 勤務場所名 
	 */
	public String workPlaceName;
	
	public StampDataOfEmployeesDto stampDataOfEmployeesDto;
	
	/**
	 * 職場コード
	 */
	private String workplaceCd;
	
	/**
	 * 職場名称
	 */
	private String workplaceNm;
	
	public DisplayScreenStampingResultDto(String workPlaceName, StampDataOfEmployeesDto stampDataOfEmployeesDto, String workplaceCd, String workplaceName ) {
		super();
		this.workPlaceName = workPlaceName;
		this.stampDataOfEmployeesDto = stampDataOfEmployeesDto;
		this.workplaceCd =workplaceCd;
		this.workplaceNm = workplaceName;
	}	
}


