package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import java.util.Optional;

/**
 * @author ThanhNX
 *
 */
public interface ConvertTimeRecordStampAdapter {

	public Optional<StampDataReflectResultImport> convertData(String empInfoTerCode,
			String contractCode, StampReceptionDataImport stampReceptData);
}
