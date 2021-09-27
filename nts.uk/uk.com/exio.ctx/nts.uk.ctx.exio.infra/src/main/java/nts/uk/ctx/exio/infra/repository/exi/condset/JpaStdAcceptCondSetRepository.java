package nts.uk.ctx.exio.infra.repository.exi.condset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceLineNumber;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.ctx.exio.infra.entity.exi.condset.OiomtExAcCond;
import nts.uk.ctx.exio.infra.entity.exi.condset.OiomtStdAcceptCondSetPk;

import javax.ejb.Stateless;

import org.apache.commons.lang3.BooleanUtils;

import java.util.List;
import java.util.Optional;

/**
 * The class Jpa standard acceptance condition setting repository.<br>
 * Repository 受入条件設定（定型）
 */
@Stateless
public class JpaStdAcceptCondSetRepository extends JpaRepository implements StdAcceptCondSetRepository {

	/** The query select all by company id */
	private static final String SELECT_ALL_BY_COMPANY_ID = "SELECT c FROM OiomtExAcCond c "
			+ "WHERE c.stdAcceptCondSetPk.cid = :companyId "
			+ "ORDER BY c.stdAcceptCondSetPk.conditionSetCd";

	/** The query select all */
	private static final String SELECT_ALL = "SELECT c FROM OiomtExAcCond c "
			+ "WHERE c.stdAcceptCondSetPk.cid = :companyId AND c.stdAcceptCondSetPk.systemType = :systemType "
			+ "ORDER BY c.stdAcceptCondSetPk.conditionSetCd";

	/**
	 * Finds all standard acceptance condition settings by company id.
	 *
	 * @param companyId the company id
	 * @return the <code>StdAcceptCondSet</code> domain list
	 */
	@Override
	public List<StdAcceptCondSet> findAllStdAcceptCondSetsByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_ALL_BY_COMPANY_ID, OiomtExAcCond.class)
								.setParameter("companyId", companyId)
								.getList(entity -> StdAcceptCondSet.createFromMemento(entity.getCompanyId(), entity));
	}

	/**
	 * Gets standard acceptance condition settings by system type.
	 *
	 * @param cid     the company id
	 * @param sysType the system type
	 * @return the <code>StdAcceptCondSet</code> domain list
	 */
	@Override
	public List<StdAcceptCondSet> getStdAcceptCondSetBySysType(String cid, int sysType) {
		return this.queryProxy().query(SELECT_ALL, OiomtExAcCond.class)
								.setParameter("companyId", cid)
								.setParameter("systemType", sysType)
								.getList(entity -> StdAcceptCondSet.createFromMemento(entity.getCompanyId(), entity));
	}

	/**
	 * Gets standard acceptance condition setting by id.
	 *
	 * @param cid            the company id
	 * @param sysType        the system type
	 * @param conditionSetCd the condition set code
	 * @return the optional of domain standard acceptance condition setting
	 */
	@Override
	public Optional<StdAcceptCondSet> getStdAcceptCondSetById(String cid, int sysType, String conditionSetCd) {
		return this.queryProxy()
				   .find(new OiomtStdAcceptCondSetPk(cid, sysType, conditionSetCd), OiomtExAcCond.class)
				   .map(entity -> StdAcceptCondSet.createFromMemento(entity.getCompanyId(), entity));

	}

	/**
	 * Add.
	 *
	 * @param domain the domain
	 */
	@Override
	public void add(StdAcceptCondSet domain) {
		this.commandProxy().insert(new OiomtExAcCond(domain));
	}

	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	@Override
	public void update(StdAcceptCondSet domain) {
		OiomtExAcCond newStdAcceptCondSet = new OiomtExAcCond(domain);
		Optional<OiomtExAcCond> updateStdAcceptCondSet = this.queryProxy().find(newStdAcceptCondSet.getStdAcceptCondSetPk(),
																						OiomtExAcCond.class);
		if (updateStdAcceptCondSet.isPresent()) {
			OiomtExAcCond entity = updateStdAcceptCondSet.get();
			entity.setConditionSetName(domain.getConditionSetName().v());
			entity.setAcceptMode(domain.getAcceptMode()
									   .map(acceptMode -> acceptMode.value)
									   .orElse(null));
			entity.setDeleteExistData(domain.getDeleteExistData().value);
			entity.setDeleteExtDataMethod(domain.getDeleteExistDataMethod()
												.map(deleteExistDataMethod -> BooleanUtils.toBoolean(deleteExistDataMethod.value))
												.orElse(null));
			entity.setCharacterCode(domain.getCharacterCode()
										  .map(exiCharset -> exiCharset.value)
										  .orElse(null));
			entity.setCsvDataLineNumber(domain.getCsvDataItemLineNumber()
											  .map(AcceptanceLineNumber::v)
											  .orElse(null));
			entity.setCsvDataStartLine(domain.getCsvDataStartLine()
											 .map(AcceptanceLineNumber::v)
											 .orElse(null));
			entity.setCheckCompleted(domain.getCheckCompleted()
										   .map(checkCompleted -> BooleanUtils.toBoolean(checkCompleted.value))
										   .orElse(null));
			this.commandProxy().update(entity);
		}
	}

	/**
	 * Remove.
	 *
	 * @param cid            the company id
	 * @param sysType        the system type
	 * @param conditionSetCd the condition set code
	 */
	@Override
	public void remove(String cid, int sysType, String conditionSetCd) {
		this.commandProxy().remove(OiomtExAcCond.class,
								   new OiomtStdAcceptCondSetPk(cid, sysType, conditionSetCd));
	}

	/**
	 * Is setting code exist boolean.
	 *
	 * @param cid            the company id
	 * @param sysType        the system type
	 * @param conditionSetCd the condition set code
	 * @return the boolean
	 */
	@Override
	public boolean isSettingCodeExist(String cid, int sysType, String conditionSetCd) {
		return this.queryProxy().find(new OiomtStdAcceptCondSetPk(cid, sysType, conditionSetCd), OiomtExAcCond.class)
								.isPresent();
	}

	/**
	 * Update from d.
	 *
	 * @param domain the domain
	 */
	@Override
	public void updateFromD(StdAcceptCondSet domain) {
		OiomtExAcCond newStdAcceptCondSet = new OiomtExAcCond(domain);
		Optional<OiomtExAcCond> updateStdAcceptCondSet = this.queryProxy().find(newStdAcceptCondSet.getStdAcceptCondSetPk(),
																						OiomtExAcCond.class);
		if (updateStdAcceptCondSet.isPresent()) {
			OiomtExAcCond entity = updateStdAcceptCondSet.get();
			entity.setCategoryId(domain.getCategoryId().orElse(null));
			entity.setCheckCompleted(domain.getCheckCompleted()
										   .map(checkCompleted -> BooleanUtils.toBoolean(checkCompleted.value))
										   .orElse(null));
			entity.setCsvDataLineNumber(domain.getCsvDataItemLineNumber()
											  .map(AcceptanceLineNumber::v)
											  .orElse(null));
			entity.setCsvDataStartLine(domain.getCsvDataStartLine()
											 .map(AcceptanceLineNumber::v)
											 .orElse(null));
			entity.setCharacterCode(domain.getCharacterCode()
										  .map(exiCharset -> exiCharset.value)
										  .orElse(null));
			this.commandProxy().update(entity);
		}
	}

}
