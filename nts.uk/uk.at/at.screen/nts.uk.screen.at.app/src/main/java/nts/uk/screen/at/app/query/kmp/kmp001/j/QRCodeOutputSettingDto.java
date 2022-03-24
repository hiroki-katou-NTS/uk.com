package nts.uk.screen.at.app.query.kmp.kmp001.j;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author tutt
 * Characteristic
 * QRコード出力の画面設定情報
 */
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QRCodeOutputSettingDto {

	/** QRコードサイズ*/
	private QRCodeSize qrCodeSize;
}
