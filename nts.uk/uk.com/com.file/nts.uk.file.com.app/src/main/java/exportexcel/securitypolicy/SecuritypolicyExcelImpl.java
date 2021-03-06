package exportexcel.securitypolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicyRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicyRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;
/**
 * 
 * @author Hoidd
 *
 */
@Stateless
@DomainID(value = "SecuritySetting")
public class SecuritypolicyExcelImpl implements MasterListData{
	@Inject
	private AccountLockPolicyRepository accountLockPolicyRepository;
	@Inject
	private PasswordPolicyRepository passwordPolicyRepository;
	
	////pass 
	public PasswordPolicyDtoExcel getPasswordPolicy() {
		String contractCode = AppContexts.user().contractCode();
		PasswordPolicy passwordPolicy = this.passwordPolicyRepository
				.getPasswordPolicy(new ContractCode(contractCode));
		
		return this.toDtoPass(passwordPolicy);
	}
	private PasswordPolicyDtoExcel toDtoPass(PasswordPolicy passwordPolicy) {
		return new PasswordPolicyDtoExcel(
				passwordPolicy.getNotificationPasswordChange().v().intValue(), passwordPolicy.isLoginCheck(),
				passwordPolicy.isInitialPasswordChange(), passwordPolicy.isUse(),
				passwordPolicy.getHistoryCount().v().intValue(),
				passwordPolicy.getComplexityRequirement().getMinimumLength().v(),
				passwordPolicy.getValidityPeriod().v().intValue(),
				passwordPolicy.getComplexityRequirement().getNumeralDigits().v(),
				passwordPolicy.getComplexityRequirement().getSymbolDigits().v(),
				passwordPolicy.getComplexityRequirement().getAlphabetDigits().v());
	}
	//account
	public AccountLockPolicyDtoExcel getAccountLockPolicy() {
		String contractCode = AppContexts.user().contractCode();
		Optional<AccountLockPolicy> accountLockPolicyOptional = this.accountLockPolicyRepository
				.getAccountLockPolicy(new ContractCode(contractCode));
		if (accountLockPolicyOptional.isPresent()) {
			return this.toDtoAccount(accountLockPolicyOptional.get());
		} else {
			return null;
		}
	}

	private AccountLockPolicyDtoExcel toDtoAccount(AccountLockPolicy accountLockPolicy) {
		return new AccountLockPolicyDtoExcel(
				accountLockPolicy.getErrorCount().v().intValue(), accountLockPolicy.getLockInterval().v().intValue(),
				accountLockPolicy.getLockOutMessage().v(), accountLockPolicy.isUse());

	}
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("??????", TextResource.localize("CAS003_40"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("column2", "",
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("column3", "",
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("???", TextResource.localize("CAS003_41"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		AccountLockPolicyDtoExcel acountPolicy = getAccountLockPolicy();
		PasswordPolicyDtoExcel passPolicy = getPasswordPolicy(); 
		List<MasterData> datas = new ArrayList<>();
			/**put password policy*/
		if(passPolicy!=null){
			if(passPolicy.isUse==false){
				putDataCustom(datas,TextResource.localize("CAS003_3"),"","","-",0);
				putDataCustom(datas,"",TextResource.localize("CAS003_4"),"","",1);
				/**put row 3*/
				putDataCustom(datas,"",TextResource.localize("CAS003_6"),TextResource.localize("CAS003_7"),"",1);
				/**put row 4*/
				putDataCustom(datas,"","",TextResource.localize("CAS003_10"),"",1);
				/**put row 5*/
				putDataCustom(datas,"","",TextResource.localize("CAS003_13"),"",1);
				/**put row 6*/
				putDataCustom(datas,"",TextResource.localize("CAS003_16"),"","",1);
				/**put row 7*/
				putDataCustom(datas,"",TextResource.localize("CAS003_19"),"","",1);
				/**put row 8*/
				putDataCustom(datas,"",TextResource.localize("CAS003_22"),"","",1);
				/**put row 9*/
				putDataCustom(datas,"",TextResource.localize("CAS003_25"),"","",0);
				/**put row 10*/
				putDataCustom(datas,"",TextResource.localize("CAS003_26"),"","",0);
			}else {
				putDataCustom(datas,TextResource.localize("CAS003_3"),"","","???",0);
				String digit = TextResource.localize("CAS003_58");
				putDataCustom(datas,"",TextResource.localize("CAS003_4"),"",String.valueOf(passPolicy.lowestDigits)+digit,1);
				/**put row 3*/
				putDataCustom(datas,"",TextResource.localize("CAS003_6"),TextResource.localize("CAS003_7"),String.valueOf(passPolicy.alphabetDigit)+digit,1);
				/**put row 4*/
				putDataCustom(datas,"","",TextResource.localize("CAS003_10"),String.valueOf(passPolicy.numberOfDigits)+digit,1);
				/**put row 5*/
				putDataCustom(datas,"","",TextResource.localize("CAS003_13"),String.valueOf(passPolicy.symbolCharacters)+digit,1);
				/**put row 6*/
				putDataCustom(datas,"",TextResource.localize("CAS003_16"),"",String.valueOf(passPolicy.historyCount)+TextResource.localize("CAS003_59"),1);//CAS003_59
				/**put row 7*/
				putDataCustom(datas,"",TextResource.localize("CAS003_19"),"",String.valueOf(passPolicy.validityPeriod)+TextResource.localize("CAS003_60"),1);//CAS003_60
				/**put row 8*/
				putDataCustom(datas,"",TextResource.localize("CAS003_22"),"",String.valueOf(passPolicy.notificationPasswordChange)+TextResource.localize("CAS003_61"),1);//CAS003_61
				/**put row 9*/
				putDataCustom(datas,"",TextResource.localize("CAS003_25"),"",passPolicy.initialPasswordChange?"???":"-",0);
				/**put row 10*/
				putDataCustom(datas,"",TextResource.localize("CAS003_26"),"",passPolicy.loginCheck?"???":"-",0);
			}
		}else {
			putDataCustom(datas,TextResource.localize("CAS003_3"),"","","",0);
			putDataCustom(datas,"",TextResource.localize("CAS003_4"),"","",1);
			/**put row 3*/
			putDataCustom(datas,"",TextResource.localize("CAS003_6"),TextResource.localize("CAS003_7"),"",1);
			/**put row 4*/
			putDataCustom(datas,"","",TextResource.localize("CAS003_10"),"",1);
			/**put row 5*/
			putDataCustom(datas,"","",TextResource.localize("CAS003_13"),"",1);
			/**put row 6*/
			putDataCustom(datas,"",TextResource.localize("CAS003_16"),"","",1);
			/**put row 7*/
			putDataCustom(datas,"",TextResource.localize("CAS003_19"),"","",1);
			/**put row 8*/
			putDataCustom(datas,"",TextResource.localize("CAS003_22"),"","",1);
			/**put row 9*/
			putDataCustom(datas,"",TextResource.localize("CAS003_25"),"","",0);
			/**put row 10*/
			putDataCustom(datas,"",TextResource.localize("CAS003_26"),"","",0);
		}
			/**put account policy*/
		if(acountPolicy!=null){
			if(acountPolicy.isUse){
				putDataCustom(datas,TextResource.localize("CAS003_27"),"","","???",0);
				putDataCustom(datas,"",TextResource.localize("CAS003_28"),TextResource.localize("CAS003_29"),timeToString(acountPolicy.lockInterval),1);
				putDataCustom(datas,"","",TextResource.localize("CAS003_30"),acountPolicy.errorCount+TextResource.localize("CAS003_59"),1);
				putDataCustom(datas,"",TextResource.localize("CAS003_32"),"",acountPolicy.getLockOutMessage(),0);
			}else {
				putDataCustom(datas,TextResource.localize("CAS003_27"),"","","-",0);
				putDataCustom(datas,"",TextResource.localize("CAS003_28"),TextResource.localize("CAS003_29"),"",0);
				putDataCustom(datas,"","",TextResource.localize("CAS003_30"),"",0);
				putDataCustom(datas,"",TextResource.localize("CAS003_32"),"","",0);
			}
		}else {
			putDataCustom(datas,TextResource.localize("CAS003_27"),"","","",0);
			putDataCustom(datas,"",TextResource.localize("CAS003_28"),TextResource.localize("CAS003_29"),"",0);
			putDataCustom(datas,"","",TextResource.localize("CAS003_30"),"",0);
			putDataCustom(datas,"",TextResource.localize("CAS003_32"),"","",0);
		}
		return datas;			
		}
	
	private void putDataCustom(List<MasterData> datas,String column1, String column2, String column3, String column4, int possition) {
		Map<String, Object> data = new HashMap<>();
		putDataEmpty(data);
		/**put setting password*/
		data.put("??????",column1);
		data.put("column2",column2);
		data.put("column3",column3);
		data.put("???",column4);
		datas.add(alignMasterDataSheetRole(data,possition));
	}
	@Override
	public String mainSheetName() {
		return TextResource.localize("CAS003_37");
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}
	
	private void putDataEmpty(Map<String, Object> data){
		data.put("??????","");
		data.put("column2","");
		data.put("column3","");
		data.put("???","");
	}
	private MasterData alignMasterDataSheetRole(Map<String, Object> data,int possiton) {
		MasterData masterData = new MasterData(data, null, "");
		masterData.cellAt("??????").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("column2").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("column3").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		/** 0 is align left*/
		if(possiton==0){
			masterData.cellAt("???").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		/** 1 is align right*/	
		}else {
			masterData.cellAt("???").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		}
		return masterData;
	}
	private String timeToString(int value ){
		if(value%60<10){
			return  String.valueOf(value/60)+":0"+  String.valueOf(value%60);
		}
		return String.valueOf(value/60)+":"+  String.valueOf(value%60);
	}
}
