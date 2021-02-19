package nts.uk.ctx.at.shared.dom.scherec.totaltimes.language;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesName;
/**
 * 
 * @author phongtq
 * 回数集計の他言語表示名
 */
@Getter
public class TotalTimesLang extends AggregateRoot {
		
	/**
	 *  会社ID
	 */
	private String companyId;

	/**
	 *  回数集計NO
	 */
	private Integer totalCountNo;
	
	/**
	 *  言語ID
	 */
	private String langId;
	
	/**
	 * 回数集計名称
	 */
	private TotalTimesName totalTimesNameEng;

	public TotalTimesLang(String companyId, Integer totalCountNo, String langId,
			TotalTimesName totalTimesNameEng) {
		super();
		this.companyId = companyId;
		this.totalCountNo = totalCountNo;
		this.langId = langId;
		this.totalTimesNameEng = totalTimesNameEng;
	}
	
	public static TotalTimesLang createFromJavaType(String companyId, Integer totalCountNo, String langId, String totalTimesNameEng) {
		return new TotalTimesLang(companyId, totalCountNo, langId, new TotalTimesName(totalTimesNameEng));
	}
}
