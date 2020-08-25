package nts.uk.ctx.at.request.app.find.application.applicationlist;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppListSprParam {
	
	/**
	 * 抽出対象
	 * ０＝全て、１＝早出・普通残業のみ　※SPR連携以外はNULL
	 */
	private int extractionTarget;
	
	/**
	 * 36協定時間
	 * ０＝表示しない、1＝表示する　　※SPR連携以外はNULL
	 */
	private int agreedHours36;
	
}
