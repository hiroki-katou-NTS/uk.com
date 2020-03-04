package nts.uk.query.app.employee.ccg029;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Ccg029QueryParam {
	
	public Integer systemType; //システム区分（0：共通、1：就業、2：給与、3：人事）

	public Boolean includePreEmployee; //入社前社員を含める
	
	public Boolean includeRetirement; //退職者を含める
	
	public Boolean includeAbsence; //休職者を含める 
	
	public Boolean includeClosed; //休業者を含める
	
	public Boolean includeTransferEmployee; //出向社員を含める
	
	public Boolean includeAcceptanceTransferEmployee; //受入出向社員を含める
	
	public Boolean getPosition; //職位を取得する
	
	public Boolean getEmployment; //雇用を取得する
	
	public Boolean getPersonalFileManagement; //個人ファイル管理を取得する
	
	public String baseDate; // 基準日
	
	public String keyword; //キーワード
	
	public GeneralDate getBaseDate() {
		return GeneralDate.fromString(this.baseDate, "yyyy/MM/dd");
	}
}
