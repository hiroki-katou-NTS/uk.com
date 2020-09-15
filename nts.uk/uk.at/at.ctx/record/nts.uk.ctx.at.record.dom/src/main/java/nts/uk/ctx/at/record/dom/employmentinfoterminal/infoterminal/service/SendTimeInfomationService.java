package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendTimeInfomation;

/**
 * @author ThanhNX
 *
 *         時刻合わせをNRに 送信する
 */
public class SendTimeInfomationService {

	private SendTimeInfomationService() {
	};

	// [1] システム時刻を取得する
	public static SendTimeInfomation send() {
		GeneralDateTime time = GeneralDateTime.now();
		return new SendTimeInfomation(time.year() % 100, time.month(), time.day(), time.hours(), time.minutes(),
				time.seconds(), time.dayOfWeek() - 1);
	}
}
