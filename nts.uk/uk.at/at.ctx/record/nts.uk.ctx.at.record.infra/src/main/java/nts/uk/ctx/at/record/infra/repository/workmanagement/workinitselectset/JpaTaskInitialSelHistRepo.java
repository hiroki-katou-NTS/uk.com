package nts.uk.ctx.at.record.infra.repository.workmanagement.workinitselectset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workmanagement.workinitselectset.TaskCode;
import nts.uk.ctx.at.record.dom.workmanagement.workinitselectset.TaskInitialSel;
import nts.uk.ctx.at.record.dom.workmanagement.workinitselectset.TaskInitialSelHist;
import nts.uk.ctx.at.record.dom.workmanagement.workinitselectset.TaskInitialSelHistRepo;
import nts.uk.ctx.at.record.dom.workmanagement.workinitselectset.TaskItem;
import nts.uk.ctx.at.record.infra.entity.workmanagement.workinitselectset.KrcmtTaskInitialSelHist;
import nts.uk.ctx.at.record.infra.entity.workmanagement.workinitselectset.KrcmtTaskInitialSelHistPk;

/**
 * 
 * @author HieuLt
 *
 */
@Stateless
public class JpaTaskInitialSelHistRepo extends JpaRepository implements TaskInitialSelHistRepo {

	private static final String DELETE_BY_SID = " DELETE FROM KrcmtTaskInitialSelHist c WHERE c.pk.sID = :empId";  
														
	private static final String SELECT_BY_SID = " SELECT c FROM KrcmtTaskInitialSelHist c WHERE c.pk.sID = :empId ";
	
	private static final String SELECT_BY_BASE_DATE = SELECT_BY_SID  
										           + " AND c.pk.startDate <= :baseDate "
										           + " AND c.endDate >= :baseDate";
	
	private static final String SELECT_BY_CID = " SELECT c FROM KrcmtTaskInitialSelHist c WHERE c.companyId = :companyId "; 

	@Override
	public void insert(TaskInitialSelHist taskInitialSelHist) {
		this.commandProxy().insertAll(KrcmtTaskInitialSelHist.toEntity(taskInitialSelHist));
		
	}

	@Override
	public void update(TaskInitialSelHist taskInitialSelHist) {
		List<KrcmtTaskInitialSelHist> listKrcmtTaskInitialSelHists = new ArrayList<KrcmtTaskInitialSelHist>();
		
		taskInitialSelHist.getLstHistory().stream().forEach(hist -> {
			KrcmtTaskInitialSelHistPk pk = new KrcmtTaskInitialSelHistPk(taskInitialSelHist.getEmpId(), hist.getDatePeriod().start());
			Optional<KrcmtTaskInitialSelHist> entity = this.queryProxy().find(pk, KrcmtTaskInitialSelHist.class);
			if (entity.isPresent()) {
				entity.get().endDate = hist.end();
				entity.get().taskCd1 = hist.getTaskItem().getOtpWorkCode1().isPresent() ? hist.getTaskItem().getOtpWorkCode1().get().v() : "";
				entity.get().taskCd2 = hist.getTaskItem().getOtpWorkCode1().isPresent() ? hist.getTaskItem().getOtpWorkCode2().get().v() : "";
				entity.get().taskCd3 = hist.getTaskItem().getOtpWorkCode1().isPresent() ? hist.getTaskItem().getOtpWorkCode3().get().v() : "";
				entity.get().taskCd4 = hist.getTaskItem().getOtpWorkCode1().isPresent() ? hist.getTaskItem().getOtpWorkCode4().get().v() : "";
				entity.get().taskCd5 = hist.getTaskItem().getOtpWorkCode1().isPresent() ? hist.getTaskItem().getOtpWorkCode5().get().v() : "";
				listKrcmtTaskInitialSelHists.add(entity.get());
			}
		});

		this.commandProxy().updateAll(listKrcmtTaskInitialSelHists);

	}

	@Override
	public void delete(String empId) {
		this.getEntityManager().createQuery(DELETE_BY_SID , KrcmtTaskInitialSelHist.class)
				.setParameter("empId", empId)
				.executeUpdate();
		
	}

	@Override
	public Optional<TaskInitialSelHist> getById(String empId) {
		List<TaskInitialSel> data = this.queryProxy().query(SELECT_BY_SID ,  KrcmtTaskInitialSelHist.class )
				.setParameter("empId", empId)
				.getList().stream().map(c -> new TaskInitialSel(c.pk.sID,
						new DatePeriod(c.pk.startDate, c.endDate) ,
						 new TaskItem(Optional.ofNullable(new TaskCode(c.taskCd1)),
								 Optional.ofNullable(new TaskCode(c.taskCd2)),
								 Optional.ofNullable(new TaskCode(c.taskCd3)),
								 Optional.ofNullable(new TaskCode(c.taskCd4)),
								 Optional.ofNullable(new TaskCode(c.taskCd5)))))
				.collect(Collectors.toList());
					
		return data.isEmpty()? Optional.empty():Optional.of(new TaskInitialSelHist(data.get(0).getEmpID(), data));
	}

	@Override
	public Optional<TaskItem> getByBaseDate(String empId, GeneralDate baseDate) {
		List<TaskInitialSel> data = this.queryProxy().query(SELECT_BY_BASE_DATE ,  KrcmtTaskInitialSelHist.class )
				.setParameter("empId", empId)
				.setParameter("baseDate", baseDate)
				.getList().stream().map(c -> new TaskInitialSel(c.pk.sID,
						new DatePeriod(c.pk.startDate, c.endDate) ,
						 new TaskItem(Optional.ofNullable(new TaskCode(c.taskCd1)),
								 Optional.ofNullable(new TaskCode(c.taskCd2)),
								 Optional.ofNullable(new TaskCode(c.taskCd3)),
								 Optional.ofNullable(new TaskCode(c.taskCd4)),
								 Optional.ofNullable(new TaskCode(c.taskCd5)))))
				.collect(Collectors.toList());
		List<TaskItem>  lst =  data.stream().map(c -> c.getTaskItem()).collect(Collectors.toList());
		if(lst.isEmpty()){
			return Optional.empty();
		}
		return Optional.of(lst.get(0));
	}

	@Override
	public List<TaskInitialSelHist> getByCid(String companyId) {
		List<TaskInitialSel> data = this.queryProxy().query(SELECT_BY_CID ,  KrcmtTaskInitialSelHist.class )
				.setParameter("companyId", companyId)
				.getList().stream().map(c -> new TaskInitialSel(c.pk.sID,
						new DatePeriod(c.pk.startDate, c.endDate) ,
						 new TaskItem(Optional.ofNullable(new TaskCode(c.taskCd1)),
								 Optional.ofNullable(new TaskCode(c.taskCd2)),
								 Optional.ofNullable(new TaskCode(c.taskCd3)),
								 Optional.ofNullable(new TaskCode(c.taskCd4)),
								 Optional.ofNullable(new TaskCode(c.taskCd5)))))
				.collect(Collectors.toList());
		List<TaskInitialSelHist> lst = data.stream().map( c -> new TaskInitialSelHist(c.getEmpID(), data) ).collect(Collectors.toList());
		return lst;
	}
}
