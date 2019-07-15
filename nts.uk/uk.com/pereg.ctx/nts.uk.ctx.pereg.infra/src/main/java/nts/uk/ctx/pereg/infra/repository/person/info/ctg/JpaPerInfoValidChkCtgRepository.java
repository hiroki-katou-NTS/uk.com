package nts.uk.ctx.pereg.infra.repository.person.info.ctg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidChkCtgRepository;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidateCheckCategory;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpectPerInfoChkCtg;


@Stateless
public class JpaPerInfoValidChkCtgRepository extends JpaRepository implements PerInfoValidChkCtgRepository{
	
	private final static String SELECT_ALL = "SELECT a FROM PpectPerInfoChkCtg a ";
	private final static String SELECT_CTG_BY_LIST_CATEGORYID = "SELECT a FROM PpectPerInfoChkCtg a WHERE a.ppectPerInfoChkCtgPK.categoryCd in :listCategoryCd ";
	private final static String SELECT_BY_CTG_CONTRACT = SELECT_CTG_BY_LIST_CATEGORYID + " AND a.ppectPerInfoChkCtgPK.contractCd = :contractCd ";
	
	/**
	 * convert from person infor check category entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private static PerInfoValidateCheckCategory toDomain(PpectPerInfoChkCtg entity){
		return PerInfoValidateCheckCategory.createFromJavaType(entity.ppectPerInfoChkCtgPK.categoryCd, entity.ppectPerInfoChkCtgPK.contractCd,
														entity.scheduleMngReq, entity.yearMngReq, entity.dailyActualMngReq, 
														entity.monthActualMngReq, entity.monthCalcMngReq, entity.payMngReq, 
														entity.bonusMngReq, entity.jobSysReq, entity.paySysReq, entity.humanSysReq);
	}
	
	/**
	 * get a list person infor valid check
	 * @author yennth
	 */
	@Override
	public List<PerInfoValidateCheckCategory> getListPerInfoValid() {
		List<PerInfoValidateCheckCategory> listPerInfo = this.queryProxy().query(SELECT_ALL, PpectPerInfoChkCtg.class)
				.getList().stream().map(x->toDomain(x)).collect(Collectors.toList());
		return listPerInfo;
	}
	
	@Override
	public List<PerInfoValidateCheckCategory> getListPerInfoValidByListCtgId(List<String> listCategoryCd, String contractCd) {
		List<PerInfoValidateCheckCategory> listPerInfo = new ArrayList<>();
		CollectionUtil.split(listCategoryCd, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			listPerInfo.addAll(this.queryProxy().query(SELECT_BY_CTG_CONTRACT, PpectPerInfoChkCtg.class)
				.setParameter("listCategoryCd", subList)
				.setParameter("contractCd", contractCd)
				.getList(x -> toDomain(x)));
		});
		return listPerInfo;
	}
}
