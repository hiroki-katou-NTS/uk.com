package nts.uk.ctx.at.function.dom.processexecution.alarmextraction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * アラーム抽出（職場別）
 */
@Getter
@AllArgsConstructor
public class WorkplaceAlarmExtraction extends DomainObject {
	/* アラーム抽出（職場別） */
	private boolean wkpAlarmCls;
	
	/* 管理者にメール送信する */
	private boolean wkpMailMng;
}
