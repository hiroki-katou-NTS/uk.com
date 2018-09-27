package nts.uk.ctx.pereg.app.find.person.setting.selectionitem;

import lombok.Data;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.PerInfoSelectionItem;

@Data
public class PerInfoSelectionItemDto {

	/**
	 * ID
	 */
	private String selectionItemId;

	/**
	 * 名称
	 */
	private String selectionItemName;

	/**
	 * コード型
	 */
	private boolean characterType;

	/**
	 * コード桁数
	 */
	private int codeLength;

	/**
	 * 名称桁数
	 */
	private int nameLength;

	/**
	 * 外部コード桁数
	 */
	private int extraCodeLength;

	/**
	 * 統合コード
	 */
	private String integrationCode;

	/**
	 * メモ
	 */
	private String memo;

	public static PerInfoSelectionItemDto fromDomain(PerInfoSelectionItem domain) {
		return new PerInfoSelectionItemDto(domain.getSelectionItemId(), domain.getSelectionItemName().v(),
				domain.getFormatSelection().getCharacterType().value,
				domain.getFormatSelection().getCodeLength().v().intValue(),
				domain.getFormatSelection().getNameLength().v().intValue(),
				domain.getFormatSelection().getExternalCodeLength().v().intValue(),
				domain.getIntegrationCode().isPresent() ? domain.getIntegrationCode().get().v() : null,
				domain.getMemo().isPresent() ? domain.getMemo().get().v() : null);

	}

	public PerInfoSelectionItemDto(String selectionItemId, String selectionItemName, int characterType, int codeLength,
			int nameLength, int extraCodeLength, String integrationCode,
			String memo) {
		super();
		this.selectionItemId = selectionItemId;
		this.selectionItemName = selectionItemName;
		this.characterType = characterType == 1 ? true : false;
		this.codeLength = codeLength;
		this.nameLength = nameLength;
		this.extraCodeLength = extraCodeLength;
		this.integrationCode = integrationCode;
		this.memo = memo;
	}
}
