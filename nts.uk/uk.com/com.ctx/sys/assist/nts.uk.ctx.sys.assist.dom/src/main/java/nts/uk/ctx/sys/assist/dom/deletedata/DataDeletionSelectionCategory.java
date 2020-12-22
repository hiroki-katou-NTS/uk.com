package nts.uk.ctx.sys.assist.dom.deletedata;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.assist.dom.categoryfordelete.CategoryId;
import nts.uk.ctx.sys.assist.dom.storage.ContractCode;
import nts.uk.ctx.sys.assist.dom.storage.PatternClassification;
import nts.uk.ctx.sys.assist.dom.storage.PatternCode;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.補助機能.データ削除.削除パターン設定.データ削除の選択カテゴリ
 */
@Getter
public class DataDeletionSelectionCategory {

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
	
	public static DataDeletionSelectionCategory createFromMemento(MementoGetter memento) {
		DataDeletionSelectionCategory domain = new DataDeletionSelectionCategory();
		domain.getMemento(memento);
		return domain;
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setCategoryId(categoryId.v());
		memento.setContractCode(contractCode.v());
		memento.setPatternClassification(patternClassification.value);
		memento.setPatternCode(patternCode.v());
		memento.setSystemType(systemType.value);
	}
	
	public void getMemento(MementoGetter memento) {
		this.categoryId = new CategoryId(memento.getCategoryId());
		this.contractCode = new ContractCode(memento.getContractCode());
		this.patternClassification = EnumAdaptor.valueOf(memento.getPatternClassification(), PatternClassification.class);
		this.patternCode = new PatternCode(memento.getPatternCode());
		this.systemType = EnumAdaptor.valueOf(memento.getSystemType(), SystemType.class);
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
