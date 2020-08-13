package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.deadlinesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.application.UseAtr;
/**
 * 申請締切設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class AppDeadlineSetting extends DomainObject {
	
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 締めＩＤ
	 */
	private int closureId;
	
	/**
	 * 利用区分
	 */
	private UseAtr userAtr;
	
	/**
	 * 締切日数
	 */
	private Deadline deadline;
	
	/**
	 * 締切基準
	 */
	// private DeadlineCriteria deadlineCriteria;
	
	public static AppDeadlineSetting createSimpleFromJavaType(String companyId,
			int closureId, 
			int userAtr,
			int deadline,
			int deadlineCriteria) {
//		return new AppDeadlineSetting(companyId,
//				closureId,
//				EnumAdaptor.valueOf(userAtr, UseAtr.class),
//				new Deadline(deadline),
//				EnumAdaptor.valueOf(deadlineCriteria, DeadlineCriteria.class));
		return null;
	}
}
