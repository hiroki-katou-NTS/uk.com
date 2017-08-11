package nts.uk.ctx.at.request.dom.setting.request.application;


import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.UseAtr;

/**
 * 申請締切設定
 * @author dudt
 *
 */
@AllArgsConstructor
public class ApplicationDeadlineSetting {
	/**　会社ID*/
	public String companyId;
	/**
	 * 締めＩＤ
	 */
	public int closureId;
	/**
	 * 申請種類
	 */
	public ApplicationType appType;
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

	public static ApplicationDeadlineSetting createSimpleFromJavaType(String companyId,int closureId, int appType, int userAtr, String deadline, int deadlineCriteria) {
		return new ApplicationDeadlineSetting(companyId,
				closureId,
				EnumAdaptor.valueOf(appType, ApplicationType.class),
				EnumAdaptor.valueOf(userAtr, UseAtr.class),
				new Deadline(deadline),
				EnumAdaptor.valueOf(deadlineCriteria, DeadlineCriteria.class));
		
	}
}
