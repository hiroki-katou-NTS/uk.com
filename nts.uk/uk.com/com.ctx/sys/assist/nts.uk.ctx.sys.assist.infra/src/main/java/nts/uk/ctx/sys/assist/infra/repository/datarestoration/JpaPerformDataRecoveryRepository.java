package nts.uk.ctx.sys.assist.infra.repository.datarestoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.Target;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.infra.entity.datarestoration.SspmtPerformDataRecovery;
import nts.uk.ctx.sys.assist.infra.entity.datarestoration.SspmtTarget;
import nts.uk.ctx.sys.assist.infra.entity.tablelist.SspmtTableList;

@Stateless
public class JpaPerformDataRecoveryRepository extends JpaRepository implements PerformDataRecoveryRepository {
	
	private static final String SELECT_ALL_QUERY_STRING = "SELECT t FROM SspmtTableList t WHERE  t.tableListPk.categoryId =:categoryId AND storageRangeSaved =:storageRangeSaved";
	
	private static final String SELECT_ALL_TARGET = "SELECT t FROM SspmtTarget t WHERE  t.targetPk.dataRecoveryProcessId =:dataRecoveryProcessId ";
	
	private static final String SELECT_INTERNAL_FILE_NAME = "SELECT t FROM SspmtTableList t WHERE  t.targetPk.dataRecoveryProcessId =:dataRecoveryProcessId AND t.internalFileName =:internalFileName ";
	
	private static final StringBuilder COUNT_BY_TABLE_SQL = new StringBuilder("SELECT count(*) from ");
	
	private static final StringBuilder DELETE_BY_TABLE_SQL = new StringBuilder("DELETE FROM ");
	
	private static final StringBuilder INSERT_BY_TABLE = new StringBuilder("INSERT INTO ");
	
	@Override
	public Optional<PerformDataRecovery> getPerformDatRecoverById(String dataRecoveryProcessId) {
		return Optional.ofNullable(
				this.getEntityManager().find(SspmtPerformDataRecovery.class, dataRecoveryProcessId).toDomain());
	}

	@Override
	public void add(PerformDataRecovery domain) {
		this.commandProxy().insert(SspmtPerformDataRecovery.toEntity(domain));
	}

	@Override
	public void update(PerformDataRecovery domain) {
		this.commandProxy().update(SspmtPerformDataRecovery.toEntity(domain));
	}

	@Override
	public void remove(String dataRecoveryProcessId) {
		this.commandProxy().remove(SspmtPerformDataRecovery.class, dataRecoveryProcessId);
	}
	
	
	@Override
	public List<TableList> getByStorageRangeSaved(String categoryId,String storageRangeSaved) {
		List<SspmtTableList> listTable = this.getEntityManager()
				.createQuery(SELECT_ALL_QUERY_STRING, SspmtTableList.class)
				.setParameter("categoryId", categoryId)
				.setParameter("storageRangeSaved", storageRangeSaved)
				.getResultList();

		return listTable.stream().map(item -> item.toDomain()).collect(Collectors.toList());
	}

	@Override
	public List<Target> findByDataRecoveryId(String dataRecoveryProcessId) {
		List<SspmtTarget> listTarget  = this.getEntityManager()
				.createQuery(SELECT_ALL_TARGET, SspmtTarget.class)
				.setParameter("dataRecoveryProcessId", dataRecoveryProcessId)
				.getResultList();

		return listTarget.stream().map(item -> item.toDomain()).collect(Collectors.toList());
	}
	
	@Override
	public Optional<TableList> getByInternal(String internalFileName, String dataRecoveryProcessId) {
		return this.queryProxy().query(SELECT_INTERNAL_FILE_NAME, SspmtTableList.class)
				.setParameter("internalFileName", internalFileName)
				.setParameter("dataRecoveryProcessId", dataRecoveryProcessId)
				.getSingle(SspmtTableList::toDomain);
	}

	@Override
	public int countDataExitTableByVKeyUp (Map<String, String> filedWhere, String tableName) {
		if(tableName != null) {
			COUNT_BY_TABLE_SQL.append(tableName).append(" WHERE ");
		}
		int i = 0 ;
		for (Map.Entry<String,String> filed : filedWhere.entrySet()) {
	          if(!filed.getValue().toString().isEmpty()) {
	        	  i++;
	        	  if(i !=0) {
	        		  COUNT_BY_TABLE_SQL.append(" AND ").append(filed.getKey() + " = ").append(filed.getValue());
	        	  } else {
	        		  COUNT_BY_TABLE_SQL.append(filed.getKey() + " = ").append(filed.getValue());
	        	  }
	          }
	     }
		
		Integer countData = (Integer)  this.getEntityManager().createNativeQuery(COUNT_BY_TABLE_SQL.toString()).getSingleResult();
		
		return countData;
	}

	@Override
	public void deleteDataExitTableByVkey(Map<String, String> filedWhere, String tableName) {
		
		EntityManager em = this.getEntityManager();
		
		if(tableName != null) {
			DELETE_BY_TABLE_SQL.append(tableName).append(" WHERE ");
		}
		int i = 0 ;
		for (Map.Entry<String,String> filed : filedWhere.entrySet()) {
	          if(!filed.getValue().toString().isEmpty()) {
	        	  i++;
	        	  if(i !=0) {
	        		  COUNT_BY_TABLE_SQL.append(" AND ").append(filed.getKey() + " = ").append(filed.getValue());
	        	  } else {
	        		  COUNT_BY_TABLE_SQL.append(filed.getKey() + " = ").append(filed.getValue());
	        	  }
	          }
	     }
		
		Query query = em.createNativeQuery(DELETE_BY_TABLE_SQL.toString());
		query.executeUpdate();
		
	}

	@Override
	public void insertDataTable(Map<String, String> dataInsertDB, String tableName) {
		if(tableName != null) {
			INSERT_BY_TABLE.append(tableName);
		}
		EntityManager em = this.getEntityManager();
		List<String> columns = new ArrayList<>();
		List<String> values = new ArrayList<>();
		for (Map.Entry<String,String> filed : dataInsertDB.entrySet()) {
			columns.add(filed.getKey());
			values.add(filed.getValue());
		}
		INSERT_BY_TABLE.append(columns);
		INSERT_BY_TABLE.append(" VALUES ");
		INSERT_BY_TABLE.append(values);
		Query query = em.createNativeQuery(INSERT_BY_TABLE.toString());
		query.executeUpdate();
		
	}
}
