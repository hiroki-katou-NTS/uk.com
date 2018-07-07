package nts.uk.ctx.exio.dom.exo.cdconvert;

import lombok.AllArgsConstructor;
import lombok.Getter;
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

}
