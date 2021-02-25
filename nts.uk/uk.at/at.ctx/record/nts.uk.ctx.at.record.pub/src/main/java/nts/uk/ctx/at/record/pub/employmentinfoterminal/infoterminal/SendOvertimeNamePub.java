package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import java.util.Optional;

public interface SendOvertimeNamePub {

	public Optional<SendOvertimeNameExport> send(String empInfoTerCode, String contractCode);

}
