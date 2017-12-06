package nts.uk.ctx.pereg.infra.repository.person.additemdata.category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmpInfoCtgData;
import nts.uk.ctx.pereg.infra.entity.person.additemdata.category.PpemtEmpInfoCtgData;
import nts.uk.ctx.pereg.infra.entity.person.additemdata.category.PpemtEmpInfoCtgDataPk;

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

	// sonnlb code start

	private PpemtEmpInfoCtgData toEntity(EmpInfoCtgData domain) {
		PpemtEmpInfoCtgDataPk key = new PpemtEmpInfoCtgDataPk(domain.getRecordId());

		return new PpemtEmpInfoCtgData(key, domain.getPersonInfoCtgId(), domain.getEmployeeId());
	}

	private void updateEntity(EmpInfoCtgData domain,PpemtEmpInfoCtgData entity) {
		entity.personInfoCtgId = domain.getPersonInfoCtgId();
		entity.employeeId = domain.getEmployeeId();
	}

	@Override
	public void addCategoryData(EmpInfoCtgData domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	// sonnlb code end

	@Override
	public void updateEmpInfoCtgData(EmpInfoCtgData domain) {
		PpemtEmpInfoCtgDataPk key = new PpemtEmpInfoCtgDataPk(domain.getRecordId());
		Optional<PpemtEmpInfoCtgData> existItem = this.queryProxy().find(key, PpemtEmpInfoCtgData.class);
		if (!existItem.isPresent()){
			throw new RuntimeException("invalid EmpInfoCtgData");
		}
		updateEntity(domain, existItem.get());
		
		this.commandProxy().update(existItem.get());
	}

	@Override
	public void deleteEmpInfoCtgData(String recordId) {
		PpemtEmpInfoCtgDataPk key = new PpemtEmpInfoCtgDataPk(recordId);
		Optional<PpemtEmpInfoCtgData> existItem = this.queryProxy().find(key, PpemtEmpInfoCtgData.class);
		if (!existItem.isPresent()){
			throw new RuntimeException("invalid EmpInfoCtgData");
		}
		this.commandProxy().remove(PpemtEmpInfoCtgData.class,key);
	}


}
