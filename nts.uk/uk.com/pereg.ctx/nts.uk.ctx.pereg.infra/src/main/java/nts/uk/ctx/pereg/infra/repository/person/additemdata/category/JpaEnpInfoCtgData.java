package nts.uk.ctx.pereg.infra.repository.person.additemdata.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmpInfoCtgData;
import nts.uk.ctx.pereg.infra.entity.person.additemdata.category.PpemtEmpInfoCtgData;

@Stateless
public class JpaEnpInfoCtgData extends JpaRepository implements EmInfoCtgDataRepository {

	private static final String SELECT_EMP_DATA_BY_SID_AND_CTG_ID = "SELECT e FROM PpemtEmpInfoCtgData e"
			+ " WHERE e.employeeId = :employeeId AND e.personInfoCtgId = :personInfoCtgId";

	private static final String SELECT_EMP_DATA_BY_CTG_ID_LST = "SELECT e FROM PpemtEmpInfoCtgData e"
			+ " WHERE e.personInfoCtgId IN :personInfoCtgId";

	private EmpInfoCtgData toDomain(PpemtEmpInfoCtgData entity) {
		return new EmpInfoCtgData(entity.recordId, entity.personInfoCtgId, entity.employeeId);
	}

	@Override
	public List<EmpInfoCtgData> getByEmpIdAndCtgId(String employeeId, String categoryId) {
		List<PpemtEmpInfoCtgData> lstEntities = this.queryProxy()
				.query(SELECT_EMP_DATA_BY_SID_AND_CTG_ID, PpemtEmpInfoCtgData.class)
				.setParameter("employeeId", employeeId).setParameter("personInfoCtgId", categoryId).getList();
		if (lstEntities == null)
			return new ArrayList<>();
		return lstEntities.stream().map(x -> toDomain(x)).collect(Collectors.toList());
	}

	// sonnlb code start

	private PpemtEmpInfoCtgData toEntity(EmpInfoCtgData domain) {
		return new PpemtEmpInfoCtgData(domain.getRecordId(), domain.getPersonInfoCtgId(), domain.getEmployeeId());
	}

	private void updateEntity(EmpInfoCtgData domain, PpemtEmpInfoCtgData entity) {
		entity.personInfoCtgId = domain.getPersonInfoCtgId();
		entity.employeeId = domain.getEmployeeId();
	}

	@Override
	public void addCategoryData(EmpInfoCtgData domain) {
		Optional<PpemtEmpInfoCtgData> existItem = this.queryProxy().find(domain.getRecordId(),
				PpemtEmpInfoCtgData.class);
		if (!existItem.isPresent()) {
			this.commandProxy().insert(toEntity(domain));
		}
		

	}

	// sonnlb code end

	@Override
	public void updateEmpInfoCtgData(EmpInfoCtgData domain) {
		Optional<PpemtEmpInfoCtgData> existItem = this.queryProxy().find(domain.getRecordId(),
				PpemtEmpInfoCtgData.class);
		if (!existItem.isPresent()) {
			throw new RuntimeException("invalid EmpInfoCtgData");
		}
		updateEntity(domain, existItem.get());

		this.commandProxy().update(existItem.get());
	}

	@Override
	public void deleteEmpInfoCtgData(String recordId) {
		Optional<PpemtEmpInfoCtgData> existItem = this.queryProxy().find(recordId, PpemtEmpInfoCtgData.class);
		if (!existItem.isPresent()) {
			return;
		}
		this.commandProxy().remove(PpemtEmpInfoCtgData.class, recordId);
	}

	@Override
	public List<EmpInfoCtgData> getByEmpIdAndCtgId(List<String> ctgId) {
		List<PpemtEmpInfoCtgData> lstEntities = new ArrayList<>();
		CollectionUtil.split(ctgId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			lstEntities.addAll(this.queryProxy()
				.query(SELECT_EMP_DATA_BY_CTG_ID_LST, PpemtEmpInfoCtgData.class)
				.setParameter("personInfoCtgId", subList)
				.getList());
		});
		return lstEntities.stream().map(x -> toDomain(x)).collect(Collectors.toList());
	}

}
