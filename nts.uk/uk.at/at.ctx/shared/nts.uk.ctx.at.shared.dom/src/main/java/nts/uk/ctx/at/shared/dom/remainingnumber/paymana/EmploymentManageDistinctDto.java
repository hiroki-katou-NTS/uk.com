package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
/**
 * UKDesign.UniversalK.就業.KDM_残数管理 (Quản lý số dư).KDM001_残数管理データの登録 (Đăng ký dữ liệu quản lý số dư)
 * .Ａ：振休管理.アルゴリズム.Ａ：振休管理データ抽出処理.Ａ：振休管理データ抽出処理
 */
@Data
@Builder
public class EmploymentManageDistinctDto {
	/**
	 *  雇用コード
	 */
	private String employmentCode;
	
	/**
	 *  管理区分 
	 */
	private ManageDistinct isManage;
	
}
