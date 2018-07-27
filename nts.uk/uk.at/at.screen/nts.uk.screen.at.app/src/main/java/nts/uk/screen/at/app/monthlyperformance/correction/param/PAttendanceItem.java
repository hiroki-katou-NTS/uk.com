package nts.uk.screen.at.app.monthlyperformance.correction.param;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.DisplayAndInputMonthlyDto;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;

@Data
@NoArgsConstructor
public class PAttendanceItem {
	/** ID: 勤怠項目ID */
	private Integer id;
	/** 名称: 勤怠項目名称 */
	private String name;
	/** 表示番号: 表示番号 */
	private Integer displayNumber;	
	/** 属性: 月次項目の属性*/
	/*1:  時間 */
	/*2:  回数 */
	/*3:  日数*/
	/*4:  金額 */
	/*5:  マスタを参照する */
	/*6:  コード */
	/*7:  区分 */
	/*8:  比率 */
	/*9:  文字 */
	private Integer attendanceAtr;
	/** ユーザが変更できる*/
	private boolean userCanChange;
	/** 本人が変更できる */
	private boolean youCanChange;
	/** 他人が変更できる */
	private boolean otherCanChange;
	/** 列幅: 表の列幅 */
	private Integer columnWidth;
	/** 改行位置: 名称の改行位置 */
	private Integer lineBreakPosition;
	/** 並び順  */
	private Integer displayOrder;	
	/** ヘッダ色: カラーコード*/
	private String headerColor;
	/**時間項目の入力単位: 時間入力単位*/
	private Integer timeInput;
	/** The user can update atr. */
	// TODO 使用区分
	private Integer userCanUpdateAtr;
	/**
	 * 1: 勤務種類 2: 就業時間帯 3: 勤務場所 4: 乖離理由 5: 職場 6: 分類 7: 職位 8: 雇用 9: するしない区分 10:
	 * 時間外の自動計算区分 11: 外出理由"
	 */
	/*private Integer typeGroup;*/
	
	public PAttendanceItem(Integer id, String name, Integer displayNumber, Integer attendanceAtr, boolean userCanChange,
			boolean youCanChange, boolean otherCanChange, Integer columnWidth, Integer lineBreakPosition, Integer displayOrder,
			String headerColor, Integer timeInput) {
		super();
		this.id = id;
		this.name = name;
		this.displayNumber = displayNumber;
		this.attendanceAtr = attendanceAtr;
		this.userCanChange = userCanChange;
		this.youCanChange = youCanChange;
		this.otherCanChange = otherCanChange;
		this.columnWidth = columnWidth;
		this.lineBreakPosition = lineBreakPosition;
		this.displayOrder = displayOrder;
		this.headerColor = headerColor;
		this.timeInput = timeInput;
	}
	public PAttendanceItem(Integer id, Integer displayOrder, Integer columnWidth){
		this.id = id;
		this.displayOrder = displayOrder;
		this.columnWidth = columnWidth;
	}
	
	@Override
	public int hashCode(){
	    return id;
	}

	@Override
	public boolean equals(Object obj){
	    if(this == obj) return true;
	    else if(obj instanceof PAttendanceItem){ // implicit null check
	    	PAttendanceItem other = (PAttendanceItem) obj;
	        return this.id.equals(other.id);
	    }
	    else if(obj instanceof DisplayAndInputMonthlyDto){ // implicit null check
	    	DisplayAndInputMonthlyDto other = (DisplayAndInputMonthlyDto) obj;
	        return this.id.equals(other.getItemMonthlyId());
	    }
	    else return false;
	}
}
