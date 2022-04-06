package nts.uk.ctx.exio.dom.input.setting.assembly.revise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.errors.ItemError;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.gul.util.Either;

/**
 * 項目の編集
 */
@Getter
@AllArgsConstructor
public class ReviseItem implements DomainAggregate {
	
	/** 会社ID */
	private String companyId;
	
	/** 受入設定コード */
	private ExternalImportCode settingCode;

	/* 受入グループID */
	private ImportingDomainId domainId;
	
	/** 受入項目NO */
	private int itemNo;
	
	/** 値の編集 */
	private ReviseValue revisingValue;
	
	/**
	 * 編集する
	 * @param targetValue
	 * @return
	 */
	public Either<ItemError, DataItem> revise(Require require, String targetValue) {

		val requireRev = new ReviseValue.Require() {
			@Override
			public ImportableItem getImportableItem() {
				return require.getImportableItem(domainId, itemNo);
			}
		};
		
		return revisingValue.revise(requireRev, targetValue).map(
				errorMessage -> new ItemError(itemNo, errorMessage.getText()),
				result -> new DataItem(itemNo, result));
	}

	public interface Require {
		ImportableItem getImportableItem(ImportingDomainId domainId, int itemNo);
	}
	
	/**
	 * 変更する（設定コピー用）
	 */
	public void changeForCopy(String companyId, ExternalImportCode settingCode) {
		this.companyId = companyId;
		this.settingCode = settingCode;
	}
}
