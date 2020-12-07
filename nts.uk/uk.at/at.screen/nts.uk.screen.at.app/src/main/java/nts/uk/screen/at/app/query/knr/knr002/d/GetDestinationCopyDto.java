package nts.uk.screen.at.app.query.knr.knr002.d;

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
public class GetDestinationCopyDto {
	//	端末No
	private String empInfoTerCode;
	// 名称
	private String empInfoTerName;
	// 機種
	private int modelEmpInfoTer;
	// MACアドレス
	private String macAddress;
	// ＩＰアドレス
	private String ipAddress;
	// シリアルＮｏ
	private String terSerialNo;
	// 設置場所
	private String workLocationName;
	// 設置場所
	private String workLocationCode;
	// 状態監視間隔
	private int intervalTime;
	// 外出応援区分
	private int outSupport;
	// 外出区分
	private int replace;
	// 外出理由
	private Integer goOutReason;
	// 入退門区分
	private int entranceExit;
	// メモ
	private String memo;
}
