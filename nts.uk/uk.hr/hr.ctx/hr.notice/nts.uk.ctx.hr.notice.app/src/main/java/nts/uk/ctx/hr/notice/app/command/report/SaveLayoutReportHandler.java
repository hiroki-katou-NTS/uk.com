package nts.uk.ctx.hr.notice.app.command.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassification;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassificationRepository;
import nts.uk.ctx.hr.notice.dom.report.RegisterPersonalReportItem;
import nts.uk.ctx.hr.notice.dom.report.RegisterPersonalReportItemRepository;
import nts.uk.ctx.hr.notice.dom.report.valueImported.ctg.HumanCategoryPub;
import nts.uk.ctx.hr.notice.dom.report.valueImported.ctg.PerInfoCtgShowImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
/**
 * アルゴリズム「登録処理」を実行する (Thực hiện thuật toán "Xử lý đăng ký")
 * @author lanlt
 *
 */
@Stateless
public class SaveLayoutReportHandler extends CommandHandler<NewLayoutReportCommand> {
	@Inject
	private PersonalReportClassificationRepository reportClsRepo;

	@Inject
	private RegisterPersonalReportItemRepository itemReportClsRepo;
	
	@Inject
	private HumanCategoryPub humanCtgPub;

	private static String itemCodeLatest;
	
	private static String ctgCodeLatest;
	
	private final static int ITEM_CODE_DEFAUT_NUMBER = 0;
	
	private final static String SPECIAL_ITEM_CODE = "SEPA";
	
	private final static String SPECIAL_CTG_CODE = "SEPA";
	
	@Override
	protected void handle(CommandHandlerContext<NewLayoutReportCommand> context) {
		
		NewLayoutReportCommand cmd = context.getCommand();
		
		String cid = AppContexts.user().companyId();

		BundledBusinessException exceptions = BundledBusinessException.newInstance();

		switch (cmd.getAction()) {
		
		case 0: // insert
			// set new layoutID for insert object
			insert(cid, cmd, exceptions);
			
			break;
			
		case 1:
			
			// update
			update(cid, cmd, exceptions);
			
			break;
		}
	}

	public void insert(String cid, NewLayoutReportCommand cmd, BundledBusinessException exceptions) {
		//ドメインモデル「個別届出種類」、「個別届出の登録項目」をすべて取得する (Get tất cả domain mode 「個別届出種類」、「個別届出の登録項目)
		Map<String, Boolean> checkExits = this.reportClsRepo.checkExist(cid, cmd.getReportCode(), cmd.getReportName());
		
		//個別届出種類.個別届出種類IDを採番する Đánh số [type đơn xin cá nhân.ID type đơn xin cá nhân])
		int maxLayoutId = this.reportClsRepo.maxId(cid);
		
		int maxDisOrder = this.reportClsRepo.maxDisorder(cid);
		
		if (checkExits.isEmpty()) {
			//max個別届出種類ID+1
			//maxID typy don xin ca nhan+1
			this.reportClsRepo.insert(PersonalReportClassification.createFromJavaType(cid, maxLayoutId + 1,
					cmd.getReportCode(), cmd.getReportName(), cmd.getReportNameYomi(), maxDisOrder + 1,
					cmd.isAbolition(), cmd.getReportType(), cmd.getRemark(), cmd.getMemo(), cmd.getMessage(),
					cmd.isFormReport(), true));

		} else {
			//届出コード、届出名の重複をチェックする (Check Overlap Code đơn xin, Name đơn xin)
			Boolean name = checkExits.get("NAME");
			
			Boolean code = checkExits.get("CODE");
			
			//メッセージ(#Msgj_41#)、または (#Msgj_42#)を表示する( Hiển thi message (# Msgj_41 #) or (# Msgj_42 #))
			if (code != null) {
				// JHN011_B222_1_1
				BusinessException codeMessage = new BusinessException("MsgJ_41",
						TextResource.localize("JHN011_B222_1_1"));
				
				codeMessage.setSuppliment("NameID", TextResource.localize("JHN011_B222_1_1"));
				
				exceptions.addMessage(codeMessage);
			}

			if (name != null) {
				BusinessException nameMessage = new BusinessException("MsgJ_42",
						TextResource.localize("JHN011_B222_1_2"));
				
				nameMessage.setSuppliment("NameID", TextResource.localize("JHN011_B222_1_2"));
				
				exceptions.addMessage(nameMessage);

			}

			// Has error, throws message
			if (exceptions.cloneExceptions().size() > 0) {
				
				exceptions.throwExceptions();
				
			}

		}
	}

	public void update(String cid, NewLayoutReportCommand cmd, BundledBusinessException exceptions) {
		String contractCd = AppContexts.user().contractCode();
		
		List<PersonalReportClassification> duplicateNameLst = this.reportClsRepo.getAllSameNameByCid(cid,
				cmd.getReportName());

		Optional<PersonalReportClassification> duplicateNameOpt = duplicateNameLst.stream()
				.filter(c -> c.getPReportClsId() != cmd.getId().intValue()).findFirst();
		
		//届出コード、届出名の重複をチェックする (Check Overlap Code đơn xin, Name đơn xin)
		if (duplicateNameOpt.isPresent()) {
		
			BusinessException nameMessage = new BusinessException("MsgJ_42", TextResource.localize("JHN011_B222_1_2"));
			
			nameMessage.setSuppliment("NameID", TextResource.localize("JHN011_B222_1_2"));
			
			exceptions.addMessage(nameMessage);
			
			// Has error, throws message
			if (exceptions.cloneExceptions().size() > 0) {
			
				exceptions.throwExceptions();
			
			}
		} else {

			Optional<PersonalReportClassification> oldReport = this.reportClsRepo
					.getDetailReportClsByReportClsID(cid, cmd.getId().intValue());
			
			if(!oldReport.isPresent()) {
				return;
			}
			
			PersonalReportClassification domain = PersonalReportClassification.createFromJavaType(cid,
					oldReport.get().getPReportClsId(), cmd.getReportCode(), cmd.getReportName(),
					cmd.getReportNameYomi(), oldReport.get().getDisplayOrder(), cmd.isAbolition(), cmd.getReportType(),
					cmd.getRemark(), cmd.getMemo(), cmd.getMessage(), cmd.isFormReport(), true);
			
			//画面項目「届出コード/届出名など」に選択、入力された情報をドメインモデル「個別届出種類」に更新する 
			//(Chọn item màn hình" Code đơn xin/ Tên đơn xin v.v.", cập nhật thông tin đã input vào domain model ''Type đơn xin cá nhân")
			this.reportClsRepo.update(domain);

			List<RegisterPersonalReportItem> listItemCls = this.itemReportClsRepo.getAllItemBy(cid,
					cmd.getId().intValue());
			
			//画面項目「レイアウトフィールド」表示されているレイアウト項目の選択、入力された情報をドメインモデル「個別届出の登録項目」に更新する 
			//Chọn  layout item đã được hiển thị item màn hình "Layout Field", Cập nhật thông tin đã input vào domain model "item đăng ký đơn xin cá nhân"
			List<String> ctgIds = cmd.getClassifications().stream().filter(c -> c.getPersonInfoCategoryID()!= null).map(c -> c.getPersonInfoCategoryID()).distinct().collect(Collectors.toList());
			
			if(CollectionUtil.isEmpty(ctgIds)) return;
			
			Map<String, PerInfoCtgShowImport> mapCategoryList = this.humanCtgPub.getInfoCtgByCtgIdsAndCid(cid, ctgIds).stream().collect(Collectors.toMap(c -> c.getId(), c -> c));
			
			if (cmd.getClassifications().size() > 0) {
				
				List<ClassificationCommand> classifications = cmd.getClassifications();
				
				List<RegisterPersonalReportItem> list_cls = new ArrayList<>();
				// check exit itemClassification in table ItemCls , neu có rồi thì xóa đi insert
				// lại. Chưa có thì insert
				
				if (!CollectionUtil.isEmpty(listItemCls)) {
					
					this.itemReportClsRepo.removeAllByLayoutId(cid, cmd.getId().intValue());
					
					itemCodeLatest = null;
					
					ctgCodeLatest = null;
					
					classifications.stream().forEach(c -> {
						
						if (!CollectionUtil.isEmpty(c.getListItemClsDf())) {
							
							c.getListItemClsDf().stream().forEach(i -> {
								
								list_cls.add(
										new RegisterPersonalReportItem(cid, cmd.getId().intValue(), cmd.getReportCode(),
												cmd.getReportName(), c.getLayoutItemType(),
												mapCategoryList.get(c.getPersonInfoCategoryID()) == null ? ""
														: mapCategoryList.get(c.getPersonInfoCategoryID())
																.getCategoryCode(),
												mapCategoryList.get(c.getPersonInfoCategoryID()) == null ? ""
														: mapCategoryList.get(c.getPersonInfoCategoryID())
																.getCategoryName(),
												contractCd, false, i.getItemCd(), i.getItemName(), i.getDispOrder(),
												Optional.empty(), "", 0, c.getPersonInfoCategoryID(),
												i.getPersonInfoItemDefinitionID(), c.getDispOrder()));
							});

						}else {
							
							//項目が[区切り線]の場合はCATEGORY_CD、ITEM_CDに"SEPA***"を保存します。***は001～999を採番する
							String itemCd = this.createNewCode(itemCodeLatest, SPECIAL_ITEM_CODE);
							
							String categoryCd = this.createNewCode(ctgCodeLatest, SPECIAL_CTG_CODE);
							
							ctgCodeLatest = categoryCd;
							
							itemCodeLatest = itemCd;
							
							list_cls.add(
									new RegisterPersonalReportItem(cid, cmd.getId().intValue(), cmd.getReportCode(),
											cmd.getReportName(), c.getLayoutItemType(), categoryCd,
											SPECIAL_CTG_CODE, contractCd, false, itemCd, SPECIAL_ITEM_CODE,
											1, Optional.empty(), "", 0, categoryCd,
											itemCd, c.getDispOrder()));
						}

					});

					this.itemReportClsRepo.insertAll(list_cls);
					
				} else {
					
					itemCodeLatest = null;
					
					ctgCodeLatest = null;
					
					classifications.stream().forEach(c -> {
						
						if (!CollectionUtil.isEmpty(c.getListItemClsDf())) {
							
							c.getListItemClsDf().stream().forEach(i -> {
								
								list_cls.add(
										new RegisterPersonalReportItem(cid, cmd.getId().intValue(), cmd.getReportCode(),
												cmd.getReportName(), c.getLayoutItemType(), 
												mapCategoryList.get(c.getPersonInfoCategoryID()) == null ? ""
														: mapCategoryList.get(c.getPersonInfoCategoryID())
																.getCategoryCode(),
												mapCategoryList.get(c.getPersonInfoCategoryID()) == null ? ""
														: mapCategoryList.get(c.getPersonInfoCategoryID())
																.getCategoryName(),
												contractCd, false, i.getItemCd(), i.getItemName(),
												i.getDispOrder(), Optional.empty(), "", 0, c.getPersonInfoCategoryID(),
												i.getPersonInfoItemDefinitionID(), c.getDispOrder()));
							});

						}else {
							
							//項目が[区切り線]の場合はCATEGORY_CD、ITEM_CDに"SEPA***"を保存します。***は001～999を採番する
							String itemCd = this.createNewCode(itemCodeLatest, SPECIAL_ITEM_CODE);
							
							String categoryCd = this.createNewCode(ctgCodeLatest, SPECIAL_CTG_CODE);
							
							ctgCodeLatest = categoryCd;
							
							itemCodeLatest = itemCd;
							
							list_cls.add(
									new RegisterPersonalReportItem(cid, cmd.getId().intValue(), cmd.getReportCode(),
											cmd.getReportName(), c.getLayoutItemType(), categoryCd,
											SPECIAL_CTG_CODE, contractCd, false, itemCd, SPECIAL_ITEM_CODE,
											1, Optional.empty(), "", 0, categoryCd,
											itemCd, c.getDispOrder()));
						}

					});

					this.itemReportClsRepo.insertAll(list_cls);
				}
			} else {
				
				if (!CollectionUtil.isEmpty(listItemCls)) {
					
					this.itemReportClsRepo.removeAllByLayoutId(cid, cmd.getId().intValue());
				
				}
			}
		}
	}
	

	private String createNewCode(String codeLastest, String strSpecial) {
		
		String numberCode = String.valueOf(ITEM_CODE_DEFAUT_NUMBER + 1);
		
		if (codeLastest != null) {
			
			numberCode = String.valueOf(Integer.parseInt(codeLastest.substring(4, 7)) + 1);
		
		}
		
		for (int i = 3; i > 0; i--) {
			
			if (i == numberCode.length()) {
				break;
			}
			
			strSpecial += "0";
			
		}
		
		return strSpecial + numberCode;
	}
}
