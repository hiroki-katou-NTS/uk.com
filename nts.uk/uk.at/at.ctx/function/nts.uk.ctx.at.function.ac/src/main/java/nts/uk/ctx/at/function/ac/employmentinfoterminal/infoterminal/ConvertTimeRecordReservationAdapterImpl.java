package nts.uk.ctx.at.function.ac.employmentinfoterminal.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.ConvertTimeRecordReservationAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.ReservReceptDataImport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.ConvertTimeRecordReservationPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.ReservReceptDataExport;

@Stateless
public class ConvertTimeRecordReservationAdapterImpl implements ConvertTimeRecordReservationAdapter {

	@Inject
	private ConvertTimeRecordReservationPub timeRecordReservationPub;

	@Override
	public Optional<AtomTask> convertData(Integer empInfoTerCode, String contractCode,
			ReservReceptDataImport reservReceptData) {
		return timeRecordReservationPub.convertData(empInfoTerCode, contractCode,
				new ReservReceptDataExport(reservReceptData.getIdNumber(), reservReceptData.getMenu(),
						reservReceptData.getYmd(), reservReceptData.getTime(), reservReceptData.getQuantity()));
	}

}
