package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import java.util.Optional;

public interface SendTimeRecordSettingPub {

	public Optional<SendTimeRecordSettingExport> send(Integer empInfoTerCode, String contractCode);
}
