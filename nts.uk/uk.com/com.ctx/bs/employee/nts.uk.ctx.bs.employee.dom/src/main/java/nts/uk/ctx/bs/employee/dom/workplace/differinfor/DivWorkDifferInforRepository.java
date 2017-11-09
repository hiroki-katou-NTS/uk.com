package nts.uk.ctx.bs.employee.dom.workplace.differinfor;

import java.util.Optional;

public interface DivWorkDifferInforRepository {
	/**
	 * get SysUsageSet
	 * @param companyId
	 * @return
	 * author: Hoang Yen
	 */
	Optional<DivWorkDifferInfor> findDivWork(String companyId);
	/**
	 * update DivWork
	 * @param SysUsageSet
	 * @return
	 * author: Hoang Yen
	 */
	void updateDivWork(DivWorkDifferInfor divWorkDifferInfor);
	/**
	 * insert DivWork
	 * @param SysUsageSet
	 * @return
	 * author: Hoang Yen
	 */
	void insertDivWork(DivWorkDifferInfor divWorkDifferInfor);
}
