package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.task.tran.AtomTask;

/**
 * @author ThanhNX
 *
 */
public interface ConvertTimeRecordStampPub {

	public Pair<Optional<AtomTask>, Optional<StampDataReflectResultExport>> convertData(Integer empInfoTerCode,
			String contractCode, StampReceptionDataExport stampReceptData);
}
