package nts.uk.file.pr.app.export.banktransfer;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferIReport;

public interface BankTransferRpIGenerator {
	void generate(FileGeneratorContext fileContext, BankTransferIReport reportData);
}
