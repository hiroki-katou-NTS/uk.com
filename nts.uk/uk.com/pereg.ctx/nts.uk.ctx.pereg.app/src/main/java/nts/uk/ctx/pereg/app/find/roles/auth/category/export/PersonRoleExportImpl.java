package nts.uk.ctx.pereg.app.find.roles.auth.category.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.i18n.I18NText;
import nts.uk.ctx.pereg.app.find.roles.auth.category.PersonInfoCategoryAuthFinder;
import nts.uk.ctx.pereg.app.find.roles.auth.category.PersonInfoCategoryDetailDto;
import nts.uk.ctx.pereg.app.find.roles.auth.item.PersonInfoItemAuthFinder;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryDetail;
import nts.uk.ctx.pereg.dom.roles.auth.category.RoleCateExportDetail;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.system.config.InstalledProduct;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID("PersonRole")
public class PersonRoleExportImpl implements MasterListData {

	private static final String CAS001_77 = "個人情報ロールコード";
    private static final String CAS001_78 = "個人情報ロール名称";
    private static final String CAS001_79 = "カテゴリ名";
    private static final String CAS001_80 = "設定済";
    private static final String CAS001_81 = "情報種類";
    private static final String CAS001_82 = "他人の情報利用";
    private static final String CAS001_83 = "他人.情報追加";
    private static final String CAS001_84 = "他人.情報削除";
    private static final String CAS001_85 = "他人.履歴追加";
    private static final String CAS001_86 = "他人.履歴削除";
    private static final String CAS001_87 = "他人.未来履歴";
    private static final String CAS001_88 = "他人.過去履歴";
    private static final String CAS001_89 = "本人の情報利用";
    private static final String CAS001_90 = "本人.情報追加 ";
    private static final String CAS001_91 = "本人.情報削除";
    private static final String CAS001_92 = "本人.履歴追加";
    private static final String CAS001_93 = "本人.履歴削除";
    private static final String CAS001_94 = "本人.未来履歴";
    private static final String CAS001_95 = "本人.過去履歴";
    private static final String CAS001_96 = "項目設定済";
    private static final String CAS001_97 = "項目";
    private static final String CAS001_98 = "他人情報権限設定";
    private static final String CAS001_99 = "本人情報権限設定";
    
    @Inject
	private PersonInfoCategoryAuthRepository personCategoryAuthRepository;
    
    @Inject
	private PerInfoItemDefRepositoty itemInfoRepo;
    
    @Inject
	PersonInfoCategoryAuthFinder personInfoCategoryAuthFinder;
    
    @Inject
	PersonInfoItemAuthFinder personInfoItemAuthFinder;
    
    
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		 List <MasterHeaderColumn> columns = new ArrayList<>();
	        columns.add(new MasterHeaderColumn(CAS001_77, TextResource.localize("CAS001_77"),
	        ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_78, TextResource.localize("CAS001_78"),
	        ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_79, TextResource.localize("CAS001_79"),
	        ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_80, TextResource.localize("CAS001_80"),
	        ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_81, TextResource.localize("CAS001_81"),
	        ColumnTextAlign.CENTER, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_82, TextResource.localize("CAS001_82"),
	        ColumnTextAlign.CENTER, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_83, TextResource.localize("CAS001_83"),
	        ColumnTextAlign.CENTER, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_84, TextResource.localize("CAS001_84"),
	        ColumnTextAlign.CENTER, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_85, TextResource.localize("CAS001_85"),
	        ColumnTextAlign.CENTER, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_86, TextResource.localize("CAS001_86"),
	        ColumnTextAlign.CENTER, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_87, TextResource.localize("CAS001_87"),
	        ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_88, TextResource.localize("CAS001_88"),
	        ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_89, TextResource.localize("CAS001_89"),
	        ColumnTextAlign.CENTER, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_90, TextResource.localize("CAS001_90"),
	        ColumnTextAlign.CENTER, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_91, TextResource.localize("CAS001_91"),
	        ColumnTextAlign.CENTER, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_92, TextResource.localize("CAS001_92"),
	        ColumnTextAlign.CENTER, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_93, TextResource.localize("CAS001_93"),
	        ColumnTextAlign.CENTER, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_94, TextResource.localize("CAS001_94"),
	        ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_95, TextResource.localize("CAS001_95"),
	        ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_96, TextResource.localize("CAS001_96"),
	        ColumnTextAlign.CENTER, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_97, TextResource.localize("CAS001_97"),
	        ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_98, TextResource.localize("CAS001_98"),
	        ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(CAS001_99, TextResource.localize("CAS001_99"),
	        ColumnTextAlign.LEFT, "", true));
	        return columns;
	}
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        List <MasterData> datas = new ArrayList<>();
        
        /*// ドメインモデル「ロール」を取得する
        List<Role> roles = roleRepo.findByType(companyId, RoleType.PERSONAL_INFO.value);
        //
        for (Role role : roles) {
        	List<PersonInfoCategoryDetailDto> personInfoCategoryExportDetailDto = this.getAllCategory(role.getRoleId());
        	personInfoCategoryExportDetailDto.stream().forEach(x -> {
        		PersonInfoCategoryAuthDto personInfoCategoryAuthDto = personInfoCategoryAuthFinder.getDetailPersonCategoryAuthByPId(role.getRoleId(), x.getCategoryId());
        		if(personInfoCategoryAuthDto != null) {
        			ItemAuth itemAuth = personInfoItemAuthFinder.getAllItemDetail(role.getRoleId(), personInfoCategoryAuthDto.getPersonInfoCategoryAuthId());
            		itemAuth.getItemLst().stream().forEach(y -> {
                        datas.add(new MasterData(dataContent(role,x,personInfoCategoryAuthDto,y), null, ""));
            		});
            		
        		}
        		
        	});
        }*/
        int payroll = NotUseAtr.NOT_USE.value;
		int personnel = NotUseAtr.NOT_USE.value;
		int atttendance = NotUseAtr.NOT_USE.value;
		List<InstalledProduct> installProduct = AppContexts.system().getInstalledProducts();
		for (InstalledProduct productType : installProduct) {
			switch (productType.getProductType()) {
			case ATTENDANCE:
				atttendance = NotUseAtr.USE.value;
				break;
			case PAYROLL:
				payroll = NotUseAtr.USE.value;
				break;
			case PERSONNEL:
				personnel = NotUseAtr.USE.value;
				break;
			default:
				break;
			}
		}
        personCategoryAuthRepository.getDataExport(payroll, personnel, atttendance).stream().forEach(x -> {
        	datas.add(new MasterData(dataContent(x), null, ""));
        });
        return datas;
	}
	private Map<String, Object> dataContent(RoleCateExportDetail dataRow) {
		Map<String, Object> data = new HashMap<>();
		// A6_1
        data.put(CAS001_77, dataRow.getRoleCode());
        // A6_2
        data.put(CAS001_78, dataRow.getRoleName());
        // A6_3
        data.put(CAS001_79, dataRow.getCategoryName());
        // A6_4
        data.put(CAS001_80, dataRow.getIsCateConfig() ? "●" : "");
        // A6_5
        data.put(CAS001_81, getTypeName(dataRow.getCategoryType()));
        // A6_6
        data.put(CAS001_82, dataRow.getAllowOtherRef() != null ? dataRow.getAllowOtherRef() == 1 ? "○" : "ー" : "");
        // A6_7
        data.put(CAS001_83, dataRow.getCategoryType() != 2 ? "ー" : dataRow.getSelfAllowAddMulti() == null ? "" : dataRow.getSelfAllowAddMulti() == 1 ? "○" : "ー");
        // A6_8
        data.put(CAS001_84, dataRow.getCategoryType() != 2 ? "ー" : dataRow.getSelfAllowDelMulti() == null ? "" : dataRow.getSelfAllowDelMulti() == 1 ? "○" : "ー");
        // A6_9
        data.put(CAS001_85, checkValue2(dataRow.getCategoryType()) != null ? checkValue2(dataRow.getCategoryType()) : dataRow.getSelfAllowAddHis() == null ? "" : dataRow.getSelfAllowAddHis() == 1 ? "○" : "ー");
        // A6_10
        data.put(CAS001_86, checkValue2(dataRow.getCategoryType()) != null ? checkValue2(dataRow.getCategoryType()) : dataRow.getSelfAllowDelHis() == null ? "" : dataRow.getSelfAllowDelHis() == 1 ? "○" : "ー");
        // A6_11
        data.put(CAS001_87, checkValue2(dataRow.getCategoryType()) != null ? checkValue2(dataRow.getCategoryType()) : dataRow.getOtherFutureHisAuth() == null ? "" :checkValue3(dataRow.getOtherFutureHisAuth(),1,0));
        // A6_12
        data.put(CAS001_88, checkValue2(dataRow.getCategoryType()) != null ? checkValue2(dataRow.getCategoryType()) : dataRow.getOtherPastHisAuth() == null ? "" : checkValue3(dataRow.getOtherPastHisAuth(),1,0));
        // A6_13
        data.put(CAS001_89, dataRow.getAllowPersonRef() != null ? dataRow.getAllowPersonRef() == 1 ? "○": "ー" : "");
        // A6_14
        data.put(CAS001_90, checkValue1(dataRow.getCategoryType()) != null ? checkValue1(dataRow.getCategoryType()) : dataRow.getSelfAllowAddMulti() == null ? "" : dataRow.getSelfAllowAddMulti() == 1 ? "○" : "ー");
        // A6_15
        data.put(CAS001_91, checkValue2(dataRow.getCategoryType()) != null ? checkValue1(dataRow.getCategoryType()) : dataRow.getSelfAllowDelMulti() == null ? "" : dataRow.getSelfAllowDelMulti() == 1 ? "○" : "ー");
        // A6_16
        data.put(CAS001_92, checkValue2(dataRow.getCategoryType()) != null ? checkValue2(dataRow.getCategoryType()) : dataRow.getSelfAllowAddHis() == null ? "" : dataRow.getSelfAllowAddHis() == 1 ? "○" : "ー");
        // A6_17
        data.put(CAS001_93, checkValue2(dataRow.getCategoryType()) != null ? checkValue2(dataRow.getCategoryType()) : dataRow.getSelfAllowDelHis() == null ? "" : dataRow.getSelfAllowDelHis() == 1 ? "○" : "ー");
        // A6_18
        data.put(CAS001_94, checkValue2(dataRow.getCategoryType()) != null ? checkValue2(dataRow.getCategoryType()) : dataRow.getSelfFutureHisAuth() ==  null ? "" :checkValue3(dataRow.getSelfFutureHisAuth(),1,0));
        // A6_19
        data.put(CAS001_95, checkValue2(dataRow.getCategoryType()) != null ? checkValue2(dataRow.getCategoryType()) : dataRow.getSelfPastHisAuth() == null ? "" :checkValue3(dataRow.getSelfPastHisAuth(),1,0));
        // A6_20
        data.put(CAS001_96, dataRow.getIsItemConfig() ? "●" : "");
        // A6_21
        data.put(CAS001_97, dataRow.getItemName());
        // A6_22
        data.put(CAS001_98, checkValue3(dataRow.getOtherAuth(),dataRow.getAllowOtherRef(),0));
        // A6_23
        data.put(CAS001_99,checkValue3(dataRow.getSelfAuth(),dataRow.getAllowPersonRef(),0));
        return data;
	}
	
	private String checkValue1(Integer categoryType) {
		String value = null;
		if(categoryType == null)
        	value = "";
        CategoryType type = EnumAdaptor.valueOf(categoryType , CategoryType.class);
        if(type.value != 2)
        	value = "ー";
        return value;
	}
	
	private String checkValue2(Integer categoryType) {
		String value = null;
		if(categoryType == null)
        	value = "";
        CategoryType type = EnumAdaptor.valueOf(categoryType , CategoryType.class);
        if(type.value != 3 && type.value != 4) {
        	value = "ー";
        }
     return value;
	}
	
	
	private String checkValue3(Integer authType, Integer check, Integer paramCheck) {
		 String value = null;
		 if(check == paramCheck || authType == null) {
			 value = "ー";
		 } else if(authType == 1) {
	    	 value = I18NText.getText("CAS001_49");
	     } else if(authType == 2) {
	    	 value = I18NText.getText("CAS001_50");
	     } else {
	    	 value = I18NText.getText("CAS001_51");
	     }
	     return value;
	}
	
	private String getTypeName(int categoryType) {
		String nameType = null;
		CategoryType type = EnumAdaptor.valueOf(categoryType , CategoryType.class);
        switch (type) {
		case SINGLEINFO:
			nameType = I18NText.getText("Enum_CategoryType_SINGLE_INFO");
			break;
		case MULTIINFO:
			nameType = I18NText.getText("Enum_CategoryType_MULTI_INFO");
			break;
		case CONTINUOUSHISTORY:
			nameType = I18NText.getText("Enum_CategoryType_CONTINUOUS_HISTORY");
			break;
		case NODUPLICATEHISTORY:
			nameType = I18NText.getText("Enum_CategoryType_NODUPLICATE_HISTORY");
			break;
		case DUPLICATEHISTORY:
			nameType = I18NText.getText("Enum_CategoryType_DUPLICATE_HISTORY");
			break;
		case CONTINUOUS_HISTORY_FOR_ENDDATE:
			nameType = I18NText.getText("Enum_CategoryType_CONTINUOUS_HISTORY");
			break;
		default :
			break;
		}
		return nameType;
	}
	
	private List<PersonInfoCategoryDetailDto> getAllCategory(String roleId) {
		String contractCd = AppContexts.user().contractCode();
		int payroll = NotUseAtr.NOT_USE.value;
		int personnel = NotUseAtr.NOT_USE.value;
		int atttendance = NotUseAtr.NOT_USE.value;
		List<InstalledProduct> installProduct = AppContexts.system().getInstalledProducts();
		for (InstalledProduct productType : installProduct) {
			switch (productType.getProductType()) {
			case ATTENDANCE:
				atttendance = NotUseAtr.USE.value;
				break;
			case PAYROLL:
				payroll = NotUseAtr.USE.value;
				break;
			case PERSONNEL:
				personnel = NotUseAtr.USE.value;
				break;
			default:
				break;
			}
		}
		List<PersonInfoCategoryDetail> ctgSourceLst = this.personCategoryAuthRepository.getAllCategory(roleId,
				AppContexts.user().contractCode(), AppContexts.user().companyId(), payroll, personnel, atttendance);
		List<String> ctgLstId = ctgSourceLst.stream().map(c -> {
			return c.getCategoryId();
		}).collect(Collectors.toList());

		Map<String, List<Object[]>> itemByCtgId = this.itemInfoRepo.getAllPerInfoItemDefByListCategoryId(ctgLstId,
				contractCd);

		List<PersonInfoCategoryDetailDto> ctgResultLst = new ArrayList<>();
		for (PersonInfoCategoryDetail i : ctgSourceLst) {
			List<Object[]> item = itemByCtgId.get(i.getCategoryId());
			if (item == null)
				continue;
			if (item.size() > 0)
				ctgResultLst.add(PersonInfoCategoryDetailDto.fromDomain(i));
		}
		return ctgResultLst;
	}
	

}
