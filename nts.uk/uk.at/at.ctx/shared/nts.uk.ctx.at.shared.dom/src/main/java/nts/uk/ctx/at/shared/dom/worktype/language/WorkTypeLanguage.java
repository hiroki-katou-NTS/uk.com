package nts.uk.ctx.at.shared.dom.worktype.language;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeAbbreviationName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
/**
 * 勤務種類の他言語表示名
 * 
 * @author sonnh
 *
 */
@Getter
public class WorkTypeLanguage {

	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 勤務種類コード
	 */
	private WorkTypeCode workTypeCode;
	
	/**
	 * 言語ID
	 */
	private String langId;
	
	/**
	 * 名称
	 */
	private WorkTypeName name;
	
	/**
	 * 略名
	 */
	private WorkTypeAbbreviationName abbreviationName;

	/**
	 * 
	 * @param companyId
	 * @param workTypeCode
	 * @param langId
	 * @param name
	 * @param abbreviationName
	 */
	public WorkTypeLanguage(String companyId, WorkTypeCode workTypeCode, String langId, WorkTypeName name,
			WorkTypeAbbreviationName abbreviationName) {
		super();
		this.companyId = companyId;
		this.workTypeCode = workTypeCode;
		this.langId = langId;
		this.name = name;
		this.abbreviationName = abbreviationName;
	}

	/**
	 * 
	 * @param companyId
	 * @param workTypeCode
	 * @param langId
	 * @param name
	 * @param abName
	 * @return
	 */
	public static WorkTypeLanguage createFromJavaType(String companyId, String workTypeCode, String langId, String name,
			String abName) {
		return new WorkTypeLanguage(companyId, new WorkTypeCode(workTypeCode), langId, new WorkTypeName(name),
				new WorkTypeAbbreviationName(abName));
	}
}
