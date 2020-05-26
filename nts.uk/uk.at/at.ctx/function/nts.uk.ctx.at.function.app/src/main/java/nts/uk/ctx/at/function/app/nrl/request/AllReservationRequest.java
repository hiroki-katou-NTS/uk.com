package nts.uk.ctx.at.function.app.nrl.request;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

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
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.ConvertTimeRecordReservationAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.ReservReceptDataImport;

/**
 * @author ThanhNX
 *
 *         データタイムレコードを予約に変換する
 */
@RequestScoped
@Named(value = Command.ALL_RESERVATION, decrypt = true)
public class AllReservationRequest extends NRLRequest<Frame> {
	
	@Inject
	private ConvertTimeRecordReservationAdapter convertTRReservationAdapter;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#sketch(nts.uk.ctx.at.function.app.nrl.request.ResourceContext)
	 */
	@Override
	public void sketch(ResourceContext<Frame> context) {
		String payload = context.getEntity().pickItem(Element.PAYLOAD);
		int length = payload.length();
		int q = length / DefaultValue.SINGLE_FRAME_LEN_48;
		//
		if (length % DefaultValue.SINGLE_FRAME_LEN != 0 || q <= 0 || q > 100) {
			context.responseNoAccept(ErrorCode.PARAM);
			return;
		}
		
		ExchangeStruct exchange = ExchangeStruct.newInstance()
				.addField(FieldName.RSV_MENU, 1, t -> FormatPattern.isHwUAlpha(t.trim()))
				.addField(FieldName.RSV_IDNO, 20, t -> FormatPattern.isHwAlphanumericS(t))
				.addField(FieldName.RSV_YMD, 6, t -> FormatPattern.isYymmdd(t))
				.addField(FieldName.RSV_HMS, 6, t -> FormatPattern.isHHmmss(t))
				.addField(FieldName.RSV_QUAN, 2, t -> FormatPattern.isNumeric(t))
				.addField(FieldName.RSV_PRELIMINARY, 13, t -> DefaultValue.PRELIMINARY.equals(t));
		
		for (int i = 0; i < q; i++) {
			int rLen = i * DefaultValue.SINGLE_FRAME_LEN_48;
			try {
				exchange.parseAppend(payload.substring(rLen, rLen + DefaultValue.SINGLE_FRAME_LEN_48));
			} catch (InvalidFieldDataException ex) {
				continue;
			}
		}

		//Insert 予約データ into DB
		String nrlNo = context.getEntity().pickItem(Element.NRL_NO);
		//TODO: default ContractCode "000000000000"
		for (int i = 0; i < q; i++) {
			Record record = exchange.getRecord(i);
			
			ReservReceptDataImport reservData = new ReservReceptDataImport(record.get(FieldName.RSV_IDNO),
					record.get(FieldName.RSV_MENU), record.get(FieldName.RSV_YMD), record.get(FieldName.RSV_HMS),
					record.get(FieldName.RSV_QUAN));

			Optional<AtomTask> result = convertTRReservationAdapter.convertData(Integer.parseInt(nrlNo.trim()), "000000000000", reservData);
			if (result.isPresent())
				result.get().run();
		}
		
		context.responseAccept();

	}

	@Override
	public String responseLength() {
		return null;
	}

}
