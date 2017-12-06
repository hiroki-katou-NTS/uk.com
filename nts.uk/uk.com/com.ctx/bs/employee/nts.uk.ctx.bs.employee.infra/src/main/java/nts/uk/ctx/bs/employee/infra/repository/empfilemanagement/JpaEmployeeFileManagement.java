package nts.uk.ctx.bs.employee.infra.repository.empfilemanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmpFileManagementRepository;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.PersonFileManagement;
import nts.uk.ctx.bs.employee.infra.entity.empfilemanagement.BsymtEmpFileManagement;
import nts.uk.ctx.bs.employee.infra.entity.empfilemanagement.BsymtEmpFileManagementPK;


@Stateless
@Transactional
public class JpaEmployeeFileManagement  extends JpaRepository implements EmpFileManagementRepository{

	public final String GET_ALL_BY_SID = "SELECT c FROM BsymtEmpFileManagement c WHERE c.sid = :sid AND c.filetype = :filetype";

	public final String CHECK_EXIST = "SELECT COUNT(c) FROM BsymtEmpFileManagement c WHERE c.sid = :sid AND c.filetype = :filetype";
	
	public final String GET_BY_FILEID = "SELECT c FROM BsymtEmpFileManagement c WHERE c.bsymtEmpFileManagementPK.fileid = :fileid ";
	
	//public final String DELETE_DOCUMENT_BY_FILEID = "DELETE FROM BsymtEmpFileManagement c  WHERE c.bsymtEmpFileManagementPK.fileid = :fileid";


	public final String GET_ALL_DOCUMENT_FILE = "SELECT c.sid , c.bsymtEmpFileManagementPK.fileid , c.disPOrder , c.personInfoctgId ,d.categoryName FROM BsymtEmpFileManagement c "
			+" JOIN PpemtPerInfoCtg d ON c.personInfoctgId =  d.ppemtPerInfoCtgPK.perInfoCtgId "
			+" WHERE c.sid = :sid AND c.filetype = :filetype ORDER BY c.disPOrder ASC";
	
	private PersonFileManagement toDomainEmpFileManagement(BsymtEmpFileManagement entity) {
		val domain = PersonFileManagement.createFromJavaType(entity.pid,
				entity.bsymtEmpFileManagementPK.fileid, entity.filetype, entity.disPOrder,
				entity.personInfoctgId);
		return domain;
	}
	
	
	private BsymtEmpFileManagement toEntityEmpFileManagement(PersonFileManagement domain) {
		BsymtEmpFileManagement entity = new BsymtEmpFileManagement();
		entity.bsymtEmpFileManagementPK = new BsymtEmpFileManagementPK(domain.getFileID());
		entity.pid = domain.getPId();
		entity.filetype = domain.getTypeFile().value;
		entity.disPOrder = domain.getUploadOrder();
		entity.personInfoctgId = domain.getPersonInfoCategoryId();
		return entity;
	}
	
	/**
	 * @param listEmpFileManagementEntity
	 * @return
	 */
	private List<PersonFileManagement> toListEmpFileManagement(List<BsymtEmpFileManagement> listEntity) {
		List<PersonFileManagement> lstEmpFileManagement = new ArrayList<>();
		if (!listEntity.isEmpty()) {
			listEntity.stream().forEach(c -> {
				PersonFileManagement empFileManagement = toDomainEmpFileManagement(c);
				
				lstEmpFileManagement.add(empFileManagement);
			});
		}
		return lstEmpFileManagement;
	}
	

	@Override
	public void insert(PersonFileManagement domain) {
		BsymtEmpFileManagement entity = toEntityEmpFileManagement(domain);
		getEntityManager().persist(entity);
	}

	@Override
	public void remove(PersonFileManagement domain) {
		Optional<BsymtEmpFileManagement> entity = this.queryProxy().find(new BsymtEmpFileManagementPK(domain.getFileID()), BsymtEmpFileManagement.class);
		if(entity.isPresent())
			this.commandProxy().remove(entity.get());
	}

	@Override
	public List<PersonFileManagement> getDataByParams(String employeeId, int filetype) {
		List<BsymtEmpFileManagement> listFile = this.queryProxy().query(GET_ALL_BY_SID, BsymtEmpFileManagement.class)
				.setParameter("sid", employeeId)
				.setParameter("filetype", filetype)
				.getList();

		return toListEmpFileManagement(listFile);
	}


	@Override
	public List<Object[]> getListDocumentFile(String employeeId, int filetype) {
		List<Object[]> listFile = this.queryProxy().query(GET_ALL_DOCUMENT_FILE, Object[].class)
				.setParameter("sid", employeeId)
				.setParameter("filetype", filetype)
				.getList();
		return listFile;
	}


	@Override
	public Optional<PersonFileManagement> getEmpMana(String fileid) {
		
		BsymtEmpFileManagement entity = this.queryProxy().query(GET_BY_FILEID, BsymtEmpFileManagement.class)
				.setParameter("fileid", fileid).getSingleOrNull();

		PersonFileManagement empFileMana = new PersonFileManagement();
		if (entity != null) {
			empFileMana = toDomainEmpFileManagement(entity);
			return Optional.of(empFileMana);

		} else {
			return Optional.empty();
		}
	}


	@Override
	public void update(PersonFileManagement domain) {
		String fileid = domain.getFileID();
		BsymtEmpFileManagement entity = this.queryProxy().query(GET_BY_FILEID, BsymtEmpFileManagement.class)
				.setParameter("fileid", fileid).getSingleOrNull();
		
		entity.personInfoctgId = domain.getPersonInfoCategoryId();
		this.commandProxy().update(entity);
		
	}


	@Override
	public boolean checkObjectExist(String employeeId, int fileType) {
		Optional<Long> count = this.queryProxy().query(CHECK_EXIST, long.class)
				.setParameter("sid", employeeId)
				.setParameter("filetype", fileType)
				.getSingle();
		if(!count.isPresent()) return false;
		else{
			return count.get().intValue() > 0;
		}
	}


	@Override
	public void removebyFileId(String fileId) {
		BsymtEmpFileManagementPK pk = new BsymtEmpFileManagementPK(fileId);
		this.commandProxy().remove(BsymtEmpFileManagement.class , pk);
	}


	
}
