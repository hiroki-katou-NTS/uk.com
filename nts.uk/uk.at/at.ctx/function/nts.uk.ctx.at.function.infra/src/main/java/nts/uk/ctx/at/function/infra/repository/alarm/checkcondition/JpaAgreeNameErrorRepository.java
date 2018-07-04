package nts.uk.ctx.at.function.infra.repository.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeNameError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeNameErrorRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.ParamFind;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36.Kfnmt36AgreeName;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36.Kfnmt36AgreeNamePK;

@Stateless
public class JpaAgreeNameErrorRepository extends JpaRepository implements IAgreeNameErrorRepository {
	private static final String SELECT_NO_WHERE = "SELECT c FROM Kfnmt36AgreeName c ";
	private static final String SELECT_PERIOD = SELECT_NO_WHERE + "WHERE c.kfnmt36AgreeNamePK.period = :period ";
	private static final String SELECT_ERROR_ALARM = SELECT_NO_WHERE + "WHERE c.kfnmt36AgreeNamePK.errorAlarm = :errorAlarm ";

	/**
	 * convert from entity to domain
	 * 
	 * @param entity
	 * @return
	 * @author yennth
	 */
	public static AgreeNameError toDomain(Kfnmt36AgreeName entity) {
		AgreeNameError domain = AgreeNameError.createFromJavaType(entity.kfnmt36AgreeNamePK.period,
				entity.kfnmt36AgreeNamePK.errorAlarm, entity.name);
		return domain;
	}

	/**
	 * convert from domain to entity
	 * 
	 * @param domain
	 * @return
	 * @author yennth
	 */
	public static Kfnmt36AgreeName toEntity(AgreeNameError domain) {
		val entity = new Kfnmt36AgreeName();
		entity.kfnmt36AgreeNamePK = new Kfnmt36AgreeNamePK(domain.getPeriod().value, domain.getErrorAlarm().value);
		entity.name = domain.getName().v();
		return entity;
	}

	/**
	 * find AgreeNameError by key
	 * 
	 * @author yennth
	 */
	@Override
	public Optional<AgreeNameError> findById(int period, int errorAlarm) {
		return this.queryProxy().find(new Kfnmt36AgreeNamePK(period, errorAlarm), Kfnmt36AgreeName.class)
				.map(c -> toDomain(c));
	}

	/**
	 * find all AgreeNameError
	 * 
	 * @author yennth
	 */
	@Override
	public List<AgreeNameError> findAll() {
		return this.queryProxy().query(SELECT_NO_WHERE, Kfnmt36AgreeName.class).getList(c -> toDomain(c));
	}

	/**
	 * find list AgreeNameError by period
	 * 
	 * @author yennth
	 */
	@Override
	public List<AgreeNameError> findByPer(int period) {
		return this.queryProxy().query(SELECT_PERIOD, Kfnmt36AgreeName.class).setParameter("period", period)
				.getList(x -> toDomain(x));
	}

	/**
	 * find list AgreeNameError by errorAlarm
	 * 
	 * @author yennth
	 */
	@Override
	public List<AgreeNameError> findByErr(int errorAlarm) {
		return this.queryProxy().query(SELECT_ERROR_ALARM, Kfnmt36AgreeName.class)
				.setParameter("errorAlarm", errorAlarm).getList(c -> toDomain(c));
	}

	/**
	 * update AgreeNameError
	 * 
	 * @author yennth
	 */
	@Override
	public void update(AgreeNameError agreeNameError) {
		this.commandProxy().update(toEntity(agreeNameError));
	}

	/**
	 * insert AgreeNameError
	 * 
	 * @author yennth
	 */
	@Override
	public void insert(AgreeNameError agreeNameError) {
		this.commandProxy().insert(toEntity(agreeNameError));
	}

	/**
	 * delete AgreeNameError
	 * 
	 * @author yennth
	 */
	@Override
	public void delete(int period, int errorAlarm) {
		this.commandProxy().remove(Kfnmt36AgreeName.class, new Kfnmt36AgreeNamePK(period, errorAlarm));
	}

	/**
	 * find name by list period and error alarm
	 * @author yennth
	 */
	@Override
	public List<String> findName(List<ParamFind> param) {
		List<String> list = new ArrayList<>();
		for (ParamFind obj : param) {
			Optional<AgreeNameError> item = this.findById(obj.getPeriod(), obj.getErrorAlarm());
			if (item.isPresent()) {
				list.add(item.get().getName().v());
			}
		}
		return list;
	}
}
