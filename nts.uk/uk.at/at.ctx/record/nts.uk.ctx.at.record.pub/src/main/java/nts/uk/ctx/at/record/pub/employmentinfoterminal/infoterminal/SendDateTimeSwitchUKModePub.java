package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import java.util.Optional;

/**
 * @author thanh_nx
 *
 *         UKモードへの切替日時をNRに 送信する
 */
public interface SendDateTimeSwitchUKModePub {

	// 設定を送る
	public Optional<DateTimeSwitchUKModeExport> process(String empInfoTerCode, String contractCode);
}
