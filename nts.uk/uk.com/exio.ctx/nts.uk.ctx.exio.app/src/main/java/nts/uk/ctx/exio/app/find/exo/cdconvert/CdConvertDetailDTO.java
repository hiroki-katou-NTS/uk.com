package nts.uk.ctx.exio.app.find.exo.cdconvert;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.cdconvert.CdConvertDetail;

@AllArgsConstructor
@Value
public class CdConvertDetailDTO {

	private String cid;

	private String convertCode;

	private String outputItem;

	private String systemCode;

	private String lineNumber;

	public static CdConvertDetailDTO fromDomain(CdConvertDetail domain) {
		return new CdConvertDetailDTO(domain.getCid(), domain.getConvertCd().v(),
				domain.getOutputItem().isPresent() ? domain.getOutputItem().get().v() : null,
				domain.getSystemCd().v(), domain.getLineNumber());

	}

}
