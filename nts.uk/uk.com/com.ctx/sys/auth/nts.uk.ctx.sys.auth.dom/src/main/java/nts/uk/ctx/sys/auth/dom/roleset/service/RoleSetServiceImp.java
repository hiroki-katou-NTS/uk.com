package nts.uk.ctx.sys.auth.dom.roleset.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.grant.rolesetjob.RoleSetGrantedJobTitleRepository;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPersonRepository;
import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSetRepository;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetLinkWebMenuAdapter;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetLinkWebMenuImport;
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
	
	@Inject
	private RoleSetLinkWebMenuAdapter roleSetLinkWebMenuAdapter;
	/**
	 * Get all Role Set - ロールセットをすべて取得する
	 * @return
	 */
	@Override
	public List<RoleSet> getAllRoleSet() {
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
	public void registerRoleSet(RoleSet roleSet) {
		// validate
		roleSet.validate();
		
		//check duplicate RoleSetCd - ロールセットコードが重複してはならない
		if (roleSetRepository.isDuplicateRoleSetCd(roleSet.getRoleSetCd().v(), roleSet.getCompanyId())) {
			throw new BusinessException("Msg_3");
		}

		// Register Role Set into DB
		this.roleSetRepository.insert(roleSet);
	}

	/**
	 * アルゴリズム「新規登録」を実行する - Execute the algorithm "new registration"
	 * @param roleSet
	 * @param menuCds
	 */
	@Override
	public void executeRegister(RoleSet roleSet, List<String> webMenuCds) {
		// register to DB - ドメインモデル「ロールセット」を新規登録する
		this.registerRoleSet(roleSet);

		// アルゴリズム「ロールセット別紐付け新規登録」を実行する - Execute the algorithm "register new ties by role set"
		this.executeRegisterLinkWebMenu(roleSet.getCompanyId()
				, roleSet.getRoleSetCd().v()
				, webMenuCds);
	}
	
	/**
	 * アルゴリズム「ロールセット別紐付け新規登録」を実行する - Execute the algorithm "register new ties by role set"
	 * @param companyId
	 * @param roleSetCd
	 * @param webMenuCds
	 */
	private void executeRegisterLinkWebMenu(String companyId, String roleSetCd, List<String> webMenuCds) {
		// pre-check : メニューが１件以上選択されていなければならない: Msg_583, メニュー
		if (CollectionUtil.isEmpty(webMenuCds)) {
			throw new BusinessException("Msg_583");
		}

		// register to web menu link - ドメインモデル「ロールセット別紐付け」を新規登録する
		
		List<RoleSetLinkWebMenuImport> listRoleSetLinkWebMenuImport = webMenuCds.stream()
				.map(webMenuCd -> new RoleSetLinkWebMenuImport(companyId
						, roleSetCd
						, webMenuCd)).collect(Collectors.toList());
		
		this.roleSetLinkWebMenuAdapter.addAllRoleSetLinkWebMenu(listRoleSetLinkWebMenuImport);
	}
	
	/**
	 * Update Role Set - ロールセット更新登録
	 * @param roleSet
	 */
	@Override
	public	void updateRoleSet(RoleSet roleSet) {
		// validate
		roleSet.validate();
		
		// update the Role set to DB - ドメインモデル「ロールセット」を更新登録する
		this.roleSetRepository.update(roleSet);
	}
	
	/**
	 * アルゴリズム「更新登録」を実行する - Execute algorithm "update registration"
	 * @param roleSet
	 * @param webMenuCds
	 */
	@Override
	public	void executeUpdate(RoleSet roleSet, List<String> webMenuCds){
		
		// update to DB - ロールセット更新登録
		this.updateRoleSet(roleSet);

		// update to web menu link - アルゴリズム「ロールセット別紐付け更新登録」を実行する 
		// step 1: delete all old web menu
		this.roleSetLinkWebMenuAdapter.deleteAllRoleSetLinkWebMenu(roleSet.getRoleSetCd().v());

		// step 2: register new role set link web menu
		// アルゴリズム「ロールセット別紐付け新規登録」を実行する - Execute the algorithm "register new ties by role set"
		this.executeRegisterLinkWebMenu(roleSet.getCompanyId()
				, roleSet.getRoleSetCd().v()
				, webMenuCds);
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
			throw new BusinessException("Msg_585");
		}
		// ロールセット職位別付与で使用されている場合は削除できない
		if (isGrantedForPosition(companyId, roleSetCd)) {
			throw new BusinessException("Msg_585");
		}

		// ドメインモデル「既定のロールセット」を取得する
		if (isDefault(companyId, roleSetCd)) {
			throw new BusinessException("Msg_585");
		}		
		
		// register to DB - ドメインモデル「ロールセット」を新規登録する
		this.roleSetRepository.delete(roleSetDom.getRoleSetCd().v(), roleSetDom.getCompanyId());
	}
	
	/**
	 * アルゴリズム「削除」を実行する - Execute algorithm "delete"
	 * @param roleSetCd
	 */
	@Override
	public void executeDelete(String roleSetCd) {
		
		// remove Role Set from DB - ドメインモデル「ロールセット」を削除する
		this.deleteRoleSet(roleSetCd);
					
		// remove role set link web menu - ドメインモデル「ロールセット別紐付け」を削除する
		roleSetLinkWebMenuAdapter.deleteAllRoleSetLinkWebMenu(roleSetCd);
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
