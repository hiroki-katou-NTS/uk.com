package nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition;

import java.util.List;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;


public interface ExtraResultMonthlyFunAdapter {
	/**
	 * 月別実績の抽出条件 を取得
	 * @param listEralCheckID
	 * @return
	 */
	List<ExtraResultMonthlyDomainEventDto> getListExtraResultMonByListEralID(List<String> listEralCheckID);
}
