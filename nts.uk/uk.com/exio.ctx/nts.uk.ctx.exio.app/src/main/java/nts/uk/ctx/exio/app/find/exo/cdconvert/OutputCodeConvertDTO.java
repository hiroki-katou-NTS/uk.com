package nts.uk.ctx.exio.app.find.exo.cdconvert;

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

	public static OutputCodeConvertDTO fromDomain(OutputCodeConvert domain) {
		return new OutputCodeConvertDTO(domain.getConvertCode().v(), domain.getConvertName().v(), domain.getCid(),
				domain.getAcceptWithoutSetting().value);
	}
}
