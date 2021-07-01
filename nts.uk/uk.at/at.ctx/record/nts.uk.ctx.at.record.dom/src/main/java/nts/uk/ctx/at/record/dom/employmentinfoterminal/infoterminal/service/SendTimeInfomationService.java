package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import org.apache.commons.lang3.StringUtils;

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
		return new SendTimeInfomation(hexPadding(time.year(), 4), hexPadding(time.month(), 2), hexPadding(time.day(), 2), hexPadding(time.hours(), 2), hexPadding(time.minutes(), 2),
				hexPadding(time.seconds(), 2), hexPadding(time.dayOfWeek() - 1, 2));
	}
	
	// [S-1] hexパディング
	private static String hexPadding(int data, int length) {
		return StringUtils.leftPad(Integer.toHexString(data), length, "0").toUpperCase();
	}
}
