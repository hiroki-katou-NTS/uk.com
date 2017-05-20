package nts.uk.file.pr.app.export.banktransfer;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferCReport;

public interface BankTransferRpCGenerator {
	void generate(FileGeneratorContext fileContext, BankTransferCReport reportData);
}
