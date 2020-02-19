package nts.uk.ctx.at.shared.dom.worktime.language;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeAbName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeName;

/**
 * 就業時間帯の他言語表示名
 * 
 * @author sonnh1
 *
 */
@Getter
public class WorkTimeLanguage {
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 勤務種類コード
	 */
	private WorkTimeCode workTimeCode;

	/**
	 * 言語ID
	 */
	private String langId;

	/**
	 * 名称
	 */
	private WorkTimeName name;

	/**
	 * 略名
	 */
	private WorkTimeAbName abbreviationName;

	public WorkTimeLanguage(String companyId, WorkTimeCode workTimeCode, String langId, WorkTimeName name,
			WorkTimeAbName abbreviationName) {
		super();
		this.companyId = companyId;
		this.workTimeCode = workTimeCode;
		this.langId = langId;
		this.name = name;
		this.abbreviationName = abbreviationName;
	}

	public static WorkTimeLanguage createFromJavaType(String companyId, String workTimeCode, String langId, String name,
			String abbreviationName) {
		return new WorkTimeLanguage(companyId, new WorkTimeCode(workTimeCode), langId, new WorkTimeName(name),
				new WorkTimeAbName(abbreviationName));
	}

}
