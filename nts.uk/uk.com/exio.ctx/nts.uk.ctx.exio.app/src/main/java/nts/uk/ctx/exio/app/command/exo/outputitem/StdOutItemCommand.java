package nts.uk.ctx.exio.app.command.exo.outputitem;

import java.util.List;

import lombok.Getter;
import lombok.Value;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.AtWorkClsDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.CharacterDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.DateDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.InstantTimeDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.NumberDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.TimeDfsCommand;

@Value
@Getter
public class StdOutItemCommand {
	private boolean isNewMode;
	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 出力項目コード
	 */
	private String outItemCd;

	/**
	 * 条件設定コード
	 */
	private String condSetCd;

	/**
	 * 出力項目名
	 */
	private String outItemName;

	/**
	 * 項目型
	 */
	private int itemType;

	/**
	 * カテゴリ項目
	 */
	private List<CategoryItemCommand> categoryItems;
	
	private AtWorkClsDfsCommand atWorkDataOutputItem;
	private CharacterDfsCommand characterDataFormatSetting;
	private DateDfsCommand dateDataFormatSetting;
	private InstantTimeDfsCommand inTimeDataFormatSetting;
	private NumberDfsCommand numberDataFormatSetting;
	private TimeDfsCommand timeDataFormatSetting;
	
	private int dispOrder;
}
