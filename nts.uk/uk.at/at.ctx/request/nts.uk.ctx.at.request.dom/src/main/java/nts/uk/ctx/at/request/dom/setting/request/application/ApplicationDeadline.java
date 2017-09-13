package nts.uk.ctx.at.request.dom.setting.request.application;


import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.common.UseAtr;

/**
 * 申請締切設定
 * @author dudt
 *
 */
@AllArgsConstructor
public class ApplicationDeadline extends AggregateRoot {
	/**　会社ID*/
	public String companyId;
	/**
	 * 締めＩＤ
	 */
	public int closureId;
	
	/**
	 * 利用区分
	 */
	public UseAtr userAtr;
	/**
	 * 月間日数
	 */
	public Deadline deadline;
	/**
	 * 締切基準
	 */
	public DeadlineCriteria deadlineCriteria;

	public static ApplicationDeadline createSimpleFromJavaType(String companyId,
			int closureId, 
			int userAtr,
			String deadline,
			int deadlineCriteria) {
		return new ApplicationDeadline(companyId,
				closureId,
				EnumAdaptor.valueOf(userAtr, UseAtr.class),
				new Deadline(deadline),
				EnumAdaptor.valueOf(deadlineCriteria, DeadlineCriteria.class));
		
	}
}
