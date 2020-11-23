package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.task.tran.AtomTask;

/**
 * @author ThanhNX
 *
 */
public interface ConvertTimeRecordStampAdapter {

	public Pair<Optional<AtomTask>, Optional<StampDataReflectResultImport>> convertData(String empInfoTerCode,
			String contractCode, StampReceptionDataImport stampReceptData);
}
