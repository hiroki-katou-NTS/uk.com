package nts.uk.ctx.sys.assist.infra.repository.categoryfordelete;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.assist.dom.categoryfordelete.CategoryForDelete;
import nts.uk.ctx.sys.assist.dom.categoryfordelete.CategoryForDeleteRepository;
import nts.uk.ctx.sys.assist.dom.categoryfordelete.SystemUsability;
import nts.uk.ctx.sys.assist.dom.categoryfordelete.TimeStore;
import nts.uk.ctx.sys.assist.infra.entity.categoryfordelete.SspmtCategoryForDelete;

@Stateless
public class JpaCategoryForDeleteRepository extends JpaRepository implements CategoryForDeleteRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspmtCategoryForDelete f";

	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.categoryId =:categoryId ";
	private static final String SELECT_BY_ATTENDANCE_SYSTEM = SELECT_ALL_QUERY_STRING
			+ " WHERE f.attendanceSystem =:attendanceSystem";
	private static final String SELECT_BY_PAYMENT_AVAIABILITY = SELECT_ALL_QUERY_STRING
			+ " WHERE f.paymentAvailability =:paymentAvailability";
	private static final String SELECT_BY_POSSIBILITY_SYSTEM = SELECT_ALL_QUERY_STRING
			+ " WHERE f.possibilitySystem =:possibilitySystem";
	private static final String SELECT_BY_SCHELPER_SYSTEM = SELECT_ALL_QUERY_STRING
			+ " WHERE f.schelperSystem =:schelperSystem";
	private static final String SELECT_BY_ATTENDANCE_SYSTEM_AND_CODENAME = SELECT_ALL_QUERY_STRING
			+ " WHERE ( f.categoryId like :keySearch OR f.categoryName LIKE :keySearch ) and f.timeStore <> :timeStore and f.categoryId NOT IN :categoriesIgnore and f.attendanceSystem = :attendanceSystem ORDER BY f.attendanceSystem,f.categoryId";
	private static final String SELECT_BY_PAYMENT_AVAIABILITY_AND_CODENAME = SELECT_ALL_QUERY_STRING
			+ " WHERE ( f.categoryId like :keySearch OR f.categoryName LIKE :keySearch ) and f.timeStore <> :timeStore and f.categoryId NOT IN :categoriesIgnore and f.paymentAvailability = :paymentAvailability ORDER BY f.paymentAvailability,f.categoryId";
	private static final String SELECT_BY_POSSIBILITY_SYSTEM_AND_CODENAME = SELECT_ALL_QUERY_STRING
			+ " WHERE ( f.categoryId like :keySearch OR f.categoryName LIKE :keySearch ) and f.timeStore <> :timeStore and f.categoryId NOT IN :categoriesIgnore and f.possibilitySystem = :possibilitySystem ORDER BY f.possibilitySystem,f.categoryId";
	private static final String SELECT_BY_SCHELPER_SYSTEM_AND_CODENAME = SELECT_ALL_QUERY_STRING
			+ " WHERE ( f.categoryId like :keySearch OR f.categoryName LIKE :keySearch ) and f.timeStore <> :timeStore and f.categoryId NOT IN :categoriesIgnore and f.schelperSystem = :schelperSystem ORDER BY f.possibilitySystem,f.categoryId";

	private static final String SELECT_BY_ATTENDANCE_SYSTEM_AND_CODENAME_CATEIGNORE = SELECT_ALL_QUERY_STRING
			+ " WHERE ( f.categoryId like :keySearch OR f.categoryName LIKE :keySearch ) and f.timeStore <> :timeStore  and f.attendanceSystem = :attendanceSystem ORDER BY f.attendanceSystem,f.categoryId";
	private static final String SELECT_BY_PAYMENT_AVAIABILITY_AND_CODENAME_CATEIGNORE = SELECT_ALL_QUERY_STRING
			+ " WHERE ( f.categoryId like :keySearch OR f.categoryName LIKE :keySearch ) and f.timeStore <> :timeStore  and f.paymentAvailability = :paymentAvailability ORDER BY f.paymentAvailability,f.categoryId";
	private static final String SELECT_BY_POSSIBILITY_SYSTEM_AND_CODENAME_CATEIGNORE = SELECT_ALL_QUERY_STRING
			+ " WHERE ( f.categoryId like :keySearch OR f.categoryName LIKE :keySearch ) and f.timeStore <> :timeStore  and f.possibilitySystem = :possibilitySystem ORDER BY f.possibilitySystem,f.categoryId";
	private static final String SELECT_BY_SCHELPER_SYSTEM_AND_CODENAME_CATEIGNORE = SELECT_ALL_QUERY_STRING
			+ " WHERE ( f.categoryId like :keySearch OR f.categoryName LIKE :keySearch ) and f.timeStore <> :timeStore  and f.schelperSystem = :schelperSystem ORDER BY f.possibilitySystem,f.categoryId";

	private static final String SELECT_BY_LIST_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.categoryId IN :lstCID ";
	
	private static final String SELECT_BY_ID = "SELECT f FROM SspmtCategoryForDelete f WHERE f.categoryId IN ( SELECT t.tableListPk.categoryId FROM SspmtTableList t WHERE  t.dataRecoveryProcessId =:storeProcessingId AND t.selectionTargetForRes =:selectionTargetForRes )";
	

	@Override
	public List<CategoryForDelete> getAllCategory() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspmtCategoryForDelete.class).getList(item -> item.toDomain());
	}

	@Override
	public Optional<CategoryForDelete> getCategoryById(String categoryId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, SspmtCategoryForDelete.class).setParameter("categoryId", categoryId)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public void add(CategoryForDelete domain) {
		this.commandProxy().insert(SspmtCategoryForDelete.toEntity(domain));
	}

	@Override
	public void update(CategoryForDelete domain) {
		this.commandProxy().update(SspmtCategoryForDelete.toEntity(domain));
	}

	@Override
	public void remove(String categoryId) {
		this.commandProxy().remove(SspmtCategoryForDelete.class, categoryId);
	}

	@Override
	public Optional<CategoryForDelete> getCategoryById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CategoryForDelete> findByAttendanceSystem() {
		return this.queryProxy().query(SELECT_BY_ATTENDANCE_SYSTEM, SspmtCategoryForDelete.class)
				.setParameter("attendanceSystem", SystemUsability.AVAILABLE.value).getList(c -> c.toDomain());
	}

	@Override
	public List<CategoryForDelete> findByPaymentAvailability() {
		return this.queryProxy().query(SELECT_BY_PAYMENT_AVAIABILITY, SspmtCategoryForDelete.class)
				.setParameter("paymentAvailability", SystemUsability.AVAILABLE.value).getList(c -> c.toDomain());
	}

	@Override
	public List<CategoryForDelete> findByPossibilitySystem() {
		return this.queryProxy().query(SELECT_BY_POSSIBILITY_SYSTEM, SspmtCategoryForDelete.class)
				.setParameter("possibilitySystem", SystemUsability.AVAILABLE.value).getList(c -> c.toDomain());
	}

	@Override
	public List<CategoryForDelete> findBySchelperSystem() {
		return this.queryProxy().query(SELECT_BY_SCHELPER_SYSTEM, SspmtCategoryForDelete.class)
				.setParameter("schelperSystem", SystemUsability.AVAILABLE.value).getList(c -> c.toDomain());
	}

	@Override
	public List<CategoryForDelete> findByAttendanceSystemAndCodeName(String keySearch, List<String> categoriesIgnore) {
		List<CategoryForDelete> resultList = new ArrayList<>();
		if (categoriesIgnore == null || categoriesIgnore.isEmpty()) {
			resultList.addAll(this.queryProxy().query(SELECT_BY_ATTENDANCE_SYSTEM_AND_CODENAME_CATEIGNORE, SspmtCategoryForDelete.class)
					.setParameter("keySearch", "%" + keySearch + "%")
					.setParameter("timeStore", TimeStore.FULL_TIME.value)
					.setParameter("attendanceSystem", SystemUsability.AVAILABLE.value).getList(c -> c.toDomain()));
		} else {
			CollectionUtil.split(categoriesIgnore, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
				resultList.addAll(this.queryProxy().query(SELECT_BY_ATTENDANCE_SYSTEM_AND_CODENAME, SspmtCategoryForDelete.class)
					.setParameter("keySearch", "%" + keySearch + "%").setParameter("categoriesIgnore", subList)
					.setParameter("timeStore", TimeStore.FULL_TIME.value)
					.setParameter("attendanceSystem", SystemUsability.AVAILABLE.value)
					.getList(c -> c.toDomain()));
			});
			resultList.sort((o1, o2) -> {
				int tmp = o1.getAttendanceSystem().value - o2.getAttendanceSystem().value;
				if (tmp != 0) return tmp;
				return o1.getCategoryId().compareTo(o2.getCategoryId());
			});
		}
		return resultList;
	}

	@Override
	public List<CategoryForDelete> findByPaymentAvailabilityAndCodeName(String keySearch, List<String> categoriesIgnore) {
		List<CategoryForDelete> resultList = new ArrayList<>();
		if (categoriesIgnore == null || categoriesIgnore.isEmpty()) {
			resultList.addAll(this.queryProxy().query(SELECT_BY_PAYMENT_AVAIABILITY_AND_CODENAME_CATEIGNORE, SspmtCategoryForDelete.class)
					.setParameter("keySearch", "%" + keySearch + "%")
					.setParameter("timeStore", TimeStore.FULL_TIME.value)
					.setParameter("paymentAvailability", SystemUsability.AVAILABLE.value).getList(c -> c.toDomain()));
		} else {
			CollectionUtil.split(categoriesIgnore, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
				resultList.addAll(this.queryProxy().query(SELECT_BY_PAYMENT_AVAIABILITY_AND_CODENAME, SspmtCategoryForDelete.class)
					.setParameter("keySearch", "%" + keySearch + "%").setParameter("categoriesIgnore", subList)
					.setParameter("timeStore", TimeStore.FULL_TIME.value)
					.setParameter("paymentAvailability", SystemUsability.AVAILABLE.value).getList(c -> c.toDomain()));
			});
			resultList.sort((o1, o2) -> {
				int tmp = o1.getPaymentAvailability().value - o2.getPaymentAvailability().value;
				if (tmp != 0) return tmp;
				return o1.getCategoryId().compareTo(o2.getCategoryId());
			});
		}
		return resultList;
	}

	@Override
	public List<CategoryForDelete> findByPossibilitySystemAndCodeName(String keySearch, List<String> categoriesIgnore) {
		List<CategoryForDelete> resultList = new ArrayList<>();
		if (categoriesIgnore == null || categoriesIgnore.isEmpty()) {
			resultList.addAll(this.queryProxy().query(SELECT_BY_POSSIBILITY_SYSTEM_AND_CODENAME_CATEIGNORE, SspmtCategoryForDelete.class)
					.setParameter("keySearch", "%" + keySearch + "%")
					.setParameter("timeStore", TimeStore.FULL_TIME.value)
					.setParameter("possibilitySystem", SystemUsability.AVAILABLE.value).getList(c -> c.toDomain()));
		} else {
			CollectionUtil.split(categoriesIgnore, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
				resultList.addAll(this.queryProxy().query(SELECT_BY_POSSIBILITY_SYSTEM_AND_CODENAME, SspmtCategoryForDelete.class)
						.setParameter("keySearch", "%" + keySearch + "%").setParameter("categoriesIgnore", subList)
						.setParameter("timeStore", TimeStore.FULL_TIME.value)
						.setParameter("possibilitySystem", SystemUsability.AVAILABLE.value).getList(c -> c.toDomain()));
			});
			resultList.sort((o1, o2) -> {
				int tmp = o1.getPossibilitySystem().value - o2.getPossibilitySystem().value;
				if (tmp != 0) return tmp;
				return o1.getCategoryId().compareTo(o2.getCategoryId());
			});
		}
		return resultList;
	}

	@Override
	public List<CategoryForDelete> findBySchelperSystemAndCodeName(String keySearch, List<String> categoriesIgnore) {
		List<CategoryForDelete> resultList = new ArrayList<>();
		if (categoriesIgnore == null || categoriesIgnore.isEmpty()) {
			resultList.addAll(this.queryProxy().query(SELECT_BY_SCHELPER_SYSTEM_AND_CODENAME_CATEIGNORE, SspmtCategoryForDelete.class)
					.setParameter("keySearch", "%" + keySearch + "%")
					.setParameter("timeStore", TimeStore.FULL_TIME.value)
					.setParameter("schelperSystem", SystemUsability.AVAILABLE.value).getList(c -> c.toDomain()));
		} else {
			CollectionUtil.split(categoriesIgnore, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
				resultList.addAll(this.queryProxy().query(SELECT_BY_SCHELPER_SYSTEM_AND_CODENAME, SspmtCategoryForDelete.class)
					.setParameter("keySearch", "%" + keySearch + "%").setParameter("categoriesIgnore", subList)
					.setParameter("timeStore", TimeStore.FULL_TIME.value)
					.setParameter("schelperSystem", SystemUsability.AVAILABLE.value).getList(c -> c.toDomain()));
			});
			resultList.sort((o1, o2) -> {
				int tmp = o1.getPossibilitySystem().value - o2.getPossibilitySystem().value;
				if (tmp != 0) return tmp;
				return o1.getCategoryId().compareTo(o2.getCategoryId());
			});
		}
		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.assist.dom.category.CategoryRepository#getCategoryByListId
	 * (java.util.List)
	 */
	@Override
	public List<CategoryForDelete> getCategoryByListId(List<String> categoryIds) {

		// check exist input
		if (CollectionUtil.isEmpty(categoryIds)) {
			return new ArrayList<>();
		}

		List<CategoryForDelete> lstCategory = new ArrayList<>();
		CollectionUtil.split(categoryIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subIdList -> {
			lstCategory.addAll(this.queryProxy().query(SELECT_BY_LIST_KEY_STRING, SspmtCategoryForDelete.class)
					.setParameter("lstCID", subIdList).getList(f -> f.toDomain()));
		});
				
		return lstCategory;
	}

	@Override
	public List<CategoryForDelete> findById(String storeProcessingId, int selectionTargetForRes) {
		return this.queryProxy().query(SELECT_BY_ID, SspmtCategoryForDelete.class)
				.setParameter("storeProcessingId", storeProcessingId)
				.setParameter("selectionTargetForRes", selectionTargetForRes)
				.getList(c -> c.toDomain());
	}
}
