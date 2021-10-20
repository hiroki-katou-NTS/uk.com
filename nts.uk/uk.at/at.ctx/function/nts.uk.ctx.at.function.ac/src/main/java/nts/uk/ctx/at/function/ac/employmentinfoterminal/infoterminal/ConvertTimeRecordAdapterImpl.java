package nts.uk.ctx.at.function.ac.employmentinfoterminal.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.ConvertTimeRecordStampAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.StampDataReflectResultImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.StampReceptionDataImport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.ConvertTimeRecordStampPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.StampReceptionDataExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.StampReceptionDataExport.StampDataExportBuilder;

/**
 * @author ThanhNX
 *
 */
@Stateless
public class ConvertTimeRecordAdapterImpl implements ConvertTimeRecordStampAdapter {

	@Inject
	private ConvertTimeRecordStampPub timeRecordStampPub;

	@Override
	public Optional<StampDataReflectResultImport> convertData(String empInfoTerCode,
			String contractCode, StampReceptionDataImport stampReceptData) {

		val convertDataOpt = timeRecordStampPub
				.convertData(empInfoTerCode, contractCode,
						new StampReceptionDataExport(new StampDataExportBuilder(stampReceptData.getIdNumber(),
								stampReceptData.getCardCategory(), stampReceptData.getShift(),
								stampReceptData.getLeavingCategory(), stampReceptData.getYmd(),
								stampReceptData.getSupportCode()).overTimeHours(stampReceptData.getOverTimeHours())
										.midnightTime(stampReceptData.getMidnightTime())
										.time(stampReceptData.getTime())));
		return convertDataOpt.map(convertData -> new StampDataReflectResultImport(convertData.getReflectDate(),
								convertData.getAtomTask()));
	}

}
