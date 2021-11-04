package nts.uk.ctx.at.record.infra.repository.jobmanagement.favoritetask.onedayfavoriteset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.daily.ouen.SupportFrameNo;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteTaskName;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.TaskContent;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.OneDayFavoriteSet;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.OneDayFavoriteTaskSetRepository;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.TaskBlockDetailContent;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.TaskContentForEachSupportFrame;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.onedayfavoriteset.KrcdtTaskFavDaySet;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.onedayfavoriteset.KrcdtTaskFavDaySetItem;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.onedayfavoriteset.KrcdtTaskFavDaySetTs;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.onedayfavoriteset.KrcdtTaskFavDaySetItemPk;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author tutt
 *
 */
@Stateless
public class JpaOneDayFavoriteTaskSetRepository extends JpaRepository implements OneDayFavoriteTaskSetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT s FROM KrcdtTaskFavDaySet s";

	private static final String SELECT_BY_SID = SELECT_ALL_QUERY_STRING + " WHERE s.sId = :sId";
	private static final String SELECT_BY_FAID = SELECT_ALL_QUERY_STRING + " WHERE s.favId = :favId";
	private static final String SELECT_BY_FAVID_AND_SID = SELECT_BY_FAID + " AND s.sId = :sId";

	@Override
	public void insert(OneDayFavoriteSet set) {
		this.commandProxy().insert(new KrcdtTaskFavDaySet(set));

		Map<Integer, List<TaskContent>> contentMap = new HashMap<>();

		for (int i = 1; i < 21; i++) {

			List<TaskContent> contents = new ArrayList<>();

			for (TaskBlockDetailContent content : set.getTaskBlockDetailContents()) {

				for (TaskContentForEachSupportFrame frame : content.getTaskContents()) {

					if (frame.getFrameNo().v() == i) {
						contents.add(frame.getTaskContent());
					}
				}
			}
			contentMap.put(i, contents);
		}

		for (Map.Entry<Integer, List<TaskContent>> entry : contentMap.entrySet()) {

			List<TaskContent> taskContents = entry.getValue();
			
			if (!taskContents.isEmpty()) {
				String taskCd1 = taskContents.stream().filter(m -> m.getItemId() == 4).findAny().map(m -> m.getTaskCode().v()).orElse(null);
				String taskCd2 = taskContents.stream().filter(m -> m.getItemId() == 5).findAny().map(m -> m.getTaskCode().v()).orElse(null);
				String taskCd3 = taskContents.stream().filter(m -> m.getItemId() == 6).findAny().map(m -> m.getTaskCode().v()).orElse(null);
				String taskCd4 = taskContents.stream().filter(m -> m.getItemId() == 7).findAny().map(m -> m.getTaskCode().v()).orElse(null);
				String taskCd5 = taskContents.stream().filter(m -> m.getItemId() == 8).findAny().map(m -> m.getTaskCode().v()).orElse(null);

				for (TaskBlockDetailContent content : set.getTaskBlockDetailContents()) {
					this.commandProxy()
							.insert(new KrcdtTaskFavDaySetItem(
									new KrcdtTaskFavDaySetItemPk(set.getFavId(), entry.getKey(), content.getStartTime().v()), taskCd1,
									taskCd2, taskCd3, taskCd4, taskCd5));
				}
			}
		
		}

		for (TaskBlockDetailContent content : set.getTaskBlockDetailContents()) {
			this.commandProxy().insert(
					new KrcdtTaskFavDaySetTs(set.getFavId(), content.getStartTime().v(), content.getEndTime().v()));
		}

	}

	@Override
	public void update(OneDayFavoriteSet set) {
		this.queryProxy().find(set.getFavId(), KrcdtTaskFavDaySet.class).ifPresent(entity -> {
			entity.favName = set.getTaskName().v();
			this.commandProxy().update(entity);
		});
	}

	@Override
	public void delete(String sId, String favId) {
		Optional<KrcdtTaskFavDaySet> setEntity = this.queryProxy()
				.query(SELECT_BY_FAVID_AND_SID, KrcdtTaskFavDaySet.class).setParameter("favId", favId)
				.setParameter("sId", sId).getSingle();

		if (setEntity.isPresent()) {
			this.commandProxy().remove(setEntity.get());
		}
	}

	@Override
	public List<OneDayFavoriteSet> getAll(String sId) {
		List<OneDayFavoriteSet> result = new ArrayList<>();
		List<KrcdtTaskFavDaySet> entities = this.queryProxy().query(SELECT_BY_SID, KrcdtTaskFavDaySet.class)
				.setParameter("sId", sId).getList();

		for (KrcdtTaskFavDaySet s : entities) {
			List<TaskBlockDetailContent> taskBlockDetails = new ArrayList<>();

			toDomain(taskBlockDetails, s);

			OneDayFavoriteSet set = new OneDayFavoriteSet(s.sId, s.favId, new FavoriteTaskName(s.favName),
					taskBlockDetails);
			result.add(set);
		}
		return result;
	}

	@Override
	public Optional<OneDayFavoriteSet> getByFavoriteId(String favId) {

		Optional<KrcdtTaskFavDaySet> optEntity = this.queryProxy().query(SELECT_BY_FAID, KrcdtTaskFavDaySet.class)
				.setParameter("favId", favId).getSingle();

		List<TaskBlockDetailContent> taskBlockDetails = new ArrayList<>();

		if (!optEntity.isPresent()) {
			return Optional.empty();

		}

		KrcdtTaskFavDaySet entity = optEntity.get();

		toDomain(taskBlockDetails, entity);

		return Optional.of(new OneDayFavoriteSet(entity.sId, entity.favId, new FavoriteTaskName(entity.favName),
				taskBlockDetails));
	}

	private void toDomain(List<TaskBlockDetailContent> taskBlockDetails, KrcdtTaskFavDaySet entity) {
		for (KrcdtTaskFavDaySetTs ts : entity.krcdttaskFavDaySetTsList) {

			List<TaskContentForEachSupportFrame> taskContents = new ArrayList<>();

			for (KrcdtTaskFavDaySetItem i : ts.krcdtTaskFavDaySetItemList) {

				TaskContentForEachSupportFrame frame1 = new TaskContentForEachSupportFrame(
						new SupportFrameNo(i.pk.supTaskNo), new TaskContent(4, new WorkCode(i.taskCd1)));

				TaskContentForEachSupportFrame frame2 = new TaskContentForEachSupportFrame(
						new SupportFrameNo(i.pk.supTaskNo), new TaskContent(5, new WorkCode(i.taskCd2)));

				TaskContentForEachSupportFrame frame3 = new TaskContentForEachSupportFrame(
						new SupportFrameNo(i.pk.supTaskNo), new TaskContent(6, new WorkCode(i.taskCd3)));

				TaskContentForEachSupportFrame frame4 = new TaskContentForEachSupportFrame(
						new SupportFrameNo(i.pk.supTaskNo), new TaskContent(7, new WorkCode(i.taskCd4)));

				TaskContentForEachSupportFrame frame5 = new TaskContentForEachSupportFrame(
						new SupportFrameNo(i.pk.supTaskNo), new TaskContent(8, new WorkCode(i.taskCd5)));

				taskContents.add(frame1);
				taskContents.add(frame2);
				taskContents.add(frame3);
				taskContents.add(frame4);
				taskContents.add(frame5);
			}

			TaskBlockDetailContent block = new TaskBlockDetailContent(new TimeWithDayAttr(ts.pk.startClock),
					new TimeWithDayAttr(ts.endClock), taskContents);

			taskBlockDetails.add(block);
		}
	}
}
