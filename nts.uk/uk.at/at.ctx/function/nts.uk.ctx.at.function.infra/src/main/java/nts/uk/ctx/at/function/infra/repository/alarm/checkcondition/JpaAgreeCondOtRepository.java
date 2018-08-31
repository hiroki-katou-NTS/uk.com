package nts.uk.ctx.at.function.infra.repository.alarm.checkcondition;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeCondOt;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeCondOtRepository;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36.Kfnmt36AgreeCondOt;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36.Kfnmt36AgreeCondOtPK;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class JpaAgreeCondOtRepository extends JpaRepository implements IAgreeCondOtRepository{
	
	private static final String SELECT_NO_WHERE = "SELECT c FROM Kfnmt36AgreeCondOt c ";
	private static final String SELECT_BY_ID = SELECT_NO_WHERE + "WHERE c.kfnmt36AgreeCondOtPK.id = :id ";
	private static final String SELECT_BY_NO = SELECT_NO_WHERE + "WHERE c.kfnmt36AgreeCondOtPK.no = :no ";
	private static final String SELECT_CODE = SELECT_NO_WHERE + "WHERE c.kfnmt36AgreeCondOtPK.companyId = :companyId AND c.kfnmt36AgreeCondOtPK.code = :code AND c.kfnmt36AgreeCondOtPK.category = :category ";
	
	private static final String DELETE_NO_WHERE = "DELETE FROM Kfnmt36AgreeCondOt c ";
	private static final String DELETE_CODE = DELETE_NO_WHERE + "WHERE c.kfnmt36AgreeCondOtPK.companyId = :companyId AND c.kfnmt36AgreeCondOtPK.category = :category AND c.kfnmt36AgreeCondOtPK.code = :code ";
	private static final String DELEETE_ID = DELETE_CODE + " AND c.kfnmt36AgreeCondOtPK.id = :id AND c.kfnmt36AgreeCondOtPK.no = :no ";
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private static AgreeCondOt toDomain(Kfnmt36AgreeCondOt entity){
		AgreeCondOt domain = AgreeCondOt.createFromJavaType(entity.kfnmt36AgreeCondOtPK.id, 
															entity.kfnmt36AgreeCondOtPK.companyId, entity.kfnmt36AgreeCondOtPK.category,
															entity.kfnmt36AgreeCondOtPK.code, 
															entity.kfnmt36AgreeCondOtPK.no, entity.ot36, entity.excessNum, 
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
		String companyId = AppContexts.user().companyId();
		entity.kfnmt36AgreeCondOtPK = new Kfnmt36AgreeCondOtPK(domain.getId(), domain.getNo(), domain.getCode().v(), 
																companyId, domain.getCategory().value);
		entity.excessNum = domain.getExcessNum().v();
		entity.messageDisp = (domain.getMessageDisp() == null ? null : domain.getMessageDisp().v());
		entity.ot36 = domain.getOt36().v();
		return entity;
	}
	/**
	 * find a AgreeCondOt by key
	 * @author yennth
	 */
	@Override
	public Optional<AgreeCondOt> findById(String id, int no, String code, String companyId, int category) {
		return this.queryProxy().find(new Kfnmt36AgreeCondOtPK(id, no, code, companyId, category), Kfnmt36AgreeCondOt.class).map(x -> toDomain(x));
	}
	/**
	 * find all AgreeCondOt
	 * @author yennth
	 */
	@Override
	public List<AgreeCondOt> findAll(String code, int category) {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().query(SELECT_CODE, Kfnmt36AgreeCondOt.class)
				.setParameter("companyId", companyId)
				.setParameter("code", code)
				.setParameter("category", category)
				.getList(x -> toDomain(x));
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
		if(agreeCondOt.getNo()>=100) {
			throw new BusinessException("Msg_1311");	
		}
		this.commandProxy().update(toEntity(agreeCondOt));
	}
	/**
	 * insert AgreeCondOt
	 * @author yennth
	 */
	@Override
	public void insert(AgreeCondOt agreeCondOt) {
		if(agreeCondOt.getId() == null){
			agreeCondOt.setId(agreeCondOt.createId());
		}
		if(agreeCondOt.getNo()>=100) {
			throw new BusinessException("Msg_1311");	
		}
		this.commandProxy().insert(toEntity(agreeCondOt));
	}
	/**
	 * delete list AgreeCondOt
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
	 * delete AgreeCondOt
	 */
	@Override
	public void deleteId(String code, int category, String id, int no) {
		String companyId = AppContexts.user().companyId();
		this.getEntityManager().createQuery(DELEETE_ID)
								.setParameter("companyId", companyId)
								.setParameter("category", category)
								.setParameter("code", code)
								.setParameter("id", id)
								.setParameter("no", no)
								.executeUpdate();
	}

}
