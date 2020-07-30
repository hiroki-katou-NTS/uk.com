package nts.uk.ctx.at.record.app.command.kdp.kdps01.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.app.command.kdp.kdp001.a.RefectActualResultCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp004.a.StampButtonCommand;

/**
 * 
 * @author sonnlb
 * 
 *         打刻入力(スマホ)から打刻データを作成する
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterSmartPhoneStampCommand {
	/**
	 * 打刻日時
	 */
	private String stampDatetime;
	/**
	 * 打刻ボタン(ページNO,ボタンNO)
	 */
	private StampButtonCommand stampButton;
	/**
	 * 地理座標
	 */
	private GeoCoordinateCommand geoCoordinate;

	/**
	 * 実績への反映内容
	 */

	private RefectActualResultCommand refActualResult;

	public GeneralDateTime getStampDatetime() {
		return GeneralDateTime.fromString(this.stampDatetime, "yyyy/MM/dd HH:mm:ss");
	}

	
}
