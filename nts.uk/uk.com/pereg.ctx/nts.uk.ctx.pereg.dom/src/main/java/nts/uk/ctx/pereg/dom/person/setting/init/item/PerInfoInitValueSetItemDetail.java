package nts.uk.ctx.pereg.dom.person.setting.init.item;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@NoArgsConstructor
@Getter
@Setter
public class PerInfoInitValueSetItemDetail {
	// 個人情報項目定義ID
		private String perInfoItemDefId;

		private String settingId;

		// 個人情報カテゴリID
		private String perInfoCtgId;

		private String itemName;

		private int isRequired;

		// 参照方法
		private int refMethodType;

		// 保存データ型
		private Integer saveDataType;

		// 文字列
		private String stringValue;

		// 数値
		private BigDecimal intValue;

		// 日付
		private GeneralDate dateValue;
		
		private String itemParentCd;

		// thêm dataType của item defined
		private Integer dataType;

		// thêm itemType của item defined
		private Integer itemType;

		// thêm itemCode của item defined
		private String itemCode;

		// thêm categoryCode của CategoryInfo
		private String ctgCode;

		// thêm constraint để validate cho chính mình
		private String constraint;

		// thêm trường numberDecimalPart của bảng common
		private Integer numberDecimalPart;

		// thêm trường numberIntegerPart của bảng common
		private Integer numberIntegerPart;

		private Integer numberItemAmount;
		
		private Integer numberItemMinus;

		private Integer timeItemMin;

		private Integer timeItemMax;

		// trường này dùng để kết nối với bảng selectionItem
		private String selectionItemId;
		
		private Integer selectionItemRefType;
		
		private String selectionItemRefCd;
		
		
		private Map<Integer, Map<Integer, String>> selectionLst;

		// trường này để xác định xem là trường date thuộc
		// kiểu ngày tháng năm, năm tháng or năm
		private Integer dateType;
		
		// hai trường này dùng để xác định giá trị của item nắm
		// trong khoảng nào khi mà item là kiểu timepoint
		private Integer timepointItemMin;
		private Integer timepointItemMax;
		
		// hai trường này dùng để xác định giá trị của item nắm
		// trong khoảng nào khi mà item là kiểu numberic
		private BigDecimal numericItemMin;
		private BigDecimal numericItemMax;
		
		// hai trường này dùng để  validate item thuộc kiểu string
		
		private Integer stringItemType;
		
		private Integer stringItemLength;
		
		private Integer stringItemDataType;
		
		private boolean isFixedItem;

}
