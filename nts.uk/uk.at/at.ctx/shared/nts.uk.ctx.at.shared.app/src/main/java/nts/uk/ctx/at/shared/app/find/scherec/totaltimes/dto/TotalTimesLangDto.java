package nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.language.TotalTimesLang;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class TotalTimesLangDto {

	private String companyId;
	
	/** The total count no. */
	private Integer totalCountNo;
	
	/** The total times name. */
	private String totalTimesNameEng;
	
	public static TotalTimesLangDto fromDomain(TotalTimesLang timeLang) {
		return new TotalTimesLangDto(timeLang.getCompanyId(), timeLang.getTotalCountNo(), timeLang.getTotalTimesNameEng().v());
	}
}
