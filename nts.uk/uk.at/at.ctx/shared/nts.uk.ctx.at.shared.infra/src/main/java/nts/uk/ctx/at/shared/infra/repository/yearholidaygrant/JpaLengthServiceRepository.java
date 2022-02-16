package nts.uk.ctx.at.shared.infra.repository.yearholidaygrant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthOfService;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayCode;
import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstLengthServiceTbl;
import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstLengthServiceTblPK;

@Stateless
public class JpaLengthServiceRepository extends JpaRepository implements LengthServiceRepository {
	private static final String SELECT_BY_CODE = "SELECT c FROM KshstLengthServiceTbl c "
			+ "WHERE c.kshstLengthServiceTblPK.companyId = :companyId "
			+ "AND c.kshstLengthServiceTblPK.yearHolidayCode = :yearHolidayCode ORDER BY c.kshstLengthServiceTblPK.grantNum ";

	private static final String DELETE_ALL = "DELETE FROM KshstLengthServiceTbl c "
			+ "WHERE c.kshstLengthServiceTblPK.companyId = :companyId ";

	private static final String DELETE_BY_CD = DELETE_ALL
			+ "AND c.kshstLengthServiceTblPK.yearHolidayCode = :yearHolidayCode ";

	private static final String DELETE_BY_KEY = DELETE_BY_CD + "AND c.kshstLengthServiceTblPK.grantNum IN :grantNums ";

	/**
	 * convert from KshstLengthServiceTbl entity to LengthServiceTbl domain
	 *
	 * @param entity
	 * @return
	 */
	private LengthOfService convertToDomain(KshstLengthServiceTbl entity) {
		return LengthOfService.createFromJavaType(entity.kshstLengthServiceTblPK.grantNum, entity.allowStatus,
				entity.standGrantDay, entity.year, entity.month);
	}

	private List<KshstLengthServiceTbl> toEntity(LengthServiceTbl domain) {

		String companyId = domain.getCompanyId();
		String code = domain.getYearHolidayCode().toString();

		return domain.getLengthOfServices().stream().map(c -> toEntityOneRecord(companyId, code, c))
				.collect(Collectors.toList());
	}

	private KshstLengthServiceTbl toEntityOneRecord(String companyId, String code, LengthOfService dom) {
		val entity = new KshstLengthServiceTbl();
		entity.kshstLengthServiceTblPK = new KshstLengthServiceTblPK(companyId, code, dom.getGrantNum().v());
		entity.allowStatus = dom.getAllowStatus().value;
		entity.month = dom.getMonth() != null ? dom.getMonth().v() : 0;
		entity.year = dom.getYear() != null ? dom.getYear().v() : 0;
		entity.standGrantDay = dom.getStandGrantDay().value;
		return entity;
	}

	/**
	 * find by code
	 *
	 * @author yennth
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<LengthServiceTbl> findByCode(String companyId, String yearHolidayCode) {
		List<KshstLengthServiceTbl> entitys = this.queryProxy().query(SELECT_BY_CODE, KshstLengthServiceTbl.class)
				.setParameter("companyId", companyId).setParameter("yearHolidayCode", yearHolidayCode).getList(c -> c);

		return toDomain(entitys);
	}

	private Optional<LengthServiceTbl> toDomain(List<KshstLengthServiceTbl> entitys) {
		if (entitys.isEmpty())
			return Optional.empty();

		List<LengthOfService> lengthOfServices = entitys.stream().map(c -> convertToDomain(c))
				.collect((Collectors.toList()));

		String companyId = entitys.get(0).kshstLengthServiceTblPK.companyId;
		String code = entitys.get(0).kshstLengthServiceTblPK.yearHolidayCode;

		return Optional.of(new LengthServiceTbl(companyId, new YearHolidayCode(code), lengthOfServices));
	}

	/**
	 * insert a length service
	 *
	 * @author yennth
	 */
	@Override
	public void add(LengthServiceTbl holidayGrant) {
		this.commandProxy().insertAll(toEntity(holidayGrant));
	}

	/**
	 * update a length service
	 */
	@Override
	public void update(LengthServiceTbl holidayGrant) {
		this.commandProxy().updateAll(toEntity(holidayGrant));
	}

	/**
	 * remove LengthServiceTbl by code
	 *
	 * @author yennth
	 */
	@Override
	public void remove(String companyId, String yearHolidayCode) {
		this.getEntityManager().createQuery(DELETE_BY_CD).setParameter("companyId", companyId)
				.setParameter("yearHolidayCode", yearHolidayCode).executeUpdate();
	}

	/**
	 * delete by companyId
	 *
	 * @author yennth
	 */
	@Override
	public void remove(String companyId) {
		this.getEntityManager().createQuery(DELETE_ALL).setParameter("companyId", companyId).executeUpdate();
	}

	/**
	 * remove LengthServiceTbl by list grantNum
	 *
	 * @author yennth
	 */
	@Override
	public void remove(String companyId, String yearHolidayCode, List<Integer> grantNums) {
		CollectionUtil.split(grantNums, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			this.getEntityManager().createQuery(DELETE_BY_KEY).setParameter("companyId", companyId)
					.setParameter("yearHolidayCode", yearHolidayCode).setParameter("grantNums", subList)
					.executeUpdate();
		});
		this.getEntityManager().flush();
	}

	@Override
	public List<LengthServiceTbl> findByCode(String companyId, List<String> yearHolidayCode) {
		if (yearHolidayCode.isEmpty())
			return new ArrayList<>();
		String query = "SELECT c FROM KshstLengthServiceTbl c "
				+ "WHERE c.kshstLengthServiceTblPK.companyId = :companyId "
				+ "AND c.kshstLengthServiceTblPK.yearHolidayCode IN :yearHolidayCode ORDER BY c.kshstLengthServiceTblPK.grantNum ";

		Map<String, List<KshstLengthServiceTbl>> entitys = this.queryProxy().query(query, KshstLengthServiceTbl.class)
				.setParameter("companyId", companyId).setParameter("yearHolidayCode", yearHolidayCode).getList(c -> c)
				.stream().collect(Collectors.groupingBy(i -> i.kshstLengthServiceTblPK.yearHolidayCode));

		return entitys.entrySet().stream().map(c -> toDomain(c.getValue()).get()).collect(Collectors.toList());

	}

}
