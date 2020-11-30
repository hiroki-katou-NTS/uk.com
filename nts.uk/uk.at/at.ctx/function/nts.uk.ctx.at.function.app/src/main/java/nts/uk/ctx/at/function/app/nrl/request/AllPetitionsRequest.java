package nts.uk.ctx.at.function.app.nrl.request;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.DefaultValue;
import nts.uk.ctx.at.function.app.nrl.PetitionType;
import nts.uk.ctx.at.function.app.nrl.data.ExchangeStruct;
import nts.uk.ctx.at.function.app.nrl.data.ExchangeStruct.Record;
import nts.uk.ctx.at.function.app.nrl.data.FieldName;
import nts.uk.ctx.at.function.app.nrl.exceptions.ErrorCode;
import nts.uk.ctx.at.function.app.nrl.exceptions.InvalidFieldDataException;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.ConvertTRAppServiceAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppLateReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppStampReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppWorkChangeReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.ApplicationReceptionDataImport;

/**
 * All petitions request.
 * 
 * @author manhnd
 */
@RequestScoped
@Named(value = Command.ALL_PETITIONS, decrypt = true)
public class AllPetitionsRequest extends NRLRequest<Frame> {

	@Inject
	private ConvertTRAppServiceAdapter adapter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#sketch(nts.uk.ctx.at.
	 * function.app.nrl.request.ResourceContext)
	 */
	@Override
	public void sketch(ResourceContext<Frame> context) {

		String payload = context.getEntity().pickItem(Element.PAYLOAD);
		int length = payload.length();
		int q = length / DefaultValue.SINGLE_FRAME_LEN;
		if (length % DefaultValue.SINGLE_FRAME_LEN != 0) {
			context.responseNoAccept(ErrorCode.PARAM);
			return;
		}

		ExchangeStruct exchange = ExchangeStruct.newInstance().addField(FieldName.PE_DV, 2, t -> true)
				.addField(FieldName.IDNO, 20, t -> true).addField(FieldName.YMD, 6, t -> true)
				.addField(FieldName.HMS, 6);
		String type = payload.substring(0, 2);
		switch (type) {
		case PetitionType.IO:
			exchange.addField(FieldName.PE_YMD, 6).addField(FieldName.PE_HM, 4).addField(FieldName.PE_BEF_AF, 1)
					.addField(FieldName.PE_REASON, 2).addField(FieldName.PE_IODV, 1).addField(FieldName.PE_OUTDV, 1)
					.addField(FieldName.PRELIMINARY, 15);
			break;
		case PetitionType.OT:
			exchange.addField(FieldName.PE_YMD, 6).addField(FieldName.PE_BEF_AF, 1).addField(FieldName.PE_REASON, 2)
					.addField(FieldName.PE_OT_NO1, 1).addField(FieldName.PE_OT_HM1, 4).addField(FieldName.PE_OT_NO2, 1)
					.addField(FieldName.PE_OT_HM2, 4).addField(FieldName.PE_OT_NO3, 1)
					.addField(FieldName.PRELIMINARY, 6);
			break;
		case PetitionType.OFF:
			exchange.addField(FieldName.PE_YMD_START, 6).addField(FieldName.PE_YMD_END, 6)
					.addField(FieldName.PE_BEF_AF, 1).addField(FieldName.PE_REASON, 2)
					.addField(FieldName.PE_WORKTYPE, 2).addField(FieldName.PRELIMINARY, 13);
			break;
		case PetitionType.WORKTIME:
			exchange.addField(FieldName.PE_YMD_START, 6).addField(FieldName.PE_YMD_END, 6)
					.addField(FieldName.PE_BEF_AF, 1).addField(FieldName.PE_REASON, 2)
					.addField(FieldName.PE_WORKTIME_TYPE, 3).addField(FieldName.PRELIMINARY, 12);
			break;
		case PetitionType.OFFWORK:
			exchange.addField(FieldName.PE_YMD, 6).addField(FieldName.PE_BEF_AF, 1).addField(FieldName.PE_REASON, 2)
					.addField(FieldName.PE_OFFWORK_NO1, 1).addField(FieldName.PE_OFFWORK_HM1, 4)
					.addField(FieldName.PE_OFFWORK_NO2, 1).addField(FieldName.PE_OFFWORK_HM2, 4)
					.addField(FieldName.PE_OFFWORK_NO3, 1).addField(FieldName.PE_OFFWORK_HM3, 4)
					.addField(FieldName.PRELIMINARY, 6);
			break;
		case PetitionType.EARLY_LATE_CANCEL:
			exchange.addField(FieldName.PE_YMD, 6).addField(FieldName.PE_BEF_AF, 1).addField(FieldName.PE_REASON, 2)
					.addField(FieldName.PE_EARLY_LATE_REASON, 1).addField(FieldName.PRELIMINARY, 20);
			break;
		case PetitionType.TIME_OFF:
			exchange.addField(FieldName.PE_YMD, 6).addField(FieldName.PE_BEF_AF, 1).addField(FieldName.PE_REASON, 2)
					.addField(FieldName.PE_OFF_TYPE, 1).addField(FieldName.PE_OFF_TIME, 18);
			break;
		}

		for (int i = 0; i < q; i++) {
			int rLen = i * DefaultValue.SINGLE_FRAME_LEN;
			try {
				exchange.parseAppend(payload.substring(rLen, rLen + DefaultValue.SINGLE_FRAME_LEN));
			} catch (InvalidFieldDataException ex) {
				continue;
			}
		}

		String nrlNo = context.getEntity().pickItem(Element.NRL_NO);
		for (int i = 0; i < q; i++) {
			Record record = exchange.getRecord(i);

			Optional<AtomTask> result = adapter.converData(nrlNo.trim(), "000000000000",
					createAppReception(record, type));
			if (result.isPresent())
				result.get().run();
		}

		context.responseAccept();
	}

	private ApplicationReceptionDataImport createAppReception(Record record, String type) {

		ApplicationReceptionDataImport common = new ApplicationReceptionDataImport(record.get(FieldName.IDNO),
				record.get(FieldName.PE_DV), record.get(FieldName.YMD), record.get(FieldName.HMS));
		switch (type) {
		case PetitionType.IO:
			// IO : 打刻 AppStampReceptionDataImport
			return new AppStampReceptionDataImport(common, record.get(FieldName.PE_OUTDV),
					record.get(FieldName.PE_BEF_AF), record.get(FieldName.PE_HM), record.get(FieldName.PE_YMD),
					record.get(FieldName.PE_IODV), record.get(FieldName.PE_REASON));
		case PetitionType.OT:
			return null;

		case PetitionType.OFF:
			return null;

		case PetitionType.WORKTIME:
			// WORKTIME : 勤務変更申請 AppWorkChangeReceptionDataImport
			return new AppWorkChangeReceptionDataImport(common, record.get(FieldName.PE_BEF_AF),
					record.get(FieldName.PE_YMD_START), record.get(FieldName.PE_YMD_END),
					record.get(FieldName.PE_WORKTIME_TYPE), record.get(FieldName.PE_REASON));

		case PetitionType.OFFWORK:
			return null;
		case PetitionType.EARLY_LATE_CANCEL:
			// EARLY_LATE_CANCEL: 遅刻早退取消申請 AppLateReceptionDataImport
			return new AppLateReceptionDataImport(common, record.get(FieldName.PE_BEF_AF), record.get(FieldName.PE_YMD),
					record.get(FieldName.PE_EARLY_LATE_REASON), record.get(FieldName.PE_REASON));
		case PetitionType.TIME_OFF:
			return null;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#responseLength()
	 */
	@Override
	public String responseLength() {
		return null;
	}

}
