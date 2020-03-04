package nts.uk.ctx.hr.shared.dom.databeforereflecting.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.shared.dom.databeforereflecting.DataBeforeReflectingPerInfo;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.DataBeforeReflectingRepository;

@Stateless
public class DataBeforeReflectingPerInfoService {

	@Inject
	private DataBeforeReflectingRepository repo;

	// 個人情報反映前データの取得
	public List<DataBeforeReflectingPerInfo> getDataBeforeReflectPerInfo(String cid, Integer workId, List<String> listPid,
			Optional<Boolean> includReflected, Optional<String> sortByColumnName, Optional<String> orderType) {

		// ドメイン [個人情報反映前データ] を取得する (Get domain "Data before reflecting personal
		// information/data trước khi phản ánh thông tin cá nhân")
		List<DataBeforeReflectingPerInfo> listDataBeforeReflectPerInfo = repo.getData(cid, workId, listPid,
				includReflected, sortByColumnName, orderType);

		return listDataBeforeReflectPerInfo;
	}
	
	// 個人情報反映前データの追加
	public void addDataBeforeReflectingPerInfo (DataBeforeReflectingPerInfo domain) {
		List<DataBeforeReflectingPerInfo> listDomain = new ArrayList<DataBeforeReflectingPerInfo>();
		listDomain.add(domain);
		repo.addData(listDomain);
	}

	public void updateDataBeforeReflectingPerInfo(DataBeforeReflectingPerInfo domain) {
		List<DataBeforeReflectingPerInfo> listDomain = new ArrayList<DataBeforeReflectingPerInfo>();
		listDomain.add(domain);
		repo.updateData(listDomain);
	}

	public void removeDataBeforeReflectingPerInfo(String histId) {
		repo.deleteDataByHistId(histId);
	}
}
