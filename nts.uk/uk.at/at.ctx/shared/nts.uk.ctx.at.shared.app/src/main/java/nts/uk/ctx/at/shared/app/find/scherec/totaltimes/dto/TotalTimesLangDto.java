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
	/** 会社ID */
	private String companyId;
	
	/** 回数集計No */
	private Integer totalCountNo;
	
	/** 回数集計名称 */
	private String totalTimesNameEng;
	
	public static TotalTimesLangDto fromDomain(TotalTimesLang timeLang) {
		return new TotalTimesLangDto(timeLang.getCompanyId(), timeLang.getTotalCountNo(), timeLang.getTotalTimesNameEng().v());
	}
}
