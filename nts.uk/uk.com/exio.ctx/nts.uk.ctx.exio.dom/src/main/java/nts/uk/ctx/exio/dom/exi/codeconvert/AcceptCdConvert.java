package nts.uk.ctx.exio.dom.exi.codeconvert;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 受入コード変換
 */
@Getter
public class AcceptCdConvert extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * コード変換コード
	 */
	private CodeConvertCode convertCd;

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
	private List<CdConvertDetails> listConvertDetails;

	/**
	 * @param cid
	 * @param convertCd
	 * @param convertName
	 * @param acceptWithoutSetting
	 * @param listConvertDetails
	 */
	public AcceptCdConvert(String cid, String convertCd, String convertName, int acceptWithoutSetting,
			List<CdConvertDetails> listConvertDetails) {
		super();
		this.cid = cid;
		this.convertCd = new CodeConvertCode(convertCd);
		this.convertName = new CodeConvertName(convertName);
		this.acceptWithoutSetting = EnumAdaptor.valueOf(acceptWithoutSetting, NotUseAtr.class);
		this.listConvertDetails = listConvertDetails;
	}

}
