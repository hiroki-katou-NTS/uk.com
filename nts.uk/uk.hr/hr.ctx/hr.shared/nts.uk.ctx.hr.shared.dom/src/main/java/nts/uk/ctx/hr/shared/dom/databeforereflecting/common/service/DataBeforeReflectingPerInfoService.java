package nts.uk.ctx.hr.shared.dom.databeforereflecting.common.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.DataBeforeReflectingPerInfo;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.DataBeforeReflectingRepository;

@Stateless
public class DataBeforeReflectingPerInfoService {

	@Inject
	private DataBeforeReflectingRepository repo;

	// 個人情報反映前データの取得
	public List<DataBeforeReflectingPerInfo> getDataBeforeReflectPerInfo(String cid, Integer workId, List<String> listSid, List<String> listPid,
			Optional<Boolean> includReflected, Optional<String> sortByColumnName, Optional<String> orderType) {

		// ドメイン [個人情報反映前データ] を取得する (Get domain "Data before reflecting personal
		// information/data trước khi phản ánh thông tin cá nhân")
		List<DataBeforeReflectingPerInfo> listDataBeforeReflectPerInfo = repo.getData(cid, workId, listSid, listPid,
				includReflected, sortByColumnName, orderType);

		return listDataBeforeReflectPerInfo;
	}
	
	// 個人情報反映前データの追加
	public void addDataBeforeReflectingPerInfo (List<DataBeforeReflectingPerInfo> listDomain) {
		repo.addData(listDomain);
	}

	public void updateDataBeforeReflectingPerInfo(List<DataBeforeReflectingPerInfo> listDomain) {
		repo.updateData(listDomain);
	}

	public void removeDataBeforeReflectingPerInfo(String histId) {
		repo.deleteDataByHistId(histId);
	}
}
