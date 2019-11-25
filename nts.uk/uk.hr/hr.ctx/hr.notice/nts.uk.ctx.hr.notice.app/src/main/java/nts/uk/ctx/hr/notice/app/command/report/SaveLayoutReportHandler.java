package nts.uk.ctx.hr.notice.app.command.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class SaveLayoutReportHandler extends CommandHandler<NewLayoutReportCommand> {
	@Inject
	private PersonalReportClassificationRepository reportClsRepo;

	@Inject
	private RegisterPersonalReportItemRepository itemReportClsRepo;

	private static String itemCodeLatest;
	
	private static String ctgCodeLatest;
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
		Map<String, Boolean> checkExits = this.reportClsRepo.checkExist(cid, cmd.getReportCode(), cmd.getReportName());
		int maxLayoutId = this.reportClsRepo.maxId(cid);
		int maxDisOrder = this.reportClsRepo.maxDisorder(cid);
		if (checkExits.isEmpty()) {
			this.reportClsRepo.insert(PersonalReportClassification.createFromJavaType(cid, maxLayoutId + 1,
					cmd.getReportCode(), cmd.getReportName(), cmd.getReportNameYomi(), maxDisOrder + 1,
					cmd.isAbolition(), cmd.getReportType(), cmd.getRemark(), cmd.getMemo(), cmd.getMessage(),
					cmd.isFormReport(), true));

		} else {
			Boolean name = checkExits.get("NAME");
			Boolean code = checkExits.get("CODE");

			if (code != null) {
				// JHN011_B222_1_1
				BusinessException codeMessage = new BusinessException("Msgj_41",
						TextResource.localize("JHN011_B222_1_1"));
				codeMessage.setSuppliment("NameID", TextResource.localize("JHN011_B222_1_1"));
				exceptions.addMessage(codeMessage);
			}

			if (name != null) {
				BusinessException nameMessage = new BusinessException("Msgj_42",
						TextResource.localize("JHN011_B222_1_2"));
				nameMessage.setSuppliment("NameID", TextResource.localize("JHN011_B222_1_1"));
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
		if (duplicateNameOpt.isPresent()) {
			BusinessException nameMessage = new BusinessException("Msgj_42", TextResource.localize("JHN011_B222_1_2"));
			nameMessage.setSuppliment("NameID", TextResource.localize("JHN011_B222_1_1"));
			exceptions.addMessage(nameMessage);
			// Has error, throws message
			if (exceptions.cloneExceptions().size() > 0) {
				exceptions.throwExceptions();
			}
		} else {
			Optional<PersonalReportClassification> oldReport = duplicateNameLst.stream()
					.filter(c -> c.getPReportClsId() == cmd.getId().intValue()).findFirst();
			PersonalReportClassification domain = PersonalReportClassification.createFromJavaType(cid,
					oldReport.get().getPReportClsId(), cmd.getReportCode(), cmd.getReportName(),
					cmd.getReportNameYomi(), oldReport.get().getDisplayOrder(), cmd.isAbolition(), cmd.getReportType(),
					cmd.getRemark(), cmd.getMemo(), cmd.getMessage(), cmd.isFormReport(), true);
			this.reportClsRepo.update(domain);

			List<RegisterPersonalReportItem> listItemCls = this.itemReportClsRepo.getAllItemBy(cid,
					cmd.getId().intValue());

			if (cmd.getClassifications().size() > 0) {
				List<ClassificationCommand> classifications = cmd.getClassifications();
				List<RegisterPersonalReportItem> list_cls = new ArrayList<>();
				// check exit itemClassification in table ItemCls , neu có rồi thì xóa đi insert
				// lại. Chưa có thì insert
				if (!CollectionUtil.isEmpty(listItemCls)) {
					this.itemReportClsRepo.removeAllByLayoutId(cmd.getId().intValue());
					itemCodeLatest = null;
					ctgCodeLatest = null;
					classifications.stream().forEach(c -> {
						if (!CollectionUtil.isEmpty(c.getListItemClsDf())) {
							c.getListItemClsDf().stream().forEach(i -> {
								list_cls.add(
										new RegisterPersonalReportItem(cid, cmd.getId().intValue(), cmd.getReportCode(),
												cmd.getReportName(), c.getLayoutItemType(), c.getCategoryCode(),
												c.getCategoryName(), contractCd, false, i.getItemCd(), i.getItemName(),
												i.getDispOrder(), Optional.empty(), "", 0, c.getPersonInfoCategoryID(),
												i.getPersonInfoItemDefinitionID(), c.getDispOrder()));
							});

						}else {
							
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
												cmd.getReportName(), c.getLayoutItemType(), c.getCategoryCode(),
												c.getCategoryName(), contractCd, false, i.getItemCd(), i.getItemName(),
												i.getDispOrder(), Optional.empty(), "", 0, c.getPersonInfoCategoryID(),
												i.getPersonInfoItemDefinitionID(), c.getDispOrder()));
							});

						}else {
							
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
					this.itemReportClsRepo.removeAllByLayoutId(cmd.getId().intValue());
				}
			}
		}
	}
	
	private final static int ITEM_CODE_DEFAUT_NUMBER = 0;
	private final static String SPECIAL_ITEM_CODE = "SEPA";
	
	private final static int CTG_CODE_DEFAUT_NUMBER = 0;
	private final static String SPECIAL_CTG_CODE = "SEPA";
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
