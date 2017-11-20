package nts.uk.ctx.sys.auth.dom.roleset.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.grant.RoleSetGrantedJobTitleRepository;
import nts.uk.ctx.sys.auth.dom.grant.RoleSetGrantedPersonRepository;
import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSetRepository;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RoleSetServiceImp implements RoleSetService{

	@Inject RoleSetRepository roleSetRepository;
	
	@Inject
	private DefaultRoleSetRepository defaultRoleSetRepository;

	@Inject
	private RoleSetGrantedPersonRepository roleSetGrantedPersonRepository;
	
	@Inject
	private RoleSetGrantedJobTitleRepository roleSetGrantedJobTitleRepository;
	
	/**
	 * Get all Role Set - ロールセットをすべて取得する
	 * @return
	 */
	@Override
	public
	List<RoleSet> getAllRoleSet() {
		String companyId = AppContexts.user().companyId();
		if (StringUtils.isNoneEmpty(companyId)) {
			return null;	
		}
			
		return roleSetRepository.findByCompanyId(companyId);
	}
	
	/**
	 * Register Role Set - ロールセット新規登録
	 * @param roleSet
	 */
	@Override
	public
	void registerRoleSet(RoleSet roleSet) {
		// validate
		roleSet.validate();
		
		//check duplicate RoleSetCd - ロールセットコードが重複してはならない
		if (roleSetRepository.isDuplicateRoleSetCd(roleSet.getRoleSetCd().v(), roleSet.getCompanyId())) {
			throw new BusinessException("Msg_3");
		}

		// pre-check : メニューが１件以上選択されていなければならない: Msg_583, メニュー
		checkHasntWebMenuAndThrowException(roleSet);

		// Register Role Set into DB
		this.roleSetRepository.insert(roleSet);
	}
	
	/**
	 * Update Role Set - ロールセット更新登録
	 * @param roleSet
	 */
	@Override
	public
	void updateRoleSet(RoleSet roleSet) {
		// validate
		roleSet.validate();
		
		// pre-check : メニューが１件以上選択されていなければならない: Msg_583, メニュー
		checkHasntWebMenuAndThrowException(roleSet);

		// update the Role set to DB - ドメインモデル「ロールセット」を更新登録する
		this.roleSetRepository.update(roleSet);
	}
	
	/**
	 * Check if the Role set hasn't any web menu and then throw business exception with #Msg_583
	 * @param roleSet
	 */
	private void checkHasntWebMenuAndThrowException(RoleSet roleSet) {
		if (CollectionUtil.isEmpty(roleSet.getRoleSetAndWebMenus())) {
			throw new BusinessException("Msg_583");
		}		
	}

	/**
	 * Delete Role Set - ロールセット削除
	 * @param roleSetCd
	 */
	@Override
	public void deleteRoleSet(String roleSetCd) {
		/**
		 * Validate constrains before perform deleting
		 */
		String companyId = AppContexts.user().companyId();
		if (StringUtils.isNoneEmpty(companyId)) {
			return;
		}
		Optional<RoleSet> roleSetOpt = roleSetRepository.findByRoleSetCdAndCompanyId(roleSetCd, companyId);
		if (!roleSetOpt.isPresent()) {
			return;
		}
		RoleSet roleSetDom = roleSetOpt.get();
		
		//Confirm preconditions - 事前条件を確認する - ドメインモデル「既定のロールセット」を取得する
		
		// ロールセット個人別付与で使用されている場合は削除できない
		if (isGrantedForPerson(companyId, roleSetCd)) {
			throw new BusinessException("Msg_???");
		}
		// ロールセット職位別付与で使用されている場合は削除できない
		if (isGrantedForPosition(companyId, roleSetCd)) {
			throw new BusinessException("Msg_???");
		}

		// ドメインモデル「既定のロールセット」を取得する
		if (isDefault(companyId, roleSetCd)) {
			throw new BusinessException("Msg_585");
		}		
		
		// register to DB - ドメインモデル「ロールセット」を新規登録する
		this.roleSetRepository.delete(roleSetDom.getRoleSetCd().v(), roleSetDom.getCompanyId());
	}
	
	/**
	 * Check setting default of Role set
	 * @return
	 */
	private boolean isDefault(String companyId, String roleSetCd) {
		return defaultRoleSetRepository.find(companyId, roleSetCd).isPresent();
	}

	/**
	 * Check if this Role Set is granted for member
	 * @return
	 */
	private boolean isGrantedForPerson(String companyId, String roleSetCd) {
		/**check from CAS014 */
		return roleSetGrantedPersonRepository.checkRoleSetCdExist(roleSetCd, companyId);
	}
	
	/**
	 * Check if this Role Set is granted for Position (manager)
	 * @return
	 */
	private boolean isGrantedForPosition(String companyId, String roleSetCd) {
		/** check from CAS014 */
		return roleSetGrantedJobTitleRepository.checkRoleSetCdExist(roleSetCd, companyId);
	}
}
