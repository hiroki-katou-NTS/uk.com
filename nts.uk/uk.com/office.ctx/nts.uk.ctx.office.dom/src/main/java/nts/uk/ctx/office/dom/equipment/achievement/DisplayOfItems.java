package nts.uk.ctx.office.dom.equipment.achievement;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.実績項目設定.項目の表示
 * @author NWS-DungDV
 *
 */
@Getter
@AllArgsConstructor
public class DisplayOfItems extends ValueObject {
	private UsageItemName itemName;
	private ItemActualUnit unit;
	private ItemDescription explanation;
}
