package nts.uk.ctx.exio.app.find.exo.cdconvert;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.cdconvert.CdConvertDetail;

@AllArgsConstructor
@Value
public class CdConvertDetailDTO {

	private String cid;

<<<<<<< HEAD
	private String convertCd;

	private String outputItem;

	private String systemCd;
=======
	private String convertCode;

	private String outputItem;

	private String systemCode;
>>>>>>> 44d20521659ca83f7757779f1910e27eba779fa6

	private String lineNumber;

	public static CdConvertDetailDTO fromDomain(CdConvertDetail domain) {
		return new CdConvertDetailDTO(domain.getCid(), domain.getConvertCd().v(), domain.getOutputItem().orElse(null),
				domain.getSystemCd(), domain.getLineNumber());

	}

}
