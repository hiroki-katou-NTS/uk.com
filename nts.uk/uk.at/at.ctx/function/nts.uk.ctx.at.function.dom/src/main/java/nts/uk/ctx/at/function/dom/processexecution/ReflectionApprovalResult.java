package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class Reflection approval result.<br>
 * 承認結果の反映
 *
 * @author nws-minhnb
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReflectionApprovalResult extends DomainObject {

	/**
	 * The Reflection result classification.<br>
	 * 使用区分
	 */
	private NotUseAtr reflectResultCls;

	/**
	 * Instantiates a new <code>ReflectionApprovalResult</code>.
	 *
	 * @param reflectResultCls the reflection result classification
	 */
	public ReflectionApprovalResult(int reflectResultCls) {
		this.reflectResultCls = EnumAdaptor.valueOf(reflectResultCls, NotUseAtr.class);
	}

}
