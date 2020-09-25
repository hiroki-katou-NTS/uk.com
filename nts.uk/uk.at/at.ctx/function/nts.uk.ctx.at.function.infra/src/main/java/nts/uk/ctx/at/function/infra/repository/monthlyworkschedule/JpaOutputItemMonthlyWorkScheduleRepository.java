package nts.uk.ctx.at.function.infra.repository.monthlyworkschedule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.ItemSelectionEnum;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkSchedule;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkScheduleRepository;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtRptWkMonOut;

@Stateless
public class JpaOutputItemMonthlyWorkScheduleRepository extends JpaRepository
		implements OutputItemMonthlyWorkScheduleRepository {

	private static final String FIND_BY_CODE_CID = "SELECT c FROM KfnmtRptWkMonOut c"
			+ "	WHERE c.companyID = :companyID" 
			+ " AND c.itemCode = :itemCode";

	private static final String FIND_BY_CID_ODER_BY = "SELECT c FROM KfnmtRptWkMonOut c"
			+ " WHERE c.companyID = :companyID"
			+ " ORDER BY c.companyID ASC ";
			
	private static final String FIND_BY_SELECTION_CID = "SELECT c FROM KfnmtRptWkMonOut c"
			+ " WHERE c.companyID = :companyID "
			+ " AND c.itemSelectionType = :itemSelectionType";;
	
	private static final String FIND_BY_SELECTION_CID_SID = "SELECT c FROM KfnmtRptWkMonOut c"
			+ " WHERE c.companyID = :companyID"
			+ " AND c.employeeID = :employeeID"
			+ " AND c.itemSelectionType = :itemSelectionType";

	private static final String FIND_BY_SELECTION_CID_CODE = "SELECT c FROM KfnmtRptWkMonOut c"
			+ " WHERE c.companyID = :companyID"
			+ " AND c.itemSelectionType = :itemSelectionType"
			+ " AND c.itemCode = :itemCode";

	private static final String FIND_BY_SELECTION_CID_CODE_SID = "SELECT c FROM KfnmtRptWkMonOut c"
			+ " WHERE c.companyID = :companyID"
			+ " AND c.itemCode = :itemCode"
			+ " AND c.employeeID = :employeeID"
			+ " AND c.itemSelectionType = :itemSelectionType";

	private static final String FIND_BY_SELECTION_NAME_CODE = "SELECT c FROM KfnmtRptWkMonOut c"
			+ " WHERE c.itemSelectionType = :itemSelectionType"
			+ " AND c.itemName = :itemName"
			+ " AND c.itemCode = :itemCode";
	
	private static final String FIND_BY_SELECTION_NAME_CODE_SID = "SELECT c FROM KfnmtRptWkMonOut c"
			+ " WHERE c.itemSelectionType = :itemSelectionType"
			+ " AND c.itemName = :itemName"
			+ " AND c.itemCode = :itemCode"
			+ " AND c.employeeID = :employeeID";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleRepository#findByCidAndCode(java.lang. String,
	 * java.lang.String)
	 */
	@Override
	public Optional<OutputItemMonthlyWorkSchedule> findByCidAndCode(String companyId, String code) {
		return this.queryProxy().query(FIND_BY_CODE_CID, KfnmtRptWkMonOut.class).setParameter("companyID", companyId)
				.setParameter("itemCode", code).getSingle(entity -> this.toDomain(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleRepository#findByCid(java.lang.String)
	 */
	@Override
	public List<OutputItemMonthlyWorkSchedule> findByCid(String companyId) {
		// Get results
		List<KfnmtRptWkMonOut> results = this.queryProxy().query(FIND_BY_CID_ODER_BY, KfnmtRptWkMonOut.class)
				.setParameter("companyID", companyId).getList();
		// Check empty
		if (CollectionUtil.isEmpty(results)) {
			return Collections.emptyList();
		}
		return results.stream().map(item -> new OutputItemMonthlyWorkSchedule(item)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleRepository#add(nts.uk.ctx.at.function.dom.
	 * monthlyworkschedule.OutputItemMonthlyWorkSchedule)
	 */
	@Override
	public void add(OutputItemMonthlyWorkSchedule domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleRepository#update(nts.uk.ctx.at.function.dom
	 * .monthlyworkschedule.OutputItemMonthlyWorkSchedule)
	 */
	@Override
	public void update(OutputItemMonthlyWorkSchedule domain) {
		this.commandProxy().update(this.toEntity(domain));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleRepository#delete(nts.uk.ctx.at.function.dom
	 * .monthlyworkschedule.OutputItemMonthlyWorkSchedule)
	 */
	@Override
	public void delete(OutputItemMonthlyWorkSchedule domain) {
		this.commandProxy().remove(this.toEntity(domain));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleRepository#deleteByCidAndCode(java.lang. String,
	 * java.lang.String)
	 */
	@Override
	public void deleteByCidAndCode(String companyId, String code) {
		Optional<KfnmtRptWkMonOut> kfnmtRptWkMonOut = this.queryProxy().query(FIND_BY_CODE_CID, KfnmtRptWkMonOut.class)
				 .setParameter("companyID", companyId)
				 .setParameter("itemCode", code)
				 .getSingle();
		if (kfnmtRptWkMonOut.isPresent()) {
			this.commandProxy().remove(kfnmtRptWkMonOut);
		}

		this.commandProxy().remove(kfnmtRptWkMonOut);

	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the output item monthly work schedule
	 */
	private OutputItemMonthlyWorkSchedule toDomain(KfnmtRptWkMonOut entity) {
		return new OutputItemMonthlyWorkSchedule(entity);
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kfnmt monthly work sche
	 */
	private KfnmtRptWkMonOut toEntity(OutputItemMonthlyWorkSchedule domain) {
		KfnmtRptWkMonOut entity = new KfnmtRptWkMonOut();
		domain.saveToMemento(entity);
		return entity;
	}

	@Override
	public List<OutputItemMonthlyWorkSchedule> findBySelectionAndCidAndSid(ItemSelectionEnum itemSelectionEnum,
			String companyId, Optional<String> employeeId) {
		if (itemSelectionEnum == ItemSelectionEnum.STANDARD_SELECTION) {
			return this.queryProxy().query(FIND_BY_SELECTION_CID, KfnmtRptWkMonOut.class)
					.setParameter("companyID", companyId)
					.setParameter("itemSelectionType", itemSelectionEnum)
					.getList(item -> new OutputItemMonthlyWorkSchedule(item));
		}
		if (itemSelectionEnum == ItemSelectionEnum.FREE_SETTING && employeeId.isPresent()) {
			return this.queryProxy().query(FIND_BY_SELECTION_CID_SID, KfnmtRptWkMonOut.class)
				  .setParameter("companyID", companyId)
				  .setParameter("employeeID", employeeId)
				  .setParameter("itemSelectionType", itemSelectionEnum)
				  .getList(item -> new OutputItemMonthlyWorkSchedule(item));
		}
		return Collections.emptyList();
	}

	@Override
	public void deleteBySelectionAndCidAndSidAndCode(ItemSelectionEnum itemSelectionEnum, 
			String itemCode, String companyId,Optional<String> employeeId) {
		Optional<KfnmtRptWkMonOut> kfnmtRptWkMonOut = null;
		if (itemSelectionEnum == ItemSelectionEnum.STANDARD_SELECTION) {
			kfnmtRptWkMonOut = this.queryProxy().query(FIND_BY_SELECTION_CID_CODE, KfnmtRptWkMonOut.class)
					.setParameter("companyID", companyId)
					.setParameter("itemSelectionType", itemSelectionEnum)
					.setParameter("itemCode", itemCode)
					.getSingle();
		}
		if (itemSelectionEnum == ItemSelectionEnum.FREE_SETTING&& employeeId.isPresent()) {
			kfnmtRptWkMonOut = this.queryProxy().query(FIND_BY_SELECTION_CID_CODE_SID, KfnmtRptWkMonOut.class)
				  .setParameter("companyID", companyId)
				  .setParameter("employeeID", employeeId)
				  .setParameter("itemCode", itemCode)
				  .setParameter("itemSelectionType", itemSelectionEnum)
				  .getSingle();
		}
		if (kfnmtRptWkMonOut.isPresent()) {
			this.commandProxy().remove(kfnmtRptWkMonOut);
		}
	}

	@Override
	public Optional<OutputItemMonthlyWorkSchedule> findBySelectionAndCidAndSidAndCode(
			ItemSelectionEnum itemSelectionEnum, String companyId, String itemCode, Optional<String> employeeId) {
		if (itemSelectionEnum == ItemSelectionEnum.STANDARD_SELECTION) {
			return this.queryProxy().query(FIND_BY_SELECTION_CID_CODE, KfnmtRptWkMonOut.class)
					.setParameter("companyID", companyId)
					.setParameter("itemSelectionType", itemSelectionEnum)
					.setParameter("itemCode", itemCode)
					.getSingle(entity -> this.toDomain(entity));
		}
		if (itemSelectionEnum == ItemSelectionEnum.FREE_SETTING&& employeeId.isPresent()) {
			return this.queryProxy().query(FIND_BY_SELECTION_CID_CODE_SID, KfnmtRptWkMonOut.class)
				  .setParameter("companyID", companyId)
				  .setParameter("employeeID", employeeId)
				  .setParameter("itemCode", itemCode)
				  .setParameter("itemSelectionType", itemSelectionEnum)
				  .getSingle(entity -> this.toDomain(entity));
		}
		return Optional.empty();
	}


	@Override
	public List<OutputItemMonthlyWorkSchedule> findBySelectionAndSidAndNameAndCode(ItemSelectionEnum itemSelectionEnum,
			String name, String code, Optional<String> employeeId) {
		if (itemSelectionEnum == ItemSelectionEnum.STANDARD_SELECTION) {
			return this.queryProxy().query(FIND_BY_SELECTION_NAME_CODE, KfnmtRptWkMonOut.class)
					.setParameter("itemCode", code)
					.setParameter("itemName", name)
					.setParameter("itemSelectionType", itemSelectionEnum)
					.getList(item -> new OutputItemMonthlyWorkSchedule(item));
		}
		if (itemSelectionEnum == ItemSelectionEnum.FREE_SETTING && employeeId.isPresent()) {
			return this.queryProxy().query(FIND_BY_SELECTION_NAME_CODE_SID, KfnmtRptWkMonOut.class)
					.setParameter("itemCode", code)
					.setParameter("itemName", name)
				  .setParameter("employeeID", employeeId)
				  .setParameter("itemSelectionType", itemSelectionEnum)
				  .getList(item -> new OutputItemMonthlyWorkSchedule(item));
		}
		return Collections.emptyList();
	}
	
}
