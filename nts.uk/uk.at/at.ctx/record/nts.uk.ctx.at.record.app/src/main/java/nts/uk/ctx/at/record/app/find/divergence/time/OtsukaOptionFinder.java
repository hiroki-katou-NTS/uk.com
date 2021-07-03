package nts.uk.ctx.at.record.app.find.divergence.time;

import javax.ejb.Stateless;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.license.option.OptionLicense;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK011_乖離時間の登録 (Đăng ký deviation time).アルゴリズム(Thuật toán).大塚オプション情報を取得する.大塚オプション情報を取得する
 */
@Stateless
public class OtsukaOptionFinder {
	
	public boolean getOtsukaOption() {
		// 【実行時情報】アプリケーションコンテキストを取得する
		OptionLicense option = AppContexts.optionLicense();
		// 取得した「オプションライセンスの購入状態」元にOUTPUT「大塚オプション」を返す
		return option.customize().ootsuka();
	}
}
