package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import java.util.Optional;

public interface SendDateTimeSwitchUKModeAdapter {
	// 設定を送る
	public Optional<DateTimeSwitchUKModeImport> process(String empInfoTerCode, String contractCode);
}
