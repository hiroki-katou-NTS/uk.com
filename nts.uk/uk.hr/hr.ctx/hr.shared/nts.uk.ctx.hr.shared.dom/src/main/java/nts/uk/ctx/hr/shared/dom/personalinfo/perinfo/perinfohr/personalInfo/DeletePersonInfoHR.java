package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformationRepository;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.個人情報.アルゴリズム.個人情報（人事）(Thông tin cá nhân (Nhân sự).個人情報の削除 (Delete thông tin cá nhân).個人情報の削除
 * @author chungnt
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeletePersonInfoHR {

	@Inject
	private PersonalInformationRepository repo;
	
	public void deletePersonalInfo(List<DeletePersonInfoHRInput> histIds) {
		for (DeletePersonInfoHRInput deletePersonInfoHRInput : histIds) {
			this.repo.delete(deletePersonInfoHRInput.getHistId());
		}
	}
}
