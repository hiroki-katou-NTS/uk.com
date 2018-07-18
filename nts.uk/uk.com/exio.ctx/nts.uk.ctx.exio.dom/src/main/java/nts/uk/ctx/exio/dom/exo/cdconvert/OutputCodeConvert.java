package nts.uk.ctx.exio.dom.exo.cdconvert;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 出力コード変換
 */
@AllArgsConstructor
@Getter
public class OutputCodeConvert extends AggregateRoot {

	/**
	 * コード変換コード
	 */
	private ConvertCode convertCode;

	/**
	 * コード変換名称
	 */
	private ConvertName convertName;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 設定のないコードの出力
	 */
	private NotUseAtr acceptWithoutSetting;

	private List<CdConvertDetail> listCdConvertDetails;

	public OutputCodeConvert(String convertCode, String convertName, String cid, int acceptWithoutSetting,
			List<CdConvertDetail> listCdConvertDetails) {
		super();
		this.convertCode = new ConvertCode(convertCode);
		this.convertName = new ConvertName(convertName);
		this.cid = cid;
		this.acceptWithoutSetting = EnumAdaptor.valueOf(acceptWithoutSetting, NotUseAtr.class);
		this.listCdConvertDetails = listCdConvertDetails;
	}
	
	
	public OutputCodeConvert(ConvertCode convertCode, ConvertName convertName, String cid, NotUseAtr acceptWithoutSetting) {
		super();
		this.convertCode = convertCode;
		this.convertName = convertName;
		this.cid = cid;
		this.acceptWithoutSetting = acceptWithoutSetting;
	}

}
