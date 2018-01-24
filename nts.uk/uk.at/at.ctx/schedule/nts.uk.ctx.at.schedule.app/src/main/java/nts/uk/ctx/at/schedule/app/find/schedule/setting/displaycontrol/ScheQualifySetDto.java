package nts.uk.ctx.at.schedule.app.find.schedule.setting.displaycontrol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.ScheQualifySet;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheQualifySetDto {
	/** 会社ID */
	private String companyId;
	
	/** 資格コード  */
	private String qualifyCode;
	
	/**
	 * From domain
	 * 
	 * @param domain
	 * @return
	 */
	public static ScheQualifySetDto fromDomain(ScheQualifySet domain){
		
		return new ScheQualifySetDto(
				domain.getCompanyId(),
				domain.getQualifyCode()
		);
	}
}
