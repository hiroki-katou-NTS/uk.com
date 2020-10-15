package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;

/**
 * @author ThanhNX
 *
 */
public interface ConvertTimeRecordReservationAdapter {

	public Optional<AtomTask> convertData(Integer empInfoTerCode, String contractCode,
			ReservReceptDataImport reservReceptData);

}
