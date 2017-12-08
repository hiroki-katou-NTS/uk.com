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
import nts.uk.ctx.bs.employee.infra.entity.perfilemanagement.BsymtPersonFileManagement;
import nts.uk.ctx.bs.employee.infra.entity.perfilemanagement.BsymtPersonFileManagementPK;


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
	
	private PersonFileManagement toDomainEmpFileManagement(BsymtPersonFileManagement entity) {
		val domain = PersonFileManagement.createFromJavaType(entity.pid,
				entity.bsymtPerFileManagementPK.fileid, entity.filetype, entity.disPOrder,
				entity.personInfoctgId);
		return domain;
	}
	
	
	private BsymtPersonFileManagement toEntityEmpFileManagement(PersonFileManagement domain) {
		BsymtPersonFileManagement entity = new BsymtPersonFileManagement();
		entity.bsymtPerFileManagementPK = new BsymtPersonFileManagementPK(domain.getFileID());
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
	private List<PersonFileManagement> toListEmpFileManagement(List<BsymtPersonFileManagement> listEntity) {
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
		BsymtPersonFileManagement entity = toEntityEmpFileManagement(domain);
		getEntityManager().persist(entity);
	}

	@Override
	public void remove(PersonFileManagement domain) {
		Optional<BsymtPersonFileManagement> entity = this.queryProxy().find(new BsymtPersonFileManagementPK(domain.getFileID()), BsymtPersonFileManagement.class);
		if(entity.isPresent())
			this.commandProxy().remove(entity.get());
	}

	@Override
	public List<PersonFileManagement> getDataByParams(String employeeId, int filetype) {
		List<BsymtPersonFileManagement> listFile = this.queryProxy().query(GET_ALL_BY_SID, BsymtPersonFileManagement.class)
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
		
		BsymtPersonFileManagement entity = this.queryProxy().query(GET_BY_FILEID, BsymtPersonFileManagement.class)
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
		BsymtPersonFileManagement entity = this.queryProxy().query(GET_BY_FILEID, BsymtPersonFileManagement.class)
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
		BsymtPersonFileManagementPK pk = new BsymtPersonFileManagementPK(fileId);
		this.commandProxy().remove(BsymtPersonFileManagement.class , pk);
	}


	
}
