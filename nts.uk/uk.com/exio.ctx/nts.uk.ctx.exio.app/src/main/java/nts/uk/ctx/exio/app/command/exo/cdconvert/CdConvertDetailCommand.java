package nts.uk.ctx.exio.app.command.exo.cdconvert;

import lombok.Value;

@Value
public class CdConvertDetailCommand
{
    
    /**
    * コード変換コード
    */
    private String convertCode;
    
    /**
    * 出力項目
    */
    private String outputItem;
    
    /**
    * 本システムのコード
    */
    private String systemCode;
    
    /**
    * 行番号
    */
    private String lineNumber;
    
    private Long version;

	public CdConvertDetailCommand(String convertCode, String outputItem, String systemCode, String lineNumber,
			Long version) {
		super();
		this.convertCode = convertCode;
		this.outputItem = outputItem;
		this.systemCode = systemCode;
		this.lineNumber = lineNumber;
		this.version = version;
	}

}
