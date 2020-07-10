package nts.uk.nrl.request;

import javax.enterprise.context.RequestScoped;

import nts.uk.nrl.Command;
import nts.uk.nrl.DefaultValue;
import nts.uk.nrl.data.ExchangeStruct;
import nts.uk.nrl.data.FieldName;
import nts.uk.nrl.data.checker.FormatPattern;
import nts.uk.nrl.exceptions.ErrorCode;
import nts.uk.nrl.exceptions.InvalidFieldDataException;
import nts.uk.nrl.xml.Element;
import nts.uk.nrl.xml.Frame;

/**
 * Time IO request.
 * 
 * @author manhnd
 */
@RequestScoped
@Named(value=Command.ALL_IO_TIME, decrypt=true)
public class TimeIORequest extends NRLRequest<Frame> {
	
	/* (non-Javadoc)
	 * @see nts.uk.nrl.request.NRLRequest#sketch(nts.uk.nrl.request.ResourceContext)
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
		
		// TODO: Insert 打刻データ into DB
		
		context.responseAccept();
	}

	/* (non-Javadoc)
	 * @see nts.uk.nrl.request.NRLRequest#responseLength()
	 */
	@Override
	public String responseLength() {
		return null; 
	}

}
