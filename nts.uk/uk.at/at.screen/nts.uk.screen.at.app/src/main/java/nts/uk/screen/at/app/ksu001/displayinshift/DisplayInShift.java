/**
 * 
 */
package nts.uk.screen.at.app.ksu001.displayinshift;

import javax.ejb.Stateless;

/**
 * @author laitv 
 * <<ScreenQuery>> シフトで表示する 
 * 
 * 【Input】 Excelの項目移送表参照
 * 【Output】 
 * ・List<ページ, 名称> 
 * ※シフトパレット
 * ・対象ページのシフトパレット
 * ・List<シフトマスタ, 出勤休日区分>
 * ・List<勤務予定（シフト）dto>
 *
 * 
 */
@Stateless
public class DisplayInShift {
	
	public void getData(DisplayInShiftParam param) {
		
	}

	public void getDataShiftPallet() {

	}

	public void getDataDisplayGrid() {

	}
}
