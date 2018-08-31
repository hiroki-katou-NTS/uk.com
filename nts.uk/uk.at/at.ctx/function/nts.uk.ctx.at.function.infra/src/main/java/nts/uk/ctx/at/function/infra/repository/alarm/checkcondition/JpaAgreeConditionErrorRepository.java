package nts.uk.ctx.at.function.infra.repository.alarm.checkcondition;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeConditionError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeConditionErrorRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36.Kfnmt36AgreeCondErr;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36.Kfnmt36AgreeCondErrPK;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author yennth
 *
 */
@Stateless
public class JpaAgreeConditionErrorRepository extends JpaRepository implements IAgreeConditionErrorRepository{
	private static final String SELECT_NO_WHERE = "SELECT c FROM Kfnmt36AgreeCondErr c ";
	private static final String SELECT_CODE = SELECT_NO_WHERE + "WHERE c.kfnmt36AgreeCondErrPK.companyId = :companyId AND c.kfnmt36AgreeCondErrPK.code = :code AND c.kfnmt36AgreeCondErrPK.category = :category ";
	
	private static final String DELETE_NO_WHERE = "DELETE FROM Kfnmt36AgreeCondErr c ";
	private static final String DELETE_CODE = DELETE_NO_WHERE + "WHERE c.kfnmt36AgreeCondErrPK.companyId = :companyId AND c.kfnmt36AgreeCondErrPK.category = :category AND c.kfnmt36AgreeCondErrPK.code = :code ";
	/**
	 * convert from AgreeConditionError domain to Kfnmt36AgreeCondErr entity
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private static AgreeConditionError toDomain(Kfnmt36AgreeCondErr entity){
		AgreeConditionError domain = AgreeConditionError.createFromJavaType(entity.kfnmt36AgreeCondErrPK.id, 
																			entity.kfnmt36AgreeCondErrPK.companyId, 
																			entity.kfnmt36AgreeCondErrPK.category,
																			entity.kfnmt36AgreeCondErrPK.code, 
																			entity.useAtr, entity.period, 
																			entity.errorAlarm, 
																			entity.messageDisp == null ? null : entity.messageDisp);
		return domain;
	} 
	/**
	 * convert from Kfnmt36AgreeCondErr entity to AgreeConditionError domain
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private static Kfnmt36AgreeCondErr toEntity(AgreeConditionError domain){
		val entity = new Kfnmt36AgreeCondErr();
		String companyId = AppContexts.user().companyId();
		entity.kfnmt36AgreeCondErrPK = new Kfnmt36AgreeCondErrPK(domain.getId(), domain.getCode().v(),
																	companyId, domain.getCategory().value);
		entity.errorAlarm = domain.getErrorAlarm().value;
		entity.messageDisp = (domain.getMessageDisp() == null ? null : domain.getMessageDisp().v());
		entity.period = domain.getPeriod().value;
		entity.useAtr = domain.getUseAtr().value;
		return entity;
	} 
	/**
	 * get AgreeConditionError by id
	 * @author yennth
	 */
	@Override
	public Optional<AgreeConditionError> findById(String id, String code, String companyId, int category) {
		return this.queryProxy().find(new Kfnmt36AgreeCondErrPK(id, code, companyId, category), Kfnmt36AgreeCondErr.class).map(c -> toDomain(c));
	}
	/**
	 * update AgreeConditionError
	 * @author yennth
	 */
	@Override
	public void update(AgreeConditionError agreeConditionError) {
		this.commandProxy().update(toEntity(agreeConditionError));;
	}
	/**
	 * insert AgreeConditionError
	 * @author yennth
	 */
	@Override
	public void insert(AgreeConditionError agreeConditionError) {
		if(agreeConditionError.getId() == null){
			agreeConditionError.setId(agreeConditionError.createId());
		}
		this.commandProxy().insert(toEntity(agreeConditionError));
	}
	/**
	 * delete a AgreeConditionError
	 * @author yennth
	 */
	@Override
	public void delete(String code, int category) {
		String companyId = AppContexts.user().companyId();
		this.getEntityManager().createQuery(DELETE_CODE)
								.setParameter("companyId", companyId)
								.setParameter("category", category)
								.setParameter("code", code)
								.executeUpdate();
	}
	/**
	 * find all AgreeConditionError
	 */
	@Override
	public List<AgreeConditionError> findAll(String code, int category) {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().query(SELECT_CODE, Kfnmt36AgreeCondErr.class)
				.setParameter("companyId", companyId)
				.setParameter("code", code)
				.setParameter("category", category)
				.getList(c -> toDomain(c));
	}

}
