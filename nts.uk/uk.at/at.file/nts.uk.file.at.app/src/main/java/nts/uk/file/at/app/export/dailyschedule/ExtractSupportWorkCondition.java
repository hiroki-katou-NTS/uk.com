package nts.uk.file.at.app.export.dailyschedule;

import lombok.Data;

/**
 * UKDesign.UniversalK.就業.KWR_帳表.KWR001_日別勤務表(daily work schedule).ユーザ固有情報(User
 * specific information).応援勤務を抽出条件
 * 
 * @author LienPTK
 */
@Data
public class ExtractSupportWorkCondition {
	// 通常勤務場所以外
	private boolean otherNormalLocation;
	// 所属職場以外
	private boolean otherWorkplace;
}
