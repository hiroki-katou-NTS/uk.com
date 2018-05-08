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
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36.Kfnmt36AgreeName;
/**
 * 
 * @author yennth
 *
 */
@Stateless
public class JpaAgreeConditionErrorRepository extends JpaRepository implements IAgreeConditionErrorRepository{
	private final String SELECT_NO_WHERE = "SELECT c FROM Kfnmt36AgreeCondErr c ";
	/**
	 * convert from AgreeConditionError domain to Kfnmt36AgreeCondErr entity
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private static AgreeConditionError toDomain(Kfnmt36AgreeCondErr entity){
		AgreeConditionError domain = AgreeConditionError.createFromJavaType(entity.kfnmt36AgreeCondErrPK.id, entity.useAtr, entity.period, 
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
		entity.kfnmt36AgreeCondErrPK = new Kfnmt36AgreeCondErrPK(domain.getId());
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
	public Optional<AgreeConditionError> findById(String id) {
		return this.queryProxy().find(new Kfnmt36AgreeCondErrPK(id), Kfnmt36AgreeCondErr.class).map(c -> toDomain(c));
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
	public void delete(String id) {
		this.commandProxy().remove(Kfnmt36AgreeCondErr.class, new Kfnmt36AgreeCondErrPK(id));;
	}
	/**
	 * find all AgreeConditionError
	 */
	@Override
	public List<AgreeConditionError> findAll() {
		return this.queryProxy().query(SELECT_NO_WHERE, Kfnmt36AgreeCondErr.class).getList(c -> toDomain(c));
	}

}
