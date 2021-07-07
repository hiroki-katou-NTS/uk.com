package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * AR:	打刻入力の共通設定
 * Path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の機能設定.打刻入力の共通設定
 * @author chungnt
 *
 */

@AllArgsConstructor
public class CommonSettingsStampInput implements DomainAggregate{
	
	/**
	 * 	会社ID
	 */
	@Getter
	private final String companyId;
	
	/**
	 * 	GoogleMap利用するか
	 */
	@Setter
	@Getter
	private boolean googlemap;
	
	/**
	 * マップ表示アドレス
	 */
	@Getter
	@Setter
	private Optional<MapAddress> mapAddres;
	
	/**
	 * 応援打刻を利用する
	 */
	@Setter
	@Getter
	private NotUseAtr supportUseArt; 
}
