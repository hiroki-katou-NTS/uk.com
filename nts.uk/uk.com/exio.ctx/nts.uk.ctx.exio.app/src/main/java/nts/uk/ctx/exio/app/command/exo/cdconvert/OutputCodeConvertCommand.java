package nts.uk.ctx.exio.app.command.exo.cdconvert;

import java.util.List;

import lombok.Value;

@Value
public class OutputCodeConvertCommand
{
    
    /**
    * コード変換コード
    */
    private String convertCode;
    
    /**
    * コード変換名称
    */
    private String convertName;
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 設定のないコードの出力
    */
    private int acceptWithoutSetting;
    
    private Long version;
    
    private List<CdConvertDetailCommand> listCdConvertDetail;

	public OutputCodeConvertCommand(String convertCode, String convertName, String cid, int acceptWithoutSetting,
			Long version, List<CdConvertDetailCommand> listCdConvertDetail) {
		super();
		this.convertCode = convertCode;
		this.convertName = convertName;
		this.cid = cid;
		this.acceptWithoutSetting = acceptWithoutSetting;
		this.version = version;
		this.listCdConvertDetail = listCdConvertDetail;
	}
    
}
