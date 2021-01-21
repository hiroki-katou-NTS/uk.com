package nts.uk.ctx.at.request.infra.repository.application.stamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository;
import nts.uk.ctx.at.request.dom.application.stamp.DestinationTimeApp;
import nts.uk.ctx.at.request.dom.application.stamp.DestinationTimeZoneApp;
import nts.uk.ctx.at.request.dom.application.stamp.StartEndClassification;
import nts.uk.ctx.at.request.dom.application.stamp.TimeStampApp;
import nts.uk.ctx.at.request.dom.application.stamp.TimeStampAppEnum;
import nts.uk.ctx.at.request.dom.application.stamp.TimeStampAppOther;
import nts.uk.ctx.at.request.dom.application.stamp.TimeZoneStampClassification;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStamp;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStampPK;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.com.time.TimeZone;
@Stateless
public class JpaAppStampRepository extends JpaRepository implements AppStampRepository{
	public static final String FIND_BY_APPID = "SELECT * FROM KRQDT_APP_STAMP WHERE CID = @cid and APP_ID = @appId";
	public static final Integer START_CANCEL = 1;
	public static final Integer START_NOT_CANCEL = 0;
	public static final Integer END_CANCEL = 1;
	public static final Integer END_NOT_CANCEL = 0;

	@Override
	public Optional<AppStamp> findByAppID(String companyID, String appID) {
		List<KrqdtAppStamp> krqdtAppStampList = new NtsStatement(FIND_BY_APPID, this.jdbcProxy())
				.paramString("cid", companyID).paramString("appId", appID).getList(res -> toEntity(res));
		return toDomain(krqdtAppStampList);

	}

	public KrqdtAppStamp toEntity(NtsResultRecord res) {
		Integer stampAtr = res.getInt("STAMP_ATR");
		Integer stampFrameNo = res.getInt("STAMP_FRAME_NO");
		Integer startTime = res.getInt("START_TIME");
		Integer endTime = res.getInt("END_TIME");
		Integer goOutAtr = res.getInt("GO_OUT_ATR");
		Integer startCancelAtr = res.getInt("START_CANCEL_ATR");
		Integer endCancelAtr = res.getInt("END_CANCEL_ATR");
		String appID = res.getString("APP_ID");
		String companyID = res.getString("CID");
		return new KrqdtAppStamp(new KrqdtAppStampPK(companyID, appID, stampAtr, stampFrameNo), startTime, endTime,
				startCancelAtr, endCancelAtr, goOutAtr);
	}

	@Override
	public void addStamp(AppStamp appStamp) {
		this.commandProxy().insertAll(toEntityList(appStamp));
		this.getEntityManager().flush();

	}

	@Override
	public void updateStamp(AppStamp appStamp) {
		String companyID = AppContexts.user().companyId();
		List<KrqdtAppStamp> listKrqdtAppStamp = toEntityList(appStamp);
		this.delete(companyID, appStamp.getAppID());
		this.getEntityManager().flush();
		this.commandProxy().insertAll(listKrqdtAppStamp);
		this.getEntityManager().flush();

	}
	public Boolean isNotExistKey(KrqdtAppStampPK key, List<KrqdtAppStamp> listKrqdtAppStamp) {
		if (CollectionUtil.isEmpty(listKrqdtAppStamp)) return true;
		return listKrqdtAppStamp.stream().filter( x -> x.krqdtAppStampPK.appID == key.appID
				&& x.krqdtAppStampPK.stampAtr == key.stampAtr
				&& x.krqdtAppStampPK.stampFrameNo == key.stampFrameNo).findFirst().isPresent() ?  true : false;
		
	}

	@Override
	public void delete(String companyID, String appID) {
		List<KrqdtAppStamp> krqdtAppStampList = new NtsStatement(FIND_BY_APPID, this.jdbcProxy())
				.paramString("cid", companyID).paramString("appId", appID).getList(res -> toEntity(res));
		List<KrqdtAppStampPK> keys = krqdtAppStampList.stream().map(
				x -> new KrqdtAppStampPK(companyID, appID, x.krqdtAppStampPK.stampAtr, x.krqdtAppStampPK.stampFrameNo)).collect(Collectors.toList());
		this.commandProxy().removeAll(KrqdtAppStamp.class, keys);
	}

	public Integer convertEnumTimeStamApp(TimeStampAppEnum timeStampAppEnum) {
		if (timeStampAppEnum == TimeStampAppEnum.ATTEENDENCE_OR_RETIREMENT) {
			return 0;
		} else if (timeStampAppEnum == TimeStampAppEnum.EXTRAORDINARY) {
			return 4;
		} else if (timeStampAppEnum == TimeStampAppEnum.GOOUT_RETURNING) {
			return 1;
		} else {
			return 3;
		}

	}

	public Integer convertTimeZoneStampClassification(TimeZoneStampClassification timeZoneStampClassification) {
		if (timeZoneStampClassification == TimeZoneStampClassification.PARENT) {
			return 2;
		} else if (timeZoneStampClassification == TimeZoneStampClassification.NURSE) {
			return 6;
		} else {
			return 5;
		}

	}

	public List<KrqdtAppStamp> toEntityList(AppStamp appStamp) {
		
		
		List<KrqdtAppStamp> listStamps = new ArrayList<KrqdtAppStamp>();

		List<TimeStampApp> listTimeStampApp = appStamp.getListTimeStampApp();
		List<DestinationTimeApp> listDestinationTimeApp = appStamp.getListDestinationTimeApp();
		List<TimeStampAppOther> listTimeStampAppOther = appStamp.getListTimeStampAppOther();
		List<DestinationTimeZoneApp> listDestinationTimeZoneApp = appStamp.getListDestinationTimeZoneApp();

		if (!CollectionUtil.isEmpty(listTimeStampApp)) {
			listTimeStampApp.stream().forEach(x -> {
				KrqdtAppStamp krqdtAppStamp;
				if (!CollectionUtil.isEmpty(listStamps)) {
					Optional<KrqdtAppStamp> optional = listStamps.stream().filter(
							item -> item.krqdtAppStampPK.stampFrameNo == x.getDestinationTimeApp().getEngraveFrameNo() && item.krqdtAppStampPK.stampAtr == convertEnumTimeStamApp(x.getDestinationTimeApp().getTimeStampAppEnum()))
							.findFirst();
					if (optional.isPresent()) {
						krqdtAppStamp = optional.get();
						if (x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.START) {
							krqdtAppStamp.startTime = x.getTimeOfDay().v();
							krqdtAppStamp.startCancelAtr = START_NOT_CANCEL;
						} 
						if (x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.END) {
							krqdtAppStamp.endTime = x.getTimeOfDay().v();
							krqdtAppStamp.endCancelAtr = END_NOT_CANCEL;
						}
					} else {
						krqdtAppStamp = new KrqdtAppStamp(
								new KrqdtAppStampPK(AppContexts.user().companyId(), appStamp.getAppID(),
										convertEnumTimeStamApp(x.getDestinationTimeApp().getTimeStampAppEnum()),
										x.getDestinationTimeApp().getEngraveFrameNo()),
								x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.START
										? x.getTimeOfDay().v()
										: null,
								x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.END
										? x.getTimeOfDay().v()
										: null,
								x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.START ? START_NOT_CANCEL : null, 
								x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.END ? END_NOT_CANCEL : null,
								x.getAppStampGoOutAtr().isPresent() ? x.getAppStampGoOutAtr().get().value : null);
						listStamps.add(krqdtAppStamp);
					}
				} else {
					krqdtAppStamp = new KrqdtAppStamp(
							new KrqdtAppStampPK(AppContexts.user().companyId(), appStamp.getAppID(),
									convertEnumTimeStamApp(x.getDestinationTimeApp().getTimeStampAppEnum()),
									x.getDestinationTimeApp().getEngraveFrameNo()),
							x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.START
									? x.getTimeOfDay().v()
									: null,
							x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.END
									? x.getTimeOfDay().v()
									: null,
							x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.START ? START_NOT_CANCEL : null, 
							x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.END ? END_NOT_CANCEL : null,
							x.getAppStampGoOutAtr().isPresent() ? x.getAppStampGoOutAtr().get().value : null);
					listStamps.add(krqdtAppStamp);
				}

				
			});
		}

		if (!CollectionUtil.isEmpty(listDestinationTimeApp)) {
			listDestinationTimeApp.stream().forEach(x -> {
				KrqdtAppStamp krqdtAppStamp;
				if (!CollectionUtil.isEmpty(listStamps)) {
					Optional<KrqdtAppStamp> optional = listStamps.stream()
							.filter(item -> item.krqdtAppStampPK.stampFrameNo == x.getEngraveFrameNo() && item.krqdtAppStampPK.stampAtr == convertEnumTimeStamApp(x.getTimeStampAppEnum())).findFirst();
					if (optional.isPresent()) {
						krqdtAppStamp = optional.get();
						if (x.getStartEndClassification() == StartEndClassification.START) {
							krqdtAppStamp.startCancelAtr = START_CANCEL;
						}
						
						if (x.getStartEndClassification() == StartEndClassification.END) {
							krqdtAppStamp.endCancelAtr = END_CANCEL;
						}
		
					} else {
						krqdtAppStamp = new KrqdtAppStamp(
								new KrqdtAppStampPK(AppContexts.user().companyId(), appStamp.getAppID(),
										convertEnumTimeStamApp(x.getTimeStampAppEnum()), x.getEngraveFrameNo()),
								null, null, x.getStartEndClassification() == StartEndClassification.START ? START_CANCEL : null,
								x.getStartEndClassification() == StartEndClassification.END ? END_CANCEL : null, 
										null);
						listStamps.add(krqdtAppStamp);
					}
				} else {
					krqdtAppStamp = new KrqdtAppStamp(
							new KrqdtAppStampPK(AppContexts.user().companyId(), appStamp.getAppID(),
									convertEnumTimeStamApp(x.getTimeStampAppEnum()), x.getEngraveFrameNo()),
							null, null, x.getStartEndClassification() == StartEndClassification.START ? START_CANCEL : null,
							x.getStartEndClassification() == StartEndClassification.END ? END_CANCEL : null, 
									null);
					listStamps.add(krqdtAppStamp);
				}
				

			});
		}
		
		if (!CollectionUtil.isEmpty(listTimeStampAppOther)) {
			listTimeStampAppOther.stream().forEach(item -> {
				KrqdtAppStamp krqdtAppStamp = new KrqdtAppStamp(
						new KrqdtAppStampPK(AppContexts.user().companyId(), appStamp.getAppID(), convertTimeZoneStampClassification(item.getDestinationTimeZoneApp().getTimeZoneStampClassification()), item.getDestinationTimeZoneApp().getEngraveFrameNo()),
						item.getTimeZone().getStartTime().v(),
						item.getTimeZone().getEndTime().v(),
						START_NOT_CANCEL,
						END_NOT_CANCEL,
						null);
				listStamps.add(krqdtAppStamp);
			});
		}
		if (!CollectionUtil.isEmpty(listDestinationTimeZoneApp)) {
			listDestinationTimeZoneApp.stream().forEach(item -> {
				KrqdtAppStamp krqdtAppStamp = new KrqdtAppStamp(
						new KrqdtAppStampPK(AppContexts.user().companyId(), appStamp.getAppID(), convertTimeZoneStampClassification(item.getTimeZoneStampClassification()), item.getEngraveFrameNo()),
						null,
						null,
						START_CANCEL,
						END_CANCEL,
						null);
				listStamps.add(krqdtAppStamp);
			});
		}
//		this.commandProxy().insertAll(listStamps);
//		this.getEntityManager().flush();
		

		return listStamps;
	}

	public Optional<AppStamp> toDomain(List<KrqdtAppStamp> krqdtAppStampList) {
		List<TimeStampApp> listTimeStampApp = new ArrayList<TimeStampApp>();
		List<DestinationTimeApp> listDestinationTimeApp = new ArrayList<DestinationTimeApp>();
		List<TimeStampAppOther> listTimeStampAppOther = new ArrayList<TimeStampAppOther>();
		List<DestinationTimeZoneApp> listDestinationTimeZoneApp = new ArrayList<DestinationTimeZoneApp>();
		Optional<AppStamp> appStamp = Optional.of(new AppStamp());

		if (!CollectionUtil.isEmpty(krqdtAppStampList)) {
			krqdtAppStampList.stream().forEach(krqdtAppStamp -> {
				Integer stampAtr = krqdtAppStamp.krqdtAppStampPK.stampAtr;
				Integer stampFrameNo = krqdtAppStamp.krqdtAppStampPK.stampFrameNo;
				Integer startTime = krqdtAppStamp.startTime;
				Integer endTime = krqdtAppStamp.endTime;
				Integer goOutAtr = krqdtAppStamp.goOutAtr;
				Integer startCancelAtr = krqdtAppStamp.startCancelAtr;
				Integer endCancelAtr = krqdtAppStamp.endCancelAtr;
				if (stampAtr == 2 || stampAtr == 6 || stampAtr == 5) {
					TimeZoneStampClassification timeZoneStampClassification = null;
					if (stampAtr == 2) {
						timeZoneStampClassification = TimeZoneStampClassification.PARENT;
					} else if (stampAtr == 6) {
						timeZoneStampClassification = TimeZoneStampClassification.NURSE;
					} else {
						timeZoneStampClassification = TimeZoneStampClassification.BREAK;
					}
					DestinationTimeZoneApp destinationTimeZoneApp = new DestinationTimeZoneApp(
							timeZoneStampClassification, stampFrameNo);
					// timeStampAppOther
					if (startTime != null && endTime != null) {

						TimeStampAppOther timeStampAppOther = new TimeStampAppOther(destinationTimeZoneApp,
								new TimeZone(startTime, endTime));
						listTimeStampAppOther.add(timeStampAppOther);
						// listDestinationTimeZoneApp
					} else {
						listDestinationTimeZoneApp.add(destinationTimeZoneApp);
					}
				} else {
					TimeStampAppEnum timeStampAppEnum = null;
					if (stampAtr == 0) {
						timeStampAppEnum = TimeStampAppEnum.ATTEENDENCE_OR_RETIREMENT;
					} else if (stampAtr == 4) {
						timeStampAppEnum = TimeStampAppEnum.EXTRAORDINARY;
					} else if (stampAtr == 1) {
						timeStampAppEnum = TimeStampAppEnum.GOOUT_RETURNING;
					} else {
						timeStampAppEnum = TimeStampAppEnum.CHEERING;
					}
					DestinationTimeApp destinationTimeAppEnd = new DestinationTimeApp(timeStampAppEnum, stampFrameNo,
							StartEndClassification.END, Optional.empty());
					DestinationTimeApp destinationTimeAppStart = new DestinationTimeApp(timeStampAppEnum, stampFrameNo,
							StartEndClassification.START, Optional.empty());
					if (startCancelAtr != null) {
						if (startCancelAtr == 1) {
							listDestinationTimeApp.add(destinationTimeAppStart);
						} else {
							Optional<GoingOutReason> appStampGoOutAtrOp = Optional.empty();
							if (goOutAtr != null) {
								GoingOutReason appStampGoOutAtr = EnumAdaptor.valueOf(goOutAtr, GoingOutReason.class);
								appStampGoOutAtrOp = Optional.of(appStampGoOutAtr);
							}
							TimeStampApp timeStampApp = new TimeStampApp(destinationTimeAppStart,
									new TimeWithDayAttr(startTime), Optional.empty(), appStampGoOutAtrOp);
							listTimeStampApp.add(timeStampApp);
						}						
					}
					if (endCancelAtr != null) {
						if (endCancelAtr == 1) {
							listDestinationTimeApp.add(destinationTimeAppEnd);
						} else {
							Optional<GoingOutReason> appStampGoOutAtrOp = Optional.empty();
							if (goOutAtr != null) {
								GoingOutReason appStampGoOutAtr = EnumAdaptor.valueOf(goOutAtr, GoingOutReason.class);
								appStampGoOutAtrOp = Optional.of(appStampGoOutAtr);
							}
							TimeStampApp timeStampApp = new TimeStampApp(destinationTimeAppEnd,
									new TimeWithDayAttr(endTime), Optional.empty(), appStampGoOutAtrOp);
							listTimeStampApp.add(timeStampApp);
						}		
					}

				}

			});
		}

		if (appStamp.isPresent()) {
			appStamp.get().setListTimeStampApp(listTimeStampApp);
			appStamp.get().setListDestinationTimeApp(listDestinationTimeApp);
			appStamp.get().setListTimeStampAppOther(listTimeStampAppOther);
			appStamp.get().setListDestinationTimeZoneApp(listDestinationTimeZoneApp);
		}

		return appStamp;
	}

}
