package nts.uk.ctx.exio.app.input.command.setting;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.exio.app.input.find.setting.assembly.ExternalImportLayoutDto;

@Getter
public class ExternalImportSettingCommand {
	
	/** 受入設定コード */
	private String code;
	
	/** 受入設定名称 */
	private String name;
	
	/** 受入グループID */
	private int group;
	
	/** 受入モード */
	private int mode;
	
	/** CSVの項目名取得行 */
	private int itemNameRow;
	
	/** CSVの取込開始行 */
	private int importStartRow;
	
	/** レイアウト */
	private List<ExternalImportLayoutDto> layout;

}
