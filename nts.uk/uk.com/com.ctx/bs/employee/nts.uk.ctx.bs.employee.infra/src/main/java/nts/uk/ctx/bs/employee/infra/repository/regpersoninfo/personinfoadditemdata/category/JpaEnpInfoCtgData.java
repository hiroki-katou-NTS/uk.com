package nts.uk.ctx.bs.employee.infra.repository.regpersoninfo.personinfoadditemdata.category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmpInfoCtgData;
import nts.uk.ctx.bs.employee.infra.entity.regpersoninfo.personinfoadditemdata.category.PpemtEmpInfoCtgData;

@Stateless
public class JpaEnpInfoCtgData extends JpaRepository implements EmInfoCtgDataRepository {

	private static final String SELECT_EMP_DATA_BY_SID_AND_CTG_ID = "SELECT e FROM PpemtEmpInfoCtgData e"
			+ " WHERE e.employeeId = :employeeId AND e.personInfoCtgId = :personInfoCtgId";

	private EmpInfoCtgData toDomain(PpemtEmpInfoCtgData entity) {
		return new EmpInfoCtgData(entity.ppemtEmpInfoCtgDataPk.recordId, entity.personInfoCtgId, entity.employeeId);
	}

	@Override
	public Optional<EmpInfoCtgData> getEmpInfoCtgDataBySIdAndCtgId(String sId, String ctgId) {
		return this.queryProxy().query(SELECT_EMP_DATA_BY_SID_AND_CTG_ID, PpemtEmpInfoCtgData.class)
				.setParameter("employeeId", sId).setParameter("personInfoCtgId", ctgId).getSingle(x -> toDomain(x));
	}

	@Override
	public List<EmpInfoCtgData> getByEmpIdAndCtgId(String employeeId, String categoryId) {
		return this.queryProxy().query(SELECT_EMP_DATA_BY_SID_AND_CTG_ID, PpemtEmpInfoCtgData.class)
				.setParameter("employeeId", employeeId).setParameter("personInfoCtgId", categoryId).getList().stream()
				.map(x -> toDomain(x)).collect(Collectors.toList());
	}

	@Override
	public void addEmpInfoCtgData(EmpInfoCtgData domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEmpInfoCtgData(EmpInfoCtgData domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEmpInfoCtgData(EmpInfoCtgData domain) {
		// TODO Auto-generated method stub
		
	}

}
