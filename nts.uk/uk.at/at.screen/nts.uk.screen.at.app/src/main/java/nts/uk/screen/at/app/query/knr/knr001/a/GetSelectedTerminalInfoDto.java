package nts.uk.screen.at.app.query.knr.knr001.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author xuannt
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetSelectedTerminalInfoDto {

	//	端末No
	private String empInfoTerCode;
	//	名称
	private String empInfoTerName;
	//	機種
	private int modelEmpInfoTer;
	//	MACアドレス
	private String macAddress;
	//	ＩＰアドレス1
	private String ipAddress1;
	//	ＩＰアドレス2
	private String ipAddress2;
	//	ＩＰアドレス3
	private String ipAddress3;
	//	ＩＰアドレス4
	private String ipAddress4;
	//	シリアルＮｏ
	private String terSerialNo;
	//	設置場所
	private String workLocationName;
	//	設置場所
	private String workLocationCode;
	//	状態監視間隔
	private int intervalTime;
	//	外出応援区分
	private int outSupport;
	//	外出区分
	private int replace;
	//	外出理由
	private Integer goOutReason;
	//	入退門区分
	private int entranceExit;
	//	メモ
	private String memo;
	
}
