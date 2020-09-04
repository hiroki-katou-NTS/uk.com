package nts.uk.ctx.at.record.pub.appreflect;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.application.reflect.ReflectStatusResultShare;

public interface ReflectApplicationWorkRecordPub {

	public Pair<ReflectStatusResultShare, Optional<AtomTask>> process(Object application, GeneralDate date,
			ReflectStatusResultShare reflectStatus);
}
