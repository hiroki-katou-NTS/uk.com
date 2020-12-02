package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 目安利用区分
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class UsageSetting implements DomainAggregate{
	/** 会社ID */
	private final String cid;
	
	/** 雇用利用区分 */
	private NotUseAtr employMentUse;
}