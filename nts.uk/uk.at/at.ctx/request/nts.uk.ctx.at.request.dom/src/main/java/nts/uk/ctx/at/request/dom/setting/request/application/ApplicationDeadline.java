package nts.uk.ctx.at.request.dom.setting.request.application;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.UseAtr;

/**
 * 申請締切設定
 * @author dudt
 *
 */
@Value
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class ApplicationDeadline extends AggregateRoot {
	/**　会社ID*/
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
	private DeadlineCriteria deadlineCriteria;

	public static ApplicationDeadline createSimpleFromJavaType(String companyId,
			int closureId, 
			int userAtr,
			int deadline,
			int deadlineCriteria) {
		return new ApplicationDeadline(companyId,
				closureId,
				EnumAdaptor.valueOf(userAtr, UseAtr.class),
				new Deadline(deadline),
				EnumAdaptor.valueOf(deadlineCriteria, DeadlineCriteria.class));
		
	}
}
