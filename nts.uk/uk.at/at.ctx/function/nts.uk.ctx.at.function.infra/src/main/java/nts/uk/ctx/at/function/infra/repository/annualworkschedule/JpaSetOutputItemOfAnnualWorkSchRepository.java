package nts.uk.ctx.at.function.infra.repository.annualworkschedule;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.SettingOutputItemOfAnnualWorkSchedule;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutputItemOfAnnualWorkSchRepository;
import nts.uk.ctx.at.function.infra.entity.annualworkschedule.KfnmtRptWkYearItem;
import nts.uk.ctx.at.function.infra.entity.annualworkschedule.KfnmtRptWkYearSet;

@Stateless
public class JpaSetOutputItemOfAnnualWorkSchRepository extends JpaRepository implements SetOutputItemOfAnnualWorkSchRepository {

	/** The Constant FIND_ALL. */
	private static final String FIND_ALL = "SELECT st FROM KfnmtRptWkYearSet st"
										 + " WHERE st.cid = :cid"
										 + "  AND st.sid = :sid"
										 + "  AND st.settingType = :settingType"
										 + "  AND st.printForm = :printForm";

	
	/** The Constant FIND_BY_CODE. */
	private static final String FIND_BY_CODE = "SELECT st FROM KfnmtRptWkYearSet st"
											 + " WHERE st.cid = :cid"
											 + "  AND st.sid = :sid"
											 + "  AND st.settingType = :settingType"
											 + "  AND st.cd = :cd";

	/** The Constant FIND_BY_SETTING_ID. */
	private static final String FIND_BY_LAYOUT_ID = "SELECT st FROM KfnmtRptWkYearSet st WHERE st.layoutId = :layoutId";
	
	/** The Constant FIND_SET_ITEM_BY_SETTING_ID. */
	private static final String FIND_SET_ITEM_BY_SETTING_ID = "SELECT st FROM KfnmtRptWkYearItem st WHERE st.kfnmtRptWkYearItemPK.layoutId = :layoutId";

	/**
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutputItemOfAnnualWorkSchRepository
	 * #findBySettingId(String)
	 */
	@Override
	public Optional<SettingOutputItemOfAnnualWorkSchedule> findByLayoutId(String layoutId) {
		// get entity by setting id
		Optional<KfnmtRptWkYearSet> entity = this.getByLayoutId(layoutId);
		
		// convert to domain
		return entity.isPresent()
			 ? Optional.of(SettingOutputItemOfAnnualWorkSchedule.createFromMemento(entity.get()))
			 : Optional.empty();
	}

	/**
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutputItemOfAnnualWorkSchRepository
	 * #findAllSeting(String, Optional)
	 */
	@Override
	public List<SettingOutputItemOfAnnualWorkSchedule> findAllSeting(String companyId
																   , Optional<String> employeeId
																   , int printForm
																   , int settingType) {
		return this.queryProxy()
				.query(FIND_ALL, KfnmtRptWkYearSet.class)
				.setParameter("cid", companyId)
				.setParameter("sid", employeeId.orElse(null))
				.setParameter("settingType", settingType)
				.setParameter("printForm", printForm)
				.getList(t -> SettingOutputItemOfAnnualWorkSchedule.createFromMemento(t));
	}

	/**
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutputItemOfAnnualWorkSchRepository
	 * #add(SettingOutputItemOfAnnualWorkSchedule)
	 */
	@Override
	public void add(SettingOutputItemOfAnnualWorkSchedule domain) {
		KfnmtRptWkYearSet entity = new KfnmtRptWkYearSet();
		domain.setMemento(entity);
		// Insert
		this.commandProxy().insert(entity);
	}

	/**
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutputItemOfAnnualWorkSchRepository
	 * #update(SettingOutputItemOfAnnualWorkSchedule)
	 */
	@Override
	public void update(SettingOutputItemOfAnnualWorkSchedule domain) {
		
		// get all attendance display item by layoutId
		List<KfnmtRptWkYearItem> lstRptWkYearItems = this.queryProxy()
				.query(FIND_SET_ITEM_BY_SETTING_ID, KfnmtRptWkYearItem.class)
				.setParameter("layoutId", domain.getLayoutId())
				.getList();
		this.commandProxy().removeAll(lstRptWkYearItems);
		this.getEntityManager().flush();

		// get entity by setting id
		Optional<KfnmtRptWkYearSet> oEntity = this.getByLayoutId(domain.getLayoutId());
		
		if (oEntity.isPresent()) {
			KfnmtRptWkYearSet entity = oEntity.get();
			domain.setMemento(entity);
			
			// update
			this.commandProxy().update(entity);
		}
	}

	/**
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutputItemOfAnnualWorkSchRepository
	 * #remove(String)
	 */
	@Override
	public void remove(String layoutId) {
		this.commandProxy().remove(KfnmtRptWkYearSet.class, layoutId); 
	}

	private Optional<KfnmtRptWkYearSet> getByLayoutId(String layoutId) {
		return this.queryProxy()
				.query(FIND_BY_LAYOUT_ID, KfnmtRptWkYearSet.class)
				.setParameter("layoutId", layoutId)
				.getSingle();
	}

	@Override
	public Optional<SettingOutputItemOfAnnualWorkSchedule> findByCode(String code
																	, Optional<String> employeeId
																	, String companyId
																	, int settingType) {
		return this.queryProxy()
				.query(FIND_BY_CODE, KfnmtRptWkYearSet.class)
				.setParameter("cid", companyId)
				.setParameter("sid", employeeId.orElse(null))
				.setParameter("settingType", settingType)
				.setParameter("cd", code)
				.getSingle(t -> SettingOutputItemOfAnnualWorkSchedule.createFromMemento(t));
	}
}
