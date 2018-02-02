package nts.uk.ctx.at.function.dom.processexecution.alarmextraction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * アラーム抽出（個人別）
 */
@Getter
@AllArgsConstructor
public class IndividualAlarmExtraction extends DomainObject {
	/* アラーム抽出（個人別） */
	private boolean indvAlarmCls;
	
	/* 本人にメール送信する */
	private boolean indvMailPrin;
	
	/* 管理者にメール送信する */
	private boolean indvMailMng;
}
