package nts.uk.screen.at.app.query.knr.knr002.b;

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
public class AcquisEmpInfoLogDto {
	// 就業情報端末コード
	private String empInfoTerCode;
	//	前回通信成功日時
	private String sTime;
	//	最新通信成功日時
	private String eTime;
}
