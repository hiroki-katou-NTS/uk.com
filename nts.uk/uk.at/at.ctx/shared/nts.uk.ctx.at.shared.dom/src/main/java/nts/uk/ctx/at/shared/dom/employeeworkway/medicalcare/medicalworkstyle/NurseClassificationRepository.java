package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;

import java.util.List;
import java.util.Optional;

public interface NurseClassificationRepository {

	// 会社の看護区分リストを取得する
	public List<NurseClassification> getListCompanyNurseCategory(String companyId);

	// 会社の看護区分リストを取得する
	public Optional<NurseClassification> getSpecifiNurseCategory(String companyId, String code);

	// insert(看護区分）
	public void insert(NurseClassification nurseClassification);

	// update(看護区分）
	public void update(NurseClassification nurseClassification);

	// delete(会社ID，看護区分コード）
	public void delete(String companyId, String code);

}
