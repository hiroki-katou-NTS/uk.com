package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import java.util.Optional;

/**
 * @author ThanhNX
 *
 */
public interface ConvertTimeRecordStampPub {

	public Optional<StampDataReflectResultExport> convertData(String empInfoTerCode,
			String contractCode, StampReceptionDataExport stampReceptData);
}
