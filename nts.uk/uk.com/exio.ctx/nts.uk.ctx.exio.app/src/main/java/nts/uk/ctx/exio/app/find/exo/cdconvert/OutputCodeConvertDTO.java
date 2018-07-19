package nts.uk.ctx.exio.app.find.exo.cdconvert;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvert;

/**
 * 出力コード変換
 */
@AllArgsConstructor
@Value
public class OutputCodeConvertDTO {

	private String convertCode;

	private String convertName;

	private String cid;

	private int acceptWithoutSetting;

<<<<<<< HEAD
	private List<CdConvertDetailDTO> listCdConvertDetailDTO;
=======
	private List<CdConvertDetailDTO> listCdConvertDetail;
>>>>>>> 44d20521659ca83f7757779f1910e27eba779fa6

	public static OutputCodeConvertDTO fromDomain(OutputCodeConvert domain) {
		return new OutputCodeConvertDTO(domain.getConvertCode().v(), domain.getConvertName().v(), domain.getCid(),
				domain.getAcceptWithoutSetting().value, domain.getListCdConvertDetails().stream().map(itemDetails -> {
					return new CdConvertDetailDTO(itemDetails.getCid(), itemDetails.getConvertCd().v(),
							itemDetails.getOutputItem().orElse(null), itemDetails.getSystemCd(),
							itemDetails.getLineNumber());
				}).collect(Collectors.toList()));
	}
}
