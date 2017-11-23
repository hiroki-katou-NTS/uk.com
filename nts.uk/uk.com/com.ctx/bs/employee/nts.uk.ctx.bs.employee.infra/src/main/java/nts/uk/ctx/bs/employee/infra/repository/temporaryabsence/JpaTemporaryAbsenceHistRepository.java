package nts.uk.ctx.bs.employee.infra.repository.temporaryabsence;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceHistRepository;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.BsymtTempAbsHistory;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
public class JpaTemporaryAbsenceHistRepository extends JpaRepository implements TemporaryAbsenceHistRepository{
	
	/**
	 * Convert from domain to entity
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private BsymtTempAbsHistory toEntity(String employeeID, DateHistoryItem item){
		return new BsymtTempAbsHistory(item.identifier(),employeeID,item.start(),item.end());
	}
	
	/**
	 * Update entity from domain
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private void updateEntity(String employeeID, DateHistoryItem item,BsymtTempAbsHistory entity){	
		entity.sid = employeeID;
		entity.startDate = item.start();
		entity.endDate = item.end();
	}
	
	@Override
	public void addTemporaryAbsenceHist(TempAbsenceHistory domain) {
		for (DateHistoryItem item : domain.getDateHistoryItems()){
			this.commandProxy().insert(toEntity(domain.getEmployeeId(), item));
		}
	}

	@Override
	public void updateTemporaryAbsenceHist(TempAbsenceHistory domain) {
		Optional<BsymtTempAbsHistory> histItem = null;
		for (DateHistoryItem item : domain.getDateHistoryItems()){
			histItem = this.queryProxy().find(item.identifier(), BsymtTempAbsHistory.class);
			if (!histItem.isPresent()){
				continue;
			}
			updateEntity(domain.getEmployeeId(), item, histItem.get());
			this.commandProxy().update(histItem.get());
		}
	}

	@Override
	public void deleteTemporaryAbsenceHist(String histId) {
		Optional<BsymtTempAbsHistory> histItem = null;
		histItem = this.queryProxy().find(histId, BsymtTempAbsHistory.class);
		if (!histItem.isPresent()){
			throw new RuntimeException("invalid BsymtTempAbsHistory");
		}
		this.commandProxy().remove(BsymtTempAbsHistory.class, histId);
		
	}

}
