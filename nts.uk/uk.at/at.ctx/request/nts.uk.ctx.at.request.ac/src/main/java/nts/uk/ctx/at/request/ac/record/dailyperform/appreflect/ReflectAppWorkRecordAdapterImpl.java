package nts.uk.ctx.at.request.ac.record.dailyperform.appreflect;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.appreflect.ReflectApplicationWorkRecordPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.reflect.ReflectApplicationWorkRecordAdapter;
import nts.uk.ctx.at.shared.dom.application.reflect.ReflectStatusResultShare;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;

@Stateless
public class ReflectAppWorkRecordAdapterImpl implements ReflectApplicationWorkRecordAdapter {

	@Inject
	private ReflectApplicationWorkRecordPub pub;

	@Override
	public Pair<ReflectStatusResultShare, Optional<AtomTask>> process(ExecutionType type, Object application, GeneralDate date,
			ReflectStatusResultShare reflectStatus) {
		return pub.process(type, application, date, reflectStatus);
	}

}
