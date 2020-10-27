package nts.uk.ctx.at.function.infra.repository.processexecution;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.RepeatMonthDayRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthDaysSelect;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtRepeatMonthDay;
//import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtRepeatMonthDayPK;

@Stateless
public class JpaRepeatMonthDayRepository extends JpaRepository
		implements RepeatMonthDayRepository {
	/**
	 * Query strings
	 */
	private static final String SELECT_ALL = "SELECT rmd FROM KfnmtRepeatMonthDay rmd ";
	private static final String SELECT_All_BY_CID_AND_EXECCD = SELECT_ALL
			+ "WHERE rmd.kfnmtAutoexecTaskDatePK.companyId = :companyId AND rmd.kfnmtAutoexecTaskDatePK.execItemCd = :execItemCd ";
	private static final String SELECT_BY_KEY = SELECT_ALL
			+ "WHERE rmd.kfnmtAutoexecTaskDatePK.companyId = :companyId "
			+ "AND rmd.kfnmtAutoexecTaskDatePK.execItemCd = :execItemCd "
			+ "AND rmd.kfnmtAutoexecTaskDatePK.monthDay = :monthDay ";
	
	@Override
	public void insert(String companyId, String execItemCd, List<RepeatMonthDaysSelect> days) {
		List<KfnmtRepeatMonthDay> repeatMonthDateList =
				days.stream()
						.map(c -> {
							Optional<KfnmtRepeatMonthDay> opt = 
							this.queryProxy().query(SELECT_BY_KEY, KfnmtRepeatMonthDay.class)
								.setParameter("companyId", companyId)
								.setParameter("execItemCd", execItemCd)
								.setParameter("monthDay", c.value).getSingle();
							if (opt.isPresent()) {
								return null;
							}
							KfnmtRepeatMonthDay entity = KfnmtRepeatMonthDay.toEntity(companyId, execItemCd, c);
							return entity;
						}).collect(Collectors.toList());
		this.commandProxy().insertAll(repeatMonthDateList);
		this.getEntityManager().flush();
	}

	@Override
	public void removeAllByCidAndExecCd(String companyId, String execItemCd) {
		List<KfnmtRepeatMonthDay> entityList = this.queryProxy().query(SELECT_All_BY_CID_AND_EXECCD, KfnmtRepeatMonthDay.class)
				.setParameter("companyId", companyId)
				.setParameter("execItemCd", execItemCd).getList();
		/*
		for (KfnmtRepeatMonthDay kfnmtRepeatMonthDay : entityList) {
			this.commandProxy().remove(KfnmtRepeatMonthDay.class,  new KfnmtRepeatMonthDayPK(companyId, execItemCd, kfnmtRepeatMonthDay.kfnmtAutoexecTaskDatePK.monthDay));
		}
		*/
		this.commandProxy().removeAll(entityList);
		this.getEntityManager().flush();
	}
}
