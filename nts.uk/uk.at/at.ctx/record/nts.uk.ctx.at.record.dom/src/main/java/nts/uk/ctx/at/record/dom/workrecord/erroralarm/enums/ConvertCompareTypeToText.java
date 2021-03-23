package nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums;

import javax.ejb.Stateless;

@Stateless
public class ConvertCompareTypeToText {
	public CompareOperatorText convertCompareType(int compareOperator) {
		CompareOperatorText compare = new CompareOperatorText();
		switch(compareOperator) {
		case 0 :/* 等しくない（≠） */
			compare.setCompareLeft("≠");
			compare.setCompareright("");
			break; 
		case 1 :/* 等しい（＝） */
			compare.setCompareLeft("＝");
			compare.setCompareright("");
			break; 
		case 2 :/* 以下（≦） */
			compare.setCompareLeft("≦");
			compare.setCompareright("");
			break;
		case 3 :/* 以上（≧） */
			compare.setCompareLeft("≧");
			compare.setCompareright("");
			break;
		case 4 :/* より小さい（＜） */
			compare.setCompareLeft("＜");
			compare.setCompareright("");
			break;
		case 5 :/* より大きい（＞） */
			compare.setCompareLeft("＞");
			compare.setCompareright("");
			break;
		case 6 :/* 範囲の間（境界値を含まない）（＜＞） */
			compare.setCompareLeft("＜");
			compare.setCompareright("＜");
			break;
		case 7 :/* 範囲の間（境界値を含む）（≦≧） */
			compare.setCompareLeft("≦");
			compare.setCompareright("≦");
			break;
		case 8 :/* 範囲の外（境界値を含まない）（＞＜） */
			compare.setCompareLeft("＜");
			compare.setCompareright("＜");
			break;
		
		default :/* 範囲の外（境界値を含む）（≧≦） */
			compare.setCompareLeft("≦");
			compare.setCompareright("≦");
			break; 
		}
		
		return compare;
	}
}
