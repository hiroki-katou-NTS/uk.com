package nts.uk.ctx.sys.gateway.dom.login.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoDtoImport;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleIndividualGrantAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleType;
import nts.uk.ctx.sys.gateway.dom.login.adapter.SysEmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.RoleImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.RoleIndividualGrantImport;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopByCompany;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopByCompanyRepository;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopModeType;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.SystemStatusType;
import nts.uk.ctx.sys.gateway.dom.stopbysystem.StopBySystem;
import nts.uk.ctx.sys.gateway.dom.stopbysystem.StopBySystemRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CollectCompanyListImpl implements CollectCompanyList {
	
	@Inject
	private RoleIndividualGrantAdapter roleIndividualGrantAdapter;
	
	@Inject
	private RoleAdapter roleAdapter;
	
	@Inject
	private UserAdapter userAdapter;
	
	@Inject
	private EmployeeInfoAdapter employeeInfoAdapter;
	
	@Inject
	private SysEmployeeAdapter employeeAdapter;
	
	@Inject
	private CompanyBsAdapter companyBsAdapter;
	
	@Inject
	private StopBySystemRepository repoStopSys;
	@Inject
	private StopByCompanyRepository repoStopCom;
	
	@Override
	public List<String> getCompanyList(String userID, String contractCd) {
		// ドメインモデル「ロール個人別付与」を取得する (get List RoleIndividualGrant)
		List<RoleIndividualGrantImport> roles = this.roleIndividualGrantAdapter.getByUserIDDateRoleType(userID, GeneralDate.today(),
				RoleType.COMPANY_MANAGER.value);

		List<RoleImport> roleImp = new ArrayList<>();

		if (!roles.isEmpty()) {
			// ドメインモデル「ロール」を取得する (Acquire domain model "role"
			roles.stream().map(roleItem -> {
				return roleImp.addAll(this.roleAdapter.getAllById(roleItem.getRoleId()));
			}).collect(Collectors.toList());
		}

		GeneralDate systemDate = GeneralDate.today();

		// ドメインモデル「ユーザ」を取得する get domain "User"
		Optional<UserImportNew> user = this.userAdapter.getByUserIDandDate(userID, systemDate);

		List<EmployeeInfoDtoImport> employees = new ArrayList<>();

		if (!user.get().getAssociatePersonId().get().isEmpty()) {
			employees.addAll(this.employeeInfoAdapter.getEmpInfoByPid(user.get().getAssociatePersonId().get()));

			employees = employees.stream()
				.filter(empItem -> !this.employeeAdapter.getStatusOfEmployee(empItem.getEmployeeId()).isDeleted())
				.collect(Collectors.toList());
		}

		// imported（権限管理）「会社」を取得する (imported (authority management) Acquire
		// "company") Request No.51
		List<CompanyBsImport> companys = this.companyBsAdapter.getAllCompany();

		List<String> companyIdAll = companys.stream().map(item -> {
			return item.getCompanyId();
		}).collect(Collectors.toList());

		List<String> lstCompanyId = new ArrayList<>();

		// merge duplicate companyId from lstRole and lstEm
		if (!roleImp.isEmpty()) {
			List<String> lstComp = new ArrayList<>();
			roleImp.forEach(role -> {
				if (role.getCompanyId() != null) {
					lstComp.add(role.getCompanyId());
				}
			});

			lstCompanyId.addAll(lstComp);
		}

		if (!employees.isEmpty()) {
			List<String> lstComp = new ArrayList<>();
			employees.forEach(emp -> {
				if (emp.getCompanyId() != null) {
					lstComp.add(emp.getCompanyId());
				}
			});

			lstCompanyId.addAll(lstComp);
		}

		lstCompanyId = lstCompanyId.stream().distinct().collect(Collectors.toList());

		// 取得した会社（List）から、会社IDのリストを抽出する (Extract the list of company IDs from
		// the acquired company (List))
		List<String> lstCompanyFinal = lstCompanyId.stream().filter(com -> companyIdAll.contains(com))
				.collect(Collectors.toList());
		List<String> lstResult = this.checkStopUse(contractCd, lstCompanyFinal);
		return lstResult;
	}
	/**
	 * @author hoatt
	 * 利用停止のチェック
	 * @param 契約コード - contractCd
	 * @param ・会社ID（List） Before filter - lstCID
	 * @return 会社ID（List） After filter
	 */
	@Override
	public List<String> checkStopUse(String contractCd, List<String> lstCID) {
		//ドメインモデル「システム全体の利用停止」を取得する (get domain [StopBySystem])
		Optional<StopBySystem> stopSys = repoStopSys.findByKey(contractCd);
		//取得できる
		//ドメインモデル「システム全体の利用停止.システム利用状態」をチェックする (StopBySystem.systemStatus)
		if(stopSys.isPresent() && stopSys.get().getSystemStatus().equals(SystemStatusType.STOP)){//「利用停止中」の場合
			//アルゴリズム「権限(ロール)のチェック」を実行する
			if(!this.checkRoleAuth(stopSys.get().getStopMode())){//False：ログイン権限なし
				return new ArrayList<>();
			}
		}
		//ドメインモデル「会社単位の利用停止」を取得する
		List<StopByCompany> lstCom = repoStopCom.getListComByContractCD(contractCd);
		if(lstCom.isEmpty()){
			return lstCID;
		}
		List<String> lstComStop = this.getLstComStopUse(lstCom);
		List<String> result = new ArrayList<>();
		for(String cID : lstCID){
			if(!lstComStop.contains(cID)){
				result.add(cID);
			}
		}
		return result;
	}
	/**
	 * @author hoatt
	 * 利用停止会社リストを取得する
	 * @param ドメインモデル「会社単位の利用停止」 - lstComStop
	 * @return 利用停止会社ID（List）
	 */
	@Override
	public List<String> getLstComStopUse(List<StopByCompany> lstComStop) {
		List<String> result = new ArrayList<>();
		//Input.ドメインモデル「会社単位の利用停止」をLoopする
		for(StopByCompany stopCom : lstComStop){
			//ドメインモデル「会社単位の利用停止.システム利用状態」をチェックする(check StopByCompany.systemStatus)
			if(stopCom.getSystemStatus().equals(SystemStatusType.STOP)){
				//アルゴリズム「権限(ロール)のチェック」を実行する
				if(this.checkRoleAuth(stopCom.getStopMode())){//True：ログイン権限あり
					continue;
				}
				//会社IDを生成する 会社ID＝[会社単位の利用停止.契約コード]+"-"+[会社単位の利用停止.会社コード]
				//		ex) 000000000001-0001
				//Output：利用停止会社ID（List）に「会社ID」を追加する
				result.add(stopCom.getContractCd() + "-" + stopCom.getCompanyCd());
			}
		}
		return result;
	}
	/**
	 * 権限(ロール)のチェック
	 * @param stopMode
	 * @return True：ログイン権限あり
　				False：ログイン権限なし
	 */
	@Override
	public boolean checkRoleAuth(StopModeType stopMode) {
		//[利用停止モード]を判別 (phân biệt [mode stop use])
		if(stopMode.equals(StopModeType.ADMIN_MODE)){
			//システム管理者ロールの設定があるか判別
			if(AppContexts.user().roles().forSystemAdmin() != null){
				return true;
			}
			//会社管理者ロールの設定があるか判別
			if(AppContexts.user().roles().forCompanyAdmin() != null){
				return true;
			}else{
				return false;
			}
		}else{
			//システム管理者ロールの設定があるか判別
			if(AppContexts.user().roles().forSystemAdmin() != null){
				return true;
			}
			//会社管理者ロールの設定があるか判別
			if(AppContexts.user().roles().forCompanyAdmin() != null){
				return true;
			}else{
				//リクエストリスト497を呼ぶ。：「ログイン者が担当者か判断する」で担当者ロールが存在するかを判別
				return roleAdapter.isEmpWhetherLoginerCharge();
			}
		}
	}

}
