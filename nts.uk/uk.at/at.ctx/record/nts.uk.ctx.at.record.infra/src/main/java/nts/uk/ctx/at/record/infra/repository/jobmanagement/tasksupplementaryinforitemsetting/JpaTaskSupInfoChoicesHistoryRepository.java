package nts.uk.ctx.at.record.infra.repository.jobmanagement.tasksupplementaryinforitemsetting;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.ChoiceCode;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesDetail;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistory;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistoryRepository;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.tasksupplementaryinforitemsetting.KrcmtTaskSupInfoChoicesHist;

/**
 * 
 * @author tutt
 *
 */
@Stateless
public class JpaTaskSupInfoChoicesHistoryRepository extends JpaRepository implements TaskSupInfoChoicesHistoryRepository {

	@Override
	public void insert(TaskSupInfoChoicesHistory history, TaskSupInfoChoicesDetail detail) {
		this.commandProxy().insert(new KrcmtTaskSupInfoChoicesHist(domain));
		
	}

	@Override
	public void insert(TaskSupInfoChoicesDetail detail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(TaskSupInfoChoicesHistory history) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(TaskSupInfoChoicesDetail detail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String historyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String historyId, ChoiceCode code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TaskSupInfoChoicesHistory> getAll(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskSupInfoChoicesDetail> get(String historyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskSupInfoChoicesDetail> get(String companyId, int itemId, GeneralDate refDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
