package nts.uk.ctx.hr.develop.dom.databeforereflecting.retirementinformation.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.hr.develop.dom.databeforereflecting.retirementinformation.RetirementInformation;

public class RetirementInformationService {

	/**
	 * 定年退職者情報の取得
	 * 
	 * @param ログイン会社ID
	 * @param （オプション）社員IDリスト
	 * @param （オプション）反映済みを含む
	 * @return List<退職者情報>
	 */
	public List<RetirementInformation> getRetirementInformation(String cId, Optional<List<String>> employeeIds,
			Optional<Object> includingReflected) {
		// ドメイン [個人情報反映前データ] を取得する (Get domain "Data before reflecting personal
		// information/data trước khi phản ánh thông tin cá nhân")

		// 個人情報反映前データリストを定年退職者情報リストへ変換する(Chuyển đổi list data trước khi phản ánh
		// thông tin các nhân sang list thông tin người nghỉ hưu)
		return Collections.emptyList();
	}
}
