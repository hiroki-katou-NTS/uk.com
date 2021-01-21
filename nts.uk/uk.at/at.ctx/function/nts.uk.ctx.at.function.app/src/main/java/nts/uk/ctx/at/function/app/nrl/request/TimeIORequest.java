package nts.uk.ctx.at.function.app.nrl.request;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.DefaultValue;
import nts.uk.ctx.at.function.app.nrl.data.ExchangeStruct;
import nts.uk.ctx.at.function.app.nrl.data.ExchangeStruct.Record;
import nts.uk.ctx.at.function.app.nrl.data.FieldName;
import nts.uk.ctx.at.function.app.nrl.data.checker.FormatPattern;
import nts.uk.ctx.at.function.app.nrl.exceptions.ErrorCode;
import nts.uk.ctx.at.function.app.nrl.exceptions.InvalidFieldDataException;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.ConvertTimeRecordStampAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.StampDataReflectResultImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.StampReceptionDataImport;

/**
 * Time IO request.
 * 
 * @author manhnd
 * 
 *         データタイムレコードを打刻に変換する
 */
@RequestScoped
@Named(value=Command.ALL_IO_TIME, decrypt=true)
public class TimeIORequest extends NRLRequest<Frame> {
	
	@Inject
	private ConvertTimeRecordStampAdapter convertTRStampAdapter;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#sketch(nts.uk.ctx.at.function.app.nrl.request.ResourceContext)
	 */
	@Override
	public void sketch(ResourceContext<Frame> context) {
		String payload = context.getEntity().pickItem(Element.PAYLOAD);
		int length = payload.length();
		int q = length / DefaultValue.SINGLE_FRAME_LEN;
		//
		if (length % DefaultValue.SINGLE_FRAME_LEN != 0 || q <= 0 || q > 100) {
			context.responseNoAccept(ErrorCode.PARAM);
			return;
		}
		
		ExchangeStruct exchange = ExchangeStruct.newInstance()
				.addField(FieldName.IO_DV, 2, t -> FormatPattern.isHwUAlphanumeric(t.trim()))
				.addField(FieldName.IDNO, 20, t -> FormatPattern.isHwAlphanumericS(t))
				.addField(FieldName.IO_SHIFT, 5, t -> DefaultValue.SPACE5.equals(t) || FormatPattern.isHwUAlphanumeric(t.trim()))
				.addField(FieldName.YMD, 6, t -> FormatPattern.isYymmdd(t))
				.addField(FieldName.HM, 4, t -> FormatPattern.isHHmm(t))
				.addField(FieldName.IO_SUPPORT_CD, 6, t -> DefaultValue.SPACE6.equals(t) || FormatPattern.isHwUAlphanumeric(t))
				.addField(FieldName.IO_OTTIME, 4, t -> DefaultValue.SPACE4.equals(t) || FormatPattern.isHHmm(t))
				.addField(FieldName.IO_MIDNIGHTOT, 4, t -> DefaultValue.SPACE4.equals(t) || FormatPattern.isHHmm(t))
				.addField(FieldName.IO_CARDDV, 1, t -> FormatPattern.isHwUAlpha(t))
				.addField(FieldName.PRELIMINARY, 12, t -> DefaultValue.PRELIMINARY.equals(t));
		
		for (int i = 0; i < q; i++) {
			int rLen = i * DefaultValue.SINGLE_FRAME_LEN;
			try {
				exchange.parseAppend(payload.substring(rLen, rLen + DefaultValue.SINGLE_FRAME_LEN));
			} catch (InvalidFieldDataException ex) {
				continue;
			}
		}

		//Insert 打刻データ into DB
		String nrlNo = context.getEntity().pickItem(Element.NRL_NO);
		//TODO: default ContractCode "000000000000"
		for (int i = 0; i < q; i++) {
			Record record = exchange.getRecord(i);
			
			StampReceptionDataImport stamData = new StampReceptionDataImport.StampDataImportBuilder(
					record.get(FieldName.IDNO), record.get(FieldName.IO_CARDDV), record.get(FieldName.IO_SHIFT),
					record.get(FieldName.IO_DV), record.get(FieldName.YMD), record.get(FieldName.IO_SUPPORT_CD))
							.time(record.get(FieldName.HM)).overTimeHours(record.get(FieldName.IO_OTTIME))
							.midnightTime(record.get(FieldName.IO_MIDNIGHTOT)).build();
			
			Pair<Optional<AtomTask>, Optional<StampDataReflectResultImport>> result = convertTRStampAdapter
					.convertData(Integer.parseInt(nrlNo.trim()), "000000000000", stamData);
			if (result.getLeft().isPresent())
				result.getLeft().get().run();
			if (result.getRight().isPresent())
				result.getRight().get().getAtomTask().run();
		}
		
		context.responseAccept();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#responseLength()
	 */
	@Override
	public String responseLength() {
		return null; 
	}

}
