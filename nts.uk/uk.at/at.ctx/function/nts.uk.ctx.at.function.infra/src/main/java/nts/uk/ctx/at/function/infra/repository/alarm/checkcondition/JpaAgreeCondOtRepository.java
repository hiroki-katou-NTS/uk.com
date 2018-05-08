package nts.uk.ctx.at.function.infra.repository.alarm.checkcondition;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeCondOt;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeCondOtRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36.Kfnmt36AgreeCondOt;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36.Kfnmt36AgreeCondOtPK;
@Stateless
public class JpaAgreeCondOtRepository extends JpaRepository implements IAgreeCondOtRepository{
	
	private final String SELECT_NO_WHERE = "SELECT c FROM Kfnmt36AgreeCondOt c ";
	private final String SELECT_BY_ID = SELECT_NO_WHERE + "WHERE c.kfnmt36AgreeCondOtPK.id = :id ";
	private final String SELECT_BY_NO = SELECT_NO_WHERE + "WHERE c.kfnmt36AgreeCondOtPK.no = :no ";
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private static AgreeCondOt toDomain(Kfnmt36AgreeCondOt entity){
		AgreeCondOt domain = AgreeCondOt.createFromJavaType(entity.kfnmt36AgreeCondOtPK.no, entity.ot36, entity.excessNum, 
															entity.messageDisp == null ? null : entity.messageDisp);
		return domain;
	}
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private static Kfnmt36AgreeCondOt toEntity(AgreeCondOt domain){
		val entity = new Kfnmt36AgreeCondOt();
		entity.kfnmt36AgreeCondOtPK = new Kfnmt36AgreeCondOtPK(domain.getId(), domain.getNo());
		entity.excessNum = domain.getExcessNum();
		entity.messageDisp = (domain.getMessageDisp() == null ? null : domain.getMessageDisp().v());
		entity.ot36 = domain.getOt36();
		return entity;
	}
	/**
	 * find a AgreeCondOt by key
	 * @author yennth
	 */
	@Override
	public Optional<AgreeCondOt> findById(String id, int no) {
		return this.queryProxy().find(new Kfnmt36AgreeCondOtPK(id, no), Kfnmt36AgreeCondOt.class).map(x -> toDomain(x));
	}
	/**
	 * find all AgreeCondOt
	 * @author yennth
	 */
	@Override
	public List<AgreeCondOt> findAll() {
		return this.queryProxy().query(SELECT_NO_WHERE, Kfnmt36AgreeCondOt.class).getList(x -> toDomain(x));
	}
	/**
	 * find AgreeCondOt by id
	 * @author yennth
	 */
	@Override
	public List<AgreeCondOt> findByPer(String id) {
		return this.queryProxy().query(SELECT_BY_ID, Kfnmt36AgreeCondOt.class)
								.setParameter("id", id).getList(x -> toDomain(x));
	}
	/**
	 * find AgreeCondOt by no
	 * @author yennth
	 */
	@Override
	public List<AgreeCondOt> findByErr(int no) {
		return this.queryProxy().query(SELECT_BY_NO, Kfnmt36AgreeCondOt.class)
								.setParameter("no", no).getList(x -> toDomain(x));
	}
	/**
	 * update AgreeCondOt 
	 * @author yennth
	 */
	@Override
	public void update(AgreeCondOt agreeCondOt) {
		this.commandProxy().update(toEntity(agreeCondOt));
	}
	/**
	 * insert AgreeCondOt
	 * @author yennth
	 */
	@Override
	public void insert(AgreeCondOt agreeCondOt) {
		this.commandProxy().insert(toEntity(agreeCondOt));
	}
	/**
	 * delete AgreeCondOt
	 * @author yennth
	 */
	@Override
	public void delete(String id, int no) {
		this.commandProxy().remove(Kfnmt36AgreeCondOt.class, new Kfnmt36AgreeCondOtPK(id, no));
	}

}
