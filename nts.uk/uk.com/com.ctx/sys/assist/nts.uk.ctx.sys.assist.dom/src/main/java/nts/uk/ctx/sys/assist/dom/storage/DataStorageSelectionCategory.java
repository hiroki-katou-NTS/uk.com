package nts.uk.ctx.sys.assist.dom.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.assist.dom.category.CategoryId;

/**
 * データ保存の選択カテゴリ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataStorageSelectionCategory {
	
	/**
	 * カテゴリID
	 */
	private CategoryId categoryId;
	
	/**
	 * システム種類
	 */
	private SystemType systemType;
	
	/**
	 * パターンコード
	 */
	private PatternCode patternCode;
	
	/**
	 * パターン区分
	 */
	private PatternClassification patternClassification;
	
	/**
	 * 契約コード
	 */
	private ContractCode contractCode;
	
	public static DataStorageSelectionCategory createFromMemento(MementoGetter memento) {
		DataStorageSelectionCategory domain = new DataStorageSelectionCategory();
		domain.getMemento(memento);
		return domain;
	}
	
	public void getMemento(MementoGetter memento) {
		this.categoryId = new CategoryId(memento.getCategoryId());
		this.systemType = EnumAdaptor.valueOf(memento.getSystemType(), SystemType.class);
		this.patternCode = new PatternCode(memento.getPatternCode());
		this.patternClassification = EnumAdaptor.valueOf(memento.getPatternClassification(), PatternClassification.class);
		this.contractCode = new ContractCode(memento.getContractCode());
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setCategoryId(categoryId.v());
		memento.setSystemType(systemType.value);
		memento.setPatternCode(patternCode.v());
		memento.setPatternClassification(patternClassification.value);
		memento.setContractCode(contractCode.v());
	}
	
	public static interface MementoSetter {
		void setCategoryId(String categoryId);
		void setSystemType(int systemType);
		void setPatternCode(String patternCode);
		void setPatternClassification(int patternClassification);
		void setContractCode(String contractCode);
	}
	
	public static interface MementoGetter {
		String getCategoryId();
		int getSystemType();
		String getPatternCode();
		int getPatternClassification();
		String getContractCode();
	}
}
