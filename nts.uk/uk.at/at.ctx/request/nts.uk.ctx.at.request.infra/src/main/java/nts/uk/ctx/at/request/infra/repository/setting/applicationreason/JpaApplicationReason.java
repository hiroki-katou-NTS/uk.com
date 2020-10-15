//package nts.uk.ctx.at.request.infra.repository.setting.applicationreason;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import javax.ejb.Stateless;
//
//import lombok.val;
//import nts.arc.enums.EnumAdaptor;
//import nts.arc.layer.infra.data.JpaRepository;
//import nts.gul.collection.CollectionUtil;
//import nts.gul.text.StringUtil;
//import nts.uk.ctx.at.request.dom.application.ApplicationType;
//import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
//import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
//import nts.uk.ctx.at.request.dom.setting.applicationreason.DefaultFlg;
//import nts.uk.ctx.at.request.dom.setting.applicationreason.ReasonTemp;
//import nts.uk.ctx.at.request.infra.entity.setting.applicationformreason.KrqstAppReason;
//import nts.uk.ctx.at.request.infra.entity.setting.applicationformreason.KrqstAppReasonPK;
////import nts.uk.shr.com.context.AppContexts;
//import nts.uk.shr.com.i18n.TextResource;
//
//@Stateless
//public class JpaApplicationReason extends JpaRepository implements ApplicationReasonRepository{
//	private static final String FINDBYCOMPANYID = "SELECT c FROM KrqstAppReason c "
//			+ "WHERE c.krqstAppReasonPK.companyId = :companyId";
//	
//	private static final String FINDBYAPPTYPE = FINDBYCOMPANYID + " AND c.krqstAppReasonPK.appType = :appType";
//
//	
//	private static final String FINDBYREASONID = FINDBYCOMPANYID + " AND c.krqstAppReasonPK.reasonID = :reasonID";
//	
////	private static final String FINDBYDEFAULT = FINDBYAPPTYPE + " AND c.defaultFlg = :defaultFlg";
//	
//	private static final String DELETEREASON = "DELETE FROM KrqstAppReason c WHERE c.krqstAppReasonPK.companyId = :companyId "
//			+ "AND c.krqstAppReasonPK.appType = :appType AND c.krqstAppReasonPK.reasonID = :reasonID ";
//
//	/**
//	 * get reason by companyid
//	 */
//	@Override
//	public List<ApplicationReason> getReasonByCompanyId(String companyId) {
//		List<ApplicationReason> data = this.queryProxy()
//				.query(FINDBYCOMPANYID, KrqstAppReason.class)
//				.setParameter("companyId", companyId)
//				.getList(c ->toOvertimeAppSetDomain(c));
//		return data;
//	}
//
//	private ApplicationReason toOvertimeAppSetDomain(KrqstAppReason c) {
//		return ApplicationReason.createSimpleFromJavaType(c.krqstAppReasonPK.companyId,
//				c.krqstAppReasonPK.appType,
//				c.krqstAppReasonPK.reasonID,
//				c.dispOrder,
//				c.reasonTemp,
//				c.defaultFlg
//				);
//	}
//
//	/**
//	 * get reason by application type
//	 */
//	@Override
//	public List<ApplicationReason> getReasonByAppType(String companyId, int appType) {		
//		return getReasonByAppType(companyId, appType, null);
//	}
//	/**
//	 * get reason by application type
//	 */
//	@Override
//	public List<ApplicationReason> getReasonByAppType(String companyId, int appType, String defaultResource) {
//		List<ApplicationReason> data = this.queryProxy()
//				.query(FINDBYAPPTYPE, KrqstAppReason.class)
//				.setParameter("companyId", companyId)
//				.setParameter("appType", appType)
//				.getList(c ->toOvertimeAppSetDomain(c));
//		List<ApplicationReason> dataTmp = data.stream().filter(x -> x.getDefaultFlg() == DefaultFlg.DEFAULT).collect(Collectors.toList());
//		ApplicationReason firstData = new ApplicationReason(companyId, EnumAdaptor.valueOf(appType, ApplicationType.class), "", 0, new ReasonTemp("選択してください"), DefaultFlg.NOTDEFAULT);
//		if(CollectionUtil.isEmpty(dataTmp)) {
//			String defaultRsName = StringUtil.isNullOrEmpty(defaultResource, true) ? "選択してください" : TextResource.localize(defaultResource);
//			firstData = new ApplicationReason(companyId, EnumAdaptor.valueOf(appType, ApplicationType.class), "", 0, new ReasonTemp(defaultRsName), DefaultFlg.DEFAULT);
//		}
//		Collections.sort(data, Comparator.comparing(ApplicationReason :: getDispOrder));
//		data.add(0, firstData);
//		return data;
//	}
//	/**
//	 * get reason by reasonID
//	 */
//	@Override
//	public Optional<ApplicationReason> getReasonById(String companyId, String reasonID) {
//		return this.queryProxy()
//				.query(FINDBYREASONID,KrqstAppReason.class)
//				.setParameter("companyId", companyId)
//				.setParameter("reasonID", reasonID)
//				.getSingle(c->toOvertimeAppSetDomain(c));
//	}
//	
//	/**
//	 * get reason by application type for KAF022
//	 * @author yennth
//	 */
//	@Override
//	public List<ApplicationReason> getByAppType(String companyId, int appType) {		
//		return this.queryProxy().query(FINDBYAPPTYPE,KrqstAppReason.class)
//				.setParameter("companyId", companyId)
//				.setParameter("appType", appType)
//				.getList(c->toOvertimeAppSetDomain(c));
//	}
//	
//	/**
//	 * convert from domain to entity
//	 * @param domain
//	 * @return
//	 * @author yennth
//	 */
//	private KrqstAppReason toEntity(ApplicationReason domain){
//		val entity = new KrqstAppReason(new KrqstAppReasonPK(domain.getCompanyId(), domain.getAppType().value, domain.getReasonID()), 
//				domain.getDispOrder(), domain.getReasonTemp().v(), domain.getDefaultFlg().value);
//		return entity;
//	}
//	
//	/**
//	 * update a list application reason
//	 * @author yennth
//	 */
//	@Override
//	public void updateReason(List<ApplicationReason> listUpdate) {
//		List<KrqstAppReason> listEntity = new ArrayList<>();
//		if (!listUpdate.isEmpty()) {
//			for (ApplicationReason obj : listUpdate) {
//				listEntity.add(toEntity(obj));
//			}
//			// update list nhận được
//			this.commandProxy().updateAll(listEntity);
//		}
//	}
//	/**
//	 * insert a item
//	 * @author yennth
//	 */
//	@Override
//	public void insertReason(ApplicationReason insert) {
//		this.commandProxy().insert(toEntity(insert));
//	}
//
//	@Override
//	public void deleteReason(String companyId, int appType, String reasonID) {
//		this.getEntityManager().createQuery(DELETEREASON)
//		.setParameter("companyId", companyId)
//		.setParameter("appType", appType)
//		.setParameter("reasonID", reasonID)
//		.executeUpdate();
//	}
//
//}
