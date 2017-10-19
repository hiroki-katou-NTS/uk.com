package nts.uk.ctx.bs.employee.infra.repository.empfilemanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import entity.employeeinfo.BsymtEmployee;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmpFileManagementRepository;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmployeeFileManagement;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.infra.entity.empfilemanagement.BsymtEmpFileManagement;
import nts.uk.ctx.bs.employee.infra.entity.empfilemanagement.BsymtEmpFileManagementPK;


@Stateless
@Transactional
public class JpaEmployeeFileManagement  extends JpaRepository implements EmpFileManagementRepository{

	public final String GET_ALL_BY_SID = "SELECT c FROM BsymtEmpFileManagement c WHERE c.sid = :sid AND (c.filetype = :filetype or :filetype = -1)";

	
	public final String GET_BY_FILEID = "SELECT c FROM BsymtEmpFileManagement c WHERE c.bsymtEmpFileManagementPK.fileid = :fileid ";


	public final String GET_ALL_DOCUMENT_FILE = "SELECT c, d.categoryName FROM BsymtEmpFileManagement c "
			+" JOIN PpemtPerInfoCtg d ON c.personInfoctgId =  d.ppemtPerInfoCtgPK.perInfoCtgI"
			+ " WHERE c.sid = :sid AND c.filetype = :filetype ";
	
	private EmployeeFileManagement toDomainEmpFileManagement(BsymtEmpFileManagement entity) {
		val domain = EmployeeFileManagement.createFromJavaType(entity.sid,
				entity.bsymtEmpFileManagementPK.fileid, entity.filetype, entity.disPOrder,
				entity.personInfoctgId);
		return domain;
	}
	
	
	private BsymtEmpFileManagement toEntityEmpFileManagement(EmployeeFileManagement domain) {
		BsymtEmpFileManagement entity = new BsymtEmpFileManagement();
		entity.bsymtEmpFileManagementPK = new BsymtEmpFileManagementPK(domain.getFileID());
		entity.sid = domain.getSId();
		entity.filetype = domain.getTypeFile();
		entity.disPOrder = domain.getUploadOrder();
		entity.personInfoctgId = domain.getPersonInfoCategoryId();
		return entity;
	}
	
	/**
	 * @param listEmpFileManagementEntity
	 * @return
	 */
	private List<EmployeeFileManagement> toListEmpFileManagement(List<BsymtEmpFileManagement> listEntity) {
		List<EmployeeFileManagement> lstEmpFileManagement = new ArrayList<>();
		if (!listEntity.isEmpty()) {
			listEntity.stream().forEach(c -> {
				EmployeeFileManagement empFileManagement = toDomainEmpFileManagement(c);
				
				lstEmpFileManagement.add(empFileManagement);
			});
		}
		return lstEmpFileManagement;
	}
	

	@Override
	public void insert(EmployeeFileManagement domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(EmployeeFileManagement domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EmployeeFileManagement> getDataByParams(String employeeId, int filetype) {
		List<BsymtEmpFileManagement> listFile = this.queryProxy().query(GET_ALL_BY_SID, BsymtEmpFileManagement.class)
				.setParameter("sid", employeeId)
				.setParameter("filetype", filetype)
				.getList();

		return toListEmpFileManagement(listFile);
	}


	@Override
	public List<Object> getListDocumentFile(String employeeId, int filetype) {
		List<Object> listFile = this.queryProxy().query(GET_ALL_DOCUMENT_FILE, Object.class)
				.setParameter("sid", employeeId)
				.setParameter("filetype", filetype)
				.getList();
		return listFile;
	}


	@Override
	public Optional<EmployeeFileManagement> getEmpMana(String fileid) {
		
		BsymtEmpFileManagement entity = this.queryProxy().query(GET_BY_FILEID, BsymtEmpFileManagement.class)
				.setParameter("fileid", fileid).getSingleOrNull();

		EmployeeFileManagement empFileMana = new EmployeeFileManagement();
		if (entity != null) {
			empFileMana = toDomainEmpFileManagement(entity);

		}
		return Optional.of(empFileMana);
	}


	@Override
	public void update(EmployeeFileManagement domain) {
		this.commandProxy().update(toEntityEmpFileManagement(domain));
	}


	
}
