package nts.uk.ctx.exio.app.find.exi.codeconvert;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.ExternalImportCodeConvert;

/**
 * 受入コード変換
 */
@AllArgsConstructor
@Value
public class AcceptCdConvertDto {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * コード変換コード
	 */
	private String convertCd;

	/**
	 * コード変換名称
	 */
	private String convertName;

	/**
	 * 設定のないコードの受入
	 */
	private int acceptWithoutSetting;

	private Long version;

	private List<CodeConvertDetailsDto> cdConvertDetails;

	public static AcceptCdConvertDto fromDomain(ExternalImportCodeConvert domain) {
		return new AcceptCdConvertDto(domain.getCompanyId(), domain.getConvertCode().v(), domain.getConvertName().v(),
				domain.getAcceptWithoutSetting().value, domain.getVersion(),
				domain.getListConvertDetails().stream().map(itemDetails -> {
					return new CodeConvertDetailsDto(itemDetails.getCid(), itemDetails.getConvertCd(),
							itemDetails.getLineNumber(), itemDetails.getOutputItem().v(),
							itemDetails.getSystemCd().v());
				}).collect(Collectors.toList()));
	}

}
