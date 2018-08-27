package nts.uk.ctx.pereg.infra.repository.filemanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.filemanagement.EmpFileManagementRepository;
import nts.uk.ctx.pereg.dom.filemanagement.PersonFileManagement;
import nts.uk.ctx.pereg.infra.entity.filemanagement.BpsmtPersonFileManagement;
import nts.uk.ctx.pereg.infra.entity.filemanagement.BpsmtPersonFileManagementPK;

@Stateless
@Transactional
public class JpaEmployeeFileManagement extends JpaRepository implements EmpFileManagementRepository {

	public static final String GET_ALL_BY_PID = "SELECT c FROM BpsmtPersonFileManagement c WHERE c.pid = :pid AND c.filetype = :filetype";

	public static final String CHECK_EXIST = "SELECT COUNT(c) FROM BpsmtPersonFileManagement c WHERE c.pid = :pid AND c.filetype = :filetype";

	public static final String GET_BY_FILEID = "SELECT c FROM BpsmtPersonFileManagement c WHERE c.bsymtPerFileManagementPK.fileid = :fileid ";

	public static final String DELETE_BY_PID_FILETYPE = "DELETE FROM BpsmtPersonFileManagement c WHERE c.pid = :pid AND c.filetype = :filetype";

	// public final String DELETE_DOCUMENT_BY_FILEID = "DELETE FROM
	// BsymtPersonFileManagement c WHERE c.BsymtPersonFileManagementPK.fileid =
	// :fileid";

	public static final String GET_ALL_DOCUMENT_FILE = "SELECT c.pid , c.bsymtPerFileManagementPK.fileid , c.disPOrder FROM BpsmtPersonFileManagement c "
			+ " WHERE c.pid = :pid AND c.filetype = :filetype ORDER BY c.disPOrder ASC";

	private PersonFileManagement toDomainEmpFileManagement(BpsmtPersonFileManagement entity) {
		val domain = PersonFileManagement.createFromJavaType(entity.pid, entity.bsymtPerFileManagementPK.fileid,
				entity.filetype, entity.disPOrder);
		return domain;
	}

	private BpsmtPersonFileManagement toEntityEmpFileManagement(PersonFileManagement domain) {
		BpsmtPersonFileManagement entity = new BpsmtPersonFileManagement();
		entity.bsymtPerFileManagementPK = new BpsmtPersonFileManagementPK(domain.getFileID());
		entity.pid = domain.getPId();
		entity.filetype = domain.getTypeFile().value;
		entity.disPOrder = domain.getUploadOrder();
		return entity;
	}

	/**
	 * @param listEmpFileManagementEntity
	 * @return
	 */
	private List<PersonFileManagement> toListEmpFileManagement(List<BpsmtPersonFileManagement> listEntity) {
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
		BpsmtPersonFileManagement entity = toEntityEmpFileManagement(domain);
		getEntityManager().persist(entity);
		this.getEntityManager().flush();
	}

	@Override
	public void remove(PersonFileManagement domain) {
		/*Optional<BpsmtPersonFileManagement> entity = this.queryProxy().find(new BpsmtPersonFileManagementPK(domain.getFileID()), BpsmtPersonFileManagement.class);
		if(entity.isPresent())
			this.commandProxy().remove(entity.get());*/
		this.getEntityManager().createQuery(DELETE_BY_PID_FILETYPE, BpsmtPersonFileManagement.class)
		.setParameter("pid", domain.getPId().toString())
		.setParameter("filetype", domain.getTypeFile().value).executeUpdate();
		
		
	}

	@Override
	public List<PersonFileManagement> getDataByParams(String pid, int filetype) {
		List<BpsmtPersonFileManagement> listFile = this.queryProxy()
				.query(GET_ALL_BY_PID, BpsmtPersonFileManagement.class).setParameter("pid", pid)
				.setParameter("filetype", filetype).getList();

		return toListEmpFileManagement(listFile);
	}

	@Override
	public List<Object[]> getListDocumentFile(String pid, int filetype) {
		List<Object[]> listFile = this.queryProxy().query(GET_ALL_DOCUMENT_FILE, Object[].class)
				.setParameter("pid", pid).setParameter("filetype", filetype).getList();
		return listFile;
	}

	@Override
	public Optional<PersonFileManagement> getEmpMana(String fileid) {

		BpsmtPersonFileManagement entity = this.queryProxy().query(GET_BY_FILEID, BpsmtPersonFileManagement.class)
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
		BpsmtPersonFileManagement entity = this.queryProxy().query(GET_BY_FILEID, BpsmtPersonFileManagement.class)
				.setParameter("fileid", fileid).getSingleOrNull();
		this.commandProxy().update(entity);

	}

	@Override
	public boolean checkObjectExist(String employeeId, int fileType) {
		Optional<Long> count = this.queryProxy().query(CHECK_EXIST, long.class).setParameter("pid", employeeId)
				.setParameter("filetype", fileType).getSingle();
		if (!count.isPresent())
			return false;
		else {
			return count.get().intValue() > 0;
		}
	}

	@Override
	public void removebyFileId(String fileId) {
		BpsmtPersonFileManagementPK pk = new BpsmtPersonFileManagementPK(fileId);
		this.commandProxy().remove(BpsmtPersonFileManagement.class, pk);
	}

}
