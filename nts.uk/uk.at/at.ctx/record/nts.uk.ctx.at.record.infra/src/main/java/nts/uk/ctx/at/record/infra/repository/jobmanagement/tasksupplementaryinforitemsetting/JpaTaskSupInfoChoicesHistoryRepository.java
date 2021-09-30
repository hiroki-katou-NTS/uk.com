package nts.uk.ctx.at.record.infra.repository.jobmanagement.tasksupplementaryinforitemsetting;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesDetail;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistory;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistoryRepository;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.tasksupplementaryinforitemsetting.KrcmtTaskSupInfoChoicesDetail;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.tasksupplementaryinforitemsetting.KrcmtTaskSupInfoChoicesHist;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.ChoiceCode;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 
 * @author tutt
 *
 */
@Stateless
public class JpaTaskSupInfoChoicesHistoryRepository extends JpaRepository
		implements TaskSupInfoChoicesHistoryRepository {

	@Override
	public void insert(TaskSupInfoChoicesHistory history, TaskSupInfoChoicesDetail detail) {
		this.commandProxy().insert(new KrcmtTaskSupInfoChoicesHist(history));
		this.commandProxy().insert(new KrcmtTaskSupInfoChoicesDetail(detail));
	}

	@Override
	public void insert(TaskSupInfoChoicesDetail detail) {
		this.commandProxy().insert(new KrcmtTaskSupInfoChoicesDetail(detail));
	}

	@Override
	public void update(TaskSupInfoChoicesHistory history) {
		this.commandProxy().update(new KrcmtTaskSupInfoChoicesHist(history));

	}

	@Override
	public void update(TaskSupInfoChoicesDetail detail) {
		this.commandProxy().update(new KrcmtTaskSupInfoChoicesDetail(detail));
	}

	@Override
	public void delete(String hisId) {
		List<KrcmtTaskSupInfoChoicesHist> histEntity = this.queryProxy()
				.query("SELECT h FROM KrcmtTaskSupInfoChoicesHist h WHERE h.pk.histId = :hisId",
						KrcmtTaskSupInfoChoicesHist.class)
				.setParameter("hisId", hisId).getList();

		List<KrcmtTaskSupInfoChoicesDetail> detailEntity = this.queryProxy()
				.query("SELECT d FROM KrcmtTaskSupInfoChoicesDetail d WHERE d.pk.histId = :hisId",
						KrcmtTaskSupInfoChoicesDetail.class)
				.setParameter("hisId", hisId).getList();

		if (!histEntity.isEmpty()) {
			for (KrcmtTaskSupInfoChoicesHist h : histEntity) {
				this.commandProxy().remove(h);
			}
		}

		if (!detailEntity.isEmpty()) {
			for (KrcmtTaskSupInfoChoicesDetail d : detailEntity) {
				this.commandProxy().remove(d);
			}
		}
	}

	@Override
	public void delete(String hisId, ChoiceCode choiceCode) {
		String code = choiceCode.v();
		
		List<KrcmtTaskSupInfoChoicesDetail> detailEntity = this.queryProxy()
				.query("SELECT d FROM KrcmtTaskSupInfoChoicesDetail d WHERE d.pk.histId = :hisId AND d.pk.code = :code",
						KrcmtTaskSupInfoChoicesDetail.class)
				.setParameter("hisId", hisId)
				.setParameter("code", code)
				.getList();
		
		if (!detailEntity.isEmpty()) {
			for (KrcmtTaskSupInfoChoicesDetail d : detailEntity) {
				this.commandProxy().remove(d);
			}
		}
	}

	@Override
	public List<TaskSupInfoChoicesHistory> getAll(String companyId) {
		List<TaskSupInfoChoicesHistory> result = new ArrayList<>();

		List<KrcmtTaskSupInfoChoicesHist> histEntity = this.queryProxy()
				.query("SELECT h FROM KrcmtTaskSupInfoChoicesHist h WHERE h.companyId = :companyId",
						KrcmtTaskSupInfoChoicesHist.class)
				.setParameter("companyId", companyId).getList();

		List<DateHistoryItem> items = KrcmtTaskSupInfoChoicesHist.toDomain(histEntity);

		// TODO: Chưa biết xử lý tiếp như thế nào.

		return result;
	}

	@Override
	public List<TaskSupInfoChoicesDetail> get(String hisId) {
		return this.queryProxy().query(
				"SELECT d FROM KrcmtTaskSupInfoChoicesDetail d WHERE d.pk.histId = :hisId ORDER BY d.pk.code ASC",
				KrcmtTaskSupInfoChoicesDetail.class).setParameter("hisId", hisId).getList(a -> a.toDomain());
	}

	@Override
	public List<TaskSupInfoChoicesDetail> get(String companyId, int itemId, GeneralDate refDate) {
		return this.queryProxy().query(
				"SELECT d FROM KrcmtTaskSupInfoChoicesDetail d JOIN KrcmtTaskSupInfoChoicesHist h WHERE d.pk.histId = h.pk.hisId AND d.companyId = :companyId "
						+ "AND d.pk.manHrItemId = :itemId AND h.startDate <= :refDate AND h.endDate >= :refDate ORDER BY d.pk.code ASC",
				KrcmtTaskSupInfoChoicesDetail.class).setParameter("companyId", companyId).setParameter("itemId", itemId)
				.setParameter("refDate", refDate).getList(a -> a.toDomain());
	}

}
