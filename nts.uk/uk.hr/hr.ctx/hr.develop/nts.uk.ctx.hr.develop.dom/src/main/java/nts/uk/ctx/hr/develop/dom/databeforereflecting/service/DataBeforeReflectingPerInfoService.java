package nts.uk.ctx.hr.develop.dom.databeforereflecting.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.databeforereflecting.DataBeforeReflectingPerInfo;
import nts.uk.ctx.hr.develop.dom.databeforereflecting.DataBeforeReflectingRepository;

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

}
