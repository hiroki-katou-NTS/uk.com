package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import java.util.Optional;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;

/**
 * 日別実績の運用開始設定
 * @author masaaki_jinno
 *
 */
public class TestOperationStartSetDailyPerformRepository_1 implements OperationStartSetDailyPerformRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository#findByCid(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<OperationStartSetDailyPerform> findByCid(CompanyId companyId) {
//		return this.queryProxy().find(companyId, KshstStartSetDailyPfm.class).map(entity -> this.toDomain(entity));
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return Optional.empty();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository#add(nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform)
	 */
	@Override
	public void add(OperationStartSetDailyPerform domain) {
//		this.commandProxy().insert(this.toEntity(domain));
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository#edit(nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform)
	 */
	@Override
	public void update(OperationStartSetDailyPerform domain) {
//		this.commandProxy().update(this.toEntity(domain));
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
	
//	private OperationStartSetDailyPerform toDomain(KshstStartSetDailyPfm entity) {
//		return new OperationStartSetDailyPerform(new JpaOperationStartSetDailyPerformGetMemento(entity));
//	}
//
//	private KshstStartSetDailyPfm toEntity(OperationStartSetDailyPerform domain) {
//		KshstStartSetDailyPfm entity = new KshstStartSetDailyPfm();
//		domain.saveToMemento(new JpaOperationStartSetDailyPerformSetMemento(entity));
//		return entity;
//	}
	
}
