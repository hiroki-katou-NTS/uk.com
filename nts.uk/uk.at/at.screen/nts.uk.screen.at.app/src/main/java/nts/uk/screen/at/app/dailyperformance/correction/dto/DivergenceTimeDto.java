package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DivergenceTimeDto {
	/*会社ID*/
	private String companyId;
	/*乖離時間ID*/
	private int divTimeId;
	/*乖離時間名称*/
	public String divTimeName;
	/*乖離時間使用設定*/
	public int divTimeUseSet;
	/*アラーム時間*/
	public int alarmTime;
	/*エラー時間*/
	public int errTime;
	/*選択使用設定*/
	public int selectUseSet;
	/*乖離理由選択エラー解除*/
	public int cancelErrSelReason;
	/*入力使用設定*/
	public int inputUseSet;
	/*乖離理由入力エラー解除*/
	public int cancelErrInputReason;
}
