package nts.uk.ctx.at.record.dom.remainingnumber.base;
import lombok.AllArgsConstructor;
/**
 * 対象選択区分
 * @author HopNT
 *
 */
@AllArgsConstructor
public enum TargetSelectionAtr {
	// 自動
	AUTOMATIC(0),
	// 申請
	REQUEST(1),
	// 手動
	MANUAL(2);
	
	public final int value;
}
