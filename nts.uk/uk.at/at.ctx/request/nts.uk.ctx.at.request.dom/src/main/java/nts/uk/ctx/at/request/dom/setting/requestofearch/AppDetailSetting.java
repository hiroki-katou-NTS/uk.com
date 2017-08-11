package nts.uk.ctx.at.request.dom.setting.requestofearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.application.common.UseAtr;
/**
 * 申請詳細設定
 * @author dudt
 *
 */
@Getter
@AllArgsConstructor
public class AppDetailSetting extends DomainObject {
	/**
	 * 出退勤時刻初期表示区分
	 */
	public DisplayFlg breakInputFieldDisFlg;
	/**
	 * 休憩時間を表示する
	 */
	public DisplayFlg breakTimeDisFlg;
	/**
	 * 出退勤時刻初期表示区分
	 */
	public DisplayFlg atworkTimeBeginDisFlg;
	/**
	 * 実績から外出を初期表示する
	 */
	public DisplayFlg goOutTimeBeginDisFlg;
	/**
	 * 残業申請時刻計算利用区分
	 */
	public UseAtr timeCalUseAtr;
	/**
	 * 残業申請時間入力利用区分
	 */
	public UseAtr timeInputUseAtr;
	
	public static AppDetailSetting appDetailSetting(int breakInputFieldDisFlg,
			int breakTimeDisFlg,
			int atworkTimeBeginDisFlg,
			int goOutTimeBeginDisFlg,
			int timeCalUseAtr,
			int timeInputUseAtr) {
		return new AppDetailSetting(EnumAdaptor.valueOf(breakInputFieldDisFlg, DisplayFlg.class),
				EnumAdaptor.valueOf(breakTimeDisFlg, DisplayFlg.class),
				EnumAdaptor.valueOf(atworkTimeBeginDisFlg, DisplayFlg.class),
				EnumAdaptor.valueOf(goOutTimeBeginDisFlg, DisplayFlg.class),
				EnumAdaptor.valueOf(timeCalUseAtr, UseAtr.class),
				EnumAdaptor.valueOf(timeInputUseAtr, UseAtr.class));
	}
	
}
