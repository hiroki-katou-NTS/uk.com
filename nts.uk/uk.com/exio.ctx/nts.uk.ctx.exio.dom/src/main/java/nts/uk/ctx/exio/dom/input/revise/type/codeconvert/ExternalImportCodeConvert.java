package nts.uk.ctx.exio.dom.input.revise.type.codeconvert;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 受入コード変換
 */
@Getter
public class ExternalImportCodeConvert extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * コード変換コード
	 */
	private CodeConvertCode convertCode;

	/**
	 * コード変換名称
	 */
	private CodeConvertName convertName;

	/**
	 * 設定のないコードの受入
	 */
	private NotUseAtr acceptWithoutSetting;

	/**
	 * List convert detail data
	 */
	private List<CodeConvertDetails> listConvertDetails;

	/**
	 * @param cid
	 * @param convertCd
	 * @param convertName
	 * @param acceptWithoutSetting
	 * @param listConvertDetails
	 */
	public ExternalImportCodeConvert(String cid, String convertCd, String convertName, int acceptWithoutSetting,
			List<CodeConvertDetails> listConvertDetails) {
		super();
		this.companyId = cid;
		this.convertCode = new CodeConvertCode(convertCd);
		this.convertName = new CodeConvertName(convertName);
		this.acceptWithoutSetting = EnumAdaptor.valueOf(acceptWithoutSetting, NotUseAtr.class);
		this.listConvertDetails = listConvertDetails;
	}

}
