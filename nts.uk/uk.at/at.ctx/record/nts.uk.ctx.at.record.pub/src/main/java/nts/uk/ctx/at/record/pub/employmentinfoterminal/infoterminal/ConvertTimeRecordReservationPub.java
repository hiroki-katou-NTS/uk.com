package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;

public interface ConvertTimeRecordReservationPub {

	public Optional<AtomTask> convertData(String empInfoTerCode,
			String contractCode, ReservReceptDataExport reservReceptData, String companyID);

}
