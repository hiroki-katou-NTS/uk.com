package nts.uk.ctx.at.request.infra.repository.reasonappdaily;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.reasonappdaily.AppReasonMap;
import nts.uk.ctx.at.request.dom.reasonappdaily.ApplicationReasonInfo;
import nts.uk.ctx.at.request.dom.reasonappdaily.ApplicationTypeReason;
import nts.uk.ctx.at.request.dom.reasonappdaily.ReasonApplicationDailyResult;
import nts.uk.ctx.at.request.dom.reasonappdaily.ReasonApplicationDailyResultRepo;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.infra.entity.reasonappdaily.KrqdtApplicationReason;
import nts.uk.ctx.at.request.infra.entity.reasonappdaily.KrqdtApplicationReasonPK;

@Stateless
public class JpaReasonApplicationDailyResultRepo extends JpaRepository implements ReasonApplicationDailyResultRepo {

	@Override
	public List<ReasonApplicationDailyResult> findReasonAppDaily(String employeeId, GeneralDate date, PrePostAtr preAtr,
			ApplicationType apptype, Optional<OvertimeAppAtr> overAppAtr) {
		Optional<KrqdtApplicationReason> entity = this.queryProxy().find(new KrqdtApplicationReasonPK(employeeId, date),
				KrqdtApplicationReason.class);
		return entity.map(x -> toDomain(x)).orElse(new ArrayList<>()).stream().filter(x -> {
			if (x.getApplicationTypeReason().getAppType() == ApplicationType.OVER_TIME_APPLICATION) {
				return overAppAtr.isPresent() && x.getApplicationTypeReason().getAppType() == apptype
						&& x.getApplicationTypeReason().getOverAppAtr().get() == overAppAtr.get()
						&& x.getPrePostAtr() == preAtr;
			}
			return x.getApplicationTypeReason().getAppType() == apptype && x.getPrePostAtr() == preAtr;
		}).collect(Collectors.toList());
	}

	@Override
	public void addUpdateReason(String cid, List<ReasonApplicationDailyResult> reason) {

		Map<Pair<String, GeneralDate>, List<ReasonApplicationDailyResult>> mapWithKey = reason.stream()
				.collect(Collectors.groupingBy(x -> Pair.of(x.getEmployeeId(), x.getDate())));

		mapWithKey.entrySet().forEach(x -> {
			Optional<KrqdtApplicationReason> entityOpt = this.queryProxy().find(
					new KrqdtApplicationReasonPK(x.getKey().getLeft(), x.getKey().getRight()),
					KrqdtApplicationReason.class);
			KrqdtApplicationReason entity = null;
			if (!entityOpt.isPresent()) {
				entity = new KrqdtApplicationReason();
				entity.pk = new KrqdtApplicationReasonPK(x.getKey().getLeft(), x.getKey().getRight());
				entity.cid = cid;
			} else {
				entity = entityOpt.get();
			}
			this.commandProxy()
					.update(toEntitySameKey(cid, x.getKey().getLeft(), x.getKey().getRight(), entity, x.getValue()));
		});
	}

	public List<ReasonApplicationDailyResult> toDomain(KrqdtApplicationReason entity) {

		List<ReasonApplicationDailyResult> results = new ArrayList<>();
		Field[] fields = entity.getClass().getDeclaredFields();
		AtomicReference<String> valueFix = new AtomicReference<String>();
		Arrays.asList(fields).stream().filter(x -> x.isAnnotationPresent(AppReasonMap.class)).forEach(x -> {
			Object value = null;
			try {
				value = x.get(entity);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			AppReasonMap appAno = x.getAnnotation(AppReasonMap.class);
			x.setAccessible(true);
			if (appAno.fix()) {
				valueFix.set(value == null ? null : String.valueOf(value));
			} else if (valueFix.get() != null || value != null) {
				ReasonApplicationDailyResult result = new ReasonApplicationDailyResult(entity.pk.sid, entity.pk.ymd,
						new ApplicationTypeReason(appAno.type(),
								appAno.type() == ApplicationType.OVER_TIME_APPLICATION ? Optional.of(appAno.overType())
										: Optional.empty()),
						appAno.before(),
						new ApplicationReasonInfo(
								valueFix.get() == null ? null
										: new AppStandardReasonCode(Integer.parseInt(valueFix.get())),
								value == null ? null : new AppReason(String.valueOf(value))));
				results.add(result);
			}
		});

		return results;
	}

	private KrqdtApplicationReason toEntitySameKey(String cid, String sid, GeneralDate date,
			KrqdtApplicationReason entity, List<ReasonApplicationDailyResult> domain) {

		Field[] fields = entity.getClass().getDeclaredFields();
		domain.forEach(data -> {
			Arrays.asList(fields).stream().filter(x -> x.isAnnotationPresent(AppReasonMap.class)).forEach(x -> {
				AppReasonMap appAno = x.getAnnotation(AppReasonMap.class);
				x.setAccessible(true);
				try {
					if (data.getApplicationTypeReason().getAppType() == ApplicationType.OVER_TIME_APPLICATION) {

						if (appAno.type() == data.getApplicationTypeReason().getAppType()
								&& appAno.overType() == data.getApplicationTypeReason().getOverAppAtr().get()
								&& appAno.before() == data.getPrePostAtr()) {
							if (appAno.fix()) {
								x.set(entity, data.getReasonInfo().getStandardReasonCode() == null ? null
										: data.getReasonInfo().getStandardReasonCode().v());
							} else {
								x.set(entity, data.getReasonInfo().getOpAppReason() == null ? null
										: data.getReasonInfo().getOpAppReason().v());
							}
						}
					} else {
						if (appAno.type() == data.getApplicationTypeReason().getAppType()
								&& appAno.before() == data.getPrePostAtr()) {
							if (appAno.fix()) {
								x.set(entity, data.getReasonInfo().getStandardReasonCode() == null ? null
										: data.getReasonInfo().getStandardReasonCode().v());
							} else {
								x.set(entity, data.getReasonInfo().getOpAppReason() == null ? null
										: data.getReasonInfo().getOpAppReason().v());
							}
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});

		});
		return entity;
	}
}
