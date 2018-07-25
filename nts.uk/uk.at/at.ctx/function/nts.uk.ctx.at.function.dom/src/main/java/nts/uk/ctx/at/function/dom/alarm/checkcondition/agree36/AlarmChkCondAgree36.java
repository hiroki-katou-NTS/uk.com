package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 36協定のアラームチェック条件
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
public class AlarmChkCondAgree36 extends DomainObject{
	private List<AgreeConditionError> listCondError;
	private List<AgreeCondOt> listCondOt;
	
}
