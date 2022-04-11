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
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.ConvertTRAppServiceAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AnnualHolidayReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppLateReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppOverTimeReceptionDataImport.AppOverTimBuilder;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppStampReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppVacationReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppWorkChangeReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppWorkHolidayReceptionDataImport.AppWorkHolidayBuilder;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.ApplicationReceptionDataImport;

/**
 * All petitions request.
 * 
 * NRLの申請をオブジェクトに変換する
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
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {

		String payload = context.getEntity().pickItem(Element.PAYLOAD);
		int length = payload.length();
		int q = length / DefaultValue.SINGLE_FRAME_LEN;
		if (length % DefaultValue.SINGLE_FRAME_LEN != 0) {
			context.responseNoAccept(ErrorCode.PARAM);
			return;
		}

		String contractCode = context.getEntity().pickItem(Element.CONTRACT_CODE);
		for (int i = 0; i < q; i++) {
			int rLen = i * DefaultValue.SINGLE_FRAME_LEN;
//			try {
			String payloadFrame = payload.substring(rLen, rLen + DefaultValue.SINGLE_FRAME_LEN);
			ExchangeStruct exchange = createExchangeStruct(payloadFrame.substring(0, 2).trim());
			exchange.parseAppend(payloadFrame);
			Record record = exchange.getRecord(0);

			Optional<AtomTask> result = adapter.converData(empInfoTerCode, contractCode,
					createAppReception(record, record.getTrim(FieldName.PE_DV)));
			if (result.isPresent())
				result.get().run();

//			} catch (InvalidFieldDataException ex) {
//				continue;
//			}
		}

		context.responseAccept();
	}

	private ExchangeStruct createExchangeStruct(String type) {
		ExchangeStruct exchange = ExchangeStruct.newInstance().addField(FieldName.PE_DV, 2, t -> true)
				.addField(FieldName.IDNO, 20, t -> true).addField(FieldName.YMD, 6, t -> true)
				.addField(FieldName.HMS, 6);
		switch (type) {
		case PetitionType.STAMP:
			exchange.addField(FieldName.PE_YMD, 6).addField(FieldName.PE_HM, 4).addField(FieldName.PE_BEF_AF, 1)
					.addField(FieldName.PE_REASON, 2).addField(FieldName.PE_IODV, 1).addField(FieldName.PE_OUTDV, 1)
					.addField(FieldName.PRELIMINARY, 15);
			break;
		case PetitionType.OVERTIME:
			exchange.addField(FieldName.PE_YMD, 6).addField(FieldName.PE_BEF_AF, 1).addField(FieldName.PE_REASON, 2)
					.addField(FieldName.PE_OT_NO1, 1).addField(FieldName.PE_OT_HM1, 4).addField(FieldName.PE_OT_NO2, 1)
					.addField(FieldName.PE_OT_HM2, 4).addField(FieldName.PE_OT_NO3, 1).addField(FieldName.PE_OT_HM3, 4)
					.addField(FieldName.PRELIMINARY, 6);
			break;
		case PetitionType.VACATION:
			exchange.addField(FieldName.PE_YMD_START, 6).addField(FieldName.PE_YMD_END, 6)
					.addField(FieldName.PE_BEF_AF, 1).addField(FieldName.PE_REASON, 2)
					.addField(FieldName.PE_WORKTYPE, 3).addField(FieldName.PRELIMINARY, 12);
			break;
		case PetitionType.WORK_CHANGE:
			exchange.addField(FieldName.PE_YMD_START, 6).addField(FieldName.PE_YMD_END, 6)
					.addField(FieldName.PE_BEF_AF, 1).addField(FieldName.PE_REASON, 2)
					.addField(FieldName.PE_WORKTIME_TYPE, 3).addField(FieldName.PRELIMINARY, 12);
			break;
		case PetitionType.WORK_HOLIDAY:
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
		case PetitionType.ANNUAL:
			exchange.addField(FieldName.PE_YMD, 6).addField(FieldName.PE_BEF_AF, 1).addField(FieldName.PE_REASON, 2)
					.addField(FieldName.PE_OFF_TYPE, 1).addField(FieldName.PE_OFF_TIME, 4)
					.addField(FieldName.PRELIMINARY, 16);
			break;
		}
		return exchange;
	}
	
	private ApplicationReceptionDataImport createAppReception(Record record, String type) {

		ApplicationReceptionDataImport common = new ApplicationReceptionDataImport(record.getTrim(FieldName.IDNO),
				record.getTrim(FieldName.PE_DV), record.getTrim(FieldName.YMD), record.getTrim(FieldName.HMS));
		
		switch (type) {
		case PetitionType.STAMP:
			// IO : 打刻 AppStampReceptionDataImport
			return new AppStampReceptionDataImport(common, record.getTrim(FieldName.PE_OUTDV),
					record.getTrim(FieldName.PE_BEF_AF), record.getTrim(FieldName.PE_HM), record.getTrim(FieldName.PE_YMD),
					record.getTrim(FieldName.PE_IODV), record.getTrim(FieldName.PE_REASON));
		case PetitionType.OVERTIME:
			return new AppOverTimBuilder(record.getTrim(FieldName.IDNO),
					record.getTrim(FieldName.PE_DV),  record.getTrim(FieldName.YMD), record.getTrim(FieldName.HMS), 
					record.getTrim(FieldName.PE_OT_HM1), record.getTrim(FieldName.PE_OT_NO1))
					        .overtimeHour2(record.getTrim(FieldName.PE_OT_HM2))
							.overtimeNo2(record.getTrim(FieldName.PE_OT_NO2))
							.overtimeHour3(record.getTrim(FieldName.PE_OT_HM3))
							.overtimeNo3(record.getTrim(FieldName.PE_OT_NO3))
							.typeBeforeAfter(record.getTrim(FieldName.PE_BEF_AF))
							.appYMD(record.getTrim(FieldName.PE_YMD))
							.reason(record.getTrim(FieldName.PE_REASON)).build();

		case PetitionType.VACATION:
			return new AppVacationReceptionDataImport(
					new ApplicationReceptionDataImport(record.getTrim(FieldName.IDNO),
							record.getTrim(FieldName.PE_DV),  record.getTrim(FieldName.YMD), record.getTrim(FieldName.HMS)),
					record.getTrim(FieldName.PE_BEF_AF), record.getTrim(FieldName.PE_YMD_START), record.getTrim(FieldName.PE_YMD_END),
					record.getTrim(FieldName.PE_WORKTYPE), record.getTrim(FieldName.PE_REASON));

		case PetitionType.WORK_CHANGE:
			// WORKTIME : 勤務変更申請 AppWorkChangeReceptionDataImport
			return new AppWorkChangeReceptionDataImport(common, record.getTrim(FieldName.PE_BEF_AF),
					record.getTrim(FieldName.PE_YMD_START), record.getTrim(FieldName.PE_YMD_END),
					record.getTrim(FieldName.PE_WORKTIME_TYPE), record.getTrim(FieldName.PE_REASON));

		case PetitionType.WORK_HOLIDAY:
			return new AppWorkHolidayBuilder(record.getTrim(FieldName.IDNO),
					record.getTrim(FieldName.PE_DV),  record.getTrim(FieldName.YMD), record.getTrim(FieldName.HMS), 
					record.getTrim(FieldName.PE_OFFWORK_HM1),  record.getTrim(FieldName.PE_OFFWORK_NO1))
							.breakTime2(record.getTrim(FieldName.PE_OFFWORK_HM2)).breakNo2(record.getTrim(FieldName.PE_OFFWORK_NO2))
							.breakTime3(record.getTrim(FieldName.PE_OFFWORK_HM3)).breakNo3(record.getTrim(FieldName.PE_OFFWORK_NO3))
							.typeBeforeAfter(record.getTrim(FieldName.PE_BEF_AF)).appYMD(record.getTrim(FieldName.PE_YMD))
							.reason(record.getTrim(FieldName.PE_REASON)).build();
			
		case PetitionType.EARLY_LATE_CANCEL:
			// EARLY_LATE_CANCEL: 遅刻早退取消申請 AppLateReceptionDataImport
			return new AppLateReceptionDataImport(common, record.getTrim(FieldName.PE_BEF_AF), record.getTrim(FieldName.PE_YMD),
					record.getTrim(FieldName.PE_EARLY_LATE_REASON), record.getTrim(FieldName.PE_REASON));
			
		case PetitionType.ANNUAL:
			return new AnnualHolidayReceptionDataImport(
					new ApplicationReceptionDataImport(record.getTrim(FieldName.IDNO),
							record.getTrim(FieldName.PE_DV),  record.getTrim(FieldName.YMD), record.getTrim(FieldName.HMS)),
					record.getTrim(FieldName.PE_OFF_TYPE), record.getTrim(FieldName.PE_OFF_TIME),
					record.getTrim(FieldName.PE_BEF_AF), record.getTrim(FieldName.PE_YMD), record.getTrim(FieldName.PE_REASON));
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
