package nts.uk.file.pr.app.export.banktransfer;

import java.util.List;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferARpData;

public interface BankTransferRpAGenerator {
	void generator(FileGeneratorContext fileGeneratorContext, List<BankTransferARpData> report);
}
