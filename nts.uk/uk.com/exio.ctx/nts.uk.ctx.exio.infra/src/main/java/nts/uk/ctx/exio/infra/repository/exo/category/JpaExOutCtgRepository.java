package nts.uk.ctx.exio.infra.repository.exo.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.Query;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.category.CategorySetting;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtgRepository;
import nts.uk.ctx.exio.infra.entity.exo.category.OiomtExOutCtg;

@Stateless
public class JpaExOutCtgRepository extends JpaRepository implements ExOutCtgRepository {

	private static final String SQL = "sql";
	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtExOutCtg f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.functionNo =:functionNo ";
	private static final String SELECT_BY_ID_AND_SETTING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.categoryId =:categoryId and f.categorySet != 0";
	private static final String SELECT_BY_ID = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.categoryId =:categoryId ";

	private static final String SELECT_BY_EXCLUDED_SETTING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.categorySet <> :excludedCategorySet ORDER BY f.categoryId";
	@Override
	public List<ExOutCtg> getAllExOutCtg() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtExOutCtg.class).getList(item -> item.toDomain());
	}

	@Override
	public List<ExOutCtg> getExOutCtgList(CategorySetting excludedCategorySet) {
		return this.queryProxy().query(SELECT_BY_EXCLUDED_SETTING, OiomtExOutCtg.class)
				.setParameter("excludedCategorySet", excludedCategorySet.value).getList(item -> item.toDomain());
	}

	@Override
	public Optional<ExOutCtg> getExOutCtgById(String functionNo) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExOutCtg.class).setParameter("functionNo", functionNo)
				.getSingle(c -> c.toDomain());
	}
	
	@Override
	public Optional<ExOutCtg> getExOutCtgByIdAndCtgSetting(Integer categoryId) {
		return this.queryProxy().query(SELECT_BY_ID_AND_SETTING, OiomtExOutCtg.class)
				.setParameter("categoryId", categoryId)
				.getSingle(c -> c.toDomain());
	}
	@Override
	public Optional<ExOutCtg> getExOutCtgByCtgId(Integer categoryId) {
		return Optional.ofNullable(this.queryProxy().query(SELECT_BY_ID, OiomtExOutCtg.class)
				.setParameter("categoryId", categoryId).getList().get(0).toDomain());
				
	}
	
	@Override
	public void add(ExOutCtg domain) {
		this.commandProxy().insert(OiomtExOutCtg.toEntity(domain));
	}

	@Override
	public void update(ExOutCtg domain) {
		this.commandProxy().update(OiomtExOutCtg.toEntity(domain));
	}

	@Override
	public void remove(int functionNo) {
		this.commandProxy().remove(OiomtExOutCtg.class, functionNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<List<String>> getData(Map<String, String> sqlAndParams) {
		String sql = sqlAndParams.get(SQL);
		sqlAndParams.remove(SQL);
		
		Query queryString = getEntityManager().createNativeQuery(sql);
		for (Entry<String, String> entry : sqlAndParams.entrySet()) {
			queryString.setParameter(entry.getKey(), entry.getValue());
		}
		
		List<Object[]> listTemp = (List<Object[]>) queryString.getResultList();
		
		return listTemp.stream().map(objects -> {
			List<String> record = new ArrayList<String>();
			for (Object field : objects) {
				record.add(field != null ? String.valueOf(field) : "");
			}
			return record;
		}).collect(Collectors.toList());
	}
}
